package com.example.quansb.qbstore.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quansb.qbstore.R;

import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.fragment.HomeFragment;
import com.example.quansb.qbstore.fragment.MineFragment;
import com.example.quansb.qbstore.fragment.ShoppingCartFragment;
import com.mysdk.util.StatusBarUtil;

import butterknife.Bind;


/**
 * app的主要activity，关联3个frament。
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private Drawable drawable = null;
    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private boolean loginStatus;
    private long exitTime=0;

    @Bind(R.id.tv_home)
    TextView tvHome;
    @Bind(R.id.tv_shopping_cart)
    TextView tvShoppingCart;
    @Bind(R.id.tv_mine)
    TextView tvMine;

    private int index = 0;//上次被选中的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int id=getIntent().getIntExtra("id",0);
//        if(id==1){
//        FragmentTransaction transaction=fragmentManager.beginTransaction();
//            transaction.show(mineFragment);
//            transaction.commit();
//        }
        StatusBarUtil.setTransparent(this);

    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        homeFragment=new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_layout,homeFragment);
        transaction.commit();
    }

    private void hideFragment(Fragment fragment, FragmentTransaction transaction) {

        if(fragment!=null){
        transaction.hide(fragment);
        }
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
        return R.layout.activity_home_layout;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        switch (v.getId()) {
            case R.id.tv_home:
                setSelectPage(0);
                hideFragment(shoppingCartFragment,fragmentTransaction);
                hideFragment(mineFragment,fragmentTransaction);
                if(homeFragment==null){
                    homeFragment=new HomeFragment();
                    fragmentTransaction.add(R.id.fl_layout,homeFragment);
                }
                else {
                    fragmentTransaction.show(homeFragment);
                }
                break;
            case R.id.tv_shopping_cart:
                setSelectPage(1);
                hideFragment(homeFragment,fragmentTransaction);
                hideFragment(mineFragment,fragmentTransaction);
                if(shoppingCartFragment==null){
                    shoppingCartFragment=new ShoppingCartFragment();
                    fragmentTransaction.add(R.id.fl_layout,shoppingCartFragment);
                }
                else {
                    fragmentTransaction.show(shoppingCartFragment);
                }

                break;
            case R.id.tv_mine:
                setSelectPage(2);

        hideFragment(homeFragment,fragmentTransaction);
        hideFragment(shoppingCartFragment,fragmentTransaction);
                if(mineFragment==null){
                    mineFragment=new MineFragment();
                    fragmentTransaction.add(R.id.fl_layout,mineFragment);
                }
                else {
                    fragmentTransaction.show(mineFragment);
                }

                break;
        }
        fragmentTransaction.commit();
    }

    private void setSelectPage(int i) {
        if (index == 0) {
            drawable = getResources().getDrawable(R.drawable.ic_home);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvHome.setCompoundDrawables(null, drawable, null, null);
        } else if (index == 1) {
            drawable = getResources().getDrawable(R.drawable.ic_shopping);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvShoppingCart.setCompoundDrawables(null, drawable, null, null);
        } else if (index == 2) {
            drawable = getResources().getDrawable(R.drawable.ic_mine);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvMine.setCompoundDrawables(null, drawable, null, null);
        }
        if (i == 0) {
            drawable = getResources().getDrawable(R.drawable.ic_home_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvHome.setCompoundDrawables(null, drawable, null, null);
            index = 0;
        } else if (i == 1) {
            drawable = getResources().getDrawable(R.drawable.ic_shopping_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvShoppingCart.setCompoundDrawables(null, drawable, null, null);
            index = 1;
        } else if (i == 2) {
            drawable = getResources().getDrawable(R.drawable.ic_mine_select);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //直角坐标系第四象限放图片
            tvMine.setCompoundDrawables(null, drawable, null, null);
            index = 2;
        }
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }







}

