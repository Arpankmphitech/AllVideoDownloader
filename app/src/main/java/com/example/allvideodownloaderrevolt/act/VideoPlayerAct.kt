package com.example.allvideodownloaderrevolt.act

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActVideoPlayerBinding
import com.example.allvideodownloaderrevolt.frag.GalleryFrag
import com.example.allvideodownloaderrevolt.frag.MusicFrag
import com.example.allvideodownloaderrevolt.frag.VideoFrag

class VideoPlayerAct : BaseAct() {

    lateinit var actVideoPlayerBinding: ActVideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actVideoPlayerBinding = ActVideoPlayerBinding.inflate(layoutInflater)
        setContentView(actVideoPlayerBinding.root)
        activity = this@VideoPlayerAct
        Utils.setStatusBarSkyGradientActivity(activity as VideoPlayerAct)
        videoPlayerInitToolBar()
        videoPlayerInitView()
        videoPlayerInitClick()
    }

    private fun videoPlayerInitToolBar() {
        actVideoPlayerBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        actVideoPlayerBinding.toolBar.txtTitleName.text = activity!!.resources.getString(R.string.video_player)
    }

    private fun videoPlayerInitClick() {
        actVideoPlayerBinding.txtVideo.setOnClickListener { bottomBarSelectOption(1) }
        actVideoPlayerBinding.txtMusic.setOnClickListener { bottomBarSelectOption(2) }
        actVideoPlayerBinding.txtGallery.setOnClickListener { bottomBarSelectOption(3) }
    }

    private fun bottomBarSelectOption(i: Int) {
        actVideoPlayerBinding.txtVideo.setTextColor(activity!!.resources.getColor(R.color.colorSilver))
        actVideoPlayerBinding.txtMusic.setTextColor(activity!!.resources.getColor(R.color.colorSilver))
        actVideoPlayerBinding.txtGallery.setTextColor(activity!!.resources.getColor(R.color.colorSilver))
        actVideoPlayerBinding.txtVideo.background = null
        actVideoPlayerBinding.txtMusic.background = null
        actVideoPlayerBinding.txtGallery.background = null
        when (i) {
            1 -> {
                actVideoPlayerBinding.txtVideo.setTextColor(activity!!.resources.getColor(R.color.colorWhite))
                actVideoPlayerBinding.txtVideo.background =
                    activity!!.resources.getDrawable(R.drawable.blue_gradient_8sdp_bg)
                loadFragmentView(VideoFrag())
            }
            2 -> {
                actVideoPlayerBinding.txtMusic.setTextColor(activity!!.resources.getColor(R.color.colorWhite))
                actVideoPlayerBinding.txtMusic.background =
                    activity!!.resources.getDrawable(R.drawable.blue_gradient_8sdp_bg)
                loadFragmentView(MusicFrag())
            }
            else -> {
                actVideoPlayerBinding.txtGallery.setTextColor(activity!!.resources.getColor(R.color.colorWhite))
                actVideoPlayerBinding.txtGallery.background =
                    activity!!.resources.getDrawable(R.drawable.blue_gradient_8sdp_bg)
                loadFragmentView(GalleryFrag())
            }
        }
    }

    private fun videoPlayerInitView() {
        bottomBarSelectOption(1)
    }

    private fun loadFragmentView(fragment: Fragment) {
        try {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameVideoPlayer, fragment).commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
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