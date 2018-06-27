package com.android.hcbd.socc.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.WarnInfo;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ProgressDialogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guocheng on 2017/4/24.
 */

public class WarnAdapter extends BaseAdapter{
    private Context context;
    private List<WarnInfo> list;

    public WarnAdapter(Context context,List<WarnInfo> list){
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_warn_layout,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
            AutoUtils.autoSize(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_order.setText(""+(i+1));
        holder.tv_warn_name.setText(list.get(i).getName());
        holder.tv_device_name.setText(list.get(i).getDevice().getName());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除提示");
                builder.setMessage("您确认删除该信息吗！");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        dialogInterface.dismiss();
                        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.warn_delete_url)
                                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                                .params("id", list.get(i).getId())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onStart(Request<String, ? extends Request> request) {
                                        super.onStart(request);
                                        ProgressDialogUtils.showLoading(context);
                                    }

                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        String result = response.body();
                                        LogUtils.LogShow(result);
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(result);
                                            if(!TextUtils.isEmpty(jsonObject.getString("data"))){
                                                ToastUtils.showShortToast(MyApplication.getInstance(),jsonObject.getString("data"));
                                                MessageEvent messageEvent = new MessageEvent();
                                                messageEvent.setEventId(MessageEvent.EVENT_WARN_DELETE);
                                                EventBus.getDefault().post(messageEvent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            try {
                                                if(!TextUtils.isEmpty(jsonObject.getString("error"))){
                                                    ToastUtils.showShortToast(MyApplication.getInstance(),jsonObject.getString("error"));
                                                }
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        ToastUtils.showShortToast(MyApplication.getInstance(),"请检查网络是否连接！");
                                    }

                                    @Override
                                    public void onFinish() {
                                        super.onFinish();
                                        ProgressDialogUtils.dismissLoading();
                                    }
                                });

                    }
                });
                builder.create().show();
            }
        });
        return view;
    }

    class ViewHolder{
        @BindView(R.id.tv_order)
        TextView tv_order;
        @BindView(R.id.tv_device_name)
        TextView tv_device_name;
        @BindView(R.id.tv_warn_name)
        TextView tv_warn_name;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
