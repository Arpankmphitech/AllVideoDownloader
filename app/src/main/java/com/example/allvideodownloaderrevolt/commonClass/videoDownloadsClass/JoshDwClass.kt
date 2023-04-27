package com.video.downloader.free.allvideo.media.download.social.videos.hd.common.videodownload

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.commonClass.Utils.newDownload
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.IOException

internal class JoshDwClass(var applicationContext: Context, var filePath: File) :
    AsyncTask<String?, Void?, Document?>() {
    var JoshDoc: Document? = null


    override fun doInBackground(vararg strArr: String?): Document? {
        try {
            Log.d("15/09", "try-->" + Jsoup.connect(strArr[0]).get())
            JoshDoc = Jsoup.connect(strArr[0]).get()
        } catch (e: IOException) {
            Utils.HideProgressbarDialog()
            (applicationContext as? Activity)?.runOnUiThread {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("15/09", "IOException--->" + e.message)
            }
            e.printStackTrace()
        }
        Log.d("15/09", "try-->$JoshDoc")
        return JoshDoc
    }

    override fun onPostExecute(document: Document?) {
        try {
            val html = document?.select("script[id=\"__NEXT_DATA__\"]")?.last()!!.html()
            Log.d("15/09", "" + html.isEmpty())

            if (html == "") {
                Utils.HideProgressbarDialog()
                (applicationContext as? Activity)?.runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        applicationContext.getString(R.string.valid_url),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
            val jSONObject = JSONObject(html)
            val str =
                jSONObject.getJSONObject("props").getJSONObject("pageProps").getJSONObject("detail")
                    .getJSONObject("data").getString("mp4_url").toString()
            Log.d("15/09", "" + str)
            newDownload(str,applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("15/09", "Exception--->" + e.message)
            Utils.HideProgressbarDialog()
            (applicationContext as? Activity)?.runOnUiThread {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}