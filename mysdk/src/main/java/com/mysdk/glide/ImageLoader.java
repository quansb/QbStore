package com.mysdk.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.io.File;

/**
 * Created by cwl on 2019/3/31.
 */

public class ImageLoader {

      private volatile static ImageLoader mInstance;
      public static ImageLoader getInstance(){
          if (mInstance==null){
              synchronized (ImageLoader.class){
                  if (mInstance==null){
                      mInstance=new ImageLoader();
                  }
              }
          }
          return mInstance;
      }


    /**
     * 默认加载 带有缓存
     * @param url
     * @param mImageView
     */
    public  void  loadImageView(Context mContext, String url, ImageView mImageView){
        Glide.with(mContext).load(url).into(mImageView);
    }

    /**
     * 加载图片不带缓存
     * @param mContext
     * @param url
     * @param mImageView
     * @param userCache
     */
    public  void  loadImageView(Context mContext, String url, ImageView mImageView,boolean userCache){
        if (userCache){
            loadImageView(mContext,url,mImageView);
            return;
        }
        loadImageWithOptions(mContext,url,mImageView,getNoCacheRequestOptions());
    }
    public  void loadImageWithOptions(Context mContext, String url, ImageView mImageView,RequestOptions options){
        Glide.with(mContext).load(url).apply(options).into(mImageView);
    }
    public  void loadImageWithOptions(Context mContext, int resId, ImageView mImageView,RequestOptions options){
        Glide.with(mContext).load(resId).apply(options).into(mImageView);
    }
    public  void loadImageWithOptions(Context mContext, Bitmap bitmap, ImageView mImageView,RequestOptions options){
        Glide.with(mContext).load(bitmap).apply(options).into(mImageView);
    }
    public  void loadLocalImage(Context mContext,int res,  ImageView  mImageView){
        Glide.with(mContext).load(res).into(mImageView);
    }
    public  void loadLocalFileImage(Context mContext, File fileImg, ImageView  mImageView){
        Glide.with(mContext).load(fileImg).into(mImageView);
    }
    public  void loadLocalBitmapImage(Context mContext,Bitmap bitmap, ImageView  mImageView){
        Glide.with(mContext).load(bitmap).into(mImageView);
    }



    /**
     * 加载圆形
     * @param mContext
     * @param url
     * @param mImageView
     */
    public  void loadImageCircle(Context mContext,String url, ImageView mImageView,boolean useCache){
        RequestOptions options;
        if (!useCache){
            options=getNoCacheRequestOptions().circleCrop();
        }else {
           options=new RequestOptions().circleCrop();
        }
        loadImageWithOptions(mContext,url,mImageView,options);
    }

    public  void loadBitmapCircle(Context mContext,Bitmap bitmap, ImageView mImageView){
        loadImageWithOptions(mContext,bitmap,mImageView,getCircleRequestOptions());
    }
    public  void loadLocalImageCircle(Context mContext,int resId, ImageView mImageView,int radius){
        loadImageWithOptions(mContext,resId,mImageView,getCircleRequestOptions());
    }

    /**
     * 设置加载中以及加载失败图片
     * @param url
     * @param mImageView
     * @param placeImage
     * @param errorImage
     */
    public  void loadImageViewLoding(Context mContext, String url, ImageView mImageView, int placeImage, int errorImage) {
        RequestOptions options = new RequestOptions()
                .error(errorImage)
                .placeholder(placeImage);
        loadImageWithOptions(mContext,url,mImageView,options);
    }
    public  void loadImageViewLoding(Context mContext, String url, ImageView mImageView, int placeImage, int errorImage,boolean useCache) {
        if (useCache){
            loadImageViewLoding(mContext,url,mImageView,placeImage,errorImage);
        }else {
            String currentTime=String.valueOf(System.currentTimeMillis()); //相同的地址也会进行缓存了
            RequestOptions options = new RequestOptions()
                    .error(errorImage)
                    .placeholder(placeImage)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .signature(new ObjectKey(currentTime));
            loadImageWithOptions(mContext,url,mImageView,options);
        }
    }
    private    RequestOptions getNoCacheRequestOptions(){
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }
    private    RequestOptions getCircleRequestOptions(){
        RequestOptions options = new RequestOptions().circleCrop();

        return options;
    }

    //清理磁盘缓存
    public  void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }
    //清理内存缓存
    public  void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }
}
