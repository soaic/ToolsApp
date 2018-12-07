package com.soaic.picturecomponent.request

import android.content.Context
import com.soaic.libcommon.network.SNetClient
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.picturecomponent.response.PictureResponse

object PictureRequest {

    fun getPictureList(context: Context, page: Int, onResultListener: OnResultListener<PictureResponse>){
        SNetClient.with(context)
                .url("https://www.apiopen.top/meituApi")
                .param("page",page.toString())
                .build().get(PictureResponse::class.java, onResultListener)
    }

}