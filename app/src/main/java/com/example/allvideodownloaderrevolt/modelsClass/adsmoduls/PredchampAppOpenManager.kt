package com.video.downloader.free.allvideo.media.download.social.videos.hd.models.adsmodule

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.androidads.adsdemo.AdsOpenInterstitialNewAd
import com.androidads.adsdemo.common.AdvertiseUtils
import com.androidads.adsdemo.common.Utils
import com.example.allvideodownloaderrevolt.AVDApplicationClass
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.SharedPreferences
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

/**
 * Prefetches App Open Ads.
 */
class PredchampAppOpenManager(myApplication: AVDApplicationClass) :
    Application.ActivityLifecycleCallbacks, LifecycleObserver {
    var appOpenAdValue = 0
    private var loadCallback: AppOpenAd.AppOpenAdLoadCallback? = null
    private var currentActivity: Activity? = null
    private val myApplication: AVDApplicationClass
    var appOpenAd: AppOpenAd? = null

    /**
     * Request an ad
     */
    fun fetchAd() {
        // Have unused ad, no need to fetch another.
        Log.e("TAG__AA_D", "fetchAd:IN----------- ")
        if (!Utils.isValidaEmptyWithZero(SharedPreferences.getStringName(SharedPreferences.IsFirstTimeAppOpenAd))
            &&
            isAdAvailable
        ) {
            Log.e("TAG__AA_D", "fetchAd:IsFirstTimeAppOpenAd ")
            return
        }
        loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(appOpenAd: AppOpenAd) {
                if (!isShowingAd) {
                    this@PredchampAppOpenManager.appOpenAd = appOpenAd
                    val fullScreenContentCallback: FullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                // Set the reference to null so isAdAvailable() returns false.
                                this@PredchampAppOpenManager.appOpenAd = null
                                isShowingAd = false
                                fetchAd()
                                Log.e(LOG_TAG, "onAdDismissedFullScreenContent-->")
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                Log.e(
                                    "14/2 -- ",
                                    "onAdFailedToShowFullScreenContent<---->" + adError.message
                                )
                                this@PredchampAppOpenManager.appOpenAd = null
                                isShowingAd = false
                                fetchAd()

//                                    if (!Utils.isValidationEmpty(Constants.AppOpenAdId) && (!Utils.isValidationEmpty(AdvertiseUtils.PredChampURL)
//                                            || !Utils.isValidationEmpty(AdvertiseUtils.GameURL)
//                                            || !Utils.isValidationEmpty(AdvertiseUtils.QurekaURL))) {
//
//                                        AdsOpenInterstitialNewAd adsOpenInterstitialNewAd = new AdsOpenInterstitialNewAd();
//                                        adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(currentActivity,
//                                                Constants.PredChampInsideType, str -> isOpenAd = false);
//                                    }
                            }

                            override fun onAdShowedFullScreenContent() {
                                isShowingAd = true
                                Log.e(LOG_TAG, "onAdShowedFullScreenContent-->")
                            }
                        }
                    appOpenAd.setFullScreenContentCallback(fullScreenContentCallback)
                    Log.e(
                        "TAG__AA_D",
                        "onAdLoaded:------>" + SharedPreferences.getStringName(SharedPreferences.IsFirstTimeAppOpenAd)
                    )
                    if (Utils.isValidaEmptyWithZero(
                            SharedPreferences.getStringName(
                                SharedPreferences.IsFirstTimeAppOpenAd
                            )
                        )
                    ) {
//                        appOpenAd.show(currentActivity);
                        Log.e("TAG__AA_D", "show:------ ")
                        SharedPreferences.setStringName(SharedPreferences.IsFirstTimeAppOpenAd, "1")
                    }
                }
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                Log.e("14/2", "loadAdError--> ffff    " + loadAdError.getMessage())
                Log.d("09/12", "onAdFail--" + isShowingAd)
                if (!isShowingAd) {
                    isShowingAd = true
                    Log.d("09/12", "onAdFail--isOpen")
                    if (!Utils.isValidatEmpty(Constant.AppOpenAdId) && (!Utils.isValidatEmpty(
                            AdvertiseUtils.PredChampURL
                        )
                                || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                                || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL))
                    ) {
                        val adsOpenInterstitialNewAd = AdsOpenInterstitialNewAd()
                        adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(currentActivity,
                            Constant.PredChampInsideType,
                            AdsOpenInterstitialNewAd.MyCallback { str: String? ->
                                isShowingAd = false
                            })
                    }
                }
            }
        }
        if (!Utils.isValidatEmpty(Constant.AppOpenAdId)) {
            val request = adRequest
            if (Utils.isValidaEmptyWithZero(SharedPreferences.getStringName(SharedPreferences.IsFirstTimeAppOpenAd))) {
                isShowingAd = false
            }
            Constant.AppOpenAdId?.let {
                AppOpenAd.load(
                    myApplication,
                    it,
                    request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    loadCallback as AppOpenAd.AppOpenAdLoadCallback
                )
            }
        }
    }

    var isFirstTime = true

    interface onNext {
        fun nextActionPerform()
    }

    var isAdOpen = false

    /**
     * Shows the ad if one isn't already showing.
     */
    fun showAdIfAvailable() {
        appOpenAdValue = SharedPreferences.getInteger(Constant.APP_OPEN_AD)
        if (appOpenAdValue == 1) {
            SharedPreferences.setInteger(Constant.APP_OPEN_AD, 0)
            Log.e("TAG__1__1", "showAdIfAvailable: ")
            return
        }
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable) {
            Log.d(LOG_TAG, "Will show ad.")
            Log.e("TAG__1__2", " Ads Available: ")
            Log.e("TAG__1__7", "show ads: ")
            appOpenAd?.show(currentActivity!!)
        } else {
            Log.e("TAG___1__6", "NOT SHOWING ADS: ")
            fetchAd()
        }
    }

    /**
     * Creates and returns ad request.
     */
    private val adRequest: AdRequest
        private get() = AdRequest.Builder().build()

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    val isAdAvailable: Boolean
        get() = appOpenAd != null

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
    }

    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.d(LOG_TAG, "onStart")
        if (!isFirstTime && !isShowingAd && !Constant.isNotificationClicked) {
            showAdIfAvailable()
            Log.d("09/12", "onStart")
        } else {
//            fetchAd();
            isFirstTime = false
        }
    }

    companion object {
        private const val LOG_TAG = "12/2"
        var appOpenAd: AppOpenAd? = null
        private var isShowingAd = false
        var isOpenAd = false
    }

    /**
     * Constructor
     */
    init {
        this.myApplication = myApplication
        if (!Constant.isRegistered) {
            this.myApplication.registerActivityLifecycleCallbacks(this)
            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
            Constant.isRegistered = true
        }
    }
}