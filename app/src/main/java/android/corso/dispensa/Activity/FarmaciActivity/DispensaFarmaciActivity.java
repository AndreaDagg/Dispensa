package android.corso.dispensa.Activity.FarmaciActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.corso.dispensa.Fragment.ProductFragments.DispensaFragment;
import android.corso.dispensa.Fragment.ProductFragments.ProdottoFragment;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.Logic.OptionMenuLogic;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DispensaFarmaciActivity extends AppCompatActivity {
    private static String CALL_BY_FAR = new CategoryItem().getCATEGORY_FAR();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensa_farmaci);


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
        Intent intent = new Intent(getApplicationContext(), FarmaciActivity.class);
        startActivity(intent);
    }


    public void setFragmentHead() {
        getSupportFragmentManager().beginTransaction().add(R.id.frameNameTableFarm, new DispensaFragment()).commit();

    }

    public void setFragmentList() {
        getSupportFragmentManager().beginTransaction().add(R.id.listFragmentDispFarm, new ProdottoFragment(CALL_BY_FAR)).commit();

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
