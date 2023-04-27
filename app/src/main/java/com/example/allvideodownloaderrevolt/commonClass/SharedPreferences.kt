package com.example.allvideodownloaderrevolt.commonClass

import android.content.Context
import android.content.SharedPreferences
import com.androidads.adsdemo.model.AdPriority
import com.example.allvideodownloaderrevolt.AVDApplicationClass
import com.google.gson.Gson

object SharedPreferences {
    const val isVPNSelect = "isVPNSelect"
    var base_rate = "base_rate"
    const val PREDICTION_OBJ = "PREDICTION_OBJ"
    const val IsFirstTimeAppOpenAd = "IsFirstTimeAppOpenAd"
    const val AdvertiseObject = "AdvertiseObject"
    const val AdvertiseObject2 = "AdvertiseObject2"
    const val AdsPriority = "AdsPriority"
    private fun get(): SharedPreferences {
        return AVDApplicationClass.application
            ?.getSharedPreferences("ad_pref", Context.MODE_PRIVATE)!!
    }

    fun getStringName(Key: String?): String? {
        return get().getString(Key, "")
    }

    fun getStringNameNew(Key: String?): String? {
        return get().getString(Key, "")
    }

    fun setStringName(Key: String?, Value: String?) {
        get().edit().putString(Key, Value).apply()
    }

    fun getBoolenValue(Key: String): Boolean {
        return get().getBoolean(
            Key, false
        )
    }

    fun setBoolenValue(Key: String, TrueorFalse: Boolean) {
        get().edit().putBoolean(
            Key, TrueorFalse
        ).apply()
    }

    fun getLng(Key: String?): String? {
        return get().getString(Key, "")
    }

    fun SetLngl(Key: String?, Value: String?) {
        get().edit().putString(Key, Value).apply()
    }

    fun setInteger(Key: String, clickval: Int) {
        get().edit().putInt(
            Key, clickval
        ).apply()
    }

    fun getInteger(Key: String?): Int {
        return get().getInt(Key, 0)
    }

    fun SETSAVEADSID(Key: String?, Value: String?) {
        get().edit().putString(Key, Value).apply()
    }

    fun GETDENAME(Key: String?): String? {
        return get().getString(Key, "")
    }

    fun SETDEVNAME(Key: String?, Value: String?) {
        get().edit().putString(Key, Value).apply()
    }

    fun GETSAVEADSID(Key: String?): String? {
        return get().getString(Key, "")
    }

    val advertiseModel: AdPriority?
        get() {
            try {
                val gson = Gson()
                val json = get().getString(AdvertiseObject, "")
                return gson.fromJson(json, AdPriority::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    fun setAdvertiseModel(res: AdPriority?): Boolean {
        val gson = Gson()
        val json = gson.toJson(res)
        return get().edit()
            .putString(AdvertiseObject, json)
            .commit()
    }
}