package com.soaic.toolsapp

import android.app.Application
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.soaic.libcommon.utils.AppUtils

class SApp: Application() {

    override fun onCreate() {
        super.onCreate()


        BGASwipeBackHelper.init(this, null)

    }

}