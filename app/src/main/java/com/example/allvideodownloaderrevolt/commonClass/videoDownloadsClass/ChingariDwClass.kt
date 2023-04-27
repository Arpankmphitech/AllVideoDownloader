package com.example.allvideodownloaderrevolt.commonClass.videoDownloadsClass

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.IOException

class ChingariDwClass(var applicationContext: Context, var filePath: File) :
    AsyncTask<String?, Void?, Document?>() {
    var roposoDoc: Document? = null
    var videoUrl: String? = ""
    override fun doInBackground(vararg strArr: String?): Document? {
        try {
            roposoDoc = Jsoup.connect(strArr[0]).get()
        } catch (e: IOException) {
            Utils.HideProgressbarDialog()
            (applicationContext as? Activity)?.runOnUiThread {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
            e.printStackTrace()
        }
        return roposoDoc
    }

    public override fun onPostExecute(document: Document?) {
        try {
            videoUrl = document!!.select("meta[property=\"og:video\"]").last()!!.attr("content")
            if (videoUrl != "" && videoUrl != null) {
                val str = videoUrl
                Utils.newDownload(str!!, applicationContext)
                Log.d("06/09", "url--->$str")
                return
            }
            Utils.HideProgressbarDialog()
            (applicationContext as? Activity)?.runOnUiThread {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (unused: Exception) {
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