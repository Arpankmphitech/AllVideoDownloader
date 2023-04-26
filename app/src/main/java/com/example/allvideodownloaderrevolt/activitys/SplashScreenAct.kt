package com.example.allvideodownloaderrevolt.activitys

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import com.androidads.adsdemo.AdsOpenInterstitialNewAd
import com.androidads.adsdemo.AllInterstitialNewAd
import com.androidads.adsdemo.common.AdvertiseUtils
import com.androidads.adsdemo.common.AppOpenManager
import com.androidads.adsdemo.model.AdPriority
import com.androidads.adsdemo.model.F
import com.androidads.adsdemo.model.G
import com.androidads.adsdemo.model.SplashCommonModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.BuildConfig
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.allvideodownloaderrevolt.AllVideoDownloaderApplicationClass
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.common.AdsIdOnDeviceStore
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.SharedPreferences
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActSplashScreenBinding
import com.example.allvideodownloaderrevolt.models.adsmoduls.SubAppOpenManager
import com.example.allvideodownloaderrevolt.security.Crypto
import com.google.gson.Gson
import com.video.downloader.free.allvideo.media.download.social.videos.hd.models.adsmodule.AdsResponseModel
import com.video.downloader.free.allvideo.media.download.social.videos.hd.models.adsmodule.DataModel
import com.video.downloader.free.allvideo.media.download.social.videos.hd.models.adsmodule.PredchampAppOpenManager
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashScreenAct : AppCompatActivity() {

    lateinit var binding: ActSplashScreenBinding
    lateinit var splashScreenActivity: Activity
    private val splashTimeOut = 6000
    var strSplashAdsAvailable: String? = null
    var strAdPriority = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActSplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mWindow = window
        mWindow.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        splashScreenActivity = this@SplashScreenAct

        initView()

    }

    private fun initView() {

        splashScreenActivity.runOnUiThread {
            if (Utils.isNetworkAvailable(
                    splashScreenActivity, canShowErrorDialogOnFail = true, isFinish = true
                )
            ) {
                splashScreenAPI()
            } else {
                Utils.showAlertWithFinis(
                    splashScreenActivity,
                    splashScreenActivity.getString(R.string.app_name),
                    splashScreenActivity.getString(R.string.network_alert),
                    true
                )
            }
        }

        printHashKey(applicationContext)

    }

    private fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.packageManager.getPackageInfo(
                pContext.packageName, PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey: String = String(Base64.encode(md.digest(), 0))
                Log.i("0101", "printHashKey() Hash Key: $hashKey")
            }

        } catch (e: NoSuchAlgorithmException) {
            Log.e("0101", "printHashKey()", e)
        } catch (e: java.lang.Exception) {
            Log.e("0101", "printHashKey()", e)
        }
    }


    private fun splashScreenAPI() {

        AndroidNetworking.get(Crypto.decrypt(Constant.moreAppApi, Constant.encryptionKey))
            .setTag("test").doNotCacheResponse().setPriority(Priority.IMMEDIATE).build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {


                    if (response != null && Utils.isValidationEmpty(response.toString())) {
                        Constant.isNotificationClicked = false
                        val dataObject = response.optJSONObject("data")
                        val dataModel = DataModel()
                        if (dataObject != null) {
                            dataModel.developerName = (dataObject.optString("developer_name"))
                            dataModel.isUserDialog = (dataObject.optBoolean("is_user_dialog"))
                            dataModel.userDialogText = (dataObject.optString("user_dialog_text"))
                            dataModel.userDialogUrl = (dataObject.optString("user_dialog_url"))
                            dataModel.appVersionCode = (dataObject.optString("app_version_code"))
                            dataModel.userDialogUpdateText =
                                (dataObject.optString("user_dialog_update_text"))
                            dataModel.bitcoinUrl = (dataObject.optString("bitcoin_url"))
                            dataModel.predchampUrl = (dataObject.optString("predchamp_url"))
                            dataModel.gameUrl = (dataObject.optString("game_url"))
                            dataModel.isBackPressCount =
                                (dataObject.optString("is_back_press_count"))
                            dataModel.isNormalAdCount = (dataObject.optString("is_normal_ad_count"))
                            dataModel.isSplashAd = (dataObject.optString("is_splash_ad"))
                            dataModel.isAdvertiseAvailable =
                                (dataObject.optString("is_advertise_available"))
                            dataModel.usrDialogBtn = (dataObject.optString("usr_dialog_btn"))
                            dataModel.userDialogImg = (dataObject.optString("user_dialog_img"))
                        }

                        try {
                            if (dataModel.developerName.equals("")) {
                                Constant.DEVELOPER_NAME = dataModel.developerName.toString()
                            }
                        } catch (e: Exception) {
                            println(e.message)
                        }
                        Log.d("19/12", "" + dataModel.isUserDialog)
                        SharedPreferences.setBoolenValue(
                            Constant.IS_USER_DIALOG, dataModel.isUserDialog!!
                        )
                        SharedPreferences.setStringName(
                            Constant.App_VERSION_CODE, dataModel.appVersionCode
                        )
                        SharedPreferences.setStringName(
                            Constant.USER_DIALOG_UPDATE_TEXT, dataModel.userDialogUpdateText
                        )
                        SharedPreferences.setStringName(
                            Constant.USER_DIALOG_URL, dataModel.userDialogUrl
                        )
                        SharedPreferences.setStringName(
                            Constant.USER_DIALOG_IMG, dataModel.userDialogImg
                        )

                        SharedPreferences.setStringName(
                            Constant.BACK_PRESS_COUNT, dataModel.isBackPressCount
                        )
                        SharedPreferences.setStringName(
                            Constant.NORMAL_AD_COUNT, dataModel.isNormalAdCount
                        )
                        SharedPreferences.setStringName(
                            Constant.USER_DIALOG_TEXT, dataModel.userDialogText
                        )
                        if (Utils.isValidationEmpty(dataModel.predchampUrl!!) && Utils.isValidationUrl(
                                dataModel.predchampUrl
                            )
                        ) {
                            AdvertiseUtils.PredChampURL = dataModel.predchampUrl
                        }

                        if (!Utils.isValidationEmpty(dataModel.bitcoinUrl!!) && Utils.isValidationUrl(
                                dataModel.bitcoinUrl
                            )
                        ) {
                            AdvertiseUtils.BitcoinURL = dataModel.bitcoinUrl
                        }

                        if (!Utils.isValidationEmpty(dataModel.gameUrl!!) && Utils.isValidationUrl(
                                dataModel.gameUrl
                            )
                        ) {
                            AdvertiseUtils.GameURL = dataModel.gameUrl
                        }

                        SharedPreferences.setStringName(
                            Constant.is_advertise_available, dataModel.isAdvertiseAvailable
                        )
                        SharedPreferences.setStringName(
                            Constant.IS_SPLASH_AD, dataModel.isSplashAd
                        )

                        if (SharedPreferences.getStringName(Constant.IS_SPLASH_AD)
                                ?.let { Utils.isValidationEmpty(it) }!!
                        ) {
                            strSplashAdsAvailable =
                                SharedPreferences.getStringName(Constant.IS_SPLASH_AD)
                        }
                        Constant.isNormalAdCount =
                            SharedPreferences.getStringName(Constant.NORMAL_AD_COUNT)!!.toInt()
                        Constant.isBackAdCount =
                            SharedPreferences.getStringName(Constant.BACK_PRESS_COUNT)!!.toInt()


                        //For Ads Priority...
                        val adObject = response.optJSONObject("ad_priority")
                        val adPriority = AdPriority()
                        adPriority.priority = (adObject!!.optString("priority"))

                        //For Facebook Ads...
                        val fobject = adObject.optJSONObject("F")
                        val fModel = F()
                        if (fobject != null) {
                            fModel.snb = (fobject.optString("small_native_banner_ad"))
                            fModel.i = (fobject.optString("interstitial_ad"))
                            fModel.n = (fobject.optString("native_ad"))
                            fModel.n2 = (fobject.optString("native_ad2"))
                            fModel.nb = (fobject.optString("native_banner_ad"))
                            fModel.nb2 = (fobject.optString("native_banner_ad2"))
                        }

                        //For Google Ads...
                        val gobject = adObject.optJSONObject("G")
                        val gModel = G()
                        gModel.i = (gobject!!.optString("interstitial_ad"))
                        gModel.n = (gobject.optString("native_ad"))
                        gModel.n2 = (gobject.optString("native_ad2"))
                        gModel.nb = (gobject.optString("native_banner_ad"))
                        gModel.nb2 = (gobject.optString("native_banner_ad2"))
                        gModel.ao = (gobject.optString("app_open_ad"))
                        gModel.b = (gobject.optString("banner_ad"))
                        adPriority.f = (fModel)
                        adPriority.g = (gModel)


                        //For Main Object Data...
                        val adsResponseModel = AdsResponseModel()
                        adsResponseModel.dataModel = (dataModel)
                        adsResponseModel.adPriority = (adPriority)
                        adsResponseModel.responseCode = (response.optInt("ResponseCode"))
                        adsResponseModel.responseMsg = (response.optString("ResponseMsg"))
                        adsResponseModel.result = (response.optString("Result"))
                        adsResponseModel.serverTime = (response.optString("ServerTime"))
                        if (BuildConfig.DEBUG) {
                            SharedPreferences.setStringName(Constant.NORMAL_AD_COUNT, "1")
                            SharedPreferences.setStringName(
                                Constant.is_advertise_available, "1"
                            )
                            SharedPreferences.setStringName(Constant.IS_SPLASH_AD, "0")
                            dataModel.isUserDialog = (true)
                            dataModel.appVersionCode = ("1")
                            dataModel.isAdvertiseAvailable = ("1")
                            adPriority.priority = ("G")
                        }
                        if (adObject != null && !Utils.isValidationEmpty(adPriority.priority)) {
                            SharedPreferences.setAdvertiseModel(adPriority)
                            Log.d("02/06", "" + Gson().toJson(adPriority))
                            Log.d("02/06", "" + SharedPreferences.setAdvertiseModel(adPriority))
                            Log.d(
                                "02/06", "" + Gson().toJson(SharedPreferences.advertiseModel)
                            )
                            strAdPriority = SharedPreferences.advertiseModel?.priority.toString()
                            Log.d("68485", " - strAdPriority --> $strAdPriority")
                            Log.d("68485", " - strSplashAdsAvailable --> $strSplashAdsAvailable")
                            if (gobject != null && !Utils.isValidationEmpty(gModel.ao)) {
                                Constant.AppOpenAdId = gModel.ao.toString()
                            }
                            if (adPriority.priority != null && strSplashAdsAvailable.equals(
                                    "1", ignoreCase = true
                                )
                            ) {
                                if ((Utils.isValidationEmpty(Constant.AppOpenAdId.toString()) && Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) || (Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        ).toString()
                                    ) && SharedPreferences.getStringName(Constant.is_advertise_available)
                                        .equals("1"))) && !Utils.isValidationEmpty(strAdPriority) && strAdPriority.equals(
                                        "G", ignoreCase = true
                                    )
                                ) {
                                    Log.d("68485", " - G IN")
                                    val manager =
                                        AppOpenManager(AllVideoDownloaderApplicationClass.application!!)
                                    if (!Utils.isValidationEmpty(
                                            SharedPreferences.getStringName(
                                                Constant.IS_SPLASH_AD
                                            )!!
                                        ) && SharedPreferences.getStringName(Constant.IS_SPLASH_AD)
                                            .equals("1")
                                    ) {

                                        manager.fetchSplashAd(
                                            splashScreenActivity,
                                            object : AppOpenManager.onNext {
                                                override fun nextActionPerform() {
                                                    screenTransaction()
                                                }
                                            })
                                    } else {
                                        screenTransaction()
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_advertise_available)
                                        .equals("1"))) && !Utils.isValidationEmpty(strAdPriority) && strAdPriority.equals(
                                        "G,F", ignoreCase = true
                                    )
                                ) {
                                    Log.d("68485", " - G F IN")
                                    val manager =
                                        AppOpenManager(AllVideoDownloaderApplicationClass.application!!)
                                    if (!Utils.isValidationEmpty(
                                            SharedPreferences.getStringName(
                                                Constant.IS_SPLASH_AD
                                            )!!
                                        ) && SharedPreferences.getStringName(Constant.IS_SPLASH_AD)
                                            .equals("1")
                                    ) {
                                        manager.fetchSplashAd(
                                            splashScreenActivity,
                                            object : AppOpenManager.onNext {
                                                override fun nextActionPerform() {
                                                    screenTransaction()
                                                }
                                            })
                                    } else {
                                        screenTransaction()
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_advertise_available)
                                        .equals("1"))) && !Utils.isValidationEmpty(strAdPriority) && strAdPriority.equals(
                                        "F,G", ignoreCase = true
                                    )
                                ) {
                                    Log.d("68485", " - F G IN")
                                    val IntAdsId: SplashCommonModel =
                                        AdsIdOnDeviceStore.GetInterstitial(splashScreenActivity)
                                    if (IntAdsId.getAccountType() != null && IntAdsId.getId() != null && !IntAdsId.getId()
                                            .equals("")
                                    ) {
                                        AllInterstitialNewAd.getInstance().AllInterstitialNewAd(
                                            this@SplashScreenAct,
                                            IntAdsId.getAccountType(),
                                            IntAdsId.getId(),
                                            IntAdsId.getAccountNo(),
                                            true,
                                            Constant.PredChampInsideType,
                                            { screenTransaction() },
                                            AllVideoDownloaderApplicationClass.application
                                        )
                                    } else {
                                        screenTransaction()
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_advertise_available)
                                        .equals("1"))) && !Utils.isValidationEmpty(strAdPriority) && strAdPriority.equals(
                                        "F", ignoreCase = true
                                    )
                                ) {
                                    Log.d("68485", " - F IN")
                                    val IntAdsId: SplashCommonModel =
                                        AdsIdOnDeviceStore.GetInterstitial(splashScreenActivity)
                                    if (IntAdsId.accountType != null && IntAdsId.id != null && !IntAdsId.getId()
                                            .equals("")
                                    ) {
                                        AllInterstitialNewAd.getInstance().AllInterstitialNewAd(
                                            this@SplashScreenAct,
                                            IntAdsId.accountType,
                                            IntAdsId.id,
                                            IntAdsId.accountNo,
                                            true,
                                            Constant.PredChampInsideType,
                                            { screenTransaction() },
                                            null
                                        )
                                    } else {
                                        screenTransaction()
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_advertise_available)
                                        .equals("2"))) && !Utils.isValidationEmpty(strAdPriority)
                                ) {
                                    Log.d("68485", " - 2")
                                    if (strAdPriority.equals(
                                            "G,F", ignoreCase = true
                                        ) || strAdPriority.equals("G", ignoreCase = true)
                                    ) {
                                        Log.d("68485", " - G-G,F")
                                        if (!Utils.isValidationEmpty(AdvertiseUtils.PredChampURL) || !Utils.isValidationEmpty(
                                                AdvertiseUtils.GameURL
                                            ) || !Utils.isValidationEmpty(AdvertiseUtils.BitcoinURL)
                                        ) {
                                            Constant.AppOpenAdId = "ABCD"
                                            val adsOpenInterstitialNewAd =
                                                AdsOpenInterstitialNewAd()
                                            adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(
                                                splashScreenActivity, Constant.PredChampInsideType
                                            ) { str: String? ->
                                                AllVideoDownloaderApplicationClass.application?.let {
                                                    PredchampAppOpenManager(
                                                        it
                                                    )
                                                }
                                                screenTransaction()
                                            }
                                        } else {
                                            screenTransaction()
                                        }
                                    } else if (strAdPriority.equals("F,G", ignoreCase = true)) {
                                        Log.d("68485", " - F,G")
                                        if (!Utils.isValidationEmpty(AdvertiseUtils.PredChampURL) || !Utils.isValidationEmpty(
                                                AdvertiseUtils.GameURL
                                            ) || !Utils.isValidationEmpty(AdvertiseUtils.BitcoinURL)
                                        ) {
                                            Constant.AppOpenAdId = "ABCD"
                                            AllInterstitialNewAd.CallOpenQureka(
                                                splashScreenActivity, Constant.PredChampInsideType
                                            ) {
                                                AllVideoDownloaderApplicationClass.application?.let { it1 ->
                                                    PredchampAppOpenManager(
                                                        it1
                                                    )
                                                }
                                                screenTransaction()
                                            }
                                        } else {
                                            screenTransaction()
                                        }
                                    } else if (strAdPriority.equals("F", ignoreCase = true)) {
                                        Log.d("68485", " - F")
                                        if (!Utils.isValidationEmpty(AdvertiseUtils.PredChampURL) || !Utils.isValidationEmpty(
                                                AdvertiseUtils.GameURL
                                            ) || !Utils.isValidationEmpty(AdvertiseUtils.BitcoinURL)
                                        ) {
                                            AllInterstitialNewAd.CallOpenQureka(
                                                splashScreenActivity, Constant.PredChampInsideType
                                            ) { screenTransaction() }
                                        } else {
                                            screenTransaction()
                                        }
                                    } else {
                                        screenTransaction()
                                    }
                                } else {
                                    screenTransaction()
                                }
                            } else {
                                Log.d("68485", " - Else IN")

                                //Constant.AppOpenAdId = null;
                                //new SubAppOpenManager(MyApplicationClass.application);
                                if (Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_advertise_available)
                                        .equals("2"))
                                ) {

                                    //For Ads Type = 2 and Ads = G,F - G and Splash Ads = 0...
                                    Log.d("68485", " - Else IN 2")
                                    if (strAdPriority.equals(
                                            "G", ignoreCase = true
                                        ) || strAdPriority.equals(
                                            "G,F", ignoreCase = true
                                        ) || strAdPriority.equals("F,G", ignoreCase = true)
                                    ) {
                                        Constant.AppOpenAdId = null
                                        AllVideoDownloaderApplicationClass.application?.let {
                                            SubAppOpenManager(
                                                it, this@SplashScreenAct
                                            )
                                        }
                                        Constant.AppOpenAdId = "ABCD"
                                    } else if (strAdPriority.equals("F", ignoreCase = true)) {
                                        Constant.AppOpenAdId = null
                                        AllVideoDownloaderApplicationClass.application?.let {
                                            SubAppOpenManager(
                                                it, this@SplashScreenAct
                                            )
                                        }
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_advertise_available
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_advertise_available)
                                        .equals("1"))) && !Utils.isValidationEmpty(strAdPriority) && strAdPriority.equals(
                                        "F", ignoreCase = true
                                    )
                                ) {

                                    //For Ads Type = 1 and Ads = F and Splash Ads = 0...
                                    Log.d("68485", " - Else IN 1 F")
                                    if (SharedPreferences.getStringName(Constant.is_advertise_available)
                                            .equals("1")
                                    ) {
                                        Constant.AppOpenAdId = null
                                        AllVideoDownloaderApplicationClass.application?.let {
                                            SubAppOpenManager(
                                                it, this@SplashScreenAct
                                            )
                                        }
                                    }
                                } else {
                                    //For Ads Type = 1 and Splash Ads = 0...
                                    Log.d("68485", " - else")
                                    if (SharedPreferences.getStringName(Constant.is_advertise_available)
                                            .equals("1")
                                    ) {
                                        AllVideoDownloaderApplicationClass.application?.let {
                                            SubAppOpenManager(
                                                it, this@SplashScreenAct
                                            )
                                        }
                                    }
                                }
                                screenTransaction()
                            }
                        } else {
                            SharedPreferences.setStringName(Constant.NORMAL_AD_COUNT, "1")
                            SharedPreferences.setStringName(
                                Constant.is_advertise_available, "1"
                            )
                            SharedPreferences.setStringName(Constant.IS_INFO, "1")
                            SharedPreferences.setStringName(Constant.IS_SPLASH_AD, "0")
                            dataModel.isUserDialog = (true)
                            dataModel.appVersionCode = ("1")
                            dataModel.isAdvertiseAvailable = ("0")
                            adPriority.priority = ("NA")
                            SharedPreferences.setAdvertiseModel(adPriority)
                            Handler(Looper.getMainLooper()).postDelayed(
                                { screenTransaction() }, splashTimeOut.toLong()
                            )
                        }
                    } else {
                        screenTransaction()
                    }
                }

                override fun onError(anError: ANError) {
                    Log.d("okhttp", "" + anError.message)
                    screenTransaction()
                }
            })
    }

    private fun screenTransaction() {

        var intent = Intent(this, LoginScreenAct::class.java)
        startActivity(intent)


//        if (!Utils.isValidationEmpty(intent.getStringExtra("android.intent.extra.TEXT"))) {
//            startActivity(
//                Intent(
//                    applicationContext, AllDownloadsActivity::class.java
//                ).putExtra(
//                    "android.intent.extra.TEXT", intent?.getStringExtra("android.intent.extra.TEXT")
//                )
//            )
//            finish()
//            return
//        }
//        startActivity(Intent(applicationContext, LanguagesActivity::class.java))

        finish()

    }


}