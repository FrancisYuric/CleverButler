package com.example.xushiyun.smartbutler.entity;

import android.widget.ImageView;

/**
 * Created by xushiyun on 2017/12/11.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.entity
 * File Name:    GirlInfo
 * Descripetion: Todo
 */

public class GirlInfo {
    private String _id;
    private String desc;
    private String type;
    private String url;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
