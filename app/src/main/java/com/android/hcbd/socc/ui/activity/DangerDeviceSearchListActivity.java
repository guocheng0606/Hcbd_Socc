package com.android.hcbd.socc.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DangerDeviceSearchInfo;
import com.android.hcbd.socc.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DangerDeviceSearchListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_device_name)
    EditText etDeviceName;
    @BindView(R.id.iv_device_name)
    ImageView ivDeviceName;
    @BindView(R.id.tv_real_state)
    TextView tvRealState;
    @BindView(R.id.iv_real_state)
    ImageView ivRealState;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.iv_begin_time)
    ImageView ivBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.iv_end_time)
    ImageView ivEndTime;
    @BindView(R.id.btn_search)
    Button btnSearch;

    private DangerDeviceSearchInfo dangerDeviceSearchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_device_search_list);
        ButterKnife.bind(this);

        dangerDeviceSearchInfo = (DangerDeviceSearchInfo) getIntent().getSerializableExtra("search_info");
        if(dangerDeviceSearchInfo != null){
            etDeviceName.setText(dangerDeviceSearchInfo.getDeviceName());
            if(dangerDeviceSearchInfo.getRealState().equals("1")){
                tvRealState.setText("已处理");
            }else if(dangerDeviceSearchInfo.getRealState().equals("2")){
                tvRealState.setText("未处理");
            }else{
                tvRealState.setText("全部状态");
            }
            tvBeginTime.setText(dangerDeviceSearchInfo.getBeginTime());
            tvEndTime.setText(dangerDeviceSearchInfo.getEndTime());
        }

        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivDeviceName.setOnClickListener(this);
        ivRealState.setOnClickListener(this);
        ivBeginTime.setOnClickListener(this);
        ivEndTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_device_name:

                break;
            case R.id.iv_real_state:
                String[] names = {"已处理","未处理","全部状态"};
                showDialog(names,tvRealState);
                break;
            case R.id.iv_begin_time:
                showDialogYearMonthDay(tvBeginTime);
                break;
            case R.id.iv_end_time:
                showDialogYearMonthDay(tvEndTime);
                break;
            case R.id.btn_search:
                if(dangerDeviceSearchInfo == null)
                    dangerDeviceSearchInfo = new DangerDeviceSearchInfo();
                dangerDeviceSearchInfo.setDeviceName(etDeviceName.getText().toString());
                if(tvRealState.getText().toString().equals("已处理")){
                    dangerDeviceSearchInfo.setRealState("1");
                }else if(tvRealState.getText().toString().equals("未处理")){
                    dangerDeviceSearchInfo.setRealState("2");
                }else{
                    dangerDeviceSearchInfo.setRealState("");
                }
                dangerDeviceSearchInfo.setBeginTime(tvBeginTime.getText().toString());
                dangerDeviceSearchInfo.setEndTime(tvEndTime.getText().toString());
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setEventId(MessageEvent.EVENT_DANGER_DEVICE_SEARCH);
                messageEvent.setObj(dangerDeviceSearchInfo);
                EventBus.getDefault().post(messageEvent);
                finishActivity();
                break;
        }

    }

}
