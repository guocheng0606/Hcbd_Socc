package com.android.hcbd.socc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DataInfo;
import com.android.hcbd.socc.entity.DataSearchInfo;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ProgressDialogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Series;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EChartsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.webView)
    WebView mWebView;


    private DataSearchInfo dataSearchInfo;
    private int currentPage = 1;
    private List<DataInfo> dataInfoList = new ArrayList<>();
    private List<DeviceInfo> deviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echarts);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(true);
        ProgressDialogUtils.showLoading(this);
        initHttpData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()) {
            case MessageEvent.EVENT_DATA_SEARCH:
                dataSearchInfo = (DataSearchInfo) event.getObj();
                ProgressDialogUtils.showLoading(this);
                currentPage = 1;
                initHttpData();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.device_data_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("device.name", dataSearchInfo == null ? "" : dataSearchInfo.getDeviceName())
                .params("beginTime", dataSearchInfo == null ? "" : dataSearchInfo.getBeginTime())
                .params("endTime", dataSearchInfo == null ? "" : dataSearchInfo.getEndTime())
                .params("currentPage", currentPage)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        if (1 == currentPage)
                            dataInfoList.clear();

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            Gson gson = new Gson();
                            JSONArray a = new JSONArray(jsonObject.getString("deviceList"));
                            deviceList.clear();
                            for (int i = 0; i < a.length(); i++) {
                                DeviceInfo deviceInfo = gson.fromJson(a.getString(i), DeviceInfo.class);
                                deviceList.add(deviceInfo);
                            }

                            JSONArray array = new JSONArray(jsonObject.getString("data"));
                            for (int i = 0; i < array.length(); i++) {
                                DataInfo dataInfo = gson.fromJson(array.getString(i), DataInfo.class);
                                dataInfoList.add(dataInfo);
                            }

                            if (array.length() == 0 && dataSearchInfo == null)
                                ProgressDialogUtils.dismissLoading();

                            JSONObject object = new JSONObject(jsonObject.getString("page"));
                            if (currentPage < object.getInt("totalSize")) {
                                currentPage++;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        initHttpData();
                                    }
                                },1000);

                            } else {
                                mWebView.removeJavascriptInterface("Android");
                                mWebView.addJavascriptInterface(new WebAppInterface(EChartsActivity.this), "Android");
                                mWebView.loadUrl("file:///android_asset/echart/echart.html");
                                mWebView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        view.loadUrl(url);
                                        return true;
                                    }

                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        mWebView.loadUrl("javascript:loadALineChart();");
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShortToast(MyApplication.getInstance(), "请检查是否连接网络！");
                            ProgressDialogUtils.dismissLoading();
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

    @OnClick({R.id.iv_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_search:
                Intent intent = new Intent(this, DataSearchActivity.class);
                intent.putExtra("search_info", dataSearchInfo);
                intent.putExtra("deviceList", (Serializable) deviceList);
                startActivity(intent);
                break;
        }
    }

    /**
     * 注入到JS里的对象接口
     */
    class WebAppInterface {
        Context mContext;

        public WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * 获取
         *
         * @return
         */
        @JavascriptInterface
        public String getLineChartOptions() {
            GsonOption option = markLineChartOptions();
            LogUtils.LogShow(option.toString());
            ProgressDialogUtils.dismissLoading();
            return option.toString();
        }

        @JavascriptInterface
        public GsonOption markLineChartOptions() {
            GsonOption option = new GsonOption();
            option.legend("DX","DY","DZ","X","Y","Z");

            option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);

            option.calculable(true);
            option.tooltip().trigger(Trigger.axis).formatter("{b}<br/>DX：{c}<br/>DY：{c1}<br/>DZ：{c2}<br/>X：{c3}<br/>Y：{c4}<br/>Z：{c5}");

            List<String> mAxisXValues = new ArrayList<>();

            for (int i = 0; i < dataInfoList.size(); i++) {
                mAxisXValues.add(dataInfoList.get(i).getDataTime());
            }

            CategoryAxis categoryAxis = new CategoryAxis();
            categoryAxis.axisLine().onZero(false);
            categoryAxis.boundaryGap(false);
            categoryAxis.setData(mAxisXValues);
            option.xAxis(categoryAxis);

            ValueAxis valueAxis = new ValueAxis();
            option.yAxis(valueAxis);

            DataZoom dataZoom = new DataZoom();
            dataZoom.setType(DataZoomType.slider);
            dataZoom.setStart(0);
            dataZoom.setEnd(16);
            option.dataZoom(dataZoom);

            List<Double> p1 = new ArrayList<>();
            List<Double> p2 = new ArrayList<>();
            List<Double> p3 = new ArrayList<>();
            List<Double> p4 = new ArrayList<>();
            List<Double> p5 = new ArrayList<>();
            List<Double> p6 = new ArrayList<>();
            for (int i = 0; i < dataInfoList.size(); i++) {
                p1.add(dataInfoList.get(i).getDx());
                p2.add(dataInfoList.get(i).getDy());
                p3.add(dataInfoList.get(i).getDz());
                p4.add(dataInfoList.get(i).getX());
                p5.add(dataInfoList.get(i).getY());
                p6.add(dataInfoList.get(i).getZ());
            }

            List<Series> lines = new ArrayList<>();
            Line line1 = new Line();
            Line line2 = new Line();
            Line line3 = new Line();
            Line line4 = new Line();
            Line line5 = new Line();
            Line line6 = new Line();
            line1.setData(p1);
            line2.setData(p2);
            line3.setData(p3);
            line4.setData(p4);
            line5.setData(p5);
            line6.setData(p6);
            line1.smooth(true).name("DX").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
            line2.smooth(true).name("DY").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
            line3.smooth(true).name("DZ").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
            line4.smooth(true).name("X").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
            line5.smooth(true).name("Y").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
            line6.smooth(true).name("Z").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
            lines.add(line1);
            lines.add(line2);
            lines.add(line3);
            lines.add(line4);
            lines.add(line5);
            lines.add(line6);
            option.setSeries(lines);

            return option;
        }

    }

}
