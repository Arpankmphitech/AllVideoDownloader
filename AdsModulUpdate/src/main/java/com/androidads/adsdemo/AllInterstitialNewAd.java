package com.androidads.adsdemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidads.adsdemo.common.AdvertiseUtils;
import com.androidads.adsdemo.common.AppOpenManager;
import com.androidads.adsdemo.common.SharedPrefrences;
import com.androidads.adsdemo.common.Utils;
import com.androidads.adsdemo.model.QurekaandPreadChampAdvertiseModel;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.jgabrielfreitas.core.BlurImageView;

import pl.droidsonroids.gif.GifImageView;


/**
 * Created by Nitull Patel on 10/17/2018.
 */

public class AllInterstitialNewAd {
    private com.facebook.ads.InterstitialAd interstitialAd = null;
    private InterstitialAd interstitialAdsGoogel = null;


    private static AllInterstitialNewAd mInstance;
    private static MyCallback myCallback;

    private boolean isShowDirectDisplya = false;
    private boolean isShowDirectDisplyaFb = false;

    private boolean isShowDirectDisplyaAppNext = false;
    private String TAG = "brijali ads ";

    public static boolean isOpenQureka = false;

    public interface MyCallback {
        void callbackCall(String str);
    }

    public static AllInterstitialNewAd getInstance() {

        if (mInstance == null) {
            mInstance = new AllInterstitialNewAd();
        }
        return mInstance;
    }

    public void AllInterstitialNewAd(Activity activity, String AdsTypeString, String Id, String AccountId_,
                                     boolean isNextPage,
                                     String qurekaorPredChampType) {

        SharedPrefrences.get(activity);

        Log.e("01/02 AllInterstitialNewAd call -=-=-=>", "AllInterstitialNewAd");

        if (isOpenQureka) {
            Log.e("01/02 isOpenQureka call -=-=-=>", isOpenQureka + "");
            return;
        }

        isShowDirectDisplyaFb = false;
//        isShowDirectDisplya = false;
        if (activity != null && !activity.isFinishing()) {
            int AdsType = Integer.parseInt(AdsTypeString);
            switch (AdsType) {
                case 0:
                    if (myCallback != null) {
                        myCallback.callbackCall("Account Type 0");
                        myCallback = null;
                    }
                    break;
                case 1:
                    Facebook(activity, Id, isNextPage, qurekaorPredChampType);
                    break;
                case 2:
                    GoogleAds(activity, Id, isNextPage, qurekaorPredChampType);
                    break;
                default:
                    if (myCallback != null) {
                        myCallback.callbackCall("Account Type default");
                        myCallback = null;
                    }
                    break;
            }
        } else {
            if (myCallback != null) {
                myCallback.callbackCall("Account Type Activity Null");
                myCallback = null;
            }
        }
    }

    public void AllInterstitialNewAd(Activity activity, String AdsTypeString, String Id, String AccountId_,
                                     boolean isNextPage,
                                     String qurekaorPredChampType, MyCallback callback, Application application) {

        myCallback = callback;
        SharedPrefrences.get(activity);

        Log.e("01/02 AllInterstitialNewAd call -=-=-=>", "AllInterstitialNewAd");

        if (isOpenQureka) {
            Log.e("01/02 isOpenQureka call -=-=-=>", isOpenQureka + "");
            return;
        }

        isShowDirectDisplyaFb = true;
//        isShowDirectDisplya = false;
        if (activity != null && !activity.isFinishing()) {
            int AdsType = Integer.parseInt(AdsTypeString);
            Log.d("09/12", "AdsType -- " + AdsType);
            switch (AdsType) {
                case 0:
                    if (myCallback != null) {
                        myCallback.callbackCall("Account Type 0");
                        myCallback = null;
                    }
                    break;
                case 1:
                    Log.d("09/12", "fb==1");
                    FacebookSplash(activity, Id, isNextPage, qurekaorPredChampType, application);
                    break;
                case 2:
                    GoogleAds(activity, Id, isNextPage, qurekaorPredChampType);
                    break;
                default:
                    if (myCallback != null) {
                        myCallback.callbackCall("Account Type default");
                        myCallback = null;
                    }
                    break;
            }
        } else {
            if (myCallback != null) {
                myCallback.callbackCall("Account Type Activity Null");
                myCallback = null;
            }
        }
    }

