package com.example.quansb.qbstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseFragment;
import com.mysdk.util.ViewCommonUtils;
import com.mysdk.view.CircleImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;
import   com.mysdk.logger.LoggerUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements View.OnClickListener, OnBannerListener {

    @Bind(R.id.mBanner)
    Banner mBanner;
    @Bind(R.id.ll_up_color_head_layout)
    LinearLayout llUpColorHeadLayout;

    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

    private int a[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        openBanner();
        adaptBannerColor();
        return view;
    }

    /**
     * 初始化数据和视图
     * @param view
     */
    private void initView(View view) {
        a=new int[]{ getResources().getColor(R.color.color_90C9E7),getResources().getColor(R.color.color_6C09de),
                getResources().getColor(R.color.color_000000), getResources().getColor(R.color.color_737373),
                getResources().getColor(R.color.color_FF5500)};

        mBanner = view.findViewById(R.id.mBanner);
        llUpColorHeadLayout.setBackgroundColor(a[0]);
    }

    /**
     * 初始化轮播图 启动banner
     */
    private void openBanner() {

        //放图片地址的集合
        list_path = new ArrayList<>();
        list_path.add("http://img.zcool.cn/community/0172bf57ac3cf60000012e7ea54ee4.jpg@900w_1l_2o_100sh.jpg");
     //   list_path.add("https://img.alicdn.com/tfs/TB1nRdPMAPoK1RjSZKbXXX1IXXa-990-320.jpg_1080x1800Q90s50.jpg");
        list_path.add("https://gw.alicdn.com/imgextra/i2/120/O1CN015YvmPr1Cl02PAqsVO_!!120-0-lubanu.jpg");
        list_path.add("https://gdp.alicdn.com/imgextra/i2/2528254892/O1CN01Hj86le1m0a3sO5HAe_!!2528254892.jpg");
      //  list_path.add("https://img.alicdn.com/tfs/TB1_3YxNCzqK1RjSZFpXXakSXXa-990-320.jpg_1080x1800Q90s50.jpg");
        list_path.add("https://gdp.alicdn.com/imgextra/i4/890482188/O1CN01aXfeNp1S296ImpSOo_!!890482188.jpg");
        list_path.add("https://img.alicdn.com/tfs/TB1d5AGLY2pK1RjSZFsXXaNlXXa-990-320.jpg_1080x1800Q90s50.jpg");
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
              LoggerUtil.logInfo(i+"");
                if (i==a.length+1){
                    llUpColorHeadLayout.setBackgroundColor(a[0]);
                }else if (i==0){
                    llUpColorHeadLayout.setBackgroundColor(a[a.length-1]);
                }else {
                    llUpColorHeadLayout.setBackgroundColor(a[i-1]);
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }


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

        switch (position){
            case 0:
            break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
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
