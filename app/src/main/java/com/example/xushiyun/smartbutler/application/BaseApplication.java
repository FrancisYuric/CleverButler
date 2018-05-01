package com.example.xushiyun.smartbutler.application;

import android.app.Application;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.example.xushiyun.smartbutler.ExampleActivity;
import com.example.xushiyun.smartbutler.handler.MyIMMessageHandler;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xushiyun on 2017/12/5.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.application
 * File Name:    BaseApplication
 * Descripetion: Todo
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);

        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=" + StaticClass.XFYUN_APP_ID);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        //TODO 集成：1.8、初始化IM SDK，并注册消息接收器
        initBmobIM();

        initIconify();
    }

    private void initIconify() {
        Iconify
                .with(new FontAwesomeModule())
                .with(new MaterialModule())
                .with(new IoniconsModule());
    }

    private void initBmobIM() {
        //测试成功,已经能够集成bmob sdk
        Bmob.initialize(this, "7801fcefd53f15f9c2e01615634767bc");


        //TODO 集成：1.8、初始化IM SDK，并注册消息接收器
//        初始化方法包含了DataSDK的初始化步骤，故无需再初始化DataSDK。
//        最好判断只有主进程运行的时候才进行初始化，避免资源浪费
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            BmobIM.init(this);
            BmobIM.registerDefaultMessageHandler(new ExampleActivity.ImMessageHandler());
        }
    }


    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
