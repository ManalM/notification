package com.example.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

class NotificationClass extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

     /*   NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeat_intent = new Intent(context , Repeat.class);
        repeat_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,repeat_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setContentTitle("Hello")
                .setContentText("this is me")
                .setAutoCancel(true);

        notificationManager.notify(100 , builder.build());

*/
        /*tring message = intent.getStringExtra("toastMessage");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();*/
        Intent repeat_intent = new Intent(context , Repeat.class);
        repeat_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,repeat_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(context, "channel1")
                .setContentIntent(pendingIntent)
                .setContentTitle("title")
                .setContentText("how are you?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, notification);
    }
}
