package com.soaic.libcommon.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static int size(List list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static <T> List<T> clone(List<T> value) {
        List<T> result = new ArrayList<T>();
        for (T t : value) {
            result.add(t);
        }
        return result;
    }

    public static <T> List<T> cloneSuper(List<? extends T> value) {
        List<T> result = new ArrayList<T>();
        for (T t : value) {
            result.add(t);
        }
        return result;
    }

    public static String[] toArray(List<String> value) {
        return value.toArray(new String[value.size()]);
    }

    public static List<String> fromArray(String[] value) {

        List<String> result = new ArrayList<String>();
        for (String s : value) {
            result.add(s);
        }
        return result;
    }

    public static String toJson(List<String> link) {

        JSONArray v = new JSONArray();
        for (String l : link) {
            v.put(l);
        }
        return v.toString();
    }

    public static List<String> fromJson(String link) {

        List<String> result = new ArrayList<String>();

        try {
            JSONArray v = new JSONArray(link);
            for (int i = 0; i < v.length(); ++i) {
                result.add(v.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
