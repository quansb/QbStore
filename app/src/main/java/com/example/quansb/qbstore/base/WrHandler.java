package com.example.quansb.qbstore.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public  class WrHandler extends Handler {
    private WeakReference <Callback>mWeakReference;

    public WrHandler(Callback callback){
        mWeakReference=new WeakReference<Callback>(callback);
    }
    private WrHandler(Callback callback, Looper looper){
        super(looper);
        mWeakReference=new WeakReference<Callback>(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if(mWeakReference!=null&&mWeakReference.get()!=null){
            Callback callback=mWeakReference.get();
            callback.handleMessage(msg);
        }

    }
}
