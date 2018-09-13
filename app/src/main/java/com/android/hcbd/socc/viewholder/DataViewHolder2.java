package com.android.hcbd.socc.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DataInfo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by guocheng on 2017/6/28.
 */

public class DataViewHolder2 extends BaseViewHolder<DataInfo> {

    private TextView tv_time;
    private TextView tv_value;
    private TextView tv_temp;

    public DataViewHolder2(ViewGroup parent) {
        super(parent, R.layout.item_data_list_layout2);
        tv_time = $(R.id.tv_time);
        tv_value = $(R.id.tv_value);
        tv_temp = $(R.id.tv_temp);
    }

    @Override
    public void setData(DataInfo data) {
        super.setData(data);
        tv_time.setText(data.getDataTime());
        tv_value.setText(""+data.getD1());
        tv_temp.setText(""+data.getD2());
    }
}
