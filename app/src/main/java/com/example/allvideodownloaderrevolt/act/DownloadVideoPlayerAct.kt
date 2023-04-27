package com.example.allvideodownloaderrevolt.act

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
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActDownloadVideoPlayerBinding
import com.universalvideoview.UniversalVideoView

class DownloadVideoPlayerAct : AppCompatActivity() {

    lateinit var actAlDwPlayerBinding: ActDownloadVideoPlayerBinding
    var mLayoutBottom: View? = null
    var mLayoutVideo: View? = null
    var isFullScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actAlDwPlayerBinding = ActDownloadVideoPlayerBinding.inflate(layoutInflater)
        setContentView(actAlDwPlayerBinding.root)

        Utils.setStatusBarBlackColorActivity(this)
        actAlDwPlayerBinding.universalVideoView.setMediaController(actAlDwPlayerBinding.universalMediaController)
        val fileUri = intent.getParcelableExtra(Intent.EXTRA_STREAM) as Uri?

        if (fileUri != null) {
            actAlDwPlayerBinding.universalVideoView.setVideoURI(fileUri)
        } else {
            actAlDwPlayerBinding.universalVideoView.setVideoURI(Uri.parse(intent.getStringExtra("path")))
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
        actAlDwPlayerBinding.universalVideoView.setVideoViewCallback(object :
            UniversalVideoView.VideoViewCallback {
            override fun onScaleChange(isScreenFull: Boolean) {
                isFullScreen =  isScreenFull
                if (isScreenFull) {
                    val layoutParams = mLayoutVideo?.layoutParams
                    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    mLayoutVideo?.layoutParams = layoutParams
                    //GONE the unconcerned views to leave room for video and controller
                    mLayoutBottom?.visibility = View.GONE
                } else {
                    val layoutParams = mLayoutVideo?.layoutParams
                    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    mLayoutVideo?.layoutParams = layoutParams
                    mLayoutBottom?.visibility = View.VISIBLE
                }
            }

            override fun onPause(mediaPlayer: MediaPlayer) { // Video pause
                Log.d("2122", "onPause UniversalVideoView callback")
            }

            override fun onStart(mediaPlayer: MediaPlayer) { // Video start/resume to play
                actAlDwPlayerBinding.progressCircle.visibility = View.GONE
                Log.d("2122", "onStart UniversalVideoView callback")
            }

            override fun onBufferingStart(mediaPlayer: MediaPlayer) { // steam start loading
                Log.d("2122", "onBufferingStart UniversalVideoView callback")
            }

            override fun onBufferingEnd(mediaPlayer: MediaPlayer) { // steam end loading
                Log.d("2122", "onBufferingEnd UniversalVideoView callback")
            }
        })
        actAlDwPlayerBinding.universalVideoView.setOnPreparedListener {
            actAlDwPlayerBinding.universalVideoView.start()
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
        actAlDwPlayerBinding.universalVideoView.suspend()
    }

    override fun onBackPressed() {
        actAlDwPlayerBinding.universalVideoView.suspend()
        finish()
    }

}