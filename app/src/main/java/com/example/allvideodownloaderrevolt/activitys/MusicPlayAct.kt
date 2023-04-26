package com.example.allvideodownloaderrevolt.activitys

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActMusicPlayBinding
import com.example.allvideodownloaderrevolt.models.AudioModel
import java.io.IOException
import java.util.*

class MusicPlayAct : BaseAct() {

    var a = 1
    private var flag = 0
    var position: Int = 0
    var song: MediaPlayer? = null
    private lateinit var list: List<AudioModel>
    lateinit var actMusicPlayerBinding: ActMusicPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actMusicPlayerBinding = ActMusicPlayBinding.inflate(layoutInflater)
        setContentView(actMusicPlayerBinding.root)
        activity = this@MusicPlayAct
        val mWindow = window
        mWindow.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        getIntentData()
        initToolBar()
        videoPlayer()
        initClick()
    }

    private fun getIntentData() {
        val intent = intent
        if (intent.hasExtra("arrayList") && intent.hasExtra("pos")) {
            list = (intent.getSerializableExtra("arrayList") as List<AudioModel>)
            position = intent.getIntExtra("pos", 0)
        }
    }

    private fun initToolBar() {
        actMusicPlayerBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        actMusicPlayerBinding.toolBar.txtTitleName.visibility = View.INVISIBLE
    }

    private fun initClick() {
        actMusicPlayerBinding.imgPrevious.setOnClickListener {
            song!!.start()
            actMusicPlayerBinding.imgPlayStop.setImageResource(R.drawable.ic_stop)
            flag = 1
            if (position >= 1) {
                position--
                song!!.stop()
                videoPlayer()
            }
        }

        actMusicPlayerBinding.imgPlayStop.setOnClickListener {
            flag = if (flag == 0) {
                actMusicPlayerBinding.imgPlayStop.setImageResource(R.drawable.ic_stop)
                song!!.start()
                1
            } else {
                actMusicPlayerBinding.imgPlayStop.setImageResource(R.drawable.ic_play)
                song!!.pause()
                0
            }
        }

        actMusicPlayerBinding.imgNext.setOnClickListener {
            song!!.start()
            actMusicPlayerBinding.imgPlayStop.setImageResource(R.drawable.ic_stop)
            flag = 1
            if (list.size - 1 > position) {
                position++
                song!!.stop()
                videoPlayer()
            }
        }
    }

    private fun videoPlayer() {
        actMusicPlayerBinding.txtArtisName.text = list[position].getaArtist()
        actMusicPlayerBinding.txtName.text = list[position].getaName()

        song = MediaPlayer()
        try {
            song!!.setDataSource(list[position].getaPath())
            song!!.prepare()
        } catch (e: IOException) {
        }

        actMusicPlayerBinding.seekBar1.progress = song!!.duration
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                actMusicPlayerBinding.seekBar1.progress = song!!.currentPosition
            }
        }, 0, 100)
        actMusicPlayerBinding.seekBar1.max = song!!.duration
        song!!.start()
    }


    override fun onBackPressed() {
        Utils.displayInterOnBack(this)
        song!!.stop()
    }

}