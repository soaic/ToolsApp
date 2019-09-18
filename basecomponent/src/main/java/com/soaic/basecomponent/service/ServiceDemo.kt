package com.soaic.basecomponent.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import com.soaic.basecomponent.R

import com.soaic.libcommon.utils.Logger

class ServiceDemo : Service() {

    lateinit var mNotificationManager: NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBind(intent: Intent): IBinder? {
        Logger.d(TAG, "onBind")
        createNotificationChannel()
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        Logger.d(TAG, "onCreate")

//        Thread {
//            run {
//                var i = 0
//                while (true) {
//                    Thread.sleep(5000)
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        sendNotification(i++)
//                    }
//                }
//            }
//        }.start()

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Logger.d(TAG, "onStartCommand")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // 通知渠道的id
        var id = "my_channel_01"
        // 用户可以看到的通知渠道的名字.
        var name = "渠道名"
        // 用户可以看到的通知渠道的描述
        var description = "描述"
        var importance = NotificationManager.IMPORTANCE_HIGH
        var mChannel = NotificationChannel(id, name, importance)
        // 配置通知渠道的属性
        mChannel.description = description
        // 设置通知出现时的闪灯（如果 android 设备支持的话）
        //mChannel.enableLights(false)
        //mChannel.lightColor = Color.RED
        // 设置通知出现时的震动（如果 android 设备支持的话）
        mChannel.enableVibration(false)
        //mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        // 最后在notificationmanager中创建该通知渠道 //
        mNotificationManager.createNotificationChannel(mChannel)


        sendNotification(1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNotification(process: Int){
        // 为该通知设置一个id
        var notifyID = 1
        // 通知渠道的id
        var channelId = "my_channel_01"
        // Create a notification and set the notification channel.
        var notification = Notification.Builder(this, channelId)
                .setContentTitle("New Message") .setContentText("You've received new messages.$process")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()

        //开启通知服务可以常驻后台，否则1分钟之内则会被系统杀掉
        startForeground(notifyID ,notification)
    }

    override fun onUnbind(intent: Intent): Boolean {
        Logger.d(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Logger.d(TAG, "onDestroy")
        super.onDestroy()
        stopForeground(true)
    }

    companion object {
        private const val TAG = "ServiceDemo"
    }
}
