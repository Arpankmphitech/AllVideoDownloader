package com.example.allvideodownloaderrevolt.act

import android.Manifest
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.adapter.ViewPagerWhatsApp
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActWhatsAppBinding

class WhatsAppAct : BaseAct() {

    lateinit var actWAppBinding: ActWhatsAppBinding
    var whatsappAdapter: ViewPagerWhatsApp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            actWAppBinding = ActWhatsAppBinding.inflate(layoutInflater)
        } catch (e: Exception) {
        }
        setContentView(actWAppBinding.root)
        activity = this
        Utils.setStatusBarSkyGradientActivity(activity as WhatsAppAct)


        actWAppBinding.listViewPager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                when (position) {
                    0 -> {
                        actWAppBinding.txtImages.setTextColor(resources.getColor(R.color.colorWhite))
                        actWAppBinding.txtImages.background =
                            getDrawable(R.drawable.blue_gradient_8sdp_bg)

                        actWAppBinding.tvVideos.setTextColor(resources.getColor(R.color.colorSilver))
                        actWAppBinding.tvVideos.background = null
                        actWAppBinding.txtSave.setTextColor(resources.getColor(R.color.colorSilver))
                        actWAppBinding.txtSave.background = null

                    }
                    1 -> {
                        actWAppBinding.tvVideos.setTextColor(resources.getColor(R.color.colorWhite))
                        actWAppBinding.tvVideos.background =
                            getDrawable(R.drawable.blue_gradient_8sdp_bg)

                        actWAppBinding.txtSave.setTextColor(resources.getColor(R.color.colorSilver))
                        actWAppBinding.txtSave.background = null
                        actWAppBinding.txtImages.setTextColor(resources.getColor(R.color.colorSilver))
                        actWAppBinding.txtImages.background = null

                    }
                    else -> {
                        actWAppBinding.txtSave.setTextColor(resources.getColor(R.color.colorWhite))
                        actWAppBinding.txtSave.background =
                            getDrawable(R.drawable.blue_gradient_8sdp_bg)

                        actWAppBinding.tvVideos.setTextColor(resources.getColor(R.color.colorSilver))
                        actWAppBinding.tvVideos.background = null
                        actWAppBinding.txtImages.setTextColor(resources.getColor(R.color.colorSilver))
                        actWAppBinding.txtImages.background = null
                    }
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        actWAppBinding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        actWAppBinding.toolBar.txtTitleName.text = "Status Saver"
        actWAppBinding.toolBar.imgApp.setImageDrawable(getDrawable(R.drawable.ic_go_wa))
        actWAppBinding.toolBar.imgApp.visibility = View.VISIBLE
        actWAppBinding.toolBar.imgApp.setOnClickListener {
            if (isPackageExisted(Constant.WHATSAPP_PACKAGE_PATH)) {
                val launchIntent = Constant.WHATSAPP_PACKAGE_PATH.let { it1 ->
                    packageManager.getLaunchIntentForPackage(
                        it1
                    )
                }

                Utils.displayInter(activity!!, {
                    launchIntent?.let { startActivity(it) }
                }, true)

            } else Toast.makeText(
                applicationContext, getString(R.string.app_not_installed), Toast.LENGTH_SHORT
            ).show()

        }

        actWAppBinding.txtImages.setOnClickListener {
            actWAppBinding.listViewPager.currentItem = 0
        }

        actWAppBinding.tvVideos.setOnClickListener {
            actWAppBinding.listViewPager.currentItem = 1
        }

        actWAppBinding.txtSave.setOnClickListener {
            actWAppBinding.listViewPager.currentItem = 2
        }

        if (Build.VERSION.SDK_INT > 31) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_MEDIA_VIDEO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_IMAGES
                    ), 101
                )
            } else {
                whatsappAdapter = ViewPagerWhatsApp(
                    supportFragmentManager,
                    Constant.WHATSAPP_IMAGE,
                    Constant.WHATSAPP_VIDEO,
                    Constant.WHATSAPP_IMAGE_R,
                    Constant.WHATSAPP_VIDEO_R,
                    intent.getIntExtra("pos", 1)
                )
                actWAppBinding.listViewPager.adapter = whatsappAdapter
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), 101
                )
            } else {
                whatsappAdapter = ViewPagerWhatsApp(
                    supportFragmentManager,
                    Constant.WHATSAPP_IMAGE,
                    Constant.WHATSAPP_VIDEO,
                    Constant.WHATSAPP_IMAGE_R,
                    Constant.WHATSAPP_VIDEO_R,
                    intent.getIntExtra("pos", 1)
                )
                actWAppBinding.listViewPager.adapter = whatsappAdapter
            }
        }

    }

    private fun isPackageExisted(targetPackage: String): Boolean {
        val packages: List<ApplicationInfo>
        val pm: PackageManager = packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == targetPackage) return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //denied
                Toast.makeText(
                    applicationContext, "Permission required to continue", Toast.LENGTH_SHORT
                ).show()
            } else {
                if (ActivityCompat.checkSelfPermission(
                        this, permission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    whatsappAdapter = ViewPagerWhatsApp(
                        supportFragmentManager,
                        Constant.WHATSAPP_IMAGE,
                        Constant.WHATSAPP_VIDEO,
                        Constant.WHATSAPP_IMAGE_R,
                        Constant.WHATSAPP_VIDEO_R,
                        intent.getIntExtra("pos", 1)
                    )
                    actWAppBinding.listViewPager.adapter = whatsappAdapter

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
        Utils.loadInter(this)
        Utils.loadSmallNative(this, 2)
    }

    override fun onBackPressed() {
        Utils.displayInterOnBack(this)
    }

}
