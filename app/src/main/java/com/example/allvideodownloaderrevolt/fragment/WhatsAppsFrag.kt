package com.example.allvideodownloaderrevolt.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.adapter.ViewPagerWhatsApp
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActWhatsAppBinding

class WhatsAppsFrag : Fragment() {

    lateinit var whatsActivity: Activity
    var adapter: ViewPagerWhatsApp? = null
    lateinit var binding: ActWhatsAppBinding
    lateinit var list_view_pager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        try {
            binding = ActWhatsAppBinding.inflate(layoutInflater)
        } catch (e: Exception) {
        }
        whatsActivity = requireActivity()
        whatsActivity.let { Utils.setStatusBarSkyGradientActivity(it) }
        list_view_pager = binding.listViewPager
        list_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                when (position) {
                    0 -> {
                        binding.txtImages.setTextColor(resources.getColor(R.color.colorWhite))
                        binding.txtImages.background =
                            resources.getDrawable(R.drawable.sky_gradient_8sdp_bg)

                        binding.tvVideos.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.tvVideos.background = null
                        binding.txtSave.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.txtSave.background = null

                    }
                    1 -> {
                        binding.tvVideos.setTextColor(resources.getColor(R.color.colorWhite))
                        binding.tvVideos.background =
                            resources.getDrawable(R.drawable.sky_gradient_8sdp_bg)

                        binding.txtSave.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.txtSave.background = null
                        binding.txtImages.setTextColor(resources.getColor(R.color.colorSilver))
                        binding.txtImages.background = null

                    }
                    else -> {
                        binding.txtSave.setTextColor(resources.getColor(R.color.colorWhite))
                        binding.txtSave.background =
                            resources.getDrawable(R.drawable.sky_gradient_8sdp_bg)

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
        binding.toolBar.txtTitleName.text = "Status Saver"
        binding.toolBar.imgApp.setImageDrawable(resources.getDrawable(R.drawable.ic_wa_go))
        binding.toolBar.imgApp.visibility = View.VISIBLE
        binding.toolBar.ivBackArrow.visibility = View.INVISIBLE
        binding.toolBar.imgApp.setOnClickListener {
            if (isPackageExisted(Constant.WHATSAPP_PACKAGE)) {
                val launchIntent = Constant.WHATSAPP_PACKAGE.let { it1 ->
                    whatsActivity.packageManager?.getLaunchIntentForPackage(
                        it1
                    )
                }
                launchIntent?.let { startActivity(it) }
            } else Toast.makeText(
                whatsActivity, getString(R.string.app_not_installed), Toast.LENGTH_SHORT
            ).show()

        }

        binding.txtImages.setOnClickListener {
            list_view_pager.currentItem = 0
        }

        binding.tvVideos.setOnClickListener {
            list_view_pager.currentItem = 1
        }

        binding.txtSave.setOnClickListener {
            list_view_pager.currentItem = 2
        }

        if (Build.VERSION.SDK_INT > 31) {
            if (whatsActivity.let {
                    ContextCompat.checkSelfPermission(
                        it, Manifest.permission.READ_MEDIA_VIDEO
                    )
                } != PackageManager.PERMISSION_GRANTED) {
                whatsActivity.let {
                    ActivityCompat.requestPermissions(
                        it, arrayOf(
                            Manifest.permission.READ_MEDIA_VIDEO,
                            Manifest.permission.READ_MEDIA_IMAGES
                        ), 101
                    )
                }
            } else {
                adapter = ViewPagerWhatsApp(
                    childFragmentManager,
                    Constant.WHATSAPP_IMAGE,
                    Constant.WHATSAPP_VIDEO,
                    Constant.WHATSAPP_IMAGE_R,
                    Constant.WHATSAPP_VIDEO_R,
                    2
                )

                list_view_pager.adapter = adapter
                Log.d("07/03", "WhatsAppsFragment----1")
            }
        } else {
            if (whatsActivity.let {
                    ContextCompat.checkSelfPermission(
                        it, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                } != PackageManager.PERMISSION_GRANTED || whatsActivity.let {
                    ContextCompat.checkSelfPermission(
                        it, Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                } != PackageManager.PERMISSION_GRANTED) {

                whatsActivity.let {
                    ActivityCompat.requestPermissions(
                        it, arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ), 101
                    )
                }
            } else {
                adapter = ViewPagerWhatsApp(
                    childFragmentManager,
                    Constant.WHATSAPP_IMAGE,
                    Constant.WHATSAPP_VIDEO,
                    Constant.WHATSAPP_IMAGE_R,
                    Constant.WHATSAPP_VIDEO_R,
                    2
                )

                list_view_pager.adapter = adapter
                Log.d("07/03", "WhatsAppsFragment----2")
            }
        }

        return binding.root
    }

    private fun isPackageExisted(targetPackage: String): Boolean {
        val packages: List<ApplicationInfo>
        val pm: PackageManager = whatsActivity.packageManager!!
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
            if (whatsActivity.let {
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        it, permission
                    )
                }) {
                //denied
                Toast.makeText(
                    whatsActivity, "Permission required to continue", Toast.LENGTH_SHORT
                ).show()
            } else {
                if (whatsActivity.let {
                        ActivityCompat.checkSelfPermission(
                            it, permission
                        )
                    } == PackageManager.PERMISSION_GRANTED) {
                    adapter = ViewPagerWhatsApp(
                        childFragmentManager,
                        Constant.WHATSAPP_IMAGE,
                        Constant.WHATSAPP_VIDEO,
                        Constant.WHATSAPP_IMAGE_R,
                        Constant.WHATSAPP_VIDEO_R,
                        2
                    )

                    list_view_pager.adapter = adapter
                    Log.d("07/03", "WhatsAppsFragment----3")

                } else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", whatsActivity.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                    break
                }
            }
        }
    }


}