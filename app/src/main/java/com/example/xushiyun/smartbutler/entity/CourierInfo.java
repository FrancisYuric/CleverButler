package com.example.xushiyun.smartbutler.entity;

/**
 * Created by xushiyun on 2017/12/9.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.entity
 * File Name:    CourierInfo
 * Descripetion: Todo
 */

public class CourierInfo {
    private String datetime;
    private String zone;
    private String remark;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CourierInfo{" +
                "datetime='" + datetime + '\'' +
                ", zone='" + zone + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
