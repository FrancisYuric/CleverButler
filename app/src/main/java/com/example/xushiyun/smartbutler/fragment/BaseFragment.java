package com.example.xushiyun.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.fragment
 * File Name:    BaseFragment
 * Description: Todo
 */

public abstract class BaseFragment extends Fragment {

    protected abstract Object setLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (setLayout() instanceof Integer) {
            view = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            view = (View)setLayout();
        }
        initView();
        initListener();
        initViewContent();
        initData();

        return view;
    }

    protected void initData() {

    }

    protected void initViewContent() {

    }

    protected void initListener() {

    }

    protected void initView() {

    }

}
