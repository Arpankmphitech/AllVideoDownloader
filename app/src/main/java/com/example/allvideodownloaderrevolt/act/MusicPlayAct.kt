package com.example.allvideodownloaderrevolt.act

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActMusicPlayBinding
import com.example.allvideodownloaderrevolt.modelsClass.AudioModel
import java.io.IOException
import java.util.*

class MusicPlayAct : BaseAct() {

    var a = 1
    private var flag = 0
    var position: Int = 0
    var music: MediaPlayer? = null
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
        val getDataIntent = intent
        if (getDataIntent.hasExtra("arrayList") && getDataIntent.hasExtra("pos")) {
            list = (getDataIntent.getSerializableExtra("arrayList") as List<AudioModel>)
            position = getDataIntent.getIntExtra("pos", 0)
        }
    }

    private fun initToolBar() {
        actMusicPlayerBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        actMusicPlayerBinding.toolBar.txtTitleName.visibility = View.INVISIBLE
    }

    private fun initClick() {
        actMusicPlayerBinding.imgPrevious.setOnClickListener {
            music!!.start()
            actMusicPlayerBinding.imgPlayStop.setImageResource(R.drawable.ic_stop)
            flag = 1
            if (position >= 1) {
                position--
                music!!.stop()
                videoPlayer()
            }
        }

        actMusicPlayerBinding.imgPlayStop.setOnClickListener {
            flag = if (flag == 0) {
                actMusicPlayerBinding.imgPlayStop.setImageResource(R.drawable.ic_stop)
                music!!.start()
                1
            } else {
                actMusicPlayerBinding.imgPlayStop.setImageResource(R.drawable.ic_play)
                music!!.pause()
                0
            }
        }

        actMusicPlayerBinding.imgNext.setOnClickListener {
            music!!.start()
            actMusicPlayerBinding.imgPlayStop.setImageResource(R.drawable.ic_stop)
            flag = 1
            if (list.size - 1 > position) {
                position++
                music!!.stop()
                videoPlayer()
            }
        }
    }

    private fun videoPlayer() {
        actMusicPlayerBinding.txtArtisName.text = list[position].getaArtist()
        actMusicPlayerBinding.txtName.text = list[position].getaName()

        music = MediaPlayer()
        try {
            music!!.setDataSource(list[position].getaPath())
            music!!.prepare()
        } catch (e: IOException) {
        }

        actMusicPlayerBinding.seekBar1.progress = music!!.duration
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                actMusicPlayerBinding.seekBar1.progress = music!!.currentPosition
            }
        }, 0, 100)
        actMusicPlayerBinding.seekBar1.max = music!!.duration
        music!!.start()
    }


    override fun onBackPressed() {
        Utils.displayInterOnBack(this)
        music!!.stop()
    }

}