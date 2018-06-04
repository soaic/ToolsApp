package com.soaic.libcommon.utils;

import java.math.BigDecimal;

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
}
