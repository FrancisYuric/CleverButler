package com.example.xushiyun.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xushiyun on 2017/12/5.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.utils
 * File Name:    UtilsTools
 * Descripetion: Todo
 */

public class UtilsTools {
    public static void setFont(Context context, TextView textView, String route) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), route);
        textView.setTypeface(font);
    }

    //将drawable储存到ShareUtil的过程进行封装
    public static void putImageToShare(Context context, Drawable drawable) {
        //保存
        //因为bitmapdraw是drawable的子类
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        //第一步,将bitma压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        //第二步:利用base64将我们的字节数组输出流转换成String
        byte[] bytes = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        //第三步:将String保存到shareUtil
        ShareUtils.putString(context,"image_title",imgString);

    }

    public static Bitmap getImageFromShare(Context context) {
        //拿到String字符串
        String img_string = ShareUtils.getString(context, "image_title", "");
        if(!img_string.equals("")) {
            //利用Base64将我们String转换
            byte[] bytes = Base64.decode(img_string, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(bytes);
            //生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            return bitmap;
        }
        return null;
    }
}
