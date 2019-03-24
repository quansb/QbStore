package com.example.quansb.qbstore.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.entity.UserInfo;
import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.CommonDialog;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.Logger;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.mysdk.okhttp.listener.DisposeDataListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;
    @Bind(R.id.tv_personal_information)
    TextView tvPersonalInformation;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_about)
    TextView tvAbout;

    private TextView tvLogout;

    @Override
    protected void initData() {
        tvLogout = findViewById(R.id.tv_logout);
    }

    @Override
    protected void initView() {
        tvBack.setText(getString(R.string.setting));
        tvCommonCentre.setVisibility(View.GONE);
        tvBack.setOnClickListener(this);
        tvPersonalInformation.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.tv_back:
                JumpActivityUtil.goToHomeActivity(this);
                break;

            case R.id.tv_personal_information:
                JumpActivityUtil.goToPersonalInformationActivity(this);
                break;

            case R.id.tv_logout:
                showMyDialog();
                break;
            case R.id.tv_address:
                JumpActivityUtil.goToAddressManagementActivity(this);
                break;
            case R.id.tv_about:
                JumpActivityUtil.goToAboutActivity(this);
                break;


        }
    }

    private void showMyDialog() {
        final CommonDialog commonDialog=new CommonDialog();
        commonDialog.ComDialog(SettingActivity.this,getString(R.string.loginout),getString(R.string.confirm),getString(R.string.cancel));
        commonDialog.setOnDialogClikeListener(new CommonDialog.OnDialogClikeListener() {
            @Override
            public void onConfirm() {
                loginOut();
            }
            @Override
            public void onCancel() {

            }
        });
    }


    private void loginOut() {

        RequestCenter.toLoginOut("cwl", new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                UserInfo userInfo = (UserInfo) object;
                Log.i(TAG, userInfo.getUser_name() + "  " + userInfo.getAvatar_img() + "");
                if (Integer.valueOf(userInfo.getStatus()) > 0) {
                    PreferencesHelp preferencesHelp = new PreferencesHelp(SettingActivity.this);
                    Boolean bool = new Boolean(false);
                    preferencesHelp.put("isLogin", bool);
                    updateUI();
                } else {
                    Logger.showToastShort(userInfo.getMsg());
                }
            }
            @Override
            public void onFailure(Object object) {

            }
        }, UserInfo.class);
    }

    private void updateUI() {
                JumpActivityUtil.goToLoginActivity(SettingActivity.this);
    }

}
