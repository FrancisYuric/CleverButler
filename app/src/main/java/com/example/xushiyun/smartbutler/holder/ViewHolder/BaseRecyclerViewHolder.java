package com.example.xushiyun.smartbutler.holder.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xushiyun on 2018/5/1.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.holder.ViewHolder
 * File Name:    BaseRecyclerViewHolder
 * Description: Todo
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        initView();
        initListener();
        initData();
    }

    protected void initData() {

    }

    protected void initListener() {
    }

    protected void initView() {

    }

}
