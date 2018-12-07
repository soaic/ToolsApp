package com.soaic.libcommon.base;

import android.os.Bundle;

import com.soaic.libcommon.R;
import com.soaic.libcommon.weiget.XWebView;

public class BasicWebActivity extends BasicActivity {
    private String curUrl;
    private String curTitle;
    private XWebView webView;
    public static String KEY_URL = "keyUrl";
    public static String KEY_TITLE = "keyTitle";

    @Override protected int getContentView(){
        return R.layout.activity_web;
    }

    @Override protected void initVariables(Bundle savedInstanceState) {
        curUrl = getIntent().getStringExtra(KEY_URL);
        curTitle = getIntent().getStringExtra(KEY_TITLE);
    }

    @Override protected void initViews() {
        webView = findViewById(R.id.webView);
    }

    @Override protected void initEvents() {
        webView.setOnWebViewListener(new XWebView.OnWebViewListener(){

            @Override
            public void onScrollChanged(int dx, int dy) {
            }

            @Override
            public void onProgressChanged(int process) {
                if(process == 100){
                    hideProgressDialog();
                }
            }

            @Override
            public void onTitleChanged(String title) {
            }

            @Override
            public void onUrlChanged(String url) {
            }
        });
    }

    @Override protected void loadData() {
        showProgressDialog();
        webView.loadUrl(curUrl);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        webView.onDestroy();
    }
}
