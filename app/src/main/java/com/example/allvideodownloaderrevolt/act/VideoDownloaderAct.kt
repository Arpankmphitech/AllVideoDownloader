package com.example.allvideodownloaderrevolt.act

import android.content.Intent
import android.os.Bundle
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActVideoDownloaderBinding

class VideoDownloaderAct : BaseAct() {

    lateinit var actVideoDwBinding: ActVideoDownloaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actVideoDwBinding = ActVideoDownloaderBinding.inflate(layoutInflater)
        setContentView(actVideoDwBinding.root)
        activity = this@VideoDownloaderAct
        Utils.setStatusBarSkyGradientActivity(activity)
        vdInitToolBar()
        vdInitClick()
    }

    private fun vdInitToolBar() {
        actVideoDwBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        actVideoDwBinding.toolBar.txtTitleName.text =
            activity.resources.getString(R.string.video_downloader_title)
    }

    private fun vdInitClick() {
        actVideoDwBinding.layoutWA.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 8)
                )
            }, true)
        }

        actVideoDwBinding.layoutShareChat.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 0)
                )
            }, true)
        }

        actVideoDwBinding.layoutTwitter.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 1)
                )
            }, true)
        }

        actVideoDwBinding.layoutFaceBook.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 2)
                )
            }, true)
        }

        actVideoDwBinding.layoutJosh.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 3)
                )
            }, true)
        }

        actVideoDwBinding.layoutInstagram.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 4)
                )
            }, true)
        }

        actVideoDwBinding.layoutChingari.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 5)
                )
            }, true)
        }

        actVideoDwBinding.layoutTiki.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(
                        activity,
                        AllDownloadsAct::class.java
                    ).putExtra("from", 6)
                )
            }, true)
        }

        actVideoDwBinding.layoutRoposo.setOnClickListener {
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