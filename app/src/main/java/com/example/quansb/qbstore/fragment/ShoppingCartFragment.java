package com.example.quansb.qbstore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.adapter.ShoppingCartGoodsAdapter;
import com.example.quansb.qbstore.base.BaseFragment;
import com.example.quansb.qbstore.entity.ShoppingCartGoods;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ShoppingCartFragment extends BaseFragment {

    @Bind(R.id.lv_list_view)
    ListView lvListView;
    private ArrayList<ShoppingCartGoods> shoppingCartGoodsArrayList = new ArrayList<>();
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_shopping_cart_layout, container, false);
        context=getContext();
        ButterKnife.bind(this, view);
        initShoppingCartGoods();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    private void initShoppingCartGoods() {

        ShoppingCartGoods goods = new ShoppingCartGoods(R.drawable.kuangwei, R.string.des + "", "699", "黄色", "L");

        for (int i = 0; i < 10; i++) {
            shoppingCartGoodsArrayList.add(goods);
        }
        ShoppingCartGoodsAdapter adapter=new ShoppingCartGoodsAdapter(context,shoppingCartGoodsArrayList);
        lvListView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}


