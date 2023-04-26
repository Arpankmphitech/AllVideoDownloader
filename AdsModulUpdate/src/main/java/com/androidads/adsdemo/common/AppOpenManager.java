package com.androidads.adsdemo.common;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.androidads.adsdemo.AdsOpenInterstitialNewAd;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

/**
 * Prefetches App Open Ads.
 */
public class AppOpenManager implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private static final String LOG_TAG = "12/2";
    public static AppOpenAd appOpenAd = null;
    int appOpenAdValue;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private Activity currentActivity;
    private static boolean isShowingAd = false;
    private final Application myApplication;

    public static boolean isOpenAd = false;

    /**
     * Constructor
     */
    public AppOpenManager(Application myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        // Have unused ad, no need to fetch another.
        Log.e("TAG__AA_D", "fetchAd:IN----------- ");
        if (!Utils.isValidaEmptyWithZero(SharedPrefrences.getStringName(currentActivity, SharedPrefrences.IsFirstTimeAppOpenAd))
                &&
                isAdAvailable()) {
            Log.e("TAG__AA_D", "fetchAd:IsFirstTimeAppOpenAd ");
            return;
        }

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                if (!isShowingAd) {
                    AppOpenManager.this.appOpenAd = appOpenAd;
                    FullScreenContentCallback fullScreenContentCallback =
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Set the reference to null so isAdAvailable() returns false.
                                    AppOpenManager.this.appOpenAd = null;
                                    isShowingAd = false;
                                    fetchAd();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    Log.e("14/2 -- ", "onAdFailedToShowFullScreenContent<---->" + adError.getMessage());
                                    AppOpenManager.this.appOpenAd = null;
                                    isShowingAd = false;
                                    fetchAd();
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    isShowingAd = true;
                                }
                            };
                    appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                    Log.e("TAG__AA_D", "onAdLoaded:------>" + SharedPrefrences.getStringName(currentActivity, SharedPrefrences.IsFirstTimeAppOpenAd));
                    if (Utils.isValidaEmptyWithZero(SharedPrefrences.getStringName(currentActivity, SharedPrefrences.IsFirstTimeAppOpenAd))) {
//                        appOpenAd.show(currentActivity);
                        Log.e("TAG__AA_D", "show:------ ");
                        SharedPrefrences.setStringName(currentActivity, SharedPrefrences.IsFirstTimeAppOpenAd, "1");
                    }
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("09/12", "onAdFail");
                if (!isOpenAd) {
                    isOpenAd = true;

                    if (/*!Utils.isValidatEmpty(Constants.AppOpenAdId) &&*/ (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL))) {


                        Log.d("09/12", "onAdFail----"+loadAdError.getMessage());
                        AdsOpenInterstitialNewAd adsOpenInterstitialNewAd = new AdsOpenInterstitialNewAd();
                        adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(currentActivity,
                                "0", str -> isOpenAd = false);
                    }
                }

            }
        };
        if (SharedPrefrences.getAdvertiseModel(currentActivity) != null)
            if (SharedPrefrences.getAdvertiseModel(currentActivity).getG() != null)
                if (!Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(currentActivity).getG().getAo())) {
                    AdRequest request = getAdRequest();
                    if (Utils.isValidaEmptyWithZero(SharedPrefrences.getStringName(currentActivity, SharedPrefrences.IsFirstTimeAppOpenAd))) {
                        isShowingAd = false;
                    }
                    AppOpenAd.load(
                            myApplication, SharedPrefrences.getAdvertiseModel(currentActivity).getG().getAo(), request,
                            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
                }
    }

    boolean isFirstTime = true;

    public interface onNext {
        void nextActionPerform();
    }

    boolean isAdOpen = false;

    public void fetchSplashAd(Activity activity, onNext onNextListener) {
        currentActivity = activity;
        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                FullScreenContentCallback fullScreenContentCallback =
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Set the reference to null so isAdAvailable() returns false.
                                fetchAd();
                                isAdOpen = false;
                                isShowingAd = false;
                                if (onNextListener != null) {
                                    onNextListener.nextActionPerform();
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.e("14/2- ", "kkkkk    --- " + adError.getMessage());
                                Log.d("09/12", "onAdFailedToShowFullScreenContent");

                                if (isShowingAd)
                                    return;
                                isAdOpen = true;
                                isShowingAd = true;
                                if ((!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                                        || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                                        || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL))) {

                                    AdsOpenInterstitialNewAd adsOpenInterstitialNewAd = new AdsOpenInterstitialNewAd();
                                    adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(activity,
                                            "0", str -> {
                                                if (onNextListener != null) {
                                                    onNextListener.nextActionPerform();
                                                    isShowingAd = false;
                                                }
                                            });
                                } else {
                                    if (onNextListener != null) {
                                        onNextListener.nextActionPerform();
                                        isShowingAd = false;
                                    }
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                isShowingAd = true;
                            }
                        };
                appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                appOpenAd.show(currentActivity);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                if (isShowingAd)
                    return;
                isShowingAd = true;
                if ((!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL))) {
                    Log.d("09/12", "2");
                    AdsOpenInterstitialNewAd adsOpenInterstitialNewAd = new AdsOpenInterstitialNewAd();
                    adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(currentActivity,
                            "0", str -> {
                                if (onNextListener != null) {
                                    onNextListener.nextActionPerform();
                                    isShowingAd = false;
                                }
                            });
                } else {
                    Log.d("09/12", "3");
                    if (onNextListener != null) {
                        onNextListener.nextActionPerform();
                        isShowingAd = false;
                    }
                }

            }
        };
        if (SharedPrefrences.getAdvertiseModel(currentActivity) != null)
            if (SharedPrefrences.getAdvertiseModel(currentActivity).getG() != null)
                if (!Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(currentActivity).getG().getAo())) {
                    AdRequest request = getAdRequest();
                    AppOpenAd.load(
                            myApplication, SharedPrefrences.getAdvertiseModel(currentActivity).getG().getAo(), request,
                            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
                } else {
                    if (onNextListener != null) {
                        onNextListener.nextActionPerform();
                    }
                }
    }

    /**
     * Shows the ad if one isn't already showing.
     */
    public void showAdIfAvailable() {

        appOpenAdValue = SharedPrefrences.getInteger(currentActivity,SharedPrefrences.APP_OPEN_AD);
        if (appOpenAdValue == 1) {
            SharedPrefrences.setInteger(currentActivity,SharedPrefrences.APP_OPEN_AD, 0);
            Log.e("TAG__1__1", "showAdIfAvailable: ");
            return;
        }
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.

        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");
            Log.e("TAG__1__2", " Ads Available: ");
//            FullScreenContentCallback fullScreenContentCallback =
//                    new FullScreenContentCallback() {
//                        @Override
//                        public void onAdDismissedFullScreenContent() {
//                            // Set the reference to null so isAdAvailable() returns false.
//                            Log.e("TAG__1__3", "onAdDismissedFullScreenContent: ");
//                            AppOpenManager.this.appOpenAd = null;
//                            isShowingAd = false;
//                            fetchAd();
//                        }
//
//                        @Override
//                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                            Log.e("TAG__1__4", "onAdFailedToShowFullScreenContent: ");
//                        }
//
//                        @Override
//                        public void onAdShowedFullScreenContent() {
//                            isShowingAd = true;
//                            Log.e("TAG__1__5", "onAdShowedFullScreenContent: ");
//                        }
//                    };
//            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            Log.e("TAG__1__7", "show ads: ");
            appOpenAd.show(currentActivity);
        } else {
            Log.e("TAG___1__6", "NOT SHOWING ADS: ");
            fetchAd();
        }
    }

    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        return appOpenAd != null;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }

    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
        Log.d(LOG_TAG, "onStart");
        if (!isFirstTime && !isShowingAd) {
            showAdIfAvailable();
            Log.d("09/12", "onStart");
        } else {
            isFirstTime = false;
        }
    }
}