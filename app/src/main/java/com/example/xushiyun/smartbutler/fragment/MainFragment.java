package com.example.xushiyun.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.holder.view.LocalImageHolderView;
import com.example.xushiyun.smartbutler.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xushiyun on 2018/4/24.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.fragment
 * File Name:    CampusFragment
 * Description: Todo
 */

public class MainFragment extends Fragment {
    private ConvenientBanner convenientBanner;
    private List<Integer> localImages = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        initListener();
        initData();
        return view;
    }

    private void initData() {
        localImages.add(R.drawable.main_fragment_banner_img1);
        localImages.add(R.drawable.main_fragment_banner_img2);
        localImages.add(R.drawable.main_fragment_banner_img3);
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.point_off, R.drawable.point_on})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
        //convenientBanner.setManualPageable(false);//设置不能手动影响


    }

    private void initListener() {

    }

    private void initView(View view) {
        convenientBanner = view.findViewById(R.id.convenientBanner);
    }
}
