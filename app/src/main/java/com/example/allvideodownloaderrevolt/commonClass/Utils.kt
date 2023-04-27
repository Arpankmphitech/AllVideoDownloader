package com.example.allvideodownloaderrevolt.commonClass

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.LinearGradient
import android.graphics.Shader
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.androidads.adsdemo.AllInterstitialNewAd
import com.androidads.adsdemo.AllNativeAdsFirstLoadDisplayAds
import com.androidads.adsdemo.common.Utils.isValidatEmpty
import com.androidads.adsdemo.model.SplashCommonModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.DownloadListener
import com.example.allvideodownloaderrevolt.R
import com.facebook.ads.NativeAdLayout
import com.google.gson.Gson
import java.net.URL
import java.util.*

object Utils {


    fun isNetworkAvailable(
        splashScreenActivity: Activity, canShowErrorDialogOnFail: Boolean, isFinish: Boolean
    ): Boolean {

        var isNetAvailable = false
        if (splashScreenActivity != null) {
            val mConnectivityManager =
                splashScreenActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (mConnectivityManager != null) {
                var mobileNetwork = false
                var wifiNetwork = false
                var mobileNetworkConnecetd = false
                var wifiNetworkConnecetd = false
                val mobileInfo =
                    mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                val wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (mobileInfo != null) {
                    mobileNetwork = mobileInfo.isAvailable
                }
                if (wifiInfo != null) {
                    wifiNetwork = wifiInfo.isAvailable
                }
                if (wifiNetwork || mobileNetwork) {
                    if (mobileInfo != null) mobileNetworkConnecetd =
                        mobileInfo.isConnectedOrConnecting
                    wifiNetworkConnecetd = wifiInfo!!.isConnectedOrConnecting
                }
                isNetAvailable = mobileNetworkConnecetd || wifiNetworkConnecetd
            }
            splashScreenActivity.setTheme(R.style.Theme_AllVideoDownloaderRevolt)
            if (!isNetAvailable && canShowErrorDialogOnFail) {
                if (splashScreenActivity is Activity) {
                    splashScreenActivity.runOnUiThread {
                        showAlertWithFinis(
                            splashScreenActivity,
                            splashScreenActivity.getString(R.string.app_name),
                            splashScreenActivity.getString(R.string.network_alert),
                            isFinish
                        )
                    }
                }
            }
        }
        return isNetAvailable
    }


