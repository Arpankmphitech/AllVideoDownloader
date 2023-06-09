package com.example.allvideodownloaderrevolt.modelsClass.adsmoduls

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.androidads.adsdemo.AdsOpenInterstitialNewAd
import com.androidads.adsdemo.AllInterstitialNewAd
import com.androidads.adsdemo.common.AdvertiseUtils
import com.androidads.adsdemo.model.SplashCommonModel
import com.example.allvideodownloaderrevolt.AVDApplicationClass
import com.example.allvideodownloaderrevolt.commonClass.AdsIdOnDeviceStore
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.SharedPreferences
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

/**
 * Prefetches App Open Ads.
 */
class SubAppOpenManager(myApplication: AVDApplicationClass, act: Activity) :
    Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private var appOpenAd: AppOpenAd? = null
    var appOpenAdValue = 0
    private var loadCallback: AppOpenAd.AppOpenAdLoadCallback? = null
    private var currentActivity: Activity? = null
    private val myApplication: AVDApplicationClass
    var activity: Activity

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
                    this@SubAppOpenManager.appOpenAd = appOpenAd
                    val fullScreenContentCallback: FullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                // Set the reference to null so isAdAvailable() returns false.
                                this@SubAppOpenManager.appOpenAd = null
                                isShowingAd = false
                                fetchAd()
                                Log.e(LOG_TAG, "onAdDismissedFullScreenContent-->")
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                Log.e(
                                    "14/2 -- ",
                                    "onAdFailedToShowFullScreenContent<---->" + adError.message
                                )
                                this@SubAppOpenManager.appOpenAd = null
                                isShowingAd = false
                                fetchAd()

