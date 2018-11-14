package com.soaic.toolsapp.response

import com.soaic.toolsapp.model.MusicBitrate
import com.soaic.toolsapp.model.MusicInfo

class MusicInfoResponse {

    var songinfo: MusicInfo = MusicInfo()
    var bitrate: MusicBitrate = MusicBitrate()
    var error_code: Int = 0

}