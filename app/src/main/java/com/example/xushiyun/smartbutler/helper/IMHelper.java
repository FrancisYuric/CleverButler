package com.example.xushiyun.smartbutler.helper;

import android.content.Context;
import android.text.TextUtils;

import com.example.xushiyun.smartbutler.utils.L;

import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

import static com.kymjs.rxvolley.toolbox.RxVolleyContext.toast;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.helper
 * File Name:    IMHelper
 * Description: Todo 即时通讯相关操作封装
 */

public class IMHelper {
    public static void connect() {
        //TODO 连接：3.1、登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        BmobUser user = BmobUser.getCurrentUser();
        if (!TextUtils.isEmpty(user.getObjectId())) {
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //连接成功
                    } else {
                        //连接失败
                        toast(e.getMessage());
                    }
                }
            });
        }
    }

    public static void disconnect() {
        //TODO 连接：3.2、退出登录需要断开与IM服务器的连接
        BmobIM.getInstance().disConnect();
    }

    public static void setStatus() {
        //TODO 连接：3.3、监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                toast(status.getMsg());
//                L.i(BmobIM.getInstance().getCurrentStatus());
            }
        });
    }
}
