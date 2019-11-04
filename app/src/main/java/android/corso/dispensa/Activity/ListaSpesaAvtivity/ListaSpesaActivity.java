package android.corso.dispensa.Activity.ListaSpesaAvtivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.corso.dispensa.Fragment.ItemShopping.productShopFragment;
import android.corso.dispensa.MainActivity;
import android.corso.dispensa.R;
import android.os.Bundle;
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
}
