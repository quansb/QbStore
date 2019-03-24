package com.example.quansb.qbstore.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.util.CommonDialog;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.mysdk.view.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalInformationActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;
    @Bind(R.id.common_top_layout)
    RelativeLayout commonTopLayout;
    @Bind(R.id.civ_head)
    CircleImageView civHead;
    @Bind(R.id.rl_head_img_layout)
    RelativeLayout rlHeadImgLayout;
    @Bind(R.id.rl_user_id_layout)
    RelativeLayout rlUserIdLayout;
    @Bind(R.id.rl_user_name_layout)
    RelativeLayout rlUserNameLayout;
    @Bind(R.id.rl_sex_layout)
    RelativeLayout rlSexLayout;
    @Bind(R.id.rl_age_layout)
    RelativeLayout rlAgeLayout;
    @Bind(R.id.rl_wallet_layout)
    RelativeLayout rlWalletLayout;

    private int  choice;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvBack.setOnClickListener(this);
        tvBack.setText("");
        tvCommonCentre.setText(R.string.personal_information);
        civHead.setOnClickListener(this);
        rlAgeLayout.setOnClickListener(this);
        rlHeadImgLayout.setOnClickListener(this);
        rlSexLayout.setOnClickListener(this);
        rlWalletLayout.setOnClickListener(this);
        rlUserIdLayout.setOnClickListener(this);
        rlUserNameLayout.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_information_layout;
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
                JumpActivityUtil.goToSettingActivity(this);
                break;
            case R.id.rl_user_id_layout:
                Toast.makeText(this,"用户名不可以修改呦！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_user_name_layout:
                break;
            case R.id.rl_sex_layout:

                showSingDialog();

                break;
            case R.id.rl_age_layout:
                break;
            case R.id.rl_wallet_layout:
                break;
            case R.id.rl_head_img_layout:
                break;
        }
    }
        private void showSingDialog(){
            final String[] items = {"男","女","保密"};
            AlertDialog.Builder Dialog = new AlertDialog.Builder(PersonalInformationActivity.this);
          //  singleChoiceDialog.setIcon(R.drawable.icon);
            Dialog.setTitle(R.string.change_sex);
            //第二个参数是默认的选项
            Dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    choice= which;
                }
            });
            Dialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (choice!=-1){
                        Toast.makeText(PersonalInformationActivity.this,
                                "你选择了" + items[choice],
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    
                }
            });

            Dialog.show();
        }
}
