package com.soaic.libcommon.utils;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

public class ActivityUtils {

    /**
     * 适配刘海屏全屏时顶部一条黑边
     * @param activity
     */
    public static void setActivityCutoutFullScreen(Activity activity){
        if(activity == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams windowManager = activity.getWindow().getAttributes();
            windowManager.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(windowManager);
        }
    }
}
