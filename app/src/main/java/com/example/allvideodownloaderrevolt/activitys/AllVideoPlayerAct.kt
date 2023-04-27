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

    lateinit var actAlPlayerBinding: ActAllVideoPlayerBinding
    private var videoList: List<VideoModel>? = null
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actAlPlayerBinding = ActAllVideoPlayerBinding.inflate(layoutInflater)
        setContentView(actAlPlayerBinding.root)
        activity = this@AllVideoPlayerAct
        Utils.setStatusBarSkyBlueGradientActivity(activity as AllVideoPlayerAct)
        initVideoPlayerToolBar()
        initVideoPlayerViewID()
    }

    private fun initVideoPlayerToolBar() {
        actAlPlayerBinding.toolBar.txtTitleName.text =
            activity.resources.getString(R.string.video_player)
        actAlPlayerBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
    }

    private fun initVideoPlayerViewID() {
        if (intent.extras != null) {
            position = intent.getIntExtra("position", 0)
            val videoList2: List<VideoModel>? = intent.getSerializableExtra("videoList") as List<VideoModel>?
            videoList = videoList2
            if (videoList2 == null || videoList2.isEmpty()) {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.toast_stg_wrong),
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val mediaController = MediaController(this)
            mediaController.setAnchorView(actAlPlayerBinding.vwVideoPlayer)
            actAlPlayerBinding.vwVideoPlayer.setMediaController(mediaController)
            actAlPlayerBinding.vwVideoPlayer.setVideoURI(Uri.parse(videoList!![position].data))
            actAlPlayerBinding.vwVideoPlayer.requestFocus()
            actAlPlayerBinding.vwVideoPlayer.start()
            return
        }
        Toast.makeText(
            activity,
            activity.resources.getString(R.string.toast_stg_wrong),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onBackPressed() {
        finish()
    }

}