package com.example.quansb.qbstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.ll_login_bt_layout)
    LinearLayout llLoginBtLayout;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ivBack.setOnClickListener(this);
        etAccount.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        llLoginBtLayout.setOnClickListener(this);
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
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                break;
            case R.id.ll_login_bt_layout:
                login();

                break;

        }

    }

    private void login() {
        String userName=etAccount.getText().toString().trim();
        String pwd=etPassword.getText().toString().trim();
    }
}
