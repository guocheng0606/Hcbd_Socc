package com.android.hcbd.socc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.WarnDataInfo;
import com.android.hcbd.socc.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DangerDeviceInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_danger_name)
    TextView tvDangerName;
    @BindView(R.id.tv_v1)
    TextView tvV1;
    @BindView(R.id.tv_s1)
    TextView tvS1;
    @BindView(R.id.tv_v2)
    TextView tvV2;
    @BindView(R.id.tv_s2)
    TextView tvS2;
    @BindView(R.id.tv_v3)
    TextView tvV3;
    @BindView(R.id.tv_s3)
    TextView tvS3;
    @BindView(R.id.tv_real_result)
    TextView tvRealResult;
    @BindView(R.id.tv_deal_state)
    TextView tvDealState;
    @BindView(R.id.tv_alarm_time)
    TextView tvAlarmTime;
    @BindView(R.id.tv_deal_time)
    TextView tvDealTime;

    private WarnDataInfo warnDataInfo;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_device_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        warnDataInfo = (WarnDataInfo) getIntent().getSerializableExtra("data");
        initData();

        ivBack.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_DANGER_DEVICE_REAL:
                finishActivity();
                break;
        }
    }

    private void initData() {
        if(warnDataInfo == null)
            return;
        tvDeviceName.setText(warnDataInfo.getWarn().getDevice().getName());
        tvDangerName.setText(warnDataInfo.getWarn().getName());
        tvV1.setText(String.valueOf(warnDataInfo.getV1()));
        tvS1.setText(warnDataInfo.getWarn().getS1()+warnDataInfo.getWarn().getV1());
        tvV2.setText(String.valueOf(warnDataInfo.getV2()));
        tvS2.setText(warnDataInfo.getWarn().getS2()+warnDataInfo.getWarn().getV2());
        tvV3.setText(String.valueOf(warnDataInfo.getV3()));
        tvS3.setText(warnDataInfo.getWarn().getS3()+warnDataInfo.getWarn().getV3());
        tvRealResult.setText(warnDataInfo.getRemark());
        tvDealState.setText(warnDataInfo.getState().equals("1")?"已处理":"未处理");
        tvAlarmTime.setText(warnDataInfo.getCreateTime().replace("T"," "));
        tvDealTime.setText(warnDataInfo.getDealTime().replace("T"," "));
        if(warnDataInfo.getState().equals("1"))
            ivEdit.setVisibility(View.GONE);
        else
            ivEdit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_edit:
                Intent intent = new Intent(this,DangerDeviceDealActivity.class);
                intent.putExtra("data",warnDataInfo);
                startActivity(intent);
                break;
        }
    }
}
