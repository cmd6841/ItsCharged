package com.itscharged;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class NotificationActivity extends Activity {
    Button button;
    static final String TAG = "NotificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notification);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (r != null) {
                    r.stop();
                    Log.d(TAG, "Ringtone stopped.");
                }
                if (audioManager != null) {
                    audioManager.setRingerMode(originalRingerMode);
                    audioManager.setStreamVolume(AudioManager.STREAM_ALARM,
                            originalVolume, AudioManager.FLAG_SHOW_UI);
                }
                finish();
            }
        });
        alert();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity destroyed.");
        if (r != null) {
            r.stop();
            Log.d(TAG, "Ringtone stopped.");
        }
        if (audioManager != null) {
            audioManager.setRingerMode(originalRingerMode);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM,
                    originalVolume, AudioManager.FLAG_SHOW_UI);
        }
    }

    Ringtone r;
    AudioManager audioManager;
    int originalRingerMode, originalVolume;

    public void alert() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        originalRingerMode = audioManager.getRingerMode();
        if (originalRingerMode != AudioManager.RINGER_MODE_NORMAL) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            originalVolume = audioManager
                    .getStreamVolume(AudioManager.STREAM_ALARM);
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM),
                    AudioManager.FLAG_SHOW_UI);
        }
        Uri notification = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(this, notification);
        r.play();
    }

    public void exit() {
        Toast.makeText(this, "Battery not charging. Application exitted!",
                Toast.LENGTH_LONG).show();
        finish();
    }
}
