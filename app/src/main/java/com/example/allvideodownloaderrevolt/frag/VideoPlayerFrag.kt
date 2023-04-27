package com.example.allvideodownloaderrevolt.frag

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.FragVideoPlayerBinding

class VideoPlayerFrag : Fragment() {

    lateinit var activity: Activity
    lateinit var binding: FragVideoPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragVideoPlayerBinding.inflate(layoutInflater)
        activity = requireActivity()
        activity.let { Utils.setStatusBarSkyGradientActivity(it) }
        initToolbar()
        initView()
        initClick()
        return binding.root
    }

    private fun initToolbar() {
        binding.toolBar.ivBackArrow.visibility = View.INVISIBLE
        binding.toolBar.txtTitleName.text = resources.getString(R.string.video_player)
    }

    private fun initView() {
        bottomBarSelectOption(1)
    }

    private fun initClick() {
        binding.txtVideo.setOnClickListener { bottomBarSelectOption(1) }
        binding.txtMusic.setOnClickListener { bottomBarSelectOption(2) }
        binding.txtGallery.setOnClickListener { bottomBarSelectOption(3) }
    }

    private fun bottomBarSelectOption(i: Int) {
        binding.txtVideo.setTextColor(resources.getColor(R.color.colorSilver))
        binding.txtMusic.setTextColor(resources.getColor(R.color.colorSilver))
        binding.txtGallery.setTextColor(resources.getColor(R.color.colorSilver))
        binding.txtVideo.background = null
        binding.txtMusic.background = null
        binding.txtGallery.background = null
        when (i) {
            1 -> {
                binding.txtVideo.setTextColor(resources.getColor(R.color.colorWhite))
                binding.txtVideo.background = resources.getDrawable(R.drawable.sky_gradient_8sdp_bg)
                loadFragmentView(VideoFrag())
            }
            2 -> {
                binding.txtMusic.setTextColor(resources.getColor(R.color.colorWhite))
                binding.txtMusic.background = resources.getDrawable(R.drawable.sky_gradient_8sdp_bg)
                loadFragmentView(MusicFrag())
            }
            else -> {
                binding.txtGallery.setTextColor(resources.getColor(R.color.colorWhite))
                binding.txtGallery.background =
                    resources.getDrawable(R.drawable.sky_gradient_8sdp_bg)
                loadFragmentView(GalleryFrag())
            }
        }
    }

    private fun loadFragmentView(fragment: Fragment) {
        try {
            if (fragmentManager != null) {
                requireFragmentManager().beginTransaction()
                    .replace(R.id.frameVideoPlayer, fragment).commitAllowingStateLoss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}