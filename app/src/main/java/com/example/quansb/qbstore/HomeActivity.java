package com.example.quansb.qbstore;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quansb.qbstore.base.BaseActivity;

import butterknife.Bind;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private Drawable drawable = null;
    private int status=0x11;
    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.tv_shopping_cart)
    TextView tvShoppingCart;
    @Bind(R.id.tv_mine)
    TextView tvMine;

    private int index=0;//上次被选中的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        tvHome.setOnClickListener(this);
        tvMine.setOnClickListener(this);
        tvShoppingCart.setOnClickListener(this);
        setSelectPage(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.tv_home:
                setSelectPage(0);
                break;
            case R.id.tv_shopping_cart:
                setSelectPage(1);
                break;
            case R.id.tv_mine:
                setSelectPage(2);
                break;
        }
    }

    private void setSelectPage(int i) {
        if (index==0){
            drawable = getResources().getDrawable(R.drawable.ic_home);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvHome.setCompoundDrawables(null, drawable, null, null);
        }else if (index==1){
            drawable = getResources().getDrawable(R.drawable.ic_shopping);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvShoppingCart.setCompoundDrawables(null, drawable, null, null);
        }else if (index==2){
            drawable = getResources().getDrawable(R.drawable.ic_mine);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvMine.setCompoundDrawables(null, drawable, null, null);
        }
        if (i==0){
            drawable = getResources().getDrawable(R.drawable.ic_home_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvHome.setCompoundDrawables(null, drawable, null, null);
            index=0;
        }else if (i==1){
            drawable = getResources().getDrawable(R.drawable.ic_shopping_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvShoppingCart.setCompoundDrawables(null, drawable, null, null);
            index=1;
        }else if (i==2){
            drawable = getResources().getDrawable(R.drawable.ic_mine_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvMine.setCompoundDrawables(null, drawable, null, null);
            index=2;
        }
    }
}
