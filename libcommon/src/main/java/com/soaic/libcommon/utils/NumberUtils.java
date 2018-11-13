package com.soaic.libcommon.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Soaic on 2018/1/31.
 */
public class NumberUtils {

    /**
     * float相加 解决精度丢失问题
     * @param floats float
     * @return float
     */
    public static float floatPlus(float... floats){
        BigDecimal bigDecimal = new BigDecimal(0);
        for (float f: floats){
            bigDecimal = bigDecimal.add(new BigDecimal(Float.toString(f)));
        }
        return bigDecimal.floatValue();
    }

    /**
     * double相加 解决精度丢失问题
     * @param doubles double
     * @return double
     */
    public static double doublePlus(double... doubles){
        BigDecimal bigDecimal = new BigDecimal(0);
        for (double d: doubles){
            bigDecimal = bigDecimal.add(new BigDecimal(Double.toString(d)));
        }
        return bigDecimal.doubleValue();
    }

    /**
     * float相乘 解决精度丢失问题
     * @param floats float
     * @return float
     */
    public static float floatMultiply(float... floats){
        BigDecimal bigDecimal = new BigDecimal(1);
        for (float f: floats){
            bigDecimal = bigDecimal.multiply(new BigDecimal(Float.toString(f)));
        }
        return bigDecimal.floatValue();
    }

    /**
     * 保留小数点后几位
     * @param decimal 小数
     * @param digit 位数
     * @return
     */
    public static String keepDecimalDigit(double decimal, int digit){
        StringBuilder pattern = new StringBuilder("#.");
        for(int i = 0; i < digit; i++){
            pattern.append("0");
        }
        DecimalFormat df = new DecimalFormat(pattern.toString());
        return df.format(decimal);
    }

    /**
     * 转成百分数保留小数点后几位
     * @param decimal 小数
     * @param digit 位数
     * @return String
     */
    public static String keepDecimalDigitPrecent(double decimal, int digit, boolean isRound){
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(digit);
        if(isRound) {
            nf.setRoundingMode(RoundingMode.FLOOR);
        }
        return nf.format(decimal * 100) + "%";
    }


}
