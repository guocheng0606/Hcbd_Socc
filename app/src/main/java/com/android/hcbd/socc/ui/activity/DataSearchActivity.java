package com.android.hcbd.socc.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.entity.DataSearchInfo;
import com.android.hcbd.socc.entity.DeviceInfo;
import com.android.hcbd.socc.util.HttpUrlUtils;
import com.android.hcbd.socc.util.LogUtils;
import com.android.hcbd.socc.util.ToastUtils;
import com.google.gson.Gson;
import com.lany.state.StateLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataSearchActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_device_name)
    EditText etDeviceName;
    @BindView(R.id.iv_device_name)
    ImageView ivDeviceName;
    @BindView(R.id.tv_begin_time)
    TextView tvBeginTime;
    @BindView(R.id.iv_begin_time)
    ImageView ivBeginTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.iv_end_time)
    ImageView ivEndTime;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.stateLayout)
    StateLayout stateLayout;
    private DataSearchInfo dataSearchInfo;
    private List<DeviceInfo> deviceList = new ArrayList<>();
    private String[] devices;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_search);
        ButterKnife.bind(this);

        stateLayout.showLoading();
        type = getIntent().getStringExtra("type");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        if (dataSearchInfo == null)
            dataSearchInfo = new DataSearchInfo();
        dataSearchInfo.setBeginTime(simpleDateFormat.format(date)+" 00:00");
        dataSearchInfo.setEndTime(simpleDateFormat.format(date)+" 23:59");

        if (dataSearchInfo != null) {
            etDeviceName.setText(dataSearchInfo.getDeviceName());
            tvBeginTime.setText(dataSearchInfo.getBeginTime());
            tvEndTime.setText(dataSearchInfo.getEndTime());
        }
        initHttpData();
        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(this);
        ivDeviceName.setOnClickListener(this);
        ivBeginTime.setOnClickListener(this);
        tvBeginTime.setOnClickListener(this);
        ivEndTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    private void initHttpData() {
        HttpParams params = new HttpParams();
        params.put("sessionOper.code", MyApplication.getInstance().getLoginInfo().getUserInfo().getCode());
        params.put("sessionOper.orgCode", MyApplication.getInstance().getLoginInfo().getUserInfo().getOrgCode());
        params.put("token", MyApplication.getInstance().getLoginInfo().getToken());
        if (!TextUtils.isEmpty(dataSearchInfo.getDeviceName())) {
            params.put("device.name", dataSearchInfo.getDeviceName());
        }
        if (!TextUtils.isEmpty(dataSearchInfo.getBeginTime())) {
            params.put("beginTime", dataSearchInfo.getBeginTime());
        }
        if (!TextUtils.isEmpty(dataSearchInfo.getEndTime())) {
            params.put("endTime", dataSearchInfo.getEndTime());
        }
        params.put("currentPage", 1);

        OkGo.<String>post(MyApplication.getInstance().getBsaeUrl()+ HttpUrlUtils.device_data_url)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        LogUtils.LogShow(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result);
                            Gson gson = new Gson();

                            deviceList.clear();
                            JSONArray a = new JSONArray(jsonObject.getString("deviceList"));
                            for(int i=0;i<a.length();i++){
                                DeviceInfo deviceInfo = gson.fromJson(a.getString(i),DeviceInfo.class);
                                deviceList.add(deviceInfo);
                            }

                            devices = new String[deviceList.size()];
                            for (int i = 0; i < deviceList.size(); i++) {
                                devices[i] = deviceList.get(i).getName();
                            }
                            stateLayout.showContent();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShortToast(MyApplication.getInstance(),"请检查是否连接网络！");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.iv_device_name:
                //showDialog(devices, etDeviceName);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
                //builder.setTitle("请选择"); //设置标题
                builder.setItems(devices,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        etDeviceName.setText(devices[which]);
                        if (deviceList.get(which).getType().getName().equals("GNSS")){
                            dataSearchInfo.setGnss(true);
                        }else{
                            dataSearchInfo.setGnss(false);
                        }

                    }
                });
                builder.create().show();

                break;
            case R.id.iv_begin_time:
                showAllTimePickerDialog(tvBeginTime);
                break;
            case R.id.tv_begin_time:
                showAllTimePickerDialog(tvBeginTime);
                break;
            case R.id.iv_end_time:
                showAllTimePickerDialog(tvEndTime);
                break;
            case R.id.tv_end_time:
                showAllTimePickerDialog(tvEndTime);
                break;
            case R.id.btn_search:
                if (dataSearchInfo == null)
                    dataSearchInfo = new DataSearchInfo();
                dataSearchInfo.setDeviceName(etDeviceName.getText().toString());
                dataSearchInfo.setBeginTime(tvBeginTime.getText().toString());
                dataSearchInfo.setEndTime(tvEndTime.getText().toString());

                Intent intent = new Intent();
                if (type.equals("data")) {
                    intent.setClass(DataSearchActivity.this,DeviceDataActivity.class);
                } else {
                    intent.setClass(DataSearchActivity.this,EChartsActivity.class);
                }
                intent.putExtra("search_info",dataSearchInfo);
                startActivity(intent);

                break;
        }

    }
}
