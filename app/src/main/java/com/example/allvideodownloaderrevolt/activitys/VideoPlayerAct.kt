package com.example.allvideodownloaderrevolt.activitys

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActVideoPlayerBinding
import com.example.allvideodownloaderrevolt.fragment.GalleryFrag
import com.example.allvideodownloaderrevolt.fragment.MusicFrag
import com.example.allvideodownloaderrevolt.fragment.VideoFrag

class VideoPlayerAct : BaseAct() {

    lateinit var binding: ActVideoPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this@VideoPlayerAct
        Utils.setStatusBarSkyGradientActivity(activity as VideoPlayerAct)
        initToolBar()
        initView()
        initClick()
    }

    private fun initToolBar() {
        binding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.video_player)
    }

    private fun initClick() {
        binding.txtVideo.setOnClickListener { bottomBarSelectOption(1) }
        binding.txtMusic.setOnClickListener { bottomBarSelectOption(2) }
        binding.txtGallery.setOnClickListener { bottomBarSelectOption(3) }
    }

    private fun bottomBarSelectOption(i: Int) {
        binding.txtVideo.setTextColor(activity.resources.getColor(R.color.colorSilver))
        binding.txtMusic.setTextColor(activity.resources.getColor(R.color.colorSilver))
        binding.txtGallery.setTextColor(activity.resources.getColor(R.color.colorSilver))
        binding.txtVideo.background = null
        binding.txtMusic.background = null
        binding.txtGallery.background = null
        when (i) {
            1 -> {
                binding.txtVideo.setTextColor(activity.resources.getColor(R.color.colorWhite))
                binding.txtVideo.background =
                    activity.resources.getDrawable(R.drawable.sky_gradient_8sdp_bg)
                loadFragmentView(VideoFrag())
            }
            2 -> {
                binding.txtMusic.setTextColor(activity.resources.getColor(R.color.colorWhite))
                binding.txtMusic.background =
                    activity.resources.getDrawable(R.drawable.sky_gradient_8sdp_bg)
                loadFragmentView(MusicFrag())
            }
            else -> {
                binding.txtGallery.setTextColor(activity.resources.getColor(R.color.colorWhite))
                binding.txtGallery.background =
                    activity.resources.getDrawable(R.drawable.sky_gradient_8sdp_bg)
                loadFragmentView(GalleryFrag())
            }
        }
    }

    private fun initView() {
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