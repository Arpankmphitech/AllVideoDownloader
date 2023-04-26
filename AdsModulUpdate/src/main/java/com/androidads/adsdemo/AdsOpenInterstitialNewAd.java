package com.androidads.adsdemo;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;
import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.OnLifecycleEvent;

import com.androidads.adsdemo.common.AdvertiseUtils;
import com.androidads.adsdemo.common.Utils;
import com.androidads.adsdemo.model.QurekaandPreadChampAdvertiseModel;
import com.jgabrielfreitas.core.BlurImageView;

import okhttp3.internal.Util;
import pl.droidsonroids.gif.GifImageView;


/**
 * Created by Nitull Patel on 10/17/2018.
 */

public class AdsOpenInterstitialNewAd {
    private static final String TAG = "AdsOpenInterstitialNewA";

    private static AdsOpenInterstitialNewAd mInstance;
    private MyCallback myCallback;

    public interface MyCallback {
        void callbackCall(String str);
    }

    public static AdsOpenInterstitialNewAd getInstance() {
        if (mInstance == null) {
            mInstance = new AdsOpenInterstitialNewAd();
        }
        return mInstance;
    }

    public void CallOpenAdsOpenQureka(final Activity activity, final String qurekaorPredChampType,
                                      MyCallback _myCallback) {

        myCallback = _myCallback;

        TextView tvHeader, tital1, tital2, tvBottomText;
        ImageView imgBanner;
        BlurImageView imgBlurBanner;
        final Button btnPlayNow;
        LinearLayout llMain;
        GifImageView gifView;
        Log.d("09/12", " - 11");
        final Dialog[] dialog = {new Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen)};
        dialog[0].getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        Log.d("09/12", " - 121");


        if (AdvertiseUtils.isValidationEmptyWithZero(qurekaorPredChampType)) {
            dialog[0].setContentView(R.layout.custome_interad_open_ad);
        } else {
            dialog[0].setContentView(R.layout.custome_interad_open_ad);
        }

        dialog[0].setCancelable(false);
        dialog[0].setCanceledOnTouchOutside(false);

        tvHeader = dialog[0].findViewById(R.id.tvHeader);
        tvBottomText = dialog[0].findViewById(R.id.tvBottomText);

        tvHeader.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));

        tital1 = dialog[0].findViewById(R.id.tital1);
        tital1.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));

        tital2 = dialog[0].findViewById(R.id.tital2);
        tital2.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));


        tvBottomText.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top1));

//        int bannerRandomImage = 0;
        if (AdvertiseUtils.isValidationEmptyWithZero(qurekaorPredChampType)) {
            //bansi changes here
//            bannerRandomImage = AdvertiseUtils.getSmartBannerModelRandom();
        } else {
//            bannerRandomImage = AdvertiseUtils.getInterstialQurekaBannerImage();
        }

        imgBlurBanner = dialog[0].findViewById(R.id.imgBlurBanner);
        imgBlurBanner.setBlur(20);

        imgBanner = dialog[0].findViewById(R.id.imgBanner);
        imgBanner.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top2));

        llMain = dialog[0].findViewById(R.id.llMain);
        gifView = dialog[0].findViewById(R.id.gifView);

        //bansi changes here

        QurekaandPreadChampAdvertiseModel qurekaandPreadChampAdvertiseModel = null;

        if (AdvertiseUtils.isValidationEmptyWithZero(qurekaorPredChampType)) {
            qurekaandPreadChampAdvertiseModel = AdvertiseUtils.getPredchampHeaderTitleDescriptionList(activity);
            tvHeader.setText(qurekaandPreadChampAdvertiseModel.getHeader());
            tital1.setText(qurekaandPreadChampAdvertiseModel.getTitle());
            tvBottomText.setText(qurekaandPreadChampAdvertiseModel.getBody());
            gifView.setImageResource(qurekaandPreadChampAdvertiseModel.getGifView());
            imgBlurBanner.setImageResource(qurekaandPreadChampAdvertiseModel.getBanner());
            imgBanner.setImageResource(qurekaandPreadChampAdvertiseModel.getBanner());

        } else {

        }

        btnPlayNow = dialog[0].findViewById(R.id.btnPlayNow);
        btnPlayNow.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.move_bottom_to_top3));
        btnPlayNow.setText(AdvertiseUtils.getInterstialButtonText(activity));

        final QurekaandPreadChampAdvertiseModel finalQurekaandPreadChampAdvertiseModel = qurekaandPreadChampAdvertiseModel;
        btnPlayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog[0] != null && dialog[0].isShowing()) {
                    dialog[0].dismiss();
                }
                dialog[0] = null;
                if (myCallback != null) {
                    myCallback.callbackCall("Success close Qureka");
                    myCallback = null;
                }
                try {

                    if (!AdvertiseUtils.isValidationEmptyWithZero(finalQurekaandPreadChampAdvertiseModel.getUrl())) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(finalQurekaandPreadChampAdvertiseModel.getUrl()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(activity, "No application that can handle this link found", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        //Bansi Changes Here
                        //Toast.makeText(activity, "No ads to show.", Toast.LENGTH_LONG).show();

                    }

                } catch (ActivityNotFoundException e) {
                    //Toast.makeText(activity, "No ads to show.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlayNow.performClick();
            }
        });

        dialog[0].findViewById(R.id.llClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog[0] != null && dialog[0].isShowing()) {
                    dialog[0].dismiss();
                }
                dialog[0] = null;
                if (myCallback != null) {
                    myCallback.callbackCall("Success close Qureka");
                    myCallback = null;
                }
            }
        });

       /* dialog[0].setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (myCallback != null) {
                    myCallback.callbackCall("Success close Qureka");
                    myCallback = null;
                }
            }
        });*/
        Log.d("09/12", " - 33");

        dialog[0].show();

    }

    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
        Log.d("9451", "Start");
    }

}
