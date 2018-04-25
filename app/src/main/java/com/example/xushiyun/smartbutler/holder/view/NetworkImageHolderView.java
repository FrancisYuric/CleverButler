package com.example.xushiyun.smartbutler.holder.view;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.xushiyun.smartbutler.R;
import com.squareup.picasso.Picasso;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.holder.view
 * File Name:    NetworkImageHolderView
 * Description: Todo
 */

public class NetworkImageHolderView implements Holder<String> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        imageView.setImageResource(R.mipmap.ic_launcher);
        Picasso.with(context).load(data).placeholder(R.mipmap.ic_launcher).fit().into(imageView);
//        Glide.with(context).load(data).into(imageView);
    }
}
