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
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseFragment;
import com.example.quansb.qbstore.util.JumpActivityUtil;

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
    private Context mContext;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_login:
                JumpActivityUtil.goToLoginActivity(mContext); // 跳转到登录界面
                break;
            case R.id.tv_login:
                JumpActivityUtil.goToLoginActivity(mContext);// 跳转到登录界面
                break;

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
                JumpActivityUtil.goToPaymentActivity(mContext);
                break;
            case R.id.tv_deliver_goods:
                JumpActivityUtil.goToDeliverGoodsActivity(mContext);
                break;
            case R.id.tv_take_back_goods:
                JumpActivityUtil.goToTakeBackGoodsActivity(mContext);
                break;
            case R.id.tv_evaluate:
                JumpActivityUtil.goToEvaluationActivity(mContext);
                break;
            case R.id.tv_after_sales:
                JumpActivityUtil.goToAfterSalesActivity(mContext);
                break;
        }

    }
}
