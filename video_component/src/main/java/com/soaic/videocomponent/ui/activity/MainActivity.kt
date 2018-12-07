package com.soaic.videocomponent.ui.activity

import android.os.Bundle
import cn.jzvd.Jzvd
import com.soaic.libcommon.base.BasicActivity
import com.soaic.videocomponent.R

class MainActivity : BasicActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initVariables(savedInstanceState: Bundle?) {
    }

    override fun initViews() {
    }

    override fun initEvents() {
    }

    override fun loadData() {
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {                                     //处理视频返回
            return
        }
        super.onBackPressed()
    }
}
