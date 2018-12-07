package com.soaic.videocomponent.response

import com.soaic.libcommon.base.BaseResponse
import com.soaic.videocomponent.model.VideoModel

class VideoResponse: BaseResponse() {
    var data: List<VideoModel> = mutableListOf()
}