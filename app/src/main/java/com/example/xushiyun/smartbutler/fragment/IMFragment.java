package com.example.xushiyun.smartbutler.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.xushiyun.smartbutler.R;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.fragment
 * File Name:    IMFragment
 * Description: Todo
 */

public class IMFragment extends BaseFragment {
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private RecyclerView contentRecyclerView = null;
    @Override
    protected Object setLayout() {
        return R.layout.fragment_im;
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
