package com.example.quansb.qbstore;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.quansb.qbstore.network.Constant;
import com.mysdk.okhttp.request.CommonRequest;

public class BaseApplication extends Application {
    public static  Context APPLICATION_CONTEXT;
    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION_CONTEXT=this;
        CommonRequest.setCommonUrl(Constant.BASE_URL);
    }
}
