package com.android.hcbd.socc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.hcbd.socc.MyApplication;
import com.android.hcbd.socc.R;
import com.android.hcbd.socc.crash.Cockroach;
import com.android.hcbd.socc.event.MessageEvent;
import com.android.hcbd.socc.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.rl_map)
    RelativeLayout rl_map;
    @BindView(R.id.rl_device_manager)
    RelativeLayout rl_device_manager;
    @BindView(R.id.rl_data)
    RelativeLayout rl_data;
    @BindView(R.id.rl_chart)
    RelativeLayout rl_chart;
    @BindView(R.id.rl_early_warning)
    RelativeLayout rl_early_warning;
    @BindView(R.id.rl_resolver_manager)
    RelativeLayout rl_resolver_manager;
    @BindView(R.id.rl_setting)
    RelativeLayout rl_setting;
    @BindView(R.id.rl_about_us)
    RelativeLayout rl_about_us;
    @BindView(R.id.rl_product_center)
    RelativeLayout rl_product_center;
    @BindView(R.id.rl_contact_us)
    RelativeLayout rl_contact_us;
    @BindView(R.id.rl_video)
    RelativeLayout rl_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initListener();
    }

    private void initListener() {
        rl_map.setOnClickListener(this);
        rl_device_manager.setOnClickListener(this);
        rl_data.setOnClickListener(this);
        rl_chart.setOnClickListener(this);
        rl_early_warning.setOnClickListener(this);
        rl_resolver_manager.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        rl_product_center.setOnClickListener(this);
        rl_contact_us.setOnClickListener(this);
        rl_video.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_map:
                intent.setClass(this,MapActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_device_manager:
                intent.setClass(this,DeviceManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_data:
                intent.setClass(this,DataSearchActivity.class);
                intent.putExtra("type","data");
                startActivity(intent);
                break;
            case R.id.rl_chart:
                intent.setClass(this,DataSearchActivity.class);
                intent.putExtra("type","charts");
                startActivity(intent);
                break;
            case R.id.rl_early_warning:
                intent.setClass(this,WarnListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_resolver_manager:
                intent.setClass(this,SoluSoftManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting:
                intent.setClass(this,SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about_us:
                intent.setClass(this,AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_product_center:
                intent.setClass(this,ProductCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_contact_us:
                intent.setClass(this,ContactUsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_video:
                intent.setClass(this,CameraListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private static boolean mBackKeyPressed = false;//记录是否有首次按键
    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
            ToastUtils.showShortToast(MyApplication.getInstance(),"再按一次退出程序");
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        }else{//退出程序
            Cockroach.uninstall();
            this.finish();
            System.exit(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEventId()){
            case MessageEvent.EVENT_LOGINOUT:
                startActivity(new Intent(this,LoginActivity.class));
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
