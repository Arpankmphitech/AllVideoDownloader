package com.example.allvideodownloaderrevolt.frag

import android.annotation.SuppressLint
import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.allvideodownloaderrevolt.adapter.AdapterVideoFolder
import com.example.allvideodownloaderrevolt.commonClass.GetMedia
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.FragVideoBinding
import com.example.allvideodownloaderrevolt.modelsClass.FolderModel

class VideoFrag : Fragment() {

    companion object {
        lateinit var binding : FragVideoBinding
        lateinit var videoActivity: Activity
        var adapter: AdapterVideoFolder? = null
        var mGetMedia: GetMedia? = null
        var folderModelList: ArrayList<FolderModel>? = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragVideoBinding.inflate(layoutInflater)
        videoActivity = requireActivity()
        initView()
        return binding.root
    }

    private fun initView() {
        mGetMedia = GetMedia(videoActivity)
        LoadVideos().execute()
        binding.LayoutRefersh.isRefreshing = true
        binding.LayoutRefersh.setOnRefreshListener { LoadVideos().execute(*arrayOfNulls<String>(0)) }
    }

    @SuppressLint("StaticFieldLeak")
    internal class LoadVideos :
        AsyncTask<String?, String?, List<FolderModel>>() {
        @Deprecated("Deprecated in Java")
        public override fun onPreExecute() {
            super.onPreExecute()
            binding.txtNoDataFound.visibility = View.GONE
        }

        override fun doInBackground(vararg strArr: String?): List<FolderModel> {
            if (folderModelList != null) {
                folderModelList?.clear()
            }
            folderModelList = mGetMedia?.getfolderList()
            return mGetMedia?.getfolderList()!!
        }

        public override fun onPostExecute(list: List<FolderModel>) {
            super.onPostExecute(list)
            if (list.isNotEmpty()) {
                binding.rcvFolderList.layoutManager = GridLayoutManager(videoActivity, 1)
                adapter = AdapterVideoFolder(videoActivity, list)
                binding.rcvFolderList.adapter = adapter
            } else {
                binding.txtNoDataFound.visibility = View.VISIBLE
            }
            binding.LayoutRefersh.isRefreshing = false
        }
    }

    override fun onStart() {
        super.onStart()
        videoActivity.let { Utils.loadInter(it) }
    }

}