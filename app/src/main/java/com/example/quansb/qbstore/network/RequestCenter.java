package com.example.quansb.qbstore.network;

import android.content.Context;
import android.net.Uri;

import com.mysdk.okhttp.CommonOkHttpClient;
import com.mysdk.okhttp.listener.DisposeDataHandle;
import com.mysdk.okhttp.listener.DisposeDataListener;
import com.mysdk.okhttp.request.CommonRequest;
import com.mysdk.okhttp.request.RequestParams;

import okhttp3.OkHttpClient;

public class RequestCenter {
    /**
     * 登录请求
     *
     * @param context
     */
    public static void toLogin(Context context, String f_id, String pwd, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("pwd", pwd);
        requestParams.PARAMS = "login";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 注册请求
     *
     * @param context
     * @param f_id
     * @param user_name
     * @param pwd
     * @param listener
     * @param clazz
     */
    public static void toRegister(Context context, String f_id, String user_name, String pwd, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("pwd", pwd);
        requestParams.put("user_name", user_name);
        requestParams.PARAMS = "register";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 退出登录
     *
     * @param f_id
     * @param listener
     * @param clazz
     */
    public static void toLoginOut(String f_id, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.PARAMS = "outLogin";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 修改个人信息
     *
     * @param f_id
     * @param listener
     * @param clazz
     */
    public static void toChangePersonalInformation(String f_id, String user_name, String age, String sex, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("user_name", user_name);
        requestParams.put("age", age);
        requestParams.put("sex", sex);
        requestParams.PARAMS = "updateUserInfo";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 发送修改头像网络请求
     *
     * @param f_id
     * @param url
     * @param listener
     * @param clazz
     */
    public static void toUpdateAvatar(String f_id, String url, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("avatar", url);
        requestParams.PARAMS = "updateAvatar";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    /**
     *    得到广告图
     * @param f_id
     * @param listener
     * @param clazz
     */
    public static void toUpdateBanner(String f_id, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.PARAMS = "getBanners";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }


    public static void toUpdateHomeData(String f_id, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.PARAMS = "getHomeData";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    public static void toGetShoppingCartData(String f_id, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.PARAMS = "getCartGoods";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    public static void toGetAddressData(String f_id, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.PARAMS = "getAddressList";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }


    public static void toSetDefaultAddress(String f_id, String address_id, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("address_id",address_id);
        requestParams.PARAMS = "setDefaultAddress";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    public static void toReadyToSettle(String f_id, String goods_id, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("goods_id",goods_id);
        requestParams.PARAMS = "getGoodsDetail";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }




    public static void toPayGoodsMoney(String f_id, String goods_id,String pay_money, String pay_pwd, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("goods_id",goods_id);
        requestParams.put("pay_money",pay_money);
        requestParams.put("pay_pwd",pay_pwd);
        requestParams.PARAMS = "payGoods";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }


    public static void toSetPayPassWord(String f_id,  String pay_pwd, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("pay_pwd",pay_pwd);
        requestParams.PARAMS = "setPayPwd";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }



    public static void toAddGoodsToCart(String f_id, String goods_id, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("goods_id",goods_id);
        requestParams.PARAMS = "addGoodsToCart";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    public static void toGetGoodsSpecifications(String f_id, String goods_id, DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("goods_id",goods_id);
        requestParams.PARAMS = "getGoodsSpecifications";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    public static void toSetGoodsSpecifications(String f_id, String goods_id,String goods_color,String goods_size,DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("goods_id",goods_id);
        requestParams.put("goods_color",goods_color);
        requestParams.put("goods_size",goods_size);
        requestParams.PARAMS = "SetSpecifications";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    public static void toGetConfirmOrderFromCart(String f_id, String all_goods_id,String type,DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id", f_id);
        requestParams.put("type",type);
        requestParams.put("goods_id",all_goods_id);
        requestParams.PARAMS = "GetConfirmOrder";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    public static void toGetOneConfirmOrder(String f_id, String goods_id,String type,String goods_size,String goods_color,DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("type",type);
        requestParams.put("goods_size",goods_size);
        requestParams.put("goods_color",goods_color);
        requestParams.put("user_id", f_id);
        requestParams.put("goods_id",goods_id);
        requestParams.PARAMS = "GetConfirmOrder";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }



    public static void toSaveAddress(String f_id, String name,String phone,String code,String address,DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("name",name);
        requestParams.put("phone",phone);
        requestParams.put("code",code);
        requestParams.put("address", address);
        requestParams.put("user_id", f_id);
        requestParams.PARAMS = "AddAddress";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }
    public static void toDeleteAddress(String f_id,String address_id,DisposeDataListener listener, Class<?> clazz) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("address_id", address_id);
        requestParams.put("user_id", f_id);
        requestParams.PARAMS = "DeleteAddress";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }



}
