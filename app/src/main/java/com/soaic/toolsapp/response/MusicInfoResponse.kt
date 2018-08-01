package com.soaic.toolsapp.response

import com.soaic.toolsapp.model.MusicBitrate
import com.soaic.toolsapp.model.MusicInfo

class MusicInfoResponse {

    lateinit var songinfo: MusicInfo
    lateinit var bitrate: MusicBitrate
    var error_code: Int = 0

}