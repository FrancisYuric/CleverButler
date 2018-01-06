package com.example.xushiyun.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.xushiyun.smartbutler.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by xushiyun on 2017/12/10.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.utils
 * File Name:    PicassoUtils
 * Descripetion: Todo
 */

public class PicassoUtils {
    //默认加载图片
    public static void loadImageView(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }

    //默认加载图片指定大小
    public static void loadImageViewSize(Context context, String url, int width, int height, ImageView imageView) {
        Picasso.with(context).load(url).resize(width, height).centerCrop().into(imageView);
    }

    //加载默认图片,错误图片,完成图片,placeholder的参数是默认的图片
    public static void loadImageViewHolder(Context context, String url,int loadImage, int errorImage, ImageView imageView) {
        Picasso.with(context).load(url).placeholder(loadImage).error(errorImage).into(imageView);
    }

    //裁剪图片的方法是用来进行相应的裁剪的图片,官方文档有提供
    public static void loadImageViewCrop(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).transform(new CropSquareTransformation()).into(imageView);
    }

    //按比例裁剪
    public static class CropSquareTransformation implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size)/2;
            int y = (source.getHeight() - size)/2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if(result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return null;
        }
    }
}
