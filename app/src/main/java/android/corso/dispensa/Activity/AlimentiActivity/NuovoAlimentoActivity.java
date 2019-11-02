package android.corso.dispensa.Activity.AlimentiActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.corso.dispensa.BarcodeDetect;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.MainActivity;
import android.corso.dispensa.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
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
    static final String CATEGORYSELECTED = new CategoryItem().getCATEGORY_ALI();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int CONFIRMED_SELECTION = 2;
    static final int REQUEST_CALL_ALI = 8;
    static final int CODEBAR_LENGTH = 13;
    private byte[] ByteStringImage = null;
    private boolean CODEBAR_DETECTED = false;
    private int daySelected = 0, monthSelected = 0, yearSelected = 0, dateSelected = 0, CONFIRMED_BACK = 0;


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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        CONFIRMED_BACK++;
        if (CONFIRMED_BACK == CONFIRMED_SELECTION) {
            return super.onKeyDown(keyCode, event);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Premi di nuovo per uscire.", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
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

                if (!((EditText) findViewById(R.id.barCodeAlim)).getText().toString().matches("")) {
                   /* DialogFragment newFragment = new DialogAlert("Barcode presente eliminare il prodotto e continuare con l'inserimento di un'altro barcode?");
                    newFragment.show(getSupportFragmentManager(), "dialog");*/

                    new AlertDialog.Builder(NuovoAlimentoActivity.this).setTitle("Barcode presente!").setMessage("Barcode presente eliminare il prodotto e continuare con l'inserimento di un'altro barcode?")
                            .setPositiveButton("Procedi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearForm();
                                    Intent intentBercode = new Intent(getApplicationContext(), BarcodeDetect.class);
                                    intentBercode.putExtra("call_by", REQUEST_CALL_ALI);
                                    startActivityForResult(intentBercode, REQUEST_CALL_ALI);
                                }
                            }).setNegativeButton("Indietro", null).show();
                } else {
                    Intent intentBercode = new Intent(getApplicationContext(), BarcodeDetect.class);
                    intentBercode.putExtra("call_by", REQUEST_CALL_ALI);
                    startActivityForResult(intentBercode, REQUEST_CALL_ALI);
                }


            }
        });
    }

    private void setMarcaProdotto(String brand) {
        EditText BrandEditText = findViewById(R.id.InsMarcaAli);
        BrandEditText.setText(brand);
    }

    private void setTipoProdotto(String type) {
        EditText BrandEditText = findViewById(R.id.InsTipoAli);
        BrandEditText.setText(type);
    }

    private void setPictureProdotto(Bitmap imageBitmap, boolean convert) {
        ImageView imageView = (ImageView) findViewById(R.id.imageViewAli);
        imageView.setImageBitmap(imageBitmap);

        if (convert) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            ByteStringImage = outputStream.toByteArray();
        }
    }


    private void setBarCode(String bar_code) {
        EditText barCode = (EditText) findViewById(R.id.barCodeAlim);
        barCode.setText(bar_code);
        setForms(bar_code);
    }

    @SuppressLint("StaticFieldLeak")
    private void setForms(String barcodeInsert) {
        final Long barcode = Long.parseLong(barcodeInsert);
        if (((EditText) findViewById(R.id.barCodeAlim)).getText().toString().length() == CODEBAR_LENGTH) {
            new AsyncTask<Void, Void, byte[]>() {
                @Override
                protected byte[] doInBackground(Void... voids) {

                    if (DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().findIdBarcode(barcode)) {
                        ProdottoEntity prodottoEntities = DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().findInfoById(barcode);
                        setMarcaProdotto(prodottoEntities.getBrand());
                        setTipoProdotto(prodottoEntities.getProducttype());
                        ByteStringImage = prodottoEntities.getImage();

                    } else {
                        return null; //TODO: CHEcK
                    }
                    return ByteStringImage;
                }


                //After asyncTask. In main thread.
                @Override
                protected void onPostExecute(byte[] bytes) {
                    super.onPostExecute(bytes);
                    //Set or remove the product icon
                    if (ByteStringImage != null) {
                        Bitmap bitmapImage = BitmapFactory.decodeByteArray(ByteStringImage, 0, ByteStringImage.length);
                        bitmapImage = Bitmap.createScaledBitmap(bitmapImage, 100, 180, true);
                        setPictureProdotto(bitmapImage, false);
                    } else {
                        setPictureProdotto(null, false);
                    }
                }
            }.execute();


        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Il barcode deve essere di 13 caratteri", Toast.LENGTH_SHORT);
            toast.show();
        }
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
                if ((takePhotoIntent.resolveActivity(getPackageManager()) != null) && (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                    startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
                }
            }

        });

    }

    private boolean checkInsertForm(boolean print) {
        if (((EditText) findViewById(R.id.barCodeAlim)).getText().toString().matches("")) {
            if (print) {
                Toast toast = Toast.makeText(getApplicationContext(), "Inserisci il codice a barre.", Toast.LENGTH_SHORT);
                toast.show();
                EditText targetView = (EditText) findViewById(R.id.barCodeAlim);
                targetView.getParent().requestChildFocus(targetView, targetView);
                targetView.setError("Inserisci barcode");
                //targetView.getBackground().setColorFilter(Color.parseColor("#DD372B"), PorterDuff.Mode.SCREEN);

            }
            return false;
        } else if (!(((EditText) findViewById(R.id.barCodeAlim)).getText().toString().length() == CODEBAR_LENGTH)) {
            if (print) {
                Toast toast = Toast.makeText(getApplicationContext(), "Il codice a barre deve contenere 13 caratteri.", Toast.LENGTH_SHORT);
                toast.show();
                EditText targetView = (EditText) findViewById(R.id.barCodeAlim);
                targetView.getParent().requestChildFocus(targetView, targetView);
                targetView.setError("Assicurati che sia di 13 caratteri");
            }
            return false;
        } else if (!(dateSelected == CONFIRMED_SELECTION)) {
            if (print) {
                Toast toast = Toast.makeText(getApplicationContext(), "Inserisci una data di scadenza.", Toast.LENGTH_SHORT);
                toast.show();
                View targetView = findViewById(R.id.calendarViewScadenzaAlim);
                targetView.getParent().requestChildFocus(targetView, targetView);
            }
            return false;
        } else if (((EditText) findViewById(R.id.InsMarcaAli)).getText().toString().matches("")) {
            if (print) {
                Toast toast = Toast.makeText(getApplicationContext(), "Inserisci la marca del prodotto", Toast.LENGTH_SHORT);
                toast.show();
                EditText targetView = (EditText) findViewById(R.id.InsMarcaAli);
                targetView.getParent().requestChildFocus(targetView, targetView);
                targetView.setError("Inserisci una marca");

            }
            return false;
        } else if (((EditText) findViewById(R.id.InsTipoAli)).getText().toString().matches("")) {
            if (print) {
                Toast toast = Toast.makeText(getApplicationContext(), "Inserisci il tipo di prodotto.", Toast.LENGTH_SHORT);
                toast.show();
                EditText targetView = (EditText) findViewById(R.id.InsTipoAli);
                targetView.getParent().requestChildFocus(targetView, targetView);
                targetView.setError("Inserisci il tipo");

            }
            return false;
        } else if (ByteStringImage == null) {
            if (print) {
                new AlertDialog.Builder(NuovoAlimentoActivity.this).setTitle("Foto assente").setMessage("Immagine non presente! Procedere comunque all'inserimento?")
                        .setPositiveButton("Scatta", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //check that there is an activity that satisfies the call to the ACTION && if we are authorized for camera
                                if ((takePhotoIntent.resolveActivity(getPackageManager()) != null) && (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                                    startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
                                }
                            }
                        }).setNegativeButton("Indietro", null).show();
            }
            return false;
        }

        return true;
    }

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


                        if (checkInsertForm(false)) {
                            ArticoloEntity articoloEntity = new ArticoloEntity();
                            Long barcode = Long.parseLong(((EditText) findViewById(R.id.barCodeAlim)).getText().toString());

                            //Check id existence
                            if (!DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().findIdBarcode(barcode)) {
                                ProdottoEntity prodottoEntity = new ProdottoEntity();
                                prodottoEntity.setIdbarcode(barcode);
                                prodottoEntity.setCategory(CATEGORYSELECTED);
                                prodottoEntity.setBrand(((EditText) findViewById(R.id.InsMarcaAli)).getText().toString());
                                prodottoEntity.setProducttype(((EditText) findViewById(R.id.InsTipoAli)).getText().toString());
                                if (ByteStringImage != null) {
                                    prodottoEntity.setImage(ByteStringImage);
                                }
                                prodottoEntity.setList(false);
                                prodottoEntity.setNewBuy(0);
                                prodottoEntity.setNote(null);

                                Long ProdottoIdRowCreated = DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().insertProdotto(prodottoEntity);
                            } else {
                                CODEBAR_DETECTED = true;
                            }

                            articoloEntity.setCategoryItem(CATEGORYSELECTED);
                            articoloEntity.setBarcode(barcode);
                            articoloEntity.setDaydeadline(daySelected);
                            articoloEntity.setMonthdeadline(monthSelected);
                            articoloEntity.setYeardeadline(yearSelected);
                            articoloEntity.setUsed(100); //Full 100%


                            Long ArticoloIdRowCreated = DispensaDatabase.getInstance(getApplicationContext()).getArticoloDao().insertArticolo(articoloEntity);

                            //Start homeActivity if switch is false
                            if (!switchMultiAlim.isChecked()) {
                                Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intentHome);
                            }
                            return true;

                        } else {
                            return false;
                        }
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {

                        if (aBoolean) {
                            if (!CODEBAR_DETECTED) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Prodotto inserito correttamente", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                CODEBAR_DETECTED = false;
                                Toast toast = Toast.makeText(getApplicationContext(), "Alimento inserito correttamente", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            if (switchMultiAlim.isChecked()) {
                                Toast toast2 = Toast.makeText(getApplicationContext(), "Inserisci una nuova data di scadenza dello stesso prodotto", Toast.LENGTH_LONG);
                                toast2.show();
                            }
                        } else {
                            checkInsertForm(true);
                        }

                    }
                }.execute();

                //TODO: Gestire la migrazione
            }
        });
    }

    private void clearForm() {

        setMarcaProdotto("");
        setTipoProdotto("");
        ByteStringImage = null;
        setPictureProdotto(null, false);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            setPictureProdotto(imageBitmap, true);

        } else if (requestCode == REQUEST_CALL_ALI && resultCode == RESULT_OK) {
            setBarCode(data.getExtras().getString("Barcode"));
        }
    }
}
