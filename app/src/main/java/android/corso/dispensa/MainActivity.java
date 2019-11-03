package android.corso.dispensa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.corso.dispensa.Activity.AlimentiActivity.AlimentiActivity;
import android.corso.dispensa.Activity.ArticoliScaduti;
import android.corso.dispensa.Activity.FarmaciActivity.FarmaciActivity;
import android.corso.dispensa.Activity.ListaSpesaAvtivity.ListaSpesaActivity;
import android.corso.dispensa.Logic.OptionMenuLogic;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Log.d("Menu2", "Intercetto");
                return true;
            case R.id.menuOpInfo:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
