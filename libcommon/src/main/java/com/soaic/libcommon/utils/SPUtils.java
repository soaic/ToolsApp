package com.soaic.libcommon.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    private final String SP_FileName = "sp_file_name";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static SPUtils spUtil;

    public static SPUtils getInstance(Context context) {
        if (spUtil == null) {
            spUtil = new SPUtils(context);
        }
        return spUtil;
    }

    @SuppressLint("CommitPrefEdits")
    private SPUtils(Context context) {
        preferences = context.getSharedPreferences(SP_FileName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 向SP存入指定key对应的数据
     * 其中value可以是String、boolean、float、int、long等各种基本类型的值
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 清空SP里所以数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 删除SP里指定key对应的数据项
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return contains(key) ? preferences.getString(key, defValue) : defValue;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return contains(key) ? preferences.getBoolean(key, defValue) : defValue;
    }

    public float getFloat(String key, float defValue) {
        return contains(key) ? preferences.getFloat(key, defValue) : defValue;
    }

    public int getInt(String key, int defValue) {
        return contains(key) ? preferences.getInt(key, defValue) : defValue;
    }

    public long getLong(String key, long defValue) {
        return contains(key) ? preferences.getLong(key, defValue) : defValue;
    }

    /**
     * 判断SP是否包含特定key的数据
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return preferences.contains(key);
    }


}