    public void displayInterstitial(Activity activity, String AdsTypeString, String Id,
                                    String AccountId, MyCallback _myCallback, boolean isNextPage,
                                    boolean isShowLoader,
                                    String qurekaorPredChampType) {

        SharedPrefrences.get(activity);
        Log.e("01/02 displayInterstitial call -=-=-=>", "displayInterstitial");
        isShowDirectDisplyaFb = true;
//        isShowDirectDisplya = true;
        myCallback = _myCallback;
        if (activity != null && !activity.isFinishing()) {
            int AdsType = Integer.parseInt(AdsTypeString);
            switch (AdsType) {
                case 0:
                    if (myCallback != null) {
                        myCallback.callbackCall("Account Type 0");
                        myCallback = null;
                    }
                    break;
                case 1:

                    if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                        CallFacebookDisplayAdsOrLoad(activity, AdsTypeString, Id, AccountId, isNextPage, isShowLoader, qurekaorPredChampType);
                    } else if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                            && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("F,G")
                            &&
                            (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                    && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getI()))
                            && interstitialAdsGoogel != null) {

                        CallGooglleDisplayAdsOrLoad(activity, AdsTypeString, SharedPrefrences.getAdvertiseModel(activity).getG().getI(), AccountId, isNextPage, isShowLoader, qurekaorPredChampType);

                    } else {
                        CallFacebookDisplayAdsOrLoad(activity, AdsTypeString, Id, AccountId, isNextPage, isShowLoader, qurekaorPredChampType);
                    }
                    break;

                case 2:

                    if (interstitialAdsGoogel != null) {

                        CallGooglleDisplayAdsOrLoad(activity, AdsTypeString, Id, AccountId, isNextPage, isShowLoader, qurekaorPredChampType);

                    } else if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                            && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("G,F")
                            &&
                            (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null
                                    && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getI()))
                            && interstitialAd != null && interstitialAd.isAdLoaded()) {

                        CallFacebookDisplayAdsOrLoad(activity, AdsTypeString,
                                SharedPrefrences.getAdvertiseModel(activity).getF().getI(),
                                AccountId, isNextPage, isShowLoader, qurekaorPredChampType);


                    } else {

                        CallGooglleDisplayAdsOrLoad(activity, AdsTypeString, Id, AccountId, isNextPage, isShowLoader, qurekaorPredChampType);

                    }


