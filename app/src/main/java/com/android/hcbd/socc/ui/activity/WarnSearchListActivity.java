package com.android.hcbd.socc.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.WarnSearchInfo;
import com.android.hcbd.socc.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WarnSearchListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_device_name)
    EditText etDeviceName;
    @BindView(R.id.iv_device_name)
    ImageView ivDeviceName;
    @BindView(R.id.et_warn_name)
    EditText etWarnName;
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

    private WarnSearchInfo warnSearchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_search_list);
        ButterKnife.bind(this);

        warnSearchInfo = (WarnSearchInfo) getIntent().getSerializableExtra("search_info");
        if(warnSearchInfo != null){
            etDeviceName.setText(warnSearchInfo.getDeviceName());
            etWarnName.setText(warnSearchInfo.getWarnName());
            tvBeginTime.setText(warnSearchInfo.getBeginTime());
            tvEndTime.setText(warnSearchInfo.getEndTime());
        }

        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        ivDeviceName.setOnClickListener(this);
        ivBeginTime.setOnClickListener(this);
        tvBeginTime.setOnClickListener(this);
        ivEndTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_device_name:

                break;
            case R.id.iv_begin_time:
                showDialogYearMonthDay(tvBeginTime);
                break;
            case R.id.tv_begin_time:
                showDialogYearMonthDay(tvBeginTime);
                break;
            case R.id.iv_end_time:
                showDialogYearMonthDay(tvEndTime);
                break;
            case R.id.tv_end_time:
                showDialogYearMonthDay(tvEndTime);
                break;
            case R.id.btn_search :
                if(warnSearchInfo == null)
                    warnSearchInfo = new WarnSearchInfo();
                warnSearchInfo.setDeviceName(etDeviceName.getText().toString());
                warnSearchInfo.setWarnName(etWarnName.getText().toString());
                warnSearchInfo.setBeginTime(tvBeginTime.getText().toString());
                warnSearchInfo.setEndTime(tvEndTime.getText().toString());
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setEventId(MessageEvent.EVENT_WARN_SEARCH);
                messageEvent.setObj(warnSearchInfo);
                EventBus.getDefault().post(messageEvent);
                finishActivity();
                break;
        }

    }
}
