package com.android.hcbd.socc.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.android.hcbd.socc.R;
import com.android.hcbd.socc.adapter.MyViewPagerAdapter;
import com.android.hcbd.socc.ui.fragment.CompanyCultureFragment;
import com.android.hcbd.socc.ui.fragment.CompanyIntroduceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ;
        ButterKnife.bind(this);
        setupTabLayout();
        ivBack.setOnClickListener(this);
    }

    private void setupTabLayout() {
        //ViewPager关联适配器
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CompanyIntroduceFragment.newInstance(null,null),"企业简介");
        adapter.addFragment(CompanyCultureFragment.newInstance(null,null),"企业文化");
        viewPager.setAdapter(adapter);
        //ViewPager和TabLayout关联
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
        }
    }
}
