package com.soaic.libcommon.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

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
        if (deniedPermissions.size() > 0) {
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
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 判断是否勾选了不再提示系统权限请求
     * 在请求权限前第一次调用一直是返回false, 在请求权限后没有勾选不在提示，调用一直返回true,勾选了则返回false
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment fragment, String... permissions) {
        for (String permission : permissions) {
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
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
            if (resultAction != null) resultAction.doExecuteFail(requestCode);
        } else {
            if (resultAction != null) resultAction.doExecuteSuccess(requestCode);
        }
    }

    /**
     * 回调接口
     */
    public interface PermissionsResultAction {

        void doExecuteSuccess(int requestCode);

        void doExecuteFail(int requestCode);
    }
}