package com.androidads.adsdemo.common;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

public class Utils {

    public static void ShowTost(Context context, String ToastMessage) {
        Toast.makeText(context, ToastMessage, Toast.LENGTH_SHORT).show();
    }

    public static Boolean isValidatEmpty(String value) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")
                || value.equalsIgnoreCase("") || value.length() == 0
                || value.equalsIgnoreCase("NA")
                || value.equalsIgnoreCase("N/A")) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isCheckURLValid(String value) {
        if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")
                || value.equalsIgnoreCase("")
                || value.length() == 0
                || value.equalsIgnoreCase("NA")
                || value.equalsIgnoreCase("N/A")) {
            return false;
        } else {
            return Patterns.WEB_URL.matcher(value).matches();
        }
    }


    public static Boolean isValidaEmptyWithZero(String value) {
        if (value == null || value.isEmpty()
                || value.equalsIgnoreCase("null")
                || value.equalsIgnoreCase("")
                || value.equalsIgnoreCase("NA")
                || value.equalsIgnoreCase("N/A")
                || value.length() == 0 || value.equalsIgnoreCase("0")) {
            return true;
        } else {
            return false;
        }
    }

}
