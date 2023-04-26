package com.example.allvideodownloaderrevolt.activitys

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.adapter.AdapterVideoList
import com.example.allvideodownloaderrevolt.common.GetMedia
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActVideoListBinding
import com.example.allvideodownloaderrevolt.models.VideoModel
import java.util.ArrayList

class VideoListAct : BaseAct() {

    companion object {
        lateinit var actVideoListBinding: ActVideoListBinding
        var adapter: AdapterVideoList? = null
        var mGetMedia: GetMedia? = null
        var videoModelList: List<VideoModel>? = null
        lateinit var videoActivity: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actVideoListBinding = ActVideoListBinding.inflate(layoutInflater)
        setContentView(actVideoListBinding.root)
        videoActivity = this@VideoListAct
        Utils.setStatusBarSkyGradientActivity(activity)
        initToolBar()
        initView()
        LoadVideos().execute()
        actVideoListBinding.LayoutRefersh.setOnRefreshListener { LoadVideos().execute(*arrayOfNulls<String>(0)) }
    }


    private fun initView() {
        actVideoListBinding.rcvFolderList.layoutManager = GridLayoutManager(this, 1)
        mGetMedia = GetMedia(this)
    }

    private fun initToolBar() {
        actVideoListBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        actVideoListBinding.toolBar.txtTitleName.text = videoActivity.resources.getString(R.string.videos)
    }

    internal class LoadVideos :
        AsyncTask<String?, String?, List<VideoModel>>() {
        public override fun onPreExecute() {
            super.onPreExecute()
            actVideoListBinding.txtNoDataFound.visibility = View.GONE
        }

        override fun doInBackground(vararg strArr: String?): List<VideoModel>? {
            videoModelList = ArrayList<VideoModel>()
            if (videoModelList != null) {
                (videoModelList as ArrayList<VideoModel>).clear()
            }
            videoModelList = mGetMedia?.getVideoByFolder(videoActivity.intent!!
                .getStringExtra("Bucket"))
            return videoModelList!!
        }

        public override fun onPostExecute(list: List<VideoModel>) {
            super.onPostExecute(list)
            if (videoModelList?.size != 0) {
                actVideoListBinding.rcvFolderList.layoutManager = GridLayoutManager(videoActivity,
                    1)
                adapter = AdapterVideoList(videoActivity, videoModelList!!)
                actVideoListBinding.rcvFolderList.adapter = adapter
            } else {
                actVideoListBinding.txtNoDataFound.visibility = View.VISIBLE
            }
            actVideoListBinding.LayoutRefersh.isRefreshing = false
        }
    }

    override fun onStart() {
        super.onStart()
        Utils.loadInter(this)
        Utils.loadSmallNative(this, 2)
    }

    override fun onBackPressed() {
        Utils.displayInterOnBack(this)
    }

}