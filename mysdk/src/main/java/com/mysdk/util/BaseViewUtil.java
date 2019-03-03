package com.mysdk.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

/**
 * 基础View工具
 * <p/>
 * Created by huzn on 2018/2/8.
 */

public class BaseViewUtil {

    /**
     * 获取屏幕像素密度
     */
    public static float getDensity(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    /**
     * dip转换成px
     */
    public static int dipToPx(Context context, float dipValue) {
        return (int) (dipValue * getDensity((Activity) context) + 0.5f);
    }

    /**
     * 通过反射的方法获取通知栏的高度
     *
     * @param context
     * @return int 返回类型
     * @throws
     * @Title: getNotificationBarHeight
     * @Description: TODO(获取通知栏的高度)
     * @author {qulj}
     * @修改者：{qulj
     * @修改日期：2015年9月1日下午6:50:58
     */
    public static int getNotificationBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
            return sbar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbar;
    }

    // ======================== 键盘相关 start ========================
    /**
     * 当有焦点的时候强制隐藏软键盘
     */
    public static void hideSystemInputWhenHasFocus(Activity context) {
        if (null != context.getCurrentFocus()) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                    (context.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hintSystemInput(Activity context) {
        if (context.getCurrentFocus() != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                    (context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hintSystemInput(Activity context, EditText editText) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                (editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    // ======================== 键盘相关 end ========================

    /**
     * 获取两个颜色之间的差值
     *
     * @param fraction      两个颜色之间的比率
     * @param startColor    开始颜色
     * @param endColor      结束颜色
     * @return 计算得到的颜色
     */
    public static int getCurrentColor(float fraction, int startColor, int endColor) {
        int redCurrent;
        int blueCurrent;
        int greenCurrent;
        int alphaCurrent;

        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaDifference = alphaEnd - alphaStart;

        redCurrent = (int) (redStart + fraction * redDifference);
        blueCurrent = (int) (blueStart + fraction * blueDifference);
        greenCurrent = (int) (greenStart + fraction * greenDifference);
        alphaCurrent = (int) (alphaStart + fraction * alphaDifference);

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }
}
