package com.example.quansb.qbstore.network;

import android.content.Context;
import android.net.Uri;

import com.mysdk.okhttp.CommonOkHttpClient;
import com.mysdk.okhttp.listener.DisposeDataHandle;
import com.mysdk.okhttp.listener.DisposeDataListener;
import com.mysdk.okhttp.request.CommonRequest;
import com.mysdk.okhttp.request.RequestParams;

import okhttp3.OkHttpClient;

public class RequestCenter   {
    /**
     * 登录请求
     * @param context
     */
    public static void toLogin(Context context,String f_id,String pwd, DisposeDataListener listener, Class<?> clazz){
        RequestParams requestParams=new RequestParams();
        requestParams.put("user_id",f_id);
        requestParams.put("pwd",pwd);
        requestParams.PARAMS="login";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 注册请求
     * @param context
     * @param f_id
     * @param user_name
     * @param pwd
     * @param listener
     * @param clazz
     */
    public static void toRegister(Context context,String f_id,String user_name, String pwd, DisposeDataListener listener, Class<?> clazz){
        RequestParams requestParams=new RequestParams();
        requestParams.put("user_id",f_id);
        requestParams.put("pwd",pwd);
        requestParams.put("user_name",user_name);
        requestParams.PARAMS="register";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 退出登录
     * @param f_id
     * @param listener
     * @param clazz
     */
    public static void toLoginOut(String f_id, DisposeDataListener listener, Class<?> clazz){
        RequestParams requestParams=new RequestParams();
        requestParams.put("user_id",f_id);
        requestParams.PARAMS="outLogin";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 修改个人信息
     * @param f_id
     * @param listener
     * @param clazz
     */
    public  static void toChangePersonalInformation(String f_id,String user_name,String age,String sex, DisposeDataListener listener, Class<?> clazz){
        RequestParams requestParams=new RequestParams();
        requestParams.put("user_id",f_id);
        requestParams.put("user_name",user_name);
        requestParams.put("age",age);
        requestParams.put("sex",sex);
        requestParams.PARAMS="updateUserInfo";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 发送修改头像网络请求
     * @param f_id
     * @param url
     * @param listener
     * @param clazz
     */
    public static void toUpdateAvatar(String f_id, String url, DisposeDataListener listener, Class<?> clazz){
        RequestParams requestParams=new RequestParams();
        requestParams.put("user_id",f_id);
        requestParams.put("avatar",url);
        requestParams.PARAMS="updateAvatar";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }


    public static void toUpdateBanner(String f_id, DisposeDataListener listener, Class<?> clazz){
        RequestParams requestParams=new RequestParams();
        requestParams.put("user_id",f_id);
        requestParams.PARAMS="getBanners";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }


    public static void toUpdateHomeData(String f_id, DisposeDataListener listener, Class<?> clazz){
        RequestParams requestParams=new RequestParams();
        requestParams.put("user_id",f_id);
        requestParams.PARAMS="getHomeData";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }




}
