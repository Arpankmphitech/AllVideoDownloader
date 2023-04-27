package com.example.allvideodownloaderrevolt.commonClass.videodownload

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.util.regex.Pattern

class TikiDwClass internal constructor(var context: Context, var tikiPath: File) :
    AsyncTask<String?, Void?, Document?>() {
    var doc: Document? = null
    var pattern = Pattern.compile("window\\.data \\s*=\\s*(\\{.+?\\});")
    override fun doInBackground(vararg strArr: String?): Document? {
        try {
            doc = Jsoup.connect(strArr[0]).get()
        } catch (e: Exception) {
            e.printStackTrace()
            Utils.HideProgressbarDialog()
            (context as? Activity)?.runOnUiThread {
                Toast.makeText(
                    context,
                    context.getString(R.string.valid_url),
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
            Utils.newDownload(videoUrl, context)
            Log.d("07/09", "" + videoUrl)
        } catch (e2: Exception) {
            Utils.HideProgressbarDialog()
            (context as? Activity)?.runOnUiThread {
                Toast.makeText(
                    context,
                    context.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
            e2.printStackTrace()
        }
    }
}