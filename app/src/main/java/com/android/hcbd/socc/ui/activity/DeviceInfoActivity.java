package com.android.hcbd.socc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DeviceInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sn)
    TextView tvSn;
    @BindView(R.id.tv_x)
    TextView tvX;
    @BindView(R.id.tv_y)
    TextView tvY;
    @BindView(R.id.tv_z)
    TextView tvZ;
    @BindView(R.id.tv_upload_code)
    TextView tvUploadCode;
    @BindView(R.id.tv_is_ref)
    TextView tvIsRef;
    @BindView(R.id.tv_solu_time)
    TextView tvSoluTime;
    @BindView(R.id.tv_subordinate_item)
    TextView tvSubordinateItem;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.tv_port)
    TextView tvPort;
    @BindView(R.id.tv_unit)
    TextView tvUnit;

    private DeviceInfo deviceInfo;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        deviceInfo = (DeviceInfo) getIntent().getSerializableExtra("data");
        initData();
        iv_back.setOnClickListener(this);
        iv_edit.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()) {
            case MessageEvent.EVENT_DEVICE_UPDATE:
                finishActivity();
                break;
        }
    }

    private void initData() {
        if (deviceInfo == null)
            return;
        tv_code.setText(formatText(deviceInfo.getCode()));
        tvName.setText(formatText(deviceInfo.getName()));
        tvSn.setText(formatText(deviceInfo.getSnNo()));
        tvX.setText(formatText(String.valueOf(deviceInfo.getX())));
        tvY.setText(formatText(String.valueOf(deviceInfo.getY())));
        tvZ.setText(formatText(String.valueOf(deviceInfo.getZ())));
        tvUploadCode.setText(formatText(deviceInfo.getUpCode()));
        tvIsRef.setText(deviceInfo.getIsRef().equals("1") ? "是" : "否");
        tvSoluTime.setText(formatText(deviceInfo.getSoluTime()));
        tvSubordinateItem.setText(formatText(deviceInfo.getProject().getNames()));
        tvDeviceType.setText(formatText(deviceInfo.getType().getNames()));
        tvPort.setText(formatText(String.valueOf(deviceInfo.getPort())));
        tvUnit.setText(formatText(String.valueOf(deviceInfo.getUnit())));
        tvRemark.setText(formatText(deviceInfo.getRemark()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_edit:
                if (deviceInfo == null)
                    return;
                Intent intent = new Intent(DeviceInfoActivity.this, DeviceEditActivity.class);
                intent.putExtra("data", deviceInfo);
                startActivity(intent);
                break;
        }
    }
}
