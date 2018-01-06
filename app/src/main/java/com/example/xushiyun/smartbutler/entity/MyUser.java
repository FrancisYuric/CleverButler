package com.example.xushiyun.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by xushiyun on 2017/12/6.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.entity
 * File Name:    MyUser
 * Descripetion: Todo
 */

public class MyUser extends BmobUser {
    private String age;
    private Boolean sex;
    private String desc;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
