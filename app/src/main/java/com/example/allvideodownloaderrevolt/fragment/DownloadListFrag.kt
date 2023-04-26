package com.example.allvideodownloaderrevolt.fragment

import android.app.Activity
import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allvideodownloaderrevolt.adapter.AdapterDownloadVideoList
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.FragDownloadListBinding
import com.example.allvideodownloaderrevolt.models.VideoDownLoadModel
import com.google.gson.Gson
import java.util.*

class DownloadListFrag(var i: Int) : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        fun newInstance(i: Int): DownloadListFrag {
            return DownloadListFrag(i)
        }
    }

    var adapter: AdapterDownloadVideoList? = null
    lateinit var binding: FragDownloadListBinding
    lateinit var activity: Activity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        try {
            binding = FragDownloadListBinding.inflate(layoutInflater)
        } catch (e: Exception) {
        }
        activity = requireActivity()
        val view: FrameLayout = binding.root
        activity.loaderManager?.initLoader(14, null, this)
        val list = getFiles()
        Log.d("13/01", "" + Gson().toJson(list))
        if (list.isNotEmpty()) {
            binding.imgEmpty.visibility = View.GONE
            adapter = AdapterDownloadVideoList(list, activity)
            binding.rcvVideoList.layoutManager = GridLayoutManager(activity, 3)
            binding.rcvVideoList.adapter = adapter
        } else {
            binding.imgEmpty.visibility = View.VISIBLE
        }
        return view
    }

    private fun getFiles(): ArrayList<String?> {
        val list: ArrayList<String?> = ArrayList()
        val file = Constant.WHATSAPP_VIDEO_PATH

        val listFile = file.listFiles()

        if (listFile != null && listFile.isNullOrEmpty()) {
            Arrays.sort(listFile)
        }

        if (listFile != null) {
            for (imgFile in listFile) {
                if (
                    imgFile.name.endsWith(".mp4")
                ) {
                    val model = imgFile.absolutePath
                    list.add(model)
                }
            }
        }

        return list
    }


    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        val strArr: Array<String>?
        val str: String?
        val str2: String? = when (i) {
            1 -> Constant.WHATSAPP_VIDEO_PATH.path
            else -> Constant.WHATSAPP_VIDEO_PATH.path
        }
        strArr = arrayOf("%$str2%", "%$str2/%/%")
        str = "_data LIKE ? AND _data NOT LIKE ? "
        return CursorLoader(
            activity,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            str,
            strArr,
            "datetaken DESC"
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, cursor: Cursor?) {
        val proVideoList = ArrayList<VideoDownLoadModel>()
        var vidSize = 0L
        if (loader != null) {
            if (cursor?.count != 0) {
                Log.d("15/09", "count-->" + cursor?.count)
                cursor?.moveToFirst()
                do {
                    val modelVideoVar = VideoDownLoadModel()
                    modelVideoVar.date =
                        cursor?.getString(cursor.getColumnIndexOrThrow("datetaken"))
                    modelVideoVar.name =
                        cursor?.getString(cursor.getColumnIndexOrThrow("title"))
                    modelVideoVar.size = cursor?.getString(cursor.getColumnIndexOrThrow("_size"))
                    modelVideoVar.thumb_img =
                        cursor?.getString(cursor.getColumnIndexOrThrow("_data"))
                    modelVideoVar.duration =
                        cursor?.getLong(cursor.getColumnIndexOrThrow("duration"))
                            ?.let { java.lang.Long.valueOf(it) }
                    modelVideoVar.resolution =
                        cursor?.getLong(cursor.getColumnIndexOrThrow("height"))
                            .toString() + " * " + cursor?.getLong(cursor.getColumnIndexOrThrow("width"))
                    if (cursor?.getString(cursor.getColumnIndexOrThrow("_size")) == null) {
                        vidSize = 0L
                    } else {
                        vidSize += cursor.getString(cursor.getColumnIndexOrThrow("_size"))
                            .toLong()
                    }
                    modelVideoVar.isselected = false
                    proVideoList.add(modelVideoVar)
                } while (cursor?.moveToNext()!!)

                return
            }
        }
    }

    override fun onStart() {
        super.onStart()
        activity.let { Utils.loadInter(it) }
    }

}