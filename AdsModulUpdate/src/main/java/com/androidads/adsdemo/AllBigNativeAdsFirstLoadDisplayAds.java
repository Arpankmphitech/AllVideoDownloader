package com.androidads.adsdemo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidads.adsdemo.common.AdvertiseUtils;
import com.androidads.adsdemo.common.SharedPrefrences;
import com.androidads.adsdemo.common.Utils;
import com.androidads.adsdemo.model.QurekaandPreadChampAdvertiseModel;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class AllBigNativeAdsFirstLoadDisplayAds {

    private static AllBigNativeAdsFirstLoadDisplayAds mInstance;
    private final String TAG = "Brijali Natvi Fb Ads";
    private MyCallback myCallback;
    private MyCallback myCallbackDisplay;
    private NativeAd nativeAdsFb;
    private NativeBannerAd nativeBannerAd;

    private LinearLayout adView;
    private ArrayList<View> viewArrayList = new ArrayList<>();
    private com.google.android.gms.ads.nativead.NativeAd googlenativeAd;
    MediaView nativeAdMedia;

    private boolean isLodingFacebookAds = true;
    private boolean isLodingGoogleAds = true;
    private boolean isLodingAppNextAds = true;

    public interface MyCallback {

        void callbackCallSuccess(String Success);

        void callbackCallFail(String Failed);

        void callbackCallClose();
    }

    public static AllBigNativeAdsFirstLoadDisplayAds getInstance() {
        if (mInstance == null) {
            mInstance = new AllBigNativeAdsFirstLoadDisplayAds();
        }
        return mInstance;
    }

    public void AllNatviAdsLodRequest(final Activity activity, String AdstypeString, String AdIds, MyCallback _myCallback,
                                      int AdsSize,
                                      NativeAdLayout FbNativAdsLayout,
                                      FrameLayout GoogleNativAds,
                                      String QurekaorPredChampType,
                                      int pos,
                                      LinearLayout llBigView,
                                      LinearLayout llSmallView) {
        SharedPrefrences.get(activity);
        myCallback = _myCallback;
        if (AdsSize == 2) {
            llBigView.setVisibility(View.VISIBLE);
        } else {
            llSmallView.setVisibility(View.VISIBLE);
        }
        int Adstype = Integer.parseInt(AdstypeString);
        Log.d("06/10", "AllNatviAdsLodRequest---" + Adstype);
        switch (Adstype) {
            case 0:
                if (myCallback != null) {
                    myCallback.callbackCallFail("Error lib AllNatviAds==>" + "No Ads " + Adstype);
                }
                break;
            case 1:
                if (AdsSize == 2) {
                    FacebookAds(activity, AdIds, FbNativAdsLayout, GoogleNativAds, AdsSize, QurekaorPredChampType, pos);
                } else {
                    FacebookNativeBannerAds(activity, AdIds, FbNativAdsLayout, GoogleNativAds, AdsSize, QurekaorPredChampType, pos);
                }
                break;
            case 2:
                GoogleNativAdsNew(activity, AdIds, AdsSize, FbNativAdsLayout, GoogleNativAds, QurekaorPredChampType, pos);
                break;
            case 3:
                if (myCallback != null) {
                    myCallback.callbackCallSuccess("Success load qureka");
                    myCallback = null;
                    return;
                }
                break;
            default:
                if (myCallback != null) {
                    myCallback.callbackCallFail("Failed load qureka");
                    myCallback = null;
                    return;
                }
                break;
        }
    }

    public void AllNativAdsDisplayThisAds(final Activity activity, String AdstypeString,
                                          int AdsSize, MyCallback _myCallback, NativeAdLayout FbNativAdsLayout,
                                          FrameLayout GoogleNativAds,
                                          String QurekaorPredChampType,
                                          int pos) {
        SharedPrefrences.get(activity);
        myCallbackDisplay = _myCallback;
        int Adstype = Integer.parseInt(AdstypeString);
        Log.d("06/10", "AllBig --Adstype--" + Adstype);
        switch (Adstype) {
            case 0:
                if (myCallbackDisplay != null) {
                    myCallbackDisplay.callbackCallFail("Error lib AllNatviAds==>" + "No Ads " + Adstype);
                    myCallbackDisplay = null;
                }
                break;
            case 1:

                if (nativeAdsFb != null && nativeAdsFb.isAdLoaded()) {
                    Log.d("06/10", "first if");
                    if (AdsSize == 2) {
                        DisplayFacebookNatviCallFunction(activity, FbNativAdsLayout, AdsSize, GoogleNativAds, QurekaorPredChampType);
                    } else {
                        DisplayFacebookNativeBannerCallFunction(activity, FbNativAdsLayout, AdsSize, GoogleNativAds, QurekaorPredChampType, pos);
                    }
                } else if (googlenativeAd != null && SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("F,G")
                        &&
                        ((SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getN()))
                                || (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getN())))) {
                    Log.d("06/10", "DisplayGoogleAdsCallThisFunction Called");
                    DisplayGoogleAdsCallThisFunction(activity, GoogleNativAds, AdsSize, QurekaorPredChampType, pos);
                } else {
                    Log.d("06/10", "first else");
                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                    } else {
                        if (myCallbackDisplay != null) {
                            myCallbackDisplay.callbackCallFail("Failed load qureka");
                            myCallbackDisplay = null;
                            return;
                        }
                    }
                }

                break;
            case 2:
                Log.d("06/10", "AllBig 1");
                if (googlenativeAd != null) {
                    Log.d("06/10", "AllBig 2");
                    DisplayGoogleAdsCallThisFunction(activity, GoogleNativAds, AdsSize, QurekaorPredChampType, pos);
                } else if (nativeAdsFb != null && nativeAdsFb.isAdLoaded() && SharedPrefrences.getAdvertiseModel(activity) != null
                        && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("G,F")
                        &&
                        ((SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getN()))
                                || (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getN())))) {
                    Log.d("06/10", "AllBig 3");

                    if (AdsSize == 2) {
                        DisplayFacebookNatviCallFunction(activity, FbNativAdsLayout, AdsSize, GoogleNativAds, QurekaorPredChampType);
                    } else {
                        DisplayFacebookNativeBannerCallFunction(activity, FbNativAdsLayout, AdsSize, GoogleNativAds, QurekaorPredChampType, pos);
                    }
                } else {
                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                    } else {
                        if (myCallbackDisplay != null) {
                            myCallbackDisplay.callbackCallFail("Failed load qureka");
                            myCallbackDisplay = null;
                            return;
                        }
                    }
                }
                break;
            case 3:
                Log.e("vick1", "1111");
                if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                    DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                } else {
                    if (myCallbackDisplay != null) {
                        myCallbackDisplay.callbackCallFail("Failed load qureka");
                        myCallbackDisplay = null;
                        return;
                    }
                }
                break;
            default:
                if (myCallbackDisplay != null) {
                    myCallbackDisplay.callbackCallFail("Failed load qureka");
                    myCallbackDisplay = null;
                }
                break;
        }
    }

    private void DisplayQurekaLoad(final Activity activity, FrameLayout googleNativAds, int adsSize,
                                   final String QurekaorPredChampType) {

        Log.d("DisplayQurekaLoad", "0000");
        //medium native banner
        if (adsSize == 1) {
            Log.d("06/10", "myCallback--" + (myCallback == null));
            Log.d("06/10", "googleNativAds--" + (googleNativAds == null));
            View myView =
                    (View) activity.getLayoutInflater().inflate(R.layout.qureka_medium_native, null);
            GifImageView gifView = myView.findViewById(R.id.gifView);

            ImageView ivBanner = myView.findViewById(R.id.ivBanner);

            TextView tvTitle = myView.findViewById(R.id.txtTitleName);
            TextView tvDescription = myView.findViewById(R.id.tvDescription);

            final QurekaandPreadChampAdvertiseModel qurekaandPreadChampAdvertiseModel
                    = AdvertiseUtils.getPredchampHeaderTitleDescriptionList(activity);

            if (AdvertiseUtils.isValidationEmptyWithZero(QurekaorPredChampType)) {
                ivBanner.setImageResource(qurekaandPreadChampAdvertiseModel.getBanner());
                gifView.setImageResource(qurekaandPreadChampAdvertiseModel.getGifView());
                tvTitle.setText(qurekaandPreadChampAdvertiseModel.getTitle());
                tvDescription.setText(qurekaandPreadChampAdvertiseModel.getBody());

            } else {
               /* ivBanner.setImageResource(AdvertiseUtils.getQurekaMediumNativeImage());
                gifView.setImageResource(AdvertiseUtils.getQurekaGIFImage());

                QurekaandPreadChampAdvertiseModel qurekaandPreadChampAdvertiseModel
                        = AdvertiseUtils.getQurekaHeaderTitleDescriptionList(activity);
                tvTitle.setText(qurekaandPreadChampAdvertiseModel.getTitle());
                tvDescription.setText(qurekaandPreadChampAdvertiseModel.getBody());*/

            }

            myView.findViewById(R.id.rlAdvertiseMain).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity != null) {

                        if (activity != null) {
                            if (qurekaandPreadChampAdvertiseModel != null) {
                                if (!AdvertiseUtils.isValidationEmptyWithZero(qurekaandPreadChampAdvertiseModel.getUrl())) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(qurekaandPreadChampAdvertiseModel.getUrl()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        activity.startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        Toast.makeText(activity, "No application that can handle this link found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    //Bansi Changes Here
                                    //Toast.makeText(activity, "No ads to show.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
            });

            if (googleNativAds != null) {
                googleNativAds.removeAllViews();
                googleNativAds.addView(myView);
            }
            if (myCallbackDisplay != null) {
                Log.d("06/10", "myCallback called");
                myCallbackDisplay.callbackCallSuccess("Success load qureka");
                myCallbackDisplay = null;
                return;
            } else if (myCallback != null) {
                Log.d("06/10", "myCallback called");
                myCallback.callbackCallSuccess("Success load qureka");
                myCallback = null;
                return;
            }
        }
        //Big native
        else if (adsSize == 2) {
          /*  View myView = LayoutInflater.from(activity)
                    .inflate(R.layout.qureka_big_native, null, false);*/

            View myView1 =
                    (View) activity.getLayoutInflater().inflate(R.layout.qureka_big_native, null);
            GifImageView gifView = myView1.findViewById(R.id.gifView);
            ImageView ivBanner = myView1.findViewById(R.id.ivBanner);

            TextView tvTitle = myView1.findViewById(R.id.txtTitleName);
            TextView tvDescription = myView1.findViewById(R.id.tvDescription);

            final QurekaandPreadChampAdvertiseModel qurekaandPreadChampAdvertiseModel
                    = AdvertiseUtils.getPredchampHeaderTitleDescriptionList(activity);

            if (AdvertiseUtils.isValidationEmptyWithZero(QurekaorPredChampType)) {
                ivBanner.setImageResource(qurekaandPreadChampAdvertiseModel.getBanner());
                gifView.setImageResource(qurekaandPreadChampAdvertiseModel.getGifView());


                tvTitle.setText(qurekaandPreadChampAdvertiseModel.getTitle());
                tvDescription.setText(qurekaandPreadChampAdvertiseModel.getBody());

            } else {
                /*ivBanner.setImageResource(AdvertiseUtils.getQurekaMediumNativeImage());
                gifView.setImageResource(AdvertiseUtils.getQurekaGIFImage());

                QurekaandPreadChampAdvertiseModel qurekaandPreadChampAdvertiseModel
                        = AdvertiseUtils.getQurekaHeaderTitleDescriptionList(activity);
                tvTitle.setText(qurekaandPreadChampAdvertiseModel.getTitle());
                tvDescription.setText(qurekaandPreadChampAdvertiseModel.getBody());
*/
            }

            myView1.findViewById(R.id.rlAdvertiseMain).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity != null) {

                        if (activity != null) {
                            if (qurekaandPreadChampAdvertiseModel != null) {
                                if (!AdvertiseUtils.isValidationEmptyWithZero(qurekaandPreadChampAdvertiseModel.getUrl())) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(qurekaandPreadChampAdvertiseModel.getUrl()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        activity.startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        Toast.makeText(activity, "No application that can handle this link found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    //Bansi Changes Here
                                    //Toast.makeText(activity, "No ads to show.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }


                    }
                }
            });

            if (googleNativAds != null) {
                googleNativAds.removeAllViews();
                googleNativAds.addView(myView1);
            }
            if (myCallbackDisplay != null) {
                myCallbackDisplay.callbackCallSuccess("Success load qureka");
                myCallbackDisplay = null;
                return;
            } else if (myCallback != null) {
                myCallback.callbackCallSuccess("Success load qureka");
                myCallback = null;
                return;
            }
        }

    }


    private void FacebookAds(final Activity activity, final String adIds,
                             final NativeAdLayout FbNativAdsLayout,
                             final FrameLayout GoogleNativAds, final int AdsSize,
                             final String QurekaorPredChampType, final int pos) {

        nativeAdsFb = new NativeAd(activity, adIds);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to
                Log.e("ads Native : ", adError.getErrorMessage());
                isLodingFacebookAds = false;
               /* if (myCallback != null) {
                    myCallback.callbackCallFail("Error lib Fb Nativ==>" + adError.getErrorMessage());
                    // myCallback = null;
                }*/

                if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("F,G")
                        &&
                        ((SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getN()))
                                || (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getN())))
                ) {
                    if (AdsSize == 2) {

                        GoogleNativAdsNew(activity, SharedPrefrences.getAdvertiseModel(activity).getG().getN(), AdsSize,
                                FbNativAdsLayout, GoogleNativAds, QurekaorPredChampType, pos);

                    } else {

                        GoogleNativAdsNew(activity, SharedPrefrences.getAdvertiseModel(activity).getG().getNb(), AdsSize,
                                FbNativAdsLayout, GoogleNativAds, QurekaorPredChampType, pos);

                    }
                } else {

                    Log.e("vick1", "222222");

                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                    } else {
                        if (myCallback != null) {
                            myCallback.callbackCallFail("Failed load qureka");
                            myCallback = null;
                            return;
                        }

                    }

