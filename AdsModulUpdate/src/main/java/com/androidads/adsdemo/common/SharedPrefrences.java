package com.androidads.adsdemo.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.androidads.adsdemo.R;
import com.androidads.adsdemo.model.AdPriority;
import com.androidads.adsdemo.model.SplashCommonModel;
import com.google.gson.Gson;


public class SharedPrefrences {
    public static final String AdvertiseObject = "AdvertiseObject";
    public static final String IsFirstTimeAppOpenAd = "IsFirstTimeAppOpenAd";
    public static String AD_APP_OPEN = "AD_APP_OPEN";
    private static android.content.SharedPreferences sharedPreference;

    public static android.content.SharedPreferences get(Activity activity) {
        return activity.getSharedPreferences("ad_pref", Context.MODE_PRIVATE);
    }

    public static String getStringName(Activity activity, String Key) {
        return get(activity).getString(Key, "");
    }

    public static String getStringNameNew(Activity activity, String Key) {
        return get(activity).getString(Key, "");
    }

    public static void setStringName(Activity activity, String Key, String Value) {
        get(activity).edit().putString(Key, Value).apply();
    }

    public static boolean getBoolenValue(Activity activity, String Key) {
        return get(activity).getBoolean(String.valueOf(Key), false);
    }

    public static void setBoolenValue(Activity activity, String Key, boolean TrueorFalse) {
        get(activity).edit().putBoolean(String.valueOf(Key), TrueorFalse).apply();
    }

    public static String getLng(Activity activity, String Key) {
        return get(activity).getString(Key, "");
    }

    public static void SetLngl(Activity activity, String Key, String Value) {
        get(activity).edit().putString(Key, Value).apply();
    }

    public static void setInteger(Activity activity, String Key, int clickval) {
        get(activity).edit().putInt(String.valueOf(Key), clickval).apply();
    }

    public static int getInteger(Activity activity, String Key) {
        return get(activity).getInt(Key, 0);
    }

    public static void setLong(Activity activity, String Key, Long clickval) {
        get(activity).edit().putLong(String.valueOf(Key), clickval).apply();
    }

    public static long getLong(Activity activity, String Key) {
        return get(activity).getLong(Key, 0);
    }


    public static void SETSAVEADSID(Activity activity, String Key, String Value) {
        get(activity).edit().putString(Key, Value).apply();
    }

    public static String GETDENAME(Activity activity, String Key) {
        return get(activity).getString(Key, "");
    }

    public static void SETDEVNAME(Activity activity, String Key, String Value) {
        get(activity).edit().putString(Key, Value).apply();
    }

    public static String GETSAVEADSID(Activity activity, String Key) {
        return get(activity).getString(Key, "");
    }

    public static AdPriority getAdvertiseModel(Activity activity) {
        try {
            Gson gson = new Gson();
            String json = get(activity).getString(AdvertiseObject, "");
            Log.d("19/12","jspn---"+json);
            return gson.fromJson(json, AdPriority.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean setAdvertiseModel(Activity activity, SplashCommonModel res) {
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return get(activity).edit()
                .putString(AdvertiseObject, json)
                .commit();
    }

}
