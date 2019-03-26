package com.example.quansb.qbstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseFragment;
import com.example.quansb.qbstore.entity.UserInfo;
import com.example.quansb.qbstore.util.Help;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.glide.GlideUtil;
import com.mysdk.view.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.tv_deliver_goods)
    TextView tvDeliverGoods;
    @Bind(R.id.tv_take_back_goods)
    TextView tvTakeBackGoods;
    @Bind(R.id.tv_evaluate)
    TextView tvEvaluate;
    @Bind(R.id.tv_after_sales)
    TextView tvAfterSales;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.iv_setting)
    ImageView ivSetting;
    @Bind(R.id.tv_order)
    TextView tvOrder;
    @Bind(R.id.tv_all_order)
    TextView tvAllOrder;

    @Bind(R.id.rl_layout)
    RelativeLayout rlLayout;
    @Bind(R.id.ll_layout)
    LinearLayout llLayout;
    @Bind(R.id.fragment_mine)
    RelativeLayout fragmentMine;
    @Bind(R.id.civ_head)
    CircleImageView civHead;

    private Context mContext;
    private Boolean loginStatus; //登录状态

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mine_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initListener();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();

    }

    private void initListener() {
        tvLogin.setOnClickListener(this);
        llLogin.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        tvAllOrder.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        tvDeliverGoods.setOnClickListener(this);
        tvTakeBackGoods.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
        tvAfterSales.setOnClickListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        loginStatus = Help.isLogin(mContext);  //是否登录
        if (!loginStatus) {
            JumpActivityUtil.goToLoginActivity(mContext); // 跳转到登录界面
            return;
        }
        switch (v.getId()) {
//                case R.id.ll_login:
//                    JumpActivityUtil.goToLoginActivity(mContext); // 跳转到登录界面
//                    break;
//                case R.id.tv_login:
//                    JumpActivityUtil.goToLoginActivity(mContext);// 跳转到登录界面
//                    break;
            case R.id.iv_setting:
                JumpActivityUtil.goToSettingActivity(mContext);// 跳转到设置界面
                break;
            case R.id.tv_all_order:
                JumpActivityUtil.goToOrderActivity(mContext);// 跳转到全部订单界面
                break;
            case R.id.tv_order:
                JumpActivityUtil.goToOrderActivity(mContext);// 跳转到全部订单界面
                break;
            case R.id.tv_pay:
                JumpActivityUtil.goToPaymentActivity(mContext);// 跳转到支付界面
                break;
            case R.id.tv_deliver_goods:
                JumpActivityUtil.goToDeliverGoodsActivity(mContext);// 跳转到待发货界面
                break;
            case R.id.tv_take_back_goods:
                JumpActivityUtil.goToTakeBackGoodsActivity(mContext);// 跳转到待收货界面
                break;
            case R.id.tv_evaluate:
                JumpActivityUtil.goToEvaluationActivity(mContext);// 跳转到待评价界面
                break;
            case R.id.tv_after_sales:
                JumpActivityUtil.goToAfterSalesActivity(mContext);// 跳转到售后界面
                break;
        }
    }

    public void refresh() {
        loginStatus = Help.isLogin(mContext);
        if (!loginStatus) {
            tvLogin.setText(R.string.login);
            civHead.setImageResource(R.drawable.ic_login);
            return;
        }
        PreferencesHelp preferencesHelp = new PreferencesHelp(getContext());
        UserInfo userInfo = (UserInfo) preferencesHelp.getObject("userinfo", UserInfo.class);
        GlideUtil.loadImageCircle(mContext, userInfo.getAvatar_img(), civHead, 50);
        tvLogin.setText(userInfo.getUser_name());
    }
}
