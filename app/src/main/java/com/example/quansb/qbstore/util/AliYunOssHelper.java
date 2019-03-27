package com.example.quansb.qbstore.util;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.example.quansb.qbstore.network.Constant;
import com.mysdk.logger.LoggerUtil;

import java.util.HashMap;

import static com.example.quansb.qbstore.BaseApplication.APPLICATION_CONTEXT;


/**
 *
 */
public class AliYunOssHelper {
    public static final String ALiEndpoint="http://oss-cn-shenzhen.aliyuncs.com";
    public static final String LOCAL_OSSTOKEN="getOssToken";
    private static OSS oss;
    private String bucket="zsc-content";// OSS的bucket 在阿里云控制台查看
     public AliYunOssHelper(){


     }
    private static OSS getOss() {
        return oss;
    }
    private void initOSS(){
        //阿里
        String endpoint = ALiEndpoint;
        // 推荐使用OSSAuthCredentialsProvider，token过期后会自动刷新。
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(Constant.BASE_URL+LOCAL_OSSTOKEN);
//config
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(APPLICATION_CONTEXT, endpoint, credentialProvider, conf);
        this.oss=oss;
    }

    /**
     *
     * @param key 一般可以选择文件名
     * @param path 文件路径
     */
    public  void uploadToOSS(final String key, final String path){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (oss==null) {
                    initOSS();
                }
                    PutObjectRequest put = new PutObjectRequest(bucket, key, path);
                    // 异步上传时可以设置进度回调
                    put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                        @Override
                        public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                LoggerUtil.logInfo("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                        }
                    });

                    OSSAsyncTask task =  oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                        @Override
                        public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                            // 生成URL
                            String url = oss.presignPublicObjectURL(bucket,key);
                            monResultListener.onSuccess(url);
                        }

                        @Override
                        public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                            // 请求异常
                            if (clientException != null) {
                                // 本地异常如网络异常等
                                clientException.printStackTrace();
                            }
                            if (serviceException != null) {
                                // 服务异常
                                LoggerUtil.logInfo("onFailure: toUpload");
                                LoggerUtil.logInfo("info", serviceException.getErrorCode());
                                LoggerUtil.logInfo("info", serviceException.getRequestId());
                                LoggerUtil.logInfo("info", serviceException.getHostId());
                                LoggerUtil.logInfo("info", serviceException.getRawMessage());
                            }
                            if (serviceException==null)
                                LoggerUtil.logInfo("info", "onFailure: null");
                            monResultListener.onFailure("上传失败");
                        }
                    });
                    task.waitUntilFinished();
                }
        }).start();



    }
    private OnResultListener monResultListener;
    public void setOnResultListener(OnResultListener onResultListenr){
        monResultListener= onResultListenr;
    }
    public interface OnResultListener{
     void onSuccess(String url);
    void onFailure(String msg);
    }

}