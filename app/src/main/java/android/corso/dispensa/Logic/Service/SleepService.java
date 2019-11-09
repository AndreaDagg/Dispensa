package android.corso.dispensa.Logic.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.corso.dispensa.NotificationApp.NotificationApp;

public class SleepService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
            new NotificationApp(context).SetNotification();
        }
    }
}
