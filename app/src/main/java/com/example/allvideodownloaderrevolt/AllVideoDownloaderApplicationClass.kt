package com.example.allvideodownloaderrevolt

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.StrictMode
import com.example.allvideodownloaderrevolt.activitys.SplashScreenAct
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.Utils
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.fullyInitialize
import com.facebook.ads.AudienceNetworkAds
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.onesignal.OneSignal

class AllVideoDownloaderApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this@AllVideoDownloaderApplicationClass

        MobileAds.initialize(this, object : OnInitializationCompleteListener {
            override fun onInitializationComplete(initializationStatus: InitializationStatus) {}
        })

        FirebaseApp.initializeApp(application!!)
        FirebaseAnalytics.getInstance(this)
        AudienceNetworkAds.initialize(application)
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constant.ONE_SIGNAL)
        OneSignal.setNotificationOpenedHandler { result ->
            if (result != null && result.notification != null && !Utils.isValidationEmpty(
                    result.notification.launchURL
                )
                && Utils.isValidationUrl(result.notification.launchURL)
            ) {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(result.notification.launchURL)
                )
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(browserIntent)
            } else {
                startActivity(
                    Intent(
                        appContext,
                        SplashScreenAct::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }

        //For FB Event Manager...
        fullyInitialize()
        FacebookSdk.sdkInitialize(application!!)
        AppEventsLogger.activateApp(application!!)
        val logger = AppEventsLogger.newLogger(application!!)
        logger.logEvent("open")
    }

    override fun onLowMemory() {
        super.onLowMemory()

    }

    override fun onTerminate() {
        super.onTerminate()

    }

    companion object {
        var application: AllVideoDownloaderApplicationClass? = null
        val appContext: Context?
            get() {
                if (application == null) {
                    application = AllVideoDownloaderApplicationClass()
                }
                return application
            }
    }
}