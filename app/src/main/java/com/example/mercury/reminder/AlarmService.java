package com.example.mercury.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;

/**
 * Created by mercury on 28.08.2015.
 */
public class AlarmService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("???????????");
        builder.setContentText("?? ??? ????? ???? ?????????? ???????????");
        builder.setTicker("????? ??? - ?? ???????").setWhen(System.currentTimeMillis());
        builder.setContentIntent(contentIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(null,R.drawable.notification_template_icon_bg));
        builder.setSmallIcon(R.drawable.notification_template_icon_bg);
        builder.setAutoCancel(true);
        builder.build();*/
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
