package com.mysdk.constant;


public class BaseConstants {

    // 是否是发布状态.true时 会关闭日志及蒲公英,并打开友盟统计.
    public static boolean isRelease = false;

    // 时间常量
    public final static int DAY = 86400;// 1天
    public final static int DOUBLEDAY = 172800;// 48小时=172800秒 两天
    public final static int HOUR = 3600;// 1小时=3600秒
    public final static int HALF_HOUR = 1800;// 半小时=1800秒
    public final static int SIX_HOUR = 21600;// 6小时=21600秒
    public final static int MINUTE = 60;
    public final static long MONTH = 2592000;// 1个月=2592000秒
    public final static long YEAR = 31104000;// 1年=31104000秒

    // 空字符串
    public static final String IS_NULL = "";

    // SharedPreference文件设置
    public static String mSetting = "setting";

    // 数字常量
    public static final int INT_MINUS_ONE = -1;
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
}