//                    DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                if (nativeAdsFb == null) {
                    isLodingFacebookAds = false;
                    if (myCallback != null) {
                        myCallback.callbackCallFail("Error lib Fb Nativ==>" + ad.getPlacementId());
                        // myCallback = null;
                    }
                    return;
                } else {
                    isLodingFacebookAds = true;
                    if (myCallback != null) {
                        myCallback.callbackCallSuccess("Facebook big native load==>");
                        myCallback = null;
                    }
                    return;
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
            }
        };
        nativeAdsFb.loadAd(
                nativeAdsFb.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());

        Log.e("01/10 facebook native request -====>", "nativeAdsFb");


    }

    private void FacebookAds(final Activity activity, final String adIds,
                             final NativeAdLayout FbNativAdsLayout,
                             final FrameLayout GoogleNativAds, final int AdsSize,
                             final String QurekaorPredChampType, final int pos,
                             boolean isPrior) {

        nativeAdsFb = new NativeAd(activity, adIds);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to
                Log.e("ads Native : ", adError.getErrorMessage());
                Log.e("19/12 : ", adError.getErrorMessage());
                isLodingFacebookAds = false;
               /* if (myCallback != null) {
                    myCallback.callbackCallFail("Error lib Fb Nativ==>" + adError.getErrorMessage());
                    // myCallback = null;
                }*/

                if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("F,G")
                        &&
                        ((SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getN2()))
                                || (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getN2())))
                ) {
                    if (AdsSize == 2) {

                        GoogleNativAdsNew(activity, SharedPrefrences.getAdvertiseModel(activity).getG().getN2(), AdsSize,
                                FbNativAdsLayout, GoogleNativAds, QurekaorPredChampType, pos);

                    } else {

                        GoogleNativAdsNew(activity, SharedPrefrences.getAdvertiseModel(activity).getG().getNb(), AdsSize,
                                FbNativAdsLayout, GoogleNativAds, QurekaorPredChampType, pos);

                    }
                } else {

                    Log.e("vick1", "222222");

                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                    } else {
                        if (myCallback != null) {
                            myCallback.callbackCallFail("Failed load qureka");
                            myCallback = null;
                            return;
                        }

                    }

