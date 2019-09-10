package com.soaic.basecomponent.activity

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import com.soaic.basecomponent.R
import com.soaic.basecomponent.service.ServiceDemo
import com.soaic.libcommon.base.BasicActivity
import com.soaic.libcommon.utils.PermissionsUtils
import com.soaic.libcommon.utils.ToastUtils

class ServiceDemoActivity : BasicActivity(){

    override fun getContentView(): Int {
        return R.layout.activity_service_demo
    }

    override fun initVariables(savedInstanceState: Bundle?) {
    }

    override fun initViews() {

    }

    override fun initEvents() {

        val intent = Intent(baseContext, ServiceDemo::class.java)

        findViewById<Button>(R.id.startService).setOnClickListener{
            startService(intent)
        }

        findViewById<Button>(R.id.stopService).setOnClickListener{
            stopService(intent)
        }

        findViewById<Button>(R.id.bindService).setOnClickListener{
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        findViewById<Button>(R.id.unBindService).setOnClickListener{
            unbindService(serviceConnection)
        }
    }

    override fun loadData() {
    }

    private var serviceConnection = object: ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}