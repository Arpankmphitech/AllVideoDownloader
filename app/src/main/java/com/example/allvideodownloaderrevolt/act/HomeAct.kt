package com.example.allvideodownloaderrevolt.act

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ComponentName
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.SharedPreferences
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.commonClass.Utils.isValidationEmpty
import com.example.allvideodownloaderrevolt.databinding.ActHomeBinding
import com.example.allvideodownloaderrevolt.frag.HomeFrag
import com.example.allvideodownloaderrevolt.frag.VideoPlayerFrag
import com.example.allvideodownloaderrevolt.frag.WhatsAppsFrag
import com.makeramen.roundedimageview.RoundedImageView

class HomeAct : BaseAct() {

    lateinit var actHomeBinding: ActHomeBinding
    var homeInt: Int = 0
    private var strDialogUpdateText = ""
    private var homeIntDialog: Dialog? = null
    private var strDialogText: String? = ""
    private var strDialogBtnText: String? = ""

    @SuppressLint("InlinedApi")
    var permission = arrayOf(
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actHomeBinding = ActHomeBinding.inflate(layoutInflater)
        setContentView(actHomeBinding.root)
        activity = this@HomeAct
        if (intent.getIntExtra("pos", 0) == 1) {
            showUpdateOrDownloadDialog()
        }
        homeInitView()
        homeIniClick()

    }

    interface ComponentNameFilter {
        fun shouldBeFilteredOut(componentName: ComponentName?): Boolean
    }

