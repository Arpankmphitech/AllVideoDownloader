package com.example.allvideodownloaderrevolt.activitys

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActAllVideoPlayerBinding
import com.example.allvideodownloaderrevolt.models.VideoModel

class AllVideoPlayerAct : BaseAct() {

    lateinit var binding: ActAllVideoPlayerBinding
    private var list: List<VideoModel>? = null
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActAllVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this@AllVideoPlayerAct
        Utils.setStatusBarSkyBlueGradientActivity(activity as AllVideoPlayerAct)
        initVideoPlayerToolBar()
        initVideoPlayerViewID()
    }

    private fun initVideoPlayerToolBar() {
        binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.video_player)
        binding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
    }

    private fun initVideoPlayerViewID() {
        if (intent.extras != null) {
            position = intent.getIntExtra("position", 0)
            val list2: List<VideoModel>? = intent.getSerializableExtra("list") as List<VideoModel>?
            list = list2
            if (list2 == null || list2.isEmpty()) {
                Toast.makeText(activity, activity.resources.getString(R.string.toast_stg_wrong), Toast.LENGTH_SHORT).show()
                return
            }
            val mediaController = MediaController(this)
            mediaController.setAnchorView(binding.vwVideoPlayer)
            binding.vwVideoPlayer.setMediaController(mediaController)
            binding.vwVideoPlayer.setVideoURI(Uri.parse(list!![position].data))
            binding.vwVideoPlayer.requestFocus()
            binding.vwVideoPlayer.start()
            return
        }
        Toast.makeText(activity, activity.resources.getString(R.string.toast_stg_wrong), Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        finish()
    }

}