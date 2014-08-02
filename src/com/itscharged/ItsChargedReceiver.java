package com.itscharged;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class ItsChargedReceiver extends BroadcastReceiver {

    static final String TAG = "ItsChargedReceiver";
    Intent notificationIntent;
    Context mContext;
    static LaunchActivity mActivity;
    static boolean isStillCharging = true;

    public ItsChargedReceiver(Context context) {
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mContext != null)
            context = mContext;

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int batteryLevel = -1;
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL;

        if (mActivity != null) {
            try {
                int chargePlug = intent.getIntExtra(
                        BatteryManager.EXTRA_PLUGGED, -1);
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
                mActivity.text.setText("Current Battery Status:");
                if (usbCharge && mActivity != null) {
                    mActivity.text.append("\n- Charging via USB.");
                } else if (acCharge && mActivity != null) {
                    mActivity.text.append("\n- Charging via AC adapter.");
                } else {
                    mActivity.text.append("\n- Discharging.");
                }
                batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,
                        -1);
                if (mActivity != null) {
                    mActivity.text.append("\n- Current battery level: "
                            + batteryLevel + "%");
                }
            } catch (Exception e) {
                Log.d(TAG,
                        "Exception in broadcast receiver.\n" + e.getMessage());
            }
        }
        if (!isCharging) {
            if (isStillCharging)
                isStillCharging = false;
            // Log.d(TAG, "Charger removed.");
        } else {
            notificationIntent = new Intent(context, NotificationActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (status == BatteryManager.BATTERY_STATUS_FULL) {
                isStillCharging = true;
                Log.d(TAG, "It's charging.");
                context.startActivity(notificationIntent);
            }
        }
    }
}
