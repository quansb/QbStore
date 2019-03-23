package com.example.quansb.qbstore.util;

import android.content.Context;
import android.content.Intent;

import com.example.quansb.qbstore.activity.AfterSalesActivity;
import com.example.quansb.qbstore.activity.DeliverGoodsActivity;
import com.example.quansb.qbstore.activity.EvaluationActivity;
import com.example.quansb.qbstore.activity.HomeActivity;
import com.example.quansb.qbstore.activity.LoginActivity;
import com.example.quansb.qbstore.activity.OrderActivity;
import com.example.quansb.qbstore.activity.PaymentActivity;
import com.example.quansb.qbstore.activity.PersonalInformationActivity;
import com.example.quansb.qbstore.activity.RegisterActivity;
import com.example.quansb.qbstore.activity.SettingActivity;
import com.example.quansb.qbstore.activity.TakeBackGoodsActivity;

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
    public static void goToRegisterActivity (Context context  ){           // 跳转到注册界面
        Intent intent=new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
    public static void goToSettingActivity (Context context  ){
        Intent intent=new Intent(context, SettingActivity.class);       // 跳转到设置界面
        context.startActivity(intent);
    }

    public static void goToHomeActivity (Context context  ){            // 跳转到首页activity中mineFragment界面
        Intent intent=new Intent(context, HomeActivity.class);
        intent.putExtra("id",1);
        context.startActivity(intent);
    }

    public static void goToAfterSalesActivity (Context context  ){
        Intent intent=new Intent(context, AfterSalesActivity.class);       // 跳转到售后界面
        context.startActivity(intent);
    }
    public static void goToDeliverGoodsActivity (Context context  ){
        Intent intent=new Intent(context, DeliverGoodsActivity.class);       // 跳转到待发货界面
        context.startActivity(intent);
    }
    public static void goToEvaluationActivity (Context context  ){
        Intent intent=new Intent(context, EvaluationActivity.class);       // 跳转到评价界面
        context.startActivity(intent);
    }
    public static void goToPaymentActivity (Context context  ){
        Intent intent=new Intent(context, PaymentActivity.class);       // 跳转到待支付界面
        context.startActivity(intent);
    }
    public static void goToTakeBackGoodsActivity (Context context  ){
        Intent intent=new Intent(context, TakeBackGoodsActivity.class);       // 跳转到待收货界面
        context.startActivity(intent);
    }


    public static void goToPersonalInformationActivity (Context context  ){
        Intent intent=new Intent(context, PersonalInformationActivity.class);       // 跳转到待收货界面
        context.startActivity(intent);
    }
}
