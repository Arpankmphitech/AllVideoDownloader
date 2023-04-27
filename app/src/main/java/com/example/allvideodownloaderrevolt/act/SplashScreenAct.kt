package com.example.allvideodownloaderrevolt.act

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
import com.example.allvideodownloaderrevolt.AVDApplicationClass
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.AdsIdOnDeviceStore
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.SharedPreferences
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActSplashScreenBinding
import com.example.allvideodownloaderrevolt.models.adsmodule.AdsResponseModel
import com.example.allvideodownloaderrevolt.models.adsmodule.DataModel
import com.example.allvideodownloaderrevolt.models.adsmodule.PredchampAppOpenManager
import com.example.allvideodownloaderrevolt.modelsClass.adsmoduls.SubAppOpenManager
import com.example.allvideodownloaderrevolt.securityClass.Crypto
import com.google.gson.Gson
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashScreenAct : AppCompatActivity() {

    lateinit var actSplashBinding: ActSplashScreenBinding
    lateinit var splashScreenActivity: Activity
    private val screenTimeOut = 6000
    var strAdsAvailable: String? = null
    var splashAdPriority = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actSplashBinding = ActSplashScreenBinding.inflate(layoutInflater)
        setContentView(actSplashBinding.root)

        val mWindow = window
        mWindow.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        splashScreenActivity = this@SplashScreenAct

        splashInitView()

    }

    private fun splashInitView() {

        splashScreenActivity.runOnUiThread {
            if (Utils.isNetworkAvailable(
                    splashScreenActivity, canShowErrorDialogOnFail = true, isFinish = true
                )
            ) {
                splashScreenDataAPI()
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


    private fun splashScreenDataAPI() {

        AndroidNetworking.get(Crypto.decrypt(Constant.moreAppApi, Constant.encryptionKey))
            .setTag("test").doNotCacheResponse().setPriority(Priority.IMMEDIATE).build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {


                    if (response != null && Utils.isValidationEmpty(response.toString())) {
                        Constant.isClickedNotification = false
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
                            dataModel.isAdNormalCount = (dataObject.optString("is_normal_ad_count"))
                            dataModel.isSplashAd = (dataObject.optString("is_splash_ad"))
                            dataModel.isAdvertiseAvailable =
                                (dataObject.optString("is_available_advertise"))
                            dataModel.usrDialogBtn = (dataObject.optString("usr_dialog_btn"))
                            dataModel.userDialogImg = (dataObject.optString("user_dialog_img"))
                        }

                        try {
                            if (dataModel.developerName.equals("")) {
                                Constant.NAME_OF_DEVELOPER = dataModel.developerName.toString()
                            }
                        } catch (e: Exception) {
                            println(e.message)
                        }
                        Log.d("19/12", "" + dataModel.isUserDialog)
                        SharedPreferences.setBoolenValue(
                            Constant.IS_USER_DIALOG, dataModel.isUserDialog!!
                        )
                        SharedPreferences.setStringName(
                            Constant.VERSION_CODE_App, dataModel.appVersionCode
                        )
                        SharedPreferences.setStringName(
                            Constant.UPDATE_USER_DIALOG_TEXT, dataModel.userDialogUpdateText
                        )
                        SharedPreferences.setStringName(
                            Constant.URL_USER_DIALOG, dataModel.userDialogUrl
                        )
                        SharedPreferences.setStringName(
                            Constant.IMG_USER_DIALOG, dataModel.userDialogImg
                        )

                        SharedPreferences.setStringName(
                            Constant.COUNT_BACK_PRESS, dataModel.isBackPressCount
                        )
                        SharedPreferences.setStringName(
                            Constant.COUNT_NORMAL_AD, dataModel.isAdNormalCount
                        )
                        SharedPreferences.setStringName(
                            Constant.TEXT_USER_DIALOG, dataModel.userDialogText
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
                            Constant.is_available_advertise, dataModel.isAdvertiseAvailable
                        )
                        SharedPreferences.setStringName(
                            Constant.IS_AD_SPLASH, dataModel.isSplashAd
                        )

                        if (SharedPreferences.getStringName(Constant.IS_AD_SPLASH)
                                ?.let { Utils.isValidationEmpty(it) }!!
                        ) {
                            strAdsAvailable = SharedPreferences.getStringName(Constant.IS_AD_SPLASH)
                        }
                        Constant.isAdNormalCount =
                            SharedPreferences.getStringName(Constant.COUNT_NORMAL_AD)!!.toInt()
                        Constant.isAdBackCount =
                            SharedPreferences.getStringName(Constant.COUNT_BACK_PRESS)!!.toInt()


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
                            SharedPreferences.setStringName(Constant.COUNT_NORMAL_AD, "1")
                            SharedPreferences.setStringName(
                                Constant.is_available_advertise, "1"
                            )
                            SharedPreferences.setStringName(Constant.IS_AD_SPLASH, "0")
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
                            splashAdPriority = SharedPreferences.advertiseModel?.priority.toString()
                            Log.d("68485", " - splashAdPriority --> $splashAdPriority")
                            Log.d("68485", " - strAdsAvailable --> $strAdsAvailable")
                            if (gobject != null && !Utils.isValidationEmpty(gModel.ao)) {
                                Constant.OpenAppAdId = gModel.ao.toString()
                            }
                            if (adPriority.priority != null && strAdsAvailable.equals(
                                    "1", ignoreCase = true
                                )
                            ) {
                                if ((Utils.isValidationEmpty(Constant.OpenAppAdId.toString()) && Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) || (Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        ).toString()
                                    ) && SharedPreferences.getStringName(Constant.is_available_advertise)
                                        .equals("1"))) && !Utils.isValidationEmpty(splashAdPriority) && splashAdPriority.equals(
                                        "G", ignoreCase = true
                                    )
                                ) {
                                    Log.d("68485", " - G IN")
                                    val manager = AppOpenManager(AVDApplicationClass.application!!)
                                    if (!Utils.isValidationEmpty(
                                            SharedPreferences.getStringName(
                                                Constant.IS_AD_SPLASH
                                            )!!
                                        ) && SharedPreferences.getStringName(Constant.IS_AD_SPLASH)
                                            .equals("1")
                                    ) {

                                        manager.fetchSplashAd(splashScreenActivity,
                                            object : AppOpenManager.onNext {
                                                override fun nextActionPerform() {
                                                    transactionScreen()
                                                }
                                            })
                                    } else {
                                        transactionScreen()
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_available_advertise)
                                        .equals("1"))) && !Utils.isValidationEmpty(splashAdPriority) && splashAdPriority.equals(
                                        "G,F", ignoreCase = true
                                    )
                                ) {
                                    Log.d("68485", " - G F IN")
                                    val manager = AppOpenManager(AVDApplicationClass.application!!)
                                    if (!Utils.isValidationEmpty(
                                            SharedPreferences.getStringName(
                                                Constant.IS_AD_SPLASH
                                            )!!
                                        ) && SharedPreferences.getStringName(Constant.IS_AD_SPLASH)
                                            .equals("1")
                                    ) {
                                        manager.fetchSplashAd(splashScreenActivity,
                                            object : AppOpenManager.onNext {
                                                override fun nextActionPerform() {
                                                    transactionScreen()
                                                }
                                            })
                                    } else {
                                        transactionScreen()
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_available_advertise)
                                        .equals("1"))) && !Utils.isValidationEmpty(splashAdPriority) && splashAdPriority.equals(
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
                                            Constant.TypePredChampInside,
                                            { transactionScreen() },
                                            AVDApplicationClass.application
                                        )
                                    } else {
                                        transactionScreen()
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_available_advertise)
                                        .equals("1"))) && !Utils.isValidationEmpty(splashAdPriority) && splashAdPriority.equals(
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
                                            Constant.TypePredChampInside,
                                            { transactionScreen() },
                                            null
                                        )
                                    } else {
                                        transactionScreen()
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_available_advertise)
                                        .equals("2"))) && !Utils.isValidationEmpty(splashAdPriority)
                                ) {
                                    Log.d("68485", " - 2")
                                    if (splashAdPriority.equals(
                                            "G,F", ignoreCase = true
                                        ) || splashAdPriority.equals("G", ignoreCase = true)
                                    ) {
                                        Log.d("68485", " - G-G,F")
                                        if (!Utils.isValidationEmpty(AdvertiseUtils.PredChampURL) || !Utils.isValidationEmpty(
                                                AdvertiseUtils.GameURL
                                            ) || !Utils.isValidationEmpty(AdvertiseUtils.BitcoinURL)
                                        ) {
                                            Constant.OpenAppAdId = "ABCD"
                                            val adsOpenInterstitialNewAd =
                                                AdsOpenInterstitialNewAd()
                                            adsOpenInterstitialNewAd.CallOpenAdsOpenQureka(
                                                splashScreenActivity, Constant.TypePredChampInside
                                            ) { str: String? ->
                                                AVDApplicationClass.application?.let {
                                                    PredchampAppOpenManager(
                                                        it
                                                    )
                                                }
                                                transactionScreen()
                                            }
                                        } else {
                                            transactionScreen()
                                        }
                                    } else if (splashAdPriority.equals("F,G", ignoreCase = true)) {
                                        Log.d("68485", " - F,G")
                                        if (!Utils.isValidationEmpty(AdvertiseUtils.PredChampURL) || !Utils.isValidationEmpty(
                                                AdvertiseUtils.GameURL
                                            ) || !Utils.isValidationEmpty(AdvertiseUtils.BitcoinURL)
                                        ) {
                                            Constant.OpenAppAdId = "ABCD"
                                            AllInterstitialNewAd.CallOpenQureka(
                                                splashScreenActivity, Constant.TypePredChampInside
                                            ) {
                                                AVDApplicationClass.application?.let { it1 ->
                                                    PredchampAppOpenManager(
                                                        it1
                                                    )
                                                }
                                                transactionScreen()
                                            }
                                        } else {
                                            transactionScreen()
                                        }
                                    } else if (splashAdPriority.equals("F", ignoreCase = true)) {
                                        Log.d("68485", " - F")
                                        if (!Utils.isValidationEmpty(AdvertiseUtils.PredChampURL) || !Utils.isValidationEmpty(
                                                AdvertiseUtils.GameURL
                                            ) || !Utils.isValidationEmpty(AdvertiseUtils.BitcoinURL)
                                        ) {
                                            AllInterstitialNewAd.CallOpenQureka(
                                                splashScreenActivity, Constant.TypePredChampInside
                                            ) { transactionScreen() }
                                        } else {
                                            transactionScreen()
                                        }
                                    } else {
                                        transactionScreen()
                                    }
                                } else {
                                    transactionScreen()
                                }
                            } else {
                                Log.d("68485", " - Else IN")

                                //Constant.OpenAppAdId = null;
                                //new SubAppOpenManager(MyApplicationClass.application);
                                if (Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_available_advertise)
                                        .equals("2"))
                                ) {

                                    //For Ads Type = 2 and Ads = G,F - G and Splash Ads = 0...
                                    Log.d("68485", " - Else IN 2")
                                    if (splashAdPriority.equals(
                                            "G", ignoreCase = true
                                        ) || splashAdPriority.equals(
                                            "G,F", ignoreCase = true
                                        ) || splashAdPriority.equals("F,G", ignoreCase = true)
                                    ) {
                                        Constant.OpenAppAdId = null
                                        AVDApplicationClass.application?.let {
                                            SubAppOpenManager(
                                                it, this@SplashScreenAct
                                            )
                                        }
                                        Constant.OpenAppAdId = "ABCD"
                                    } else if (splashAdPriority.equals("F", ignoreCase = true)) {
                                        Constant.OpenAppAdId = null
                                        AVDApplicationClass.application?.let {
                                            SubAppOpenManager(
                                                it, this@SplashScreenAct
                                            )
                                        }
                                    }
                                } else if ((Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) || (!Utils.isValidationEmpty(
                                        SharedPreferences.getStringName(
                                            Constant.is_available_advertise
                                        )!!
                                    ) && SharedPreferences.getStringName(Constant.is_available_advertise)
                                        .equals("1"))) && !Utils.isValidationEmpty(splashAdPriority) && splashAdPriority.equals(
                                        "F", ignoreCase = true
                                    )
                                ) {

                                    //For Ads Type = 1 and Ads = F and Splash Ads = 0...
                                    Log.d("68485", " - Else IN 1 F")
                                    if (SharedPreferences.getStringName(Constant.is_available_advertise)
                                            .equals("1")
                                    ) {
                                        Constant.OpenAppAdId = null
                                        AVDApplicationClass.application?.let {
                                            SubAppOpenManager(
                                                it, this@SplashScreenAct
                                            )
                                        }
                                    }
                                } else {
                                    //For Ads Type = 1 and Splash Ads = 0...
                                    Log.d("68485", " - else")
                                    if (SharedPreferences.getStringName(Constant.is_available_advertise)
                                            .equals("1")
                                    ) {
                                        AVDApplicationClass.application?.let {
                                            SubAppOpenManager(
                                                it, this@SplashScreenAct
                                            )
                                        }
                                    }
                                }
                                transactionScreen()
                            }
                        } else {
                            SharedPreferences.setStringName(Constant.COUNT_NORMAL_AD, "1")
                            SharedPreferences.setStringName(
                                Constant.is_available_advertise, "1"
                            )
                            SharedPreferences.setStringName(Constant.IS_INFO, "1")
                            SharedPreferences.setStringName(Constant.IS_AD_SPLASH, "0")
                            dataModel.isUserDialog = (true)
                            dataModel.appVersionCode = ("1")
                            dataModel.isAdvertiseAvailable = ("0")
                            adPriority.priority = ("NA")
                            SharedPreferences.setAdvertiseModel(adPriority)
                            Handler(Looper.getMainLooper()).postDelayed(
                                { transactionScreen() }, screenTimeOut.toLong()
                            )
                        }
                    } else {
                        transactionScreen()
                    }
                }

                override fun onError(anError: ANError) {
                    Log.d("okhttp", "" + anError.message)
                    transactionScreen()
                }
            })
    }

    private fun transactionScreen() {

//        var intent = Intent(this, LoginScreenAct::class.java)
//        startActivity(intent)


        if (!Utils.isValidationEmpty(intent.getStringExtra("android.intent.extra.TEXT"))) {
            startActivity(
                Intent(
                    applicationContext, AllDownloadsAct::class.java
                ).putExtra(
                    "android.intent.extra.TEXT", intent?.getStringExtra("android.intent.extra.TEXT")
                )
            )
            finish()
            return
        }
        startActivity(Intent(applicationContext, LanguagesAct::class.java))

        finish()

    }


}