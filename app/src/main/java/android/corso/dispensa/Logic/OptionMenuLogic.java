package android.corso.dispensa.Logic;

import android.content.Context;
import android.content.Intent;
import android.corso.dispensa.Activity.FutureExpireds;
import android.corso.dispensa.Activity.Info;
import android.corso.dispensa.Activity.NotificationSet;
import android.corso.dispensa.MainActivity;
import android.corso.dispensa.NotificationApp.NotificationApp;

public class OptionMenuLogic {

    private Context CONTEX;

    public OptionMenuLogic(Context context) {
        this.CONTEX = context;

    }

    public Intent getGoHomeIntent() {
        return new Intent(CONTEX, MainActivity.class);
    }

    public Intent getGoNotificationIntent() {
        return new Intent(CONTEX, NotificationSet.class);
    }

    public Intent getGoDay() {
        return new Intent(CONTEX, FutureExpireds.class);
    }

    public Intent getGoInfo() {
        return new Intent(CONTEX, Info.class);
    }




}
