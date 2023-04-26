package com.example.allvideodownloaderrevolt.activitys

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActDownloadVideoPlayerBinding
import com.universalvideoview.UniversalVideoView

class DownloadVideoPlayerAct : AppCompatActivity() {

    lateinit var binding: ActDownloadVideoPlayerBinding
    var mBottomLayout: View? = null
    var mVideoLayout: View? = null
    var isFullScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActDownloadVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.setStatusBarBlackColorActivity(this)
        binding.universalVideoView.setMediaController(binding.universalMediaController)
        val fileUri = intent.getParcelableExtra(Intent.EXTRA_STREAM) as Uri?

        if (fileUri != null) {
            binding.universalVideoView.setVideoURI(fileUri)
        } else {
            binding.universalVideoView.setVideoURI(Uri.parse(intent.getStringExtra("path")))
        }

        initView()

    }

    private fun initView() {
        intent.getStringExtra("path")?.let {
            if (!videoFileIsCorrupted(it)) {
                Utils.showAlertWithFinis(
                    this, getString(R.string.app_name), "Video file is corrupted", true
                )
            }
        }
        binding.universalVideoView.setVideoViewCallback(object :
            UniversalVideoView.VideoViewCallback {
            override fun onScaleChange(isFullscreen: Boolean) {
                isFullScreen = isFullscreen
                if (isFullscreen) {
                    val layoutParams = mVideoLayout?.layoutParams
                    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    mVideoLayout?.layoutParams = layoutParams
                    //GONE the unconcerned views to leave room for video and controller
                    mBottomLayout?.visibility = View.GONE
                } else {
                    val layoutParams = mVideoLayout?.layoutParams
                    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    mVideoLayout?.layoutParams = layoutParams
                    mBottomLayout?.visibility = View.VISIBLE
                }
            }

            override fun onPause(mediaPlayer: MediaPlayer) { // Video pause
                Log.d("2122", "onPause UniversalVideoView callback")
            }

            override fun onStart(mediaPlayer: MediaPlayer) { // Video start/resume to play
                binding.progressCircle.visibility = View.GONE
                Log.d("2122", "onStart UniversalVideoView callback")
            }

            override fun onBufferingStart(mediaPlayer: MediaPlayer) { // steam start loading
                Log.d("2122", "onBufferingStart UniversalVideoView callback")
            }

            override fun onBufferingEnd(mediaPlayer: MediaPlayer) { // steam end loading
                Log.d("2122", "onBufferingEnd UniversalVideoView callback")
            }
        })
        binding.universalVideoView.setOnPreparedListener {
            binding.universalVideoView.start()
        }
    }

    private fun videoFileIsCorrupted(path: String): Boolean {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(applicationContext, Uri.parse(path))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        val hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)
        return "yes" == hasVideo
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.universalVideoView.suspend()
    }

    override fun onBackPressed() {
        binding.universalVideoView.suspend()
        finish()
    }

}