package com.soaic.libcommon.network.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Soaic on 2018/08/14.
 */
public class NetStatusUtil {

    /**
     * 判断网络是否可用.
     *
     * @return true, if is network available
     */
    public static boolean isAvailable(Context context) {
        return getType(context) != -1;
    }

    /**
     * 获取网络状态.
     * @param context Context
     * @return ConnectivityManager.TYPE_WIFI|ConnectivityManager.TYPE_MOBILE
     */
    public static int getType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return -1;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return networkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 是否是WIFI.
     */
    public static boolean isWifi(Context context){
        return getType(context) == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 是否是移动网络.
     */
    public static boolean isMobile(Context context){
        return getType(context) == ConnectivityManager.TYPE_MOBILE;
    }
}