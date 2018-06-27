package com.android.hcbd.socc.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.util.ToastUtils;
import com.blankj.utilcode.utils.AppUtils;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_version)
    LinearLayout llVersion;
    @BindView(R.id.ll_feedback)
    LinearLayout llFeedback;
    @BindView(R.id.ll_ip_address)
    LinearLayout llIpAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        tvVersion.setText("当前版本：" + AppUtils.getAppVersionName(this));
    }

    @OnClick({R.id.iv_back, R.id.ll_version, R.id.ll_feedback,R.id.ll_ip_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.ll_version:
                PgyUpdateManager.register(this, new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        ToastUtils.showShortToast(MyApplication.getInstance(), "已经是最新版本");
                    }

                    @Override
                    public void onUpdateAvailable(String s) {
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(s);
                        new AlertDialog.Builder(SettingActivity.this)
                                .setTitle("发现新版本，v" + appBean.getVersionName())
                                .setMessage(appBean.getReleaseNote())
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startDownloadTask(SettingActivity.this, appBean.getDownloadURL());
                                    }
                                }).show();
                    }
                });
                break;
            case R.id.ll_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.ll_ip_address:
                startActivity(new Intent(this, IpAddressActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_LOGINOUT:
                finish();
                break;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
