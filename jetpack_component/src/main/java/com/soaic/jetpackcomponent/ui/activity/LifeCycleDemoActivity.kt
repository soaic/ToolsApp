package com.soaic.jetpackcomponent.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.soaic.jetpackcomponent.R
import com.soaic.jetpackcomponent.listener.MyLifecyclesListener
import com.soaic.libcommon.utils.Logger

class LifeCycleDemoActivity: AppCompatActivity() {
    private lateinit var myLocationListener: MyLifecyclesListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)
        myLocationListener = MyLifecyclesListener(this){
            Logger.d("lifecycle==========$it")
        }
        lifecycle.addObserver(myLocationListener)

    }

}