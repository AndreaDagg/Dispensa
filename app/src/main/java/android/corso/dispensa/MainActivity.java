package android.corso.dispensa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.corso.dispensa.Activity.AlimentiActivity.AlimentiActivity;
import android.corso.dispensa.Activity.ArticoliScaduti;
import android.corso.dispensa.Activity.FarmaciActivity.FarmaciActivity;
import android.corso.dispensa.Activity.ListaSpesaAvtivity.ListaSpesaActivity;
import android.corso.dispensa.Logic.OptionMenuLogic;
import android.corso.dispensa.Logic.SharedPreferencesApp;
import android.corso.dispensa.NotificationApp.NotificationApp;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.Manifest.permission.SET_ALARM;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), SET_ALARM) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{SET_ALARM}, 1);
        }

        showDialogNotification();
    }

    @Override
    protected void onResume() {

        //-> Recover the View of the home buttons and call new intents
        setAlimentiButtonHome();
        setFarmaciButtonHome();
        setListaSpesaButtonHome();
        setArticoliScaduti();

        super.onResume();
    }

    private void showDialogNotification() {
        final SharedPreferencesApp sharedPreferencesApp = new SharedPreferencesApp(getApplicationContext());
        if (sharedPreferencesApp.getDialogNotification()) {
            new AlertDialog.Builder(MainActivity.this).setTitle("Ricevere notifiche").setMessage("Vuoi ricevere le notifiche da Dispensa?")
                    .setPositiveButton("Abilita", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreferencesApp.setDialogNotificationUp();
                            sharedPreferencesApp.setNotificationUp();
                            NotificationApp notificationApp = new NotificationApp(MainActivity.this);
                            notificationApp.deleteAlarm();
                            notificationApp.SetNotification();

                        }
                    }).setNegativeButton("Indietro", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedPreferencesApp.setDialogNotificationUp();
                    if (!sharedPreferencesApp.getDialogNotification()) {
                        Toast.makeText(MainActivity.this, "Le notifiche sono disattivate! Per attivarele selezionare un orario dal menu.", Toast.LENGTH_LONG).show();
                    }
                }
            }).show();

        }

    }

    private void setAlimentiButtonHome() {
        Button alimentiButtonHome = (Button) findViewById(R.id.AlimentiButtonHome);
        alimentiButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alimentiActivityInt = new Intent(getApplicationContext(), AlimentiActivity.class);
                startActivity(alimentiActivityInt);

            }
        });
    }

    private void setFarmaciButtonHome() {
        Button farmaciButtonHome = (Button) findViewById(R.id.FarmaciButtonHome);
        farmaciButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent farmaciActivityInt = new Intent(getApplicationContext(), FarmaciActivity.class);
                startActivity(farmaciActivityInt);
            }
        });
    }

    private void setListaSpesaButtonHome() {
        Button listaSpesaButtonHome = (Button) findViewById(R.id.ListaSpesaButtonHome);
        listaSpesaButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaSpesaActivityInt = new Intent(getApplicationContext(), ListaSpesaActivity.class);
                startActivity(listaSpesaActivityInt);
            }
        });
    }

    private void setArticoliScaduti() {
        Button articoliScadutiButtonHome = (Button) findViewById(R.id.ArticoliScadutiButtonHome);
        articoliScadutiButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent articoliScadutiInt = new Intent(getApplicationContext(), ArticoliScaduti.class);
                startActivity(articoliScadutiInt);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuOpHome:
                startActivity(new OptionMenuLogic(getApplicationContext()).getGoHomeIntent());
                return true;
            case R.id.menuOpNotify:
                startActivity(new OptionMenuLogic(getApplicationContext()).getGoNotificationIntent());
                return true;
            case R.id.menuOpInfo:
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
