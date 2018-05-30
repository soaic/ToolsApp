package com.soaic.toolsapp.model

class Music {
    var id: String = ""
    var name: String = ""
    var singer: String = ""
    var album: String = ""
    var picture: String = ""


    constructor()

    constructor(id: String, name: String, singer: String, album: String, picture: String) {
        this.id = id
        this.name = name
        this.picture = picture
        this.singer = singer
        this.album = album
    }


}