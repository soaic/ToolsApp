package com.soaic.toolsapp

import android.app.Application
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper

class SApp: Application() {

    override fun onCreate() {
        super.onCreate()
        BGASwipeBackHelper.init(this, null)
    }

}