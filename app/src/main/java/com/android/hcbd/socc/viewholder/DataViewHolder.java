package com.android.hcbd.socc.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DataInfo;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by guocheng on 2017/6/28.
 */

public class DataViewHolder extends BaseViewHolder<DataInfo> {
    private TextView tv_time;
    private TextView tv_dx;
    private TextView tv_dy;
    private TextView tv_x;
    private TextView tv_y;
    public DataViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_data_list_layout);
        tv_time = $(R.id.tv_time);
        tv_dx = $(R.id.tv_dx);
        tv_dy = $(R.id.tv_dy);
        tv_x = $(R.id.tv_x);
        tv_y = $(R.id.tv_y);
    }

    @Override
    public void setData(DataInfo data) {
        super.setData(data);
        tv_time.setText(data.getDataTime());
        tv_dx.setText(""+data.getDx());
        tv_dy.setText(""+data.getDy());
        tv_x.setText(""+data.getX());
        tv_y.setText(""+data.getY());
    }
}
