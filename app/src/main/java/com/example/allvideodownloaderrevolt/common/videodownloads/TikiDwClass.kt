package com.video.downloader.free.allvideo.media.download.social.videos.hd.common.videodownload

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.common.Utils
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.util.regex.Pattern

class TikiDwClass internal constructor(var applicationContext: Context, var tikiPath: File) :
    AsyncTask<String?, Void?, Document?>() {
    var doc: Document? = null
    var pattern = Pattern.compile("window\\.data \\s*=\\s*(\\{.+?\\});")
    override fun doInBackground(vararg strArr: String?): Document? {
        try {
            doc = Jsoup.connect(strArr[0]).get()
        } catch (e: Exception) {
            e.printStackTrace()
            Utils.HideProgressbarDialog()
            (applicationContext as? Activity)?.runOnUiThread {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return doc
    }

    public override fun onPostExecute(document: Document?) {
        try {
            val matcher = pattern.matcher(document.toString())
            var str = ""
            while (matcher.find()) {
                str = matcher.group().replaceFirst("window.data = ".toRegex(), "").replace(";", "")
            }
            val jSONObject = JSONObject(str)
            val videoUrl = jSONObject.getString("video_url").replace("_4", "")
            Utils.newDownload(videoUrl, applicationContext)
            Log.d("07/09", "" + videoUrl)
        } catch (e2: Exception) {
            Utils.HideProgressbarDialog()
            (applicationContext as? Activity)?.runOnUiThread {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
            e2.printStackTrace()
        }
    }
}