package com.soaic.toolsapp.response

import com.soaic.toolsapp.model.VideoModel

class VideoResponse: BaseResponse() {
    var data: List<VideoModel> = mutableListOf()
}