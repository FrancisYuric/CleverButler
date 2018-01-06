package com.example.xushiyun.smartbutler.utils;

import android.util.Log;

/**
 * Created by xushiyun on 2017/12/5.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.utils
 * File Name:    L
 * Descripetion: 进行项目log的封装, packing log utils
 */

public class L {
    public static final Boolean DEBUG = true;
    public static final String TAG = "SmartButler";

    //4个状态,d,e,i,w
    public static final void d(String text) {
        if(DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static final void i(String text) {
        if(DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static final void w(String text) {
        if(DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static final void e(String text) {
        if(DEBUG) {
            Log.e(TAG, text);
        }
    }



}
