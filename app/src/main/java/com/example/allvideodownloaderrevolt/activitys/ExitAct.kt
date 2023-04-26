package com.example.allvideodownloaderrevolt.activitys

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import com.androidads.adsdemo.common.AppOpenManager
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActExitBinding

class ExitAct : BaseAct() {

    lateinit var binding: ActExitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActExitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this@ExitAct
        initView()
        initClick()
    }

    private fun initClick() {
        binding.btnAppNo.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(Intent(activity,  HomeAct::class.java)
                    .putExtra("pos", 2))
            },true)

        }

        binding.btnAppYes.setOnClickListener {
            Constant.isNotificationClicked = true
            AppOpenManager.appOpenAd = null
            try {
                val intent1 = Intent(Intent.ACTION_MAIN)
                intent1.addCategory(Intent.CATEGORY_HOME)
                intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent1)
                finish()
                finishAffinity()
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

        binding.btnRateNowApp.setOnClickListener {
            Utils.openUrl(
                this@ExitAct,
                "http://play.google.com/store/apps/details?id=$packageName"
            )
        }
    }

    private fun initView() {
        val gradientDrawable = binding.btnAppYes.background as GradientDrawable
        gradientDrawable.setStroke(5, activity.resources.getColor(R.color.colorDodgerBlue))

        val gradientDrawable2 = binding.btnAppNo.background as GradientDrawable
        gradientDrawable2.setStroke(5, activity.resources.getColor(R.color.colorDodgerBlue))
    }

    override fun onStart() {
        super.onStart()
        Utils.loadInter(this)
        Utils.loadBigNative(this)
    }

    override fun onBackPressed() {

    }

}