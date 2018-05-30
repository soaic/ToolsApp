package com.soaic.libcommont.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private static int getRatioSize(int w, int h) {
        // 图片最大分辨率
        int imageHeight = 1920;
        int imageWidth = 1280;
        // 缩放比
        int ratio = 1;
        if(w <= 0 || h <= 0){
            return 1;
        }
        // 缩放比,由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        if (w > h && w > imageWidth) {
            // 如果图片宽度比高度大,以宽度为基准
            ratio = Math.round(w * 1.0f / imageWidth);
        } else if (w < h && h > imageHeight) {
            // 如果图片高度比宽度大,以高度为基准
            ratio = Math.round(h * 1.0f / imageHeight);
        }
        // 最小比率为1
        if (ratio <= 0)
            ratio = 1;
        return ratio;
    }

    public static Bitmap getBitmapFromFile(String filePath){
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        int photoDegree = readPictureDegree(filePath);
        BitmapFactory.decodeFile(filePath, newOpts);
        if(photoDegree == 90 || photoDegree == 270){
            newOpts.inSampleSize = getRatioSize(newOpts.outHeight, newOpts.outWidth);
        }else{
            newOpts.inSampleSize = getRatioSize(newOpts.outWidth, newOpts.outHeight);
        }
        if(newOpts.inSampleSize > 1)
            newOpts.inSampleSize += newOpts.inSampleSize % 2;
        newOpts.inJustDecodeBounds = false;//读取所有内容
        newOpts.inDither = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inTempStorage = new byte[32 * 1024];
        Bitmap bitmap = null;
        File file = new File(filePath);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
            bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, newOpts);
            //旋转图片
            if (photoDegree != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(photoDegree);
                // 创建新的图片
                bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File saveBitmapToFile(Bitmap bitmap, File saveDir, String fileName){
        if(saveDir == null) return null;
        if(!saveDir.isDirectory()){
            saveDir.mkdir();
        }
        File file = new File(saveDir + fileName);
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}