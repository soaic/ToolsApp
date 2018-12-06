package com.soaic.toolsapp.response

import com.soaic.libcommon.base.BaseResponse
import com.soaic.toolsapp.model.VideoModel

class VideoResponse: BaseResponse() {
    var data: List<VideoModel> = mutableListOf()
}