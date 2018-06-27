package com.android.hcbd.socc.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hcbd.socc.R;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductInfo01Activity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;

    private List<Integer> bannerList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info01);
        ButterKnife.bind(this);

        bannerList.add(R.drawable.image_big_hx_mini);
        bannerList.add(R.drawable.image_big_hx_mini_2);
        bannerList.add(R.drawable.image_big_hx_mini_3);
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerViewHolder();
            }
        },bannerList).setPageIndicator(new int[]{R.drawable.ic_page_indicator,R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finishActivity();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    class BannerViewHolder implements Holder<Integer> {

        private ImageView mBannerImageView;

        @Override
        public View createView(Context context) {
            mBannerImageView = new ImageView(ProductInfo01Activity.this);
            mBannerImageView.setScaleType(ImageView.ScaleType.CENTER);
            return mBannerImageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            Glide.with(ProductInfo01Activity.this).load(data).into(mBannerImageView);
        }

    }

}
