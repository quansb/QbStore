package com.example.quansb.qbstore.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseFragment;
import com.example.quansb.qbstore.entity.BannersEntity;
import com.example.quansb.qbstore.entity.HomeDataEntity;
import com.example.quansb.qbstore.entity.UserInfo;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.Logger;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.example.quansb.qbstore.view.RushToBuyController;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.exception.OkHttpException;
import com.mysdk.okhttp.listener.DisposeDataListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements View.OnClickListener, OnBannerListener, NestedScrollView.OnScrollChangeListener {


    @Bind(R.id.ll_up_data_color_first_layout)
    LinearLayout llUpDataColorFirstLayout;
    @Bind(R.id.ll_up_data_color_second_layout)
    LinearLayout llUpDataColorSecondLayout;
    //    @Bind(R.id.ll_up_data_color_theme_layout)
//    LinearLayout llUpDataColorThemeLayout;
    @Bind(R.id.sv_scroll_view)
    NestedScrollView svScrollView;
    @Bind(R.id.mBanner)
    Banner mBanner;
    @Bind(R.id.rl_title_name_layout)
    RelativeLayout rlTitleNameLayout;
    @Bind(R.id.ll_rush_table_top_left)
    LinearLayout llRushTableTopLeft;
    @Bind(R.id.ll_rush_table_top_right)
    LinearLayout llRushTableTopRight;
    @Bind(R.id.ll_rush_table_bottom_lift)
    LinearLayout llRushTableBottomLift;
    @Bind(R.id.ll_rush_table_bottom_right)
    LinearLayout llRushTableBottomRight;

    //    private  BannersEntity bannersEntity;
    private ArrayList<String> list_title;
    private UserInfo userInfo;
    private PreferencesHelp preferencesHelp;
    private String user_id;
    private ArrayList<String> list_path = new ArrayList<>();
    private ArrayList<String> colors = new ArrayList<>();
    private ArrayList<String> banner_jump_url = new ArrayList<>();
    private Boolean flag;
    private Context context;
    private int mCurrntIndex = 0;
    private HomeDataEntity homeDataEntity;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }


    /**
     * 数据初始化
     */
    private void initData() {
        context = getContext();
        preferencesHelp = new PreferencesHelp(context);
        user_id = preferencesHelp.getString("user_id", "");    //通过用户id来发起网络请求
        rlTitleNameLayout.getBackground().mutate().setAlpha(0);
        flag = true; // 标题栏颜色 是否可以跟随banner变化
        svScrollView.setOnScrollChangeListener(this);



        RequestCenter.toUpdateHomeData(user_id, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
               homeDataEntity= (HomeDataEntity) object;
                if (Integer.valueOf(homeDataEntity.getStatus()) > 0) {
                    upDataBanner();
                    loadRushToBuyLayout();

                } else {
                    Logger.showToastShort(homeDataEntity.getMsg());
                }
            }
            @Override
            public void onFailure(Object object) {
                OkHttpException okHttpException= (OkHttpException) object;
                LoggerUtil.logInfo(okHttpException.getEmsg().toString());
                Toast.makeText(context,okHttpException.getEmsg().toString(), Toast.LENGTH_LONG).show();
            }
        }, HomeDataEntity.class);

    }

    /**
     * 初始化数据和视图
     *
     * @param
     */
    private void initView() {


    }

    /**
     * 加载 限时抢购 的试图 在 banner下方 4个 item
     */
    private void loadRushToBuyLayout() {
        //        View viewTopLife = LayoutInflater.from(context).inflate(R.layout.common_rush_to_buy_layout, llRushTableTopLeft, false);
//        llRushTableTopLeft.addView(viewTopLife);
        RushToBuyController rushToBuyController = new RushToBuyController(context);
        rushToBuyController.initData(homeDataEntity.getRushGoodsEntities());
        rushToBuyController.loadView(R.layout.common_rush_to_buy_layout, llRushTableTopLeft, 0);
        rushToBuyController.loadView(R.layout.common_rush_to_buy_layout, llRushTableTopRight, 1);
        rushToBuyController.loadView(R.layout.common_rush_to_buy_layout, llRushTableBottomLift, 2);
        rushToBuyController.loadView(R.layout.common_rush_to_buy_layout, llRushTableBottomRight, 3);
    }


    public void upDataBanner() {
        for (BannersEntity.Banner banner : homeDataEntity.getBannersEntity().getBanners()) {
            list_path.add(banner.getBanner_url());
            colors.add(banner.getBanner_color());
            banner_jump_url.add(banner.getBanner_jump_url());
            openBanner();
            adaptBannerColor();
        }
    }


    /**
     * 初始化轮播图 启动banner
     */
    private void openBanner() {

//        //放图片地址的集合
//        list_path = new ArrayList<>();
//        list_path.add("https://zsc-content.oss-cn-shenzhen.aliyuncs.com/first_banner.jpg");
//        //   list_path.add("https://img.alicdn.com/tfs/TB1nRdPMAPoK1RjSZKbXXX1IXXa-990-320.jpg_1080x1800Q90s50.jpg");
//        list_path.add("https://zsc-content.oss-cn-shenzhen.aliyuncs.com/second_banner.jpg");
//        list_path.add("https://zsc-content.oss-cn-shenzhen.aliyuncs.com/third_banner.jpg");
//        //  list_path.add("https://img.alicdn.com/tfs/TB1_3YxNCzqK1RjSZFpXXakSXXa-990-320.jpg_1080x1800Q90s50.jpg");
//        list_path.add("https://zsc-content.oss-cn-shenzhen.aliyuncs.com/fourth_banner.jpg");
//        list_path.add("https://zsc-content.oss-cn-shenzhen.aliyuncs.com/fifth_banner.jpg");
        //放标题的集合
        list_title = new ArrayList<>();
        list_title.add("轮播图1");   //轮播图1    轮播图2    轮播图3    轮播图4
        list_title.add("轮播图2");
        list_title.add("轮播图3");
        list_title.add("轮播图4");
        //设置banner样式
//        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器，图片加载器在下方
        mBanner.setImageLoader(new MyLoader());
        //设置图片地址集合
        LoggerUtil.logInfo(list_path.toString());
        mBanner.setImages(list_path);
        //设置标题的集合
        //  mBanner.setBannerTitles(list_title);
        //设置轮播间隔时间
        mBanner.setDelayTime(3000);
        //设置轮播动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置轮播
        mBanner.isAutoPlay(true);
        //设置指示器位置
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置监听
        mBanner.setOnBannerListener(this);
        //启动轮播图
        mBanner.start();
    }


    //****************************************

    /**
     * 根据banner的图片来改变 首部的布局颜色
     */
    private void adaptBannerColor() {
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                setColor(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setColor(int i) {
        mCurrntIndex = i;
        if (i == colors.size() + 1) {
            if (flag) {
                llUpDataColorFirstLayout.setBackgroundColor(Color.parseColor(colors.get(0)));
            }

            llUpDataColorSecondLayout.setBackgroundColor(Color.parseColor(colors.get(0)));
            //   llUpDataColorThemeLayout.setBackgroundColor(Color.parseColor(colors.get(0)));   //主题更换颜色

        } else if (i == 0) {
            if (flag) {
                llUpDataColorFirstLayout.setBackgroundColor(Color.parseColor(colors.get(colors.size() - 1)));
            }

            llUpDataColorSecondLayout.setBackgroundColor(Color.parseColor(colors.get(colors.size() - 1)));
            //        llUpDataColorThemeLayout.setBackgroundColor(Color.parseColor(colors.get(colors.size() - 1)));

        } else {
            if (flag) {
                llUpDataColorFirstLayout.setBackgroundColor(Color.parseColor(colors.get(i - 1)));
            }
            llUpDataColorSecondLayout.setBackgroundColor(Color.parseColor(colors.get(i - 1)));
            // llUpDataColorThemeLayout.setBackgroundColor(Color.parseColor(colors.get(i - 1)));
        }
    }
    //*************************************************


    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }


    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getContext(), position + "项", Toast.LENGTH_SHORT).show();

        switch (position) {
            case 0:

                JumpActivityUtil.goToBannerJumpActivity(getContext(), position, banner_jump_url);
                break;

            case 1:
                JumpActivityUtil.goToBannerJumpActivity(getContext(), position, banner_jump_url);
                break;
            case 2:
                JumpActivityUtil.goToBannerJumpActivity(getContext(), position, banner_jump_url);
                break;
            case 3:
                JumpActivityUtil.goToBannerJumpActivity(getContext(), position, banner_jump_url);
                break;
            case 4:
                JumpActivityUtil.goToBannerJumpActivity(getContext(), position, banner_jump_url);
                break;
        }
    }

    /**
     * @param nestedScrollView
     * @param scrollX
     * @param scrollY          往下滑动的距离
     * @param oldScrollX
     * @param oldScrollY
     */
    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


