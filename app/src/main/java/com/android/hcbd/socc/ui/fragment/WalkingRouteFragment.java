package com.android.hcbd.socc.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.ui.activity.RouteShowActivity;
import com.android.hcbd.socc.viewholder.WalkingRouteViewHolder;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 步行线路
 */
public class WalkingRouteFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    Unbinder unbinder;

    private RoutePlanSearch mSearch;
    private RecyclerArrayAdapter<WalkingRouteLine> adapter;

    private LatLng mParam1;
    private LatLng mParam2;

    public WalkingRouteFragment() {
    }

    public static WalkingRouteFragment newInstance(LatLng param1, LatLng param2) {
        WalkingRouteFragment fragment = new WalkingRouteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getParcelable(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walking_route, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(routeListener);
        PlanNode stMassNode = PlanNode.withLocation(mParam1);
        PlanNode enMassNode = PlanNode.withLocation(mParam2);
        mSearch.walkingSearch(new WalkingRoutePlanOption().from(stMassNode).to(enMassNode));
        initListener();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()) {
            case MessageEvent.EVENT_ROUTE_CHANGE_RESULT:
                recyclerView.showProgress();
                LatLng temp = mParam1;
                mParam1 = mParam2;
                mParam2 = temp;
                PlanNode stMassNode = PlanNode.withLocation(mParam1);
                PlanNode enMassNode = PlanNode.withLocation(mParam2);
                mSearch.walkingSearch(new WalkingRoutePlanOption().from(stMassNode).to(enMassNode));
                break;
        }
    }

    private void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), RouteShowActivity.class);
                intent.putExtra("type",3);
                intent.putExtra("data",adapter.getAllData().get(position));
                intent.putExtra("id",""+(position+1));
                startActivity(intent);
            }
        });
    }

    OnGetRoutePlanResultListener routeListener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                adapter.clear();
                //抱歉，未找到结果
                return;
            }
            if(walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
                if(walkingRouteResult.getRouteLines().size() > 0){
                    adapter.clear();
                    adapter.addAll(walkingRouteResult.getRouteLines());
                }else{
                    adapter.clear();
                }
            }

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerDecoration itemDecoration = new DividerDecoration(0xFFE6E6E6, 2, 0, 0);
        itemDecoration.setDrawLastItem(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<WalkingRouteLine>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new WalkingRouteViewHolder(parent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
        EventBus.getDefault().unregister(this);
    }

}
