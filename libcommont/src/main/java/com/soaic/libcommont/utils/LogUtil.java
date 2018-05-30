package com.soaic.libcommont.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogUtil {
    private static int LOG_MAX_LENGTH = 2000;
    public static final boolean DEBUG = true;
    private final static String SIGN = "[Soaic]";
    private static String LOG_FILE = getSDPath() + "/log.log";

    public static void d(String TAG, String msg) {
        if (DEBUG) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.d(TAG + i, SIGN + getFileLineMethod() + msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    Log.d(TAG + i, SIGN + getFileLineMethod() + msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
    public static void d(String msg) {
        if (DEBUG) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.d(_FILE_() + i, SIGN + getFileLineMethod() + msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    Log.d(_FILE_() + i, SIGN + getFileLineMethod() + msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
    public static void w(String TAG, String msg) {
        if (DEBUG) {
            Log.w(TAG, SIGN + getFileLineMethod() + msg);
        }
    }
    public static void w(String msg) {
        if (DEBUG) {
            Log.w(_FILE_(), SIGN + getLineMethod() + msg);
        }
    }
    public static void i(String TAG, String msg) {
        if (DEBUG) {
            Log.i(TAG, SIGN + getFileLineMethod() + msg);
        }
    }
    public static void i(String msg) {
        if (DEBUG) {
            Log.i(_FILE_(), SIGN + msg);
        }
    }
    public static void e(String msg) {
        if (DEBUG) {
            Log.e(_FILE_(), SIGN + getLineMethod() + msg);
        }
    }
    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(TAG, SIGN + getLineMethod() + msg);
        }
    }

    /**
     * 将日志写到SD卡中
     * @param TAG
     * @param msg
     */
    public static void f(String TAG, String msg) {
        if (DEBUG) {
            try {
                FileWriter fw = new FileWriter(LOG_FILE, true);
                fw.write(msg + "\n");
                fw.close();
                i(TAG, msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getFileLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return "[" +
                traceElement.getFileName() + "|" +
                traceElement.getLineNumber() + "|" +
                traceElement.getMethodName() + "]";
    }
    private static String getLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return "[" +
                traceElement.getLineNumber() + "|" +
                traceElement.getMethodName() + "]";
    }
    private static String _FILE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return traceElement.getFileName();
    }
    private static String _FUNC_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName();
    }
    private static int _LINE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }
    private static String _TIME_() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        return sdf.format(now);
    }
    private static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

}
