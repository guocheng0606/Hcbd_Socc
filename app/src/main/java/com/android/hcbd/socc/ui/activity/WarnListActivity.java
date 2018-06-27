package com.android.hcbd.socc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.WarnInfo;
import com.android.hcbd.socc.entity.WarnSearchInfo;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.android.hcbd.socc.viewholder.WarnViewHolder;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WarnListActivity extends BaseActivity implements View.OnClickListener,RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    private int currentPage = 1;
    private List<WarnInfo> warnInfoList = new ArrayList<>();
    private RecyclerArrayAdapter<WarnInfo> adapter;
    private WarnSearchInfo warnSearchInfo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initView();
        initHttpData();
        initListener();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_WARN_SEARCH:
                warnSearchInfo = (WarnSearchInfo) event.getObj();
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
                break;
            case MessageEvent.EVENT_WARN_ADD:
            case MessageEvent.EVENT_WARN_UPDATE:
            case MessageEvent.EVENT_WARN_DELETE:
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
                break;
        }
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(0xFFEDEDED, 1, 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<WarnInfo>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new WarnViewHolder(parent);
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
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.warn_list_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("device.name", warnSearchInfo == null ? "" : warnSearchInfo.getDeviceName())
                .params("name", warnSearchInfo == null ? "" : warnSearchInfo.getWarnName())
                .params("beginTime", warnSearchInfo == null ? "" : warnSearchInfo.getBeginTime())
                .params("endTime", warnSearchInfo == null ? "" : warnSearchInfo.getEndTime())
                .params("currentPage",currentPage)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            JSONArray array = new JSONArray(jsonObject.getString("data"));
                            if(array.length() > 0){
                                warnInfoList.clear();
                                Gson gson = new Gson();
                                for(int i=0;i<array.length();i++){
                                    WarnInfo warnInfo = gson.fromJson(array.getString(i),WarnInfo.class);
                                    warnInfoList.add(warnInfo);
                                }
                                if(currentPage == 1)
                                    adapter.clear();
                                adapter.addAll(warnInfoList);
                            }else{
                                if(currentPage == 1){
                                    recyclerView.showEmpty();
                                }else{
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
                        if(currentPage == 1){
                            ToastUtils.showShortToast(MyApplication.getInstance(),"请检查是否连接网络！");
                        }else{
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
        ivSearch.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(WarnListActivity.this,WarnInfoActivity.class);
                intent.putExtra("data",warnInfoList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_search:
                Intent intent = new Intent(this,WarnSearchListActivity.class);
                intent.putExtra("search_info",warnSearchInfo);
                startActivity(intent);
                break;
            case R.id.iv_add:
                startActivity(new Intent(this,WarnEditActivity.class));
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
        System.out.println("加载更多。。。"+currentPage);
        initHttpData();
    }
}
