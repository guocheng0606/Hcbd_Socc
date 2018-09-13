package com.android.hcbd.socc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DataInfo;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/4/27.
 */

public class DataAdapter extends BaseAdapter{
    private Context context;
    private List<DataInfo> list;
    public DataAdapter(Context context,List<DataInfo> list){
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
            view = LayoutInflater.from(context).inflate(R.layout.item_data_list_layout1,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
            AutoUtils.autoSize(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_time.setText(list.get(i).getDataTime());
        holder.tv_dx.setText(""+list.get(i).getDx());
        holder.tv_dy.setText(""+list.get(i).getDy());
        holder.tv_x.setText(""+list.get(i).getX());
        holder.tv_y.setText(""+list.get(i).getY());
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_dx)
        TextView tv_dx;
        @BindView(R.id.tv_dy)
        TextView tv_dy;
        @BindView(R.id.tv_x)
        TextView tv_x;
        @BindView(R.id.tv_y)
        TextView tv_y;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
