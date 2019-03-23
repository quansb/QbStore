package com.example.quansb.qbstore.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.im_test)
    ImageView imTest;
    @Bind(R.id.im_test2)
    ImageView imTest2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imTest.setOnClickListener(this);
        imTest2.setOnClickListener(this);
       Glide.with(this).load("https://img.alicdn.com/imgextra/i4/446338500/O1CN01eV6KvT2Cf33SiCFao-446338500.jpg").into(imTest2);
        Glide.with(this).load("https://img.alicdn.com/imgextra/i1/446338500/O1CN01ASMA3O2Cf33ToKDpx-446338500.jpg").into(imTest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_test:


                break;
            case R.id.im_test2:
                break;
        }
    }
}
