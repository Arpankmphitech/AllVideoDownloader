package com.example.allvideodownloaderrevolt.act

import android.Manifest
import android.app.Activity
import android.content.ClipboardManager
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.JavaHelper
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.commonClass.Utils.hideKeyBoard
import com.example.allvideodownloaderrevolt.commonClass.videoDownloadsClass.ChingariDwClass
import com.example.allvideodownloaderrevolt.commonClass.videoDownloadsClass.InstaMethodDwClass
import com.example.allvideodownloaderrevolt.commonClass.videoDownloadsClass.InstagramDwClass
import com.example.allvideodownloaderrevolt.commonClass.videoDownloadsClass.RoposoDwClass
import com.example.allvideodownloaderrevolt.commonClass.videodownload.*
import com.example.allvideodownloaderrevolt.commonClass.videodownload.JoshDwClass
import com.example.allvideodownloaderrevolt.commonClass.videodownload.ShareChatDwClass
import com.example.allvideodownloaderrevolt.databinding.ActAllDownloadsBinding
import com.example.allvideodownloaderrevolt.securityClass.Crypto
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL

class AllDownloadsAct : BaseAct() {

    lateinit var actAlDwBinding: ActAllDownloadsBinding
    private var fromInt = 0
    private var methodInsta: InstaMethodDwClass? = null
    private var accessPermission = arrayOf(
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actAlDwBinding = ActAllDownloadsBinding.inflate(layoutInflater)
        setContentView(actAlDwBinding.root)

        activity = this@AllDownloadsAct
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        Utils.setStatusBarSkyGradientActivity(activity as AllDownloadsAct)

        initView()
        AVDToolBar()
        AVDSetData()
        AVDClick()
    }

    private fun initView() {
        fromInt = intent.getIntExtra("from", 0)
    }

    private fun AVDToolBar() {
        actAlDwBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        when (fromInt) {
            0 -> {
                actAlDwBinding.toolBar.txtTitleName.text =
                    activity!!.resources.getString(R.string.sharechat)
            }
            1 -> {
                actAlDwBinding.toolBar.txtTitleName.text =
                    activity!!.resources.getString(R.string.twitter)
            }
            2 -> {
                actAlDwBinding.toolBar.txtTitleName.text =
                    activity!!.resources.getString(R.string.facebook)
            }
            3 -> {
                actAlDwBinding.toolBar.txtTitleName.text =
                    activity!!.resources.getString(R.string.josh)
            }
            4 -> {
                actAlDwBinding.toolBar.txtTitleName.text =
                    activity!!.resources.getString(R.string.instagram)
            }
            5 -> {
                actAlDwBinding.toolBar.txtTitleName.text =
                    activity!!.resources.getString(R.string.chingari)
            }
            6 -> {
                actAlDwBinding.toolBar.txtTitleName.text =
                    activity!!.resources.getString(R.string.tiki)
            }
            7 -> {
                actAlDwBinding.toolBar.txtTitleName.text =
                    activity!!.resources.getString(R.string.roposo)
            }
            8 -> {
                actAlDwBinding.toolBar.txtTitleName.text =
                    activity!!.resources.getString(R.string.vimeo)
            }
        }
    }

