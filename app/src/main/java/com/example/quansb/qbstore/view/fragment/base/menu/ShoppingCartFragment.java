package com.example.quansb.qbstore.view.fragment.base.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.view.fragment.base.BaseFragment;

public class ShoppingCartFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_shopping_cart,container,false);
        return view;
    }
}


