package com.synergy.synergydhrishtiplus;

import android.app.Application;
import android.content.Context;

import com.koushikdutta.async.AsyncSocket;
import java.util.HashMap;

public class DPApplication extends Application {
     HashMap<Integer, AsyncSocket> socketHashMap = new HashMap<>();

    private static Context context;

    public void onCreate() {
        super.onCreate();
        DPApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return DPApplication.context;
    }


}
