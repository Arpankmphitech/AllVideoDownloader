package com.example.allvideodownloaderrevolt.activitys

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.adapter.AdapterImageList
import com.example.allvideodownloaderrevolt.common.GetMedia
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActImageListBinding
import com.example.allvideodownloaderrevolt.models.GalleyPhotosListModel

class ImageListAct : BaseAct() {

    companion object {
        lateinit var binding: ActImageListBinding
        lateinit var imageActivity: Activity
        var getMedia: GetMedia? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActImageListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageActivity = this@ImageListAct
        Utils.setStatusBarSkyGradientActivity(imageActivity as ImageListAct)
        initToolBar()
        initView()
        LoadVideos().execute()
        binding.LayoutRefersh.setOnRefreshListener { LoadVideos().execute(*arrayOfNulls<String>(0)) }
    }

    private fun initView() {
        binding.rcvFolderList.layoutManager = GridLayoutManager(this, 1)
        getMedia = GetMedia(this)
    }


    private fun initToolBar() {
        binding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        binding.toolBar.txtTitleName.text = imageActivity.resources.getString(R.string.photo)
    }

    internal class LoadVideos : AsyncTask<String?, String?, List<GalleyPhotosListModel>>() {
        public override fun onPreExecute() {
            super.onPreExecute()
            binding.txtNoDataFound.visibility = View.GONE
        }

        override fun doInBackground(vararg strArr: String?): List<GalleyPhotosListModel>? {
            return getMedia!!.getImageFolder(imageActivity)[imageActivity.intent!!.getIntExtra(
                "pos", 0
            )].albumPhotos
        }

        public override fun onPostExecute(list: List<GalleyPhotosListModel>) {
            super.onPostExecute(list)
            if (list.isNotEmpty()) {
                binding.rcvFolderList.layoutManager = GridLayoutManager(imageActivity, 3)
                binding.rcvFolderList.adapter = AdapterImageList(imageActivity, list)
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