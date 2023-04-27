package com.example.allvideodownloaderrevolt.frag

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.allvideodownloaderrevolt.R
import com.example.allvideodownloaderrevolt.act.VideoDownloaderAct
import com.example.allvideodownloaderrevolt.act.VideoPlayerAct
import com.example.allvideodownloaderrevolt.act.WhatsAppAct
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.FragHomeBinding

class HomeFrag : Fragment() {

    private lateinit var homeActivity: Activity
    lateinit var binding: FragHomeBinding

    @SuppressLint("InlinedApi")
    var pemission = arrayOf(
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragHomeBinding.inflate(layoutInflater)
        homeActivity = requireActivity()
        homeActivity.let { Utils.setStatusBarBlueGradientActivity(it) }
        initClick()
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initClick() {
//        binding.llVideoCall.setOnClickListener {
//            Utils.displayInter(homeActivity, {
//                startActivity(Intent(homeActivity, SelectRoomActivity::class.java))
//            }, true)
//        }

        binding.layoutSaveStatus.setOnClickListener {

            if (isPackageExisted(Constant.WHATSAPP_PACKAGE_PATH)) {
                if (Build.VERSION.SDK_INT > 31) {
                    if (ContextCompat.checkSelfPermission(
                            homeActivity,
                            Manifest.permission.READ_MEDIA_VIDEO
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            homeActivity,
                            pemission,
                            101
                        )
                    } else {
                        Utils.displayInter(homeActivity, {
                            startActivity(Intent(homeActivity, WhatsAppAct::class.java))
                        }, true)
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(
                            homeActivity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(
                            homeActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            homeActivity,
                            pemission,
                            101
                        )
                    } else {
                        Utils.displayInter(homeActivity, {
                            startActivity(Intent(homeActivity, WhatsAppAct::class.java))
                        }, true)
                    }
                }
            } else {
                Toast.makeText(
                    homeActivity,
                    homeActivity.resources.getString(R.string.app_not_installed2),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.layoutVideoPlayerHome.setOnClickListener {
            Utils.displayInter(homeActivity, {
                startActivity(Intent(homeActivity, VideoPlayerAct::class.java))
            }, true)
        }

        binding.layoutVideoDownloader.setOnClickListener {
            Utils.displayInter(homeActivity, {
                startActivity(Intent(homeActivity, VideoDownloaderAct::class.java))
            }, true)
        }
    }

    override fun onStart() {
        super.onStart()
        homeActivity.let { Utils.loadInter(it) }
        homeActivity.let { Utils.loadBigNative(it) }
        homeActivity.let { Utils.loadSmallNative(it, 2) }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun isPackageExisted(packageName: String): Boolean {
        val packages: List<ApplicationInfo>
        val pm: PackageManager = homeActivity.packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == packageName) return true
        }
        return false
    }

}