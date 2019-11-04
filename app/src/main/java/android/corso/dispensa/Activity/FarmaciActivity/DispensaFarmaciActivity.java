package android.corso.dispensa.Activity.FarmaciActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.corso.dispensa.Activity.AlimentiActivity.AlimentiActivity;
import android.corso.dispensa.Fragment.ProductFragments.DispensaFragment;
import android.corso.dispensa.Fragment.ProductFragments.ProdottoFragment;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.Logic.CheckDeadline;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.KeyEvent;

public class DispensaFarmaciActivity extends AppCompatActivity {
    private static String CALL_BY_ALI =new CategoryItem().getCATEGORY_FAR();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensa_farmaci);


    }

    @Override
    protected void onResume() {
        super.onResume();

        setFragmentHead();
        setFragmentList();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent intent = new Intent(getApplicationContext(), AlimentiActivity.class);
        startActivity(intent);
        return super.onKeyDown(keyCode, event);
    }

    public boolean setFragmentHead() {
        getSupportFragmentManager().beginTransaction().add(R.id.frameNameTable, new DispensaFragment()).commit();
        return true;
    }
    public boolean setFragmentList(){
        getSupportFragmentManager().beginTransaction().add(R.id.listFragmentDisp, new ProdottoFragment(CALL_BY_ALI)).commit();
        return true;
    }


}
