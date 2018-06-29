package com.soaic.toolsapp

import android.app.Application
import com.soaic.libcommon.utils.AppUtils

class SApp: Application() {

    override fun onCreate() {
        super.onCreate()
        AppUtils.switchLanguage(applicationContext, AppUtils.getAppLanguage(applicationContext))
    }

}