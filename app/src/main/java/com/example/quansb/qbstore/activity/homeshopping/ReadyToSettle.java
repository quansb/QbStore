package com.example.quansb.qbstore.activity.homeshopping;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.adapter.GoodsDetailAdapter;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.entity.GoodsInfo;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.Logger;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.example.quansb.qbstore.view.BuyGoodsDialog;
import com.mysdk.glide.ImageLoader;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.listener.DisposeDataListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoaderInterface;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReadyToSettle extends BaseActivity implements View.OnClickListener, NestedScrollView.OnScrollChangeListener {
    @Bind(R.id.rl_title_layout)
    RelativeLayout rlTitleLayout;
    @Bind(R.id.sv_scroll_view)
    NestedScrollView svScrollView;
    @Bind(R.id.im_back)
    ImageView imBack;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.tv_add_cart)
    TextView tvAddCart;
    @Bind(R.id.tv_buy)
    TextView tvBuy;
    @Bind(R.id.im_share)
    ImageView imShare;
    @Bind(R.id.tv_goods_prices)
    TextView tvGoodsPrices;
    @Bind(R.id.tv_goods_details)
    TextView tvGoodsDetails;
    @Bind(R.id.tv_goods_courier)
    TextView tvGoodsCourier;
    @Bind(R.id.tv_goods_sales)
    TextView tvGoodsSales;
    @Bind(R.id.tv_goods_local)
    TextView tvGoodsLocal;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.lv_list_view)
    ListView lvListView;
    private Context context;
    private GoodsInfo goodsInfo;
    private   String goodsId;





    @Override
    protected void initData() {
        context = this;
        svScrollView.setOnScrollChangeListener(this);
        imBack.setOnClickListener(this);
        tvAddCart.setOnClickListener(this);
        tvBuy.setOnClickListener(this);
        imShare.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        PreferencesHelp help = new PreferencesHelp(context);
        goodsId = getIntent().getStringExtra("goods_id");
        RequestCenter.toReadyToSettle(help.getUserID(), goodsId, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                goodsInfo = (GoodsInfo) object;
                if (Integer.valueOf(goodsInfo.getStatus()) > 0) {
                    toGetGoodsDetails(goodsInfo);
                } else {
                    LoggerUtil.showToastShort(context, goodsInfo.getMsg());
                }
            }
            @Override
            public void onFailure(Object object) {
                Logger.showToastShort(getString(R.string.net_exception));
            }
        }, GoodsInfo.class);
    }







    /**
     *
     * @param goodsInfo  获取到实体，更新界面
     */
    private void toGetGoodsDetails(GoodsInfo goodsInfo) {
        tvGoodsPrices.setText(goodsInfo.getGoodsEntity().getGoods_price());
        tvGoodsDetails.setText(goodsInfo.getGoodsEntity().getGoods_des());
        tvGoodsCourier.setText("快递" + goodsInfo.getGoodsEntity().getCourier());
        tvGoodsLocal.setText(goodsInfo.getGoodsEntity().getLocal());
        tvGoodsSales.setText("月销" + goodsInfo.getGoodsEntity().getPin());
        upDataBanner(goodsInfo);
        GoodsDetailAdapter adapter = new GoodsDetailAdapter(context);
        adapter.setGoodsDesList(goodsInfo.getGoods_detail_urls());
        lvListView.setAdapter(adapter);
    }
    private void upDataBanner(GoodsInfo goodsInfo) {
      //  banner.setBannerStyle(BannerConfig.CENTER);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片地址集合
        banner.setImages(goodsInfo.getGoods_urls());
        //设置标题的集合
        //  mBanner.setBannerTitles(list_title);
        //设置轮播间隔时间
        //   banner.setDelayTime(3000);
        //设置轮播动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播
        banner.isAutoPlay(false);
        //设置指示器位置
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置监听
        //   banner.setOnBannerListener();
        //启动轮播图
        banner.start();
    }

    private class MyLoader implements ImageLoaderInterface {
        @Override
        public void displayImage(Context context, Object path, View imageView) {
            ImageLoader.getInstance().loadImageView(context, (String) path, (ImageView) imageView);
        }

        @Override
        public View createImageView(Context context) {
            return null;
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ready_settle_text_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.tv_buy:
                BuyGoodsDialog dialog = new BuyGoodsDialog();
                dialog.setGoodsInfo(goodsInfo);
                dialog.setContext(context);
                dialog.setGoodsId(goodsId);
                dialog.show(getSupportFragmentManager(), "buy_goods");
                break;
            case R.id.tv_add_cart:
                BuyGoodsDialog dialog1 = new BuyGoodsDialog();
                dialog1.setGoodsInfo(goodsInfo);
                dialog1.setContext(context);
                dialog1.setGoodsId(goodsId);
                dialog1.show(getSupportFragmentManager(), "add_cart");
                break;
            case R.id.im_share:

                break;
        }


    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {


        int maxHeight = banner.getHeight();
        int starChangeHeigth = rlTitleLayout.getHeight();
        if (i1 >= 0 && i1 <= starChangeHeigth) {
            rlTitleLayout.setBackgroundColor(getResources().getColor(R.color.color_white));
            rlTitleLayout.getBackground().mutate().setAlpha(0);
            tvTitleName.setVisibility(View.INVISIBLE);
            tvTitleName.setTextColor(Color.argb(0, 255, 11, 11));
            imBack.setImageResource(R.drawable.ic_back_white2);
            imBack.getBackground().mutate().setAlpha(255);
        } else if (i1 > starChangeHeigth && i1 < maxHeight) {
            rlTitleLayout.setBackgroundColor(getResources().getColor(R.color.color_white));
            rlTitleLayout.getBackground().mutate().setAlpha((int) (255 * (1.0 * (i1) / maxHeight)));
            tvTitleName.setVisibility(View.VISIBLE);
            tvTitleName.setTextColor(Color.argb((int) (255 * (1.0 * (i1) / maxHeight)), 255, 11, 11));
            imBack.setImageResource(R.drawable.ic_back_gay);
            imBack.getBackground().mutate().setAlpha((int) ((255 * (1 - (1.0 * (i1) / maxHeight)))));
        } else if (i1 > maxHeight) {
            rlTitleLayout.setBackgroundColor(getResources().getColor(R.color.color_white));
            tvTitleName.setVisibility(View.VISIBLE);
            rlTitleLayout.getBackground().mutate().setAlpha(255);
            tvTitleName.setTextColor(Color.argb(255, 255, 11, 11));
            imBack.getBackground().mutate().setAlpha(0);
        }
    }
}
