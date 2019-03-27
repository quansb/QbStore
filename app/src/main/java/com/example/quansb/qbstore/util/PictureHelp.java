package com.example.quansb.qbstore.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.mysdk.permission.PermissionHelper;
import com.mysdk.util.ViewCommonUtils;


import java.io.File;
import java.io.IOException;
import java.io.PipedReader;



public class PictureHelp {
   private  Uri mCutUri; //剪切后的URI
   private Activity mActivity;
   public String mAuthority;
   private String mCutImgPath;
  public PictureHelp(Activity activity){
      mActivity=activity;
  }
  public Uri getCutUri(){
      return mCutUri;
  }
  public void setAuthority(String authority){
      mAuthority=authority;
  }
  public String getCutImgPath(){
      return mCutImgPath;
  }
    /**
     * 打开相册
     * @param
     */
    public  void openPicture(int requestCode){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent,requestCode);
    }

    /**
     * 打开相机
     * @param OutFile 拍照得到的图片
     */
    public  void openCamera(File OutFile,int requestCode){

    if(mAuthority==null){
        try{
            throw new Exception(" mAuthority必须与Manifest  <provider 标签  android:authorities属性值一样   ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

        try {
            if (OutFile.exists()){
                OutFile.delete();//删除
            }
            OutFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri imageuri ;
        if (Build.VERSION.SDK_INT >= 24){
            imageuri = FileProvider.getUriForFile(mActivity,
                    mAuthority, //要与manifest注册的相同
             OutFile);
        }else{
            imageuri = Uri.fromFile(OutFile);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
        mActivity.startActivityForResult(intent, requestCode);
    }

    /**
     *
     * @param imgPath 真实图片路径
     * @param requestCode 启动剪切的请求码
     */
    @NonNull
    public  void cutForPhoto(String imgPath,String cutPath,int requestCode) {
        try {
            //设置裁剪之后的图片路径文件
            File cutfile = new File(cutPath); //随便命名一个
            if (cutfile.exists()){ //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = null; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            mCutImgPath=cutPath;
            Intent intent = new Intent("com.android.camera.action.CROP");
            //拍照留下的图片
            File camerafile = new File(imgPath);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imageUri = FileProvider.getUriForFile(mActivity,
                        mAuthority,
                        camerafile);
            } else {
                imageUri = Uri.fromFile(camerafile);
            }
            outputUri = Uri.fromFile(cutfile);
            //把这个 uri 提供出去，就可以解析成 bitmap了
            mCutUri = outputUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop",true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", ViewCommonUtils.dipToPx(mActivity,200));
            intent.putExtra("outputY",ViewCommonUtils.dipToPx(mActivity,200));
            intent.putExtra("scale",true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data",false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            mActivity.startActivityForResult(intent,requestCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
