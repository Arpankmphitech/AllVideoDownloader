package com.example.allvideodownloaderrevolt.commonClass;

import android.util.Log;

public class JavaHelper {
    public static Long getTweetId(String str) {
        try {
            return Long.valueOf(/*Long.parseLong(*/str.split("\\/")[5].split("\\?")[0]/*)*/);
        } catch (Exception e) {
            Log.d("TAG", "getTweetId: " + e.getLocalizedMessage());
            return null;
        }
    }
}
