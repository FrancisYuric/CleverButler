package com.example.xushiyun.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xushiyun.smartbutler.utils.ObjectUtils;
import com.example.xushiyun.smartbutler.utils.UtilsTools;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.fragment
 * File Name:    BaseFragment
 * Description: Todo
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbinder;

    protected abstract Object setLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("setLayout() type must be int or View!");
        }
        if (!ObjectUtils.checkNotNull(rootView)) {
            mUnbinder = ButterKnife.bind(this, rootView);
        }
        onBindView(savedInstanceState, rootView);
        initView();
        initListener();
        initViewContent();
        initData();

        return rootView;
    }

    protected void onBindView(Bundle savedInstanceState, View rootView) {

    }

    protected void initData() {

    }

    protected void initViewContent() {

    }

    protected void initListener() {

    }

    protected void initView() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

}
