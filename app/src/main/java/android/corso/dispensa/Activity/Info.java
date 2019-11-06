package android.corso.dispensa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.corso.dispensa.Logic.OptionMenuLogic;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
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
