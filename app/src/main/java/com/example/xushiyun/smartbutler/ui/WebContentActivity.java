package com.example.xushiyun.smartbutler.ui;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.xushiyun.smartbutler.R;

public class WebContentActivity extends BaseActivity {
    private ProgressBar pb_loading;
    private WebView wb_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_content);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");

        getSupportActionBar().setTitle(title);

        pb_loading = findViewById(R.id.pb_loading);
        wb_content = findViewById(R.id.wb_content);

        wb_content.getSettings().setJavaScriptEnabled(true);
        wb_content.getSettings().setSupportZoom(true);
        wb_content.getSettings().setBuiltInZoomControls(true);

        wb_content.setWebChromeClient(new WebViewClient());
        wb_content.loadUrl(url);

        wb_content.setWebViewClient(new android.webkit.WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                else {
                    view.loadUrl(request.toString());
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

        });
    }

    public class WebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100) {
                pb_loading.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
