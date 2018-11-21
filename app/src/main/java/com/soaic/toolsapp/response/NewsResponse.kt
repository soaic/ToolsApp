package com.soaic.toolsapp.response

import com.soaic.toolsapp.model.NewsModel

class NewsResponse: BaseResponse() {

    private var T1348647853363: List<NewsModel> = mutableListOf()

    fun getData(): List<NewsModel>{
        return T1348647853363
    }

}