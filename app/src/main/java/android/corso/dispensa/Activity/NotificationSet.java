package android.corso.dispensa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.corso.dispensa.Dialog.TimePickerDialog;
import android.corso.dispensa.Logic.OptionMenuLogic;
import android.corso.dispensa.Logic.SharedPreferencesApp;
import android.corso.dispensa.MainActivity;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class NotificationSet extends AppCompatActivity implements android.app.TimePickerDialog.OnTimeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_set);
        Button button = findViewById(R.id.buttonSETNOT);
        SharedPreferencesApp sharedPreferencesApp = new SharedPreferencesApp(getApplicationContext());
        TextView textViewSetHour = (TextView) findViewById(R.id.hourSet);
        textViewSetHour.setText(sharedPreferencesApp.getHourPreferences() + " : " + sharedPreferencesApp.getMinutesPreferences());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerDialog();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        TextView textViewSetHour = (TextView) findViewById(R.id.hourSet);
        SharedPreferencesApp sharedPreferencesApp = new SharedPreferencesApp(getApplicationContext());
        sharedPreferencesApp.doSave(hourOfDay, minute);
        textViewSetHour.setText(sharedPreferencesApp.getHourPreferences() + " : " + sharedPreferencesApp.getMinutesPreferences());


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
            case R.id.menuOpSetDay:
                startActivity(new OptionMenuLogic(getApplicationContext()).getGoDay());
                return true;
            case R.id.menuOpInfo:
                startActivity(new OptionMenuLogic(getApplicationContext()).getGoInfo());
        }


        return super.onOptionsItemSelected(item);
    }

}
