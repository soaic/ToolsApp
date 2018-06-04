package com.soaic.libcommon.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 高德地图定位
 */
public class LocationUtil {
    private OnLocationListener onLocationListener = null;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    private AMapLocationListener mLocationListener;
    private Map<String, Object> locationData = new HashMap<>();

    public static LocationUtil getInstance() {
        return SingleLoader.instance;
    }
    
    private static class SingleLoader{
        private final static LocationUtil instance = new LocationUtil();
    }

    /**
     * 开始定位
     * @param context
     */
    public void startLocation(Context context, OnLocationListener listener){
        this.onLocationListener = listener;
        startLocation(context);
    }

    /**
     * 开始定位
     * @param context
     */
    public void startLocation(Context context){
        if(mLocationClient == null){
            init(context);
        }
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        mLocationClient = new AMapLocationClient(context);
        //初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //设置定位回调监听
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if(onLocationListener != null){
                    if (amapLocation != null) {
                        if (amapLocation.getErrorCode() == 0) {
                            //定位成功回调信息，设置相关消息
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            Date date = new Date(amapLocation.getTime());
                            locationData.put("latitude",amapLocation.getLatitude());
                            locationData.put("longitude",amapLocation.getLongitude());
                            locationData.put("locationType",amapLocation.getLocationType());
                            locationData.put("accuracy",amapLocation.getAccuracy());
                            locationData.put("time",df.format(date));
                            locationData.put("address", amapLocation.getAddress());
                            onLocationListener.onLocation(locationData);
                        } else {
                            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                            onLocationListener.onError(amapLocation.getErrorInfo(), amapLocation.getErrorCode());
                        }
                    }else{
                        onLocationListener.onError(null, -1);
                    }
                }
            }
        };
        mLocationClient.setLocationListener(mLocationListener);
    }

    /**
     * 停止定位
     */
    public void stopLocation(){
        if(mLocationClient!=null){
            mLocationClient.stopLocation();
            mLocationClient.unRegisterLocationListener(mLocationListener);
        }
    }

    /**
     * 销毁定位。
     */
    public void onDestroy() {
        if(mLocationClient!=null){
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    public interface OnLocationListener{
        void onLocation(Map<String, Object> location);
        void onError(String errStr, int errCode);
    }

    public void setOnLocationListener(OnLocationListener onLocationListener) {
        this.onLocationListener = onLocationListener;
    }
}
