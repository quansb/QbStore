package com.example.quansb.qbstore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.util.JumpActivityUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvBack.setOnClickListener(this);
        tvBack.setText(R.string.about);
        tvCommonCentre.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                JumpActivityUtil.goToSettingActivity(this);
                break;
        }
    }
}
