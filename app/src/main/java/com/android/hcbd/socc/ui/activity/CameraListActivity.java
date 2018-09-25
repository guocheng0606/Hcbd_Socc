package com.android.hcbd.socc.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.adapter.CameraAdapter;
import com.android.hcbd.socc.camera.realplay.EZRealPlayActivity;
import com.android.hcbd.socc.camera.util.EZUtils;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.entity.VideoKey;
import com.android.hcbd.socc.remoteplayback.PlayBackListActivity;
import com.android.hcbd.socc.remoteplayback.RemoteListContant;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ProgressDialogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.google.gson.Gson;
import com.lany.state.StateLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.videogo.constant.IntentConsts;
import com.videogo.exception.BaseException;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.util.DateTimeUtil;
import com.videogo.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraListActivity extends BaseActivity implements View.OnClickListener {
    protected static final String TAG = "CameraListActivity";
    public final static int REQUEST_CODE = 100;
    public final static int RESULT_CODE = 101;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.stateLayout)
    StateLayout stateLayout;

    private CameraAdapter adapter;
    private List<DeviceInfo> deviceInfoList = new ArrayList<>();
    private VideoKey videoKey;
    private String videoKeyStr;
    private int mClickType;

    public final static int TAG_CLICK_PLAY = 1;
    public final static int TAG_CLICK_REMOTE_PLAY_BACK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_list);
        ButterKnife.bind(this);

        adapter = new CameraAdapter(CameraListActivity.this, deviceInfoList);
        listView.setAdapter(adapter);
        initHttpData();
        ivBack.setOnClickListener(this);
        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new CameraAdapter.onViewItemClickListener() {

            @Override
            public void onPlayClick(View v, int position) {
                mClickType = TAG_CLICK_PLAY;
                DeviceInfo deviceInfo = adapter.getAllData().get(position);
                new GetCamersInfoTask(deviceInfo).execute();
            }

            @Override
            public void onRemotePlayBackClick(View view, int position) {
                mClickType = TAG_CLICK_REMOTE_PLAY_BACK;
                DeviceInfo deviceInfo = adapter.getAllData().get(position);
                new GetCamersInfoTask(deviceInfo).execute();
            }
        });
    }

    private class GetCamersInfoTask extends AsyncTask<Void, Void, EZDeviceInfo> {
        private DeviceInfo deviceInfo;

        public GetCamersInfoTask(DeviceInfo deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialogUtils.showLoading(CameraListActivity.this);
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
            switch (mClickType) {
                case TAG_CLICK_PLAY:
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

                        Intent intent = new Intent(CameraListActivity.this, EZRealPlayActivity.class);
                        intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                        intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, ezDeviceInfo);
                        startActivityForResult(intent, REQUEST_CODE);
                        return;
                    }
                    break;
                case TAG_CLICK_REMOTE_PLAY_BACK:
                    if (ezDeviceInfo.getCameraNum() <= 0 || ezDeviceInfo.getCameraInfoList() == null || ezDeviceInfo.getCameraInfoList().size() <= 0) {
                        LogUtil.d(TAG, "cameralist is null or cameralist size is 0");
                        return;
                    }
                    if (ezDeviceInfo.getCameraNum() == 1 && ezDeviceInfo.getCameraInfoList() != null && ezDeviceInfo.getCameraInfoList().size() == 1) {
                        LogUtil.d(TAG, "cameralist have one camera");
                        EZCameraInfo cameraInfo = EZUtils.getCameraInfoFromDevice(ezDeviceInfo, 0);
                        if (cameraInfo == null) {
                            return;
                        }
                        Intent intent = new Intent(CameraListActivity.this, PlayBackListActivity.class);
                        intent.putExtra(RemoteListContant.QUERY_DATE_INTENT_KEY, DateTimeUtil.getNow());
                        intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                        startActivity(intent);
                        return;
                    }
                    break;
                default:
                    break;
            }

        }


    }

    private void initHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl() + "/soccApp/appVideoAction!list.action")
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        stateLayout.showLoading();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            Gson gson = new Gson();
                            videoKeyStr = jsonObject.getString("videoKey");
                            videoKey = gson.fromJson(jsonObject.getString("videoKey"), VideoKey.class);
                            JSONArray array = new JSONArray(jsonObject.getString("data"));
                            deviceInfoList.clear();
                            if (array.length() > 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    DeviceInfo deviceInfo = gson.fromJson(array.getString(i), DeviceInfo.class);
                                    deviceInfoList.add(deviceInfo);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
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
                        stateLayout.showContent();
                    }

                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
        }
    }

}
