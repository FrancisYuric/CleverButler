package com.example.xushiyun.smartbutler.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by xushiyun on 2017/12/12.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.utils
 * File Name:    PermissionUtils
 * Descripetion: Todo
 */

public class PermissionUtils {
    //正常获取权限
    public static void checkPermissionForNormal(Activity activity, String permission) {
        //判断是否同意此权限
        if(ContextCompat.checkSelfPermission(activity,
               permission) != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但是用户拒绝了请求,此方法将返回true
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) activity,
                    permission)) {
                Toast.makeText(activity, "你之前拒绝过此权限", Toast.LENGTH_SHORT).show();
            }
            else {
                //申请权限
                ActivityCompat.requestPermissions(activity, new String[] {
                        permission
                },100);
            }
        }
        else {
            //说明之前同意过,直接执行拨打电话的方法
        }
    }
}
