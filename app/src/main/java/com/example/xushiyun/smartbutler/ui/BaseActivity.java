package com.example.xushiyun.smartbutler.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.xushiyun.smartbutler.R;

/**
 * Created by xushiyun on 2017/12/5.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.ui
 * File Name:    BaseActivity
 * Descripetion: Activity基类
 */

/**
 * 主要做的事情：
 * 1.统一的属性
 * 2.统一的接口
 * 3.统一的方法
 */

public abstract class BaseActivity extends AppCompatActivity {
    public abstract Object layout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(layout()instanceof Integer) {
            setContentView((int)layout());
        }
        else if(layout()instanceof View) {
            setContentView((View)layout());
        }
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initListener();
        initData();
    }

    protected void initData(){

    }

    protected void initListener(){

    }

    protected void initView(){

    }

    //菜单栏操作


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
