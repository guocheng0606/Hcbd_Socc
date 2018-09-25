package com.android.hcbd.socc.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.adapter.DangerDeviceListAdapter;
import com.android.hcbd.socc.adapter.MapDeviceStateAdapter;
import com.android.hcbd.socc.camera.realplay.EZRealPlayActivity;
import com.android.hcbd.socc.camera.util.EZUtils;
import com.android.hcbd.socc.entity.DataSearchInfo;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.entity.DeviceStateListInfo;
import com.android.hcbd.socc.entity.LocationInfo;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.listener.MyLocationListener;
import com.android.hcbd.socc.listener.MyOrientationListener;
import com.android.hcbd.socc.util.CoordUtils;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.OpenLocalMapUtil;
import com.android.hcbd.socc.util.ProgressDialogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.blankj.utilcode.utils.KeyboardUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.videogo.constant.IntentConsts;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.android.hcbd.socc.ui.activity.CameraListActivity.REQUEST_CODE;


public class MapActivity extends BaseActivity implements View.OnClickListener{

    protected static final String TAG = "MapActivity";

    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_refresh)
    ImageView iv_refresh;
    @BindView(R.id.iv_more)
    ImageView iv_more;
    @BindView(R.id.tv_connect)
    TextView tv_connect;
    @BindView(R.id.tv_danger)
    TextView tv_danger;
    @BindView(R.id.tv_disconnect)
    TextView tv_disconnect;
    @BindView(R.id.activity_map)
    LinearLayout activityMap;
    @BindView(R.id.ib_small)
    ImageButton ibSmall;
    @BindView(R.id.ib_large)
    ImageButton ibLarge;
    @BindView(R.id.ib_mode)
    ImageButton ibMode;
    @BindView(R.id.ib_loc)
    ImageButton ibLoc;
    @BindView(R.id.rl_map)
    RelativeLayout rlMap;


    private BaiduMap mBaiduMap;
    private DeviceStateListInfo deviceStateListInfo;

    public LocationClient mLocationClient;
    public BDLocationListener mBDLocationListener = new MyLocationListener();
    private boolean isFirstLocation;

    //模式切换，正常模式
    private boolean modeFlag = true;
    //当前地图缩放级别
    private float zoomLevel;

    /**
     * 方向传感器的监听器
     */
    private MyOrientationListener myOrientationListener;
    /**
     * 方向传感器X方向的值
     */
    private int mXDirection;
    private LocationInfo mCurrentlocation;

    private List<DeviceStateListInfo.DataInfo> connectList = new ArrayList<>();
    private List<DeviceStateListInfo.DataInfo> dangerList = new ArrayList<>();
    private List<DeviceStateListInfo.DataInfo> disconnectList = new ArrayList<>();

    private Timer timer;
    private boolean isrefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        mMapView.onCreate(this, savedInstanceState);
        mBaiduMap = mMapView.getMap();

        // 隐藏百度的LOGO
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        // 不显示地图上比例尺
        mMapView.showScaleControl(true);

        // 不显示地图缩放控件（按钮控制栏）
        mMapView.showZoomControls(false);

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setIndoorEnable(true);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));

        mLocationClient = new LocationClient(this);     //声明LocationClient类
        mLocationClient.registerLocationListener(mBDLocationListener);
        initLocation();
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        /*BitmapDescriptor bitmap = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_geo);*/
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null,4521984,4521984);
        mBaiduMap.setMyLocationConfiguration(config);
        httpData();
        initListener();
        initOritationListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()) {
            case MessageEvent.EVENT_LOCATION_SUCCESS:
                mCurrentlocation = (LocationInfo) event.getObj();
                if(mCurrentlocation == null)
                    return;
                if (isFirstLocation) {
                    isFirstLocation = false;
                    if(mBaiduMap.getMapStatus().zoom < 12){
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
                        ibLarge.setEnabled(true);
                        ibLarge.setImageResource(R.drawable.icon_zoomin);
                        ibSmall.setEnabled(true);
                        ibSmall.setImageResource(R.drawable.icon_zoomout);
                    }
                    LatLng ll = new LatLng(mCurrentlocation.getLatitude(), mCurrentlocation.getLongitude());
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                    mBaiduMap.animateMapStatus(update);
                }
                //构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(mCurrentlocation.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mXDirection).latitude(mCurrentlocation.getLatitude())
                        .longitude(mCurrentlocation.getLongitude()).build();
                // 设置定位数据
                mBaiduMap.setMyLocationData(locData);
                // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                /*BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_geo);
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmap);
                mBaiduMap.setMyLocationConfiguration(config);*/
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    if (isrefresh) {
                        isrefresh = false;
                        if (timer == null) {
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    httpData();
                                }
                            }, 0, Integer.parseInt(deviceStateListInfo.getIndexRate()) * 1000);
                        }
                    }

                    tv_connect.setText(Html.fromHtml("连接：" + "<font color=\"#12ef12\">" + deviceStateListInfo.getConnect() + "</font>"));
                    tv_danger.setText(Html.fromHtml("危险：" + "<font color=\"#ff0000\">" + deviceStateListInfo.getDanger() + "</font>"));
                    tv_disconnect.setText(Html.fromHtml("断开：" + "<font color=\"#ffbc00\">" + deviceStateListInfo.getDisconnect() + "</font>"));
                    mBaiduMap.clear();
                    List<DeviceStateListInfo.DataInfo> dataInfoList = deviceStateListInfo.getDataInfoList();
                    for (int i = 0; i < dataInfoList.size(); i++) {
                        final LatLng point = new LatLng(dataInfoList.get(i).getY(), dataInfoList.get(i).getX());
                        final String imgUrl = MyApplication.getInstance().getBsaeUrl() + dataInfoList.get(i).getImg().replaceAll("\\\\","/" );
                        final String title = dataInfoList.get(i).getName();
                        //使用RxAndroid异步处理耗时操作
                        Observable.create(new Observable.OnSubscribe<Bitmap>() {
                            @Override
                            public void call(Subscriber<? super Bitmap> subscriber) { //创建Observable，用来发送数据
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Glide.with(MapActivity.this)
                                            .load(imgUrl)
                                            .asBitmap()
                                            .override(75, 75)
                                            .fitCenter()
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                            .get();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                                subscriber.onNext(bitmap);
                                subscriber.onCompleted();
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())  //使用ui线程来接收Observable发送的数据
                                .subscribe(new Observer<Bitmap>() {  //创建了Subscriber(订阅者)，用来接收事件处理
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Bitmap bitmap) {
                                        //构建Marker图标
                                        BitmapDescriptor bt = null;
                                        if (bitmap == null) {
                                            bt = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
                                        } else {
                                            bt = BitmapDescriptorFactory.fromBitmap(bitmap);
                                        }
                                        OverlayOptions option = new MarkerOptions()
                                                .position(point)
                                                .icon(bt)
                                                .title(title);
                                        //在地图上添加Marker，并显示
                                        mBaiduMap.addOverlay(option);
                                    }
                                });
                    }

                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
                    ibLarge.setEnabled(true);
                    ibLarge.setImageResource(R.drawable.icon_zoomin);
                    ibSmall.setEnabled(true);
                    ibSmall.setImageResource(R.drawable.icon_zoomout);
                    String[] points = deviceStateListInfo.getPoints().split(",");
                    LatLng ll = new LatLng(Double.parseDouble(points[1]), Double.parseDouble(points[0]));
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                    mBaiduMap.animateMapStatus(update);

                    connectList.clear();
                    dangerList.clear();
                    disconnectList.clear();
                    for (int i = 0; i < deviceStateListInfo.getDataInfoList().size(); i++) {
                        if (deviceStateListInfo.getDataInfoList().get(i).getState().equals("连接"))
                            connectList.add(deviceStateListInfo.getDataInfoList().get(i));
                        if (deviceStateListInfo.getDataInfoList().get(i).getState().equals("危险"))
                            dangerList.add(deviceStateListInfo.getDataInfoList().get(i));
                        if (deviceStateListInfo.getDataInfoList().get(i).getState().equals("断开"))
                            disconnectList.add(deviceStateListInfo.getDataInfoList().get(i));
                    }
                    if (dangerList.size() > 0)
                        showDangerDialog(dangerList);
                    ProgressDialogUtils.dismissLoading();
                    break;
            }
        }
    };

    private void httpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.device_state_list_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        realjson(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShortToast(MyApplication.getInstance(), "请检查是否连接网络！");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    private void realjson(final String json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                    if (deviceStateListInfo == null)
                        deviceStateListInfo = new DeviceStateListInfo();
                    deviceStateListInfo.setIndexRate(jsonObject.getString("indexRate"));
                    deviceStateListInfo.setDanger(jsonObject.getString("danger"));
                    deviceStateListInfo.setConnect(jsonObject.getString("connect"));
                    deviceStateListInfo.setDisconnect(jsonObject.getString("disconnect"));
                    deviceStateListInfo.setPoints(jsonObject.getString("points"));
                    deviceStateListInfo.setTypeId(jsonObject.getString("typeId"));

                    Gson gson = new Gson();
                    JSONArray array = new JSONArray(jsonObject.getString("data"));
                    List<DeviceStateListInfo.DataInfo> dataInfoList = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        DeviceStateListInfo.DataInfo dataInfo = gson.fromJson(array.getString(i), DeviceStateListInfo.DataInfo.class);
                        dataInfoList.add(dataInfo);
                    }
                    deviceStateListInfo.setDataInfoList(dataInfoList);

                    Message message = new Message();
                    message.what = 0x01;
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initListener() {
        iv_back.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        iv_more.setOnClickListener(this);
        tv_connect.setOnClickListener(this);
        tv_danger.setOnClickListener(this);
        tv_disconnect.setOnClickListener(this);
        ibSmall.setOnClickListener(this);
        ibLarge.setOnClickListener(this);
        ibLoc.setOnClickListener(this);
        ibMode.setOnClickListener(this);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (int i = 0; i < deviceStateListInfo.getDataInfoList().size(); i++) {
                    LatLng point = new LatLng(deviceStateListInfo.getDataInfoList().get(i).getY(), deviceStateListInfo.getDataInfoList().get(i).getX());
                    if (point.toString().equals(marker.getPosition().toString())) {
                        View view = LayoutInflater.from(MapActivity.this).inflate(R.layout.map_window_layout,null);
                        TextView tv = (TextView) view.findViewById(R.id.tv);
                        tv.setText(deviceStateListInfo.getDataInfoList().get(i).getName());
                        LatLng pt = new LatLng(deviceStateListInfo.getDataInfoList().get(i).getY(), deviceStateListInfo.getDataInfoList().get(i).getX());
                        InfoWindow mInfoWindow = new InfoWindow(view, pt, -60);
                        mBaiduMap.showInfoWindow(mInfoWindow);
                        showMarkerPopupWinder(deviceStateListInfo.getDataInfoList().get(i), pt);
                    }
                }
                return false;
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                mBaiduMap.hideInfoWindow();
            }
        });
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                if(Math.abs(zoomLevel-mapStatus.zoom)<0.000001){
                    return;
                }
                zoomLevel = mapStatus.zoom;
                if(zoomLevel == mBaiduMap.getMinZoomLevel() + 1){
                    ibSmall.setImageResource(R.drawable.icon_zoomout_dis);
                    ibSmall.setEnabled(false);
                    ToastUtils.showShortToast(MapActivity.this,"已缩小至最低级别");
                    ibLarge.setEnabled(true);
                    ibLarge.setImageResource(R.drawable.icon_zoomin);
                }else if(zoomLevel == (modeFlag ? mBaiduMap.getMaxZoomLevel()-1 : mBaiduMap.getMaxZoomLevel())){
                    ibLarge.setImageResource(R.drawable.icon_zoomin_dis);
                    ibLarge.setEnabled(false);
                    ToastUtils.showShortToast(MapActivity.this,"已放大至最高级别");
                    ibSmall.setEnabled(true);
                    ibSmall.setImageResource(R.drawable.icon_zoomout);
                }else{
                    ibLarge.setEnabled(true);
                    ibLarge.setImageResource(R.drawable.icon_zoomin);
                    ibSmall.setEnabled(true);
                    ibSmall.setImageResource(R.drawable.icon_zoomout);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        locationStart();
        // 开启方向传感器
        myOrientationListener.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        locationStop();
        mMapView.onDestroy();
        // 关闭方向传感器
        myOrientationListener.stop();
        closeTimer();
    }

    private void closeTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_refresh:
                ProgressDialogUtils.showLoading(this);
                httpData();
                break;
            case R.id.iv_more:
                showMorePop();
                break;
            case R.id.tv_device_state_list:
                Intent intent = new Intent(this, DeviceStateListActivity.class);
                intent.putExtra("data", deviceStateListInfo);
                startActivityForResult(intent,0x10);
                dismissPopupWindows();
                break;
            case R.id.tv_auto_refresh:
                showAutoRehreshDialog();
                dismissPopupWindows();
                break;
            case R.id.tv_connect:
                showDevicePopupWindow(1);
                break;
            case R.id.tv_danger:
                showDevicePopupWindow(2);
                break;
            case R.id.tv_disconnect:
                showDevicePopupWindow(3);
                break;
            case R.id.ib_large:
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                zoomLevel = mBaiduMap.getMapStatus().zoom;
                if (zoomLevel < (modeFlag ? mBaiduMap.getMaxZoomLevel()-1 : mBaiduMap.getMaxZoomLevel())) {
                    ibSmall.setEnabled(true);
                    ibSmall.setImageResource(R.drawable.icon_zoomout);
                } else {
                    ibLarge.setImageResource(R.drawable.icon_zoomin_dis);
                    ibLarge.setEnabled(false);
                    ToastUtils.showShortToast(this,"已放大至最高级别");
                }
                break;
            case R.id.ib_small:
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                zoomLevel = mBaiduMap.getMapStatus().zoom;
                if (zoomLevel > mBaiduMap.getMinZoomLevel() + 1) {
                    ibLarge.setEnabled(true);
                    ibLarge.setImageResource(R.drawable.icon_zoomin);
                } else {
                    ibSmall.setImageResource(R.drawable.icon_zoomout_dis);
                    ibSmall.setEnabled(false);
                    ToastUtils.showShortToast(this,"已缩小至最低级别");
                }
                break;
            case R.id.ib_loc:
                isFirstLocation = true;
                break;
            case R.id.ib_mode:
                if(modeFlag){
                    modeFlag = false;
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }else{
                    modeFlag = true;
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
                zoomLevel = mBaiduMap.getMapStatus().zoom;
                if(zoomLevel == mBaiduMap.getMinZoomLevel()){
                    ibSmall.setImageResource(R.drawable.icon_zoomout_dis);
                    ibSmall.setEnabled(false);
                    ibLarge.setEnabled(true);
                    ibLarge.setImageResource(R.drawable.icon_zoomin);
                }else if(zoomLevel == (modeFlag ? mBaiduMap.getMaxZoomLevel()-1 : mBaiduMap.getMaxZoomLevel())){
                    ibLarge.setImageResource(R.drawable.icon_zoomin_dis);
                    ibLarge.setEnabled(false);
                    ibSmall.setEnabled(true);
                    ibSmall.setImageResource(R.drawable.icon_zoomout);
                }else{
                    ibLarge.setEnabled(true);
                    ibLarge.setImageResource(R.drawable.icon_zoomin);
                    ibSmall.setEnabled(true);
                    ibSmall.setImageResource(R.drawable.icon_zoomout);
                }
                break;
        }

    }

    PopupWindow devicePopup;
    private void showDevicePopupWindow(final int i) {
        if(devicePopup != null){
            devicePopup.dismiss();
            devicePopup = null;
        }
        View popupView = getLayoutInflater().inflate(R.layout.popup_map_device_state_layout, null);
        devicePopup = new PopupWindow(popupView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        devicePopup.setFocusable(true);
        devicePopup.setOutsideTouchable(true);
        devicePopup.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        // 设置popWindow的显示和消失动画
        //devicePopup.setAnimationStyle(R.style.mypopwindow_anim_style);

        TextView title = (TextView) popupView.findViewById(R.id.tv_title);
        ListView listView = (ListView) popupView.findViewById(R.id.listView);
        MapDeviceStateAdapter adapter = null;
        if (i == 1) {
            if (connectList.size() > 0) {
                title.setText("连接设备列表");
                adapter = new MapDeviceStateAdapter(this, connectList);
                listView.setAdapter(adapter);
            } else {
                title.setText("无连接状态设备");
            }
        } else if (i == 2) {
            if (dangerList.size() > 0) {
                showDangerDialog(dangerList);
            } else {
                title.setText("无危险状态设备");
            }
        } else if (i == 3) {
            if (disconnectList.size() > 0) {
                title.setText("断开设备列表");
                adapter = new MapDeviceStateAdapter(this, disconnectList);
                listView.setAdapter(adapter);
            } else {
                title.setText("无断开状态设备");
            }
        }
        final MapDeviceStateAdapter finalAdapter = adapter;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                LatLng ll = null;
                /*if (i == 1) {
                    ll = new LatLng(connectList.get(position).getY(), connectList.get(position).getX());
                } else {
                    ll = new LatLng(disconnectList.get(position).getY(), disconnectList.get(position).getX());
                }*/

                if(mBaiduMap.getMapStatus().zoom < 18){
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));
                    ibLarge.setEnabled(true);
                    ibLarge.setImageResource(R.drawable.icon_zoomin);
                    ibSmall.setEnabled(true);
                    ibSmall.setImageResource(R.drawable.icon_zoomout);
                }

                ll = new LatLng(finalAdapter.getAllData().get(position).getY(), finalAdapter.getAllData().get(position).getX());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(update);

                View wView = LayoutInflater.from(MapActivity.this).inflate(R.layout.map_window_layout,null);
                TextView tv = (TextView) wView.findViewById(R.id.tv);
                tv.setText(finalAdapter.getAllData().get(position).getName());
                InfoWindow mInfoWindow = new InfoWindow(wView, ll, -60);
                mBaiduMap.showInfoWindow(mInfoWindow);
            }
        });
        // 在底部显示
        devicePopup.showAtLocation(activityMap, Gravity.BOTTOM, 0, 0);
    }

    private void showAutoRehreshDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.map_auto_refresh_layout);
        final EditText et_min = (EditText) window.findViewById(R.id.et_min);
        Button btn_save = (Button) window.findViewById(R.id.btn_save);
        et_min.setText(deviceStateListInfo.getIndexRate());
        et_min.setSelection(deviceStateListInfo.getIndexRate().length());
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                KeyboardUtils.hideSoftInput(MapActivity.this);
                if (TextUtils.isEmpty(et_min.getText().toString()))
                    return;
                OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.indexRate_url)
                        .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                        .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                        .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                        .params("state", et_min.getText().toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onStart(Request<String, ? extends Request> request) {
                                super.onStart(request);
                                ProgressDialogUtils.showLoading(MapActivity.this);
                            }

                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                LogUtils.LogShow(result);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(result);
                                    if (!TextUtils.isEmpty(jsonObject.getString("data"))) {
                                        ToastUtils.showShortToast(MyApplication.getInstance(), jsonObject.getString("data"));
                                        isrefresh = true;
                                        closeTimer();
                                        httpData();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    try {
                                        if (!TextUtils.isEmpty(jsonObject.getString("error"))) {
                                            ToastUtils.showShortToast(MyApplication.getInstance(), jsonObject.getString("error"));
                                        }
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                ToastUtils.showShortToast(MyApplication.getInstance(), "请检查网络是否连接！");
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                ProgressDialogUtils.dismissLoading();
                            }
                        });

            }
        });
    }

    private PopupWindow mPopupWindow;
    private void showMorePop() {
        if (mPopupWindow == null) {
            View popupView = getLayoutInflater().inflate(R.layout.popup_map_more_layout, null);
            mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

            TextView tv_device_state_list = (TextView) popupView.findViewById(R.id.tv_device_state_list);
            TextView tv_auto_refresh = (TextView) popupView.findViewById(R.id.tv_auto_refresh);
            tv_device_state_list.setOnClickListener(this);
            tv_auto_refresh.setOnClickListener(this);
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mPopupWindow.dismiss();
                    return false;
                }
            });
        }
        mPopupWindow.showAsDropDown(tv_connect);
    }

    private void dismissPopupWindows() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public void locationStart() {
        if (mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    public void locationStop() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    private void showDangerDialog(final List<DeviceStateListInfo.DataInfo> list) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.dialog_danger_list_layout);
        ListView listView = (ListView) window.findViewById(R.id.listView);
        DangerDeviceListAdapter dangerDeviceListAdapter = new DangerDeviceListAdapter(MapActivity.this, list);
        listView.setAdapter(dangerDeviceListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                Intent intent = new Intent(MapActivity.this, DangerDeviceListActivity.class);
                intent.putExtra("device_name", list.get(i).getName());
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化方向传感器
     */
    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {

            @Override
            public void onOrientationChanged(float x) {
                mXDirection = (int) x;
                //构造定位数据
                System.out.println("direction = " + mXDirection);
                if(mCurrentlocation == null)
                    return;
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(mCurrentlocation.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mXDirection).latitude(mCurrentlocation.getLatitude())
                        .longitude(mCurrentlocation.getLongitude()).build();
                // 设置定位数据
                mBaiduMap.setMyLocationData(locData);
            }
        });
    }


    PopupWindow markerPopup;
    private void showMarkerPopupWinder(final DeviceStateListInfo.DataInfo data, final LatLng latLng) {
        if(markerPopup != null){
            markerPopup.dismiss();
            markerPopup = null;
        }
        View popupView = LayoutInflater.from(MapActivity.this).inflate(R.layout.popup_map_marker_info_layout, null);
        markerPopup = new PopupWindow(popupView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        markerPopup.setFocusable(true);
        markerPopup.setOutsideTouchable(true);
        markerPopup.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        final LinearLayout ll_layout = (LinearLayout) popupView.findViewById(R.id.ll_layout);
        TextView tvName = (TextView) popupView.findViewById(R.id.tv_name);
        TextView tvSn = (TextView) popupView.findViewById(R.id.tv_sn);
        final TextView tvAddress = (TextView) popupView.findViewById(R.id.tv_address);
        final TextView tvMore = (TextView) popupView.findViewById(R.id.tv_more);
        TextView tvPoint = (TextView) popupView.findViewById(R.id.tv_point);
        ImageView ivFollow = (ImageView) popupView.findViewById(R.id.iv_follow);
        final ProgressBar progress = (ProgressBar) popupView.findViewById(R.id.progress);
        tvName.setText("设备名称：" + data.getName());
        tvSn.setText("设备类型：" + data.getType()+"    状态："+data.getState());
        tvPoint.setText("坐标：" + data.getX()+","+data.getY());

        final GeoCoder mSearch = GeoCoder.newInstance();
        final String[] addr = new String[1];
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                }
                //获取地理编码结果
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    tvAddress.setText("");
                    return;
                }
                //获取反向地理编码结果
                addr[0] = reverseGeoCodeResult.getAddress() + reverseGeoCodeResult.getSematicDescription();
                tvAddress.setText("地址：" + reverseGeoCodeResult.getAddress() + reverseGeoCodeResult.getSematicDescription());
                progress.setVisibility(View.GONE);
                ll_layout.setVisibility(View.VISIBLE);
            }
        });
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));

        ivFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MapActivity.this).setTitle("请选择")
                        .setItems(new String[]{"查看线路", "本机地图"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        Intent intent = new Intent(MapActivity.this, LineSelectActivity.class);
                                        intent.putExtra("st",new LatLng(mCurrentlocation.getLatitude(), mCurrentlocation.getLongitude()));
                                        intent.putExtra("en", latLng);
                                        intent.putExtra("addr", addr[0]);
                                        startActivity(intent);
                                        if(markerPopup != null)
                                            markerPopup.dismiss();
                                        break;
                                    case 1:
                                        if(OpenLocalMapUtil.isBaiduMapInstalled() && OpenLocalMapUtil.isGdMapInstalled()){
                                            chooseOpenMap(latLng,data.getName());
                                            return;
                                        }
                                        if(OpenLocalMapUtil.isBaiduMapInstalled()){
                                            openBaiduMap(latLng,data.getName());
                                            return;
                                        }
                                        if(OpenLocalMapUtil.isGdMapInstalled()){
                                            openGaoDeMap(latLng,data.getName());
                                            return;
                                        }
                                        openWebMap(latLng,data.getName());
                                        break;
                                }
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getType().equals("视频")){

                    getALlCamera(data);

                } else {
                    DataSearchInfo dataSearchInfo = new DataSearchInfo();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date(System.currentTimeMillis());
                    dataSearchInfo.setDeviceName(data.getName());
                    if (data.getType().equals("GNSS")){
                        dataSearchInfo.setBeginTime(simpleDateFormat.format(date)+" 00:00");
                        dataSearchInfo.setEndTime(simpleDateFormat.format(date)+" 23:59");
                        dataSearchInfo.setGnss(true);
                    } else {
                        dataSearchInfo.setBeginTime(simpleDateFormat.format(getDateBefore(date,3))+" 00:00");
                        dataSearchInfo.setEndTime(simpleDateFormat.format(date)+" 23:59");
                        dataSearchInfo.setGnss(false);
                    }

                    LogUtils.LogShow("beginTime = "+dataSearchInfo.getBeginTime());

                    Intent intent = new Intent();
                    intent.setClass(MapActivity.this,EChartsActivity.class);
                    intent.putExtra("search_info",dataSearchInfo);
                    startActivity(intent);
                }

            }
        });

        // 在底部显示
        markerPopup.showAtLocation(activityMap, Gravity.BOTTOM, 0, 0);
        markerPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mSearch.destroy();
            }
        });
    }

    private void getALlCamera(final DeviceStateListInfo.DataInfo data){
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl() + "/soccApp/appVideoAction!list.action")
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        ProgressDialogUtils.showLoading(MapActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            Gson gson = new Gson();
                            String videoKeyStr = jsonObject.getString("videoKey");
                            List<DeviceInfo> deviceInfoList = new ArrayList<>();
                            JSONArray array = new JSONArray(jsonObject.getString("data"));

                            if (array.length() > 0) {

                                for (int i = 0; i < array.length(); i++) {
                                    DeviceInfo deviceInfo = gson.fromJson(array.getString(i), DeviceInfo.class);
                                    deviceInfoList.add(deviceInfo);
                                }
                                DeviceInfo deviceInfo = null;
                                for (DeviceInfo info : deviceInfoList){
                                    if (data.getName().equals(info.getName())) {
                                        deviceInfo = info;
                                        break;
                                    }
                                }

                                new GetCamersInfoTask(deviceInfo,videoKeyStr).execute();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShortToast(MyApplication.getInstance(), "请检查是否连接网络！");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                });
    }

    private class GetCamersInfoTask extends AsyncTask<Void, Void, EZDeviceInfo> {
        private DeviceInfo deviceInfo;
        private String videoKeyStr;
        public GetCamersInfoTask(DeviceInfo deviceInfo,String videoKeyStr) {
            this.deviceInfo = deviceInfo;
            this.videoKeyStr = videoKeyStr;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected EZDeviceInfo doInBackground(Void... voids) {
            String[] str = deviceInfo.getSnNo().split("_");
            if (str.length != 2) {
                return null;
            }
            int index = Integer.parseInt(str[1].substring(str[1].length() - 1, str[1].length()));
            String appKey, appToken = "";
            try {
                JSONObject jsonObject = new JSONObject(videoKeyStr);
                appKey = jsonObject.getString("appKey" + index);
                appToken = jsonObject.getString("appToken" + index);

                EZOpenSDK.initLib(MyApplication.getInstance(), appKey);
                EZOpenSDK.getInstance().setAccessToken(appToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<EZDeviceInfo> result = null;
            try {
                result = EZOpenSDK.getInstance().getDeviceList(0, 50);
            } catch (BaseException e) {
                e.printStackTrace();
                return null;
            }
            for (EZDeviceInfo info : result) {
                if (info.getDeviceSerial().equals(str[0])) {
                    return info;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(EZDeviceInfo ezDeviceInfo) {
            super.onPostExecute(ezDeviceInfo);

            ProgressDialogUtils.dismissLoading();
            if (ezDeviceInfo.getCameraNum() <= 0 || ezDeviceInfo.getCameraInfoList() == null || ezDeviceInfo.getCameraInfoList().size() <= 0) {
                LogUtil.d(TAG, "cameralist is null or cameralist size is 0");
                return;
            }
            if (ezDeviceInfo.getCameraNum() == 1 && ezDeviceInfo.getCameraInfoList() != null && ezDeviceInfo.getCameraInfoList().size() == 1) {
                LogUtil.d(TAG, "cameralist have one camera");
                final EZCameraInfo cameraInfo = EZUtils.getCameraInfoFromDevice(ezDeviceInfo, 0);
                if (cameraInfo == null) {
                    return;
                }

                Intent intent = new Intent(MapActivity.this, EZRealPlayActivity.class);
                intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, ezDeviceInfo);
                startActivityForResult(intent, REQUEST_CODE);
                return;
            }
        }


    }

    private void chooseOpenMap(final LatLng latLng , final String name){
        BottomSheet.Builder builder = new BottomSheet
                .Builder(MapActivity.this, com.cocosw.bottomsheet.R.style.BottomSheet_Dialog)
                .title("请选择");
        builder.sheet(0, "百度地图").sheet(1, "高德地图")
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openBaiduMap(latLng,name);
                        } else if(which == 1){
                            openGaoDeMap(latLng,name);
                        }
                    }
                }).build().show();
    }

    private void openBaiduMap(LatLng latLng , String name){
        Intent intent = new Intent();
        // 反向地址解析
        intent.setData(Uri.parse("baidumap://map/marker?location="+latLng.latitude+","+latLng.longitude+"&title="+name+"&src=andr.baidu.openAPIdemo"));
        startActivity(intent);
    }

    private void openGaoDeMap(LatLng latLng ,String name){
        //double[] points = OpenLocalMapUtil.bdToGaoDe(latLng.latitude,latLng.longitude);

        LatLng desLatLng = CoordUtils.convertBaiduToGPS(latLng);

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setPackage("com.autonavi.minimap");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("androidamap://viewMap?sourceApplication=socc&lat="+desLatLng.latitude+"&lon="+desLatLng.longitude+"&poiname="+name+"&dev=1"));
        startActivity(intent);
    }

    private void openWebMap(LatLng latLng ,String name){
        Uri mapUri = Uri.parse("http://api.map.baidu.com/marker?location=" + latLng.latitude + "," + latLng.longitude +
                "&title=" + name + "&output=html");
        Intent loction = new Intent(Intent.ACTION_VIEW, mapUri);
        startActivity(loction);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0x10) {
            if (resultCode == 0x11) {
                if(mBaiduMap.getMapStatus().zoom < 18){
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));
                    ibLarge.setEnabled(true);
                    ibLarge.setImageResource(R.drawable.icon_zoomin);
                    ibSmall.setEnabled(true);
                    ibSmall.setImageResource(R.drawable.icon_zoomout);
                }
                DeviceStateListInfo.DataInfo data = (DeviceStateListInfo.DataInfo) intent.getSerializableExtra("device_info");
                LatLng ll = new LatLng(data.getY(), data.getX());;
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(update);

                View wView = LayoutInflater.from(MapActivity.this).inflate(R.layout.map_window_layout,null);
                TextView tv = (TextView) wView.findViewById(R.id.tv);
                tv.setText(data.getName());
                InfoWindow mInfoWindow = new InfoWindow(wView, ll, -60);
                mBaiduMap.showInfoWindow(mInfoWindow);
            }
        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

}
