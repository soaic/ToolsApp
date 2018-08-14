package com.soaic.toolsapp.ui.activity.music

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.soaic.libcommon.glide.GlideUtil
import com.soaic.libcommon.network.SNetClient
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.toolsapp.R
import com.soaic.toolsapp.response.MusicInfoResponse
import com.soaic.toolsapp.ui.activity.base.BasicActivity
import com.soaic.toolsapp.weight.MusicPlayerView

class MusicDetailActivity : BasicActivity(){

    private lateinit var musicDetailPic: ImageView
    private lateinit var musicDetailName: TextView
    private lateinit var musicDetailPlay: MusicPlayerView

    override val contentView: Int
        get() = R.layout.activity_music_detail

    override fun initVariables(savedInstanceState: Bundle?) {
        val songId= intent.getStringExtra("songId")
        playMusic(songId)
    }

    override fun initViews() {
        musicDetailPic = findViewById(R.id.musicDetailPic)
        musicDetailName = findViewById(R.id.musicDetailName)
        musicDetailPlay = findViewById(R.id.musicDetailPlay)
    }

    override fun initEvents() {

    }

    override fun loadData() {

    }

    private fun playMusic(songId: String){
        showProgressDialog()
        SNetClient.with(applicationContext)
                .url("https://tingapi.ting.baidu.com/v1/restserver/ting")
                .param("method","baidu.ting.song.play")
                .param("songid",songId)
                .build()
                .get(MusicInfoResponse::class.java, object: OnResultListener<MusicInfoResponse> {
                    override fun onSuccess(t: MusicInfoResponse) {
                        hideProgressDialog()
                        musicDetailName.text = t.songinfo.title
                        GlideUtil.display(musicDetailPic, t.songinfo.pic_premium)
                        musicDetailPlay.setCoverURL(t.songinfo.pic_premium)
                        musicDetailPlay.start()
                    }
                    override fun onFailure(err: Throwable) {
                        hideProgressDialog()
                        err.printStackTrace()
                    }
                })
    }
}