    private fun available(name: String?): Boolean {
        var available = true
        if (name == null) {
            return false
        }
        try {
            // check if available
            activity!!.packageManager.getPackageInfo(name.trim { it <= ' ' }, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            // if not available set
            // available as false
            available = false
        }
        return available
    }

    private fun getPackgeName(): String? {
        var packageName: String? = ""
        val url: String? = SharedPreferences.getStringName(Constant.URL_USER_DIALOG)
        if (!isValidationEmpty(url!!)
            && Utils.isValidationUrl(url)
        ) {
            packageName = getQueryMap(url!!)["id"]
            return packageName
        }
        return ""
    }

    private fun getQueryMap(query: String): Map<String?, String?> {
        val map: MutableMap<String?, String?> = HashMap()
        val queryString = query.split("\\?".toRegex()).toTypedArray()
        if (queryString.size > 1) {
            val params = queryString[1].split("&".toRegex()).toTypedArray()
            for (param in params) {
                val name = param.split("=".toRegex()).toTypedArray()[0]
                val value = param.split("=".toRegex()).toTypedArray()[1]
                map[name] = value
            }
        }
        return map
    }

    @SuppressLint("WrongConstant")
    fun redirectStore(updateUrl: String?) {
        if (updateUrl == null) return
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse(updateUrl)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.let {
            val activityInfo = intent.resolveActivityInfo(it!!.packageManager, intent.flags)
            if (activityInfo != null && activityInfo.exported) {
                ActivityCompat.startActivity(it, intent, null)
            }
        }
    }

    private fun showUpdateOrDownloadDialog() {
        var currentVersionCode = 0
        val versionCodeApi: Int

        versionCodeApi = SharedPreferences.getInteger(Constant.VERSION_CODE_App)
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            currentVersionCode = pInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        strDialogText = SharedPreferences.getStringName(Constant.TEXT_USER_DIALOG)
        strDialogUpdateText =
            SharedPreferences.getStringName(Constant.UPDATE_USER_DIALOG_TEXT).toString()
        val imageUserDialog: String =
            SharedPreferences.getStringName(Constant.IMG_USER_DIALOG).toString()
        val isUserDialogChkUrl: String =
            SharedPreferences.getStringName(Constant.URL_USER_DIALOG).toString()
        strDialogBtnText = SharedPreferences.getStringName(Constant.BTN_USER_DIALOG)
        if (isValidationEmpty(strDialogUpdateText)) {
            strDialogUpdateText = getString(R.string.An_update)
        }
        if (isValidationEmpty(strDialogText!!)) {
            strDialogText = ""
        }
        if (isValidationEmpty(strDialogBtnText!!)) {
            strDialogBtnText = getString(R.string.download)
        }
        if (currentVersionCode < versionCodeApi) {
            Log.d("15/06", "1")
            runOnUiThread {
                showDownloadUpdateDialog(
                    "IsUpdate",
                    strDialogUpdateText,
                    "",
                    getString(R.string.update),
                    Constant.URL_PLAYSTORE + packageName
                )
            }
        } else {
            if (!isUserDialogChkUrl.equals(
                    "",
                    ignoreCase = true
                ) && SharedPreferences.getBoolenValue(Constant.IS_USER_DIALOG)
                && Constant.trueDialog && !available(getPackgeName())
            ) {
                Constant.trueDialog = false
                val apiPackageName = isUserDialogChkUrl.substring(46)
                if (Utils.appAlreadyInstalled(
                        applicationContext, apiPackageName
                    )
                ) {
                    runOnUiThread {
                        showDownloadUpdateDialog(
                            "IsNotUpdate",
                            strDialogText,
                            imageUserDialog,
                            strDialogBtnText,
                            isUserDialogChkUrl
                        )
                    }
                }
            }
        }
    }

    private fun showDownloadUpdateDialog(
        CheckFrom: String,
        UserMessage: String?,
        UserImageUrl: String?,
        userDialogBtn: String?,
        ClickUrl: String,
    ) {
        try {
            homeIntDialog = Dialog(activity!!, R.style.DialogTheme)
            homeIntDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            homeIntDialog!!.window!!.setBackgroundDrawable(null)
            homeIntDialog!!.setContentView(R.layout.download_update_dialog_item)
            homeIntDialog!!.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            val rImgDownloadLogo: RoundedImageView =
                homeIntDialog!!.findViewById(R.id.rImgDownloadLogo)
            val txtDialogMainTitle = homeIntDialog!!.findViewById<TextView>(R.id.txtDialogMainTitle)
            val txtDialogDes = homeIntDialog!!.findViewById<TextView>(R.id.txtDialogDes)
            val btnDownload =
                homeIntDialog!!.findViewById<AppCompatButton>(R.id.btnUriDataDownloads)
            val btnCancel = homeIntDialog!!.findViewById<AppCompatButton>(R.id.btnCancel)
            val relativeLayoutDialog =
                homeIntDialog!!.findViewById<RelativeLayout>(R.id.relativeLayoutDialog)

            if (CheckFrom.equals("IsNotUpdate", ignoreCase = true)) {
                txtDialogMainTitle.text = "Download the app"
            } else {
                txtDialogMainTitle.text = "Update the app"
                if (!isValidationEmpty(SharedPreferences.getStringName(Constant.UPDATE_USER_DIALOG_TEXT)!!)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtDialogDes.text = Html.fromHtml(
                            "" + SharedPreferences.getStringName(Constant.UPDATE_USER_DIALOG_TEXT),
                            Html.FROM_HTML_MODE_COMPACT
                        )
                    } else {
                        txtDialogDes.text =
                            Html.fromHtml("" + SharedPreferences.getStringName(Constant.UPDATE_USER_DIALOG_TEXT))
                    }

                }
            }

            if (UserMessage != null && !UserMessage.equals("", ignoreCase = true)) {
                txtDialogDes.text = UserMessage
            }
            if (userDialogBtn != null && !userDialogBtn.equals("", ignoreCase = true)) {
                btnDownload.text = userDialogBtn
            }
            if (UserImageUrl != null && !UserImageUrl.equals("", ignoreCase = true)) {
                Glide.with(this).load(UserImageUrl)
                    .into(rImgDownloadLogo)
            }

            btnDownload.setOnClickListener {
                if (homeIntDialog != null && homeIntDialog!!.isShowing) {
                    homeIntDialog!!.dismiss()
                    homeIntDialog = null
                    redirectStore(ClickUrl)
                }
            }

            btnCancel.setOnClickListener {
                if (homeIntDialog != null && homeIntDialog!!.isShowing) {
                    homeIntDialog!!.dismiss()
                    homeIntDialog = null
                }
            }

            relativeLayoutDialog.setOnClickListener {
                if (homeIntDialog != null && homeIntDialog!!.isShowing) {
                    homeIntDialog!!.dismiss()
                    homeIntDialog = null
                    redirectStore(ClickUrl)
                }
            }
            homeIntDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun homeIniClick() {

        actHomeBinding.imgHome.setOnClickListener {
            if (homeInt == 1) {
            } else {
                homeInt = 1
                bottomBarSelectOption(0)
                loadFragmentView(HomeFrag())
            }
        }

        actHomeBinding.includeHomeBottomBar.layoutStatusSaver.setOnClickListener {
            homeInt = 0
            bottomBarSelectOption(1)
            if (isPackageExisted(Constant.WHATSAPP_PACKAGE_PATH)) {
                if (Build.VERSION.SDK_INT > 31) {
                    if (ContextCompat.checkSelfPermission(
                            activity!!,
                            Manifest.permission.READ_MEDIA_VIDEO
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            permission,
                            101
                        )
                    } else {
                        loadFragmentView(WhatsAppsFrag())
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(
                            activity!!,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(
                            activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            permission,
                            101
                        )
                    } else {
                        loadFragmentView(WhatsAppsFrag())
                    }
                }
            } else {
                actHomeBinding.imgHome.setImageDrawable(activity!!.resources.getDrawable(R.drawable.icon_home_gray))

                actHomeBinding.includeHomeBottomBar.imgStatusSaver.setImageDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.icon_status_saver_gray
                    )
                )
                actHomeBinding.includeHomeBottomBar.txtStatusSaver.visibility = View.VISIBLE
                actHomeBinding.includeHomeBottomBar.txtStatusSaver2.visibility = View.GONE

                actHomeBinding.includeHomeBottomBar.imgVideoPlayer.setImageDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.ic_unslc_video_player_gray
                    )
                )
                actHomeBinding.includeHomeBottomBar.txtVideoPlayer.visibility = View.VISIBLE
                actHomeBinding.includeHomeBottomBar.txtVideoPlayer2.visibility = View.GONE
                Toast.makeText(
                    activity,
                    activity!!.resources.getString(R.string.app_not_installed2),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        actHomeBinding.includeHomeBottomBar.layoutVideoPlayer.setOnClickListener {
            homeInt = 0
            bottomBarSelectOption(2)
            loadFragmentView(VideoPlayerFrag())
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun bottomBarSelectOption(i: Int) {
        actHomeBinding.includeHomeBottomBar.imgStatusSaver.setImageDrawable(
            activity!!.resources.getDrawable(
                R.drawable.icon_status_saver_gray
            )
        )
        actHomeBinding.includeHomeBottomBar.txtStatusSaver.visibility = View.VISIBLE
        actHomeBinding.includeHomeBottomBar.txtStatusSaver2.visibility = View.GONE
        actHomeBinding.includeHomeBottomBar.imgVideoPlayer.setImageDrawable(
            activity!!.resources.getDrawable(
                R.drawable.ic_unslc_video_player_gray
            )
        )
        actHomeBinding.includeHomeBottomBar.txtVideoPlayer.visibility = View.VISIBLE
        actHomeBinding.includeHomeBottomBar.txtVideoPlayer2.visibility = View.GONE
        actHomeBinding.imgHome.setImageDrawable(activity!!.resources.getDrawable(R.drawable.icon_home_gray))

        when (i) {
            1 -> {
                actHomeBinding.includeHomeBottomBar.imgStatusSaver.setImageDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.ic_status_saver_blue
                    )
                )
                actHomeBinding.includeHomeBottomBar.txtStatusSaver2.visibility = View.VISIBLE
                actHomeBinding.includeHomeBottomBar.txtStatusSaver.visibility = View.GONE

            }
            2 -> {
                actHomeBinding.includeHomeBottomBar.imgVideoPlayer.setImageDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.ic_video_player_blue
                    )
                )
                actHomeBinding.includeHomeBottomBar.txtVideoPlayer2.visibility = View.VISIBLE
                actHomeBinding.includeHomeBottomBar.txtVideoPlayer.visibility = View.GONE

            }
            else -> {
                actHomeBinding.imgHome.setImageDrawable(activity!!.resources.getDrawable(R.drawable.ic_gradient_home))

                actHomeBinding.includeHomeBottomBar.imgStatusSaver.setImageDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.icon_status_saver_gray
                    )
                )
                actHomeBinding.includeHomeBottomBar.txtStatusSaver.visibility = View.VISIBLE
                actHomeBinding.includeHomeBottomBar.txtStatusSaver2.visibility = View.GONE

                actHomeBinding.includeHomeBottomBar.imgVideoPlayer.setImageDrawable(
                    activity!!.resources.getDrawable(
                        R.drawable.ic_unslc_video_player_gray
                    )
                )
                actHomeBinding.includeHomeBottomBar.txtVideoPlayer.visibility = View.VISIBLE
                actHomeBinding.includeHomeBottomBar.txtVideoPlayer2.visibility = View.GONE

            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun isPackageExisted(packageName: String): Boolean {
        val packages: List<ApplicationInfo>
        val pm: PackageManager = packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == packageName) return true
        }
        return false
    }

    private fun homeInitView() {
        Utils.gradientTextViewColor(
            activity!!.resources.getColor(R.color.colorSpringGreen),
            activity!!.resources.getColor(R.color.colorMalachite),
            actHomeBinding.includeHomeBottomBar.txtStatusSaver2,
            activity!!.resources.getString(R.string.status_saver)
        )

        Utils.gradientTextViewColor(
            activity!!.resources.getColor(R.color.colorSpringGreen),
            activity!!.resources.getColor(R.color.colorMalachite),
            actHomeBinding.includeHomeBottomBar.txtVideoPlayer2,
            activity!!.resources.getString(R.string.video_player)
        )

        bottomBarSelectOption(0)
        loadFragmentView(HomeFrag())
        homeInt = 1
    }

    private fun loadFragmentView(fragment: Fragment) {
        try {
            supportFragmentManager.beginTransaction()
                .replace(R.id.HomeFrame, fragment)
                .commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission!!)) {
                //denied
                Toast.makeText(
                    applicationContext,
                    "Permission required to continue",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                } else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                    break
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Utils.loadInter(activity!!)
    }

    override fun onBackPressed() {
        startActivity(Intent(activity, ExitAct::class.java))
    }

}