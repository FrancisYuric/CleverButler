package com.example.xushiyun.smartbutler.utils;

import android.widget.EditText;

/**
 * Created by xushiyun on 2018/5/3.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.utils
 * File Name:    TextUtils
 * Description: Todo
 */

public class MyTextUtils {
    public static final String getPureContent(Object content) {
        return content.toString().trim();
    }
    public static final void cleanEditTextMessage(EditText editText) {
        editText.setText("");
    }
}
