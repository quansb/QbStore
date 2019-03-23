package com.mysdk.okhttp.request;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by cwl on 2017/5/6.
 */

public class CommonRequest {
    private static String mBaseUrl;
    public static void setCommonUrl(String url){
        mBaseUrl=url;
    }
    public static Request createPostRequest(RequestParams params) {
        return createPostRequest( params, null);
    }

    public static Request createPostRequest( RequestParams params, RequestParams headers) {
        FormBody.Builder mFormBodyBuild = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                mFormBodyBuild.add(entry.getKey(), entry.getValue());
            }
        }
        //添加请求头
        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody mFormBody = mFormBodyBuild.build();
        Headers mHeader = mHeaderBuild.build();
        Request request = new Request.Builder().url(mBaseUrl+params.PARAMS).
                post(mFormBody).
                headers(mHeader)
                .build();
        return request;
    }


    public static Request createGetRequest( RequestParams params) {
        return createGetRequest( params, null);
    }


    public static Request createGetRequest( RequestParams params, RequestParams headers) {
        StringBuilder urlBuilder = new StringBuilder(mBaseUrl+params.PARAMS).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                //Log.i("info1", "createGetRequest: "+entry.getKey());
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        Headers.Builder mHeaderBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeaderBuild.add(entry.getKey(), entry.getValue());
            }
        }
        Headers mHeader = mHeaderBuild.build();
        Request request = new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get().headers(mHeader).build();
      Log.i("info1", "createGetRequest: "+urlBuilder.substring(0, urlBuilder.length() - 1).toString());
        return request;
    }
    /**
     * @param params
     * @return
     */
    public static Request createMonitorRequest( RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(mBaseUrl).append("&");
        if (params != null && params.hasParams()) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
    }

    /**
     * 文件上传请求
     *
     * @return
     */
    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    public static Request createMultiPostRequest( RequestParams params) {

        MultipartBody.Builder bodyBuilder  = new MultipartBody.Builder();
        bodyBuilder .setType(MultipartBody.FORM);
        if (params != null) {

            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    bodyBuilder .addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {

                    bodyBuilder .addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }
        return new Request.Builder().url(mBaseUrl).post(bodyBuilder .build()).build();
    }

    /**
     * 上传字节流
     * @param params
     * @return
     */
    public static Request createStreamMultiPostRequest(RequestParams params, byte[] byteData,String picName) {

        MultipartBody.Builder bodyBuilder  = new MultipartBody.Builder();
        bodyBuilder .setType(MultipartBody.FORM);
        if (params != null) {

            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof String) {
                    bodyBuilder.addFormDataPart(entry.getKey(), (String)entry.getValue());
                }
            }
        }
        bodyBuilder.addFormDataPart("file", picName, createRequestBody(MediaType.parse("application/octet-stream"), byteData,null));
        return new Request.Builder().url(mBaseUrl).post(bodyBuilder .build()).build();
    }
    public static  <T> RequestBody createRequestBody(final MediaType contentType, final byte[] data,final File file ) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                if (data!=null)
                    return data.length;
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    if (data!=null)
                    {
                        source = Okio.source(new ByteArrayInputStream(data));
                        Buffer buf = new Buffer();
                        long remaining = contentLength();
                        long current = 0;
                        for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                            sink.write(buf, readCount);
                            current += readCount;
                            //callback  进度通知
                        }
                    }
                    else
                    {
                        source = Okio.source(file);
                        Buffer buf = new Buffer();
                        long remaining = contentLength();
                        long current = 0;
                        for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                            sink.write(buf, readCount);
                            current += readCount;
                            //callback 进度通知
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
    }

    /**
     * 文件下载请求
     */
    public static Request createDownloadRequest(String downloadUrl){

        return new Request.Builder()
                .url(downloadUrl)
                .build();

    }
}
