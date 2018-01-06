package com.example.xushiyun.smartbutler.entity;

/**
 * Created by xushiyun on 2017/12/9.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.entity
 * File Name:    ChatListData
 * Descripetion: Todo
 */

public class ChatListData {
    private Boolean type;//这里使用boolean类型进行数据类型的储存功能,false表示左边,true表示右边
    private String data;//这个项目里面好像只有要求这个数据= =,很尴尬,其实感觉还有很多数据需要表示的= =,比如说时间啊等等

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
