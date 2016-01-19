package ufms.br.com.ufmsapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStateMonitor extends BroadcastReceiver {

    protected static boolean isConnected = false;

    public static boolean isConnected() {
        return isConnected;
    }

    public static void setIsConnected(boolean isConnected) {
        isConnected = isConnected;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();


        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            setIsConnected(true);

        } else {
            setIsConnected(false);
        }


    }
}