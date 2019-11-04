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
            hour = getSharedPreferences().getInt("hourpreferences", 10);
        }
        return hour;
    }

    public int getMinutesPreferences() {
        int minutes = 11;
        if (getSharedPreferences() != null) {
            minutes = getSharedPreferences().getInt("minutepreferences", 10);
        }
        return minutes;
    }

    public void doSave(int hour, int minutes){
        SharedPreferences sharedPreferences = CONTEXT.getSharedPreferences("DispensaSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("hourpreferences",hour);
        editor.putInt("minutepreferences",minutes);
        editor.apply();
        AppStart();
        Toast.makeText(CONTEXT,"Salvato!",Toast.LENGTH_SHORT).show();
        new NotificationApp(CONTEXT).SetNotification();
    }

    public boolean getIsFirstAppOpen(){
        boolean firstStartApp = true;
        if (getSharedPreferences()!=null){
            firstStartApp = getSharedPreferences().getBoolean("AppStart",true);
        }
        return firstStartApp;
    }

    public void AppStart(){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean("AppStart",false);
        editor.apply();

    }





}
