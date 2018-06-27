package com.android.hcbd.socc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.WarnInfo;
import com.android.hcbd.socc.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WarnInfoActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.tv_warn_code)
    TextView tvWarnCode;
    @BindView(R.id.tv_warn_name)
    TextView tvWarnName;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_s1)
    TextView tvS1;
    @BindView(R.id.tv_s2)
    TextView tvS2;
    @BindView(R.id.tv_s3)
    TextView tvS3;

    private WarnInfo warnInfo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        warnInfo = (WarnInfo) getIntent().getSerializableExtra("data");
        initData();

        ivBack.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_WARN_UPDATE:
                finishActivity();
                break;
        }
    }

    private void initData() {
        if(warnInfo == null)
            return;
        tvWarnCode.setText(warnInfo.getCode());
        tvWarnName.setText(warnInfo.getName());
        tvDeviceName.setText(warnInfo.getDevice().getName());
        tvS1.setText(warnInfo.getS1()+warnInfo.getV1());
        tvS2.setText(warnInfo.getS2()+warnInfo.getV2());
        tvS3.setText(warnInfo.getS3()+warnInfo.getV3());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_edit:
                Intent intent = new Intent(this,WarnEditActivity.class);
                intent.putExtra("warnInfo",warnInfo);
                startActivity(intent);
                break;
        }
    }
}
