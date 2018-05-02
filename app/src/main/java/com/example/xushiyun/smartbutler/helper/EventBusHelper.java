package com.example.xushiyun.smartbutler.helper;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by xushiyun on 2018/5/3.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.helper
 * File Name:    EventBusHelper
 * Description: Todo
 */

public class EventBusHelper {
    public static final void register(Object o) {
        EventBus.getDefault().register(o);
    }

    public static final void unregister(Object o) {
        EventBus.getDefault().unregister(o);

    }

}
