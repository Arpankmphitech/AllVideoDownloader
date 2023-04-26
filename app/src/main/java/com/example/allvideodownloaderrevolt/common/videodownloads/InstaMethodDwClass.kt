package com.example.allvideodownloaderrevolt.common.videodownloads

import android.app.Activity
import com.example.allvideodownloaderrevolt.interfaces.InstaOnClick

class InstaMethodDwClass(var activity: Activity?, var  instaOnClick: InstaOnClick) {

    companion object {
        var isDownload: Boolean = true
    }

    fun onClick(position: Int, type: String?, data: String?) {
        instaOnClick.show(position, type, data)
    }
}