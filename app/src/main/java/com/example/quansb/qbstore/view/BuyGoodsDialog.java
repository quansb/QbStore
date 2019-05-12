package com.example.quansb.qbstore.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.FlowLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.entity.AddressInfo;
import com.example.quansb.qbstore.entity.BaseDataEntity;
import com.example.quansb.qbstore.entity.Event;
import com.example.quansb.qbstore.entity.GoodsInfo;
import com.example.quansb.qbstore.entity.SpecificationsEntity;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.Constant;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.glide.ImageLoader;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.listener.DisposeDataListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BuyGoodsDialog extends BottomDialog {


    @Bind(R.id.ll_bottom_sure)
    LinearLayout llBottomSure;
    @Bind(R.id.tv_prices)
    TextView tvPrices;
    @Bind(R.id.im_close)
    ImageView imClose;
    @Bind(R.id.tv_residue)
    TextView tvResidue;
    @Bind(R.id.tv_show_selected)
    TextView tvShowSelected;
    @Bind(R.id.im_goods_image)
    ImageView imGoodsImage;
    @Bind(R.id.rl_title_layout)
    RelativeLayout rlTitleLayout;
    @Bind(R.id.tv_check_size)
    TextView tvCheckSize;
    @Bind(R.id.fl_select_color_layout)
    FlowLayout flSelectColorLayout;
    @Bind(R.id.fl_select_size_layout)
    FlowLayout flSelectSizeLayout;
    @Bind(R.id.tv_minus)
    TextView tvMinus;
    @Bind(R.id.tv_sum)
    TextView tvSum;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    private GoodsInfo goodsInfo;
    private Context context;
    private String userId;
    private String goodsId;
    private SpecificationsEntity specificationsEntity;
    private List<TextView> tvColorList = new ArrayList<>();
    private List<TextView> tvSizeList = new ArrayList<>();
    private int colorPrevious = 0;
    private int sizePrevious = 0;
    private boolean isClickColor = false;
    private boolean isClickSize = false;
    private String goods_color;
    private String goods_size;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_buy_goods_layout, container, false);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        PreferencesHelp help = new PreferencesHelp(context);
        userId = help.getUserID();
        if(goodsInfo!=null&&goodsInfo.getGoodsEntity()!=null&&goodsInfo.getGoodsEntity().getGoods_url()!=null) {
            ImageLoader.getInstance().loadImageView(context, goodsInfo.getGoodsEntity().getGoods_url(), imGoodsImage, false);
        }
        if(goodsInfo!=null&&goodsInfo.getGoodsEntity()!=null&&goodsInfo.getGoodsEntity().getGoods_price()!=null) {
        tvPrices.setText(goodsInfo.getGoodsEntity().getGoods_price());
        }
        //      tvResidue.setText("存库"+goodsInfo.getGoodsEntity().getPin()+"件");
        GetGoodsSpecifications();
        return rootView;
    }

    /**
     * 发送网络请求获取物品参数
     */
    private void GetGoodsSpecifications() {
        RequestCenter.toGetGoodsSpecifications(userId, goodsId, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                specificationsEntity = (SpecificationsEntity) object;
                if (Integer.valueOf(specificationsEntity.getStatus()) > 0) {
                //    LoggerUtil.showToastShort(context, specificationsEntity.getMsg());
                    toShowColor();
                    toShowSize();
                } else {
                    LoggerUtil.showToastShort(context, specificationsEntity.getMsg());
                }
            }

            @Override
            public void onFailure(Object object) {
                Toast.makeText(context, R.string.net_exception, Toast.LENGTH_LONG).show();
            }
        }, SpecificationsEntity.class);
    }

    /**
     * 设置尺码的瀑布流格式
     */
    private void toShowSize() {
        for (int i = 0; i < specificationsEntity.getSizes().size(); i++) {
            final TextView tv = new TextView(context);
            tv.setText(specificationsEntity.getSizes().get(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv.setPadding(30, 15, 30, 15);
            tv.setBackgroundResource(R.drawable.bg_common_size_color_layout);
            tvSizeList.add(tv);
            final int finalI = i;
            tvSizeList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvSizeList.get(sizePrevious).setTextColor(getResources().getColor(R.color.color_8a000000));
                    tvSizeList.get(sizePrevious).setBackgroundResource(R.drawable.bg_common_size_color_layout);
                    tvSizeList.get(finalI).setTextColor(getResources().getColor(R.color.color_T_mall_theme));
                    tvSizeList.get(finalI).setBackgroundResource(R.drawable.bg_common_size_color_select_layout);
                    goods_size = tvSizeList.get(finalI).getText().toString().trim();
                    sizePrevious = finalI;
                    isClickSize = true;
                }
            });
            flSelectSizeLayout.addView(tv);
        }
    }

    /**
     * 设置颜色的瀑布流
     */
    private void toShowColor() {
        for (int i = 0; i < specificationsEntity.getColors().size(); i++) {
            final TextView tv = new TextView(context);
            tv.setText(specificationsEntity.getColors().get(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv.setPadding(30, 15, 30, 15);
            tv.setBackgroundResource(R.drawable.bg_common_size_color_layout);
            tvColorList.add(tv);
            final int finalI = i;
            tvColorList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvColorList.get(colorPrevious).setTextColor(getResources().getColor(R.color.color_8a000000));
                    tvColorList.get(colorPrevious).setBackgroundResource(R.drawable.bg_common_size_color_layout);
                    tvColorList.get(finalI).setTextColor(getResources().getColor(R.color.color_T_mall_theme));
                    tvColorList.get(finalI).setBackgroundResource(R.drawable.bg_common_size_color_select_layout);
                    goods_color = tvColorList.get(finalI).getText().toString().trim();
                    colorPrevious = finalI;
                    isClickColor = true;
                }
            });
            flSelectColorLayout.addView(tv);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_bottom_sure, R.id.im_close, R.id.im_goods_image, R.id.tv_check_size, R.id.fl_select_color_layout, R.id.fl_select_size_layout, R.id.tv_minus, R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bottom_sure:
                if (getTag() != null && getTag().equals(Constant.BUY_GOODS)) {
                    if (isClickColor && isClickSize) {
                        JumpActivityUtil.goToConfirmAnOrderActivity(context, goodsInfo, goodsId, goods_color, goods_size);
                        dismiss();
                    } else if (isClickSize) {
                        LoggerUtil.showToastShort(context, "请选择颜色");
                    } else if (isClickColor) {
                        LoggerUtil.showToastShort(context, "请选择尺码");
                    } else if (isClickColor == false && isClickSize == false) {
                        LoggerUtil.showToastShort(context, "请选择颜色和尺码");
                    }
                } else if (getTag() != null && getTag().equals(Constant.ADD_CART)) {
                    if (isClickColor && isClickSize) {
                        addGoodsToCart();
                    } else if (isClickSize) {
                        LoggerUtil.showToastShort(context, "请选择颜色");
                    } else if (isClickColor) {
                        LoggerUtil.showToastShort(context, "请选择尺码");
                    } else if (isClickColor == false && isClickSize == false) {
                        LoggerUtil.showToastShort(context, "请选择颜色和尺码");
                    }
                }
                break;
            case R.id.im_close:
                dismiss();
                break;
            case R.id.im_goods_image:
                break;
            case R.id.tv_check_size:
                break;
            case R.id.fl_select_color_layout:
                break;
            case R.id.fl_select_size_layout:
                break;
            case R.id.tv_minus:
                break;
            case R.id.tv_add:
                break;
        }
    }

    private void addGoodsToCart() {
        RequestCenter.toSetGoodsSpecifications(userId, goodsId, goods_color, goods_size, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                BaseDataEntity entity = new BaseDataEntity();
                if (Integer.valueOf(entity.getStatus()) > 0) {
                    LoggerUtil.showToastShort(context, entity.getMsg());
                } else {
                    LoggerUtil.showToastShort(context, entity.getMsg());
                }
            }

            @Override
            public void onFailure(Object object) {

            }
        }, BaseDataEntity.class);


        RequestCenter.toAddGoodsToCart(userId, goodsId, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                AddressInfo addressInfo = (AddressInfo) object;
                if (Integer.valueOf(addressInfo.getStatus()) > 0) {
                    Toast.makeText(context, addressInfo.getMsg(), Toast.LENGTH_SHORT).show();
                    Event event = new Event();
                    event.setUpDateShoppingCart(true);
                    EventBus.getDefault().post(event);
                    dismiss();
                } else {
                    Toast.makeText(context, addressInfo.getMsg(), Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Object object) {
                Toast.makeText(context, R.string.net_exception, Toast.LENGTH_LONG).show();
            }
        }, AddressInfo.class);
    }
}
