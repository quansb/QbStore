package com.mysdk.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.NumberKeyListener;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Description: 共用的控件操作，主要为了改变其状态或者非空判断,或者对字符串教研
 * 主要与界面相关的处理（逻辑相关的在ApplicationUtil）
 * Created by zhengty on 2015/10/28.
 */
public class ViewCommonUtils {
    /**
     * 传入的TextView中是否全都不为空
     * @return true为都不是空且长度大于0，false为有一个或多个为空或长度小于0
     */
    public static boolean lengthNotNull(TextView... eds) {
        for (TextView ed : eds) {
            if (null == ed || ed.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 传入的TextView中是否全都为空
     * @return true全部为空, false有一个或多个不为空且长度大于0
     */
    public static boolean lengthIsNull(TextView... eds) {
        for (TextView ed : eds) {
            if (null != ed && ed.getText().toString().length() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在有光标的时候：有文字显示清除按钮，无文字不显示
     * 在无光标的时候：总是不显示
     *
     * @return true为tv不为空，false则tv为空
     */
    public static boolean judgeIsVisibleClearText(TextView tv, ImageView img) {
        if (!tv.getText().toString().equals("")) {
            judgeIsCursor(tv, img);
            return true;
        } else {
            img.setVisibility(View.GONE);
            return false;
        }
    }

    /**
     * 光标在的时候显示，不在则不显示清除按钮
     */
    public static void judgeIsCursor(TextView tv, ImageView img) {
        if (tv.isFocused() && tv.getText().toString().length() > 0) {
            img.setVisibility(View.VISIBLE);
        } else {
            img.setVisibility(View.GONE);
        }
    }

    /**
     * 光标在的时候，或者有内容的时候，显示view，否则不显示
     */
    public static void judgeIsCursor(EditText et, CheckBox view) {
        if (et.isFocused() || et.getText().toString().length() > 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }



    // 键盘高度：为0表示未弹出
    private static int heightDifference;

    /**
     * 监听键盘是否弹出，有效的前提是actiity设置的键盘弹出模式为android:windowSoftInputMode="stateHidden"
     *
     * @param et
     * @param sc
     * @param mContext 需求改变,于是原先的RelativeLayout默认隐藏，现在改为ScrollView使其默认下滑到最后，
     *                 留个坐标，下次要是改回来直接改ScrollView..
     */
    public static void scrollBySoft(EditText et, final ScrollView sc, final Activity mContext) {
        et.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            // 当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                // 获取当前界面可视部分
                mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                // 获取屏幕的高度
                int screenHeight = mContext.getWindow().getDecorView().getRootView().getHeight();
                // 此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                heightDifference = screenHeight - r.bottom;
                if (heightDifference > 0) {
                    //滑动到最低端（只在登录界面实现，注册及找回密码不实现，因为注册按钮顶上去后导致输入框被顶上去）
                    sc.smoothScrollTo(0, heightDifference);
//                    re.setVisibility(View.GONE);
                } else {
//                    re.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    /**
     * @return 限制editText不能输入空格
     */
    public static InputFilter[] limitspace() {
        InputFilter[] filters = new InputFilter[]{new InputFilter.LengthFilter(20), new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                if (src.length() < 1) {
                    return null;
                } else {
                    char temp[] = (src.toString()).toCharArray();
                    char result[] = new char[temp.length];
                    for (int i = 0, j = 0; i < temp.length; i++) {
                        if (temp[i] == ' ') {
                            continue;
                        } else {
                            result[j++] = temp[i];
                        }
                    }
                    return String.valueOf(result).trim();
                }
            }
        }};
        return filters;
    }

    /**
     * @return 限制editText不能输入空格，换行符
     */
    public static InputFilter[] limitspace(int length) {
        InputFilter[] filters = new InputFilter[]{new InputFilter.LengthFilter(length), new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                if (src.length() < 1) {
                    return null;
                } else if (src.equals(" ") || src.equals(System.getProperty("line.separator"))) {
                    return "";
                } else {
                    return null;
                }
            }
        }};
        return filters;
    }

    /**
     * 点击回车隐藏键盘
     */
    public static void setEnterToHideSoft(EditText editText, final Activity mContext) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(mContext.getWindow().getDecorView().getWindowToken(), 0);
                return true;
            }
        });
    }

    /**
     * 限制EditText不能输入某个字符
     *
     * @param justachar 需要限制的字符
     * @return InputFilter
     */
    public static InputFilter[] limitChar(final char justachar) {
        InputFilter[] filters = new InputFilter[]{new InputFilter.LengthFilter(40), new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                if (src.length() < 1) {
                    return null;
                } else {
                    char temp[] = (src.toString()).toCharArray();
                    char result[] = new char[temp.length];
                    for (int i = 0, j = 0; i < temp.length; i++) {
                        if (temp[i] == justachar) {
                            continue;
                        } else {
                            result[j++] = temp[i];
                        }
                    }
                    return String.valueOf(result);
                }
            }
        }};
        return filters;
    }

    /*
    * 将拼接文字上色。
    * 改变一段文字中某些文字的颜色
    */
    public static SpannableString drawFontColorSpan(String str, String color, int start, int end) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 将拼接文字的某些文字的大小和字体修改。
     * 改变一段文字中某些文字的大小-原有的文字大小的基础上，相对设置文字大小
     * 目前用到的场景有：医院列表-推荐商品上的 价格（人民币符号是正常字体，小一号，价格是粗体大一号）
     * @param str
     * @param porportion 比例，0.8f，1.2f,1.4f等
     * @param typeface  字体，Typeface.BOLD 等
     * @param start
     * @param end
     * @return
     */
    public static SpannableString drawRelativeSizeSpanAndTypeface(String str, float porportion, int typeface, int start, int end) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new RelativeSizeSpan(porportion), start, end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(typeface), start, end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 改变某一个关键词的字体颜色
     *
     * @param value     原字符串
     * @param keyWords  需要改变颜色的关键词
     * @param mContext  Context
     * @return SpannableString
     */
    public static SpannableString changKeyWordColor(String value, String keyWords, Context mContext,int color) {
        String inputString = keyWords.replace(" ", "");
        char[] keywords = inputString.toCharArray();
        SpannableString outputString = new SpannableString(value);
        char[] showChars = value.toCharArray();
        for (int position = 0; position < showChars.length; position++) {
            char word = showChars[position];
            for (char keyword : keywords) {
                if (word == keyword) {
                    ForegroundColorSpan span = new ForegroundColorSpan(color);
                    outputString.setSpan(span, position, position + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                }
            }
        }
        return outputString;
    }



    /**
     * 获取屏幕宽度【像素】
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;// (int)(dm.widthPixels * dm.density +0.5f);
    }

    /**
     * 获取屏幕宽度【像素】
     *
     * @param mContext
     * @return
     */
    public static int getScreenWidth(Context mContext) {
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度度【像素】
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;// (int)(dm.heightPixels * dm.density +0.5f);
    }

    /**
     * 获取屏幕像素密度
     *
     * @param activity
     * @return
     */
    public static float getDensity(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    /**
     * 获取屏幕宽度【DIP】
     *
     * @param activity
     * @return
     */
    public static int getScreenWidthDip(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度度【DIP】
     *
     * @param activity
     * @return
     */
    public static int getScreenHeightDip(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * dip转换成px
     */
    public static int dipToPx(Context context, float dipValue) {
        return (int) (dipValue * getDensity((Activity) context) + 0.5f);
    }

    /**
     * px转换成dip
     */
    public static int pxToDip(Context context, float pxValue) {
        return (int) (pxValue / getDensity((Activity) context) + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * fontScale （DisplayMetrics类中属性scaledDensity）
     */
    public static int spToPx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * fontScale （DisplayMetrics类中属性scaledDensity）
     */
    public static int pxToSp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
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



    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        /*Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;*/

        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            int y = context.getResources().getDimensionPixelSize(x);
            return y;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将TextView的文本复制到粘贴板
     *
     * @param mContext Context
     * @param textView 要复制的TextView
     */
    public static void copyTextView(Context mContext, TextView textView) {
        ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(textView.getText().toString().trim());

    }

    /**
     * 设置TextView中某一段可点击
     * @param tv            要设置的TextView
     * @param text          字符串
     * @param start         开始位置下标（包含）
     * @param end           结束位置下标（不包含）
     * @param color         可点击字符的颜色值
     * @param clickableSpan ClickableSpan，用于监听点击事件，也可用ViewCommonUtils.NoUnderlineSpan
     */
    public static void setClickableTextView(TextView tv, String text, int start, int end, int color, NoUnderlineSpan clickableSpan) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
        tv.setLinkTextColor(color);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setFocusable(false);
        tv.setClickable(false);
        tv.setLongClickable(false);
    }

    /**  设置一个textview包含不同文本大小
     * @param textView textview控件
     * @param text 显示文本
     * @param startPosition 起始位置（包含）
     * @param endPostion 结束位置（不包含）
     * @param size dip值
     */
    public static void setSizeAdjustTextView(Activity activity,TextView textView,String text,int startPosition,int endPostion,int size,int startPosition2,int endPostion2,int size2){
         SpannableString spannableString = new SpannableString(text);
         spannableString.setSpan(new AbsoluteSizeSpan(size,true),startPosition,endPostion,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
         spannableString.setSpan(new AbsoluteSizeSpan(size2,true),startPosition2,endPostion2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
         textView.setText(spannableString);
    }

    /**  设置一个textView指定范围[start, end)的文字大小
     * @param textView textview控件
     * @param text 显示文本
     * @param startPosition 起始位置（包含）
     * @param endPosition 结束位置（不包含）
     * @param size dip值
     */
    public static void setSizeAdjustTextView(TextView textView,String text,int startPosition,int endPosition,int size){
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new AbsoluteSizeSpan(size,true),startPosition,endPosition,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private static Bitmap compressBitmapSize(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        bitmap.recycle();
        return BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size(), options);
    }

    /**
     * 不带下划线的ClickableSpan
     */
    public static class NoUnderlineSpan extends ClickableSpan {

        @Override
        public void onClick(View widget) {
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }

    }

    /**
     * 设置TextView中某一段字符的颜色
     *
     * @param tv    要设置的TextView
     * @param text  字符串
     * @param start 开始位置下标（包含）
     * @param end   结束位置下标（不包含）
     * @param color 颜色值
     */
    public static void setColorTextView(TextView tv, String text, int start, int end, int color) {
        SpannableString sp = new SpannableString(text);
        sp.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
        tv.setFocusable(false);
        tv.setClickable(false);
        tv.setLongClickable(false);
    }

    /**
     * 设置一段可点击的文本
     *
     * @param mainText      要设置的文本内容
     * @param highlightText 高亮可点击的文本内容
     * @param color         高亮的颜色
     */
    public static void setClickableTextView(TextView tv, String mainText, String highlightText, int color, ClickableSpan clickableSpan) {
        if (StringUtils.StrIsNull(mainText) || mainText.length() == 0 ||
                StringUtils.StrIsNull(highlightText) || highlightText.length() == 0) {
            return;
        }

        // 没有匹配的高亮字符
        SpannableString sp = new SpannableString(mainText);
        int start = mainText.indexOf(highlightText);
        if (start == -1)
            return;

        sp.setSpan(clickableSpan, start, start + highlightText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(sp);
        tv.setLinkTextColor(color);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setFocusable(false);
        tv.setClickable(false);
        tv.setLongClickable(false);
    }

    // 统一使用cardId，v5.6.6改
//    /**
//     * @description 此方法主要为了兼容下面的inputCardRule方法，将card_type逆向转变为0,1,2
//     * @auther zhengty
//     * created at 2016/4/14 20:47
//     */
//    public static int getTypeByCardId(String card_type) {
//        if (card_type.equals(ConstantConfig.CARD_TYPE_CN_ID)) {
//            return 0;
//        } else if (card_type.equals(ConstantConfig.CARD_TYPE_HK_ID)) {
//            return 1;
//        } else if (card_type.equals(ConstantConfig.CARD_TYPE_PASSPORT_ID)) {
//            return 2;
//        } else {
//            return 0;
//        }
//    }





    /**
     * 隐藏软键盘
     */
    public static void hintSystemInput(Activity context) {
        if(context.getCurrentFocus() != null) {
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

    /**
     * 显示软键盘
     */
    public static void showSystemInput(Activity context, EditText editText) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    /****** add by fanjh on 2016/9/29 ******/
    /**
     * 类似于点击EditText自动弹出的软键盘，切换页面可以自动收起
     * 注意之前的方法强制弹出键盘会导致回退和前进页面的时候软键盘在部分手机上不会收起
     */
    public static void hideSystemInputNormal(Activity context, EditText editText) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                (editText.getWindowToken(), 0);
    }

    public static void showSystemInputNormal(Activity context, EditText editText) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editText, 0);
    }

    /**
     * 当有焦点的时候强制隐藏软键盘
     */
    public static void hideSystemInputWhenHasFocus(Activity context) {
        if(null != context.getCurrentFocus()) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                    (context.getCurrentFocus().getWindowToken(), 0);
        }
    }
    /****** add by fanjh on 2016/9/29 end ******/

//    public static void changeRewardTextColor(Context mContext, int index, TextView... textViews) {
//        for (int i = 0; i < textViews.length; i++) {
//            if (i == index) {
//                textViews[i].setTextColor(mContext.getResources().getColor(R.color.reward_set_money));
//            } else {
//                textViews[i].setTextColor(mContext.getResources().getColor(R.color.gray_color));
//            }
//        }
//    }

    /**
     * 只有当编辑的editText不为空字符串，button才可点击
     */
    public static void editTextChangeButtonClickable(final EditText editText, final Button button) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editText.getText().toString().trim().equals("")) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });
    }

    /**
     * 获取View在屏幕上的x，y坐标
     *
     * @param v 要获取宽高的View
     * @return 得到位置数组，第一个数为x坐标，第二个数为y坐标
     */
    public static int[] getLocation(View v) {
        int[] loc = new int[4];
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        loc[0] = location[0];
        loc[1] = location[1];
        return loc;
    }

    // 用于记录上一次点击时间，防止多次点击时使用
    private static long mLastClickTime = 0;
    // 两次点击之间的最大时间间隔，在此时间间隔内无法再次点击，防止多次点击时使用
    private static final long MAX_DIFFER_TIME = 1000;
    /**
     * 防止点击多次
     *
     * @return true代表点击了多次，false代表没有点击多次
     */
    public static boolean isFastDoubleClick() {
        long sysTime = System.currentTimeMillis();
        long timeDiffer = sysTime - mLastClickTime;
        if (timeDiffer >= 0 && timeDiffer <= MAX_DIFFER_TIME) {
            return true;
        } else {
            mLastClickTime = sysTime;
            return false;
        }
    }

    /**
     * 为了解决ListView在ScrollView中只能显示一行数据的问题
     */
    public static void setListViewHeightBasedOnChildren(BaseAdapter baseAdapter, ListView listView) {
        if (baseAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = baseAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = baseAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (baseAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 设置EditText中hint字体的大小
     *
     * @param text 设置的文本
     * @param size hint字体大小
     */
    public static void setETHintSize(EditText editText, String text, int size) {
        SpannableString spannableString = new SpannableString(text);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(size, true);
        spannableString.setSpan(absoluteSizeSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannableString(spannableString));
    }

    /**
     * add by fanjh on 2016/8/29
     * 限制空格和回车键的输入，同时还会处理复制文本的空格和回车的删除
     *
     * @param num 限制字数
     * @return
     */
    public static InputFilter[] limitNumAndSpaceAndN(int num) {
        return new InputFilter[]{new InputFilter.LengthFilter(num), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String lineSeparator = System.getProperty("line.separator");
                if (" ".equals(source) || lineSeparator.equals(source)) {
                    return "";
                }
                String text = source.toString();
                if (text.contains(" ")) {
                    text = text.replaceAll(" ", "");
                    if (text.contains(lineSeparator)) {
                        text = text.replaceAll(lineSeparator, "");
                    }
                    return text;
                } else {
                    if (text.contains(lineSeparator)) {
                        text = text.replaceAll(lineSeparator, "");
                        return text;
                    }
                }
                return null;
            }
        }};
    }

    /**
     * 用户手机号码隐私处理
     * @param phone 手机号码
     * @return 处理后带*的手机号码
     */
    public static String phonePrivateHandle(String phone){
        if (StringUtils.StrIsNull(phone) || phone.length() < 7)
            return phone;

        StringBuilder phoneText = new StringBuilder();
        phoneText.append(phone);
        phoneText.replace(3, phone.length() - 4, "****");
        return phoneText.toString();
    }


    /**
     * add by fanjh on 2016/12/28 添加一些ViewGroup的过度动画
     *
     * @param viewGroup 需要添加动画的ViewGroup
     */
    public static void addHideAndShowAlphaAnim(final ViewGroup viewGroup){
        LayoutTransition transition = new LayoutTransition();
        ObjectAnimator animator = ObjectAnimator.ofFloat(null, "alpha", 0, 1);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(null,"alpha",1,0);
        transition.setAnimator(LayoutTransition.APPEARING,animator);
        transition.setAnimator(LayoutTransition.DISAPPEARING,animator1);
        transition.setDuration(LayoutTransition.APPEARING,800);
        transition.setDuration(LayoutTransition.DISAPPEARING,500);
        transition.setStagger(LayoutTransition.APPEARING,100);
        transition.setStagger(LayoutTransition.DISAPPEARING,100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            transition.disableTransitionType(LayoutTransition.CHANGING);
            transition.disableTransitionType(LayoutTransition.CHANGE_APPEARING);
            transition.disableTransitionType(LayoutTransition.DISAPPEARING);
        }
        viewGroup.setLayoutTransition(transition);
    }

    /**
     * 设置view的透明度，适配5.0系统，从Until中移动到本类，5.6.6改
     *
     * @author hukk
     * @param view
     * @param alpha
     */
    public static void setAlpha(View view, int alpha) {
        if (Build.VERSION.SDK_INT >= 21) {
            view.getBackground().setAlpha(alpha);
        }
    }

    /**
     * 设置视图滑动过程中收起键盘
     * @param mContext 当前Activity
     * @param view 需要滑动收键盘的视图
     * @param editText 当前焦点所在的输入框
     */
    public static void setMoveHideImm(@NonNull final Activity mContext, @NonNull View view, @Nullable final EditText editText){
        view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_MOVE){
                    if(null == editText){
                        ViewCommonUtils.hintSystemInput(mContext);
                    }else {
                        ViewCommonUtils.hideSystemInputNormal(mContext, editText);
                    }
                }return false;}
            });
    }

    /**
     * 设置透明状态栏
     * @param window 当前activity所在window
     */
    public static void setTranslucentStatus(Window window){
        //兼容性较强的方案，主要是针对5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 全透明实现
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * v5.0以上设置状态栏颜色
     */
    public static void setStatusColor(Window window, @ColorRes int color) {

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            //取消状态栏透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            window.setStatusBarColor(window.getContext().getResources().getColor(color));
        }
    }

    /**
     * 设置需要充当状态栏的视图高度
     * @param viewStatus 视图
     */
    public static int setStatusViewHeight(Context mContext,View viewStatus){
        int statusHeight = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT? ViewCommonUtils.getStatusBarHeight(mContext):0;
        ViewGroup.LayoutParams params = viewStatus.getLayoutParams();
        if(statusHeight != params.height) {
            params.height = statusHeight;
            viewStatus.requestLayout();
        }
        return statusHeight;
    }

    /**
     *  @author Fanjh
     *  @date 2017/3/11
     *  @description: Fragment相关公共工具类
     *  @note:使用内部类区分度更高，当后期工具类越来越大的时候也更方便单独成类
     */
    public static class FragmentUtils{
        /**
         *  @author Fanjh
         *  @date 2017/3/11
         *  @description:用于初始化Fragment
         *  @note:FragmentManager在内存回收后恢复会自动保存之前添加的Fragment
         *  可能导致多次添加Fragment出现重叠情况（可以通过视图过度绘制看）
         *  @return 最后成功使用的Fragment
         */
        public static Fragment addFragmentWithoutRepeat(FragmentManager fm, int layout_id, Fragment fragment){
            Fragment temp = fm.findFragmentById(layout_id);
            FragmentTransaction transaction = fm.beginTransaction();
            if(null == temp || !temp.isAdded()) {
                transaction.add(layout_id, fragment);
            }
            transaction.commit();
            return null == temp?fragment:temp;
        }
    }

    /**
     * 为View添加渐变显示动画
     */
    public static void showViewWithAlpha(final View view, int duration,
                                         AnimatorListenerAdapter listenerAdapter) {
        view.setAlpha(0);
        view.animate()
                .alpha(1)
                .setDuration(duration)
                .setListener(listenerAdapter)
                .start();
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 为View添加渐变消失动画，AnimatorListenerAdapter传null时，将在动画结束后，默认设置visibility为GONE
     */
    public static void hideViewWithAlpha(final View view, int duration,
                                         AnimatorListenerAdapter listenerAdapter) {
        view.setAlpha(1);
        view.animate()
                .alpha(0)
                .setDuration(duration)
                .setListener(null == listenerAdapter ? new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                } : listenerAdapter)
                .start();
    }

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


    public static String getDensityType(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        String icon_type;
        if (density < 2) {
            icon_type = "1";
        } else if (density < 3) {
            icon_type = "2";
        } else {
            icon_type = "3";
        }
        return icon_type;
    }

    /**
     * 计算viewA的右侧和ViewB的左侧的距离
     * @param viewA
     * @param viewB
     * @return 单位 px
     */
    public static int getDistanceBetweenView(View viewA, View viewB) {
        int[] positionA = getLocation(viewA);
        int[] positionB = getLocation(viewB);

        return Math.abs(positionA[0] + viewA.getWidth() - positionB[0]);
    }

    /**
     * 修改状态栏的信息文字的颜色为黑色
     * 仅支持小米，魅族，以及其它6.0以上设备
     * @param window
     */
    public static boolean setStatusBarFontDark(Window window) {
        if (window == null) return false;
        setTranslucentStatus(window);
        boolean result = false;
        // 小米MIUI
        try {
            Class clazz = window.getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, 0, darkModeFlag);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            value |= bit;
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // android6.0+系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTranslucentStatus(window);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            result = true;
        }
        return result;
    }
}

