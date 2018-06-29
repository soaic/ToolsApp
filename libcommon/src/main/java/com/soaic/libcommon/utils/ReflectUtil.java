package com.soaic.libcommon.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {

    private static final String TAG = "ReflectUtil";

    public static Object getValue(Object receiver, String clsName, String fieldName) {
        try {
            Class<?> cls = Class.forName(clsName);
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(receiver);

        } catch (Exception e) {
            Logger.w(TAG, e.toString());
        }
        return null;
    }

    public static boolean setValue(Object receiver, String fieldName, Object value) {
        return setValue(receiver, receiver.getClass(), fieldName, value);
    }

    public static boolean setValue(Object receiver, String clsName, String fieldName, Object value) {
        try {
            Class<?> cls = Class.forName(clsName);
            return setValue(receiver, cls, fieldName, value);
        } catch (Exception e) {
            Logger.w(TAG, e.toString());
        }
        return false;
    }

    public static boolean setValue(Object receiver, Class<?> cls, String fieldName, Object value) {
        try {
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(receiver, value);
            return true;
        } catch (Exception e) {
            Logger.w(TAG, e.toString());
        }
        return false;
    }

    public static Object newInstance(String clsName, Class<?>[] paramsType, Object... args) {
        try {
            Class<?> cls = Class.forName(clsName);
            Constructor<?> ctor = cls.getConstructor(paramsType);
            return ctor.newInstance(args);
        } catch (Exception e) {
            Logger.w(TAG, e.toString());
        }
        return null;
    }

    public static Object invoke(String clsName, String methodName, Class<?>[] paramType, Object... args) {
        try {
            Class<?> cls = Class.forName(clsName);
            Method method = cls.getDeclaredMethod(methodName, paramType);
            return method.invoke(cls, args);
        } catch (Exception e) {
            Logger.w(TAG, e.toString());
        }
        return null;
    }

    public static Object invoke(Class<?> cls, String methodName, Class<?>[] paramType, Object... args) {
        try {
            Method method = cls.getDeclaredMethod(methodName, paramType);
            return method.invoke(cls, args);
        } catch (Exception e) {
            Logger.w(TAG, e.toString());
        }
        return null;
    }

    public static Object invoke(Object receiver, String methodName, Class<?>[] paramType, Object... args) {
        return invoke(receiver, receiver.getClass().getName(), methodName, paramType, args);
    }

    public static Object invoke(Object receiver, String clsName, String methodName, Class<?>[] paramType, Object... args) {
        try {
            Class<?> cls = Class.forName(clsName);
            Method method = cls.getDeclaredMethod(methodName, paramType);
            if (receiver == null) {
                return method.invoke(cls, args);
            } else {
                return method.invoke(receiver, args);
            }
        } catch (Exception e) {
            Logger.w(TAG, e.toString());
        }
        return null;
    }

    public static Class<?> loadClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
