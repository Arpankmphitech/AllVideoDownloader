package com.example.allvideodownloaderrevolt.modelsClass

import androidx.documentfile.provider.DocumentFile
import java.io.File

/* loaded from: classes2.dex */
class StatusModel {
    var documentFile: DocumentFile? = null
    var file: File? = null
    var isApi30 = true
    var isVideo: Boolean
    var isImage: Boolean
    var path: String? = null
    var title: String? = null

    constructor(file: File, str: String?, str2: String?) {
        this.file = file
        title = str
        path = str2
        isVideo = file.name.endsWith(".mp4")
        isImage = file.name.endsWith(".jpg")
    }

    constructor(documentFile: DocumentFile) {
        this.documentFile = documentFile
        val name = documentFile.name
        isVideo = name?.endsWith(".mp4")!!
        isImage = name?.endsWith(".jpg")!!
    }
}