    private fun AVDClick() {
        actAlDwBinding.btnUriDataDownloads.setOnClickListener {
            if (Build.VERSION.SDK_INT > 31) {
                if (ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_MEDIA_VIDEO
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(activity!!, accessPermission, 101)
                } else {
//                    if (!Utils.isValidationUrl(actAlDwBinding.edtWebURL.text.toString())) {
//                        Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
//                            .show()
//                    }
                    Constant.createFolder()
                    if (Utils.isNetworkAvailable(
                            activity!!, canShowErrorDialogOnFail = true, isFinish = true
                        )
                    ) {
                        when (fromInt) {
                            0 -> {
                                //share chat
                                shareChatVideoDownloads()
                            }
                            1 -> {
                                //twitter
                                twitterVideoDownloads()
                            }
                            2 -> {
                                //fb
                                facebookVideoDownloads(activity!!)
                            }
                            3 -> {
                                //josh
                                joshVideoDownloads()
                            }
                            4 -> {
                                //instagram
                                instaVideoDownloads()
                            }
                            5 -> {
                                //chingari
                                chingariVideoDownloads()
                            }
                            6 -> {
                                //tiki
                                tikiVideoDownloads()
                            }
                            7 -> {
                                //roposo
                                roposoVideoDownloads()
                            }
                            8 -> {
                                //vimeo
                                vimeoVideoDownloads()
                            }
                        }
                    } else {
//                        Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
//                            .show()
                    }
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this, accessPermission, 101
                    )
                } else {
//                    if (!Utils.isValidationUrl(actAlDwBinding.edtWebURL.text.toString())) {
//                        Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
//                            .show()
//                    }
                    Constant.createFolder()
                    if (Utils.isNetworkAvailable(
                            activity!!, canShowErrorDialogOnFail = true, isFinish = true
                        )
                    ) {
                        when (fromInt) {
                            0 -> {
                                //share chat
                                shareChatVideoDownloads()
                            }
                            1 -> {
                                //twitter
                                twitterVideoDownloads()
                            }
                            2 -> {
                                //fb
                                facebookVideoDownloads(activity!!)
                            }
                            3 -> {
                                //josh
                                joshVideoDownloads()
                            }
                            4 -> {
                                //instagram
                                instaVideoDownloads()
                            }
                            5 -> {
                                //chingari
                                chingariVideoDownloads()
                            }
                            6 -> {
                                //tiki
                                tikiVideoDownloads()
                            }
                            7 -> {
                                //roposo
                                roposoVideoDownloads()
                            }
                            8 -> {
                                //vimeo
                                roposoVideoDownloads()
                            }
                        }
                    } else {
//                        Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
//                            .show()
                    }
                }
            }
        }
        actAlDwBinding.btnUriDataPaste.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            try {
                val text = clipboard.primaryClip!!.getItemAt(0).text
                actAlDwBinding.edtWebURL.setText(text)
            } catch (e: Exception) {
                return@setOnClickListener
            }
        }
    }

    private fun AVDSetData() {
        val gradientDrawable = actAlDwBinding.btnUriDataDownloads.background as GradientDrawable
        when (fromInt) {
            0 -> {
                setDataChange(
                    activity!!.resources.getString(R.string.sharechat),
                    activity!!.resources.getColor(R.color.colorRose),
                    activity!!.resources.getColorStateList(R.color.colorRose),
                    activity!!.resources.getColorStateList(R.color.colorLavenderBlush),
                    activity!!.resources.getDrawable(R.drawable.sharechat_logo),
                    activity!!.resources.getDrawable(R.drawable.ic_share_download),
                    gradientDrawable
                )
            }
            1 -> {
                setDataChange(
                    activity!!.resources.getString(R.string.twitter),
                    activity!!.resources.getColor(R.color.colorDeepSky),
                    activity!!.resources.getColorStateList(R.color.colorDeepSky),
                    activity!!.resources.getColorStateList(R.color.colorAlicBlue),
                    activity!!.resources.getDrawable(R.drawable.twitter_logo),
                    activity!!.resources.getDrawable(R.drawable.ic_twitter_download),
                    gradientDrawable
                )
            }
            2 -> {
                setDataChange(
                    activity!!.resources.getString(R.string.facebook),
                    activity!!.resources.getColor(R.color.colorMariner),
                    activity!!.resources.getColorStateList(R.color.colorMariner),
                    activity!!.resources.getColorStateList(R.color.colorLavender),
                    activity!!.resources.getDrawable(R.drawable.ic_icon_facebook),
                    activity!!.resources.getDrawable(R.drawable.ic_facebook_download),
                    gradientDrawable
                )
            }
            3 -> {
                setDataChange(
                    activity!!.resources.getString(R.string.josh),
                    activity!!.resources.getColor(R.color.colorDarkTurquoise),
                    activity!!.resources.getColorStateList(R.color.colorDarkTurquoise),
                    activity!!.resources.getColorStateList(R.color.colorLightCyan),
                    activity!!.resources.getDrawable(R.drawable.icon_josh),
                    activity!!.resources.getDrawable(R.drawable.ic_josh_download),
                    gradientDrawable
                )
            }
            4 -> {
                setDataChange(
                    activity!!.resources.getString(R.string.instagram),
                    activity!!.resources.getColor(R.color.colorRadicalRed),
                    activity!!.resources.getColorStateList(R.color.colorRadicalRed),
                    activity!!.resources.getColorStateList(R.color.colorAmour),
                    activity!!.resources.getDrawable(R.drawable.icon_instagram),
                    activity!!.resources.getDrawable(R.drawable.ic_instagram_download),
                    gradientDrawable
                )
            }
            5 -> {
                setDataChange(
                    activity!!.resources.getString(R.string.chingari),
                    activity!!.resources.getColor(R.color.colorBlackCurrent),
                    activity!!.resources.getColorStateList(R.color.colorBlackCurrent),
                    activity!!.resources.getColorStateList(R.color.colorThistle),
                    activity!!.resources.getDrawable(R.drawable.chingari_logo),
                    activity!!.resources.getDrawable(R.drawable.ic_chingari_download),
                    gradientDrawable
                )
            }
            6 -> {
                setDataChange(
                    activity!!.resources.getString(R.string.tiki),
                    activity!!.resources.getColor(R.color.colorCorn),
                    activity!!.resources.getColorStateList(R.color.colorCorn),
                    activity!!.resources.getColorStateList(R.color.colorCornSilk),
                    activity!!.resources.getDrawable(R.drawable.ic_tiki_logo),
                    activity!!.resources.getDrawable(R.drawable.ic_tiki_download),
                    gradientDrawable
                )
            }
            7 -> {
                setDataChange(
                    activity!!.resources.getString(R.string.roposo),
                    activity!!.resources.getColor(R.color.colorHeliotrope),
                    activity!!.resources.getColorStateList(R.color.colorHeliotrope),
                    activity!!.resources.getColorStateList(R.color.colorMagnolia),
                    activity!!.resources.getDrawable(R.drawable.ic_roposo_logo),
                    activity!!.resources.getDrawable(R.drawable.ic_roposo_download),
                    gradientDrawable
                )

            }
            8 -> {
                setDataChange(
                    activity!!.resources.getString(R.string.vimeo),
                    activity!!.resources.getColor(R.color.colorSummerSky),
                    activity!!.resources.getColorStateList(R.color.colorSummerSky),
                    activity!!.resources.getColorStateList(R.color.colorAliceBlue),
                    activity!!.resources.getDrawable(R.drawable.ic_vimeo_logo),
                    activity!!.resources.getDrawable(R.drawable.ic_vimeo_download),
                    gradientDrawable
                )
            }
        }
    }


    private fun setDataChange(
        string: String?, color: Int, colorStateList: ColorStateList?,
        stateList: ColorStateList?, drawable2: Drawable?,
        drawable: Drawable?, gradientDrawable: GradientDrawable,
    ) {
        actAlDwBinding.txtDownloadTitle.text = string
        actAlDwBinding.txtFrImageTitle.text =
            activity!!.resources.getString(R.string._1_copy_video_link_from_sharechat, string)
//        actAlDwBinding.txtDownloadTitle.setTextColor(color)
//        actAlDwBinding.btnUriDataDownloads.setTextColor(color)
        actAlDwBinding.imgDownloadLogo.backgroundTintList = colorStateList
//        actAlDwBinding.btnUriDataPaste.backgroundTintList = colorStateList
//        actAlDwBinding.linkDownloadBoxBg.backgroundTintList = stateList
        actAlDwBinding.imgDownloadLogo.setImageDrawable(drawable2)
        actAlDwBinding.imgLogoDefault.setImageDrawable(drawable)
//        gradientDrawable.setStroke(5, color)
    }

    private fun instaVideoDownloads() {
        methodInsta = InstaMethodDwClass(activity) { _, type, data ->

            if (type.equals("getData")) {
                Utils.ShowProgressbarDialog(
                    activity!!, getString(R.string.please_wait_we_are_downloading)
                )
                val findData = InstagramDwClass(
                    applicationContext
                ) { _, _, _ -> }
                findData.data(data)
            }

        }
//        val url = actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' }
//        if (!Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' })) {
//            methodInsta!!.onClick(0, "getData", url)
//        } else {
//            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
//        }

        val url = actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' }
        if (Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' })) {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        } else if (!Utils.isValidationUrl(actAlDwBinding.edtWebURL.text.toString()
                .trim { it <= ' ' })
        ) {
            Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
        } else {
            methodInsta!!.onClick(0, "getData", url)
        }
    }

    private fun roposoVideoDownloads() {
        if (!Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' })) {
            if (actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' }.contains("roposo")) {
                val url = actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' }
                Log.e("17/01", "roposoVideoDownloads:$url")
                Utils.ShowProgressbarDialog(
                    activity!!, getString(R.string.please_wait_we_are_downloading)
                )
                RoposoDwClass(activity!!, Constant.ROPOSO_PATH, url).execute(url)
            } else {
                Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun chingariVideoDownloads() {
        if (!Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' })) {
            if (actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' }.contains("chingari")) {
                val url = actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' }
                Utils.ShowProgressbarDialog(
                    activity!!, getString(R.string.please_wait_we_are_downloading)
                )
                ChingariDwClass(activity!!, Constant.CHINGARI_PATH).execute(url)
            } else {
                Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun tikiVideoDownloads() {
        if (!Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' })) {
            if (actAlDwBinding.edtWebURL.text.toString().contains("tiki")) {
                val url = actAlDwBinding.edtWebURL.text.toString()
                Utils.ShowProgressbarDialog(
                    activity!!, getString(R.string.please_wait_we_are_downloading)
                )
                TikiDwClass(
                    activity!!, Constant.TIKI_PATH
                ).execute(url)
            } else {
                Toast.makeText(
                    activity, getString(R.string.valid_url), Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun joshVideoDownloads() {
        if (!Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' })) {
            if (actAlDwBinding.edtWebURL.text.toString().contains("myjosh")) {
                var url = actAlDwBinding.edtWebURL.text.toString()
                Log.e("13/01", "joshVideoDownloads: $url")
                if (url.contains("http") && url.contains("?")) {
                    url = url.substring(url.indexOf("http"), url.indexOf("?"))
                    if (URLUtil.isValidUrl(url)) {
                        Utils.ShowProgressbarDialog(
                            activity!!,
                            activity!!.resources.getString(R.string.please_wait_we_are_downloading)
                        )
                        JoshDwClass(activity!!, Constant.JOSH_PATH).execute(url)
                    } else {
                        Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareChatVideoDownloads() {
        try {
            if (Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' })) {
                Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
                hideKeyBoard(activity)

            } else if (!Utils.isValidationUrl(actAlDwBinding.edtWebURL.text.toString()
                    .trim { it <= ' ' })
            ) {
                Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
                hideKeyBoard(activity)

            } else {
                val url = URL(actAlDwBinding.edtWebURL.text.toString())
                val host = url.host
                if (host.contains("sharechat")) {
                    Utils.ShowProgressbarDialog(
                        activity!!,
                        activity!!.resources.getString(R.string.please_wait_we_are_downloading)
                    )
                    ShareChatDwClass(
                        activity!!, Constant.SHARE_CHAT_PATH
                    ).execute(actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' })
                    hideKeyBoard(activity)
                } else {
                    Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
                        .show()
                    hideKeyBoard(activity)
                }
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    private fun twitterVideoDownloads() {
        if (Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString())) {
            Toast.makeText(
                activity, activity!!.resources.getString(R.string.enter_url), Toast.LENGTH_SHORT
            ).show()
            Log.e("0101", "twitterVideoDownloads:23 ")
            Utils.HideProgressbarDialog()

        } else if (!Utils.isValidationUrl(actAlDwBinding.edtWebURL.text.toString())) {
            Toast.makeText(
                activity, activity!!.resources.getString(R.string.valid_url), Toast.LENGTH_SHORT
            ).show()
            Log.e("0101", "twitterVideoDownloads:2 ")
            Utils.HideProgressbarDialog()

        } else {
            if (!Utils.isValidatEmpty(
                    actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' }) && URL(
                    actAlDwBinding.edtWebURL.text.toString()
                ).host.contains("twitter.com")
            ) {
                Utils.ShowProgressbarDialog(
                    activity!!, getString(R.string.please_wait_we_are_downloading)
                )
                val tweetId = JavaHelper.getTweetId(actAlDwBinding.edtWebURL.text.toString().trim())

                if (tweetId != null) {
                    AndroidNetworking.post(
                        Crypto.decrypt(
                            Constant.twitterApi, Constant.encryptionKey
                        )
                    ).addBodyParameter(
                        "id", tweetId.toString()
                    ).setTag("test").setPriority(Priority.MEDIUM).build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {

                                if (response != null) {
                                    if (response.optJSONArray("videos") != null) {
                                        if (response.optJSONArray("videos")!!
                                                .optJSONObject(0) != null
                                        ) {
                                            if (!Utils.isValidationEmpty(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(0).get("url").toString()
                                                )
                                            ) {
                                                Utils.newDownload(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(0).get("url").toString(),
                                                    activity!!
                                                )
                                            }
                                        } else if (response.optJSONArray("videos")!!
                                                .optJSONObject(1) != null
                                        ) {
                                            if (!Utils.isValidationEmpty(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(1).get("url").toString()
                                                )
                                            ) {
                                                Utils.newDownload(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(1).get("url").toString(),
                                                    activity!!
                                                )
                                            }
                                        } else if (response.optJSONArray("videos")!!
                                                .optJSONObject(2) != null
                                        ) {
                                            if (!Utils.isValidationEmpty(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(2).get("url").toString()
                                                )
                                            ) {
                                                Utils.newDownload(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(2).get("url").toString(),
                                                    activity!!
                                                )
                                            }

                                        }
                                    }
                                }
                            }

                            override fun onError(error: ANError) {
                                Utils.HideProgressbarDialog()
                                Toast.makeText(
                                    activity,
                                    activity!!.getString(R.string.valid_url),
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e("0101", "twitterVideoDownloads:3 ")

                            }
                        })
                } else {
                    (activity as? Activity)?.runOnUiThread {
                        Toast.makeText(
                            activity, activity!!.getString(R.string.valid_url), Toast.LENGTH_SHORT
                        ).show()
                        Log.e("0101", "twitterVideoDownloads: ")
                        Utils.HideProgressbarDialog()
                    }
                }


            }
        }

    }

    private fun facebookVideoDownloads(activity: Activity) {
        if (!Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' })) {
            val url = actAlDwBinding.edtWebURL.text.toString().trim { it <= ' ' }
            if (url.contains("fb")) {
                Utils.ShowProgressbarDialog(
                    activity, activity.resources.getString(R.string.please_wait_we_are_downloading)
                )
                Log.d("10/03", "1")
                FacebookVideoLinkParserDwClass({ }, activity).execute(url)
            } else {
                Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun vimeoVideoDownloads() {
        if (!Utils.isValidatEmpty(actAlDwBinding.edtWebURL.text.toString().trim())) {
            if (actAlDwBinding.edtWebURL.text.toString().contains("vimeo")) {
                val url = actAlDwBinding.edtWebURL.text.toString()
                Utils.ShowProgressbarDialog(
                    activity!!, getString(R.string.please_wait_we_are_downloading)
                )
                VimeoDwClass(
                    applicationContext, Constant.VIMEO_PATH
                ).execute(url)

            } else {
                Toast.makeText(
                    applicationContext, getString(R.string.valid_url), Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        Utils.loadInter(this)
        Utils.loadSmallNative(this, 2)
    }

    override fun onBackPressed() {
        Utils.displayInterOnBack(this)
    }

}