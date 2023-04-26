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
        lateinit var binding: ActVideoListBinding
        var adapter: AdapterVideoList? = null
        var mGetMedia: GetMedia? = null
        var videoModelList: List<VideoModel>? = null
        lateinit var videoActivity: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActVideoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoActivity = this@VideoListAct
        Utils.setStatusBarSkyGradientActivity(activity)
        initToolBar()
        initView()
        LoadVideos().execute()
        binding.LayoutRefersh.setOnRefreshListener { LoadVideos().execute(*arrayOfNulls<String>(0)) }
    }


    private fun initView() {
        binding.rcvFolderList.layoutManager = GridLayoutManager(this, 1)
        mGetMedia = GetMedia(this)
    }

    private fun initToolBar() {
        binding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        binding.toolBar.txtTitleName.text = videoActivity.resources.getString(R.string.videos)
    }

    internal class LoadVideos :
        AsyncTask<String?, String?, List<VideoModel>>() {
        public override fun onPreExecute() {
            super.onPreExecute()
            binding.txtNoDataFound.visibility = View.GONE
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
                binding.rcvFolderList.layoutManager = GridLayoutManager(videoActivity,
                    1)
                adapter = AdapterVideoList(videoActivity, videoModelList!!)
                binding.rcvFolderList.adapter = adapter
            } else {
                binding.txtNoDataFound.visibility = View.VISIBLE
            }
            binding.LayoutRefersh.isRefreshing = false
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