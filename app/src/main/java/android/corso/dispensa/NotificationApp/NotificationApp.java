package android.corso.dispensa.NotificationApp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.corso.dispensa.Activity.ArticoliScaduti;
import android.corso.dispensa.R;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class NotificationApp {


    private Context CONTEXT;

    public NotificationApp(Context context) {
        this.CONTEXT = context;

    }


    public void getNotificationExpired() {
        Intent intent = new Intent(CONTEXT, ArticoliScaduti.class);
        intent.putExtra("today", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(CONTEXT, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilderExpired = new NotificationCompat.Builder(CONTEXT, "dispensa_channel")
                .setSmallIcon(R.drawable.apple16px)
                .setContentTitle("Prodotti scaduti!")
                .setContentText("Clicca per aprire la lista dei prodotti scaduti!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_background, "Lista prodotti scaduti", pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notificationChannelOne", "Prodotti Scaduti", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Canale delle notifiche dei Prodotti scaduti");

            /* Build notification Channel */
            ((NotificationManager) CONTEXT.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            notificationBuilderExpired.setChannelId(channel.getId());
        }

        /* Send */
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(CONTEXT);
        managerCompat.notify(1, notificationBuilderExpired.build());


    }

    public void getNotificationExpire() {
        PendingIntent pendingIntent = PendingIntent.getActivity(CONTEXT, 0, new Intent(CONTEXT, ArticoliScaduti.class), 0);

        NotificationCompat.Builder notificationBuilderExpired = new NotificationCompat.Builder(CONTEXT, "dispensa_channel")
                .setSmallIcon(R.drawable.apple16px)
                .setContentTitle("Questi prodotti stanno per scadere!")
                .setContentText("Clicca per aprire la lista dei prodotti che scadranno a breve!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_background, "Apri lista", pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel2 = new NotificationChannel("notificationChannelTwo", "Prodotti che scadranno", NotificationManager.IMPORTANCE_DEFAULT);
            channel2.setDescription("Canale delle notifiche dei Prodotti scaduti");

            /* Build notification Channel */
            ((NotificationManager) CONTEXT.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel2);
            notificationBuilderExpired.setChannelId(channel2.getId());
        }

        /* Send */
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(CONTEXT);
        managerCompat.notify(2, notificationBuilderExpired.build());


    }


}


