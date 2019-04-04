package com.example.quansb.qbstore.util;

import android.widget.ImageView;
import android.widget.TextView;


public class ClickOnTheChange {
    private static boolean status=true;

    public static void toChangeColor(ImageView image,int originalImage,int replaceImage){
        if(status){
            image.setImageResource(replaceImage);
            status=false;
        }else {
            image.setImageResource(originalImage);
            status=true;
        }
    }

    public static void toChangeColor(TextView image, int originalImage, int replaceImage){
        if(status){
            image.setBackgroundResource(replaceImage);
            status=false;
        }else {
            image.setBackgroundResource(originalImage);
            status=true;
        }
    }


}
