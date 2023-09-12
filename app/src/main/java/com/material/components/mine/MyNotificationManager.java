package com.material.components.mine;

import static android.app.NotificationManager.IMPORTANCE_HIGH;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.material.components.R;

import java.util.Calendar;

public class MyNotificationManager {

    private static MyNotificationManager instance = null;

    private MyNotificationManager() {
    }

    public static MyNotificationManager getInstance() {
        if (instance == null) {
            synchronized (MyNotificationManager.class) {
                if (instance == null) {
                    instance = new MyNotificationManager();
                }
            }
        }
        return instance;
    }


    public static final String CHANNEL_ID = "health_data_collection_app";

    public void createNotificationChannel(Application application){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "collect_data_hint", IMPORTANCE_HIGH);
            NotificationManager realNotificationManager = application.getSystemService(NotificationManager.class);
            realNotificationManager.createNotificationChannel(channel);
        }

    }

    public void showNotification(Context context, String title, String message, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
        Log.i("MyNotificationManager", "showNotification: ");
    }


    public void initNotification(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 设置为用户选择的时间
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 24);
        // 设置重复闹钟
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                60*1000, pendingIntent);

        Log.i("MenuDrawerNews", "initNotification: SUCCESS");
    }



}
