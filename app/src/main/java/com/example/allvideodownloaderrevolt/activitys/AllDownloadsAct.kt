package com.example.allvideodownloaderrevolt.activitys

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
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.JavaHelper
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.common.Utils.hideKeyBoard
import com.example.allvideodownloaderrevolt.common.videodownloads.ChingariDwClass
import com.example.allvideodownloaderrevolt.common.videodownloads.InstaMethodDwClass
import com.example.allvideodownloaderrevolt.common.videodownloads.InstagramDwClass
import com.example.allvideodownloaderrevolt.common.videodownloads.RoposoDwClass
import com.example.allvideodownloaderrevolt.databinding.ActAllDownloadsBinding
import com.example.allvideodownloaderrevolt.security.Crypto
import com.video.downloader.free.allvideo.media.download.social.videos.hd.common.videodownload.*
import com.video.downloader.free.allvideo.media.download.social.videos.hd.common.videodownload.JoshDwClass
import com.video.downloader.free.allvideo.media.download.social.videos.hd.common.videodownload.ShareChatDwClass
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL

class AllDownloadsAct : BaseAct() {

    lateinit var binding: ActAllDownloadsBinding
    private var intFrom = 0
    private var method: InstaMethodDwClass? = null
    private var permission = arrayOf(
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActAllDownloadsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this@AllDownloadsAct
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        Utils.setStatusBarSkyGradientActivity(activity as AllDownloadsAct)

        initView()
        initToolBar()
        initSetData()
        initClick()
    }

    private fun initView() {
        intFrom = intent.getIntExtra("from", 0)
    }

