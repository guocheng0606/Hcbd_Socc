package com.android.hcbd.socc.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.adapter.DeviceStateListAdapter;
import com.android.hcbd.socc.entity.DeviceStateListInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceStateListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.listView)
    ListView listView;
    private DeviceStateListAdapter adapter;

    private DeviceStateListInfo deviceStateListInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_state_list);
        ButterKnife.bind(this);

        deviceStateListInfo = (DeviceStateListInfo) getIntent().getSerializableExtra("data");

        adapter = new DeviceStateListAdapter(this,deviceStateListInfo.getDataInfoList());
        listView.setAdapter(adapter);

        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
        }
    }
}
