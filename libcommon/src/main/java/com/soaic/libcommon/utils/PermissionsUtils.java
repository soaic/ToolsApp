package com.soaic.libcommon.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 6.0权限工具类
 * Created by Soaic on 2016/5/20.
 */
public class PermissionsUtils {
    private PermissionsResultAction resultAction;

    private static class MPermissionsUtilsLoader {
        private static final PermissionsUtils INSTANCE = new PermissionsUtils();
    }

    public static PermissionsUtils getInstance() {
        return MPermissionsUtilsLoader.INSTANCE;
    }

    /**
     * 申请权限
     *
     * @param activity
     * @param requestCode
     * @param permissions
     * @param resultAction
     */
    public void requestPermissions(Activity activity, int requestCode, String[] permissions, PermissionsResultAction resultAction) {
        this.resultAction = resultAction;
        requestPermissions(activity, requestCode, permissions);
    }

    public void requestPermissions(Activity activity, int requestCode, String[] permissions) {
        List<String> deniedPermissions = findDeniedPermissions(activity, permissions);
        if (deniedPermissions == null) {
            if (resultAction != null) resultAction.doExecuteFail(requestCode, false);
        } else if (deniedPermissions.size() > 0) {
            ActivityCompat.requestPermissions(activity, deniedPermissions.toArray(new String[]{}), requestCode);
        } else {
            if (resultAction != null) resultAction.doExecuteSuccess(requestCode);
        }
    }

    /**
     * 获取没有授权的权限
     *
     * @param activity
     * @param permission
     * @return
     */
    public static List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (ActivityCompat.checkSelfPermission(activity, value) != PackageManager.PERMISSION_GRANTED) {
                if(!ActivityCompat.shouldShowRequestPermissionRationale(activity, value)){
                    return null;
                }
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 处理结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }

        if (deniedPermissions.size() > 0) {
            if (resultAction != null) resultAction.doExecuteFail(requestCode, true);
        } else {
            if (resultAction != null) resultAction.doExecuteSuccess(requestCode);
        }
    }

    /**
     * 回调接口
     */
    public interface PermissionsResultAction {

        void doExecuteSuccess(int requestCode);

        void doExecuteFail(int requestCode, boolean isShowPermissionsDialog);
    }
}
