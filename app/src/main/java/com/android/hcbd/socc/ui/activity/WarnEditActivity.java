package com.android.hcbd.socc.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.entity.WarnInfo;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ProgressDialogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.android.hcbd.socc.widget.CustomSpinner;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WarnEditActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.et_warn_name)
    EditText etWarnName;
    @BindView(R.id.sp_device_name)
    CustomSpinner sp_device_name;
    @BindView(R.id.sp_s1)
    CustomSpinner spS1;
    @BindView(R.id.et_s1)
    EditText etS1;
    @BindView(R.id.sp_s2)
    CustomSpinner spS2;
    @BindView(R.id.et_s2)
    EditText etS2;
    @BindView(R.id.sp_s3)
    CustomSpinner spS3;
    @BindView(R.id.et_s3)
    EditText etS3;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;

    private WarnInfo warnInfo;
    private List<DeviceInfo> deviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_edit);
        ButterKnife.bind(this);

        warnInfo = (WarnInfo) getIntent().getSerializableExtra("warnInfo");
        if(warnInfo == null){
            tvTitle.setText("预警设定");
        }else{
            tvTitle.setText("预警修改");
        }
        ll_main.setVisibility(View.GONE);
        InitHttpData();
        initListener();
    }

    private void InitHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.warn_toedit_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid", warnInfo == null ? "": ""+warnInfo.getId())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        ProgressDialogUtils.showLoading(WarnEditActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            JSONArray array = new JSONArray(jsonObject.getString("deviceList"));
                            Gson gson = new Gson();
                            for(int i=0;i<array.length();i++){
                                DeviceInfo deviceInfo = gson.fromJson(array.getString(i),DeviceInfo.class);
                                deviceList.add(deviceInfo);
                            }
                            String[] items = new String[deviceList.size()];
                            for(int i=0;i<deviceList.size();i++){
                                items[i] = deviceList.get(i).getName();
                            }
                            ll_main.setVisibility(View.VISIBLE);
                            String[] s1 = {">","<","="};
                            sp_device_name.initializeStringValues(items,"请选择");
                            spS1.initializeStringValues(s1);
                            spS2.initializeStringValues(s1);
                            spS3.initializeStringValues(s1);

                            if(warnInfo != null){
                                etWarnName.setText(warnInfo.getName());
                                for(int i=0;i<deviceList.size();i++){
                                    if(warnInfo.getDevice().getId() == deviceList.get(i).getId()){
                                        sp_device_name.setSelection(i+1);
                                        break;
                                    }
                                }
                                for(int i=0;i<s1.length;i++){
                                    if(warnInfo.getS1().equals(s1[i])){
                                        spS1.setSelection(i);
                                    }
                                    if(warnInfo.getS2().equals(s1[i])){
                                        spS2.setSelection(i);
                                    }
                                    if(warnInfo.getS3().equals(s1[i])){
                                        spS3.setSelection(i);
                                    }
                                }
                                etS1.setText(String.valueOf(warnInfo.getV1()));
                                etS2.setText(String.valueOf(warnInfo.getV2()));
                                etS3.setText(String.valueOf(warnInfo.getV3()));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_save:
                if(TextUtils.isEmpty(etWarnName.getText().toString())){
                    ToastUtils.showShortToast(this,"请输入预警名称！");
                    return;
                }
                if(sp_device_name.getSelectedItemPosition() == 0){
                    ToastUtils.showShortToast(this,"请选择设备名称！");
                    return;
                }
                if(TextUtils.isEmpty(etS1.getText().toString())){
                    ToastUtils.showShortToast(this,"请输入设定值1");
                    return;
                }
                if(TextUtils.isEmpty(etS2.getText().toString())){
                    ToastUtils.showShortToast(this,"请输入设定值2");
                    return;
                }
                if(TextUtils.isEmpty(etS3.getText().toString())){
                    ToastUtils.showShortToast(this,"请输入设定值3");
                    return;
                }
                save();
                break;
        }
    }

    private void save() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.warn_edit_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid", warnInfo == null ? "": ""+warnInfo.getId())
                .params("id", warnInfo == null ? "": ""+warnInfo.getId())
                .params("code", warnInfo == null ? "": warnInfo.getCode())
                .params("name", etWarnName.getText().toString())
                .params("device.id", deviceList.get(sp_device_name.getSelectedItemPosition()-1).getId())
                .params("v1", etS1.getText().toString())
                .params("v2", etS2.getText().toString())
                .params("v3", etS3.getText().toString())
                .params("s1", (String)spS1.getSelectedItem())
                .params("s2", (String)spS2.getSelectedItem())
                .params("s3", (String)spS3.getSelectedItem())
                .params("remark", "")
                .params("state", warnInfo == null ? "1" : warnInfo.getState())
                .params("orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("operNames", MyApplication.getInstance().getLoginInfo().getUserInfo().getNames())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        ProgressDialogUtils.showLoading(WarnEditActivity.this);
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
                                if(warnInfo == null)
                                    messageEvent.setEventId(MessageEvent.EVENT_WARN_ADD);
                                else
                                    messageEvent.setEventId(MessageEvent.EVENT_WARN_UPDATE);
                                EventBus.getDefault().post(messageEvent);
                                finishActivity();
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
}
