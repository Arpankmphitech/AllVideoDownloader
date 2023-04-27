package com.androidads.adsdemo;import android.Manifest;import android.app.Activity;import android.content.ActivityNotFoundException;import android.content.Intent;import android.net.Uri;import android.os.Bundle;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.widget.ImageView;import android.widget.Toast;import androidx.annotation.NonNull;import com.androidads.adsdemo.common.AdvertiseUtils;import com.androidads.adsdemo.common.SharedPrefrences;import com.androidads.adsdemo.common.Utils;import com.androidads.adsdemo.model.QurekaandPreadChampAdvertiseModel;import com.facebook.ads.Ad;import com.facebook.ads.AdError;import com.facebook.ads.AdSettings;import com.google.ads.mediation.admob.AdMobAdapter;import com.google.android.gms.ads.AdListener;import com.google.android.gms.ads.AdRequest;import com.google.android.gms.ads.AdSize;import com.google.android.gms.ads.AdView;import com.google.android.gms.ads.LoadAdError;public class AllBannerAds {    public static boolean isSucces = false;    private static AllBannerAds mInstance;    private MyCallback myCallback;    private String TAG = "Inmbi";    private static String[] PERMISSIONS_STORAGE = {            Manifest.permission.ACCESS_FINE_LOCATION,            Manifest.permission.ACCESS_COARSE_LOCATION,            Manifest.permission.VIBRATE,    };    public interface MyCallback {        void callbackCallSuccess(View str);        void callbackCallFail(String str);        void callbackCallClose();    }    public static AllBannerAds getInstance() {        if (mInstance == null) {            mInstance = new AllBannerAds();        }        return mInstance;    }    public void BannerAds(Activity activity, String adTypeString, String size, String Id, MyCallback _myCallback, String Account,                          String QurekaorPredChampType) {        SharedPrefrences.get(activity);        isSucces = false;        myCallback = _myCallback;        int adType = Integer.parseInt(adTypeString);        switch (adType) {            case 0:                if (myCallback != null) {                    myCallback.callbackCallFail("no Ads 0");                    myCallback = null;                }                break;            case 1:                FbBanner(activity, size, Id, QurekaorPredChampType);                break;            case 2:                GoogleBanner(activity, size, Id, QurekaorPredChampType);                break;            case 3:                //DisplayQurekaLoad(activity, size, QurekaorPredChampType);                if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)                        || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)                        || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {                    DisplayQurekaLoad(activity, size, QurekaorPredChampType);                } else {                    if (myCallback != null) {                        myCallback.callbackCallFail("no Ads 0");                        myCallback = null;                        return;                    }                }                break;            default:                if (myCallback != null) {                    myCallback.callbackCallFail("no Ads");                    myCallback = null;                }                break;        }    }    private void DisplayQurekaLoad(final Activity activity, String adsSize, final String QurekaorPredChamp) {        //0 or blank PredChamp and 1 Qureka        SharedPrefrences.get(activity);        View myView = LayoutInflater.from(activity)                .inflate(R.layout.qureka_smart_banner, null, false);        ImageView ivBanner = myView.findViewById(R.id.ivBanner);        final QurekaandPreadChampAdvertiseModel finalQurekaandPreadChampAdvertiseModel = AdvertiseUtils.getSmartBannerModelRandom(activity);        if (AdvertiseUtils.isValidationEmptyWithZero(QurekaorPredChamp)) {            ivBanner.setImageResource(finalQurekaandPreadChampAdvertiseModel.getBanner());        } else {//            ivBanner.setImageResource(AdvertiseUtils.getQurekaSmallBannerImage());        }        ivBanner.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if (activity != null) {                    assert finalQurekaandPreadChampAdvertiseModel != null;                    if (!AdvertiseUtils.isValidationEmptyWithZero(finalQurekaandPreadChampAdvertiseModel.getUrl())) {                        try {                            Intent intent = new Intent(Intent.ACTION_VIEW);                            intent.setData(Uri.parse(finalQurekaandPreadChampAdvertiseModel.getUrl()));                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);                            activity.startActivity(intent);                        } catch (ActivityNotFoundException e) {                            Toast.makeText(activity, "No application that can handle this link found", Toast.LENGTH_SHORT).show();                        }                    } else {                        //Toast.makeText(activity, "No ads to show.", Toast.LENGTH_LONG).show();                    }                }            }        });        if (myCallback != null) {            myCallback.callbackCallSuccess(myView);            myCallback = null;            return;        }    }    private void FbBanner(final Activity activity, final String sizeString, final String Id,                          final String QurekaorPredChampType) {        final com.facebook.ads.AdView adView;        int size = Integer.parseInt(sizeString);        switch (size) {            case 1:                adView = new com.facebook.ads.AdView(activity, Id, com.facebook.ads.AdSize.BANNER_HEIGHT_50);                break;            case 2:                adView = new com.facebook.ads.AdView(activity, Id, com.facebook.ads.AdSize.BANNER_HEIGHT_90);                break;            case 3:                adView = new com.facebook.ads.AdView(activity, Id, com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);                break;            default:                adView = new com.facebook.ads.AdView(activity, Id, com.facebook.ads.AdSize.BANNER_HEIGHT_50);                break;        }        AdSettings.addTestDevice("b4594d0b-e98a-4c4c-a3cd-4b6f44bfdcd4");      /*  AdListener adListener = new AdListener(){            @Override            public void onAdFailedToLoad(LoadAdError loadAdError) {                super.onAdFailedToLoad(loadAdError);            }            @Override            public void onAdLoaded() {                super.onAdLoaded();            }        };        adView.loadAd(adView.buildLoadAdConfig()                .withAdListener((com.facebook.ads.AdListener) adListener).build());*/        adView.loadAd(adView.buildLoadAdConfig().withAdListener(new com.facebook.ads.AdListener() {            @Override            public void onError(Ad ad, AdError adError) {                /*if (myCallback != null) {                    myCallback.callbackCallFail(adError.getErrorMessage());                    myCallback = null;                }*/                if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("F,G")                        &&                        (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getB()))                ) {                    GoogleBanner(activity, sizeString, SharedPrefrences.getAdvertiseModel(activity).getG().getB(), QurekaorPredChampType);                } else {                    //DisplayQurekaLoad(activity, sizeString, QurekaorPredChampType);                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {                        DisplayQurekaLoad(activity, sizeString, QurekaorPredChampType);                    } else {                        if (myCallback != null) {                            myCallback.callbackCallFail("no Ads 0");                            myCallback = null;                            return;                        }                    }                }            }            @Override            public void onAdLoaded(Ad ad) {                if (myCallback != null) {                    myCallback.callbackCallSuccess(adView);                    //   myCallback = null;                }            }            @Override            public void onAdClicked(Ad ad) {            }            @Override            public void onLoggingImpression(Ad ad) {            }        }).build());        Log.e("tag", "FbBanner");       /* adView.setAdListener(new com.facebook.ads.AdListener() {            @Override            public void onError(Ad ad, AdError adError) {                if (myCallback != null) {                    myCallback.callbackCallFail(adError.getErrorMessage());                    myCallback = null;                }            }            @Override            public void onAdLoaded(Ad ad) {                if (myCallback != null) {                    myCallback.callbackCallSuccess(adView);                    //   myCallback = null;                }            }            @Override            public void onAdClicked(Ad ad) {            }            @Override            public void onLoggingImpression(Ad ad) {            }        });*/    }    private void GoogleBanner(final Activity activity, final String sizeString, final String Id, final String QurekaorPredChampType) {        Bundle extras = new Bundle();        extras.putString("max_ad_content_rating", "G");        AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras)                .build();        final AdView adView = new AdView(activity);        int size = Integer.parseInt(sizeString);        switch (size) {            case 1:                adView.setAdSize(AdSize.BANNER);                break;            case 2:                adView.setAdSize(AdSize.FULL_BANNER);                break;            case 3:                adView.setAdSize(AdSize.LARGE_BANNER);                break;            case 4:                adView.setAdSize(AdSize.MEDIUM_RECTANGLE);                break;            case 5:                adView.setAdSize(AdSize.SMART_BANNER);                break;            default:                adView.setAdSize(AdSize.BANNER);                break;        }        adView.setAdUnitId(Id);        adView.setAdListener(new AdListener() {            @Override            public void onAdLoaded() {                if (myCallback != null) {                    myCallback.callbackCallSuccess(adView);                    myCallback = null;                }            }            @Override            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {                super.onAdFailedToLoad(loadAdError);                isSucces = false;                adView.destroy();                /*if (myCallback != null) {                    myCallback.callbackCallFail(String.valueOf(loadAdError.getMessage()));                    adView.destroy();                    myCallback = null;                }*/                if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("G,F")                        &&                        (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getB()))) {                    FbBanner(activity, sizeString, SharedPrefrences.getAdvertiseModel(activity).getF().getB(), QurekaorPredChampType);                } else {                    //DisplayQurekaLoad(activity, sizeString, QurekaorPredChampType);                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {                        DisplayQurekaLoad(activity, sizeString, QurekaorPredChampType);                    } else {                        if (myCallback != null) {                            myCallback.callbackCallFail("no Ads 0");                            myCallback = null;                            return;                        }                    }                }                Log.d("Ads Banner fail - ", loadAdError.getMessage());            }            @Override            public void onAdOpened() {            }            @Override            public void onAdClicked() {                // Code to be executed when the user clicks on an ad.            }            @Override            public void onAdClosed() {                if (myCallback != null) {                    myCallback.callbackCallClose();                    myCallback = null;                }            }        });        adView.loadAd(adRequest);        Log.e("01/10 ", "GoogleBanner");    }}