package com.synergy.synergydhrishtiplus.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.synergy.synergydhrishtiplus.SynergyApplicationClass;
public class SharedPref {

    private static final String SHARED_PREF_NAME = "Synergy.synergy.usersharedpref";
    private static final String LOGIN_USER_ID = "LoginUserId";
    private static final String HARDWARE_DETAIL="HardwareList";
    private static final  String HARDWARE_CONNETION="Hardware_connection";
    private static final String TOKEN = "Synergytoken";

    private SharedPref() {
    }

    private static SharedPreferences getUserSharedPreferences() {
        return SynergyApplicationClass.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }



    public static void setLoginUserId(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(LOGIN_USER_ID, newValue);
        editor.apply();
    }

    public static String getLoginUserId() {
        return getUserSharedPreferences().getString(LOGIN_USER_ID, "");
    }

    public static void setHardwareDetail(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(HARDWARE_DETAIL, newValue);
        editor.apply();
    }

    public static void setSocketConnnectionDetail(String newValue) {
        final SharedPreferences.Editor editor = getUserSharedPreferences().edit();
        editor.putString(HARDWARE_CONNETION, newValue);
        editor.apply();
    }
    public static String getSocketConnnectionDetail() {
        return getUserSharedPreferences().getString(HARDWARE_CONNETION, "");
    }

    public static String getHardwareDetail() {
        return getUserSharedPreferences().getString(HARDWARE_DETAIL, "");
    }



   /* public static void putInt(Context mContext, String key, int value) {
        //    getInstance(mContext);
        // mEditor = S.edit();
        getUserSharedPreferences().edit().putInt(key, value);
        getUserSharedPreferences().edit().commit();
    }
    public static String getAllProductPrice() {
        return getUserSharedPreferences().getString(ALL_PRODUCT_PRICE, "");
    }*/



}
