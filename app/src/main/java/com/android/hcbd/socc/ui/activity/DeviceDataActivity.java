package com.android.hcbd.socc.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DataInfo;
import com.android.hcbd.socc.entity.DataSearchInfo;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.android.hcbd.socc.viewholder.DataViewHolder1;
import com.android.hcbd.socc.viewholder.DataViewHolder2;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
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

public class DeviceDataActivity extends BaseActivity implements View.OnClickListener, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_export)
    ImageView ivExport;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @BindView(R.id.ll_top1)
    LinearLayout llTop1;
    @BindView(R.id.ll_top2)
    LinearLayout llTop2;


    private int currentPage = 1;
    private DataSearchInfo dataSearchInfo;
    private List<DataInfo> dataInfoList = new ArrayList<>();
    private List<DeviceInfo> deviceList = new ArrayList<>();
    private RecyclerArrayAdapter<DataInfo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_data);
        ButterKnife.bind(this);


        dataSearchInfo = (DataSearchInfo) getIntent().getSerializableExtra("search_info");
        if (dataSearchInfo.isGnss()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            llTop1.setVisibility(View.VISIBLE);
            llTop2.setVisibility(View.GONE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            llTop1.setVisibility(View.GONE);
            llTop2.setVisibility(View.VISIBLE);
        }
        initView();
        initHttpData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(0xFFEDEDED, 1, 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<DataInfo>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if (dataSearchInfo.isGnss())
                    return new DataViewHolder1(parent);
                else
                    return new DataViewHolder2(parent);
            }
        });
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(0xFF5CB9F4);
        swipeRefreshLayout.setOnRefreshListener(this);
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

        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl() + HttpUrlUtils.device_data_url)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            LogUtils.LogShow(jsonObject.getString("data"));
                            Gson gson = new Gson();
                            deviceList.clear();
                            JSONArray a = new JSONArray(jsonObject.getString("deviceList"));
                            for (int i = 0; i < a.length(); i++) {
                                DeviceInfo deviceInfo = gson.fromJson(a.getString(i), DeviceInfo.class);
                                deviceList.add(deviceInfo);
                            }

                            JSONArray array = new JSONArray(jsonObject.getString("data"));
                            if (array.length() > 0) {
                                dataInfoList.clear();
                                for (int i = 0; i < array.length(); i++) {
                                    DataInfo dataInfo = gson.fromJson(array.getString(i), DataInfo.class);
                                    dataInfoList.add(dataInfo);
                                }
                                if (currentPage == 1)
                                    adapter.clear();
                                adapter.addAll(dataInfoList);
                            } else {
                                if (currentPage == 1) {
                                    recyclerView.showEmpty();
                                } else {
                                    adapter.stopMore();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (currentPage == 1) {
                            ToastUtils.showShortToast(MyApplication.getInstance(), "请检查是否连接网络！");
                        } else {
                            adapter.pauseMore();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivExport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
        }
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        initHttpData();
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        initHttpData();
    }
}
