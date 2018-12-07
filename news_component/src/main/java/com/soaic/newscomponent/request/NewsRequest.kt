package com.soaic.newscomponent.request

import android.content.Context
import com.soaic.libcommon.network.SNetClient
import com.soaic.libcommon.network.listener.OnResultListener
import com.soaic.newscomponent.response.NewsResponse

object NewsRequest {

    fun getNewsList(context: Context, page: Int, onResultListener: OnResultListener<NewsResponse>){

        //0-10 从0开始返回10条数据
        val url = "http://c.m.163.com/nc/article/headline/T1348647853363/${(page-1)*20}-20.html"
        SNetClient.with(context)
                .url(url)
                .build().get(NewsResponse::class.java, onResultListener)
    }
}