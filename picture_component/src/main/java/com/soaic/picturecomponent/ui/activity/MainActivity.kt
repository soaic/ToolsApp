package com.soaic.picturecomponent.ui.activity

import android.os.Bundle
import com.soaic.libcommon.base.BasicActivity
import com.soaic.libcommon.glide.GlideSimpleLoader
import com.soaic.libcommon.weiget.imagewatcher.ImageWatcherHelper
import com.soaic.picturecomponent.R

class MainActivity :BasicActivity() {
    private lateinit var iwHelper: ImageWatcherHelper

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initVariables(savedInstanceState: Bundle?) {
        iwHelper = ImageWatcherHelper.with(this, GlideSimpleLoader())
    }

    override fun initViews() {
    }

    override fun initEvents() {
    }

    override fun loadData() {
    }

    override fun getImageWatchHelper(): ImageWatcherHelper {
        return iwHelper
    }

    override fun onBackPressed() {
        if (iwHelper.handleBackPressed()) {                         //处理ImageWatch返回
            return
        }
        super.onBackPressed()
    }

}
