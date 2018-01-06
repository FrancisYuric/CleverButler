package com.example.xushiyun.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xushiyun on 2017/12/5.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.utils
 * File Name:    ShareUtils
 * Descripetion: Todo
 */

public class ShareUtils {
    private static final String CONFIG = "config";

    public static void putBoolean(Context context, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
    public static Boolean getBoolean(Context context, String key, Boolean def) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sp.getBoolean(key, def);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
    public static String getString(Context context, String key, String def) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sp.getString(key, def);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }
    public static int getInt(Context context, String key, int def) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sp.getInt(key, def);
    }

    public static void deleteShare(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    public static void deleteAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }


}
