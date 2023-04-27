package com.example.allvideodownloaderrevolt.commonClass.videodownload

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.commonClass.Utils.HideProgressbarDialog
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.IOException

class VimeoDwClass(var context: Context, var vimeoPath: File) :
    AsyncTask<String?, Void?, Document?>() {
    var roposoDoc: Document? = null
    var videoUrl: String? = ""
    override fun doInBackground(vararg strArr: String?): Document? {
        try {
            roposoDoc = Jsoup.connect("https://www.expertsphp.com/instagram-reels-downloader.php")
                .data("url", strArr[0]).userAgent("Mozilla").post()
        } catch (e: IOException) {
            e.printStackTrace()
            HideProgressbarDialog()
            (context as? Activity)?.runOnUiThread {
                Toast.makeText(
                    context,
                    context.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return roposoDoc
    }

    public override fun onPostExecute(document: Document?) {
        try {
            videoUrl = document!!.select("a[download]").first()!!.attr("href")
            if (videoUrl != "" && videoUrl != null) {
                val str = videoUrl
                Utils.newDownload(str!!,context)
                return
            }
            HideProgressbarDialog()
            (context as? Activity)?.runOnUiThread {
                Toast.makeText(
                    context,
                    context.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (unused: Exception) {
            HideProgressbarDialog()
            (context as? Activity)?.runOnUiThread {
                Toast.makeText(
                    context,
                    context.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}