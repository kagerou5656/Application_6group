package com.example.application_6group;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification extends BroadcastReceiver {

    @Override   // データを受信した
    public void onReceive(Context context, Intent intent) {

        int requestCode = intent.getIntExtra("RequestCode",0);
        String statusChangeUser = intent.getStringExtra("StatusChangeUser");

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, requestCode, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT |
                                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "default";
        // app name
        String title = context.getString(R.string.app_name);
        String noti_title = "";
        String noti_text = "";
        String noti_button_text ="";

        if (requestCode == 1) {
            noti_title = context.getString(R.string.caution_notification_title);
            noti_text = context.getString(R.string.caution_notification_text);
            noti_button_text = context.getString(R.string.caution_noti_button_text);
        }
        else if (requestCode == 2) {
            noti_title = context.getString(R.string.dangerous_notification_title);
            noti_text = statusChangeUser + context.getString(R.string.dangerous_notification_text);
            noti_button_text = context.getString(R.string.dangerous_noti_button_text);
        }

        // Notification　Channel 設定
        NotificationChannel channel =
                new NotificationChannel(channelId, title,
                        NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(noti_text);



        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(android.R.drawable.btn_star)
                        .setContentTitle(noti_title)
                        .setContentText(noti_text)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .addAction(android.R.drawable.star_on,
                                noti_button_text,
                                pendingIntent);

        NotificationManagerCompat notificationManagerCompat
                = NotificationManagerCompat.from(context);

        // 通知
        notificationManagerCompat.notify(R.string.app_name, builder.build());

    }
}
