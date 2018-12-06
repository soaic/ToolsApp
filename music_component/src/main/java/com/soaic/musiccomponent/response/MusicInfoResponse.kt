package com.soaic.musiccomponent.response

import com.soaic.musiccomponent.model.MusicBitrate
import com.soaic.musiccomponent.model.MusicInfo

class MusicInfoResponse {

    var songinfo: MusicInfo = MusicInfo()
    var bitrate: MusicBitrate = MusicBitrate()
    var error_code: Int = 0

}