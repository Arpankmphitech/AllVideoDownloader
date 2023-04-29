package com.example.allvideodownloaderrevolt.frag

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allvideodownloaderrevolt.adapter.AdapterGallery
import com.example.allvideodownloaderrevolt.commonClass.GetMedia
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.FragVideoBinding
import com.example.allvideodownloaderrevolt.modelsClass.GalleyAlbumModel

class GalleryFrag : Fragment() {

    companion object {
        lateinit var binding: FragVideoBinding
        lateinit var galleryActivity: Activity
        var getMedia: GetMedia? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragVideoBinding.inflate(layoutInflater)
        galleryActivity = requireActivity()
        initView()
        return binding.root
    }

    private fun initView() {
        getMedia = GetMedia(galleryActivity)
        LoadVideos().execute(*arrayOfNulls<String>(0))
        binding.LayoutRefersh.isRefreshing = true
        binding.LayoutRefersh.setOnRefreshListener { LoadVideos().execute(*arrayOfNulls<String>(0)) }
    }

    internal class LoadVideos :
        AsyncTask<String?, String?, List<GalleyAlbumModel>>() {
        public override fun onPreExecute() {
            super.onPreExecute()
            binding.txtNoDataFound.visibility = View.GONE
        }

        override fun doInBackground(vararg strArr: String?): List<GalleyAlbumModel> {
            return getMedia?.getImageFolderList(galleryActivity)!!
        }

        public override fun onPostExecute(list: List<GalleyAlbumModel>) {
            super.onPostExecute(list)
            if (list.isNotEmpty()) {
                binding.rcvFolderList.layoutManager = GridLayoutManager(galleryActivity, 3)
                binding.rcvFolderList.adapter = AdapterGallery(galleryActivity, list)
            } else {
                binding.txtNoDataFound.visibility = View.VISIBLE
            }
            binding.LayoutRefersh.isRefreshing = false
        }
    }

    override fun onStart() {
        super.onStart()
        galleryActivity.let { Utils.loadInter(it) }
    }
}