package android.corso.dispensa.Activity.FarmaciActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.corso.dispensa.Fragment.ProductFragments.DispensaFragment;
import android.corso.dispensa.Fragment.ProductFragments.ProdottoFragment;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.R;
import android.os.Bundle;

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


}
