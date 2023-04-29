package com.example.allvideodownloaderrevolt.act

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.adapter.AdapterImageList
import com.example.allvideodownloaderrevolt.commonClass.GetMedia
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActImageListBinding
import com.example.allvideodownloaderrevolt.modelsClass.GalleyPhotosListModel

class ImageListAct : BaseAct() {

    companion object {
        lateinit var actImgListBinding: ActImageListBinding
        lateinit var imageAct: Activity
        var getMedia: GetMedia? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actImgListBinding = ActImageListBinding.inflate(layoutInflater)
        setContentView(actImgListBinding.root)
        imageAct = this@ImageListAct
        Utils.setStatusBarSkyGradientActivity(imageAct as ImageListAct)
        ImageInitToolBar()
        ImageinitView()
        ImageLoadVideos().execute()
        actImgListBinding.LayoutRefersh.setOnRefreshListener { ImageLoadVideos().execute(*arrayOfNulls<String>(0)) }
    }

    private fun ImageinitView() {
        actImgListBinding.rcvFolderList.layoutManager = GridLayoutManager(this, 1)
        getMedia = GetMedia(this)
    }


    private fun ImageInitToolBar() {
        actImgListBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        actImgListBinding.toolBar.txtTitleName.text = imageAct.resources.getString(R.string.photo)
    }

    internal class ImageLoadVideos : AsyncTask<String?, String?, List<GalleyPhotosListModel>>() {
        public override fun onPreExecute() {
            super.onPreExecute()
            actImgListBinding.txtNoDataFound.visibility = View.GONE
        }

        override fun doInBackground(vararg strArr: String?): List<GalleyPhotosListModel>? {
            return getMedia!!.getImageFolderList(imageAct)[imageAct.intent!!.getIntExtra(
                "pos", 0
            )].albumPhotos
        }

        public override fun onPostExecute(list: List<GalleyPhotosListModel>) {
            super.onPostExecute(list)
            if (list.isNotEmpty()) {
                actImgListBinding.rcvFolderList.layoutManager = GridLayoutManager(imageAct, 3)
                actImgListBinding.rcvFolderList.adapter = AdapterImageList(imageAct, list)
            } else {
                actImgListBinding.txtNoDataFound.visibility = View.VISIBLE
            }
            actImgListBinding.LayoutRefersh.isRefreshing = false
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