//                                    if (!Utils.isValidationEmpty(Constant.OpenAppAdId) && (!Utils.isValidationEmpty(AdvertiseUtils.PredChampURL)
//                                            || !Utils.isValidationEmpty(AdvertiseUtils.GameURL)
//                                            || !Utils.isValidationEmpty(AdvertiseUtils.QurekaURL))) {
//
//                                        AdsOpenInterstitialNewAd adsOpenInterstitialNewAd = new AdsOpenInterstitialNewAd();
//                                        adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(currentActivity,
//                                                Constant.TypePredChampInside, str -> isOpenAd = false);
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
                Log.d("09/12", "onAdFail")
                if (SharedPreferences.getStringName(Constant.IS_AD_SPLASH).equals("1")) {
                    if (!isOpenAd) {
                        Log.d("09/12", "onAdFail--isOpen")
                        if (!Utils.isValidationEmpty(Constant.OpenAppAdId!!) && (!Utils.isValidationEmpty(
                                AdvertiseUtils.PredChampURL
                            )
                                    || !Utils.isValidationEmpty(AdvertiseUtils.GameURL)
                                    || !Utils.isValidationEmpty(AdvertiseUtils.BitcoinURL))
                        ) {
                            isOpenAd = true
                            val adsOpenInterstitialNewAd = AdsOpenInterstitialNewAd()
                            adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(currentActivity,
                                Constant.TypePredChampInside,
                                AdsOpenInterstitialNewAd.MyCallback { str: String? ->
                                    isOpenAd = false
                                })
                        }
                    }
                } else {
                    SharedPreferences.setStringName(Constant.IS_AD_SPLASH, "1")
                }
            }
        }
        if (!Utils.isValidationEmpty(Constant.OpenAppAdId!!)) {
            val request = adRequest
            if (Utils.isValidaEmptyWithZero(SharedPreferences.getStringName(SharedPreferences.IsFirstTimeAppOpenAd))) {
                isShowingAd = false
            }
            Constant.OpenAppAdId?.let {
                AppOpenAd.load(
                    myApplication, it, request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback as AppOpenAd.AppOpenAdLoadCallback
                )
            }
        }
    }

    var isFirstTime = true

    interface onNext {
        fun nextActionPerform()
    }

    var isAdOpen = false
    fun fetchSplashAd(activity: Activity?, onNextListener: onNext?) {
        currentActivity = activity
        val loadCallback: AppOpenAd.AppOpenAdLoadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(appOpenAd: AppOpenAd) {
                val fullScreenContentCallback: FullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            Log.e("TAG__", "onAdDismissedFullScreenContent-SPLASH_FUNC->")
                            fetchAd()
                            isAdOpen = false
                            isShowingAd = false
                            onNextListener?.nextActionPerform()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            Log.e("14/2- ", "kkkkk    --- " + adError.message)
                            Log.d("09/12", "onAdFailedToShowFullScreenContent")
                            if (isShowingAd) return
                            isAdOpen = true
                            isShowingAd = true
                            if (SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                                    SharedPreferences.advertiseModel?.priority!!
                                )
                                && SharedPreferences.advertiseModel?.priority
                                    .equals("G,F")
                                && (SharedPreferences.advertiseModel?.f != null
                                        && !Utils.isValidaEmptyWithZero(
                                    SharedPreferences.advertiseModel?.f?.i
                                ))
                            ) {
                                val IntAdsId: SplashCommonModel? =
                                    AdsIdOnDeviceStore.GetInterstitial(activity!!)
                                AllInterstitialNewAd.getInstance().AllInterstitialNewAd(activity,
                                    IntAdsId?.accountType,
                                    IntAdsId?.id,
                                    IntAdsId?.accountNo,
                                    true,
                                    Constant.TypePredChampInside,
                                    object : AllInterstitialNewAd.MyCallback {
                                        override fun callbackCall(str: String) {
                                            if (onNextListener != null) {
                                                onNextListener.nextActionPerform()
                                                isShowingAd = false
                                            }
                                        }
                                    },
                                    null
                                )
                            } else if (!Utils.isValidationEmpty(Constant.OpenAppAdId!!) && (!Utils.isValidationEmpty(
                                    AdvertiseUtils.PredChampURL
                                )
                                        || !Utils.isValidationEmpty(AdvertiseUtils.GameURL)
                                        || !Utils.isValidationEmpty(AdvertiseUtils.BitcoinURL))
                            ) {
                                val adsOpenInterstitialNewAd = AdsOpenInterstitialNewAd()
                                adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(activity,
                                    Constant.TypePredChampInside,
                                    AdsOpenInterstitialNewAd.MyCallback { str: String? ->
                                        if (onNextListener != null) {
                                            onNextListener.nextActionPerform()
                                            isShowingAd = false
                                        }
                                    })
                            } else {
                                if (onNextListener != null) {
                                    onNextListener.nextActionPerform()
                                    isShowingAd = false
                                }
                            }
                        }

                        override fun onAdShowedFullScreenContent() {
                            isShowingAd = true
                            Log.e("TAG__", "onAdShowedFullScreenContent-SPLASH_FUNC->")
                        }
                    }
                appOpenAd.setFullScreenContentCallback(fullScreenContentCallback)
                Log.e("TAG__", "showing-SPLASH_FUNC->")
                appOpenAd.show(currentActivity!!)
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                Log.e("14/2", "loadAdError-SPLASH_FUNC->" + loadAdError.getMessage())
                if (isShowingAd) return
                isShowingAd = true
                if (SharedPreferences.advertiseModel != null && !Utils.isValidationEmpty(
                        SharedPreferences.advertiseModel?.priority!!
                    )
                    && SharedPreferences.advertiseModel?.priority.equals("G,F")
                    && (SharedPreferences.advertiseModel?.f != null
                            && !Utils.isValidaEmptyWithZero(
                        SharedPreferences.advertiseModel?.f?.i
                    ))
                ) {
                    Log.d("09/12", "1")
                    val IntAdsId: SplashCommonModel? =
                        AdsIdOnDeviceStore.GetInterstitial(activity!!)
                    AllInterstitialNewAd.getInstance().AllInterstitialNewAd(activity,
                        IntAdsId?.accountType,
                        IntAdsId?.id,
                        IntAdsId?.accountNo,
                        true,
                        Constant.TypePredChampInside,
                        object : AllInterstitialNewAd.MyCallback {
                            override fun callbackCall(str: String) {
                                Log.d("09/12", "called")
                                if (onNextListener != null) {
                                    onNextListener.nextActionPerform()
                                    isShowingAd = false
                                }
                            }
                        },
                        null
                    )
                } else if (!Utils.isValidationEmpty(Constant.OpenAppAdId!!) && (!Utils.isValidationEmpty(
                        AdvertiseUtils.PredChampURL
                    )
                            || !Utils.isValidationEmpty(AdvertiseUtils.BitcoinURL))
                ) {
                    Log.d("09/12", "2")
                    val adsOpenInterstitialNewAd = AdsOpenInterstitialNewAd()
                    adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(currentActivity,
                        Constant.TypePredChampInside,
                        AdsOpenInterstitialNewAd.MyCallback { str: String? ->
                            if (onNextListener != null) {
                                onNextListener.nextActionPerform()
                                isShowingAd = false
                            }
                        })
                } else {
                    Log.d("09/12", "3")
                    if (onNextListener != null) {
                        onNextListener.nextActionPerform()
                        isShowingAd = false
                    }
                }
            }
        }
        if (!Utils.isValidationEmpty(Constant.OpenAppAdId!!)) {
            Log.e("TAG__", "load-SPLASH_FUNC->")
            val request = adRequest
            Constant.OpenAppAdId?.let {
                AppOpenAd.load(
                    myApplication, it, request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback
                )
            }
        } else {
            onNextListener?.nextActionPerform()
        }
    }

    /**
     * Shows the ad if one isn't already showing.
     */
    fun showAdIfAvailable() {
        appOpenAdValue = SharedPreferences.getInteger(Constant.AD_APP_OPEN)
        if (appOpenAdValue == 1) {
            SharedPreferences.setInteger(Constant.AD_APP_OPEN, 0)
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
        if (!isFirstTime && !isShowingAd && !Constant.isClickedNotification) {
            showAdIfAvailable()
            Log.d("09/12", "onStart")
        } else {
            fetchAd()
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
        Log.d("078945", " - Open Ads In")
        this.myApplication = myApplication
        activity = act
        if (!Constant.isRegistered) {
            this.myApplication.registerActivityLifecycleCallbacks(this)
            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
            Constant.isRegistered = true
        }
    }
}