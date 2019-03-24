package com.example.quansb.qbstore.util;

import android.content.Context;



public class Help {
    public static boolean isLogin(Context context){
        PreferencesHelp preferencesHelp=new PreferencesHelp(context);
        Boolean b=new Boolean(false);
        boolean reuslt= (boolean) preferencesHelp.get("isLogin",b);
       return reuslt;
    }

}