//                    DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                if (nativeAdsFb == null) {
                    isLodingFacebookAds = false;
                    if (myCallback != null) {
                        myCallback.callbackCallFail("Error lib Fb Nativ==>" + ad.getPlacementId());
                        // myCallback = null;
                    }
                    return;
                } else {
                    isLodingFacebookAds = true;
                    if (myCallback != null) {
                        myCallback.callbackCallSuccess("Facebook big native load==>");
                        myCallback = null;
                    }
                    return;
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
            }
        };
        nativeAdsFb.loadAd(
                nativeAdsFb.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());

        Log.e("01/10 facebook native request -====>", "nativeAdsFb");


    }

    private void FacebookNativeBannerAds(final Activity activity, final String adIds,
                                         final NativeAdLayout FbNativAdsLayout,
                                         final FrameLayout GoogleNativAds, final int AdsSize,
                                         final String QurekaorPredChampType, final int pos) {

        nativeBannerAd = new NativeBannerAd(activity, adIds);
        Log.d("06/10", "FacebookNativeBannerAds");
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to
                Log.d("06/10", "error --- >" + adError.getErrorMessage());
                Log.e("ads Native failed: ", adError.getErrorMessage());
                isLodingFacebookAds = false;
                /*if (myCallback != null) {
                    myCallback.callbackCallFail("Error lib Fb Nativ==>" + adError.getErrorMessage());
                    // myCallback = null;
                }*/

                if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("F,G")
                        &&
                        ((SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getN()))
                                || (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getN())))
                ) {
                    if (AdsSize == 2) {

                        GoogleNativAdsNew(activity, SharedPrefrences.getAdvertiseModel(activity).getG().getN(), AdsSize,
                                FbNativAdsLayout, GoogleNativAds, QurekaorPredChampType, pos);

                    } else {

                        GoogleNativAdsNew(activity, SharedPrefrences.getAdvertiseModel(activity).getG().getNb(), AdsSize,
                                FbNativAdsLayout, GoogleNativAds, QurekaorPredChampType, pos);

                    }
                } else {

//                    DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);

                    Log.e("vick1", "333333");

                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                    } else {
                        if (myCallback != null) {
                            myCallback.callbackCallFail("Failed load qureka");
                            myCallback = null;
                            return;
                        }
                    }
                }

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d("06/10", "onAdLoadedddd");
                if (nativeBannerAd == null) {
                    isLodingFacebookAds = false;
                    if (myCallback != null) {
                        myCallback.callbackCallFail("Error lib Fb Nativ==>" + ad.getPlacementId());
                        // myCallback = null;
                    }
                    return;
                } else {
                    isLodingFacebookAds = true;
                    if (myCallback != null) {
                        myCallback.callbackCallSuccess("Facebook native banner load==>");
                        myCallback = null;
                    }
                    return;
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
            }
        };

        nativeBannerAd.loadAd(
                nativeBannerAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());

        Log.e("01/10 facebook native request -====>", "nativeAdsFb");


    }

    private void GoogleNativAdsNew(final Activity activity, final String AdIds, final int AdsSize,
                                   final NativeAdLayout FbNativAdsLayout,
                                   final FrameLayout GoogleNativAds,
                                   final String QurekaorPredChampType, final int pos) {
        try {
            try {
                final AdLoader.Builder builder = new AdLoader.Builder(activity, AdIds);

                builder.forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                        NativeAdView adView =
                                (NativeAdView) activity.getLayoutInflater()
                                        .inflate(R.layout.google_native_layout, null);
                        if (AdsSize == 1) {
                            Log.d("05/10", "DisplayGoogleAdsCallThisFunction--pos--" + pos);
                            if (pos == 1) {
                                adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.google_native_layout_2, null);
                            } else
                                adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.google_native_layout, null);
                        }
                        populateNativeAdView(activity, nativeAd, adView, AdsSize);

                        if (googlenativeAd != null) {
                        }
                        googlenativeAd = nativeAd;
                        isLodingGoogleAds = true;
                        if (myCallback != null) {
                            myCallback.callbackCallSuccess("GoogleNativAds Success" + "   Google Nativ");
                            myCallback = null;
                            return;
                        }
                    }

                });

                VideoOptions videoOptions = new VideoOptions.Builder().build();
                NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
                builder.withNativeAdOptions(adOptions);

                AdLoader adLoader = builder.withAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        if (myCallback != null) {
                            myCallback.callbackCallClose();
                            myCallback = null;
                            return;
                        }
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.e("06/10 onAdFailedToLoad- ", loadAdError.getMessage() + " ");
                        isLodingGoogleAds = false;
                        googlenativeAd = null;
                        /*if (myCallback != null) {
                            myCallback.callbackCallFail("GoogleNativAds Nativ==>" + String.valueOf(loadAdError.getCode()) + "   Google Nativ");
                            myCallback = null;
                            return;
                        }*/

                        if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                                && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("G,F")
                                &&
                                ((SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                        && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getN()))
                                        || (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null
                                        && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getN())))
                        ) {

                            if (AdsSize == 2) {
                                FacebookAds(activity, SharedPrefrences.getAdvertiseModel(activity).getF().getN(), FbNativAdsLayout, GoogleNativAds, AdsSize, QurekaorPredChampType, pos);
                            } else {
                                FacebookNativeBannerAds(activity, SharedPrefrences.getAdvertiseModel(activity).getF().getNb(), FbNativAdsLayout, GoogleNativAds, AdsSize, QurekaorPredChampType, pos);
                            }


                        } else {

//                            DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);

                            if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                                    || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                                    || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                                Log.e("vick1", "444444 2222222");
                                DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                            } else {
                                Log.e("vick1", "444444 3333333");

                                if (myCallback != null) {
                                    myCallback.callbackCallFail("Failed load qureka");
                                    myCallback = null;
                                    return;
                                }

                            }

                        }

                        super.onAdFailedToLoad(loadAdError);
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }
                }).build();


                Bundle extras = new Bundle();
                extras.putString("max_ad_content_rating", "G");
                AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build();
                adLoader.loadAd(adRequest);

                Log.e("01/10 google native request -====>", "adRequest");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            Log.e("OutOfMemoryError", "OutOfMemoryError");

        }
    }

    private void GoogleNativAdsNew(final Activity activity, final String AdIds, final int AdsSize,
                                   final NativeAdLayout FbNativAdsLayout,
                                   final FrameLayout GoogleNativAds,
                                   final String QurekaorPredChampType, final int pos,
                                   boolean isPrior) {
        try {
            try {
                final AdLoader.Builder builder = new AdLoader.Builder(activity, AdIds);

                builder.forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                        NativeAdView adView =
                                (NativeAdView) activity.getLayoutInflater()
                                        .inflate(R.layout.google_native_layout, null);
                        if (AdsSize == 1) {
                            Log.d("05/10", "DisplayGoogleAdsCallThisFunction--pos--" + pos);
                            if (pos == 1) {
                                adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.google_native_layout_2, null);
                            } else
                                adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.google_native_layout, null);
                        }
                        populateNativeAdView(activity, nativeAd, adView, AdsSize);

                        if (googlenativeAd != null) {
                        }
                        googlenativeAd = nativeAd;
                        isLodingGoogleAds = true;
                        if (myCallback != null) {
                            myCallback.callbackCallSuccess("GoogleNativAds Success" + "   Google Nativ");
                            myCallback = null;
                            return;
                        }
                    }

                });

                VideoOptions videoOptions = new VideoOptions.Builder().build();
                NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
                builder.withNativeAdOptions(adOptions);

                AdLoader adLoader = builder.withAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        if (myCallback != null) {
                            myCallback.callbackCallClose();
                            myCallback = null;
                            return;
                        }
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.e("06/10 onAdFailedToLoad- ", loadAdError.getMessage() + " ");
                        Log.d("19/12", "big -- getF().getN2()--->" + loadAdError.getMessage());
                        isLodingGoogleAds = false;
                        googlenativeAd = null;
                        /*if (myCallback != null) {
                            myCallback.callbackCallFail("GoogleNativAds Nativ==>" + String.valueOf(loadAdError.getCode()) + "   Google Nativ");
                            myCallback = null;
                            return;
                        }*/

                        if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                                && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("G,F")
                                &&
                                ((SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                        && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getN2()))
                                        || (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null
                                        && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getN2())))
                        ) {

                            if (AdsSize == 2) {
                                Log.d("19/12", "getF().getN2()--->" + SharedPrefrences.getAdvertiseModel(activity).getF().getN2());
                                FacebookAds(activity, SharedPrefrences.getAdvertiseModel(activity).getF().getN2(), FbNativAdsLayout, GoogleNativAds, AdsSize, QurekaorPredChampType, pos, true);
                            } else {
                                FacebookNativeBannerAds(activity, SharedPrefrences.getAdvertiseModel(activity).getF().getNb(), FbNativAdsLayout, GoogleNativAds, AdsSize, QurekaorPredChampType, pos);
                            }


                        } else {

//                            DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);

                            if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                                    || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                                    || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                                Log.e("vick1", "444444 2222222");
                                DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                            } else {
                                Log.e("vick1", "444444 3333333");

                                if (myCallback != null) {
                                    myCallback.callbackCallFail("Failed load qureka");
                                    myCallback = null;
                                    return;
                                }

                            }

                        }

                        super.onAdFailedToLoad(loadAdError);
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }
                }).build();


                Bundle extras = new Bundle();
                extras.putString("max_ad_content_rating", "G");
                AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build();
                adLoader.loadAd(adRequest);

                Log.e("01/10 google native request -====>", "adRequest");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            Log.e("OutOfMemoryError", "OutOfMemoryError");

        }
    }

    private void DisplayFacebookNatviCallFunction(final Activity activity,
                                                  final NativeAdLayout fbNativAdsLayout, final int AdsSize,
                                                  final FrameLayout GoogleNativAds,
                                                  final String QurekaorPredChampType) {
        try {
            if (nativeAdsFb != null && nativeAdsFb.isAdLoaded()) {
                inflateAdFb(nativeAdsFb, activity, fbNativAdsLayout, AdsSize);
                if (myCallbackDisplay != null) {
                    myCallbackDisplay.callbackCallSuccess("OnResumeSuc" + "Ok" + "Ok");
                }
            } else {
                if (isLodingFacebookAds) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DisplayFacebookNatviCallFunction(activity, fbNativAdsLayout, AdsSize, GoogleNativAds, QurekaorPredChampType);
                        }
                    }, 1000);
                } else {


                    isLodingFacebookAds = false;
                  /*  if (myCallbackDisplay != null) {
                        myCallbackDisplay.callbackCallFail("Facebook Ads Failed");
                    }*/
//                    DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                    Log.e("vick1", "555555");


                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {

                        DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                    } else {
                        if (myCallbackDisplay != null) {
                            myCallbackDisplay.callbackCallFail("Failed load qureka");
                            myCallbackDisplay = null;
                            return;
                        }

                    }

                }
            }
           /* if (AdsSize == 1) {
                Log.e("20/9 Ads layout -- ", "Calling 1 ");
                nativeAdMedia.setVisibility(View.GONE);
            } else {
                nativeAdMedia.setVisibility(View.VISIBLE);
                Log.e("20/9 Ads layout -- ", "Calling else ");

            }
*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DisplayFacebookNativeBannerCallFunction(final Activity activity,
                                                         final NativeAdLayout fbNativAdsLayout, final int AdsSize,
                                                         final FrameLayout GoogleNativAds,
                                                         final String QurekaorPredChampType,
                                                         final int pos) {
        try {
            Log.d("06/10", "DisplayFacebookNativeBannerCallFunction---<<");
            if (nativeBannerAd != null && nativeBannerAd.isAdLoaded()) {
                Log.d("06/10", "DisplayFacebookNativeBannerCallFunction---<<if");
                inflateNativeBannerAd(nativeBannerAd, activity, fbNativAdsLayout, AdsSize, pos);
                if (myCallbackDisplay != null) {
                    myCallbackDisplay.callbackCallSuccess("OnResumeSuc" + "Ok" + "Ok");
                }
            } else {
                Log.d("06/10", "DisplayFacebookNativeBannerCallFunction---<<else");
                if (isLodingFacebookAds) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            DisplayFacebookNativeBannerCallFunction(activity, fbNativAdsLayout, AdsSize,
                                    GoogleNativAds, QurekaorPredChampType, pos);
                        }
                    }, 1000);
                } else {

                    Log.d("06/10", "DisplayFacebookNativeBannerCallFunction---<<else {{{{{{");

                    isLodingFacebookAds = false;
                    /*if (myCallbackDisplay != null) {
                        myCallbackDisplay.callbackCallFail("Facebook Ads Failed");
                    }*/
