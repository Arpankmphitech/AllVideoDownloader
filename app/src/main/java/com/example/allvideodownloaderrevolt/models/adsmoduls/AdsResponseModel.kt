package com.video.downloader.free.allvideo.media.download.social.videos.hd.models.adsmodule

import com.androidads.adsdemo.model.AdPriority
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AdsResponseModel : Serializable {
    @SerializedName("data")
    @Expose
    var dataModel: DataModel? = null

    @SerializedName("package_data")
    @Expose
    var packageData: List<Any>? = null

    @SerializedName("ResponseCode")
    @Expose
    var responseCode: Int? = null

    @SerializedName("ResponseMsg")
    @Expose
    var responseMsg: String? = null

    @SerializedName("Result")
    @Expose
    var result: String? = null

    @SerializedName("ServerTime")
    @Expose
    var serverTime: String? = null

    @SerializedName("ad_priority")
    @Expose
    var adPriority: AdPriority? = null
}