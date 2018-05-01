package com.example.xushiyun.smartbutler.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xushiyun.smartbutler.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.fragment
 * File Name:    IMFragment
 * Description: Todo
 */

public class IMFragment extends BaseFragment {

    @BindView(R.id.fragment_im_content_recyclerView)
    RecyclerView contentRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected Object setLayout() {
        return R.layout.fragment_im;
    }

    @Override
    protected void initView() {
        super.initView();
    }

}