//        int showTitleLayoutHeight = rlTitleNameLayout.getHeight() * 3;
        int maxAlphaHeight = rlTitleNameLayout.getHeight() * 3;

        int contentHeight = (int) (llUpDataColorSecondLayout.getHeight() * 0.65);
        if (scrollY <= contentHeight) {
            flag = true;
            setColor(mCurrntIndex);
            rlTitleNameLayout.getBackground().mutate().setAlpha(0);

        } else if (contentHeight < scrollY && scrollY < maxAlphaHeight) {
            llUpDataColorFirstLayout.setBackgroundColor(getResources().getColor(R.color.color_f15555));

            llUpDataColorFirstLayout.getBackground().mutate().setAlpha((int) (255 * (1.0 * (scrollY - contentHeight / 4) / maxAlphaHeight)));
            rlTitleNameLayout.getBackground().mutate().setAlpha((int) (255 * (1.0 * (scrollY) / maxAlphaHeight)));
//            rlTitleNameLayout.setBackgroundColor(getResources().getColor(R.color.color_f15555));
            flag = false;
        } else if (scrollY >= maxAlphaHeight) {
            llUpDataColorFirstLayout.getBackground().mutate().setAlpha(255);
        }

    }


    private class MyLoader implements ImageLoaderInterface {
        @Override
        public void displayImage(Context context, Object path, View imageView) {
            Glide.with(context).load(path).into((ImageView) imageView);
        }

        @Override
        public View createImageView(Context context) {
            return null;
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
