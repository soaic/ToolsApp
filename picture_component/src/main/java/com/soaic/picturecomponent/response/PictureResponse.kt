package com.soaic.picturecomponent.response

import com.soaic.libcommon.base.BaseResponse
import com.soaic.picturecomponent.model.PictureModel

class PictureResponse: BaseResponse(){
    var data: List<PictureModel> = mutableListOf()
}