//                    CallGooglleDisplayAdsOrLoad(activity, AdsTypeString, Id, AccountId, isNextPage, isShowLoader, qurekaorPredChampType);
                    break;
                case 3:
                    //CallOpenQureka(activity, qurekaorPredChampType);

                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        CallOpenQureka(activity, qurekaorPredChampType);
                    } else {
                        if (myCallback != null) {
                            myCallback.callbackCall("Account Type 0");
                            myCallback = null;
                            return;
                        }
                    }

                    break;
                default:
                    if (myCallback != null) {
                        myCallback.callbackCall("Account Type default");
                        myCallback = null;
                    }
                    break;
            }
        } else {
            if (myCallback != null) {
                myCallback.callbackCall("Account  displayInterstitial Activity Numm");
                myCallback = null;
            }
        }
    }

    public static void CallOpenQureka(final Activity activity, final String qurekaorPredChampType) {

        Log.e("01/02 CallOpenQureka call -=-=-=>", "CallOpenQureka");

        isOpenQureka = true;

        TextView tvHeader, tital1, tital2, tvBottomText;
        ImageView imgBanner;
        BlurImageView imgBlurBanner;
        final Button btnPlayNow;
        LinearLayout llMain;
        GifImageView gifView;


        final Dialog[] dialog = {new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen)};
        dialog[0].getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        Log.d("06/10", "qurekaorPredChampType--=->" + qurekaorPredChampType);
        if (AdvertiseUtils.isValidationEmptyWithZero(qurekaorPredChampType)) {
            dialog[0].setContentView(AdvertiseUtils.getPredChampIntertialLayout()/*R.layout.custome_predchamp_interad*/);
        } else {
//            dialog[0].setContentView(AdvertiseUtils.getQurekaIntertialLayout()/*R.layout.custome_qureka_interad*/);
        }

        dialog[0].setCancelable(false);
        dialog[0].setCanceledOnTouchOutside(false);

        tvHeader = dialog[0].findViewById(R.id.tvHeader);
        tvBottomText = dialog[0].findViewById(R.id.tvBottomText);

        if (tvHeader != null)
            tvHeader.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));

        tital1 = dialog[0].findViewById(R.id.tital1);
        if (tital1 != null) {
            tital1.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));
        }

        tital2 = dialog[0].findViewById(R.id.tital2);
        if (tital2 != null) {
            tital2.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));
        }


        if (tvBottomText != null) {
            tvBottomText.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));
        }


        final QurekaandPreadChampAdvertiseModel qurekaandPreadChampAdvertiseModel = AdvertiseUtils.getPredchampHeaderTitleDescriptionList(activity);

        imgBlurBanner = dialog[0].findViewById(R.id.imgBlurBanner);
        if (imgBlurBanner != null) {
            imgBlurBanner.setImageResource(qurekaandPreadChampAdvertiseModel.getBanner());
            imgBlurBanner.setBlur(20);
        }

        imgBanner = dialog[0].findViewById(R.id.imgBanner);
        if (imgBanner != null) {
            imgBanner.setImageResource(qurekaandPreadChampAdvertiseModel.getBanner());
            imgBanner.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top2));
        }
        llMain = dialog[0].findViewById(R.id.llMain);
        gifView = dialog[0].findViewById(R.id.gifView);

        if (AdvertiseUtils.isValidationEmptyWithZero(qurekaorPredChampType)) {
            tvHeader.setText(qurekaandPreadChampAdvertiseModel.getHeader());
            tital1.setText(qurekaandPreadChampAdvertiseModel.getTitle());
            tvBottomText.setText(qurekaandPreadChampAdvertiseModel.getBody());
            gifView.setImageResource(AdvertiseUtils.getPredChampOnlyGIFImage());
        } else {
            /*QurekaandPreadChampAdvertiseModel qurekaandPreadChampAdvertiseModel = AdvertiseUtils.getQurekaHeaderTitleDescriptionList(activity);
            tvHeader.setText(qurekaandPreadChampAdvertiseModel.getHeader());
            tital1.setText(qurekaandPreadChampAdvertiseModel.getTitle());
            tvBottomText.setText(qurekaandPreadChampAdvertiseModel.getBody());
            gifView.setImageResource(AdvertiseUtils.getQurekaGIFImage());*/
        }


        btnPlayNow = dialog[0].findViewById(R.id.btnPlayNow);
        if (btnPlayNow != null) {
            btnPlayNow.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top3));
            btnPlayNow.setText(AdvertiseUtils.getInterstialButtonText(activity));
        }
        /*imgClose = dialog[0].findViewById(R.id.imgClose);
        imgClose.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top4));
*/

        if (btnPlayNow != null) {

            btnPlayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("01/02 btnPlayNow call -=-=-=>", "btnPlayNow");
                    if (dialog[0] != null && dialog[0].isShowing()) {
                        Log.e("01/02 btnClose dialog[0].dismiss() -=-=-=>", "dialog[0].dismiss()");
                        dialog[0].dismiss();
                    }
                    if (myCallback != null) {
                        myCallback.callbackCall("Success close Qureka");
                        myCallback = null;
                    }

                    dialog[0] = null;
                    try {

                        if (activity != null) {
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
                                //Toast.makeText(activity, "No ads to show.", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (ActivityNotFoundException e) {
                        //Toast.makeText(activity, "No ads to show.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
        }

        if (llMain != null)
            llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("01/02 llMain call -=-=-=>", "llMain");

                    btnPlayNow.performClick();
                }
            });

        dialog[0].findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("01/02 ivClose call -=-=-=>", "ivClose");
                isOpenQureka = false;
                if (dialog[0] != null && dialog[0].isShowing()) {
                    Log.e("01/02 dialog[0].dismiss() -=-=-=>", "dialog[0].dismiss()");
                    dialog[0].dismiss();
                }
                dialog[0] = null;
                if (myCallback != null) {
                    myCallback.callbackCall("Success close Qureka");
                    myCallback = null;
                }
            }
        });

        dialog[0].findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("01/02 btnClose call -=-=-=>", "btnClose");
                isOpenQureka = false;
                if (dialog[0] != null && dialog[0].isShowing()) {
                    Log.e("01/02 btnClose dialog[0].dismiss() -=-=-=>", "dialog[0].dismiss()");
                    dialog[0].dismiss();
                }
                dialog[0] = null;
                if (myCallback != null) {
                    myCallback.callbackCall("Success close Qureka");
                    myCallback = null;
                }
            }
        });

        dialog[0].setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                isOpenQureka = false;

                /*if (myCallback != null) {
                    myCallback.callbackCall("Success close Qureka");
                    myCallback = null;
                }*/
            }
        });

        if (activity != null && !activity.isFinishing())
            dialog[0].show();

    }

    public static void CallOpenQureka(final Activity activity, final String qurekaorPredChampType, MyCallback myCallBack) {

        myCallback = myCallBack;
        Log.e("01/02 CallOpenQureka call -=-=-=>", "CallOpenQureka");

        isOpenQureka = true;

        TextView tvHeader, tital1, tital2, tvBottomText;
        ImageView imgBanner;
        BlurImageView imgBlurBanner;
        final Button btnPlayNow;
        LinearLayout llMain;
        GifImageView gifView;


        final Dialog[] dialog = {new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen)};
        dialog[0].getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        Log.d("06/10", "qurekaorPredChampType--=->" + qurekaorPredChampType);
        if (AdvertiseUtils.isValidationEmptyWithZero(qurekaorPredChampType)) {
            dialog[0].setContentView(AdvertiseUtils.getPredChampIntertialLayout()/*R.layout.custome_predchamp_interad*/);
        } else {
//            dialog[0].setContentView(AdvertiseUtils.getQurekaIntertialLayout()/*R.layout.custome_qureka_interad*/);
        }

        dialog[0].setCancelable(false);
        dialog[0].setCanceledOnTouchOutside(false);

        tvHeader = dialog[0].findViewById(R.id.tvHeader);
        tvBottomText = dialog[0].findViewById(R.id.tvBottomText);

        if (tvHeader != null)
            tvHeader.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));

        tital1 = dialog[0].findViewById(R.id.tital1);
        if (tital1 != null) {
            tital1.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));
        }

        tital2 = dialog[0].findViewById(R.id.tital2);
        if (tital2 != null) {
            tital2.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));
        }


        if (tvBottomText != null) {
            tvBottomText.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));
        }


        final QurekaandPreadChampAdvertiseModel qurekaandPreadChampAdvertiseModel = AdvertiseUtils.getPredchampHeaderTitleDescriptionList(activity);

        imgBlurBanner = dialog[0].findViewById(R.id.imgBlurBanner);
        if (imgBlurBanner != null) {
            imgBlurBanner.setImageResource(qurekaandPreadChampAdvertiseModel.getBanner());
            imgBlurBanner.setBlur(20);
        }

        imgBanner = dialog[0].findViewById(R.id.imgBanner);
        if (imgBanner != null) {
            imgBanner.setImageResource(qurekaandPreadChampAdvertiseModel.getBanner());
            imgBanner.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top2));
        }
        llMain = dialog[0].findViewById(R.id.llMain);
        gifView = dialog[0].findViewById(R.id.gifView);

        if (AdvertiseUtils.isValidationEmptyWithZero(qurekaorPredChampType)) {
            tvHeader.setText(qurekaandPreadChampAdvertiseModel.getHeader());
            tital1.setText(qurekaandPreadChampAdvertiseModel.getTitle());
            tvBottomText.setText(qurekaandPreadChampAdvertiseModel.getBody());
            gifView.setImageResource(AdvertiseUtils.getPredChampOnlyGIFImage());
        } else {
            /*QurekaandPreadChampAdvertiseModel qurekaandPreadChampAdvertiseModel = AdvertiseUtils.getQurekaHeaderTitleDescriptionList(activity);
            tvHeader.setText(qurekaandPreadChampAdvertiseModel.getHeader());
            tital1.setText(qurekaandPreadChampAdvertiseModel.getTitle());
            tvBottomText.setText(qurekaandPreadChampAdvertiseModel.getBody());
            gifView.setImageResource(AdvertiseUtils.getQurekaGIFImage());*/
        }


        btnPlayNow = dialog[0].findViewById(R.id.btnPlayNow);
        if (btnPlayNow != null) {
            btnPlayNow.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top3));
            btnPlayNow.setText(AdvertiseUtils.getInterstialButtonText(activity));
        }
        /*imgClose = dialog[0].findViewById(R.id.imgClose);
        imgClose.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top4));
*/

        if (btnPlayNow != null) {

            btnPlayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("01/02 btnPlayNow call -=-=-=>", "btnPlayNow");
                    if (dialog[0] != null && dialog[0].isShowing()) {
                        Log.e("01/02 btnClose dialog[0].dismiss() -=-=-=>", "dialog[0].dismiss()");
                        dialog[0].dismiss();
                    }
                    if (myCallback != null) {
                        myCallback.callbackCall("Success close Qureka");
                        myCallback = null;
                    }

                    dialog[0] = null;
                    try {

                        if (activity != null) {
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
                                //Toast.makeText(activity, "No ads to show.", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (ActivityNotFoundException e) {
                        //Toast.makeText(activity, "No ads to show.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
        }

        if (llMain != null)
            llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("01/02 llMain call -=-=-=>", "llMain");

                    btnPlayNow.performClick();
                }
            });

        dialog[0].findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("01/02 ivClose call -=-=-=>", "ivClose");
                isOpenQureka = false;
                if (dialog[0] != null && dialog[0].isShowing()) {
                    Log.e("01/02 dialog[0].dismiss() -=-=-=>", "dialog[0].dismiss()");
                    dialog[0].dismiss();
                }
                dialog[0] = null;
                if (myCallback != null) {
                    myCallback.callbackCall("Success close Qureka");
                    myCallback = null;
                }
            }
        });

        dialog[0].findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("01/02 btnClose call -=-=-=>", "btnClose");
                isOpenQureka = false;
                if (dialog[0] != null && dialog[0].isShowing()) {
                    Log.e("01/02 btnClose dialog[0].dismiss() -=-=-=>", "dialog[0].dismiss()");
                    dialog[0].dismiss();
                }
                dialog[0] = null;
                if (myCallback != null) {
                    myCallback.callbackCall("Success close Qureka");
                    myCallback = null;
                }
            }
        });

        dialog[0].setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                isOpenQureka = false;

                /*if (myCallback != null) {
                    myCallback.callbackCall("Success close Qureka");
                    myCallback = null;
                }*/
            }
        });

        if (activity != null && !activity.isFinishing())
            dialog[0].show();

    }

    private void Facebook(final Activity activity, final String id, final boolean isNextPage,
                          final String qurekaorPredChampType) {
        if (interstitialAd != null && interstitialAd.isAdLoaded() && !isShowDirectDisplya) {
            return;
        }
        interstitialAd = new com.facebook.ads.InterstitialAd(activity, id);

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                if (interstitialAd != null) {
                    interstitialAd.destroy();
                }
                if (isNextPage) {

                } else {
                    if (interstitialAd != null) {
                        try {
                            interstitialAd.loadAd();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (myCallback != null) {
                    myCallback.callbackCall("onAdClosed Facebook");
                    myCallback = null;
                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e("ads", "Interstitial ad " + adError.getErrorMessage());
                Log.d("09/12", "onError-fb--" + adError.getErrorMessage());

                if (interstitialAd != null) {
                    interstitialAd.destroy();
                }

                interstitialAd = null;

                if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("F,G")
                        &&
                        (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getI()))) {
                    //Google Load Here VIck 07/03/2022

                    GoogleAds(activity, SharedPrefrences.getAdvertiseModel(activity).getG().getI(),
                            isNextPage, qurekaorPredChampType);


                } else {

                    if (isShowDirectDisplyaFb) {
                        isShowDirectDisplyaFb = false;
                        //CallOpenQureka(activity, qurekaorPredChampType);

                        if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                                || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                                || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                            CallOpenQureka(activity, qurekaorPredChampType);
                        } else {
                            if (myCallback != null) {
                                myCallback.callbackCall("Account Type 0");
                                myCallback = null;
                                return;
                            }
                        }

                        return;
                    }
                }

                /*if (myCallback != null) {
                    myCallback.callbackCall("onError" + adError.toString());
                    interstitialAd = null;
                    myCallback = null;
                } else {
                    interstitialAd = null;
                }*/
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                if (isShowDirectDisplyaFb) {
                    isShowDirectDisplyaFb = false;
                    if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                        interstitialAd.show();
                    } else {
                        if (myCallback != null) {
                            Log.e("6/11/2019", "MultipleClick Next");
                            myCallback.callbackCall("Int MultipleClick Next");
                            myCallback = null;
                        }
                    }
                } else {
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };
        AdSettings.addTestDevice("49792a5e-8a74-42b8-b134-aab6e24d6f75");
        if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());

            Log.e("01/10 facebook interstitialAd request -====>", "interstitialAd");


        }
    }

    private void FacebookSplash(final Activity activity, final String id, final boolean isNextPage,
                                final String qurekaorPredChampType, Application application) {
        if (interstitialAd != null && interstitialAd.isAdLoaded() && !isShowDirectDisplya) {
            interstitialAd.show();
            return;
        }
        interstitialAd = new com.facebook.ads.InterstitialAd(activity, id);

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                if (interstitialAd != null) {
                    interstitialAd.destroy();
                }
                if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("F,G")
                        &&
                        (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getAo()))
                        && application != null) {
                    new AppOpenManager(application);
                }
                if (isNextPage) {

                } else {
                    if (interstitialAd != null) {
                        try {
                            interstitialAd.loadAd();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (myCallback != null) {
                    myCallback.callbackCall("onAdClosed Facebook");
                    myCallback = null;
                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e("ads", "Interstitial ad " + adError.getErrorMessage());
                Log.d("09/12", "onError-fb--inter--" + adError.getErrorMessage());

                if (interstitialAd != null) {
                    interstitialAd.destroy();
                }

                interstitialAd = null;


                if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                        && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("F,G")
                        &&
                        (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getG() != null
                                && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getG().getAo()))
                        && application != null) {
                    //Google Load Here VIck 07/03/2022
                    Log.d("09/12", "F,G ----if");

//                    GoogleAds(activity, SharedPrefrences.getAdvertiseModel(activity).getG().getI(),
//                            isNextPage, qurekaorPredChampType);
                    AppOpenManager manager = new AppOpenManager(application);
                    manager.fetchSplashAd(activity, new AppOpenManager.onNext() {
                        @Override
                        public void nextActionPerform() {
                            if (myCallback != null) {
                                myCallback.callbackCall("Account Type 0");
                                myCallback = null;
                                return;
                            }
                        }
                    });
                } else {
                    Log.d("09/12", "F,G ----else");

//                    if (isShowDirectDisplyaFb) {
//                        isShowDirectDisplyaFb = false;
                    //CallOpenQureka(activity, qurekaorPredChampType);

                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        CallOpenQureka(activity, qurekaorPredChampType);
                    } else {
                        if (myCallback != null) {
                            myCallback.callbackCall("Account Type 0");
                            myCallback = null;
                            return;
                        }
                    }

                    return;
//                    }
                }

                /*if (myCallback != null) {
                    myCallback.callbackCall("onError" + adError.toString());
                    interstitialAd = null;
                    myCallback = null;
                } else {
                    interstitialAd = null;
                }*/
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                if (isShowDirectDisplyaFb) {
                    isShowDirectDisplyaFb = false;
                    if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                        interstitialAd.show();
                    } else {
                        if (myCallback != null) {
                            Log.e("6/11/2019", "MultipleClick Next");
                            myCallback.callbackCall("Int MultipleClick Next");
                            myCallback = null;
                        }
                    }
                } else {
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };
        AdSettings.addTestDevice("49792a5e-8a74-42b8-b134-aab6e24d6f75");
        if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());

            Log.e("01/10 facebook interstitialAd request -====>", "interstitialAd");


        }
    }

    private void GoogleAds(final Activity activity, String id, final boolean isNextPage,
                           final String qurekaorPredChampType) {
        try {

            if (interstitialAdsGoogel != null) {
                if (myCallback != null) {
                    myCallback.callbackCall("onAdFailedToLoad Google" + "Return");
                    myCallback = null;
                }
                return;
            }
            AdRequest adRequest = new AdRequest.Builder().build();
            Log.d("20/12", id);
            interstitialAdsGoogel.load(activity, id, adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                            Log.e("01/10 google interstitialAdsGoogel request -====>", "onAdLoaded");

                            interstitialAdsGoogel = interstitialAd;
                            if (isShowDirectDisplya) {
                                isShowDirectDisplya = false;
                                interstitialAdsGoogel.show(activity);

                                interstitialAdsGoogel.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {

                                        if (myCallback != null) {
                                            myCallback.callbackCall("Google Display Finish Actl");
                                            myCallback = null;
                                            interstitialAdsGoogel = null;
                                        } else {
                                        }
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                        super.onAdFailedToShowFullScreenContent(adError);
                                        Log.d("ads--- ", "onAdFailedToShowFullScreenContent  " + adError.getMessage());
                                        if (myCallback != null) {
                                            myCallback.callbackCall("Google Display Finish Actl");
                                            myCallback = null;
                                            interstitialAdsGoogel = null;
                                        }
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        Log.d("ads--- ", "onAdShowedFullScreenContent  ");
                                    }
                                });


                            }

                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d("20/12", loadAdError.getMessage());
                            Log.e("01/10 google interstitialAdsGoogel request -====>", "onAdFailedToLoad");

                            interstitialAdsGoogel = null;
                            /*if (myCallback != null) {
                                isShowDirectDisplya = false;
                                interstitialAdsGoogel = null;
                                myCallback.callbackCall("onAdFailedToLoad Google" + loadAdError.getMessage());
                                myCallback = null;
                            } else {
                                isShowDirectDisplya = false;
                                interstitialAdsGoogel = null;
                            }*/

                            if (SharedPrefrences.getAdvertiseModel(activity) != null && !Utils.isValidatEmpty(SharedPrefrences.getAdvertiseModel(activity).getPriority())
                                    && SharedPrefrences.getAdvertiseModel(activity).getPriority().equalsIgnoreCase("G,F")
                                    &&
                                    (SharedPrefrences.getAdvertiseModel(activity) != null && SharedPrefrences.getAdvertiseModel(activity).getF() != null
                                            && !Utils.isValidaEmptyWithZero(SharedPrefrences.getAdvertiseModel(activity).getF().getI()))) {
                                //Facebook Load Here VIck 07/03/2022

                                Facebook(activity, SharedPrefrences.getAdvertiseModel(activity).getF().getI(),
                                        isNextPage, qurekaorPredChampType);

                            } else {

                                if (isShowDirectDisplya) {
                                /*Log.e("26/01 isShowDirectDisplya -=-=-=-=>", isShowDirectDisplya + "");
                                isShowDirectDisplya = false;
                                CallOpenQureka(activity);
                                return;*/
                                    isShowDirectDisplya = false;
                                    interstitialAdsGoogel = null;
                              /*  myCallback.callbackCall("onAdFailedToLoad Google" + loadAdError.getMessage());
                                myCallback = null;*/
                                } else {
                                    isShowDirectDisplya = false;
                                    if (myCallback != null) {
                                        interstitialAdsGoogel = null;
//                                    myCallback.callbackCall("onAdFailedToLoad Google" + loadAdError.getMessage());
//                                    myCallback = null;
                                    } else {
                                        interstitialAdsGoogel = null;
                                    }
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();

            interstitialAdsGoogel = null;

            /*isShowDirectDisplya = false;
            if (myCallback != null) {
                isShowDirectDisplya = false;
                myCallback.callbackCall("onAdFailedToLoad Google");
                myCallback = null;
            }*/

            if (isShowDirectDisplya) {
                isShowDirectDisplya = false;
                //CallOpenQureka(activity, qurekaorPredChampType);

                if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                    CallOpenQureka(activity, qurekaorPredChampType);
                } else {
                    if (myCallback != null) {
                        myCallback.callbackCall("Account Type 0");
                        myCallback = null;
                        return;
                    }
                }
                return;

            } else {
                isShowDirectDisplya = false;
                if (myCallback != null) {
                    myCallback.callbackCall("onAdFailedToLoad Google");
                    myCallback = null;
                }
            }

        }
    }


    private void CallFacebookDisplayAdsOrLoad(Activity activity, String adsType, String id, String accountId,
                                              boolean isNextPage, boolean isShowLoader,
                                              String qurekaorPredChampType) {

        try {
            if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
                /*isShowDirectDisplyaFb = true;
                AllInterstitialNewAd(activity, adsType, id, accountId, isNextPage);*/

                isShowDirectDisplyaFb = false;
                /*if (myCallback != null) {
                    myCallback.callbackCall("Facebook Display Ads Fail");
                    myCallback = null;
                }*/

                //CallOpenQureka(activity, qurekaorPredChampType);
                if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                    CallOpenQureka(activity, qurekaorPredChampType);
                } else {
                    if (myCallback != null) {
                        myCallback.callbackCall("Account Type 0");
                        myCallback = null;
                        return;
                    }
                }

                return;

            } else if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                isShowDirectDisplyaFb = false;
                if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
            } else if (!isAppIsInBackground(activity)) {

                if (!((Activity) activity).isFinishing()) {
                    try {
                        isShowDirectDisplyaFb = true;
                    } catch (Exception e) {
                        isShowDirectDisplyaFb = false;
                        e.printStackTrace();
                    }

                    if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                        isShowDirectDisplyaFb = false;
                        interstitialAd.show();
                    } else {
                        isShowDirectDisplyaFb = true;
                        AllInterstitialNewAd(activity, adsType, id, accountId, isNextPage, qurekaorPredChampType);
                    }


                } else {
                    isShowDirectDisplyaFb = false;
                  /*  if (myCallback != null) {
                        myCallback.callbackCall("Facebook Display Finish Act ");
                        myCallback = null;
                    }*/

                    //CallOpenQureka(activity, qurekaorPredChampType);
                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        CallOpenQureka(activity, qurekaorPredChampType);
                    } else {
                        if (myCallback != null) {
                            myCallback.callbackCall("Account Type 0");
                            myCallback = null;
                            return;
                        }
                    }
                    return;

                }
            } else {
                isShowDirectDisplyaFb = false;
                /*if (myCallback != null) {
                    myCallback.callbackCall("Facebook Display Ads Fail");
                    myCallback = null;
                }*/

                //CallOpenQureka(activity, qurekaorPredChampType);
                if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                        || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                    CallOpenQureka(activity, qurekaorPredChampType);
                } else {
                    if (myCallback != null) {
                        myCallback.callbackCall("Account Type 0");
                        myCallback = null;
                        return;
                    }
                }
                return;

            }
        } catch (Exception e) {
            isShowDirectDisplyaFb = false;
           /* if (myCallback != null) {
                myCallback.callbackCall("Facebook Display Ads Fail");
                myCallback = null;
            }*/

            //CallOpenQureka(activity, qurekaorPredChampType);
            if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                    || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                    || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                CallOpenQureka(activity, qurekaorPredChampType);
            } else {
                if (myCallback != null) {
                    myCallback.callbackCall("Account Type 0");
                    myCallback = null;
                    return;
                }
            }
            return;

        }


    }

    private void CallGooglleDisplayAdsOrLoad(Activity activity, String adsType, String id, String accountId,
                                             boolean isNextPage, boolean isShowLoader,
                                             String qurekaorPredChampType) {

        Log.d("ads---", "adsType--" + adsType);
        try {
            if (interstitialAdsGoogel == null) {
                isShowDirectDisplya = true;
//                AllInterstitialNewAd(activity, adsType, id, accountId, isNextPage);

                interstitialAdsGoogel = null;
                if (isShowDirectDisplya) {
                    isShowDirectDisplya = false;
                    //CallOpenQureka(activity, qurekaorPredChampType);
                    if (!Utils.isValidatEmpty(AdvertiseUtils.PredChampURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.GameURL)
                            || !Utils.isValidatEmpty(AdvertiseUtils.BitcoinURL)) {
                        CallOpenQureka(activity, qurekaorPredChampType);
                    } else {
                        if (myCallback != null) {
                            myCallback.callbackCall("Account Type 0");
                            myCallback = null;
                            return;
                        }
                    }
                    return;
                }

              /*  if(!isShowDirectDisplya) {
                    isShowDirectDisplya = false;
                    interstitialAdsGoogel = null;
                    Log.e("27/01 interstitialAdsGoogel == null ads--- ", "interstitialAdsGoogel == null ");
                    CallOpenQureka(activity);
                    return;
                }
                else{
                    if (myCallback != null) {
                        Log.e("27/01 interstitialAdsGoogel == null ads--- ", "interstitialAdsGoogel == null ");
                        myCallback.callbackCall("Google Display Finish Actl");
                        myCallback = null;
                        interstitialAdsGoogel = null;
                    }
                }*/

            } else if (interstitialAdsGoogel != null) {
                interstitialAdsGoogel.show(activity);
                Log.d("ads--- ", "interstitialAdsGoogel display  ");
                isShowDirectDisplya = false;
                interstitialAdsGoogel.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        interstitialAdsGoogel = null;
                        if (myCallback != null) {
                            myCallback.callbackCall("Google Display Finish Actl");
                            myCallback = null;
                        }

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.d("ads -- ", adError.getMessage() + " ads ");
                        interstitialAdsGoogel = null;
                        if (myCallback != null) {
                            myCallback.callbackCall("Google Display Finish Actl");
                            myCallback = null;
                        }

                    }

                    @Override
                    public void onAdShowedFullScreenContent() {

                    }
                });

            }

        } catch (Exception e) {
            Log.d("ads - ", "Catech - " + e.getMessage());
            isShowDirectDisplya = false;

          /*  CallOpenQureka(activity);
            return;*/
            interstitialAdsGoogel = null;
            if (myCallback != null) {
                myCallback.callbackCall("Google Display Ads Fail");
                myCallback = null;
            }

        }
    }

    public boolean isAppIsInBackground(Context context) {
        try {
            boolean isInBackground = true;
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT > 20) {
                for (ActivityManager.RunningAppProcessInfo processInfo : am.getRunningAppProcesses()) {
                    if (processInfo.importance == 100) {
                        for (String activeProcess : processInfo.pkgList) {
                            if (activeProcess.equals(context.getPackageName())) {
                                isInBackground = false;
                            }
                        }
                    }
                }
                return isInBackground;
            } else if (((ActivityManager.RunningTaskInfo) am.getRunningTasks(1).get(0)).topActivity.getPackageName().equals(context.getPackageName())) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }


}
