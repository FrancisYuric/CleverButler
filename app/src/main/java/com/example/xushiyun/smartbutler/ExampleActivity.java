package com.example.xushiyun.smartbutler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xushiyun.smartbutler.helper.EventBusHelper;
import com.example.xushiyun.smartbutler.helper.IMHelper;
import com.example.xushiyun.smartbutler.ui.BaseActivity;
import com.example.xushiyun.smartbutler.utils.ObjectUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.newim.event.MessageEvent;

/**
 * Created by xushiyun on 2018/4/9.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler
 * File Name:    ExampleActivity
 * Description: Todo
 */

public class ExampleActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_message)
    public TextView tv_message = null;
    @BindView(R.id.et_message)
    public EditText et_message = null;

    @BindView(R.id.connect_btn)
    public Button connect = null;
    @BindView(R.id.open)
    public Button open = null;

    private Unbinder mUnbinder = null;

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMessage(MessageEvent messageEvent) {
        tv_message.append("接收到：" + messageEvent.getMessage().getContent() + "\n");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        initSDKs();
        initView();

    }

    private void initSDKs() {
        EventBusHelper.register(this);
        mUnbinder = ButterKnife.bind(this);
    }

    private void initView() {
        connect.setOnClickListener(this);
        open.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unregister(this);
        IMHelper.disconnect();
        if(ObjectUtils.checkNotNull(mUnbinder)) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect_btn:
                IMHelper.connect();
                break;
            case R.id.open:
                IMHelper.openConversation(IMHelper.getCurrentUsername(), et_message.getText().toString());
                tv_message.append("发送者：" + et_message.getText().toString() + "\n");
                et_message.setText("");
                break;
            default:
                break;

        }
    }
}
