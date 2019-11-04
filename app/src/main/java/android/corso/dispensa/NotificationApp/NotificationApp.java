package android.corso.dispensa.NotificationApp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.corso.dispensa.Activity.ArticoliScaduti;
import android.corso.dispensa.Logic.SharedPreferencesApp;
import android.corso.dispensa.R;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Objects;


public class NotificationApp {


    private Context CONTEXT;

    public NotificationApp(Context context) {
        this.CONTEXT = context;

    }

    public void SetNotification() {

        SharedPreferencesApp sharedPreferencesApp = new SharedPreferencesApp(CONTEXT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, sharedPreferencesApp.getHourPreferences());
        calendar.set(Calendar.MINUTE, sharedPreferencesApp.getMinutesPreferences() - 1);
        calendar.set(Calendar.SECOND, 1);


        Intent intentNotificationApp = new Intent(CONTEXT, NotificationApp.Notification_reciver.class);
        intentNotificationApp.setAction("MY_NOTIFICATION_MESSAGE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CONTEXT, 0, intentNotificationApp, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) CONTEXT.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }


    static public class Notification_reciver extends BroadcastReceiver {

        public Notification_reciver() {

        }

        @SuppressLint("UnsafeProtectedBroadcastReceiver")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), "MY_NOTIFICATION_MESSAGE")) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Intent intentGetNotificationExpired = new Intent(context, ArticoliScaduti.class);
                intentGetNotificationExpired.putExtra("today", true);
                intentGetNotificationExpired.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentGetNotificationExpired, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder notificationBuilderExpired = new NotificationCompat.Builder(context, "dispensa_channel")
                        .setSmallIcon(R.drawable.apple16px)
                        .setContentTitle("Prodotti scaduti!")
                        .setContentText("Clicca per aprire la lista dei prodotti scaduti!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_launcher_background, "Lista prodotti scaduti", pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("notificationChannelOne", "Prodotti Scaduti", NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("Canale delle notifiche dei Prodotti scaduti");

                    ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
                    notificationBuilderExpired.setChannelId(channel.getId());
                }

                notificationManager.notify(0, notificationBuilderExpired.build());
            }
        }
    }


}


