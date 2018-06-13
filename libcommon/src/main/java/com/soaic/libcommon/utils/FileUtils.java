package com.soaic.libcommon.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

public class FileUtils {

    /**
     * 获取临时存储的图片地址
     */
    public static String getTempFilePath(Context context){
        return context.getExternalCacheDir() + File.separator + "temp_" + System.currentTimeMillis() / 1000+".jpg";
    }

    public static Uri getFileUri(Context context, File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, getFileProviderName(context), file);//通过FileProvider创建一个content类型的Uri
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".fileProvider";
    }
}
