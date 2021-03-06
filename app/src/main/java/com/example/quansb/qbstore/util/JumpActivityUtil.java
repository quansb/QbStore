package com.example.quansb.qbstore.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;

import com.example.quansb.qbstore.activity.AboutActivity;
import com.example.quansb.qbstore.activity.AddressManagementActivity;
import com.example.quansb.qbstore.activity.AfterSalesActivity;
import com.example.quansb.qbstore.activity.AgeChangeActivity;
import com.example.quansb.qbstore.activity.DeliverGoodsActivity;
import com.example.quansb.qbstore.activity.EvaluationActivity;
import com.example.quansb.qbstore.activity.HomeActivity;
import com.example.quansb.qbstore.activity.LoginActivity;
import com.example.quansb.qbstore.activity.ModifyHeadPictureActivity;
import com.example.quansb.qbstore.activity.NicknameActivity;
import com.example.quansb.qbstore.activity.OrderActivity;
import com.example.quansb.qbstore.activity.PaymentActivity;
import com.example.quansb.qbstore.activity.PersonalInformationActivity;
import com.example.quansb.qbstore.activity.RegisterActivity;
import com.example.quansb.qbstore.activity.SelectAddressActivity;
import com.example.quansb.qbstore.activity.SettingActivity;
import com.example.quansb.qbstore.activity.TakeBackGoodsActivity;
import com.example.quansb.qbstore.activity.addaddress.AddAddressActivity;
import com.example.quansb.qbstore.activity.homebanner.BannerJumpActivity;
import com.example.quansb.qbstore.activity.homegoodspay.ConfirmAnOrderActivity;
import com.example.quansb.qbstore.activity.homeshopping.ReadyToSettle;
import com.example.quansb.qbstore.entity.GoodsInfo;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.quansb.qbstore.util.Constant.ADDRESS_REQUEST_CODE;
import static com.example.quansb.qbstore.util.Constant.INFO_REQUEST_CODE;

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
        Intent intent=new Intent(context, PersonalInformationActivity.class);       // 跳转到个人信息界面
        context.startActivity(intent);
    }

    public static void goToAddressManagementActivity(Context context){
        Intent intent=new Intent(context, AddressManagementActivity.class);       // 跳转到地址管理界面
        context.startActivity(intent);
    }
    public static void goToAboutActivity(Context context){
        Intent intent=new Intent(context, AboutActivity.class);       // 跳转到关于界面
        context.startActivity(intent);
    }

    public static void goToNicknameActivity(Context context){
        Intent intent=new Intent(context,NicknameActivity.class);       // 跳转到修改昵称界面
        context.startActivity(intent);
    }

    public static void goToNicknameActivity(Activity activity,String nickname){
        Intent intent=new Intent(activity,NicknameActivity.class);   // 跳转到修改昵称界面
        intent.putExtra("nick_name",nickname);
        activity.startActivityForResult(intent, INFO_REQUEST_CODE);
    }

    public static void goToAgeChangeActivity(Activity activity,String age){
        Intent intent=new Intent(activity,AgeChangeActivity.class);   // 跳转到修改年龄界面
        intent.putExtra("age",age);
        activity.startActivityForResult(intent, INFO_REQUEST_CODE);
    }

    public static void goToAgeChangeActivity(Context context){
        Intent intent=new Intent(context,AgeChangeActivity.class);       // 跳转到修改年龄界面
        context.startActivity(intent);
    }

    public static void goToModifyHeadPictureActivity(Context context){
        Intent intent=new Intent(context,ModifyHeadPictureActivity.class);       // 跳转到修改头像界面
        context.startActivity(intent);
    }

    public static void goToBannerJumpActivity(Context context, int position, ArrayList banner_jump_url){
        Intent intent=new Intent(context,BannerJumpActivity.class);// 跳转到WebView Activity
        intent.putExtra("banner_jump_url",banner_jump_url);
        intent.putExtra("position",position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(context).startActivity(intent);
        }
    }

    public static void goToReadyToSettle(Context context, String goods_id){
        Intent intent=new Intent(context,ReadyToSettle.class);          //商品详情
        intent.putExtra("goods_id",goods_id);
        context.startActivity(intent);
    }



    public static void goToConfirmAnOrderActivity(Context context ){
        Intent intent=new Intent(context,ConfirmAnOrderActivity.class);
        context.startActivity(intent);
    }

    public static void goToConfirmAnOrderActivity(Context context,String cart_goods_id ){     //购物车多个商品跳转
        Intent intent=new Intent(context,ConfirmAnOrderActivity.class);
        intent.putExtra("num","1");
        intent.putExtra("cart_goods_id",cart_goods_id);
        context.startActivity(intent);
    }


    public static void goToConfirmAnOrderActivity(Context context,GoodsInfo goodsInfo ,String goodsId,String color,String size){    //单个商品跳转
        Intent intent=new Intent(context,ConfirmAnOrderActivity.class);
        intent.putExtra("num","0");
        intent.putExtra("color",color);
        intent.putExtra("size",size);
        intent.putExtra("goodsId",goodsId);
        context.startActivity(intent);
    }

    public static void goToSelectAddressActivity(Activity activity){
        Intent intent=new Intent(activity,SelectAddressActivity.class);
        intent.putExtra("select_address",1);
        activity.startActivityForResult(intent,ADDRESS_REQUEST_CODE);
    }

    public static void goToAddAddressActivity(Context context){
        Intent intent=new Intent(context,AddAddressActivity.class);       // 跳转到修改年龄界面
        context.startActivity(intent);
    }


}
