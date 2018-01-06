package com.example.xushiyun.smartbutler.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.service.SmsService;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.PermissionUtils;
import com.example.xushiyun.smartbutler.utils.ShareUtils;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.google.gson.JsonObject;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private Switch sw_speak;
    private Switch sw_sms;

    private LinearLayout ll_update;
    private TextView tv_version_cur;

    private String versionName;
    private int versionCode;

    private LinearLayout ll_scan;
    private TextView scan_result;
    private LinearLayout ll_mycode;

    private LinearLayout ll_location;

    private LinearLayout ll_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        sw_speak = findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);

        Boolean speakopen = ShareUtils.getBoolean(this, "speakopen", false);
        if(speakopen) {
            sw_speak.setChecked(true);
        }

        sw_sms = findViewById(R.id.sw_sms);
        sw_sms.setOnClickListener(this);
        Boolean smsopen = ShareUtils.getBoolean(this, "smsopen", false);
        if(smsopen) {
            sw_sms.setChecked(true);
        }

        ll_update = findViewById(R.id.ll_update);
        ll_update.setOnClickListener(this);

        //在最初的初始化的时候需要进行当前apk版本的获取和显示工作
        try {
            getVersionNameCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //界面的初始化的时候需要显示对应的当前版本
        tv_version_cur = findViewById(R.id.version_cur);
        tv_version_cur.setText("当前版本: " + versionName);

        ll_scan = findViewById(R.id.ll_scan);
        ll_mycode = findViewById(R.id.ll_mycode);
        ll_scan.setOnClickListener(this);
        ll_mycode.setOnClickListener(this);
        scan_result = findViewById(R.id.scan_result);

        ll_location = findViewById(R.id.ll_location);
        ll_location.setOnClickListener(this);

        ll_about = findViewById(R.id.ll_about);
        ll_about.setOnClickListener(this);
    }

    private void getVersionNameCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
        versionName = packageInfo.versionName;
        versionCode = packageInfo.versionCode;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sw_speak:
                sw_speak.setSelected(!sw_speak.isSelected());
                //默认语音播放是关闭的,利用sp来进行储存
                ShareUtils.putBoolean(this, "speakopen", sw_speak.isChecked());
                break;
            case R.id.sw_sms:
                //确定开启,关闭服务逻辑,这里使用
                sw_sms.setSelected(!sw_sms.isSelected());
                Boolean status = sw_sms.isChecked();
                ShareUtils.putBoolean(this, "smsopen", status);
                if(status) {
//                    PermissionUtils.checkPermissionForNormal(this, Manifest.permission.SYSTEM_ALERT_WINDOW);
                    startService(new Intent(this, SmsService.class));
                }
                else stopService(new Intent(this, SmsService.class));
                break;
            case R.id.ll_update:
                L.i("点击了更新按钮");
                //当点击到了更新对应组件时,通过URL来获取对应的json数据进行解析
                RxVolley.get(StaticClass.CHECK_UPDATE_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i(t);
                        parsingJson(t);
                    }
                });
                break;
            case R.id.ll_scan:
                Intent openCameraIntent = new Intent(this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.ll_mycode:
                startActivity(new Intent(SettingActivity.this, MyCodeActivity.class));
                break;
            case R.id.ll_location:
                startActivity(new Intent(SettingActivity.this, LocationActivity.class));
                break;
            case R.id.ll_about:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;
        }
    }

    //解析json,比较服务器端的最新app的版本和本机上的版本,如果本机版本不是最新版本,执行相应操作
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            int versionCode = jsonObject.getInt("versionCode");
            String content = jsonObject.getString("content");
            final String url = jsonObject.getString("url");
            if(versionCode == this.versionCode) {
                Toast.makeText(this, "当前版本已经是最新版本", Toast.LENGTH_SHORT).show();
            }else {
                new AlertDialog.Builder(this).setTitle("有新版本啦!")
                        .setMessage(content).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SettingActivity.this, UpdateActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //什么都不会做,但是这个方法会自动执行dismiss方法
                    }
                }).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            scan_result.setText(scanResult);
        }
    }
}
