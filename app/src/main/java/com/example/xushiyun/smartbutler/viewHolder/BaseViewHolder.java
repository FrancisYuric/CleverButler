package com.example.xushiyun.smartbutler.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.xushiyun.smartbutler.utils.ObjectUtils;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.viewHolder
 * File Name:    BaseViewHolder
 * Description: Todo
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    abstract Object setLayout();
    public BaseViewHolder(View itemView) {
        super(itemView);
        if(setLayout() instanceof Integer) {

        }
        initView();
    }

    protected void initView() {
    }
}
