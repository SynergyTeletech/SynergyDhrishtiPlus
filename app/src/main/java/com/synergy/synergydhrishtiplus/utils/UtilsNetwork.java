package com.synergy.synergydhrishtiplus.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.synergy.synergydhrishtiplus.network.ApiClient;
import com.synergy.synergydhrishtiplus.network.ApiInterface;


public class UtilsNetwork {

    static boolean isOnline = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

//        boolean isOnline = false;
//        try {
//            ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkCapabilities capabilities = null;  // need ACCESS_NETWORK_STATE permission
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                capabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());
//            }
//            isOnline = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
//            Log.e("kamalconnect",""+isOnline);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return isOnline;
//        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo nInfo = cm.getActiveNetworkInfo();
//      boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
//        return connected;

//        try {
//            InetAddress ipAddr = InetAddress.getByName("www.google.com");
//            //You can replace it with your name
//            return !ipAddr.equals("");
//
//        } catch (Exception e) {
//            Log.e("internetKamal","kamal");
//            return false;
//        }
    }

}
