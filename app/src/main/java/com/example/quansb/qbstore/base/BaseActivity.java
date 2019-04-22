package com.example.quansb.qbstore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.mysdk.util.StatusBarUtil;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "info1";
    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initData();
        initView();

    }




    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}


