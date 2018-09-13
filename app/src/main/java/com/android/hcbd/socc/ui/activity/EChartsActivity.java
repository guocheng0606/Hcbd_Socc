package com.android.hcbd.socc.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DataInfo;
import com.android.hcbd.socc.entity.DataSearchInfo;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ProgressDialogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Legend;
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
import com.guocheng.echartlibrary.EChartWebView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EChartsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.echartWebView)
    EChartWebView echartWebView;

    private DataSearchInfo dataSearchInfo;
    private int currentPage = 1;
    private List<DataInfo> dataInfoList = new ArrayList<>();
    private List<DeviceInfo> deviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echarts);
        ButterKnife.bind(this);

        ProgressDialogUtils.showLoading(this);
        dataSearchInfo = (DataSearchInfo) getIntent().getSerializableExtra("search_info");
        initHttpData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initHttpData() {
        HttpParams params = new HttpParams();
        params.put("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode());
        params.put("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode());
        params.put("token", MyApplication.getInstance().getLoginInfo().getToken());
        if (!TextUtils.isEmpty(dataSearchInfo.getDeviceName())) {
            params.put("device.name", dataSearchInfo.getDeviceName());
        }
        if (!TextUtils.isEmpty(dataSearchInfo.getBeginTime())) {
            params.put("beginTime", dataSearchInfo.getBeginTime());
        }
        if (!TextUtils.isEmpty(dataSearchInfo.getEndTime())) {
            params.put("endTime", dataSearchInfo.getEndTime());
        }
        params.put("currentPage", currentPage);

        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.device_data_url)
                .params(params)
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
                                //设置数据源
                                echartWebView.setDataSource(new EChartWebView.DataSource() {
                                    @Override
                                    public GsonOption markLineChartOptions() {
                                        GsonOption option = null;
                                        if (dataSearchInfo.isGnss()){
                                            option = getLineChartOption1();
                                        } else {
                                            option = getLineChartOption2();
                                        }
                                        ProgressDialogUtils.dismissLoading();
                                        return option;
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

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
        }
    }

    public GsonOption getLineChartOption1() {
        GsonOption option = new GsonOption();

        Legend legend = new Legend();
        legend.data("DX","DY","DZ","LEN","X","Y","Z").selected("X",false).selected("Y",false).selected("Z",false);
        option.legend(legend);

        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore);

        option.calculable(true);
        option.tooltip().trigger(Trigger.axis).formatter("{b}<br/>DX：{c}<br/>DY：{c1}<br/>DZ：{c2}<br/>LEN：{c3}<br/>X：{c4}<br/>Y：{c5}<br/>Z：{c6}");

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
        dataZoom.setEnd(100);
        option.dataZoom(dataZoom);

        List<Double> p0 = new ArrayList<>();
        List<Double> p1 = new ArrayList<>();
        List<Double> p2 = new ArrayList<>();
        List<Double> p3 = new ArrayList<>();
        List<Double> p4 = new ArrayList<>();
        List<Double> p5 = new ArrayList<>();
        List<Double> p6 = new ArrayList<>();
        for (int i = 0; i < dataInfoList.size(); i++) {
            p1.add(Double.valueOf(dataInfoList.get(i).getD1()));
            p2.add(Double.valueOf(dataInfoList.get(i).getD2()));
            p3.add(Double.valueOf(dataInfoList.get(i).getD3()));
            p4.add(Double.valueOf(dataInfoList.get(i).getD4()));
            p5.add(Double.valueOf(dataInfoList.get(i).getD5()));
            p6.add(Double.valueOf(dataInfoList.get(i).getD6()));
            p0.add(Double.valueOf(dataInfoList.get(i).getD0()));
        }

        List<Series> lines = new ArrayList<>();

        Line line0 = new Line();
        Line line1 = new Line();
        Line line2 = new Line();
        Line line3 = new Line();
        Line line4 = new Line();
        Line line5 = new Line();
        Line line6 = new Line();
        line0.setData(p0);
        line1.setData(p1);
        line2.setData(p2);
        line3.setData(p3);
        line4.setData(p4);
        line5.setData(p5);
        line6.setData(p6);
        line1.smooth(false).name("DX").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
        line2.smooth(false).name("DY").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
        line3.smooth(false).name("DZ").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
        line0.smooth(false).name("LEN").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
        line4.smooth(false).name("X").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
        line5.smooth(false).name("Y").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
        line6.smooth(false).name("Z").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line0);
        lines.add(line4);
        lines.add(line5);
        lines.add(line6);
        option.setSeries(lines);
        return option;
    }

    public GsonOption getLineChartOption2() {
        GsonOption option = new GsonOption();

        Legend legend = new Legend();
        legend.data("数值","温度");
        option.legend(legend);

        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore);

        option.calculable(true);
        option.tooltip().trigger(Trigger.axis).formatter("{b}<br/>数值：{c}<br/>温度：{c1}");

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
        dataZoom.setEnd(100);
        option.dataZoom(dataZoom);

        List<Double> p1 = new ArrayList<>();
        List<Double> p2 = new ArrayList<>();
        for (int i = 0; i < dataInfoList.size(); i++) {
            p1.add(Double.valueOf(dataInfoList.get(i).getD1()));
            p2.add(Double.valueOf(dataInfoList.get(i).getD2()));
        }

        List<Series> lines = new ArrayList<>();

        Line line1 = new Line();
        Line line2 = new Line();
        line1.setData(p1);
        line2.setData(p2);
        line1.smooth(false).name("数值").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
        line2.smooth(false).name("温度").itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0)");
        lines.add(line1);
        lines.add(line2);
        option.setSeries(lines);
        return option;
    }

    /**
     * 注入到JS里的对象接口
     */
    /*class WebAppInterface {
        Context mContext;

        public WebAppInterface(Context c) {
            mContext = c;
        }

        *//**
         * 获取
         *
         * @return
         *//*
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

    }*/

}
