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
import com.example.allvideodownloaderrevolt.adapter.AdapterMusic
import com.example.allvideodownloaderrevolt.commonClass.GetMedia
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.FragMusicBinding
import com.example.allvideodownloaderrevolt.modelsClass.AudioModel

class MusicFrag : Fragment() {

    companion object {
        lateinit var binding: FragMusicBinding
        lateinit var musicActivity: Activity
        var mGetMedia: GetMedia? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragMusicBinding.inflate(layoutInflater)
        musicActivity = requireActivity()
        initView()
        return binding.root
    }


    private fun initView() {
        mGetMedia = GetMedia(musicActivity)
        LoadVideos().execute(*arrayOfNulls<String>(0))
        binding.LayoutRefersh.isRefreshing = true
        binding.LayoutRefersh.setOnRefreshListener { LoadVideos().execute(*arrayOfNulls<String>(0)) }
    }

    @SuppressLint("StaticFieldLeak")
    internal class LoadVideos :
        AsyncTask<String?, String?, List<AudioModel>>() {
        public override fun onPreExecute() {
            super.onPreExecute()
            binding.txtNoDataFound.visibility = View.GONE
        }

        override fun doInBackground(vararg strArr: String?): List<AudioModel> {
            return mGetMedia?.allMusic!!
        }

        public override fun onPostExecute(list: List<AudioModel>) {
            super.onPostExecute(list)
            if (list.isNotEmpty()) {
                binding.rcvFolderList.layoutManager = GridLayoutManager(musicActivity, 1)
                binding.rcvFolderList.adapter = AdapterMusic(musicActivity, list)
            } else {
                binding.txtNoDataFound.visibility = View.VISIBLE
            }
            binding.LayoutRefersh.isRefreshing = false
        }
    }

    override fun onStart() {
        super.onStart()
        musicActivity.let { Utils.loadInter(it) }
    }

}