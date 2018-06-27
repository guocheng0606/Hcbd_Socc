package com.android.hcbd.socc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.hcbd.socc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductCenterActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_produce_01)
    LinearLayout llProduce01;
    @BindView(R.id.ll_produce_02)
    LinearLayout llProduce02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_center);
        ButterKnife.bind(this);

        ivBack.setOnClickListener(this);
        llProduce01.setOnClickListener(this);
        llProduce02.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
            case R.id.ll_produce_01:
                startActivity(new Intent(this,ProductInfo01Activity.class));
                break;
            case R.id.ll_produce_02:
                startActivity(new Intent(this,ProductInfo02Activity.class));
                break;
        }
    }
}
