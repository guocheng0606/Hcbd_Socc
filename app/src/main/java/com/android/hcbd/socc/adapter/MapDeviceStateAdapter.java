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
 * Created by guocheng on 2017/4/25.
 */

public class MapDeviceStateAdapter extends BaseAdapter {
    private Context context;
    private List<DeviceStateListInfo.DataInfo> list;
    public MapDeviceStateAdapter(Context context,List<DeviceStateListInfo.DataInfo> list){
        this.context = context;
        this.list = list;
    }

    public List<DeviceStateListInfo.DataInfo> getAllData(){
        return list;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_map_device_state_layout,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
            AutoUtils.autoSize(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_order.setText(""+(i+1));
        holder.tv_Name.setText(list.get(i).getName());
        if(list.get(i).getType().equals("GNSS")){
            holder.tv_type.setText(list.get(i).getType()+"（非GPS）");
        }else{
            holder.tv_type.setText(list.get(i).getType());
        }
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_order)
        TextView tv_order;
        @BindView(R.id.tv_name)
        TextView tv_Name;
        @BindView(R.id.tv_type)
        TextView tv_type;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }

    }

}
