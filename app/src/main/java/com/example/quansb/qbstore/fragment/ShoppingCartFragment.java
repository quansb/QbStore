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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.adapter.ShoppingCartGoodsAdapter;
import com.example.quansb.qbstore.base.BaseFragment;
import com.example.quansb.qbstore.entity.Event;
import com.example.quansb.qbstore.entity.GoodsEntity;
import com.example.quansb.qbstore.entity.ShoppingCartGoods;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.okhttp.listener.DisposeDataListener;
import com.mysdk.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.lv_list_view)
    ListView lvListView;
    @Bind(R.id.im_goods_selected_all)
    ImageView imGoodsSelectedAll;
    @Bind(R.id.tv_money_sum)
    TextView tvMoneySum;
    @Bind(R.id.tv_goods_sum)
    TextView tvGoodsSum;
    @Bind(R.id.ll_goods_sum_layout)
    LinearLayout llGoodsSumLayout;
    private ArrayList<GoodsEntity> CartGoodsArrayList = new ArrayList<>();
    private Context context;
    private ShoppingCartGoods cartGoods;
    private int sum = 0;      //商品总数
    private int pricesSum = 0;    //总价格
    private boolean status = true;
    private StringBuilder stringBuilder = new StringBuilder();   //所有商品id
    private String totalGoodsId;      //所有商品id
    ShoppingCartGoodsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_shopping_cart_layout, container, false);
        context = getContext();
        ButterKnife.bind(this, view);
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.color_000000));
        imGoodsSelectedAll.setOnClickListener(this);
        tvMoneySum.setOnClickListener(this);
        tvGoodsSum.setOnClickListener(this);
        llGoodsSumLayout.setOnClickListener(this);
        adapter = new ShoppingCartGoodsAdapter(context);
        lvListView.setAdapter(adapter);
        initShoppingCartGoods();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.color_000000));
        }
    }

    /**
     * 发送网络请求 获取购物车信息
     */
    private void initShoppingCartGoods() {
        adapter.clearData();
        adapter.setOnClickListener(new ShoppingCartGoodsAdapter.OnClickListener() {
            @Override
            public void onClick(GoodsEntity goodsEntity) {

                if (goodsEntity.getIs_choose().equals("1")) {
                    sum = sum + 1;
                    pricesSum = pricesSum + Integer.valueOf(goodsEntity.getGoods_price());
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append("_").append(goodsEntity.getGoods_id());
                    } else {
                        stringBuilder.append(goodsEntity.getGoods_id());
                    }
                } else if (goodsEntity.getIs_choose().equals("0")) {
                    sum = sum - 1;
                    pricesSum = pricesSum - Integer.valueOf(goodsEntity.getGoods_price());
                    stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                }
                tvMoneySum.setText("￥" + pricesSum);
                tvGoodsSum.setText("(" + sum + ")");
                adapter.notifyDataSetChanged();
                totalGoodsId = stringBuilder.toString();

                if (sum == 0) {
                    imGoodsSelectedAll.setImageResource(R.drawable.ic_round1);
                    status = false;
                } else if (sum == cartGoods.getGoodsEntities().size()) {
                    imGoodsSelectedAll.setImageResource(R.drawable.ic_round_red2);
                    status = true;
                } else if (sum == cartGoods.getGoodsEntities().size() - 1) {
                    imGoodsSelectedAll.setImageResource(R.drawable.ic_round1);
                    status = false;
                }

            }
        });
        PreferencesHelp preferencesHelp = new PreferencesHelp(context);
        RequestCenter.toGetShoppingCartData(preferencesHelp.getUserID(), new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {

                cartGoods = (ShoppingCartGoods) object;
                if (Integer.valueOf(cartGoods.getStatus()) > 0) {
                    upDateShoppingCartData();
                    Toast.makeText(context, R.string.loading, Toast.LENGTH_SHORT).show();
                } else {
                    //   Logger.showToastShort(cartGoods.getMsg());
                }
            }

            @Override
            public void onFailure(Object object) {
                Toast.makeText(context, R.string.net_exception, Toast.LENGTH_LONG).show();
            }
        }, ShoppingCartGoods.class);

    }

    /**
     * 获取到购物车信息
     */
    private void upDateShoppingCartData() {
        if (cartGoods.getGoodsEntities() == null || cartGoods.getGoodsEntities().size() == 0) {
            return;
        }
        CartGoodsArrayList.addAll(cartGoods.getGoodsEntities());
        adapter.setData(CartGoodsArrayList);
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEvent(Event event) {
        if (event.isUpDateShoppingCart()) {
            initShoppingCartGoods();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.im_goods_selected_all:
                upDateImageColor();
                break;
            case R.id.ll_goods_sum_layout:
                if (!totalGoodsId.equals("")) {
                    JumpActivityUtil.goToConfirmAnOrderActivity(context, totalGoodsId);
                } else {
                    Toast.makeText(context, "请选择要购买的商品", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_goods_sum:
                break;

        }
    }

    private void upDateImageColor() {
        if (status) {
            imGoodsSelectedAll.setImageResource(R.drawable.ic_round_red2);
            status = false;
            if (cartGoods.getGoodsEntities() == null || cartGoods.getGoodsEntities().size() == 0) {

            } else {
                pricesSum = 0;
                stringBuilder.delete(0, stringBuilder.length());
                for (int i = 0; i < cartGoods.getGoodsEntities().size(); i++) {
                    cartGoods.getGoodsEntities().get(i).setIs_choose("1");
                    pricesSum += Integer.valueOf(cartGoods.getGoodsEntities().get(i).getGoods_price());
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append("_").append(cartGoods.getGoodsEntities().get(i).getGoods_id());
                    } else {
                        stringBuilder.append(cartGoods.getGoodsEntities().get(i).getGoods_id());
                    }
                }
                sum = cartGoods.getGoodsEntities().size();
                tvMoneySum.setText("￥" + pricesSum);
                tvGoodsSum.setText("(" + sum + ")");
                totalGoodsId = stringBuilder.toString();
                adapter.notifyDataSetChanged();
            }
        } else {
            imGoodsSelectedAll.setImageResource(R.drawable.ic_round1);
            if (cartGoods.getGoodsEntities() == null || cartGoods.getGoodsEntities().size() == 0) {

            } else {
                for (int i = 0; i < cartGoods.getGoodsEntities().size(); i++) {
                    cartGoods.getGoodsEntities().get(i).setIs_choose("0");
                }
                pricesSum = 0;
                sum = 0;
                stringBuilder.delete(0, stringBuilder.length());
                totalGoodsId = stringBuilder.toString();
                tvMoneySum.setText("￥" + pricesSum);
                tvGoodsSum.setText("(" + sum + ")");
                adapter.notifyDataSetChanged();
            }
            status = true;
        }
    }


}


