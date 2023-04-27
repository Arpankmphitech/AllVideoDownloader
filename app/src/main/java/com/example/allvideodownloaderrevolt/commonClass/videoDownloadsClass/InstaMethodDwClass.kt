package com.example.allvideodownloaderrevolt.commonClass.videoDownloadsClass

import android.app.Activity
import com.example.allvideodownloaderrevolt.interfacesClass.InstaOnClick

class InstaMethodDwClass(var activity: Activity?, var  instaOnClick: InstaOnClick) {

    companion object {
        var isDownload: Boolean = true
    }

    fun onClick(position: Int, type: String?, data: String?) {
        instaOnClick.show(position, type, data)
    }
}