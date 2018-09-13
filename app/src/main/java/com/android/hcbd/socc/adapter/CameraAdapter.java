package com.android.hcbd.socc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DeviceInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2018/9/12.
 */

public class CameraAdapter extends BaseAdapter {

    private List<DeviceInfo> list;
    private Context mContext;
    public CameraAdapter(Context mContext,List<DeviceInfo> list){
        this.mContext = mContext;
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

    public List<DeviceInfo> getAllData(){
        return list;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if ( view == null ){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_camera_list_layout,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.camera_name_tv.setText(list.get(i).getName());
        holder.item_play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != itemClickListener) {
                    itemClickListener.onPlayClick(v,i);
                }
            }
        });
        holder.tab_remoteplayback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != itemClickListener) {
                    itemClickListener.onRemotePlayBackClick(v,i);
                }
            }
        });
        return view;
    }

    class ViewHolder {

        @BindView(R.id.item_play_btn)
        ImageButton item_play_btn;
        @BindView(R.id.camera_name_tv)
        TextView camera_name_tv;
        @BindView(R.id.tab_remoteplayback_btn)
        ImageButton tab_remoteplayback_btn;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public interface onViewItemClickListener {
        public void onPlayClick(View v,int position);
        public void onRemotePlayBackClick(View view, int position);
    }
    private onViewItemClickListener itemClickListener = null;

    public void setOnItemClickListener(onViewItemClickListener listener) {
        this.itemClickListener = listener;
    }


}