    private fun initToolBar() {
        binding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        when (intFrom) {
            0 -> {
                binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.sharechat)
            }
            1 -> {
                binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.twitter)
            }
            2 -> {
                binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.facebook)
            }
            3 -> {
                binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.josh)
            }
            4 -> {
                binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.instagram)
            }
            5 -> {
                binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.chingari)
            }
            6 -> {
                binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.tiki)
            }
            7 -> {
                binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.roposo)
            }
            8 -> {
                binding.toolBar.txtTitleName.text = activity.resources.getString(R.string.vimeo)
            }
        }
    }

    private fun initClick() {
        binding.btnUriDataDownloads.setOnClickListener {
            if (Build.VERSION.SDK_INT > 31) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_MEDIA_VIDEO
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(activity, permission, 101)
                } else {
//                    if (!Utils.isValidationUrl(binding.edtWebURL.text.toString())) {
//                        Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
//                            .show()
//                    }
                    Constant.createFolder()
                    if (Utils.isNetworkAvailable(
                            activity,
                            canShowErrorDialogOnFail = true,
                            isFinish = true
                        )
                    ) {
                        when (intFrom) {
                            0 -> {
                                //share chat
                                shareChatDownloads()
                            }
                            1 -> {
                                //twitter
                                twitterDownloads()
                            }
                            2 -> {
                                //fb
                                facebookDownloads(activity)
                            }
                            3 -> {
                                //josh
                                joshDownloads()
                            }
                            4 -> {
                                //instagram
                                instagramDownloads()
                            }
                            5 -> {
                                //chingari
                                chingariDownloads()
                            }
                            6 -> {
                                //tiki
                                tikiDownloads()
                            }
                            7 -> {
                                //roposo
                                roposoDownloads()
                            }
                            8 -> {
                                //vimeo
                                vimeoDownloads()
                            }
                        }
                    } else {
//                        Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
//                            .show()
                    }
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        permission,
                        101
                    )
                } else {
//                    if (!Utils.isValidationUrl(binding.edtWebURL.text.toString())) {
//                        Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
//                            .show()
//                    }
                    Constant.createFolder()
                    if (Utils.isNetworkAvailable(
                            activity,
                            canShowErrorDialogOnFail = true,
                            isFinish = true
                        )
                    ) {
                        when (intFrom) {
                            0 -> {
                                //share chat
                                shareChatDownloads()
                            }
                            1 -> {
                                //twitter
                                twitterDownloads()
                            }
                            2 -> {
                                //fb
                                facebookDownloads(activity)
                            }
                            3 -> {
                                //josh
                                joshDownloads()
                            }
                            4 -> {
                                //instagram
                                instagramDownloads()
                            }
                            5 -> {
                                //chingari
                                chingariDownloads()
                            }
                            6 -> {
                                //tiki
                                tikiDownloads()
                            }
                            7 -> {
                                //roposo
                                roposoDownloads()
                            }
                            8 -> {
                                //vimeo
                                roposoDownloads()
                            }
                        }
                    } else {
//                        Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT)
//                            .show()
                    }
                }
            }
        }
        binding.btnUriDataPaste.setOnClickListener {
            val clipboard =
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            try {
                val text = clipboard.primaryClip!!.getItemAt(0).text
                binding.edtWebURL.setText(text)
            } catch (e: Exception) {
                return@setOnClickListener
            }
        }
    }

    private fun initSetData() {
        val gradientDrawable = binding.btnUriDataDownloads.background as GradientDrawable
        when (intFrom) {
            0 -> {
                setChangeData(
                    activity.resources.getString(R.string.sharechat),
                    activity.resources.getColor(R.color.colorRose),
                    activity.resources.getColorStateList(R.color.colorRose),
                    activity.resources.getColorStateList(R.color.colorLavenderBlush),
                    activity.resources.getDrawable(R.drawable.ic_sharechat),
                    activity.resources.getDrawable(R.drawable.ic_download_share), gradientDrawable
                )
            }
            1 -> {
                setChangeData(
                    activity.resources.getString(R.string.twitter),
                    activity.resources.getColor(R.color.colorDeepSky),
                    activity.resources.getColorStateList(R.color.colorDeepSky),
                    activity.resources.getColorStateList(R.color.colorAlicBlue),
                    activity.resources.getDrawable(R.drawable.ic_twitter),
                    activity.resources.getDrawable(R.drawable.ic_download_twitter),
                    gradientDrawable
                )
            }
            2 -> {
                setChangeData(
                    activity.resources.getString(R.string.facebook),
                    activity.resources.getColor(R.color.colorMariner),
                    activity.resources.getColorStateList(R.color.colorMariner),
                    activity.resources.getColorStateList(R.color.colorLavender),
                    activity.resources.getDrawable(R.drawable.ic_facebook),
                    activity.resources.getDrawable(R.drawable.ic_download_facebook),
                    gradientDrawable
                )
            }
            3 -> {
                setChangeData(
                    activity.resources.getString(R.string.josh),
                    activity.resources.getColor(R.color.colorDarkTurquoise),
                    activity.resources.getColorStateList(R.color.colorDarkTurquoise),
                    activity.resources.getColorStateList(R.color.colorLightCyan),
                    activity.resources.getDrawable(R.drawable.ic_josh),
                    activity.resources.getDrawable(R.drawable.ic_download_josh), gradientDrawable
                )
            }
            4 -> {
                setChangeData(
                    activity.resources.getString(R.string.instagram),
                    activity.resources.getColor(R.color.colorRadicalRed),
                    activity.resources.getColorStateList(R.color.colorRadicalRed),
                    activity.resources.getColorStateList(R.color.colorAmour),
                    activity.resources.getDrawable(R.drawable.ic_instagram),
                    activity.resources.getDrawable(R.drawable.ic_download_instagram),
                    gradientDrawable
                )
            }
            5 -> {
                setChangeData(
                    activity.resources.getString(R.string.chingari),
                    activity.resources.getColor(R.color.colorBlackCurrent),
                    activity.resources.getColorStateList(R.color.colorBlackCurrent),
                    activity.resources.getColorStateList(R.color.colorThistle),
                    activity.resources.getDrawable(R.drawable.ic_chingari),
                    activity.resources.getDrawable(R.drawable.ic_download_chingari),
                    gradientDrawable
                )
            }
            6 -> {
                setChangeData(
                    activity.resources.getString(R.string.tiki),
                    activity.resources.getColor(R.color.colorCorn),
                    activity.resources.getColorStateList(R.color.colorCorn),
                    activity.resources.getColorStateList(R.color.colorCornSilk),
                    activity.resources.getDrawable(R.drawable.ic_tiki),
                    activity.resources.getDrawable(R.drawable.ic_download_tiki), gradientDrawable
                )
            }
            7 -> {
                setChangeData(
                    activity.resources.getString(R.string.roposo),
                    activity.resources.getColor(R.color.colorHeliotrope),
                    activity.resources.getColorStateList(R.color.colorHeliotrope),
                    activity.resources.getColorStateList(R.color.colorMagnolia),
                    activity.resources.getDrawable(R.drawable.ic_roposo),
                    activity.resources.getDrawable(R.drawable.ic_download_roposo), gradientDrawable
                )

            }
            8 -> {
                setChangeData(
                    activity.resources.getString(R.string.vimeo),
                    activity.resources.getColor(R.color.colorSummerSky),
                    activity.resources.getColorStateList(R.color.colorSummerSky),
                    activity.resources.getColorStateList(R.color.colorAliceBlue),
                    activity.resources.getDrawable(R.drawable.ic_vimeo),
                    activity.resources.getDrawable(R.drawable.ic_download_vimeo), gradientDrawable
                )
            }
        }
    }


    private fun setChangeData(
        string: String?, color: Int, colorStateList: ColorStateList?,
        stateList: ColorStateList?, drawable2: Drawable?,
        drawable: Drawable?, gradientDrawable: GradientDrawable,
    ) {
        binding.txtDownloadTitle.text = string
        binding.txtFrImageTitle.text =
            activity.resources.getString(R.string._1_copy_video_link_from_sharechat, string)
//        binding.txtDownloadTitle.setTextColor(color)
//        binding.btnUriDataDownloads.setTextColor(color)
        binding.imgDownloadLogo.backgroundTintList = colorStateList
//        binding.btnUriDataPaste.backgroundTintList = colorStateList
//        binding.linkDownloadBoxBg.backgroundTintList = stateList
        binding.imgDownloadLogo.setImageDrawable(drawable2)
        binding.imgLogoDefault.setImageDrawable(drawable)
//        gradientDrawable.setStroke(5, color)
    }

    private fun instagramDownloads() {
        method = InstaMethodDwClass(activity) { _, type, data ->

            if (type.equals("getData")) {
                Utils.ShowProgressbarDialog(
                    activity,
                    getString(R.string.please_wait_we_are_downloading)
                )
                val findData =
                    InstagramDwClass(
                        applicationContext
                    ) { _, _, _ -> }
                findData.data(data)
            }

        }
//        val url = binding.edtWebURL.text.toString().trim { it <= ' ' }
//        if (!Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim { it <= ' ' })) {
//            method!!.onClick(0, "getData", url)
//        } else {
//            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
//        }

        val url = binding.edtWebURL.text.toString().trim { it <= ' ' }
        if (Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim { it <= ' ' })) {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        } else if (!Utils.isValidationUrl(binding.edtWebURL.text.toString().trim { it <= ' ' })) {
            Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
        } else {
            method!!.onClick(0, "getData", url)
        }
    }

    private fun roposoDownloads() {
        if (!Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim { it <= ' ' })) {
            if (binding.edtWebURL.text.toString().trim { it <= ' ' }.contains("roposo")) {
                val url = binding.edtWebURL.text.toString().trim { it <= ' ' }
                Log.e("17/01", "roposoDownloads:$url")
                Utils.ShowProgressbarDialog(
                    activity,
                    getString(R.string.please_wait_we_are_downloading)
                )
                RoposoDwClass(activity, Constant.ROPOSO_PATH, url).execute(url)
            } else {
                Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun chingariDownloads() {
        if (!Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim { it <= ' ' })) {
            if (binding.edtWebURL.text.toString().trim { it <= ' ' }.contains("chingari")) {
                val url = binding.edtWebURL.text.toString().trim { it <= ' ' }
                Utils.ShowProgressbarDialog(
                    activity,
                    getString(R.string.please_wait_we_are_downloading)
                )
                ChingariDwClass(activity, Constant.CHINGARI_PATH).execute(url)
            } else {
                Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun tikiDownloads() {
        if (!Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim { it <= ' ' })) {
            if (binding.edtWebURL.text.toString().contains("tiki")) {
                val url = binding.edtWebURL.text.toString()
                Utils.ShowProgressbarDialog(
                    activity,
                    getString(R.string.please_wait_we_are_downloading)
                )
                TikiDwClass(
                    activity,
                    Constant.TIKI_PATH
                ).execute(url)
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
        }
    }

    private fun joshDownloads() {
        if (!Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim { it <= ' ' })) {
            if (binding.edtWebURL.text.toString().contains("myjosh")) {
                var url = binding.edtWebURL.text.toString()
                Log.e("13/01", "joshDownloads: $url")
                if (url.contains("http") && url.contains("?")) {
                    url = url.substring(url.indexOf("http"), url.indexOf("?"))
                    if (URLUtil.isValidUrl(url)) {
                        Utils.ShowProgressbarDialog(
                            activity,
                            activity.resources.getString(R.string.please_wait_we_are_downloading)
                        )
                        JoshDwClass(activity, Constant.JOSH_PATH).execute(url)
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

    private fun shareChatDownloads() {
        try {
            if (Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim { it <= ' ' })) {
                Toast.makeText(activity, getString(R.string.enter_url), Toast.LENGTH_SHORT).show()
                hideKeyBoard(activity)

            } else if (!Utils.isValidationUrl(
                    binding.edtWebURL.text.toString().trim { it <= ' ' })
            ) {
                Toast.makeText(activity, getString(R.string.valid_url), Toast.LENGTH_SHORT).show()
                hideKeyBoard(activity)

            } else {
                val url = URL(binding.edtWebURL.text.toString())
                val host = url.host
                if (host.contains("sharechat")) {
                    Utils.ShowProgressbarDialog(
                        activity,
                        activity.resources.getString(R.string.please_wait_we_are_downloading)
                    )
                    ShareChatDwClass(
                        activity,
                        Constant.SHARE_CHAT_PATH
                    ).execute(binding.edtWebURL.text.toString()
                        .trim { it <= ' ' })
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

    private fun twitterDownloads() {
        if (Utils.isValidatEmpty(binding.edtWebURL.text.toString())) {
            Toast.makeText(
                activity,
                activity.resources.getString(R.string.enter_url),
                Toast.LENGTH_SHORT
            ).show()
            Log.e("0101", "twitterDownloads:23 ")
            Utils.HideProgressbarDialog()

        } else if (!Utils.isValidationUrl(binding.edtWebURL.text.toString())) {
            Toast.makeText(
                activity,
                activity.resources.getString(R.string.valid_url),
                Toast.LENGTH_SHORT
            ).show()
            Log.e("0101", "twitterDownloads:2 ")
            Utils.HideProgressbarDialog()

        } else {
            if (!Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim { it <= ' ' })
                && URL(binding.edtWebURL.text.toString()).host
                    .contains("twitter.com")
            ) {
                Utils.ShowProgressbarDialog(
                    activity,
                    getString(R.string.please_wait_we_are_downloading)
                )
                val tweetId =
                    JavaHelper.getTweetId(binding.edtWebURL.text.toString().trim())

                if (tweetId != null) {
                    AndroidNetworking.post(
                        Crypto.decrypt(
                            Constant.twitterApi,
                            Constant.encryptionKey
                        )
                    )
                        .addBodyParameter(
                            "id",
                            tweetId.toString()
                        )
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {

                                if (response != null) {
                                    if (response.optJSONArray("videos") != null) {
                                        if (response.optJSONArray("videos")!!
                                                .optJSONObject(0) != null
                                        ) {
                                            if (!Utils.isValidationEmpty(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(0)
                                                        .get("url").toString()
                                                )
                                            ) {
                                                Utils.newDownload(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(0)
                                                        .get("url").toString(),
                                                    activity
                                                )
                                            }
                                        } else if (response.optJSONArray("videos")!!
                                                .optJSONObject(1) != null
                                        ) {
                                            if (!Utils.isValidationEmpty(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(1)
                                                        .get("url").toString()
                                                )
                                            ) {
                                                Utils.newDownload(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(1)
                                                        .get("url").toString(),
                                                    activity
                                                )
                                            }
                                        } else if (response.optJSONArray("videos")!!
                                                .optJSONObject(2) != null
                                        ) {
                                            if (!Utils.isValidationEmpty(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(2)
                                                        .get("url").toString()
                                                )
                                            ) {
                                                Utils.newDownload(
                                                    response.optJSONArray("videos")!!
                                                        .optJSONObject(2)
                                                        .get("url").toString(),
                                                    activity)
                                            }

                                        }
                                    }
                                }
                            }

                            override fun onError(error: ANError) {
                                Utils.HideProgressbarDialog()
                                Toast.makeText(
                                    activity,
                                    activity.getString(R.string.valid_url),
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e("0101", "twitterDownloads:3 ")

                            }
                        })
                } else {
                    (activity as? Activity)?.runOnUiThread {
                        Toast.makeText(
                            activity,
                            activity.getString(R.string.valid_url),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("0101", "twitterDownloads: ")
                        Utils.HideProgressbarDialog()
                    }
                }


            }
        }

    }

    private fun facebookDownloads(activity: Activity) {
        if (!Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim { it <= ' ' })) {
            val url = binding.edtWebURL.text.toString().trim { it <= ' ' }
            if (url.contains("fb")) {
                Utils.ShowProgressbarDialog(
                    activity,
                    activity.resources.getString(R.string.please_wait_we_are_downloading)
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

    private fun vimeoDownloads() {
        if (!Utils.isValidatEmpty(binding.edtWebURL.text.toString().trim())) {
            if (binding.edtWebURL.text.toString().contains("vimeo")) {
                val url = binding.edtWebURL.text.toString()
                Utils.ShowProgressbarDialog(
                    activity,
                    getString(R.string.please_wait_we_are_downloading)
                )
                VimeoDwClass(
                    applicationContext,
                    Constant.VIMEO_PATH
                ).execute(url)

            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.valid_url),
                    Toast.LENGTH_SHORT
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