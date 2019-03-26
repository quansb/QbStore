package com.example.quansb.qbstore.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferencesHelp {

    private Context mContext;
    private boolean isApply = true;
    private static SharedPreferences sp;
    private final String FILE_NAME="config";

    public PreferencesHelp(Context context) {
        mContext = context;
    }


    /**
     * 写入至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值 boolean
     * @return 是否插入成功 apply 方式返回null
     */
    public Boolean put(String key, Object value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        SharedPreferences.Editor editor = null;
        if (value instanceof Boolean) {
            editor = sp.edit().putBoolean(key, (Boolean) value);
        }

        if (value instanceof String) {
            editor = sp.edit().putString(key, (String) value);
        }

        if (value instanceof Integer) {
            editor = sp.edit().putInt(key, (Integer) value);
        }

        if (value instanceof Long) {
            editor = sp.edit().putLong(key, (Long) value);
        }
        if (value instanceof Float) {
            editor = sp.edit().putFloat(key, (Float) value);
        }

        if (editor != null) {
            if (isApply) {
                editor.apply();
                return null;
            } else {
                return editor.commit();
            }
        }
        return false;
    }


    /**
     * 从sp中读取
     *
     * @param key      存储节点名称
     * @param defValue 默认值
     * @return 读取boolean标示从sp中
     */
    public Object get(String key, Object defValue) {
        //(存储节点文件名称,读写方式)
        Object o = new Object();
        if (sp == null) {
            sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }

        if (defValue instanceof Boolean) {
            o = (sp.getBoolean(key, (Boolean) defValue));
        }

        if (defValue instanceof String) {
            o = sp.getString(key, (String) defValue);
        }

        if (defValue instanceof Integer) {
            o = sp.getInt(key, (Integer) defValue);
        }

        if (defValue instanceof Long) {
            o = sp.getLong(key, (Long) defValue);
        }
        if (defValue instanceof Float) {
            o = sp.getFloat(key, (Float) defValue);
        }
        return (Object) o;
    }

    public void putString(String key,String value){
        if (sp == null) {
            sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = null;
        editor = sp.edit().putString(key,  value);
        if (editor != null) {
            if (isApply) {
                editor.apply();
            } else {
                editor.commit();
            }
        }
    }

    public String getString(String key,String defValue){
        if (sp == null) {
            sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
       return sp.getString(key,  defValue);
    }

    /**
     * 从sp中移除指定节点
     *
     * @param key 需要移除节点的名称
     * @return 是否插入成功 apply 方式返回null
     */
    public Boolean remove(String key) {
        if (sp == null) {
            sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }

        SharedPreferences.Editor editor = sp.edit().remove(key);
        if (isApply) {
            editor.apply();
            return null;
        } else {
            return editor.commit();
        }
    }

    /**
     * 是否已经存在key
     * @param key
     * @return
     */
    public  boolean contains( String key)
    {
        if (sp == null) {
            sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
          return sp.contains(key);
    }



    /**
     * 设置是否以 apply方式提交
     * @param apply 以apply方式提交，否则：commit
     */
    public void setApply(boolean apply) {
        isApply = apply;
    }
    /**
     * 存放对象
     * @param key
     * @param object
     */
    public void putObject(String key,Object object){
        if (sp == null) {
            sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        Gson gson=new Gson();
        putString(key,gson.toJson(object));

    }

    /**
     * 获取对象
     * @param key
     * @param clazz
     * @return
     */
    public Object getObject(String key,Class clazz){
        if (sp == null) {
            sp = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        Gson gson=new Gson();
        String result=getString(key,"");
        if (!result.equals("")){
            return    gson.fromJson(result,clazz) ;
        }else {
            return null;
        }
    }

}