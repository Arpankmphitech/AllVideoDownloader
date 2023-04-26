package com.example.allvideodownloaderrevolt.activitys

import android.content.Intent
import android.os.Bundle
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActVideoDownloaderBinding

class VideoDownloaderAct : BaseAct() {

    lateinit var binding: ActVideoDownloaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActVideoDownloaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this@VideoDownloaderAct
        Utils.setStatusBarSkyGradientActivity(activity)
        initToolBar()
        initClick()
    }

    private fun initToolBar() {
        binding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        binding.toolBar.txtTitleName.text =
            activity.resources.getString(R.string.video_downloader_title)
    }

    private fun initClick() {
        binding.layoutWA.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 8)
                )
            }, true)
        }

        binding.layoutShareChat.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 0)
                )
            }, true)
        }

        binding.layoutTwitter.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 1)
                )
            }, true)
        }

        binding.layoutFaceBook.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 2)
                )
            }, true)
        }

        binding.layoutJosh.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 3)
                )
            }, true)
        }

        binding.layoutInstagram.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 4)
                )
            }, true)
        }

        binding.layoutChingari.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 5)
                )
            }, true)
        }

        binding.layoutTiki.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 6)
                )
            }, true)
        }

        binding.layoutRoposo.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 7)
                )
            }, true)
        }
    }

    override fun onStart() {
        super.onStart()
        Utils.loadInter(this)
        Utils.loadBigNative(this)
        Utils.loadSmallNative(this, 2)
    }

    override fun onBackPressed() {
        Utils.displayInterOnBack(this)
    }

}