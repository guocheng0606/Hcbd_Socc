package com.android.hcbd.socc.ui.activity;

public class PreviewLineChartActivity extends BaseActivity /*implements View.OnClickListener */{

    /*@BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.lcv_pre_main)
    LineChartView mLineChartView;
    @BindView(R.id.plcv_pre_main)
    PreviewLineChartView mPreLineChartView;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;

    private DataSearchInfo dataSearchInfo;
    private LineChartData mChartData;                   //展示区域的数据
    private LineChartData mPreChartData;                //预览区域的数据
    private int currentPage = 1;
    private List<DataInfo> dataInfoList = new ArrayList<>();
    private List<DeviceInfo> deviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_line_chart);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        ll_main.setVisibility(View.GONE);
        //ToastUtils.showLongToast(MyApplication.getContext(),"请选择搜索条件！");
        ProgressDialogUtils.showLoading(this);
        initHttpData();
        initListener();
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

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        //预览区滑动监听
        mPreLineChartView.setViewportChangeListener(new ViewportChangeListener() {
            @Override
            public void onViewportChanged(Viewport viewport) {
                // 这里切记不要使用动画，因为预览图是不需要动画的
                mLineChartView.setCurrentViewport(viewport);         //直接设置当前窗口图表
            }
        });
    }

    private void initHttpData() {
        OkGo.post(HttpUrlUtils.device_data_url)
                .params("sessionOper.code", MyApplication.app.getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.app.getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.app.getLoginInfo().getToken())
                .params("device.name", dataSearchInfo == null ? "" : dataSearchInfo.getDeviceName())
                .params("beginTime", dataSearchInfo == null ? "" : dataSearchInfo.getBeginTime())
                .params("endTime", dataSearchInfo == null ? "" : dataSearchInfo.getEndTime())
                .params("currentPage", currentPage)
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtils.LogShow(s);
                        if (1 == currentPage)
                            dataInfoList.clear();

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
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
                                initHttpData();
                            } else {
                                ll_main.setVisibility(View.VISIBLE);
                                setAllDatas();
                                mLineChartView.setLineChartData(mChartData);         //设置选中区数据
                                mPreLineChartView.setLineChartData(mPreChartData);   //设置预览区数据
                                mLineChartView.setZoomEnabled(false);                //禁用缩放
                                mLineChartView.setScrollEnabled(false);              //禁用滚动
                                previewX();
                                ProgressDialogUtils.dismissLoading();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ToastUtils.showShortToast(MyApplication.getContext(), "搜索出错！");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(MyApplication.getContext(), "请检查是否连接网络！");
                    }

                    @Override
                    public void onAfter(String s, Exception e) {
                        super.onAfter(s, e);
                    }
                });
    }

    private void setAllDatas() {
        List<PointValue> mPointValues1 = new ArrayList<>();
        List<PointValue> mPointValues2 = new ArrayList<>();
        List<PointValue> mPointValues3 = new ArrayList<>();
        List<PointValue> mPointValues4 = new ArrayList<>();
        List<PointValue> mPointValues5 = new ArrayList<>();
        List<PointValue> mPointValues6 = new ArrayList<>();
        List<AxisValue> mAxisXValues = new ArrayList<>();

        for (int i = 0; i < dataInfoList.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(dataInfoList.get(i).getDataTime()));
        }

        for (int i = 0; i < dataInfoList.size(); i++) {
            mPointValues1.add(new PointValue(i, (float) dataInfoList.get(i).getDx()));
            mPointValues2.add(new PointValue(i, (float) dataInfoList.get(i).getDy()));
            mPointValues3.add(new PointValue(i, (float) dataInfoList.get(i).getDz()));
            mPointValues4.add(new PointValue(i, (float) dataInfoList.get(i).getX()));
            mPointValues5.add(new PointValue(i, (float) dataInfoList.get(i).getY()));
            mPointValues6.add(new PointValue(i, (float) dataInfoList.get(i).getZ()));
        }

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(mPointValues1).setColor(Color.parseColor("#ff0000")).setShape(ValueShape.CIRCLE).setHasPoints(true).setPointRadius(2).setStrokeWidth(3));
        lines.add(new Line(mPointValues2).setColor(Color.parseColor("#ff9900")).setShape(ValueShape.CIRCLE).setHasPoints(true).setPointRadius(2).setStrokeWidth(3));
        lines.add(new Line(mPointValues3).setColor(Color.parseColor("#ffff00")).setShape(ValueShape.CIRCLE).setHasPoints(true).setPointRadius(2).setStrokeWidth(3));
        lines.add(new Line(mPointValues4).setColor(Color.parseColor("#00ff00")).setShape(ValueShape.CIRCLE).setHasPoints(true).setPointRadius(2).setStrokeWidth(3));
        lines.add(new Line(mPointValues5).setColor(Color.parseColor("#00ffff")).setShape(ValueShape.CIRCLE).setHasPoints(true).setPointRadius(2).setStrokeWidth(3));
        lines.add(new Line(mPointValues6).setColor(Color.parseColor("#4a86e8")).setShape(ValueShape.CIRCLE).setHasPoints(true).setPointRadius(2).setStrokeWidth(3));

        mChartData = new LineChartData(lines);
        Axis axisX = new Axis().setHasLines(true);
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        axisX.setMaxLabelChars(10);
        axisX.setTextSize(10);//设置字体大小
        //axisX.getLineColor()
        mChartData.setAxisXBottom(axisX);

        Axis axisY = new Axis().setHasLines(true);

        List<AxisValue> values = new ArrayList<>();
        for (int i = -200; i < 200; i += 10) {
            AxisValue value = new AxisValue(i);
            String label = "" + i;
            value.setLabel(label);
            values.add(value);
        }
        axisY.setValues(values);
        axisY.setMaxLabelChars(4);
        axisY.setTextSize(10);//设置字体大小
        mChartData.setAxisYLeft(axisY);  //Y轴设置在左边


        //将相同的数据也设置到预览区 并更改颜色
        mPreChartData = new LineChartData(mChartData);
        for(int i=0;i<mPreChartData.getLines().size();i++){
            mPreChartData.getLines().get(i).setColor(ChartUtils.DEFAULT_DARKEN_COLOR).setHasPoints(false);
        }
    }

    *//**
     * 根据X方向预览
     *//*
    private void previewX() {
        Viewport tempViewport = new Viewport(mLineChartView.getMaximumViewport());
        float dx = tempViewport.width() / 2;            //原区域1/4
        tempViewport.inset(dx, 0);                      //设置临时窗口大小
        //根据是否有动画 设置数据
        mPreLineChartView.setCurrentViewportWithAnimation(tempViewport);
        //只能水平缩放
        mPreLineChartView.setZoomType(ZoomType.HORIZONTAL);
    }

    @Override
    public void onClick(View view) {
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
    }*/

}
