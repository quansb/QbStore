package com.example.quansb.qbstore.network;

import android.content.Context;

import com.mysdk.okhttp.CommonOkHttpClient;
import com.mysdk.okhttp.listener.DisposeDataHandle;
import com.mysdk.okhttp.listener.DisposeDataListener;
import com.mysdk.okhttp.request.CommonRequest;
import com.mysdk.okhttp.request.RequestParams;

import okhttp3.OkHttpClient;

public class RequestCenter {
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
        requestParams.put("f_id",f_id);
        requestParams.put("pwd",pwd);
        requestParams.put("user_name",user_name);
        requestParams.PARAMS="register";
        CommonOkHttpClient.post(CommonRequest.
                createPostRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }



}
