package com.soaic.videocomponent.request

import android.content.Context
import com.soaic.libcommon.network.SNetClient
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.videocomponent.response.VideoResponse

object VideoRequest {

    fun getVideoList(context: Context, page: Int, onResultListener: OnResultListener<VideoResponse>){
        SNetClient.with(context)
                .url("https://www.apiopen.top/satinGodApi")
                .param("type", "5") //1全部 2文字 3图片 4gif 5视频
                .param("page",page.toString())
                .build().get(VideoResponse::class.java, onResultListener)
    }
}