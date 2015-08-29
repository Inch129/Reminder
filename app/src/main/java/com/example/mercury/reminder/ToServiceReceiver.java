package com.example.mercury.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ToServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG", "OnReceive");
        //достаем введенные пользователем данные
        String t = intent.getExtras().getString("заголовок");
        String d = intent.getExtras().getString("описание");

        //при нажатии нотификации эксплисит переходим в приложение
        Intent myIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_ONE_SHOT);

        //сама нотификация
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(t);
        builder.setContentText(d);
        builder.setTicker("Напоминание").setWhen(System.currentTimeMillis());
        builder.setLargeIcon(BitmapFactory.decodeResource(null, R.drawable.notification_template_icon_bg));
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.notification_template_icon_bg);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }
}
