package com.android.hcbd.socc.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.WarnDataInfo;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ProgressDialogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DangerDeviceDealActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_danger_name)
    TextView tvDangerName;
    @BindView(R.id.tv_v1)
    TextView tvV1;
    @BindView(R.id.tv_s1)
    TextView tvS1;
    @BindView(R.id.tv_v2)
    TextView tvV2;
    @BindView(R.id.tv_s2)
    TextView tvS2;
    @BindView(R.id.tv_v3)
    TextView tvV3;
    @BindView(R.id.tv_s3)
    TextView tvS3;
    @BindView(R.id.et_deal_result)
    EditText etDealResult;

    private WarnDataInfo warnDataInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_device_deal);
        ButterKnife.bind(this);

        warnDataInfo = (WarnDataInfo) getIntent().getSerializableExtra("data");
        initData();
        ivBack.setOnClickListener(this);
        ivSave.setOnClickListener(this);
    }

    private void initData() {
        if(warnDataInfo == null)
            return;
        tvDeviceName.setText(warnDataInfo.getWarn().getDevice().getName());
        tvDangerName.setText(warnDataInfo.getWarn().getName());
        tvV1.setText(String.valueOf(warnDataInfo.getV1()));
        tvS1.setText(warnDataInfo.getWarn().getS1()+warnDataInfo.getWarn().getV1());
        tvV2.setText(String.valueOf(warnDataInfo.getV2()));
        tvS2.setText(warnDataInfo.getWarn().getS2()+warnDataInfo.getWarn().getV2());
        tvV3.setText(String.valueOf(warnDataInfo.getV3()));
        tvS3.setText(warnDataInfo.getWarn().getS3()+warnDataInfo.getWarn().getV3());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_save:
                if(TextUtils.isEmpty(etDealResult.getText().toString())){
                    ToastUtils.showShortToast(this,"请输入处理结果！");
                    break;
                }
                save();
                break;
        }
    }

    private void save() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.warn_data_edit_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid", warnDataInfo.getId())
                .params("remark", etDealResult.getText().toString())
                .params("state", warnDataInfo.getState())
                .params("orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("operNames", MyApplication.getInstance().getLoginInfo().getUserInfo().getNames())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        ProgressDialogUtils.showLoading(DangerDeviceDealActivity.this);
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
                                messageEvent.setEventId(messageEvent.EVENT_DANGER_DEVICE_REAL);
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
