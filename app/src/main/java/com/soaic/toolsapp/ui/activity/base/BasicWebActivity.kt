package com.soaic.toolsapp.ui.activity.base

import android.os.Bundle
import com.soaic.libcommon.weiget.XWebView
import com.soaic.toolsapp.R

class BasicWebActivity : BasicActivity(){

    private lateinit var curUrl : String
    private lateinit var curTitle : String
    private lateinit var webView : XWebView

    companion object {
        const val KEY_URL = "keyUrl"
        const val KEY_TITLE = "keyTitle"
    }

    override val contentView: Int
        get() = R.layout.activity_web

    override fun initVariables(savedInstanceState: Bundle?) {
        curUrl = intent.getStringExtra(KEY_URL)
        curTitle = intent.getStringExtra(KEY_TITLE)
    }

    override fun initViews() {
        webView = findViewById(R.id.webView)
    }

    override fun initEvents() {
        webView.setOnWebViewListener(object : XWebView.OnWebViewListener{
            override fun onScrollChanged(dx: Int, dy: Int) {
            }

            override fun onProgressChanged(process: Int) {
                if(process == 100){
                    hideProgressDialog()
                }
            }

            override fun onTitleChanged(title: String) {

            }

            override fun onUrlChanged(url: String?) {
            }
        })
    }

    override fun loadData() {
        showProgressDialog()
        webView.loadUrl(curUrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.onDestroy()
    }
}