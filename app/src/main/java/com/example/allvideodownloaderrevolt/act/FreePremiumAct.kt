package com.example.allvideodownloaderrevolt.act

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.os.storage.StorageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.documentfile.provider.DocumentFile
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActFreePremiumBinding
import com.example.allvideodownloaderrevolt.modelsClass.StatusModel
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class FreePremiumAct : BaseAct() {

    lateinit var actFreePreBinding: ActFreePremiumBinding
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actFreePreBinding = ActFreePremiumBinding.inflate(layoutInflater)
        setContentView(actFreePreBinding.root)
        activity = this@FreePremiumAct
        Utils.setStatusBarBlueGradientActivity(activity as FreePremiumAct)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S || "S" == Build.VERSION.CODENAME) {
            // Android 12 or Android 12 Beta
        }
        if (isAppInstalled(applicationContext, "com.whatsapp")) {
            position++
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            if (position == 2) {
                if (contentResolver.persistedUriPermissions.isNotEmpty()) {

                    val fromTreeUri = DocumentFile.fromTreeUri(
                        applicationContext,
                        contentResolver.persistedUriPermissions[1].uri
                    )

                    val listFiles = fromTreeUri?.listFiles()
                    val videoList: java.util.ArrayList<StatusModel> = java.util.ArrayList()
                    val imageList: java.util.ArrayList<StatusModel> = java.util.ArrayList()
                    for (documentFile: DocumentFile in listFiles!!) {
                        var status = StatusModel(documentFile)
                        if (
                            status.isVideo
                        ) {
                            videoList.add(status)
                        }
                        if (status.isImage) {
                            imageList.add(status)
                        }
                    }
                    Constant.WHATSAPP_VIDEO_R = videoList
                    Constant.WHATSAPP_IMAGE_R = imageList
                }

            } else {
                if (isAppInstalled(applicationContext, "com.whatsapp")) {
                    if (contentResolver.persistedUriPermissions.isNotEmpty()) {

                        val fromTreeUri = DocumentFile.fromTreeUri(
                            applicationContext,
                            contentResolver.persistedUriPermissions[0].uri
                        )

                        val listFiles = fromTreeUri?.listFiles()
                        val videoList: java.util.ArrayList<StatusModel> = java.util.ArrayList()
                        val imageList: java.util.ArrayList<StatusModel> = java.util.ArrayList()
                        for (documentFile: DocumentFile in listFiles!!) {
                            var status = StatusModel(documentFile)
                            if (
                                status.isVideo
                            ) {
                                videoList.add(status)
                            }
                            if (status.isImage) {
                                imageList.add(status)
                            }
                        }
                        Constant.WHATSAPP_VIDEO_R = videoList
                        Constant.WHATSAPP_IMAGE_R = imageList
                    }
                }
            }
        }
        permReqLauncher.launch(
            PERMISSIONS
        )

        initViewFreePrem()
    }

    companion object {
        var PERMISSIONS = arrayOf(
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.POST_NOTIFICATIONS,
        )
    }

    // New Changes WhatsApp and Bussiness Problem
    private fun isAppInstalled(context: Context, packageName: String?): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName!!, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            if (granted) {

            } else {
                permissionFun()
            }
        }

    private fun permissionFun() {
        if (Build.VERSION.SDK_INT > 31) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_VIDEO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_AUDIO
                    ),
                    101
                )
            } else {
                GetData().execute()
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    101
                )
            } else {
                GetData().execute()
            }
            //New changes
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                if (position == 2) {
                    if (contentResolver.persistedUriPermissions.isNotEmpty()) {

                        val fromTreeUri = DocumentFile.fromTreeUri(
                            applicationContext,
                            contentResolver.persistedUriPermissions[1].uri
                        )

                        val listFiles = fromTreeUri?.listFiles()
                        val videoList: java.util.ArrayList<StatusModel> = java.util.ArrayList()
                        val imageList: java.util.ArrayList<StatusModel> = java.util.ArrayList()
                        for (documentFile: DocumentFile in listFiles!!) {
                            var status = StatusModel(documentFile)
                            if (
                                status.isVideo
                            ) {
                                videoList.add(status)
                            }
                            if (status.isImage) {
                                imageList.add(status)
                            }
                        }
                        Constant.WHATSAPP_VIDEO_R = videoList
                        Constant.WHATSAPP_IMAGE_R = imageList
                    }


                } else {
                    if (isAppInstalled(applicationContext, "com.whatsapp")) {
                        if (contentResolver.persistedUriPermissions.isNotEmpty()) {

                            val fromTreeUri = DocumentFile.fromTreeUri(
                                applicationContext,
                                contentResolver.persistedUriPermissions[0].uri
                            )

                            val listFiles = fromTreeUri?.listFiles()
                            val videoList: java.util.ArrayList<StatusModel> = java.util.ArrayList()
                            val imageList: java.util.ArrayList<StatusModel> = java.util.ArrayList()
                            for (documentFile: DocumentFile in listFiles!!) {
                                var status = StatusModel(documentFile)
                                if (
                                    status.isVideo
                                ) {
                                    videoList.add(status)
                                }
                                if (status.isImage) {
                                    imageList.add(status)
                                }
                            }
                            Constant.WHATSAPP_VIDEO_R = videoList
                            Constant.WHATSAPP_IMAGE_R = imageList
                        }
                    }
                }
            }
            //End
        }
    }

    private fun initViewFreePrem() {
        actFreePreBinding.allFreeLayout.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {

                if (arePermissionDenied(position)) {
                    if (position == 2) {
                        requestPermissionQWhatsApp()
                    } else {
                        if (isAppInstalled(applicationContext, "com.whatsapp")) {
                            requestPermissionQWhatsApp()
                        }
                    }
                    //End

                } else
                    if (Build.VERSION.SDK_INT > 31) {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.READ_MEDIA_VIDEO
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(
                                    Manifest.permission.READ_MEDIA_VIDEO,
                                    Manifest.permission.READ_MEDIA_IMAGES,
                                    Manifest.permission.READ_MEDIA_AUDIO
                                ),
                                102
                            )
                        } else {
                            Utils.displayInter(activity, {
                                move(HomeAct::class.java, 1)
                            }, true)
                        }
                    } else {

                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ),
                                102
                            )
                        } else {
                            Utils.displayInter(activity, {
                                move(HomeAct::class.java, 1)
                            }, true)
                        }
                    }
            } else {
                if (Build.VERSION.SDK_INT > 31) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_MEDIA_VIDEO
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.READ_MEDIA_VIDEO,
                                Manifest.permission.READ_MEDIA_IMAGES
                            ),
                            102
                        )
                    } else {
                        Utils.displayInter(activity, {
                            move(HomeAct::class.java, 1)
                        }, true)
                    }
                } else {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ),
                            102
                        )
                    } else {
                        Utils.displayInter(activity, {
                            move(HomeAct::class.java, 1)
                        }, true)
                    }
                }
            }
        }

        actFreePreBinding.allPremiumLayout.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {

                if (arePermissionDenied(position)) {
                    if (position == 2) {
                        requestPermissionQWhatsApp()
                    } else {
                        if (isAppInstalled(applicationContext, "com.whatsapp")) {
                            requestPermissionQWhatsApp()
                        }
                    }
                    //End

                } else
                    if (Build.VERSION.SDK_INT > 31) {
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.READ_MEDIA_VIDEO
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(
                                    Manifest.permission.READ_MEDIA_VIDEO,
                                    Manifest.permission.READ_MEDIA_IMAGES
                                ),
                                102
                            )
                        } else {
                            Utils.displayInter(activity, {
                                move(HomeAct::class.java, 1)
                            }, true)
                        }
                    } else {

                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ),
                                102
                            )
                        } else {
                            Utils.displayInter(activity, {
                                move(HomeAct::class.java, 1)
                            }, true)
                        }
                    }
            } else {
                if (Build.VERSION.SDK_INT > 31) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_MEDIA_VIDEO
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.READ_MEDIA_VIDEO,
                                Manifest.permission.READ_MEDIA_IMAGES,
                                Manifest.permission.READ_MEDIA_AUDIO
                            ),
                            102

                        )
                    } else {
                        Utils.displayInter(activity, {
                            move(HomeAct::class.java, 1)
                        }, true)
                    }
                } else {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ),
                            102
                        )
                    } else {
                        Utils.displayInter(activity, {
                            move(HomeAct::class.java, 1)
                        }, true)
                    }
                }
            }
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent = result.data!!
                if (data.data.toString() == "content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses") {
                    contentResolver.takePersistableUriPermission(
                        data.data!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                        if (contentResolver.persistedUriPermissions.isNotEmpty()) {

                            val fromTreeUri = DocumentFile.fromTreeUri(
                                applicationContext,
                                contentResolver.persistedUriPermissions[0].uri
                            )

                            val listFiles = fromTreeUri?.listFiles()
                            val videoList: ArrayList<StatusModel> = ArrayList()
                            val imageList: ArrayList<StatusModel> = ArrayList()
                            for (documentFile: DocumentFile in listFiles!!) {
                                var statusModel = StatusModel(documentFile)
                                if (
                                    statusModel.isVideo
                                ) {
                                    videoList.add(statusModel)
                                }
                                if (statusModel.isImage) {
                                    imageList.add(statusModel)
                                }
                            }
                            Constant.WHATSAPP_VIDEO_R = videoList
                            Constant.WHATSAPP_IMAGE_R = imageList
                        }
                    }
                } else if (Build.VERSION.SDK_INT < 29 || !Constant.STATUS_DIRECTORY_NEW.exists()) {
                } else {
                    requestPermissionQ()
                }
            }
        }


    // New Changes WhatsApp and Bussiness Problem
    private fun arePermissionDenied(a: Int): Boolean {
        if (Constant.STATUS_DIRECTORY_NEW.exists()) {
            return contentResolver.persistedUriPermissions.size <= (a - 1)
        }
        return false
    }

    @SuppressLint("WrongConstant", "ShowToast")
    private fun requestPermissionQ() {
        val createOpenDocumentTreeIntent: Intent =
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                (getSystemService(Context.STORAGE_SERVICE) as StorageManager).primaryStorageVolume
                    .createOpenDocumentTreeIntent()
            } else {
                TODO("VERSION.SDK_INT < Q")
            }
        var uri =
            (createOpenDocumentTreeIntent.getParcelableExtra<Parcelable>("android.provider.extra.INITIAL_URI") as Uri?)!!
        val replace = uri.toString().replace("/root/", "/document/")
        val parse =
            Uri.parse("$replace%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses")
        uri = parse
        Log.d("URI", parse.toString())
        createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", uri)
        createOpenDocumentTreeIntent.flags = 67
        activityResultLauncher.launch(createOpenDocumentTreeIntent)
        Toast.makeText(applicationContext, "Click 'Use this Folder' to allow!", Toast.LENGTH_SHORT)
            .show()
    }

    class GetData : AsyncTask<Unit, Unit, String>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Unit?): String? {
            Constant.WHATSAPP_IMAGE = getWhatsAppImage()
            Constant.WHATSAPP_VIDEO = getWhatsAppVideo()
            return null
        }

        @Deprecated(
            "Deprecated in Java",
            ReplaceWith("super.onPostExecute(result)", "android.os.AsyncTask")
        )
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

        }

        private fun getWhatsAppImage(): ArrayList<String?> {
            val list: ArrayList<String?> = java.util.ArrayList()
            var file = File(
                Environment.getExternalStorageDirectory()
                    .toString() + Constant.WHATSAPP_STATUS_FOLDER_PATH
            )
            if (!file.exists()) {
                file = File(
                    Environment.getExternalStorageDirectory()
                        .toString() + Constant.WHATSAPP_STATUS_FOLDER_PATH2
                )
            }
            val listFile = file.listFiles()

            if (listFile != null && listFile.isNullOrEmpty()) {
                Arrays.sort(listFile)
            }

            if (listFile != null) {
                for (imgFile in listFile) {
                    if (
                        imgFile.name.endsWith(".jpg")
                        || imgFile.name.endsWith(".jpeg")
                        || imgFile.name.endsWith(".png")
                    ) {
                        val model = imgFile.absolutePath
                        list.add(model)
                    }
                }
            }
            return list
        }

        private fun getWhatsAppVideo(): ArrayList<String?> {
            val list: ArrayList<String?> = ArrayList()

            var file = File(
                Environment.getExternalStorageDirectory()
                    .toString() + Constant.WHATSAPP_STATUS_FOLDER_PATH
            )

            if (!file.exists()) {
                file = File(
                    Environment.getExternalStorageDirectory()
                        .toString() + Constant.WHATSAPP_STATUS_FOLDER_PATH2
                )
            }
            val listFile = file.listFiles()

            if (listFile != null && listFile.isNullOrEmpty()) {
                Arrays.sort(listFile)
            }

            if (listFile != null) {
                for (videoFile in listFile) {
                    if (
                        videoFile.name.endsWith(".mp4")
                        || videoFile.name.endsWith(".mkv")
                        || videoFile.name.endsWith(".avi")
                        || videoFile.name.endsWith(".mov")
                    ) {
                        val model = videoFile.absolutePath
                        list.add(model)
                    }
                }
            }
            return list
        }
    }

    @SuppressLint("WrongConstant")
    private fun requestPermissionQWhatsApp() {
        val createOpenDocumentTreeIntent: Intent =
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                (getSystemService(Context.STORAGE_SERVICE) as StorageManager).primaryStorageVolume
                    .createOpenDocumentTreeIntent()
            } else {
                TODO("VERSION.SDK_INT < Q")
            }
        var uri =
            (createOpenDocumentTreeIntent.getParcelableExtra<Parcelable>("android.provider.extra.INITIAL_URI") as Uri?)!!
        uri = uri
        val replace = uri.toString().replace("/root/", "/document/")
        val parse =
            Uri.parse("$replace%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses")
        uri = parse
        Log.d("URI", parse.toString())
        createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", uri)
        createOpenDocumentTreeIntent.flags = 67
        activityResultLauncher.launch(createOpenDocumentTreeIntent)
        Toast.makeText(applicationContext, "Click 'Use this Folder' to allow!", 0).show()
    }

    override fun onStart() {
        super.onStart()
        Utils.loadInter(this)
        Utils.loadSmallNative(this, 2)
    }

    override fun onBackPressed() {
        finish()
    }


}