package com.example.xushiyun.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.xushiyun.smartbutler.R;

/**
 * Created by xushiyun on 2017/12/7.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.view
 * File Name:    LoginDIalog
 * Descripetion: 这里定义的是一个自定义的类
 */

public class MyAlertDialog extends Dialog {
    public MyAlertDialog(@NonNull Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout, style, Gravity.CENTER );
    }
    public MyAlertDialog(Context context, int width, int height, int layout, int style, int gravity, int anim) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = gravity;

        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    public MyAlertDialog(Context context, int width, int height, int layout, int style, int gravity) {
        this(context, width, height, layout, style, gravity, R.style.pop_up_anim);
    }
}
