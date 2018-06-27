package com.android.hcbd.socc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DeviceStateListInfo;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/4/20.
 */

public class DeviceStateListAdapter extends BaseAdapter {
    private Context context;
    private List<DeviceStateListInfo.DataInfo> dataInfoList;
    public DeviceStateListAdapter(Context context,List<DeviceStateListInfo.DataInfo> dataInfoList){
        this.context = context;
        this.dataInfoList = dataInfoList;
    }

    @Override
    public int getCount() {
        return  dataInfoList == null ? 0 : dataInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataInfoList == null ? null : dataInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_device_state_list_layout,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
            AutoUtils.autoSize(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_order.setText(""+(i+1));
        holder.tv_name.setText(dataInfoList.get(i).getName());
        if(dataInfoList.get(i).getState().equals("连接")){
            holder.tv_name.setTextColor(0xFF12EF12);
        }else if(dataInfoList.get(i).getState().equals("断开")){
            holder.tv_name.setTextColor(0xFFFFBC00);
        }else if(dataInfoList.get(i).getState().equals("危险")){
            holder.tv_name.setTextColor(0xFFFF0000);
        }else{
            holder.tv_name.setTextColor(0xFF000000);
        }
        if(dataInfoList.get(i).getType().equals("GNSS")){
            holder.tv_type.setText(dataInfoList.get(i).getType()+"（非GPS）");
        }else{
            holder.tv_type.setText(dataInfoList.get(i).getType());
        }
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_order)
        TextView tv_order;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_type)
        TextView tv_type;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
