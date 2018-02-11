package com.uncommon.jonat.listenhear;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

//import com.firebase.Constraint;
//import com.firebase.jobdispatcher.FirebaseJobDispatcher;
//import com.firebase.jobdispatcher.GooglePlayDriver;
//import com.firebase.jobdispatcher.Job;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
/**
 * Created by jonat on 2/10/2018.
 */

public class ListenService extends FirebaseMessagingService {
    private static final String TAG = "ListenService";
    private static final long VIBRATE_PATTERN[] = {0, 250, 250, 250};

    private static final ArrayList<RemoteMessage> MESSAGE_QUEUE = new ArrayList<>();

    public static final boolean isEmpty() {
        return MESSAGE_QUEUE.isEmpty();
    }

    public static final RemoteMessage pop() {
        return MESSAGE_QUEUE.remove(MESSAGE_QUEUE.size() - 1);
    }

    //class variables
    private final String mId = "id_listenhear";
    private final CharSequence mName= "ListenHear Notifications";
    private final String mDescription = "Notifications on local sounds that ListenHear detects.";//getApplicationContext().getString(R.string.notification_channel_descript);
    private NotificationChannel mChannel;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Creating ListenService...");
        if (VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "onCreate: Build version O");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            mChannel = new NotificationChannel(mId  , mName, importance);
            mChannel.setDescription(mDescription);
            mChannel.setVibrationPattern(VIBRATE_PATTERN);
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    public static final String MSG_TOKENS[] = {
            "doorbell",
            "car horn",
            "baby cry"
    };

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: From " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "onMessaged Received: Data available.");
            //handle data
        }

        MESSAGE_QUEUE.add(remoteMessage);
        RemoteMessage.Notification nt = remoteMessage.getNotification();
        if (nt != null) {
            //Log.d(TAG, "onMessageReceived: message notification body: " + remoteMessage.getNotification().getBody());
            createNotification(nt.getBody());
        }
        //createNotification(remoteMessage.getNotification().getBody());
    }


    private void createNotification(String messageBody) {


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getApplicationContext().getString(R.string.app_name))
                .setContentText(messageBody)
                .setVibrate(VIBRATE_PATTERN)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }
}
