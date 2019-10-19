package android.corso.dispensa.Activity.AlimentiActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlimentiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimenti);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setButtonOpenDipensaAlimenti();
        setButtonAddFood();

    }

    private void setButtonOpenDipensaAlimenti() {
        Button buttonOpenDispAli = findViewById(R.id.buttonOpenDispAli);
        buttonOpenDispAli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DispensaAlimentiActivity.class));
            }
        });
    }

    private void setButtonAddFood() {
        Button buttonAddFood = findViewById(R.id.buttonAddAli);
        buttonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NuovoAlimentoActivity.class));
            }
        });
    }
}
