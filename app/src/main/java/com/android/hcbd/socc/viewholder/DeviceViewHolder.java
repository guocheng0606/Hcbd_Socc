package com.android.hcbd.socc.viewholder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ProgressDialogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 14525 on 2017/6/28.
 */

public class DeviceViewHolder extends BaseViewHolder<DeviceInfo> {
    private TextView tv_order;
    private TextView tv_name;
    private TextView tv_type;
    private ImageView iv_delete;
    public DeviceViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_device_manager_layout);
        tv_order = $(R.id.tv_order);
        tv_name = $(R.id.tv_name);
        tv_type = $(R.id.tv_type);
        iv_delete = $(R.id.iv_delete);
    }

    @Override
    public void setData(final DeviceInfo data) {
        super.setData(data);
        tv_order.setText(""+(getDataPosition()+1));
        tv_name.setText(data.getName());
        if(data.getType().getName().equals("GNSS")){
            tv_type.setText(data.getType().getName()+"（非GPS）");
        }else{
            tv_type.setText(data.getType().getName());
        }
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("删除提示");
                builder.setMessage("您确认删除该信息吗？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.device_delete_url)
                                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                                .params("id", data.getId())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onStart(Request<String, ? extends Request> request) {
                                        super.onStart(request);
                                        ProgressDialogUtils.showLoading(getContext());
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
                                                messageEvent.setEventId(MessageEvent.EVENT_DEVICE_DELETE);
                                                EventBus.getDefault().post(messageEvent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            try {
                                                if(TextUtils.isEmpty(jsonObject.getString("error")))
                                                    ToastUtils.showShortToast(MyApplication.getInstance(),jsonObject.getString("error"));
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
    }
}