    fun showAlertWithFinis(
        activity: Activity, title: String, message: String, isFinish: Boolean
    ) {
        if (activity != null && !activity.isFinishing) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setCancelable(false)
            builder.setPositiveButton(activity.getString(R.string.ok)) { dialog: DialogInterface?, _: Int ->
                if (dialog != null) {
                    if (isFinish) {
                        if (activity != null && !activity.isFinishing) {
                            if (dialog != null) {
                                dialog.dismiss()
                                activity.finish()
                            }
                        }
                    } else {
                        if (activity != null && !activity.isFinishing) {
                            if (dialog != null) {
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }.show()
        }

    }

    fun isValidationEmpty(value: String?): Boolean {
        return value == null || value.equals(
            "",
            ignoreCase = true
        ) || value.length == 0 || value.equals("null", ignoreCase = true)

    }


    fun isValidationUrl(value: String?): Boolean {
        return try {
            if (isValidatEmpty(value)) {
                false
            } else {
                URL(value).toURI()
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    fun isValidaEmptyWithZero(value: String?): Boolean {
        return (value == null || value.isEmpty() || value.equals(
            "null",
            ignoreCase = true
        ) || value.equals("", ignoreCase = true) || value.length == 0 || value.equals(
            "0",
            ignoreCase = true
        ))
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarSkyGradientActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            val background = activity.resources.getDrawable(R.drawable.ic_sky_gradient_bg)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = activity.resources.getColor(android.R.color.transparent)
            window.navigationBarColor = activity.resources.getColor(android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }
    }

    var progressbarDialog: ProgressDialog? = null

    fun ShowProgressbarDialog(activity: Activity, message: String) {
        try {
            if (progressbarDialog != null && progressbarDialog!!.isShowing) {
                progressbarDialog!!.dismiss()
                progressbarDialog = null
            }
            progressbarDialog = null
            if (activity != null && !activity.isFinishing) {
                progressbarDialog = ProgressDialog(activity)
                progressbarDialog!!.setMessage(message)
                progressbarDialog!!.isIndeterminate = false
                progressbarDialog!!.setCancelable(false)
                progressbarDialog!!.show()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun newDownload(url: String, activity: Context) {
        val uniqueString = UUID.randomUUID().toString()
        AndroidNetworking.download(
            url, Constant.TWITTER_PATH.path, "$uniqueString.mp4"
        ).setTag("downloadTest").setPriority(Priority.MEDIUM).build()
            .setDownloadProgressListener { _, _ -> }.startDownload(object : DownloadListener {
                override fun onDownloadComplete() {
                    HideProgressbarDialog()
                    Toast.makeText(activity, "Download complete", Toast.LENGTH_SHORT).show()
                }

                override fun onError(anError: ANError) {
                    HideProgressbarDialog()
                    Toast.makeText(
                        activity,
                        activity.resources.getString(R.string.valid_url),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun isValidatEmpty(value: String): Boolean {
        return value == null || value.isEmpty() || value.equals(
            "null", ignoreCase = true
        ) || value.equals("", ignoreCase = true) || value.length == 0
    }

    fun HideProgressbarDialog() {
        if (progressbarDialog != null && progressbarDialog!!.isShowing) {
            progressbarDialog!!.dismiss()
        }
        progressbarDialog = null
    }

    fun hideKeyBoard(mActivity: Activity?) {
        if (mActivity != null && !mActivity.isFinishing) {
            val imm =
                mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = mActivity.currentFocus
            if (view == null) {
                view = View(mActivity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun loadInter(activity: Activity) {
        val interstitialAdsId: SplashCommonModel = AdsIdOnDeviceStore.GetInterstitial(activity)
        AllInterstitialNewAd.getInstance().AllInterstitialNewAd(
            activity,
            interstitialAdsId.accountType,
            interstitialAdsId.id,
            interstitialAdsId.accountNo,
            true,
            Constant.TypePredChampInside
        )
    }

    fun loadSmallNative(activity: Activity, pos: Int) {
        Log.d("05/10", "LoadSmallNative -$pos")

//        loadMediumNativeAds(
//            Constant.SmallNative,
//            Constant.TypePredChampInside,
//            activity!!,
//            activity.findViewById<View>(R.id.allMainSelection_medium_bottom)
//                .findViewById(R.id.nativeAdContainer),
//            activity.findViewById<View>(R.id.allMainSelection_medium_bottom)
//                .findViewById(R.id.frameLayoutPlaceholder),
//            pos,
//            activity.findViewById<View>(R.id.allMainSelection_medium_bottom)
//                .findViewById(R.id.layoutBigPlaceHolder),
//            activity.findViewById<View>(R.id.allMainSelection_medium_bottom)
//                .findViewById(R.id.layoutSmallPlaceHolder)
//        )
    }

    private fun loadMediumNativeAds(
        smallNative: Int,
        predChampInsideType: String?,
        activity: Activity,
        FbNativeAdLayout_medium: NativeAdLayout?,
        GoogleNativeAdLayout_medium: FrameLayout?,
        pos: Int,
        llBigView: LinearLayout?,
        llSmallView: LinearLayout?
    ) {
        val nativeAdsId: SplashCommonModel = AdsIdOnDeviceStore.GetNativeAds(
            smallNative, activity, pos
        )
        val view: View = activity.findViewById(R.id.allMainSelection_medium_bottom)
        view.visibility = View.VISIBLE
        Log.d("19/12", "medium--->" + Gson().toJson(nativeAdsId))

        AllNativeAdsFirstLoadDisplayAds.getInstance().AllNatviAdsLodRequest(
            activity,
            nativeAdsId.accountType,
            nativeAdsId.id,
            object : AllNativeAdsFirstLoadDisplayAds.MyCallback {
                override fun callbackCallSuccess(Success: String) {
                    Log.e("Ads Exit Dlg==>", Success + "")
                    Log.d("19/12", "callbackCallSuccess----1---$Success")
//                    if (Success == "Success load qureka") {
//                    } else {
                    llBigView!!.visibility = View.GONE
                    llSmallView!!.visibility = View.GONE
//                    }
                    view.visibility = View.VISIBLE
//                    showMediumNativeAds(
//                        Constant.SmallNative,
//                        Constant.TypePredChampInside,
//                        activity,
//                        activity.findViewById(R.id.allMainSelection_medium_bottom),
//                        activity.findViewById<View>(R.id.allMainSelection_medium_bottom)
//                            .findViewById(R.id.nativeAdContainer),
//                        activity.findViewById<View>(R.id.allMainSelection_medium_bottom)
//                            .findViewById(R.id.frameLayoutPlaceholder),
//                        2
//                    )
                }

                override fun callbackCallFail(Failed: String) {

//                    if (Failed == "Failed load qureka"){
//                        Log.e("1010 Dlg=if=>", Failed + "")
//                        llSmallView!!.visibility = View.VISIBLE
//                    } else {
                    Log.e("1010 Dlg=else=>", Failed + "")
                    llBigView!!.visibility = View.GONE
                    llSmallView!!.visibility = View.GONE
                    view.visibility = View.GONE
//                    }

                    Log.d("19/12", "callbackCallFail----$Failed")
                    Log.e("1010 Exit Dlg==>", Failed + "")
                }

                override fun callbackCallClose() {
                    llBigView!!.visibility = View.GONE
                    llSmallView!!.visibility = View.GONE
                    view.visibility = View.GONE
                    Log.d("19/12", "callbackCallSuccess----Close")
                    Log.e(" 1010 Exit Dlg ==>", "Close fb Yes")
                }
            },
            smallNative,
            FbNativeAdLayout_medium,
            GoogleNativeAdLayout_medium,
            predChampInsideType,
            pos,
            llBigView,
            llSmallView
        )
    }

    fun showMediumNativeAds(
        smallNative: Int,
        predChampInsideType: String?,
        activity: Activity,
        llMainSelection_medium: View,
        FbNativeAdLayout_medium: NativeAdLayout?,
        GoogleNativeAdLayout_medium: FrameLayout?,
        pos: Int
    ) {
        Log.d("06/10", "called--$pos")
        val nativeAdsId: SplashCommonModel = AdsIdOnDeviceStore.GetNativeAds(
            smallNative, activity, pos
        )
        AllNativeAdsFirstLoadDisplayAds.getInstance().AllNativAdsDisplayThisAds(
            activity,
            nativeAdsId.accountType,
            smallNative,
            object : AllNativeAdsFirstLoadDisplayAds.MyCallback {
                override fun callbackCallSuccess(Success: String) {
                    Log.e("Display Nativ Ads==>", "$Success $pos")
                    llMainSelection_medium.visibility = View.VISIBLE
                }

                override fun callbackCallFail(Failed: String) {
//                    if (Failed== "Failed load qureka"){

//                    }else {
                    llMainSelection_medium.visibility = View.GONE
//                    }
                    Log.e("1010 Nativ Ads==>", Failed + "")
                }

                override fun callbackCallClose() {
                    llMainSelection_medium.visibility = View.GONE
                    Log.e("1010 ==>", "Close fb Yes")
                }
            },
            FbNativeAdLayout_medium,
            GoogleNativeAdLayout_medium,
            predChampInsideType,
            pos
        )
    }


    fun displayInterOnBack(activity: Activity) {

        if (Constant.countBack == Constant.isAdBackCount) {
            Constant.countBack = 1
            val interstitialAdsId: SplashCommonModel = AdsIdOnDeviceStore.GetInterstitial(activity)
            AllInterstitialNewAd.getInstance().displayInterstitial(
                activity,
                interstitialAdsId.accountType,
                interstitialAdsId.id,
                interstitialAdsId.accountNo,
                AllInterstitialNewAd.MyCallback {
                    activity.finish()
                },
                true,
                true,
                Constant.TypePredChampInside
            )
        } else {
            Constant.countBack++
            activity.finish()
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarBlueGradientActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            val background = activity.resources.getDrawable(R.drawable.ic_blue_with_gradient_bg)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = activity.resources.getColor(android.R.color.transparent)
            window.navigationBarColor = activity.resources.getColor(android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }
    }

    fun displayInter(
        activity: Activity, myCallback: AllInterstitialNewAd.MyCallback, isCount: Boolean
    ) {
        val interstitialAdsId: SplashCommonModel = AdsIdOnDeviceStore.GetInterstitial(activity)
        AllInterstitialNewAd.getInstance().displayInterstitial(
            activity,
            interstitialAdsId.accountType,
            interstitialAdsId.id,
            interstitialAdsId.accountNo,
            myCallback,
            true,
            true,
            Constant.TypePredChampInside
        )
    }

    fun loadBigNative(activity: Activity) {
//        loadBigNativeAd(
//            Constant.BigNative,
//            Constant.TypePredChampInside,
//            activity,
//            activity.findViewById<View>(R.id.allMainSelectionInclude).findViewById(R.id.nativeAdContainer),
//            activity.findViewById<View>(R.id.allMainSelectionInclude).findViewById(R.id.frameLayoutPlaceholder),
//            activity.findViewById(R.id.allMainSelectionInclude),
//            activity.findViewById<View>(R.id.allMainSelectionInclude).findViewById(R.id.layoutBigPlaceHolder),
//            activity.findViewById<View>(R.id.allMainSelectionInclude).findViewById(R.id.layoutSmallPlaceHolder)
//
//        )

    }

    fun appAlreadyInstalled(activity: Context?, name: String): Boolean {
        var available = true
        Log.d("15/10", "" + name)
        if (isValidationEmpty(
                name
            )
        ) {
            available = false
        } else {
            try {
                // check if available
                activity!!.packageManager.getPackageInfo(name.trim(), 0)
            } catch (e: PackageManager.NameNotFoundException) {
                Log.d("15/10", "" + e)
                // available as false
                available = false
            }
        }
        Log.d("15/10", "" + available)
        return available
    }

    fun gradientTextViewColor(color: Int, color1: Int, btnRoom: TextView, strName: String) {
        val paint = btnRoom.paint
        val width = paint.measureText(strName)
        val textShader: Shader = LinearGradient(
            0f,
            0f,
            width,
            btnRoom.textSize,
            intArrayOf(color, color, color1),
            null,
            Shader.TileMode.CLAMP
        )
        btnRoom.paint.shader = textShader

    }

    @SuppressLint("WrongConstant")
    fun openUrl(activity: Activity, url: String) {

        if (url == null) return
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse(url)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.let {
            val activityInfo = intent.resolveActivityInfo(it.packageManager, intent.flags)
            if (activityInfo != null && activityInfo.exported) {
                ActivityCompat.startActivity(it, intent, null)
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarSkyBlueGradientActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            val background = activity.resources.getDrawable(R.drawable.sky_gradient_bg_135)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = activity.resources.getColor(android.R.color.transparent)
            window.navigationBarColor = activity.resources.getColor(android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }
    }

    fun setStatusBarBlackColorActivity(activity: Activity) {

    }

    fun setDataFromSharedPrefrences(
        loginTableName: String,
        mode: Int,
        email: String,
        password: String,
        activity: Activity
    ) {
        val sharedPreferences = activity.getSharedPreferences(loginTableName, mode)
        val myEdit = sharedPreferences.edit()

        myEdit.putString("email", email)
        myEdit.putString("password", password)
        myEdit.apply()

    }


}