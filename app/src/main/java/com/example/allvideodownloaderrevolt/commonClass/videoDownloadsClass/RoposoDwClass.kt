package com.example.allvideodownloaderrevolt.commonClass.videoDownloadsClass

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.IOException

class RoposoDwClass(
    var context: Context, var filePath: File, var url: String
) : AsyncTask<String?, Void?, Document?>() {
    var documentRoposo: Document? = null
    var videoUrl: String? = ""
    var f11758d = ArrayList<String>()

    override fun doInBackground(vararg strArr: String?): Document? {
        try {
            documentRoposo = Jsoup.connect(strArr[0]).get()
        } catch (e: IOException) {
            Utils.HideProgressbarDialog()
            (context as? Activity)?.runOnUiThread {
                Toast.makeText(
                    context, context.getString(R.string.valid_url), Toast.LENGTH_SHORT
                ).show()
            }
            e.printStackTrace()
        }
        return documentRoposo
    }

    public override fun onPostExecute(document: Document?) {
        try {
            var html = ""

            val m5483h = Jsoup.connect(url.trim()).timeout(600000).get()
//                .select("div[class=css-ptw7mq]")
//                .select("div[class=css-cssveg]")
//                .select("div[class=css-y2dkm8]")
                .select("video[class=css-y9nlbe]").select("video").select("source").attr("src")


            html = m5483h.toString()

            if (html != null && !html.equals("")) Utils.newDownload(html, context)

        } catch (unused: Exception) {
            Utils.HideProgressbarDialog()
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
