package com.android.hcbd.socc.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DataSearchInfo;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataSearchActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_device_name)
    EditText etDeviceName;
    @BindView(R.id.iv_device_name)
    ImageView ivDeviceName;
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
    private DataSearchInfo dataSearchInfo;
    private List<DeviceInfo> deviceList = new ArrayList<>();
    private String[] devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_search);
        ButterKnife.bind(this);

        dataSearchInfo = (DataSearchInfo) getIntent().getSerializableExtra("search_info");
        deviceList = (List<DeviceInfo>) getIntent().getSerializableExtra("deviceList");
        devices = new String[deviceList.size()];
        for(int i=0;i<deviceList.size();i++){
            devices[i] = deviceList.get(i).getName();
        }

        if(dataSearchInfo != null){
            etDeviceName.setText(dataSearchInfo.getDeviceName());
            tvBeginTime.setText(dataSearchInfo.getBeginTime());
            tvEndTime.setText(dataSearchInfo.getEndTime());
        }

        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivDeviceName.setOnClickListener(this);
        ivBeginTime.setOnClickListener(this);
        tvBeginTime.setOnClickListener(this);
        ivEndTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_device_name:
                showDialog(devices,etDeviceName);
                break;
            case R.id.iv_begin_time:
                showAllTimePickerDialog(tvBeginTime);
                break;
            case R.id.tv_begin_time:
                showAllTimePickerDialog(tvBeginTime);
                break;
            case R.id.iv_end_time:
                showAllTimePickerDialog(tvEndTime);
                break;
            case R.id.tv_end_time:
                showAllTimePickerDialog(tvEndTime);
                break;
            case R.id.btn_search:
                if(dataSearchInfo == null)
                    dataSearchInfo = new DataSearchInfo();
                dataSearchInfo.setDeviceName(etDeviceName.getText().toString());
                dataSearchInfo.setBeginTime(tvBeginTime.getText().toString());
                dataSearchInfo.setEndTime(tvEndTime.getText().toString());
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setEventId(MessageEvent.EVENT_DATA_SEARCH);
                messageEvent.setObj(dataSearchInfo);
                EventBus.getDefault().post(messageEvent);
                finishActivity();
                break;
        }

    }
}
