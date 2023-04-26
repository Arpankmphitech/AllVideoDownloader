package com.example.allvideodownloaderrevolt.activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

open class BaseAct : AppCompatActivity() {
    lateinit var activity: Activity

    fun move(go: Class<*>?, i: Int) {
        startActivity(Intent(applicationContext, go).putExtra("pos", i))
    }

    override fun onDestroy() {
        Glide.with(applicationContext).pauseRequests()
        super.onDestroy()
    }
}