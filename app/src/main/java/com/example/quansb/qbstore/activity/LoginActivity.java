package com.example.quansb.qbstore.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.entity.UserInfo;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.Logger;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.okhttp.listener.DisposeDataListener;
import com.mysdk.util.StringUtils;
import com.mysdk.view.CircleImageView;

import java.net.URL;

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
    @Bind(R.id.circle_img)
    CircleImageView circleImg;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    private UserInfo userInfo;

    @Override
    protected void initData() {


    }

    private void updateUI() {

        PreferencesHelp preferencesHelp = new PreferencesHelp(this);
        Boolean bool = new Boolean(true);
        preferencesHelp.put("isLogin", bool);
        preferencesHelp.putObject("userinfo",userInfo);
        JumpActivityUtil.goToHomeActivity(LoginActivity.this);
    }

    @Override
    protected void initView() {

        ivBack.setOnClickListener(this);
        etAccount.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        llLoginBtLayout.setOnClickListener(this);
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
                finish();
                break;
            case R.id.ll_login_bt_layout:
                login();
                break;
            case R.id.tv_register:
                JumpActivityUtil.goToRegisterActivity(this);
                break;
        }

    }

    private void login() {
        String userName = etAccount.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(pwd)) {
            Logger.showToastShort(getString(R.string.input_canno_null));
            return;
        }
        RequestCenter.toLogin(this, userName, pwd, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                userInfo = (UserInfo) object;

                if (Integer.valueOf(userInfo.getStatus()) > 0) {
                    updateUI();
                } else {
                    Logger.showToastShort(userInfo.getMsg());
                }
            }

            @Override
            public void onFailure(Object object) {
                Logger.showToastShort(getString(R.string.network_no_data));
            }
        }, UserInfo.class);
    }


}
