package com.soaic.libcommon.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    /**
     * 获取临时存储的图片地址
     */
    public static String getTempImagePath(Context context){
        return getTempFilePath(context, "jpg");
    }

    public static String getTempMusicPath(Context context){
        return getTempFilePath(context, "mp3");
    }

    private static String getTempFilePath(Context context, String fileType){
        return context.getExternalCacheDir() + File.separator + "temp_" + System.currentTimeMillis() / 1000 + "." +fileType;
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

    /**
     * 获取文件大小
     * @param file
     * @return
     */
    public static int getFileSize(File file) {
        return (int) (file.length() / 1024 / 1024);
    }

    /**
     * 获取文件夹大小
     * @param file
     * @return
     */
    public static int getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        if (fileList == null) {
            return 0;
        }
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return (int) (size / 1024 / 1024);
    }

    /**
     * 删除文件或文件夹
     */
    public static boolean deleteFileOrFolder(File file) {
        if (file.isFile()) {
            return file.delete();
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if ((childFile == null) || (childFile.length == 0)) {
                return file.delete();
            }
            for (File f : childFile) {
                deleteFileOrFolder(f);
            }
            return file.delete();
        }
        return false;
    }

    /**
     * 将inputStream保存文件
     * @param context 上下文
     * @param inputStream 文件流
     * @return 文件地址
     */
    public static String saveFile(Context context, InputStream inputStream){
        if(inputStream == null) return null;
        FileOutputStream fos = null;
        try {
            String path = getTempMusicPath(context);
            fos = new FileOutputStream(path);
            byte[] buf = new byte[2048];
            int len;
            while((len = inputStream.read(buf)) != -1){
                fos.write(buf, 0, len);
            }
            fos.flush();
            return path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
