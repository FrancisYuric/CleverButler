package com.example.xushiyun.smartbutler.entity;

import java.io.Serializable;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.entity
 * File Name:    IMEntity
 * Description: Todo
 */

public class IMEntity implements Serializable {
    public int status = 0;
    public String icon_url = "";
    public String content ="";

    public IMEntity(int status, String icon_url, String content) {
        this.status = status;
        this.icon_url = icon_url;
        this.content = content;
    }
}
