package com.mysdk.util;

import android.app.Activity;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符类型判断，正则校验，计算等工具类
 * create by dinghy
 */
public class StringUtils {

    static String nullStr = "";

    public static boolean isEmpty(String str) {
        if (null != str && nullStr.equals(str)) {
            return true;
        }
        return false;
    }

    public static boolean StrIsNull(String str) {
        if (null == str || nullStr.equals(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    public static boolean objectIsNull(Object object) {
        if (null == object) {
            return true;
        }
        return false;
    }

    public static int StringToInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int StringToIntWithDefault(String value, int defaultInt) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return defaultInt;
        }
    }

    public static float StringToFloatWithDefault(String value, float defaultInt) {
        try {
            return Float.valueOf(value);
        } catch (Exception e) {
            return defaultInt;
        }
    }

    public static long StringToLongWithDefault(String value, long defaultLong) {
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            return defaultLong;
        }
    }

    /**
     * 判断intent是否有String参数
     *
     * @param mContext Context
     * @param extra    参数
     * @return 若有则返回该参数的值，否则返回空字符串
     */
    public static String IsHasExtra(Activity mContext, String extra) {
        if (mContext.getIntent().hasExtra(extra) && null != mContext.getIntent().getStringExtra(extra)) {
            return mContext.getIntent().getStringExtra(extra);
        } else {
            return "";
        }
    }
    public static String IsHasExtra(Activity mContext, String extra,String defaultString) {
       return StringUtils.StrIsNull(IsHasExtra(mContext,extra))? defaultString:IsHasExtra(mContext,extra);
    }



    /**
     * 判断intent是否有boolean参数
     *
     * @param mContext
     * @param extra
     * @return
     */
    public static boolean IsHasBooleanExtra(Activity mContext, String extra) {
        return mContext.getIntent().getBooleanExtra(extra, false);
    }
    /** 一般情况下根据是否为1判断String是否为true
     * @param booleanValue
     * @return
     */
    public static boolean isTrueCom(String booleanValue){
        if ("1".equals(booleanValue)){
            return true;
        }
        return false;
    }

