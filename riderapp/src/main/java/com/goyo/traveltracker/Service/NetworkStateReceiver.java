package com.goyo.traveltracker.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.goyo.traveltracker.forms.all_order;

/**
 * Created by mis on 14-Jul-17.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    public static boolean IsMobailConnected=true;
    public static boolean IsWifiConnected=true;
    /*
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        int networkType = intent.getExtras().getInt(ConnectivityManager.EXTRA_NETWORK_TYPE);
        boolean isWiFi = networkType == ConnectivityManager.TYPE_WIFI;
        boolean isMobile = networkType == ConnectivityManager.TYPE_MOBILE;
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(networkType);
        boolean isConnected = networkInfo.isConnected();

        if (isWiFi) {
            if (isConnected) {
                Log.i("APP_TAG", "Wi-Fi - CONNECTED");
                IsWifiConnected=true;
            } else {
                Log.i("APP_TAG", "Wi-Fi - DISCONNECTED");
                IsWifiConnected=false;
            }
        } else if (isMobile) {
            if (isConnected) {
                all_order all_order=new all_order();
                all_order.SendOfflineTagstoServer();
                Log.i("APP_TAG", "Mobile - CONNECTED");
                IsMobailConnected=true;
            } else {
                Log.i("APP_TAG", "Mobile - DISCONNECTED");
                IsMobailConnected=false;
            }
        } else {
            if (isConnected) {
                Log.i("APP_TAG", networkInfo.getTypeName() + " - CONNECTED");
//                IsMobailConnected=true;
//                IsWifiConnected=true;
            } else {
                Log.i("APP_TAG", networkInfo.getTypeName() + " - DISCONNECTED");
//                IsMobailConnected=false;
//                IsWifiConnected=false;

            }
        }
    }

}
