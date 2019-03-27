package com.example.quansb.qbstore.activity;

import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.util.Constant;

import butterknife.Bind;

public class NicknameActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;
    @Bind(R.id.tv_common_right)   //公共top最右边的一个确定按钮
    TextView tvCommonRight;
    @Bind(R.id.v_common_line)
    View vCommonLine;
    @Bind(R.id.common_top_layout)
    RelativeLayout commonTopLayout;
    @Bind(R.id.et_nick_name)
    EditText etNickName;
    public static final int NICKNAME = 10;//    用户昵称允许输入的最大长度
    private String nickName;

    @Override
    protected void initData() {
         if (getIntent().hasExtra("nick_name")){
             nickName=getIntent().getStringExtra("nick_name");
         }
    }

    @Override
    protected void initView() {
        tvBack.setOnClickListener(this);
        tvBack.setText("");
        tvCommonCentre.setText(R.string.change_nickname);
        tvCommonRight.setVisibility(View.VISIBLE);
        vCommonLine.setVisibility(View.GONE);
        commonTopLayout.setBackgroundResource(R.color.color_white);
        nickNameLength();
        tvCommonRight.setOnClickListener(this);
        if(nickName!=null){
        etNickName.setText(nickName);
        }
    }

    private void   nickNameLength(){


        etNickName.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            if ( !Character.isLetterOrDigit(source.charAt(i))
                                    && !Character.toString(source.charAt(i)) .equals("_")
                                    && !Character.toString(source.charAt(i)) .equals("-"))
                            {
                                return "";
                            }
                        }
                        return null;
                    }
                },new InputFilter.LengthFilter(NICKNAME)
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_nickname_layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:   //返回
                finish();
                break;
            case R.id.tv_common_right:     //确定修改
                String nickName=etNickName.getText().toString().trim();
                if (nickName.length()<4){
                    Toast.makeText(this,"请输入4个或4个字符以上的用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                //返回 信息 设置结果码
               setResult(Constant.NICK_NAME_RESULT_CODE,new Intent().putExtra("nick_name",nickName));
                finish();
                break;
        }
    }
}
