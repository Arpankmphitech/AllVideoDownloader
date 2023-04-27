package com.example.allvideodownloaderrevolt.commonClass

import android.os.Environment
import com.example.allvideodownloaderrevolt.modelsClass.StatusModel
import java.io.File

object Constant {
    var WHATSAPP_PACKAGE_PATH = "com.whatsapp"
    var WHATSAPP_STATUS_FOLDER_PATH = "/WhatsApp/Media/.Statuses/"
    var TINY_URL = "https://tinyurl.com/mty9um6v"
    var WHATSAPP_STATUS_FOLDER_PATH2 = "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses/"
    var trueDialog = true
    var isClickedNotification = false
    var Firstime = true
    var isRegistered = false
    var isAdNormalCount = 0
    var isAdBackCount = 0
    var OpenAppAdId: String? = ""
    const val TypePredChampInside: String = "0"
    var BigNative = 2
    var SmallNative = 1
    var forwardCount = 1
    var countBack = 1
    const val is_available_advertise = "is_available_advertise"
    const val COUNT_BACK_PRESS = "is_back_press_count"
    const val COUNT_NORMAL_AD = "is_normal_ad_count"
    var AD_APP_OPEN = "AD_APP_OPEN"
    const val TABLE_NAME_LOGIN = "MySharedPref"
    const val IS_INFO = "IS_INFO"
    const val IS_AD_SPLASH = "IS_AD_SPLASH"
    const val TEXT_USER_DIALOG = "TEXT_USER_DIALOG"
    const val IMG_USER_DIALOG = "IMG_USER_DIALOG"
    const val URL_USER_DIALOG = "URL_USER_DIALOG"
    const val   VERSION_CODE_App = "app_versionCode"
    const val IS_USER_DIALOG = "IS_USER_DIALOG"
    const val BTN_USER_DIALOG = "BTN_USER_DIALOG"
    var URL_PLAYSTORE = "https://play.google.com/store/apps/details?id="
    var NAME_OF_DEVELOPER = "Yashenam Inc"
    const val UPDATE_USER_DIALOG_TEXT = "UPDATE_USER_DIALOG_TEXT"
    const val ONE_SIGNAL = "81061f5d-5354-4cef-8dfa-69eb7abefeda"
    var WHATSAPP_IMAGE_R: ArrayList<StatusModel> = ArrayList()
    var WHATSAPP_VIDEO_R: ArrayList<StatusModel> = ArrayList()
    var WHATSAPP_IMAGE: ArrayList<String?> = ArrayList()
    var WHATSAPP_VIDEO: ArrayList<String?> = ArrayList()

    //    var FOLDER_NAME = ArrayList<folderFacer>()
    var STATUS_DIRECTORY_NEW =
        File(Environment.getExternalStorageDirectory().path + File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses")
    var WHATSAPP_VIDEO_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/Whatsapp/Videos/")
    var WHATSAPP_IMAGES_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/Whatsapp/Images/")
    var SHARE_CHAT_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/Share Chat/")
    var TWITTER_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/Twitter/")
    var FACEBOOK_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/Facebook/")
    var JOSH_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/Josh/")
    var INSTAGRAM_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/InstagramDwClass/")
    var CHINGARI_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/Chingari/")
    var TIKI_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/Tiki/")
    var ROPOSO_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Videos Downloader/Roposo/")
    var VIMEO_PATH =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/All Video Downloader/Vimeo/")

    init {
        System.loadLibrary("api_keys")
    }

    var isShown = false

    val encryptionKey: String? external get
    val twitterApi: String? external get
    val moreAppApi: String? external get
    val loginApi: String? external get
    val header: String? external get
    val token: String? external get

    fun createFolder() {
        if (!SHARE_CHAT_PATH.exists()) {
            SHARE_CHAT_PATH.mkdirs()
        }
        if (!TWITTER_PATH.exists()) {
            TWITTER_PATH.mkdirs()
        }
        if (!FACEBOOK_PATH.exists()) {
            FACEBOOK_PATH.mkdirs()
        }
        if (!JOSH_PATH.exists()) {
            JOSH_PATH.mkdirs()
        }
        if (!WHATSAPP_IMAGES_PATH.exists()) {
            WHATSAPP_IMAGES_PATH.mkdirs()
        }
        if (!WHATSAPP_VIDEO_PATH.exists()) {
            WHATSAPP_VIDEO_PATH.mkdirs()
        }
        if (!INSTAGRAM_PATH.exists()) {
            INSTAGRAM_PATH.mkdirs()
        }
        if (!CHINGARI_PATH.exists()) {
            CHINGARI_PATH.mkdirs()
        }
        if (!TIKI_PATH.exists()) {
            TIKI_PATH.mkdirs()
        }
        if (!ROPOSO_PATH.exists()) {
            ROPOSO_PATH.mkdirs()
        }
        if (!VIMEO_PATH.exists()) {
            VIMEO_PATH.mkdirs()
        }
    }
}
