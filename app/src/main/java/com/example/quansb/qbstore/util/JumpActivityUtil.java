package com.example.quansb.qbstore.util;

import android.content.Context;
import android.content.Intent;

import com.example.quansb.qbstore.activity.HomeActivity;
import com.example.quansb.qbstore.activity.LoginActivity;
import com.example.quansb.qbstore.activity.OrderActivity;
import com.example.quansb.qbstore.activity.RegisterActivity;
import com.example.quansb.qbstore.activity.SettingActivity;

/**
 * 跳转工具类
 */
public class JumpActivityUtil {

    public static void goToLoginActivity (Context context ){
        Intent intent=new Intent(context, LoginActivity.class);     // 跳转到登录界面
        context.startActivity(intent);
    }

    public static void goToOrderActivity (Context context  ){       // 跳转到全部订单界面
        Intent intent=new Intent(context, OrderActivity.class);
        context.startActivity(intent);
    }
    public static void goToRegisterActivity (Context context  ){
        Intent intent=new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
    public static void goToSettingActivity (Context context  ){
        Intent intent=new Intent(context, SettingActivity.class);       // 跳转到设置界面
        context.startActivity(intent);
    }

    public static void goToHomeActivity (Context context  ){
        Intent intent=new Intent(context, HomeActivity.class);
        intent.putExtra("id",1);
        context.startActivity(intent);
    }



}
