package com.soaic.newscomponent.response

import com.soaic.libcommon.base.BaseResponse
import com.soaic.newscomponent.model.NewsModel

class NewsResponse: BaseResponse() {

    private var T1348647853363: List<NewsModel> = mutableListOf()

    fun getData(): List<NewsModel>{
        return T1348647853363
    }

}