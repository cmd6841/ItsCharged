package com.itscharged;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ItsChargedBootReceiver extends BroadcastReceiver {

    static final String TAG = "ItsChargedBootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG,
                    "Received boot completed intent. Starting ItsChargedService now.");
            Intent serviceIntent = new Intent(context, ItsChargedService.class);
            context.stopService(serviceIntent);
            context.startService(serviceIntent);
        }
    }

}
