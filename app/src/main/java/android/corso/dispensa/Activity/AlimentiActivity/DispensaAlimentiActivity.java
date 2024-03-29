package android.corso.dispensa.Activity.AlimentiActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Fragment.ProductFragments.DispensaFragment;
import android.corso.dispensa.Fragment.ProductFragments.ProdottoFragment;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.Logic.OptionMenuLogic;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class DispensaAlimentiActivity extends AppCompatActivity {
    private static String CALL_BY_ALI = new CategoryItem().getCATEGORY_ALI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensa_alimenti);


    }

    @Override
    protected void onResume() {
        super.onResume();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setFragmentHead();
                setFragmentList();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        Intent intent = new Intent(getApplicationContext(), AlimentiActivity.class);
        startActivity(intent);
    }

    public boolean setFragmentHead() {
        getSupportFragmentManager().beginTransaction().add(R.id.frameNameTable, new DispensaFragment()).commit();
        return true;
    }

    public boolean setFragmentList() {
        getSupportFragmentManager().beginTransaction().add(R.id.listFragmentDisp, new ProdottoFragment(CALL_BY_ALI)).commit();
        return true;
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
