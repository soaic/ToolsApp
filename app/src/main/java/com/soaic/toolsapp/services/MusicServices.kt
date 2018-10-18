package com.soaic.toolsapp.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicServices : Service(){


    private lateinit var musicPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()

        musicPlayer = MediaPlayer()

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }


    fun play() : Boolean {


        return false
    }

    fun pause() : Boolean {



        return false
    }

    fun nextMusic() : Boolean {

        return false
    }

    fun prevMusic() : Boolean {

        return false
    }
}