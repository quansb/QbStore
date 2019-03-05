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
        requestParams.put("f_id",f_id);
        requestParams.put("pwd",pwd);
        CommonOkHttpClient.post(CommonRequest.
                createGetRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }

    public static void toRegister(Context context,String f_id,String pwd, DisposeDataListener listener, Class<?> clazz){
        RequestParams requestParams=new RequestParams();
        requestParams.put("f_id",f_id);
        requestParams.put("pwd",pwd);
        CommonOkHttpClient.post(CommonRequest.
                createGetRequest(requestParams), new DisposeDataHandle(listener, clazz));
    }



}
