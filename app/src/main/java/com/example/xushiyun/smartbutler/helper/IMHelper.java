package com.example.xushiyun.smartbutler.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.ExampleActivity;
import com.example.xushiyun.smartbutler.utils.L;

import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessageSendListener;
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

public final class IMHelper {

    public static String getCurrentUsername() {
        return BmobUser.getCurrentUser().getUsername().trim();
    }
    public static void connect() {
        //TODO 连接：3.1、登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        BmobUser user = BmobUser.getCurrentUser();
        if (!TextUtils.isEmpty(user.getUsername())) {
            BmobIM.connect(user.getUsername().trim(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //连接成功
                        L.e("服务器连接成功");
                    } else {
                        //连接失败
                        toast(e.getMessage());
                        Log.e("TAG",e.getMessage()+"  "+e.getErrorCode());
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

    public static void openConversation(String receiverId, final String messageContent) {
        final BmobIMUserInfo info =new BmobIMUserInfo();
        info.setAvatar("填写接收者的头像");
        info.setUserId(receiverId);
        info.setName("填写接收者的名字");
        BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
            @Override
            public void done(BmobIMConversation c, BmobException e) {
                if(e==null){
//                    isOpenConversation = true;
                    //在此跳转到聊天页面或者直接转化
                    final BmobIMConversation mBmobIMConversation=BmobIMConversation.obtain(BmobIMClient.getInstance(),c);
//                    tv_message.append("发送者："+et_message.getText().toString()+"\n");
                    final BmobIMTextMessage msg =new BmobIMTextMessage();
                    msg.setContent(messageContent);
                    mBmobIMConversation.sendMessage(msg, new MessageSendListener() {
                        @Override
                        public void done(BmobIMMessage msg, BmobException e) {
                            if (e != null) {
                            }else{
                            }
                        }
                    });
                }else{
//                    Toast.makeText(ExampleActivity.this, "开启会话出错", Toast.LENGTH_SHORT).show();
                    L.e("开启会话出错");
                }
            }
        });
    }
}
