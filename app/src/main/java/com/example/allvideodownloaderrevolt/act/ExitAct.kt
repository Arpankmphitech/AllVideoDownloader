package com.example.allvideodownloaderrevolt.act

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import com.androidads.adsdemo.common.AppOpenManager
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActExitBinding

class ExitAct : BaseAct() {

    lateinit var actExitBinding: ActExitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actExitBinding = ActExitBinding.inflate(layoutInflater)
        setContentView(actExitBinding.root)
        activity = this@ExitAct
        initView()
        initClick()
    }

    private fun initClick() {
        actExitBinding.btnAppNo.setOnClickListener {
            Utils.displayInter(activity, {
                startActivity(
                    Intent(activity, HomeAct::class.java)
                        .putExtra("pos", 2)
                )
            }, true)

        }

        actExitBinding.btnAppYes.setOnClickListener {
            Constant.isNotificationClicked = true
            AppOpenManager.appOpenAd = null
            try {
                val intentYes = Intent(Intent.ACTION_MAIN)
                intentYes.addCategory(Intent.CATEGORY_HOME)
                intentYes.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intentYes)
                finish()
                finishAffinity()
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

        actExitBinding.btnRateNowApp.setOnClickListener {
            Utils.openUrl(
                this@ExitAct,
                "http://play.google.com/store/apps/details?id=$packageName"
            )
        }
    }

    private fun initView() {
        val drawableGradientYes = actExitBinding.btnAppYes.background as GradientDrawable
        drawableGradientYes.setStroke(5, activity.resources.getColor(R.color.colorDodgerBlue))

        val drawableGradientNo = actExitBinding.btnAppNo.background as GradientDrawable
        drawableGradientNo.setStroke(5, activity.resources.getColor(R.color.colorDodgerBlue))
    }

    override fun onStart() {
        super.onStart()
        Utils.loadInter(this)
        Utils.loadBigNative(this)
    }

    override fun onBackPressed() {

    }

}