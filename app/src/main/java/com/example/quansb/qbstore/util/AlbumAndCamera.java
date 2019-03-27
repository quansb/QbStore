package com.example.quansb.qbstore.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import com.example.quansb.qbstore.activity.PersonalInformationActivity;

import java.io.File;
import java.io.IOException;

import static com.example.quansb.qbstore.util.Constant.OPEN_CHOOSE_PHOTO_CODE;


public class AlbumAndCamera {

    private Uri imageUri;
    private Activity mActivity;
   // private String authority;
    private String cameraPhotoPath;  //相机照相得到的图片路径

    public AlbumAndCamera(Activity activity){
        mActivity=activity;
    }

    public Uri getCutUri(){
        return imageUri;
    }

//    public void setAuthority (String authority ){
//        this.authority=authority;
//    }


    public String getCameraPhotoPath() {
        return cameraPhotoPath;
    }


    /**
     *
     * @param context
     * @param requestCode
     * @param PermissionRequestCode
     * @param authority     mAuthority必须与Manifest  <provider 标签  android:authorities属性值一样
     */
   public void openCamera(Context context,int requestCode,int PermissionRequestCode,String authority) {

       if(authority==null){
           try{
               throw new Exception(" mAuthority必须与Manifest  <provider 标签  android:authorities属性值一样   ");
           }catch (Exception e){
               e.printStackTrace();
           }
       }

       if (ContextCompat.checkSelfPermission(context, Manifest.permission.
               WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
           ActivityCompat.requestPermissions(mActivity,      // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
                   new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionRequestCode); // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
       }else {

           cameraPhotoPath = context.getExternalCacheDir() + "/output_image.jpg";
           File outputImage = new File(context.getExternalCacheDir(), "output_image.jpg");
           try {
               if (outputImage.exists()) {
                   outputImage.delete();
               }
               outputImage.createNewFile();
           } catch (IOException e) {
               e.printStackTrace();
           }
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               imageUri = FileProvider.getUriForFile(context, authority, outputImage);
           } else {
               imageUri = Uri.fromFile(outputImage);
           }
           //启动相机
           Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
           intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
           mActivity.startActivityForResult(intent, requestCode);

       }




    }

    public void openAlbum(Context context,int requestCode,int PermissionRequestCode) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(mActivity,      // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionRequestCode); // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
        }
        else {  //权限已经被授予
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
           mActivity.startActivityForResult(intent, requestCode);
        }
    }

}
