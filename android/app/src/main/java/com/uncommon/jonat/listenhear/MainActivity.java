package com.uncommon.jonat.listenhear;

import android.*;
import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static Boolean inBackground = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //numOfMissedMessages = getString(R.string.num_of_missed_messages);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        if(savedInstanceState == null) {
            checkPermissions();
            logSavedToken();
            //updateRegistration();
            addFragment(new ViewMessageFragment());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private final int REQUEST_VIBRATE = 3249;
    private void checkPermissions() {
        int check = ContextCompat.checkSelfPermission(this,
                Manifest.permission.VIBRATE);

        if(check != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.VIBRATE)) {/**
                Snackbar.make(coordinatorLayout , R.string.reason_need_vibrate,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        REQUEST_VALIDATE);
                            }
                        })
                        .show();
             **/
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.VIBRATE},
                        REQUEST_VIBRATE);
            }
        }

    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch(requestCode) {
            case REQUEST_VIBRATE:
                Log.d(TAG, "onRequestPermissionsResult: received response for requesting validate.");
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_messages:
                swapFragment(new ViewMessageFragment());
                return true;
            case R.id.action_settings:
                swapFragment( new SettingFragment());
                return true;
            case R.id.action_clips:
                swapFragment( new ClipFragment());
                return true;
            case R.id.action_upload:
                swapFragment( new FileFragment());
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);

    }

    private void logSavedToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String token = prefs.getString(ListenIDService.FIREBASE_TOKEN, null);
        if(token != null)
        {
            Log.d(TAG, "Token: " + token);
        }
    }

    public FragmentManager fm = getSupportFragmentManager();
    private void swapFragment(Fragment f) {
        fm.beginTransaction().replace(R.id.fragment_container, f  ).addToBackStack(null).commit();
    }
    public void addFragment(Fragment f) {
        fm.beginTransaction().add(R.id.fragment_container, f).commit();
    }
}
