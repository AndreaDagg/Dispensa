package android.corso.dispensa.Activity.AlimentiActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.corso.dispensa.BarcodeDetect;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.MainActivity;
import android.corso.dispensa.R;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static android.Manifest.permission.CAMERA;

public class NuovoAlimentoActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int CONFIRMED_SELECTION = 2;
    static final int REQUEST_CALL_ALI = 8;
    private byte[] ByteStringImage = null;
    private boolean CODEDAR_DETECTED = false;
    private int daySelected = 0, monthSelected = 0, yearSelected = 0, dateSelected = 0;
    private String barcodeRead = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuovo_alimento);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{CAMERA}, 1);
        }


    }

    @Override
    protected void onResume() {

        setDeadline();
        getFoodPicture();
        getinsertMarca();
        getinsertType();
        getBarcode();
        setInsertButton();


        super.onResume();
    }


    private void getinsertMarca() {
        EditText foodBrand = (EditText) findViewById(R.id.InsMarcaAli);
    }

    private void getinsertType() {
        EditText foodType = (EditText) findViewById(R.id.InsTipoAli);

    }

    private void getBarcode() {
        final Button getBarcodeButton = (Button) findViewById(R.id.barCodeReadAlim);
        getBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBercode = new Intent(getApplicationContext(), BarcodeDetect.class);
                intentBercode.putExtra("call_by", REQUEST_CALL_ALI);
                //TODO: results intent
                startActivityForResult(intentBercode, REQUEST_CALL_ALI);
            }
        });
    }

    private void setBarCode(String bar_code) {
        EditText barCode = (EditText) findViewById(R.id.barCodeAlim);
        barCode.setText(bar_code);

    }

    private void setDeadline() {
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarViewScadenzaAlim);
        final TextView dataTextView = (TextView) findViewById(R.id.textViewDataAli);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                daySelected = dayOfMonth;
                monthSelected = month + 1;
                yearSelected = year;
                dateSelected = CONFIRMED_SELECTION;
                dataTextView.setText(daySelected + " / " + monthSelected + " / " + yearSelected);
                view.invalidate(); //Refresh

            }
        });


    }

    private void getFoodPicture() {
        Button foodPicture = (Button) findViewById(R.id.cameraAli);
        foodPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //check that there is an activity that satisfies the call to the ACTION && if we are authorized for camera
                if ((takePhotoIntent.resolveActivity(getPackageManager()) != null) && (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_DENIED)) {
                    startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
                }
            }

        });

    }

    //TODO: Cercare metodo per implementare lettura tramite barcode

    private void setInsertButton() {
        Button insertButton = findViewById(R.id.InsertAlim);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                new AsyncTask<Void, Void, Boolean>() {
                    Switch switchMultiAlim = findViewById(R.id.SwitchMultiAli);

                    @Override
                    protected Boolean doInBackground(Void... voids) {

                        ProdottoEntity prodottoEntity = new ProdottoEntity();
                        ArticoloEntity articoloEntity = new ArticoloEntity();

                        //TODO: check if id insert is 13 character
                        if ((!((EditText) findViewById(R.id.barCodeAlim)).getText().toString().matches("")) && (dateSelected == CONFIRMED_SELECTION)) {


                            Long barcode = Long.parseLong(((EditText) findViewById(R.id.barCodeAlim)).getText().toString());

                            //Check id existence
                            if (!DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().findIdBarcode(barcode)) {
                                prodottoEntity.setIdbarcode(barcode);
                                prodottoEntity.setCategory("ALI");
                                prodottoEntity.setBrand(((EditText) findViewById(R.id.InsMarcaAli)).getText().toString());
                                prodottoEntity.setProducttype(((EditText) findViewById(R.id.InsTipoAli)).getText().toString());
                                //TODO: Found a way for save an image
                                if (ByteStringImage != null) {
                                    prodottoEntity.setImage(new String(ByteStringImage));
                                }
                                prodottoEntity.setList(false);
                                prodottoEntity.setNewBuy(0);
                                prodottoEntity.setNote(null);
                            } else {
                                CODEDAR_DETECTED = true;
                            }


                                articoloEntity.setBarcode(Long.parseLong(((EditText) findViewById(R.id.barCodeAlim)).getText().toString()));


                            articoloEntity.setDaydeadline(daySelected);
                            articoloEntity.setMonthdeadline(monthSelected);
                            articoloEntity.setYeardeadline(yearSelected);
                            articoloEntity.setUsed(100); //Full 100%

                            //Start homeActivity if switch is false
                            if (!switchMultiAlim.isChecked()) {
                                Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intentHome);
                            }

                            Long ProdottoIdRowCreated = DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().insertProdotto(prodottoEntity);
                            Long ArticoloIdRowCreated = DispensaDatabase.getInstance(getApplicationContext()).getArticoloDao().insertArticolo(articoloEntity);
                            return true;

                        } else {
                            //insert codebar
                            return false;
                        }
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {

                        if (((EditText) findViewById(R.id.barCodeAlim)).getText().toString().matches("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Inserisci il codice a barre", Toast.LENGTH_SHORT);
                            toast.show();
                        } else if ((dateSelected != CONFIRMED_SELECTION) && (!aBoolean)) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Inserisci una data di scadenza", Toast.LENGTH_SHORT);
                            toast.show();
                        } else if (aBoolean && !CODEDAR_DETECTED) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Prodotto inserito correttamente", Toast.LENGTH_SHORT);
                            toast.show();
                        } else if (aBoolean) {
                            CODEDAR_DETECTED = false;
                            Toast toast = Toast.makeText(getApplicationContext(), "Alimento inserito correttamente", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        if (switchMultiAlim.isChecked()) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Inserisci una nuova data di scadenza dello stesso prodotto", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                }.execute();


                //TODO: Gestire la migrazione
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = (ImageView) findViewById(R.id.imageViewAli);
            imageView.setImageBitmap(imageBitmap);

            //TODO: non funziona storage img
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            this.ByteStringImage = byteArray;
        } else if (requestCode == REQUEST_CALL_ALI && resultCode == RESULT_OK) {
            setBarCode(data.getExtras().getString("Barcode"));


        }
    }

}
