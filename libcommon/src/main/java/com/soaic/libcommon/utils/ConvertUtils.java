package com.soaic.libcommon.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConvertUtils {
    /**
     * inputStream 转 Str
     * @return String
     */
    public static String inputStreamToStr(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 字符串资源文件转int
     * @param context
     * @param resStr
     * @return
     */
    public static int resStrToInt(Context context, String resStr){
        if(TextUtils.isEmpty(resStr)) return 0;
        String[] res = resStr.split("\\.");
        if(res.length == 3){
            return context.getResources().getIdentifier(res[2] , res[1], context.getPackageName());
        }else {
            return 0;
        }
    }

}
