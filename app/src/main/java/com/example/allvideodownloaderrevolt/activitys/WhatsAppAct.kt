package com.example.allvideodownloaderrevolt.activitys

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
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActWhatsAppBinding

class WhatsAppAct : BaseAct() {

    lateinit var binding: ActWhatsAppBinding
    var adapter: ViewPagerWhatsApp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActWhatsAppBinding.inflate(layoutInflater)
        } catch (e: Exception) {
        }
        setContentView(binding.root)
        activity = this
        Utils.setStatusBarSkyGradientActivity(activity as WhatsAppAct)


        binding.listViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                when (position) {
                    0 -> {
                        binding.txtImages.setTextColor(resources.getColor(R.color.colorWhite))
                        binding.txtImages.background = getDrawable(R.drawable.sky_gradient_8sdp_bg)

                        binding.tvVideos.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.tvVideos.background = null
                        binding.txtSave.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.txtSave.background = null

                    }
                    1 -> {
                        binding.tvVideos.setTextColor(resources.getColor(R.color.colorWhite))
                        binding.tvVideos.background = getDrawable(R.drawable.sky_gradient_8sdp_bg)

                        binding.txtSave.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.txtSave.background = null
                        binding.txtImages.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.txtImages.background = null

                    }
                    else -> {
                        binding.txtSave.setTextColor(resources.getColor(R.color.colorWhite))
                        binding.txtSave.background = getDrawable(R.drawable.sky_gradient_8sdp_bg)

                        binding.tvVideos.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.tvVideos.background = null
                        binding.txtImages.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.txtImages.background = null
                    }
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        binding.toolBar.ivBackArrow.setOnClickListener { onBackPressed() }
        binding.toolBar.txtTitleName.text = "Status Saver"
        binding.toolBar.imgApp.setImageDrawable(getDrawable(R.drawable.ic_wa_go))
        binding.toolBar.imgApp.visibility = View.VISIBLE
        binding.toolBar.imgApp.setOnClickListener {
            if (isPackageExisted(Constant.WHATSAPP_PACKAGE)) {
                val launchIntent = Constant.WHATSAPP_PACKAGE.let { it1 ->
                    packageManager.getLaunchIntentForPackage(
                        it1
                    )
                }

                Utils.displayInter(activity, {
                    launchIntent?.let { startActivity(it) }
                }, true)

            } else Toast.makeText(
                applicationContext, getString(R.string.app_not_installed), Toast.LENGTH_SHORT
            ).show()

        }

        binding.txtImages.setOnClickListener {
            binding.listViewPager.currentItem = 0
        }

        binding.tvVideos.setOnClickListener {
            binding.listViewPager.currentItem = 1
        }

        binding.txtSave.setOnClickListener {
            binding.listViewPager.currentItem = 2
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
                adapter = ViewPagerWhatsApp(
                    supportFragmentManager,
                    Constant.WHATSAPP_IMAGE,
                    Constant.WHATSAPP_VIDEO,
                    Constant.WHATSAPP_IMAGE_R,
                    Constant.WHATSAPP_VIDEO_R,
                    intent.getIntExtra("pos", 1)
                )
                binding.listViewPager.adapter = adapter
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
                adapter = ViewPagerWhatsApp(
                    supportFragmentManager,
                    Constant.WHATSAPP_IMAGE,
                    Constant.WHATSAPP_VIDEO,
                    Constant.WHATSAPP_IMAGE_R,
                    Constant.WHATSAPP_VIDEO_R,
                    intent.getIntExtra("pos", 1)
                )
                binding.listViewPager.adapter = adapter
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
                    adapter = ViewPagerWhatsApp(
                        supportFragmentManager,
                        Constant.WHATSAPP_IMAGE,
                        Constant.WHATSAPP_VIDEO,
                        Constant.WHATSAPP_IMAGE_R,
                        Constant.WHATSAPP_VIDEO_R,
                        intent.getIntExtra("pos", 1)
                    )
                    binding.listViewPager.adapter = adapter

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
