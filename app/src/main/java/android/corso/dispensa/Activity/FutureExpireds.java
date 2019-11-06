package android.corso.dispensa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.corso.dispensa.Logic.OptionMenuLogic;
import android.corso.dispensa.Logic.SharedPreferencesApp;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FutureExpireds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_expireds);
        setView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        insertButton();
    }


    private void insertButton() {
        Button button = (Button) findViewById(R.id.buttonSETday);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferencesApp sharedPreferencesApp = new SharedPreferencesApp(getApplicationContext());
                EditText editTextFutureday = findViewById(R.id.editTextfuture);
                TextView textViewFuturre = findViewById(R.id.ViewFuture);

                sharedPreferencesApp.saveFuture(Integer.parseInt(editTextFutureday.getText().toString()));

                editTextFutureday.setText(sharedPreferencesApp.getDayFuture() + "");
                textViewFuturre.setText("Le scadenze future sono impostate a " + sharedPreferencesApp.getDayFuture() + "giorni.");


            }
        });
    }

    public void setView() {
        SharedPreferencesApp sharedPreferencesApp = new SharedPreferencesApp(getApplicationContext());
        EditText editTextFutureday = findViewById(R.id.editTextfuture);
        TextView textViewFuturre = findViewById(R.id.ViewFuture);

        editTextFutureday.setText(sharedPreferencesApp.getDayFuture() + "");
        textViewFuturre.setText("Le scadenze future sono impostate a " + sharedPreferencesApp.getDayFuture() + "giorni.");
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
