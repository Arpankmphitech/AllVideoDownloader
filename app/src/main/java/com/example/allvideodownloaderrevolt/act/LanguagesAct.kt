package com.example.allvideodownloaderrevolt.act

import android.os.Bundle
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActLanguagesBinding

class LanguagesAct : BaseAct() {

    lateinit var actLanguageBinding: ActLanguagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actLanguageBinding = ActLanguagesBinding.inflate(layoutInflater)
        setContentView(actLanguageBinding.root)
        activity = this@LanguagesAct
        Utils.setStatusBarBlueGradientActivity(activity as LanguagesAct)
        initView()
    }

    private fun initView() {
        actLanguageBinding.allEnglish.setOnClickListener {
            Utils.displayInter(activity, {
                move(FreePremiumAct::class.java, 1)
            }, true)
        }

        actLanguageBinding.allHindi.setOnClickListener {
            Utils.displayInter(activity, {
                move(FreePremiumAct::class.java, 1)
            }, true)
        }
    }

    override fun onStart() {
        super.onStart()
        Utils.loadInter(this)
        Utils.loadBigNative(this)
    }

    override fun onBackPressed() {
        finish()
    }

}