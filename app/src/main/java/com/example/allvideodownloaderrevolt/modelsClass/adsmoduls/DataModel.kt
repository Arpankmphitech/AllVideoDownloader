package com.video.downloader.free.allvideo.media.download.social.videos.hd.models.adsmodule

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataModel {
    @SerializedName("home")
    @Expose
    var home: List<Any>? = null

    @SerializedName("exit")
    @Expose
    var exit: List<Any>? = null

    @SerializedName("developer_name")
    @Expose
    var developerName: String? = null

    @SerializedName("is_extra_native")
    @Expose
    var is_extra_native: String? = null

    @SerializedName("isStaticVideo")
    @Expose
    var isStaticVideo: String? = null

    @SerializedName("is_ad_dialog")
    @Expose
    var isAdDialog: Boolean? = null

    @SerializedName("ad_dialog_time")
    @Expose
    var adDialogTime: String? = null

    @SerializedName("is_rate_dialog")
    @Expose
    var isRateDialog: Boolean? = null

    @SerializedName("is_user_dialog")
    @Expose
    var isUserDialog: Boolean? = null

    @SerializedName("user_dialog_text")
    @Expose
    var userDialogText: String? = null

    @SerializedName("user_dialog_url")
    @Expose
    var userDialogUrl: String? = null

    @SerializedName("app_version_code")
    @Expose
    var appVersionCode: String? = null

    @SerializedName("user_dialog_update_text")
    @Expose
    var userDialogUpdateText: String? = null

    @SerializedName("google_id")
    @Expose
    var googleId: String? = null

    @SerializedName("bitcoin_url")
    @Expose
    var bitcoinUrl: String? = null

    @SerializedName("quereka_id")
    @Expose
    var qurekaId: String? = null

    @SerializedName("is_info_available")
    @Expose
    var isInfo: String? = null

    @SerializedName("is_splash_ad")
    @Expose
    var isSplashAd: String? = null

    @SerializedName("predchamp_url")
    @Expose
    var predchampUrl: String? = null

    @SerializedName("game_url")
    @Expose
    var gameUrl: String? = null

    @SerializedName("back_press_count")
    @Expose
    var isBackPressCount: String? = null

    @SerializedName("normal_ad_count")
    @Expose
    var isAdNormalCount: String? = null

    @SerializedName("is_available_advertise")
    @Expose
    var isAdvertiseAvailable: String? = null

    @SerializedName("app_url")
    @Expose
    var appUrl: String? = null

    @SerializedName("app_node_url")
    @Expose
    var appNodeUrl: String? = null

    @SerializedName("is_play_store")
    @Expose
    var isPlayStore: String? = null

    @SerializedName("usr_dialog_btn")
    @Expose
    var usrDialogBtn: String? = null

    @SerializedName("rate_text")
    @Expose
    var rateText: String? = null

    @SerializedName("user_dialog_img")
    @Expose
    var userDialogImg: String? = null

    @SerializedName("testFlag")
    @Expose
    var testFlag: String? = null
}