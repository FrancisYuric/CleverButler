package com.example.xushiyun.smartbutler.ui;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.adapter.IMMultiAdapter;
import com.example.xushiyun.smartbutler.application.BaseApplication;
import com.example.xushiyun.smartbutler.constant.ConstantIM;
import com.example.xushiyun.smartbutler.entity.IMEntity;
import com.example.xushiyun.smartbutler.utils.InputUtils;
import com.example.xushiyun.smartbutler.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by xushiyun on 2018/5/1.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.ui
 * File Name:    IMActivity
 * Description: Todo
 */

public class IMActivity extends SimpleActivity implements View.OnClickListener {
    //用于输入需要发送的信息
    @BindView(R.id.inputEditText)
    public TextInputEditText inputEditText;

    @BindView(R.id.chat_log)
    public RecyclerView chat_log;

    @BindView(R.id.btn_send)
    public Button btn_send;

    private String sender_id = null;
    private String receiver_id = null;


    private IMMultiAdapter imMultiAdapter;
    private final List<IMEntity> imEntities = new ArrayList<>();

    @Override
    protected Object setLayout() {
        return R.layout.activity_im;
    }

    private String friendName = null;//当前聊天对象


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        receiver_id = getIntent().getStringExtra(ConstantIM.CURRENT_ID);
        if(TextUtils.isEmpty(receiver_id)) {
            receiver_id = sender_id;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_send.setOnClickListener(this);
    }

    @Override
    protected void initLogic() {
        super.initLogic();
        chat_log.setLayoutManager(new LinearLayoutManager(this));
        imMultiAdapter = new IMMultiAdapter(imEntities);
        chat_log.setAdapter(imMultiAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                imEntities.add(new IMEntity(0, null, "111111111"));
                imMultiAdapter.notifyDataSetChanged();
                chat_log.scrollToPosition(imEntities.size() - 1);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            final View v = getCurrentFocus();
            if (InputUtils.isShouldHideInput(v, ev)) {
                InputUtils.hideNormalInputKeyBoard(this, v);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少,否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
}
