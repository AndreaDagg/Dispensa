package android.corso.dispensa.Logic;

import android.content.Context;
import android.corso.dispensa.NotificationApp.NotificationApp;

public class StartAppSettings {
    private Context CONTEXT;

    public StartAppSettings(Context CONTEXT) {
        this.CONTEXT = CONTEXT;
    }

    public void SetNotificatication(){
        SharedPreferencesApp sharedPreferencesApp = new SharedPreferencesApp(CONTEXT);

        if (sharedPreferencesApp.getIsFirstAppOpen()){

        }

    }



}
