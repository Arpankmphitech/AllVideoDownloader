package com.example.allvideodownloaderrevolt.commonClass.videodownload

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.interfacesClass.AsyncResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class FacebookVideoLinkParserDwClass(asyncResponse: AsyncResponse?, activitys: Activity) :
    AsyncTask<String?, Void?, String>() {
    var delegate: AsyncResponse? = null //Call back interface
    var activity: Activity

    override fun onPostExecute(result: String) {
        delegate?.processFinish(result)
    }

    init {
        delegate = asyncResponse //Assigning call back interfacethrough constructor
        activity = activitys
    }

    override fun doInBackground(vararg urls: String?): String {
        try {
            Log.d("10/03", "2")

            val userAgent =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
            val document: Document = Jsoup.connect(urls[0]).userAgent(userAgent).get()
            Log.d("10/03", "document---${document}")

            val ogVideo: Elements = document.select("meta[property=og:video]")
            //replace ampersands
            Log.d("10/03", "ogVideo---${ogVideo}")
            var url: String = ogVideo.attr("content")
            url = url.replace("&amp;", "&")
            Log.d("10/03", "url---$url")
            if (url !== "" && url != null) {
                val str = url
                Utils.newDownload(str, activity)
                Log.d("06/09", "url--->$str")
            }
            return url
        } catch (e: IOException) {
            Log.d("10/03", "e---${e.message}")
        }
        return ""
    }
}