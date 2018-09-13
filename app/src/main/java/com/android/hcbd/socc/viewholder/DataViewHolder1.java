package com.android.hcbd.socc.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DataInfo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by guocheng on 2017/6/28.
 */

public class DataViewHolder1 extends BaseViewHolder<DataInfo> {
    private TextView tv_time;
    private TextView tv_dx;
    private TextView tv_dy;
    private TextView tv_dz;
    private TextView tv_x;
    private TextView tv_y;
    private TextView tv_z;
    public DataViewHolder1(ViewGroup parent) {
        super(parent, R.layout.item_data_list_layout1);
        tv_time = $(R.id.tv_time);
        tv_dx = $(R.id.tv_dx);
        tv_dy = $(R.id.tv_dy);
        tv_dz = $(R.id.tv_dz);
        tv_x = $(R.id.tv_x);
        tv_y = $(R.id.tv_y);
        tv_z = $(R.id.tv_z);
    }

    @Override
    public void setData(DataInfo data) {
        super.setData(data);
        tv_time.setText(data.getDataTime());
        tv_dx.setText(""+data.getD1());
        tv_dy.setText(""+data.getD2());
        tv_dz.setText(""+data.getD3());
        tv_x.setText(""+data.getD4());
        tv_y.setText(""+data.getD5());
        tv_z.setText(""+data.getD6());
    }
}
