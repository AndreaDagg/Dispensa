package android.corso.dispensa.Activity.AlimentiActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.R;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Base64;
import java.util.List;

public class DispensaAlimentiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensa_alimenti);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setTX();
    }

    private void setTX() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                List<ProdottoEntity> prodottoEntities;
                List<ArticoloEntity> articoloEntities;
                prodottoEntities = DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().findAll();
                articoloEntities = DispensaDatabase.getInstance(getApplicationContext()).getArticoloDao().findAll();

                TextView texT = (TextView) findViewById(R.id.textViewDDDD);
                ImageView ima = (ImageView) findViewById(R.id.imageViewProva);



                if (prodottoEntities == null) {
                    texT.setText("E' vuoto");
                } else {
                    String b = "MARCHE: ", a = "BARCODEA: ", c = "BarcodeB: ";
                    for (int i = 0; i < prodottoEntities.size(); i++) {

                        b = b + " " + prodottoEntities.get(i).getBrand()+" - ";
                        a = a + " " + prodottoEntities.get(i).getIdbarcode() +" - ";
                        c = c + " " + articoloEntities.get(i).getBarcode() +" - ";




                    }

                    texT.setText(b + a +c );
                    byte[] image = prodottoEntities.get(0).getImage().getBytes();

                    ima.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
                }

                return null;
            }
        }.execute();
    }
}
