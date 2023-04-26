package com.example.allvideodownloaderrevolt.models

import java.io.Serializable

class AudioModel : Serializable {
    var aAlbum: String? = null
    var aArtist: String? = null
    var aName: String? = null
    var aPath: String? = null
    fun getaPath(): String? {
        return aPath
    }

    fun setaPath(str: String?) {
        aPath = str
    }

    fun getaName(): String? {
        return aName
    }

    fun setaName(str: String?) {
        aName = str
    }

    fun getaAlbum(): String? {
        return aAlbum
    }

    fun setaAlbum(str: String?) {
        aAlbum = str
    }

    fun getaArtist(): String? {
        return aArtist
    }

    fun setaArtist(str: String?) {
        aArtist = str
    }
}