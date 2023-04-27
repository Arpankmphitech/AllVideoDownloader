package com.example.allvideodownloaderrevolt.modelsClass

import java.util.*

class GalleyAlbumModel {
    var albumPhotos: Vector<GalleyPhotosListModel>? = null
        get() {
            if (field == null) {
                field = Vector()
            }
            return field
        }
    var coverUri: String? = null
    var id = 0
    var name: String? = null
}