package android.corso.dispensa.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.corso.dispensa.Dialog.TimePickerDialog;
import android.corso.dispensa.Logic.SharedPreferencesApp;
import android.corso.dispensa.MainActivity;
import android.corso.dispensa.R;
import android.os.Bundle;
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




}
