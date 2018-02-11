package com.uncommon.jonat.listenhear;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
/**
 * Created by jonat on 2/10/2018.
 */

public class ListenIDService extends FirebaseInstanceIdService {
    private static final String TAG = "ListenIDService";
    
    public static final String FIREBASE_TOKEN = "firebase_token";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Creating ListenIDService...");
    }

    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString(FIREBASE_TOKEN, refreshedToken).apply();
        sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        //TODO
    }
}
