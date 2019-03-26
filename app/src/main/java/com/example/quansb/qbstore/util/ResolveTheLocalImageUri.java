package com.example.quansb.qbstore.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.quansb.qbstore.R;

public class ResolveTheLocalImageUri {




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String handleImageOnKitKat(Intent data, Context context) {
        String imagePath = null;
        Uri uri = data.getData();
        if (uri != null) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                //如果是document类型的Uri，则使用document id 处理
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];  //解析出数字格式的id
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection,context);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null,context);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                //如果是content类型的Uri，则使用普通的方式处理
                imagePath = getImagePath(uri, null,context);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                //如果是file类型的Uri，直接获取图片路径
                imagePath = uri.getPath();
            }
        }
        return imagePath;
    }


    public static String getImagePath(Uri uri, String selection, Context context) {
        String path = null;
        //通过Uri和selection 来获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


}
