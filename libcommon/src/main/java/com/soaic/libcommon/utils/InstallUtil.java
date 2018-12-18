package com.soaic.libcommon.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.soaic.libcommon.R;

public class InstallUtil {

    public static boolean isAvailable(Context context, String packageName){
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {

            return false;
        }
    }

    public static boolean handleWebUrl(Context context, String url){

        if (url.endsWith(".apk") || url.contains(".apk?")) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else if (url.startsWith("tmast://appdetails")) {
            if (!InstallUtil.isAvailable(context, "com.tencent.android.qqdownloader")) {
                ToastUtils.showShortToast(context, context.getString(R.string.yingyongbao_tip));
            } else {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
            return true;
        }
        return false;
    }
}
