package com.example.quansb.qbstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.util.JumpActivityUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvBack.setOnClickListener(this);
        tvBack.setText(getString(R.string.setting));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                JumpActivityUtil.goToHomeActivity(this);
                break;
        }
    }
}
