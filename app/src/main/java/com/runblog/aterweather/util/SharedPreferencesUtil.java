package com.runblog.aterweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 共享工具类
 */
public class SharedPreferencesUtil {
    private static final String TAG = SharedPreferencesUtil.class.getSimpleName();
    private static final String SHARE_FILE_NAME="share_data";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtil sharedPreferencesUtil;


    private SharedPreferencesUtil(Context context){
        sharedPreferences = context.getSharedPreferences(SHARE_FILE_NAME,Context.MODE_PRIVATE);
        mEditor = sharedPreferences.edit();
    }

    public static SharedPreferencesUtil getInstance(Context context){
        if(sharedPreferencesUtil == null){
            sharedPreferencesUtil = new SharedPreferencesUtil(context);
        }
        return sharedPreferencesUtil;
    }

    public void put(String key,Object object){
        if (object instanceof String) {
            mEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            mEditor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            mEditor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            mEditor.putLong(key, (Long) object);
        } else {
            mEditor.putString(key, object.toString());
        }

        mEditor.commit();
    }

    public Object get(String key,Object defaultValue){
        if (defaultValue instanceof String) {
            return sharedPreferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        }

        return null;
    }

    public void remove(String key){
        mEditor.remove(key);
        mEditor.commit();
    }


    public void clear(String spName){
        if(TextUtils.isEmpty(spName)){
            spName = SHARE_FILE_NAME;
        }
        mEditor.clear();
        mEditor.commit();
    }

    public boolean contains(String key){
        return sharedPreferences.contains(key);
    }

    public Map<String,?> getAll(Context context,String spName){
        return sharedPreferences.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
