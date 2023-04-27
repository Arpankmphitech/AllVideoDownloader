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


internal class ShareChatDwClass(var context: Context, var filePath: File) :
    AsyncTask<String?, Void?, Document?>() {
    var documentShareChat: Document? = null


    override fun doInBackground(vararg strArr: String?): Document? {
        try {
            documentShareChat = Jsoup.connect(strArr[0]).get()
        } catch (e: IOException) {
            Utils.HideProgressbarDialog()
            (context as? Activity)?.runOnUiThread {
                Toast.makeText(
                    context,
                    context.getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("15/09", "IOException--->" + e.message)
            }
            e.printStackTrace()
        }
        return documentShareChat
    }

    override fun onPostExecute(document: Document?) {
        try {
            val demo = document!!.getElementsByTag("script")
            var html = ""

            for (i in demo) {
                for (node in i.dataNodes()) {
                    try {
                        var obj = JSONObject(node.wholeData)
                        html = obj.get("contentUrl").toString()
                        break
                    } catch (e: Exception) {

                    }
//                    if (node.wholeData.contains("contentUrl")) {
//                        html = node.wholeData.substring((node.wholeData.indexOf("contentUrl") + 13),
//                            (node.wholeData.indexOf("\"",
//                                node.wholeData.indexOf("contentUrl") + 14)))
//                        break
//                    }
                }
            }
            if (html != null && !html.equals(""))
                newDownload(html, context)
//                newDownloadShareChat(html,
//                    "/All Videos Downloader/Share Chat/", applicationContext,
//                    "Share Chat " + System.currentTimeMillis() + ".mp4")
            else {
                Utils.HideProgressbarDialog()
                (context as? Activity)?.runOnUiThread {
                    Toast.makeText(
                        context,
                        context.getString(R.string.valid_url),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("15/09", "Exception--->" + e.message)
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