//                    DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);

                    Log.e("vick1", "666666");

                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        DisplayQurekaLoad(activity, GoogleNativAds, AdsSize, QurekaorPredChampType);
                    } else {
                        if (myCallbackDisplay != null) {
                            myCallbackDisplay.callbackCallFail("Failed load qureka");
                            myCallbackDisplay = null;
                            return;
                        }

                    }
                }
            }
           /* if (AdsSize == 1) {
                Log.e("20/9 Ads layout -- ", "Calling 1 ");
                nativeAdMedia.setVisibility(View.GONE);
            } else {
                nativeAdMedia.setVisibility(View.VISIBLE);
                Log.e("20/9 Ads layout -- ", "Calling else ");

            }
*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DisplayGoogleAdsCallThisFunction(final Activity activity, final FrameLayout googleNativAds,
                                                  final int AdsSize,
                                                  final String QurekaorPredChampType, final int pos) {
        if (googlenativeAd != null) {
            NativeAdView adView =
                    (NativeAdView) activity.getLayoutInflater().inflate(R.layout.google_native_layout, null);
            if (AdsSize == 1) {
                Log.d("05/10", "DisplayGoogleAdsCallThisFunction--pos--" + pos);
                if (pos == 1) {
                    adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.google_native_layout_2, null);
                } else
                    adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.google_native_layout, null);
            }
            populateNativeAdView(activity, googlenativeAd, adView, AdsSize);
            googleNativAds.removeAllViews();
            googleNativAds.addView(adView);
            Log.d("06/10", "DisplayGoogleAdsCallThisFunction----" + (myCallback != null));
            if (myCallbackDisplay != null) {
                myCallbackDisplay.callbackCallSuccess("Display Google" + "  Yes");
            }
        } else {
            if (isLodingGoogleAds) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DisplayGoogleAdsCallThisFunction(activity, googleNativAds, AdsSize, QurekaorPredChampType, pos);
                    }
                }, 1000);
            } else {
                /*if (myCallbackDisplay != null) {
                    myCallbackDisplay.callbackCallFail("Display  Google Ads Failed");
                }*/
