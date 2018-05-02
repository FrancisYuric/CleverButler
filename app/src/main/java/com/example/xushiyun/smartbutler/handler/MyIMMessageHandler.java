package com.example.xushiyun.smartbutler.handler;

import com.example.xushiyun.smartbutler.utils.L;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.handler
 * File Name:    MyIMMessageHandler
 * Description: Todo
 */
//TODO 集成：1.6、自定义消息接收器处理在线消息和离线消息
public class MyIMMessageHandler extends BmobIMMessageHandler {
    private MyIMMessageHandler() {
    }

    private static final class Holder{
        private static final MyIMMessageHandler INSTANCE = new MyIMMessageHandler();
    }

    public static MyIMMessageHandler getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void onMessageReceive(MessageEvent messageEvent) {
        super.onMessageReceive(messageEvent);
        L.e(messageEvent.getMessage().getContent());
        EventBus.getDefault()
                .post(messageEvent);
    }

    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        super.onOfflineReceive(offlineMessageEvent);
    }

    public void init () {
//        EventBus.getDefault().register(this);
    }

    public void unbind() {
//        EventBus.getDefault().unregister(this);
    }

}
