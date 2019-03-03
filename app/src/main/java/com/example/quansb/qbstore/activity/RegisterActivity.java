package com.example.quansb.qbstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.tv_login_register)
    TextView tvLoginRegister;


    @Override
    protected void initData() {

        tvRegister.setVisibility(tvRegister.GONE);
        tvLoginRegister.setText(R.string.register);

    }

    @Override
    protected void initView() {
        tvLoginRegister.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        etAccount.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_login_bt_layout:
                break;

        }
    }
}