//                DisplayQurekaLoad(activity, googleNativAds, AdsSize, QurekaorPredChampType);

                Log.e("vick1", "777777");

                if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                    DisplayQurekaLoad(activity, googleNativAds, AdsSize, QurekaorPredChampType);
                } else {
                    if (myCallbackDisplay != null) {
                        myCallbackDisplay.callbackCallFail("Failed load qureka");
                        myCallbackDisplay = null;
                        return;
                    }
                }
            }
        }
    }

    private void inflateAdFb(NativeAd nativeAd, Activity activity, NativeAdLayout nativeAdLayout, int AdsSize) {
        nativeAd.unregisterView();
        if (nativeAdLayout != null) {
            nativeAdLayout.removeAllViews();
        }
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.fb_native_layout, nativeAdLayout, false);
        if (nativeAdLayout != null) {
            nativeAdLayout.addView(adView);
        }
        // Add the AdOptionsView
        LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_icon_view);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        TextView nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
//        nativeAdBody.setText("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
//        nativeAdSocialContext.setText("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.GONE);

        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);


        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);

        Log.e("AdsSize", "==>" + AdsSize + "");

        if (AdsSize == 1) {
            nativeAdMedia.setVisibility(View.GONE);
        } else {
            nativeAdMedia.setVisibility(View.VISIBLE);
        }
    }

    private void inflateNativeBannerAd(NativeBannerAd nativeBannerAd, Activity activity, NativeAdLayout nativeAdLayout,
                                       int AdsSize, int pos) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        if (nativeAdLayout != null) {
            nativeAdLayout.removeAllViews();
        }
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.

        if (AdsSize == 50) {
            adView = (LinearLayout) inflater.inflate(R.layout.fb_native_banner_small_ad, nativeAdLayout, false);
        } else {
            if (pos == 1)
                adView = (LinearLayout) inflater.inflate(R.layout.fb_native_banner_ad_2, nativeAdLayout, false);
            else
                adView = (LinearLayout) inflater.inflate(R.layout.fb_native_banner_ad, nativeAdLayout, false);
        }
        if (nativeAdLayout != null) {
            nativeAdLayout.addView(adView);
        }

        /*// Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_banner_ad_container);
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_unit, nativeAdLayout, false);
        nativeAdLayout.addView(adView);*/

        // Add the AdChoices icon
        LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        MediaView nativeAdIconView = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }

    private void populateNativeAdView(Activity activity, com.google.android.gms.ads.nativead.NativeAd nativeAd,
                                      NativeAdView adView, int AdsSize) {
        com.google.android.gms.ads.nativead.MediaView mideaview = null;
        if (AdsSize == 2) {
            mideaview = adView.findViewById(R.id.ad_media);
            // Set the media view.
            adView.setMediaView((com.google.android.gms.ads.nativead.MediaView) mideaview);
        }
        adView.findViewById(R.id.ad_stars).setVisibility(View.GONE);
        // Set other ad assets.
        TextView ad_body = adView.findViewById(R.id.ad_body);
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (mideaview != null)
            adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.

        if (nativeAd.getBody() == null) {
//            adView.getBodyView().setVisibility(View.INVISIBLE);
            adView.getBodyView().setVisibility(View.GONE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
//            adView.getCallToActionView().setVisibility(View.INVISIBLE);
            adView.getCallToActionView().setVisibility(View.GONE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            ((TextView) adView.getCallToActionView()).setBackground(activity.getResources().getDrawable(R.drawable.btn_ads_blue_btn));
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
//            adView.getPriceView().setVisibility(View.INVISIBLE);
            adView.getPriceView().setVisibility(View.GONE);
        } else {
            adView.getPriceView().setVisibility(View.GONE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
//            adView.getStoreView().setVisibility(View.INVISIBLE);
            adView.getStoreView().setVisibility(View.GONE);
        } else {
            adView.getStoreView().setVisibility(View.GONE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
//            adView.getStarRatingView().setVisibility(View.INVISIBLE);
            adView.getStarRatingView().setVisibility(View.GONE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.GONE);
        }

        if (nativeAd.getAdvertiser() == null) {
//            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
            adView.getAdvertiserView().setVisibility(View.GONE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }


        adView.setNativeAd(nativeAd);
        VideoController vc = nativeAd.getMediaContent().getVideoController();
        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        } else {
        }

        if (mideaview != null) {
            mideaview.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View parent, View child) {
                    if (child instanceof ImageView) {
                        ImageView imageView = (ImageView) child;
                        imageView.setAdjustViewBounds(true);
                    }
                }

                @Override
                public void onChildViewRemoved(View parent, View child) {
                }
            });
            mideaview.setVisibility(View.VISIBLE);
        }
    }

}
