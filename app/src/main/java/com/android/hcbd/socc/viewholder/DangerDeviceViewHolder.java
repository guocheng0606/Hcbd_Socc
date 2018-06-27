package com.android.hcbd.socc.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.WarnDataInfo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by 14525 on 2017/6/28.
 */

public class DangerDeviceViewHolder extends BaseViewHolder<WarnDataInfo> {
    private TextView tv_order;
    private TextView tv_name;
    private TextView tv_type;
    private TextView tv_state;
    public DangerDeviceViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_danger_device_list_layout);
        tv_order = $(R.id.tv_order);
        tv_name = $(R.id.tv_name);
        tv_type = $(R.id.tv_type);
        tv_state = $(R.id.tv_state);
    }

    @Override
    public void setData(WarnDataInfo data) {
        super.setData(data);
        tv_order.setText(""+(getDataPosition()+1));
        tv_name.setText(data.getWarn().getDevice().getName());
        tv_type.setText(data.getWarn().getName());
        tv_state.setText(data.getState().equals("1") ? "已处理" : "未处理");
    }
}
