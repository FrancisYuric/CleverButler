package com.example.xushiyun.smartbutler.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.xushiyun.smartbutler.R;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends BaseActivity {

    private ListView ll_info = null;
    private List<String> infos = null;
    private ArrayAdapter<String> arrayAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        ll_info = findViewById(R.id.lv_info);

        infos = new ArrayList<>();

        infos.add("应用名: " + getString(R.string.app_name));
        infos.add("版本: " + getAppVersionName());
        infos.add("官方网站 :" + "www.baidu.com");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, infos);
        ll_info.setAdapter(arrayAdapter);
    }

    private String getAppVersionName() {
        String appVersionName = null;
        PackageManager pm = getPackageManager();
        try {
            PackageInfo  packageInfo = pm.getPackageInfo(getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            return appVersionName;
        }
    }
}
