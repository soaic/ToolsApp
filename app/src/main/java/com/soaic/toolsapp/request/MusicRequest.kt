package com.soaic.toolsapp.request

import android.content.Context
import com.soaic.libcommon.glide.GlideUtil
import com.soaic.libcommon.network.SNetClient
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.toolsapp.response.MusicInfoResponse
import com.soaic.toolsapp.response.MusicResponse

object MusicRequest {

    fun getMusicList(context: Context, size: Int, offset: Int, onResultListener: OnResultListener<MusicResponse>){
        SNetClient.with(context)
                .url("https://tingapi.ting.baidu.com/v1/restserver/ting/")
                .param("method","baidu.ting.billboard.billList")
                .param("type","1")
                .param("size", size.toString())
                .param("offset",offset.toString())
                .retryCount(2)
                .setServerErrorInterceptor(SServerErrorHandler())
                .build().get(MusicResponse::class.java, onResultListener)
    }

    fun getMusicDetail(context: Context, songId: String, onResultListener: OnResultListener<MusicInfoResponse>){
        SNetClient.with(context)
                .url("https://tingapi.ting.baidu.com/v1/restserver/ting")
                .param("method","baidu.ting.song.play")
                .param("songid",songId)
                .build()
                .get(MusicInfoResponse::class.java, onResultListener)
    }
}