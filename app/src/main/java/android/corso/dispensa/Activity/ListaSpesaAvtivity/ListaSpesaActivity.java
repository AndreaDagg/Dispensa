package android.corso.dispensa.Activity.ListaSpesaAvtivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.corso.dispensa.Fragment.ItemShopping.productShopFragment;
import android.corso.dispensa.Logic.OptionMenuLogic;
import android.corso.dispensa.MainActivity;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ListaSpesaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_spesa);
        setFrame();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setButtonDeleteShop();
        setButtonAddShop();
    }

    public void setFrame() {
        getSupportFragmentManager().beginTransaction().add(R.id.FrameListaSpesa, new productShopFragment()).commit();
    }

    public void setButtonDeleteShop() {
        Button buttonDeleteShop = findViewById(R.id.ListaRemoveShopButton);
        buttonDeleteShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameListaSpesa, new productShopFragment()).addToBackStack(null).commit();

            }
        });
    }

    public void setButtonAddShop() {
        Button buttonDeleteShop = findViewById(R.id.ListaAddShopButton);
        buttonDeleteShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(getApplicationContext(), AggiungiArticloloActivity.class);
                startActivity(intentAdd);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentMain);
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
