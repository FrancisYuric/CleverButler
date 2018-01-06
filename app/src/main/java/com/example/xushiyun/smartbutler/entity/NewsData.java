package com.example.xushiyun.smartbutler.entity;

/**
 * Created by xushiyun on 2017/12/10.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.entity
 * File Name:    NewsData
 * Descripetion: Todo
 */

public class NewsData {
    private String title;
    private String time;
    private String pic;
    private String url;
    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
