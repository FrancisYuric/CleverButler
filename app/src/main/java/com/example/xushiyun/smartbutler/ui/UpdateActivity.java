package com.example.xushiyun.smartbutler.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

/**
 * 这个界面的主要目的是为了获取settingactivity传递过来的参数(压缩包的地址),
 * 然后从服务器端获取对应的数据,根据下载数据的具体情况来进行下载进度的显示,包括进度条,数字信息的显示
 */
public class UpdateActivity extends BaseActivity {
    private NumberProgressBar numberProgressBar;
    private String path;
    private TextView tv_size;
    private String url;
    //handler大兄弟用于处理相应的
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_LOADING:
                    Bundle bundle = msg.getData();
                    //实时更新进度
                    long transferredBytes =
                            bundle.getLong("transferredBytes");
                    long totalSize = bundle.getLong("totalSize");
                    tv_size.setText(transferredBytes + "/" + totalSize);
                    //在每次下载进度发生改变的时候进行进度条的更新操作
                    numberProgressBar.setProgress((int)((float) transferredBytes / (float)totalSize * 100));
                    break;
                case StaticClass.HANDLER_OK:
                    tv_size.setText("下载成功");
                    //在字体改变为下载成功之后进行安装包的安装
                    startInstallApk();
                    break;
                case StaticClass.HANDLER_ON:
                    tv_size.setText("下载失败");
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initView();
        initDownloadSetting();
    }

    private void initDownloadSetting() {
        path = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".apk";
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        if(!TextUtils.isEmpty(url)) {
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    L.i("transferredBytes:" + transferredBytes + "totalSize:" + totalSize);
                    Message msg = new Message();
                    msg.what = StaticClass.HANDLER_LOADING;
                    Bundle bundle = new Bundle();
                    bundle.putLong("transferredBytes", transferredBytes);
                    bundle.putLong("totalSize", totalSize);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    L.e("成功");
                    mHandler.sendEmptyMessage(StaticClass.HANDLER_OK);
                }

                @Override
                public void onFailure(VolleyError error) {
                    super.onFailure(error);
                    L.e("失败");
                    mHandler.sendEmptyMessage(StaticClass.HANDLER_ON);
                }
            });
        }
    }

    private void initView() {
        //基本的界面初始化操作
        tv_size = findViewById(R.id.tv_size);
        numberProgressBar = findViewById(R.id.number_progress_bar);
        numberProgressBar.setMax(100);
    }

    //在下载完成之后进行app的安装= =
    private void startInstallApk() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
        startActivity(i);
        finish();
    }
}
