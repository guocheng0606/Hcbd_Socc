package com.android.hcbd.socc.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DeviceEditInfo;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ProgressDialogUtils;
import com.android.hcbd.socc.util.ToastUtils;
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

public class DeviceEditActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sn)
    EditText etSn;
    @BindView(R.id.et_x)
    EditText etX;
    @BindView(R.id.et_y)
    EditText etY;
    @BindView(R.id.et_h)
    EditText etH;
    @BindView(R.id.et_upload_code)
    EditText etUploadCode;
    @BindView(R.id.cb_yes)
    AppCompatCheckBox cbYes;
    @BindView(R.id.cb_no)
    AppCompatCheckBox cbNo;
    @BindView(R.id.et_solu_time)
    EditText etSoluTime;
    @BindView(R.id.tv_subordinate_item)
    TextView tvSubordinateItem;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.activity_device_edit)
    LinearLayout activityDeviceEdit;
    @BindView(R.id.iv_upload_code)
    ImageView ivUploadCode;
    @BindView(R.id.iv_subordinate_item)
    ImageView ivSubordinateItem;
    @BindView(R.id.iv_device_type)
    ImageView ivDeviceType;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.et_port)
    EditText etPort;

    private DeviceInfo deviceInfo;
    private DeviceEditInfo deviceEditInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_edit);
        ButterKnife.bind(this);

        deviceInfo = (DeviceInfo) getIntent().getSerializableExtra("data");
        tvTitle.setText(deviceInfo == null ? "添加设备信息" : "修改设备信息");
        ll_main.setVisibility(View.GONE);
        cbYes.setChecked(true);
        cbNo.setChecked(false);
        initHttpData();
        initListener();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x11) {
                ll_main.setVisibility(View.VISIBLE);

                if (deviceInfo == null)
                    return;
                tvCode.setText(deviceInfo.getCode());
                etName.setText(deviceInfo.getName());
                etSn.setText(deviceInfo.getSnNo());
                etX.setText(String.valueOf(deviceInfo.getX()));
                etY.setText(String.valueOf(deviceInfo.getY()));
                etH.setText(String.valueOf(deviceInfo.getZ()));
                etUploadCode.setText(deviceInfo.getUpCode());
                if (deviceInfo.getIsRef().equals("1")) {
                    cbYes.setChecked(true);
                    cbNo.setChecked(false);
                } else {
                    cbYes.setChecked(false);
                    cbNo.setChecked(true);
                }
                etSoluTime.setText(deviceInfo.getSoluTime());
                tvSubordinateItem.setText(deviceInfo.getProject().getNames());
                tvDeviceType.setText(deviceInfo.getType().getNames());
                etPort.setText(String.valueOf(deviceInfo.getPort()));
                etRemark.setText(deviceInfo.getRemark());
            }
        }
    };

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        ivUploadCode.setOnClickListener(this);
        ivSubordinateItem.setOnClickListener(this);
        tvSubordinateItem.setOnClickListener(this);
        ivDeviceType.setOnClickListener(this);
        tvDeviceType.setOnClickListener(this);
        cbYes.setOnClickListener(this);
        cbNo.setOnClickListener(this);
    }

    private void initHttpData() {
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.device_toedit_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid", deviceInfo == null ? "" : "" + deviceInfo.getId())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        ProgressDialogUtils.showLoading(DeviceEditActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        realJson(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShortToast(MyApplication.getInstance(), "请检查网络是否连接！");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        ProgressDialogUtils.dismissLoading();
                    }
                });

    }

    private void realJson(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    Gson gson = new Gson();
                    if (deviceEditInfo == null)
                        deviceEditInfo = new DeviceEditInfo();
                    JSONArray deviceArray = new JSONArray(jsonObject.getString("deviceList"));
                    List<DeviceEditInfo.Device> deviceList = new ArrayList<DeviceEditInfo.Device>();
                    for (int i = 0; i < deviceArray.length(); i++) {
                        DeviceEditInfo.Device device = gson.fromJson(deviceArray.getString(i), DeviceEditInfo.Device.class);
                        deviceList.add(device);
                    }
                    deviceEditInfo.setDeviceList(deviceList);

                    JSONArray typeArray = new JSONArray(jsonObject.getString("typeList"));
                    List<DeviceEditInfo.Type> typeList = new ArrayList<DeviceEditInfo.Type>();
                    for (int i = 0; i < typeArray.length(); i++) {
                        DeviceEditInfo.Type type = gson.fromJson(typeArray.getString(i), DeviceEditInfo.Type.class);
                        typeList.add(type);
                    }
                    deviceEditInfo.setTypeList(typeList);

                    JSONArray projectArray = new JSONArray(jsonObject.getString("projectList"));
                    List<DeviceEditInfo.Project> projectList = new ArrayList<DeviceEditInfo.Project>();
                    for (int i = 0; i < projectArray.length(); i++) {
                        DeviceEditInfo.Project project = gson.fromJson(projectArray.getString(i), DeviceEditInfo.Project.class);
                        projectList.add(project);
                    }
                    deviceEditInfo.setProjectList(projectList);

                    if (deviceInfo != null) {
                        DeviceEditInfo.DataInfo dataInfo = gson.fromJson(jsonObject.getString("data"), DeviceEditInfo.DataInfo.class);
                        deviceEditInfo.setDataInfo(dataInfo);
                    }

                    Message message = new Message();
                    message.what = 0x11;
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_upload_code:
                String[] devices = new String[deviceEditInfo.getDeviceList().size()];
                for (int i = 0; i < deviceEditInfo.getDeviceList().size(); i++) {
                    devices[i] = deviceEditInfo.getDeviceList().get(i).getNames();
                }
                showDialog(devices, etUploadCode);
                break;
            case R.id.iv_subordinate_item:
            case R.id.tv_subordinate_item:
                String[] project = new String[deviceEditInfo.getProjectList().size()];
                for (int i = 0; i < deviceEditInfo.getProjectList().size(); i++) {
                    project[i] = deviceEditInfo.getProjectList().get(i).getNames();
                }
                showDialog(project, tvSubordinateItem);
                break;
            case R.id.iv_device_type:
            case R.id.tv_device_type:
                String[] types = new String[deviceEditInfo.getTypeList().size()];
                for (int i = 0; i < deviceEditInfo.getTypeList().size(); i++) {
                    types[i] = deviceEditInfo.getTypeList().get(i).getNames();
                }
                showDialog(types, tvDeviceType);
                break;
            case R.id.iv_save:
                if(TextUtils.isEmpty(etUploadCode.getText().toString())){
                    cbYes.setChecked(true);
                    cbNo.setChecked(false);
                }else{
                    cbYes.setChecked(false);
                    cbNo.setChecked(true);
                }
                if (TextUtils.isEmpty(tvSubordinateItem.getText().toString())) {
                    ToastUtils.showShortToast(this, "请选择所属项目！");
                    return;
                }
                if (TextUtils.isEmpty(tvDeviceType.getText().toString())) {
                    ToastUtils.showShortToast(this, "请选择设备类型！");
                    return;
                }
                saveHttp();
                break;
            case R.id.cb_yes:
                cbYes.setChecked(true);
                cbNo.setChecked(false);
                break;
            case R.id.cb_no:
                cbYes.setChecked(false);
                cbNo.setChecked(true);
                break;

        }
    }

    private void saveHttp() {
        int projectId = 0, typeId = 0;
        for (int i = 0; i < deviceEditInfo.getProjectList().size(); i++) {
            if (tvSubordinateItem.getText().toString().equals(deviceEditInfo.getProjectList().get(i).getNames())) {
                projectId = deviceEditInfo.getProjectList().get(i).getId();
            }
        }
        for (int i = 0; i < deviceEditInfo.getTypeList().size(); i++) {
            if (tvDeviceType.getText().toString().equals(deviceEditInfo.getTypeList().get(i).getNames())) {
                typeId = deviceEditInfo.getTypeList().get(i).getId();
            }
        }
        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+HttpUrlUtils.device_edit_url)
                .params("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode())
                .params("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("token", MyApplication.getInstance().getLoginInfo().getToken())
                .params("oid", deviceInfo == null ? "" : "" + deviceInfo.getId())
                .params("id", deviceInfo == null ? "" : "" + deviceInfo.getId())
                .params("code", deviceInfo == null ? "" : "" + deviceInfo.getCode())
                .params("name", etName.getText().toString())
                .params("snNo", etSn.getText().toString())
                .params("x", etX.getText().toString())
                .params("y", etY.getText().toString())
                .params("z", etH.getText().toString())
                .params("upCode", etUploadCode.getText().toString())
                .params("isRef", cbYes.isChecked() ? "1" : "0")
                .params("soluTime", etSoluTime.getText().toString())
                .params("project.id", projectId)
                .params("type.id", typeId)
                .params("port", etPort.getText().toString())
                .params("remark", etRemark.getText().toString())
                .params("state", deviceInfo == null ? "1" : deviceInfo.getState())
                .params("orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode())
                .params("operNames", MyApplication.getInstance().getLoginInfo().getUserInfo().getNames())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        ProgressDialogUtils.showLoading(DeviceEditActivity.this);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            if (!TextUtils.isEmpty(jsonObject.getString("data"))) {
                                ToastUtils.showShortToast(MyApplication.getInstance(), jsonObject.getString("data"));
                                MessageEvent messageEvent = new MessageEvent();
                                if (deviceInfo == null) {
                                    messageEvent.setEventId(MessageEvent.EVENT_DEVICE_ADD);
                                } else {
                                    messageEvent.setEventId(messageEvent.EVENT_DEVICE_UPDATE);
                                }
                                EventBus.getDefault().post(messageEvent);
                                finishActivity();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                if (!TextUtils.isEmpty(jsonObject.getString("error"))) {
                                    ToastUtils.showShortToast(MyApplication.getInstance(), jsonObject.getString("error"));
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShortToast(MyApplication.getInstance(), "请检查网络是否连接！");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        ProgressDialogUtils.dismissLoading();
                    }
                });


    }
}
