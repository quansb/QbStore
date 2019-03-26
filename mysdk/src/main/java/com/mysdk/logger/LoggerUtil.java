package com.mysdk.logger;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Toast;

import com.mysdk.constant.BaseConstants;
import com.mysdk.util.StringUtils;

public class LoggerUtil {
    public static final String LOG_TAG = "COMMON_LOG";//通用日志tag
    public static final String REQUEST_TAG = "COMMON_REQUEST";//请求日志tag

    /**
     * 打印日志 如果已经开启日志打印功能,传入Tag 与日志内容即可
     *
     * @param tag
     * @param pLog
     */
    public static void logError(String tag, Object pLog) {
        if (!BaseConstants.isRelease){
            Logger.t(tag);
            Logger.e(pLog + "");
        }
    }

    public static void logError(Object pLog) {
        if (!BaseConstants.isRelease){
            Logger.t(LOG_TAG);
            Logger.e(pLog + "");
        }
    }

    public static void logInfo(String tag, String pLog) {
        if (!BaseConstants.isRelease){
            Logger.t(tag);
            Logger.i(pLog);
        }
    }

    public static void logInfo(String pLog) {
        if (!BaseConstants.isRelease) {
            Logger.t(LOG_TAG);
            Logger.i(pLog);
        }
    }

    public static void logJson(String tag,String json){
        if (!BaseConstants.isRelease) {
            Logger.t(tag);
            Logger.json(json);
        }
    }

    public static void logWarn(String tag, Object pLog) {
        if (!BaseConstants.isRelease){
            Logger.t(tag);
            Logger.w(pLog + "");
        }
    }

    public static void logWarn(String tag, Object pLog, Throwable throwable) {
        if (!BaseConstants.isRelease){
            Logger.t(tag);
            Logger.e(throwable,pLog + "");
        }
    }

    public static void logDebug(String tag, Object pLog) {
        if (!BaseConstants.isRelease) {
            Logger.t(tag);
            Logger.d(pLog + "");
        }
    }

    public static void logDebug(Object pLog) {
        if (!BaseConstants.isRelease){
            Logger.t(LOG_TAG);
            Logger.d(pLog);
        }
    }

    /************************
     * Toast  start
     ***************************/
    private static Toast toast;

    /**
     * LENGTH_SHORT toast
     *
     * @param mContext
     * @param msg  内容
     */
    public static void showToastShort(Context mContext, String msg) {
        if (!StringUtils.StrIsNull(msg)) {
            showToast(mContext, msg, Toast.LENGTH_SHORT);
        }
    }
//    /**
//     * LENGTH_SHORT toast
//     *
//     * @param
//     * @param toastString  内容
//     */
//    public static void showToastShort( String toastString) {
//        if (!StringUtils.StrIsNull(toastString)) {
//            showToast(BaseApplication.APPLICATION_CONTEXT, toastString, Toast.LENGTH_SHORT);
//        }
//    }

    /**
     * @param context
     * @param stringResId String资源id
     */
    public static void showToastShort(Context context,@StringRes int stringResId){
        showToast(context, context.getString(stringResId), Toast.LENGTH_SHORT);
    }

    /**
     * LENGTH_LONG toast
     *
     * @param mContext
     * @param context  内容
     */
    public static void showToastLong(Context mContext, String context) {
        if (!StringUtils.StrIsNull(context)) {
            showToast(mContext, context, Toast.LENGTH_LONG);
        }
    }

    /**
     * 展现toast
     *
     * @param context  上下文
     * @param msg      内容
     * @param duration 展现时长
     */
    private static void showToast(Context context, CharSequence msg,
                                  int duration) {
        showToast(context,msg,duration,-1);
    }

    /**
     * 初始化 toast
     *
     * @param mContext
     * update by fanjh on 2016/9/21
     * 这里不需要static持有view，因为makeText内部会new Toast，同时设置视图。
     */
    private static void getToast(Context mContext) {
        if (toast == null) {
            toast = Toast.makeText(mContext.getApplicationContext(), BaseConstants.IS_NULL, Toast.LENGTH_LONG);
        }
    }
    /************************ Toast  end***************************/

    private static void showToast(Context context,CharSequence msg,int duration,int gravity){
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            if(gravity != -1) {
                toast.setGravity(gravity, 0, 0);
            }else{
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0 , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,48,context.getResources().getDisplayMetrics()));
            }
            toast.show();
        } catch (Exception e) {
            logInfo(e.getMessage());
        }
    }

    /**
     * 居中显示
     */
    public static void showToastLongCenter(Context mContext, String context) {
        if (!StringUtils.StrIsNull(context)) {
            showToast(mContext, context, Toast.LENGTH_LONG, Gravity.CENTER);
        }
    }
}
