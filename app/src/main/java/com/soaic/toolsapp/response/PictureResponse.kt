package com.soaic.toolsapp.response

import com.soaic.libcommon.base.BaseResponse
import com.soaic.toolsapp.model.PictureModel

class PictureResponse: BaseResponse(){
    var data: List<PictureModel> = mutableListOf()
}