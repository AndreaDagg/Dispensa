package android.corso.dispensa.Activity.AlimentiActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.corso.dispensa.Fragment.ProductFragments.DispensaFragment;
import android.corso.dispensa.Fragment.ProductFragments.ProdottoFragment;
import android.corso.dispensa.R;
import android.os.Bundle;


public class DispensaAlimentiActivity extends AppCompatActivity {
    private static String CALL_BY_ALI = "ALI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensa_alimenti);


        setFragmentHead();
        setFragmentList();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public void setFragmentHead() {
        getSupportFragmentManager().beginTransaction().add(R.id.frameNameTable, new DispensaFragment()).commit();
    }
    public void setFragmentList(){
        getSupportFragmentManager().beginTransaction().add(R.id.listFragmentDisp, new ProdottoFragment(CALL_BY_ALI)).commit();
    }



}
