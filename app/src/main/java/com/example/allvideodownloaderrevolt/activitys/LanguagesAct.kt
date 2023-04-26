package com.example.allvideodownloaderrevolt.activitys

import android.os.Bundle
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActLanguagesBinding

class LanguagesAct : BaseAct() {

    lateinit var binding: ActLanguagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActLanguagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this@LanguagesAct
        Utils.setStatusBarBlueGradientActivity(activity as LanguagesAct)
        initView()
    }

    private fun initView() {
        binding.allEnglish.setOnClickListener {
            Utils.displayInter(activity, {
                move(FreePremiumAct::class.java, 1)
            }, true)
        }

        binding.allHindi.setOnClickListener {
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