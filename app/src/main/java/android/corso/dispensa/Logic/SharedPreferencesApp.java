package android.corso.dispensa.Logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.corso.dispensa.NotificationApp.NotificationApp;
import android.widget.Toast;

public class SharedPreferencesApp {

    private Context CONTEXT;

    public SharedPreferencesApp(Context context) {
        this.CONTEXT = context;

    }


    public SharedPreferences getSharedPreferences() {

        return CONTEXT.getSharedPreferences("DispensaSetting", Context.MODE_PRIVATE);

    }

    public int getHourPreferences() {
        int hour = 11;
        if (getSharedPreferences() != null) {
            hour = getSharedPreferences().getInt("hourpreferences", 22);
        }
        return hour;
    }

    public int getMinutesPreferences() {
        int minutes = 11;
        if (getSharedPreferences() != null) {
            minutes = getSharedPreferences().getInt("minutepreferences", 16);
        }
        return minutes;
    }

    public void doSave(int hour, int minutes) {
        SharedPreferences sharedPreferences = CONTEXT.getSharedPreferences("DispensaSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("hourpreferences", hour);
        editor.putInt("minutepreferences", minutes);
        editor.apply();
        //TODO:AppStart();
        Toast.makeText(CONTEXT, "Salvato!", Toast.LENGTH_SHORT).show();
        NotificationApp notificationApp = new NotificationApp(CONTEXT);
        notificationApp.deleteAlarm();
        notificationApp.SetNotification();
    }

    public boolean getNotificationIsEnabled() {
        boolean firstStartApp = true;
        if (getSharedPreferences() != null) {
            firstStartApp = getSharedPreferences().getBoolean("Notification", true);
        }
        return firstStartApp;
    }

    public void setNotificationUp() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean("Notification", false);
        editor.apply();

    }

    public boolean getDialogNotification() {
        boolean firstStartApp = true;
        if (getSharedPreferences() != null) {
            firstStartApp = getSharedPreferences().getBoolean("DialogShow", true);
        }
        return firstStartApp;
    }

    public void setDialogNotificationUp() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean("DialogShow", false);
        editor.apply();

    }


}
