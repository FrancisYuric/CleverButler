package com.example.xushiyun.smartbutler.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * Created by xushiyun on 2017/12/12.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.view
 * File Name:    DispatchLinearLayout
 * Descripetion: Todo
 */

public class DispatchLinearLayout extends LinearLayout {
    private MyDispatchEventListener myDispatchEventListener;

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public static interface MyDispatchEventListener {
        boolean dispatchKeyEvent(KeyEvent keyEvent);
    }

    public MyDispatchEventListener getMyDispatchEventListener() {
        return myDispatchEventListener;
    }

    public void setMyDispatchEventListener(MyDispatchEventListener myDispatchEventListener) {
        this.myDispatchEventListener = myDispatchEventListener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(myDispatchEventListener != null) {
            return myDispatchEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
