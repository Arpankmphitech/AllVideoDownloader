package com.example.allvideodownloaderrevolt.act

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.adapter.AdapterVideoList
import com.example.allvideodownloaderrevolt.commonClass.GetMedia
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActVideoListBinding
import com.example.allvideodownloaderrevolt.modelsClass.VideoModel
import java.util.ArrayList

class VideoListAct : BaseAct() {

    companion object {
        lateinit var actVideoListBinding: ActVideoListBinding
        var vListAdapter: AdapterVideoList? = null
        var mGetMedia: GetMedia? = null
        var videoListModel: List<VideoModel>? = null
        lateinit var videoActivity: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actVideoListBinding = ActVideoListBinding.inflate(layoutInflater)
        setContentView(actVideoListBinding.root)
        videoActivity = this@VideoListAct
        activity
        Utils.setStatusBarSkyGradientActivity(activity!!)
        videoListToolBar()
        videoListInitView()
        LoadVideos().execute()
        actVideoListBinding.LayoutRefersh.setOnRefreshListener { LoadVideos().execute(*arrayOfNulls<String>(0)) }
    }


    private fun videoListInitView() {
        actVideoListBinding.rcvFolderList.layoutManager = GridLayoutManager(this, 1)
        mGetMedia = GetMedia(this)
    }

    private fun videoListToolBar() {
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
            videoListModel = ArrayList<VideoModel>()
            if (videoListModel != null) {
                (videoListModel as ArrayList<VideoModel>).clear()
            }
            videoListModel = mGetMedia?.getVideoByFolder(videoActivity.intent!!
                .getStringExtra("Bucket"))
            return videoListModel!!
        }

        public override fun onPostExecute(list: List<VideoModel>) {
            super.onPostExecute(list)
            if (videoListModel?.size != 0) {
                actVideoListBinding.rcvFolderList.layoutManager = GridLayoutManager(videoActivity,
                    1)
                vListAdapter = AdapterVideoList(videoActivity, videoListModel!!)
                actVideoListBinding.rcvFolderList.adapter = vListAdapter
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