    /**  使其不为空的工具方法
     * @param targerString
     * @return
     */
    public  static String getNoNullString(String targerString){
        if (targerString==null){
            return nullStr;
        }
        return  targerString;
    }
    /**
     * 通用JSON解析指定JSONOBJECT中的某个字段，仅限String类型
     *
     * @param jsonObject 需要解析的对象
     * @param key        需要解析的字段名
     * @return 解析结果
     */
    public static String jsonObjHasStringExtra(JSONObject jsonObject, String key) {
        String value = "";
        try {
            if (null != jsonObject && jsonObject.has(key)) {
                if (null != jsonObject.getString(key)) {
                    value = jsonObject.getString(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 通用JSON解析指定JSONOBJECT中的某个字段，仅限String类型
     *
     * @param jsonObject    需要解析的对象
     * @param key           需要解析的字段名
     * @param defaultString 默认值
     * @return 解析结果
     */
    public static String jsonObjHasStringExtra(JSONObject jsonObject, String key, String defaultString) {
        String value = defaultString;
        try {
            if (jsonObject.has(key)) {
                if (null != jsonObject.getString(key)) {
                    value = jsonObject.getString(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 通用JSON解析指定JSONOBJECT中的某个字段，仅限int类型
     *
     * @param jsonObject   需要解析的对象
     * @param key          需要解析的字段名
     * @param defaultValue 默认值
     * @return 解析结果
     */
    public static int jsonObjHasIntExtra(JSONObject jsonObject, String key, int defaultValue) {
        int value;
        try {
            value = jsonObject.has(key) ? jsonObject.getInt(key) : defaultValue;
        } catch (Exception e) {
            e.printStackTrace();
            value = defaultValue;
        }
        return value;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判断str是否含有中文字符
     *
     * @param str
     * @return true:含有中文字符；false：不含中文字符
     */
    public static boolean containChese(String str) {
        boolean res = false;
        char[] cTemp = str.toCharArray();
        int size = str.length();
        for (int i = 0; i < size; i++) {
            if (isChinese(cTemp[i])) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * 判断是否为数字【浮点数】
     *
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        if(str == null || "".equals(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为数字【整数】
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (null == str || "".equals(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否只包含数字和英文
     *
     * @param string
     * @return
     */
    public static boolean isOnlyContainNumAndEng(String string) {
        if (string == null || string.equals("")) {
            return false;
        }
        Pattern p = Pattern.compile("^[a-z0-9A-Z]+$");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    /**
     * 半角转全角
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 根据JSON中的键名解析String转int
     * 如果成功，获得int结果
     * 否则为默认值
     *
     * @param key          键名
     * @param defaultValue 默认值
     */
    public static int parseFromJsonString(JSONObject object, String key, int defaultValue) {
        int result = defaultValue;
        try {
            if (object.has(key)) {
                result = Integer.parseInt(object.getString(key));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result = defaultValue;
        }
        return result;
    }

    /**
     * Byte与Byte的倍数
     */
    public static final int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final int MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    public static final int GB = 1073741824;

    /**
     * 字节数转合适内存大小
     * <p>保留3位小数</p>
     *
     * @param byteNum 字节数
     * @return 合适内存大小
     */
    public static String byte2FitMemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < KB) {
            return String.format("%.3fB", (double) byteNum + 0.0005);
        } else if (byteNum < MB) {
            return String.format("%.3fKB", (double) byteNum / KB + 0.0005);
        } else if (byteNum < GB) {
            return String.format("%.3fMB", (double) byteNum / MB + 0.0005);
        } else {
            return String.format("%.3fGB", (double) byteNum / GB + 0.0005);
        }
    }

    /**
     * 从Intent中获得指定key的String类型value
     */
    public static String getStringFromIntent(Intent intent, String key, String defaultValue) {
        if (null == intent || null == key) {
            return defaultValue;
        }
        if (intent.hasExtra(key)) {
            return intent.getStringExtra(key);
        }
        return defaultValue;
    }

    public static String getErrorInfoFromException(Exception exception) {
        String result;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            result = sw.toString();
        } catch (Exception e2) {
            result = "解析失败" + e2.getMessage();
        }
        return result;
    }

    /**
     * 处理一段文字的字体大小，让其前后两端文字大小不一
     * @param rawStr
     * @param separator 分隔标识
     * @param aheadSizePx 主要部分文字的大小px
     * @param latterSizePx 次要部分文字的大小px
     * @return
     */
    public static SpannableString formatStrWithTextSize(String rawStr, String separator, int aheadSizePx, int latterSizePx) {
        if (rawStr == null || "".equals(rawStr)) return new SpannableString("");

        SpannableString spanString = new SpannableString(rawStr);
        int index = rawStr.indexOf(separator);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(aheadSizePx);

        if (index == -1) {
            spanString.setSpan(span, 0, rawStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            return spanString;
        }
        spanString.setSpan(span, 0, index, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan subSpan = new AbsoluteSizeSpan(latterSizePx);
        spanString.setSpan(subSpan, index, rawStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spanString;
    }

    /**
     * 处理浮点数，整数和小数部分字体大小
     * @param rawStr 浮点数
     * @param integerSizePx 整数部分文字的大小px
     * @param decimalsSizePx 小数部分文字的大小px
     * @return
     */
    public static SpannableString formatFloatStrWithTextSize(String rawStr, int integerSizePx, int decimalsSizePx) {
        if (rawStr == null || "".equals(rawStr)) return new SpannableString("");
        if (!rawStr.matches("^(-?\\d+)(\\.\\d+)?$")) return new SpannableString(rawStr);
        return formatStrWithTextSize(rawStr, ".", integerSizePx, decimalsSizePx);
    }

    /**
     * 过滤html，css标签
     * @param
     * @return
     */
    public static String delectHtmlTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>p]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签
        return htmlStr.trim();
    }
}
