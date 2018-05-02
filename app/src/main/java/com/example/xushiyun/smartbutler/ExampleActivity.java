package com.example.xushiyun.smartbutler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.adapter.IMMultiAdapter;
import com.example.xushiyun.smartbutler.entity.IMEntity;
import com.example.xushiyun.smartbutler.ui.BaseActivity;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.IMEvent;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by xushiyun on 2018/4/9.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler
 * File Name:    ExampleActivity
 * Description: Todo
 */

public class ExampleActivity extends BaseActivity {

    private boolean isConnect = false;
    private EditText et_connect_id;
    private EditText et_receiver_id;
    private boolean isOpenConversation = false;
    private BmobIMConversation mBmobIMConversation;
    private  static  TextView tv_message;
    private EditText et_message;

    private IMMultiAdapter imMultiAdazpter;
    private final List<IMEntity> entityList = new ArrayList<>();
    private RecyclerView recyclerView;

    private Button connect;
    private Button open;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMessage(MessageEvent messageEvent) {
        tv_message.append("接收到："+messageEvent.getMessage().getContent()+"\n");
    }

//    public static class ImMessageHandler extends BmobIMMessageHandler {
//
//        @Override
//        public void onMessageReceive(MessageEvent messageEvent) {
//            super.onMessageReceive(messageEvent);
//            //在线消息
//            tv_message.append("接收到："+messageEvent.getMessage().getContent()+"\n");
//        }
//
//        @Override
//        public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
//            super.onOfflineReceive(offlineMessageEvent);
//            //离线消息，每次connect的时候会查询离线消息，如果有，此方法会被调用
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        initSDKs();
        initView();

    }

    private void initSDKs() {
        EventBus.getDefault().register(this);
    }

    private void initView() {
        et_connect_id = findViewById(R.id.et_connect_id);
        et_receiver_id = findViewById(R.id.et_receiver_id);
        tv_message = findViewById(R.id.tv_message);
        et_message = findViewById(R.id.et_message);
        connect = findViewById(R.id.connect_btn);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExampleActivity.this, "aaaaaaaaaa", Toast.LENGTH_SHORT).show();
                connect();
            }
        });
        open = findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConversation();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
    }

    private void connect() {
        BmobIM.connect(et_connect_id.getText().toString(), new ConnectListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    isConnect = true;
                    Log.e("TAG","服务器连接成功");
                }else {
                    Log.e("TAG",e.getMessage()+"  "+e.getErrorCode());
                }
            }
        });
    }

    private void openConversation() {
        BmobIMUserInfo info =new BmobIMUserInfo();
        info.setAvatar("填写接收者的头像");
        info.setUserId(et_receiver_id.getText().toString());
        info.setName("填写接收者的名字");
        BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
            @Override
            public void done(BmobIMConversation c, BmobException e) {
                if(e==null){
                    isOpenConversation = true;
                    //在此跳转到聊天页面或者直接转化
                    mBmobIMConversation=BmobIMConversation.obtain(BmobIMClient.getInstance(),c);
                    tv_message.append("发送者："+et_message.getText().toString()+"\n");
                    BmobIMTextMessage msg =new BmobIMTextMessage();
                    msg.setContent(et_message.getText().toString());
                    mBmobIMConversation.sendMessage(msg, new MessageSendListener() {
                        @Override
                        public void done(BmobIMMessage msg, BmobException e) {
                            if (e != null) {
                                et_message.setText("");
                            }else{
                            }
                        }
                    });
                }else{
                    Toast.makeText(ExampleActivity.this, "开启会话出错", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
