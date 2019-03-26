package com.example.quansb.qbstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.util.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AgeChangeActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;
    @Bind(R.id.tv_common_right)
    TextView tvCommonRight;
    @Bind(R.id.v_common_line)
    View vCommonLine;
    @Bind(R.id.et_age)
    EditText etAge;
    @Bind(R.id.common_top_layout)
    RelativeLayout commonTopLayout;
    private String age;

    @Override
    protected void initData() {

        if (getIntent().hasExtra("age")) {
            age=getIntent().getStringExtra("age");
        }
    }

    @Override
    protected void initView() {
        tvBack.setOnClickListener(this);
        tvBack.setText("");
        tvCommonCentre.setText(R.string.change_age);
        vCommonLine.setVisibility(View.GONE);
        tvCommonRight.setText(R.string.sure);
        tvCommonRight.setVisibility(View.VISIBLE);
        etAge.setHint(R.string.input_age);
        tvCommonRight.setOnClickListener(this);
        commonTopLayout.setBackgroundResource(R.color.color_white);

        if(age!=null){
            etAge.setText(age);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_age_change_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_common_right:     //确定修改年龄
                String age=etAge.getText().toString().trim();
                if (age.equals("0")|age.equals("00")|age.isEmpty()){
                    Toast.makeText(this,"请输入1-99的年龄",Toast.LENGTH_SHORT).show();
                    return;
                }
                setResult(Constant.AGE_RESULT_CODE,new Intent().putExtra("age",age));
                finish();
                break;
        }
    }
}
