package com.example.mercury.reminder;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mercury on 28.08.2015.
 */
public class ToServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG","OnReceive");
        Intent toService = new Intent(context, MainActivity.class);
        context.startActivity(toService);
       /* Intent timeIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, timeIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
*/



    }
}
