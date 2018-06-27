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
 * Created by guocheng on 2017/4/24.
 */

public class DangerDeviceListAdapter extends BaseAdapter{
    private Context context;
    private List<DeviceStateListInfo.DataInfo> list;
    public DangerDeviceListAdapter(Context context,List<DeviceStateListInfo.DataInfo> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list == null ? null : list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_danger_device_list_layout,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
            AutoUtils.autoSize(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_order.setText(""+(i+1));
        holder.tv_name.setText(list.get(i).getName());
        holder.tv_type.setText(list.get(i).getType());
        holder.tv_state.setText(list.get(i).getState());
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_order)
        TextView tv_order;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.tv_state)
        TextView tv_state;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
