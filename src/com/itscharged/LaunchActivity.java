package com.itscharged;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LaunchActivity extends Activity {
    static final String TAG = "LaunchActivity";
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Log.d(TAG, "Launch Activity created.");
        Intent serviceIntent = new Intent(getBaseContext(),
                ItsChargedService.class);
        stopService(serviceIntent);
        startService(serviceIntent);

        text = (TextView) findViewById(R.id.textView1);
        ItsChargedReceiver.mActivity = this;

        Toast.makeText(this,
                "Service is started. You may close this application now!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
