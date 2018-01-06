package com.example.xushiyun.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.xushiyun.smartbutler.MainActivity;
import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.MyUser;
import com.example.xushiyun.smartbutler.utils.ShareUtils;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.example.xushiyun.smartbutler.utils.UtilsTools;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity {
    //启动页需要实现的功能,1.延迟2000ms 2.判断是否是第一次运行 3.自定义字体 4.Activity全屏主题

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.SPLASH:
                    if(isFirstOpen()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    //标题
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        handler.sendEmptyMessageDelayed(StaticClass.SPLASH, 2000);
        tv_title = (TextView) findViewById(R.id.tv_splash_title);
        UtilsTools.setFont(this, tv_title, StaticClass.SPLASH_FONT);
    }

    private Boolean isFirstOpen() {
        Boolean isFirstOpen = ShareUtils.getBoolean(this, StaticClass.ISFIRSTOPEN, true);
        if(isFirstOpen) {
            ShareUtils.putBoolean(this, StaticClass.ISFIRSTOPEN, false);
            return true;
        }
        return false;
    }

    //禁止返回
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
