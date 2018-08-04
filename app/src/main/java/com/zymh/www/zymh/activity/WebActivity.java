package com.zymh.www.zymh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.zymh.www.zymh.R;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    private WebSettings webSettings;
    private TextView tvTitle;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initData();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webview);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return true;
            }
        });
        webSettings = webView.getSettings();
        //启用支持javascript
        webSettings.setJavaScriptEnabled(true);
    }

    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        tvTitle.setText(title);
        webView.loadUrl(url);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }
}
