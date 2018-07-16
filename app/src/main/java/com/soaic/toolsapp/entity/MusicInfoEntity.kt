package com.soaic.toolsapp.entity

import com.soaic.toolsapp.model.MusicBitrate
import com.soaic.toolsapp.model.MusicInfo

class MusicInfoEntity {

    lateinit var songinfo: MusicInfo
    lateinit var bitrate: MusicBitrate
    var error_code: Int = 0

}