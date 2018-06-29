package com.soaic.libcommon.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class AppUtils {

    public static void switchLanguage(Context context, String language){
        if(TextUtils.isEmpty(language)) { return; }
        Locale myLocale = new Locale(language);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(myLocale);
        } else {
            conf.locale = myLocale;
        }
        res.updateConfiguration(conf, dm);
        setAppLanguage(context, language);
    }

    public static String getAppLanguage(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("language", getSystemLanguage());
    }

    private static void setAppLanguage(Context context, String language){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", language);
        editor.apply();
        editor.commit();
    }

    public static String getSystemLanguage(){
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale.getLanguage() + "-" + locale.getCountry();
    }

    public static String getAssetsToString(Context context, String fileName){
        try {
            return ConvertUtils.inputStreamToStr(context.getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
