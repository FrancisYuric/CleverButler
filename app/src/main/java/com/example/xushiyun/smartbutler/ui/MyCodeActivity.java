package com.example.xushiyun.smartbutler.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.xushiyun.smartbutler.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class MyCodeActivity extends BaseActivity {
    private ImageView iv_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_code);

        initView();
    }

    private void initView() {
        iv_scan = findViewById(R.id.iv_scan);
        int width = getResources().getDisplayMetrics().widthPixels;
        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("你的智能管家", width/2, width/2,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_scan.setImageBitmap(qrCodeBitmap);
    }
}
