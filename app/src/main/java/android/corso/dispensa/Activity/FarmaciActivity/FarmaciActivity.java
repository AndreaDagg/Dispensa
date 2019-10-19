package android.corso.dispensa.Activity.FarmaciActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FarmaciActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmaci);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setButtonAddFarm();
        setOpenDispFarm();
    }

    protected void setButtonAddFarm(){
        Button buttonAddFarm = (Button)findViewById(R.id.buttonAddFarm);
        buttonAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NuovoFarmacoActivity.class));
            }
        });
    }

    protected void setOpenDispFarm(){
        Button buttonOpenDispFarm = (Button)findViewById(R.id.buttonOpenDispFarm);
        buttonOpenDispFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DispensaFarmaciActivity.class));
            }
        });
    }
}
