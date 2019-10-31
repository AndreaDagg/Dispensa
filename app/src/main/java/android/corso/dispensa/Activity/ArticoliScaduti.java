package android.corso.dispensa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.corso.dispensa.Fragment.ItemScaduti.expiredItemFragment;
import android.corso.dispensa.R;
import android.os.Bundle;

public class ArticoliScaduti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articoli_scaduti);
        setFragmentList();
    }


    public void setFragmentList(){
        getSupportFragmentManager().beginTransaction().add(R.id.FrameDeadLine, new expiredItemFragment()).commit();
    }



}
