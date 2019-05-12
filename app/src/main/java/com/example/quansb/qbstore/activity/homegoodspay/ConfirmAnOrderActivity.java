package com.example.quansb.qbstore.activity.homegoodspay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.adapter.ConfirmOrderAdapter;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.entity.AddressEntity;
import com.example.quansb.qbstore.entity.ConfirmOrderEntity;
import com.example.quansb.qbstore.entity.GoodsEntity;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.Constant;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.example.quansb.qbstore.view.PassWordDialog;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.listener.DisposeDataListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfirmAnOrderActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;
    @Bind(R.id.tv_common_right)
    TextView tvCommonRight;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.lv_list_view)
    ListView lvListView;
    @Bind(R.id.tv_submit_orders)
    TextView tvSubmitOrders;
    @Bind(R.id.tv_goods_sum)
    TextView tvGoodsSum;
    @Bind(R.id.tv_money_sum)
    TextView tvMoneySum;
    @Bind(R.id.ll_select_address)
    LinearLayout llSelectAddress;
    private Context context = this;
    private String goodsID;   //    支付结算商品id
    private String totalGoodsId; //  购物车商品所有id
    private String user_id;
    private ConfirmOrderAdapter adapter;
    private Activity activity=this;

    @Override
    protected void initData() {
        adapter = new ConfirmOrderAdapter(context);
        lvListView.setAdapter(adapter);
        PreferencesHelp help = new PreferencesHelp(context);
        user_id = help.getUserID();
        tvBack.setOnClickListener(this);
        tvSubmitOrders.setOnClickListener(this);
        llSelectAddress.setOnClickListener(this);
        tvBack.setText("确认订单");
        tvCommonCentre.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void initView() {

        if (getIntent().getStringExtra("num").equals("1")) {
            totalGoodsId = getIntent().getStringExtra("cart_goods_id");
            goodsID = totalGoodsId;
            RequestCenter.toGetConfirmOrderFromCart(user_id, totalGoodsId, "2", new DisposeDataListener() {
                @Override
                public void onSuccess(Object object) {
                    ConfirmOrderEntity confirmOrderEntity = (ConfirmOrderEntity) object;
                    if (Integer.valueOf(confirmOrderEntity.getStatus()) > 0) {
                        if (confirmOrderEntity.getAddressEntity() != null) {
                            toShowAddress(confirmOrderEntity.getAddressEntity());
                        } else {
                            toShowDefaultAddress();
                        }
                        toShowGoodsFromCart(confirmOrderEntity.getGoodsEntities());
                        toShowTotalPrice(confirmOrderEntity.getTotalPrice());
                    } else {
                        LoggerUtil.showToastShort(context, confirmOrderEntity.getMsg());
                    }
                }

                @Override
                public void onFailure(Object object) {

                }
            }, ConfirmOrderEntity.class);

        } else if (getIntent().getStringExtra("num").equals("0")) {
            String goodsId = getIntent().getStringExtra("goodsId");
            String color = getIntent().getStringExtra("color");
            String size = getIntent().getStringExtra("size");
            goodsID = goodsId;
            RequestCenter.toGetOneConfirmOrder(user_id, goodsId, "1", size, color, new DisposeDataListener() {
                @Override
                public void onSuccess(Object object) {
                    ConfirmOrderEntity confirmOrderEntity = (ConfirmOrderEntity) object;
                    if (Integer.valueOf(confirmOrderEntity.getStatus()) > 0) {
                        if (confirmOrderEntity.getAddressEntity() != null) {
                            toShowAddress(confirmOrderEntity.getAddressEntity());
                        } else {
                            toShowDefaultAddress();
                        }
                        toShowGoodsFromCart(confirmOrderEntity.getGoodsEntities());
                        toShowTotalPrice(confirmOrderEntity.getTotalPrice());
                    } else {
                        LoggerUtil.showToastShort(context, confirmOrderEntity.getMsg());
                    }
                }

                @Override
                public void onFailure(Object object) {

                }
            }, ConfirmOrderEntity.class);
        }
    }

    private void toShowDefaultAddress() {
        tvName.setText("请设置收件人");
        tvAddress.setText("请设置收货地址");
        tvPhone.setText("请设置手机号");
    }

    private void toShowTotalPrice(String totalPrice) {
        tvMoneySum.setText(totalPrice);
    }

    private void toShowGoodsFromCart(ArrayList<GoodsEntity> goodsEntities) {
        adapter.setGoodsList(goodsEntities);
        adapter.notifyDataSetChanged();
        tvGoodsSum.setText("共" + goodsEntities.size() + "件,总金额");
    }


    private void toShowAddress(AddressEntity addressEntity) {
        tvName.setText(addressEntity.getConsignee());
        tvAddress.setText(addressEntity.getAddress());
        tvPhone.setText(addressEntity.getPhone());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_submit_orders:
                PreferencesHelp help = new PreferencesHelp(context);
                String payPassWord = help.getUserHasPwd();
                PassWordDialog dialog = new PassWordDialog();
                dialog.setmContext(context);
                dialog.setAllPrice(tvMoneySum.getText() + "");
                dialog.setGoodsID(goodsID);
                dialog.setActivity(activity);
                if (payPassWord.equals("1")) {
                    dialog.show(getSupportFragmentManager(), "has_pwd");  //有支付密码直接输入支付密码
                } else {
                    dialog.show(getSupportFragmentManager(), "set_pwd");  //设置支付密码
                }
                break;

            case R.id.ll_select_address:
                JumpActivityUtil.goToSelectAddressActivity(this);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String name = data.getStringExtra("name");
            String address = data.getStringExtra("address");
            String phone = data.getStringExtra("phone");
            if (requestCode == Constant.ADDRESS_REQUEST_CODE && resultCode == 1) {
                tvAddress.setText(address);
                tvName.setText(name);
                tvPhone.setText(phone);
            }
        }
    }
}
