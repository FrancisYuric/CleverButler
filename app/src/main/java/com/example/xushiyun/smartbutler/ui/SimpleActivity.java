package com.example.xushiyun.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.xushiyun.smartbutler.utils.ObjectUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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

public abstract class SimpleActivity extends AppCompatActivity {
    protected Object setTitle() {
        return null;
    }

    private Unbinder mUnbinder;

    protected abstract Object setLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (setLayout() instanceof Integer) {
            setContentView((Integer) setLayout());
        } else if (setLayout() instanceof View) {
            setContentView((View) setLayout());
        }
        mUnbinder = ButterKnife.bind(this);
        onBindView();
        initView();
        initListener();
        initLogic();
        initData();
    }

    protected void initLogic(){

    }

    private void onBindView() {

    }

    protected void initData() {
        if (!ObjectUtils.checkNotNull(setTitle())) {
            if (setTitle() instanceof String) {
                getSupportActionBar().setTitle((CharSequence) setTitle());
            } else if (setTitle() instanceof Integer) {
                getSupportActionBar().setTitle((Integer) setTitle());
            }
        }
    }

    protected void initListener() {

    }

    protected void initView() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ObjectUtils.checkNotNull(mUnbinder)) {
            mUnbinder.unbind();
        }
    }
}
