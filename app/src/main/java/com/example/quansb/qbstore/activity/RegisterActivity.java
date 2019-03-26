package com.example.quansb.qbstore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.entity.UserInfo;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.view.CommonDialog;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.Logger;
import com.mysdk.okhttp.listener.DisposeDataListener;
import com.mysdk.util.StringUtils;
import com.mysdk.view.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private UserInfo userInfo;
    @Bind(R.id.circle_img)
    CircleImageView circleImg;
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
                finish();
                break;
            case R.id.tv_login_register:
                register();
                break;
        }
    }
    private void showMyDialog() {
        final CommonDialog commonDialog=new CommonDialog();
        commonDialog.ComDialog(RegisterActivity.this,getString(R.string.register_success),getString(R.string.confirm),getString(R.string.cancel));
        commonDialog.setOnDialogClikeListener(new CommonDialog.OnDialogClickListener() {
            @Override
            public void onConfirm() {
                JumpActivityUtil.goToLoginActivity(RegisterActivity.this);
            }
            @Override
            public void onCancel() {
            }
        });
    }


    private void updateUI() {
        showMyDialog();
    }



    public void register() {
        String userName = etAccount.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(pwd)) {
            Logger.showToastShort(getString(R.string.input_canno_null));
            return;
        }
        RequestCenter.toRegister(this, userName, "123", pwd, new DisposeDataListener() {
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
