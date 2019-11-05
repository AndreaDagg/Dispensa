package android.corso.dispensa.Activity.ListaSpesaAvtivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.corso.dispensa.BarcodeDetect;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class AggiungiArticloloActivity extends AppCompatActivity {
    static final String CATEGORYSELECTED = new CategoryItem().getCATEGORY_LIS(), DEFAULTINSERT = "1234";
    static final int CONFIRMED_SELECTION = 2;
    static final int REQUEST_CALL_LIS = 7;
    static final int CODEBAR_LENGTH = 13;
    private int CONFIRMED_BACK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_articlolo);
    }

    @Override
    protected void onResume() {

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
            finish();
            return super.onKeyDown(keyCode, event);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Premi di nuovo per uscire.", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }

    private void getinsertMarca() {
        EditText foodBrand = (EditText) findViewById(R.id.InsMarcaLis);
    }

    private void getinsertType() {
        EditText foodType = (EditText) findViewById(R.id.InsTipoLis);

    }

    private void getBarcode() {
        final Button getBarcodeButton = (Button) findViewById(R.id.barCodeReadLis);
        getBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!((TextView) findViewById(R.id.barCodeLis)).getText().toString().matches("")) {
                   /* DialogFragment newFragment = new DialogAlert("Barcode presente eliminare il prodotto e continuare con l'inserimento di un'altro barcode?");
                    newFragment.show(getSupportFragmentManager(), "dialog");*/

                    new AlertDialog.Builder(AggiungiArticloloActivity.this).setTitle("Barcode presente!").setMessage("Barcode presente eliminare il prodotto e continuare con l'inserimento di un'altro barcode?")
                            .setPositiveButton("Procedi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearForm();
                                    Intent intentBercode = new Intent(getApplicationContext(), BarcodeDetect.class);
                                    intentBercode.putExtra("call_by", REQUEST_CALL_LIS);
                                    startActivityForResult(intentBercode, REQUEST_CALL_LIS);
                                }
                            }).setNegativeButton("Indietro", null).show();
                } else {
                    Intent intentBercode = new Intent(getApplicationContext(), BarcodeDetect.class);
                    intentBercode.putExtra("call_by", REQUEST_CALL_LIS);
                    startActivityForResult(intentBercode, REQUEST_CALL_LIS);
                }


            }
        });
    }

    private void setMarcaProdotto(String brand) {
        EditText BrandEditText = findViewById(R.id.InsMarcaLis);
        BrandEditText.setText(brand);
    }

    private void setTipoProdotto(String type) {
        EditText BrandEditText = findViewById(R.id.InsTipoLis);
        BrandEditText.setText(type);
    }


    private void setBarCode(String bar_code) {

        TextView barCode = (TextView) findViewById(R.id.barCodeLis);
        barCode.setText(bar_code);
        setForms(bar_code);

    }

    @SuppressLint("StaticFieldLeak")
    private void setForms(final String barcodeInsert) {
        final Long barcode = Long.parseLong(barcodeInsert);
        if (((TextView) findViewById(R.id.barCodeLis)).getText().toString().length() == CODEBAR_LENGTH) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

                    if (DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().findIdBarcode(barcode)) {


                        ProdottoEntity prodottoEntities = DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().findInfoById(barcode);
                        setMarcaProdotto(prodottoEntities.getBrand());
                        setTipoProdotto(prodottoEntities.getProducttype());


                    } else {
                        return null; //TODO: CHEcK
                    }
                    return null;
                }


            }.execute();


        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Il barcode deve essere di 13 caratteri", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    private boolean checkInsertForm(boolean print) {
        if (((EditText) findViewById(R.id.InsMarcaLis)).getText().toString().matches("")) {
            if (print) {
                Toast toast = Toast.makeText(getApplicationContext(), "Inserisci la marca del prodotto", Toast.LENGTH_SHORT);
                toast.show();
                EditText targetView = (EditText) findViewById(R.id.InsMarcaLis);
                targetView.getParent().requestChildFocus(targetView, targetView);
                targetView.setError("Inserisci una marca");

            }
            return false;
        } else if (((EditText) findViewById(R.id.InsTipoLis)).getText().toString().matches("")) {
            if (print) {
                Toast toast = Toast.makeText(getApplicationContext(), "Inserisci il tipo di prodotto.", Toast.LENGTH_SHORT);
                toast.show();
                EditText targetView = (EditText) findViewById(R.id.InsTipoLis);
                targetView.getParent().requestChildFocus(targetView, targetView);
                targetView.setError("Inserisci il tipo");

            }
            return false;
        } else if (((TextView) findViewById(R.id.barCodeLis)).getText().toString().matches("")) {
            ((TextView) findViewById(R.id.barCodeLis)).setText(DEFAULTINSERT);
            return false;
        }

        return true;
    }

    private void setInsertButton() {
        Button insertButton = findViewById(R.id.InsertLis);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                new AsyncTask<Void, Void, Boolean>() {


                    @Override
                    protected Boolean doInBackground(Void... voids) {


                        if (checkInsertForm(false)) {


                            Long barcode = Long.parseLong(((TextView) findViewById(R.id.barCodeLis)).getText().toString());

                            //Check id existence
                            if (!DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().findIdBarcode(barcode)) {
                                ProdottoEntity prodottoEntity = new ProdottoEntity();
                                prodottoEntity.setIdbarcode(barcode);
                                prodottoEntity.setCategory(CATEGORYSELECTED);
                                prodottoEntity.setBrand(((EditText) findViewById(R.id.InsMarcaLis)).getText().toString());
                                prodottoEntity.setProducttype(((EditText) findViewById(R.id.InsTipoLis)).getText().toString());

                                if (((EditText) findViewById(R.id.InsQuantLis)).getText().toString().matches("")) {
                                    prodottoEntity.setNewBuy(1);
                                } else {
                                    prodottoEntity.setNewBuy(Integer.parseInt(((EditText) findViewById(R.id.InsQuantLis)).getText().toString()));
                                }
                                prodottoEntity.setList(true);
                                prodottoEntity.setNote(null);
                                prodottoEntity.setImage(null);


                                Long ProdottoIdRowCreated = DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().insertProdotto(prodottoEntity);
                            } else {
                                if (DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().getCategoryById(barcode).equals(new CategoryItem().getCATEGORY_ALI())) {
                                    DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().updateProdottoIsList(true, barcode);

                                    if (((EditText) findViewById(R.id.InsQuantLis)).getText().toString().matches("")) {
                                        DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().updateProdottoQuantity(
                                                DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().getQuantuityNewBuy(barcode) + 1, barcode);
                                    } else {
                                        DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().updateProdottoQuantity(
                                                DispensaDatabase.getInstance(getApplicationContext()).getProdottoDao().getQuantuityNewBuy(barcode) +
                                                        Integer.parseInt(((EditText) findViewById(R.id.InsQuantLis)).getText().toString()), barcode);
                                    }

                                }

                            }

                            return true;

                        } else {
                            return false;
                        }
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {

                        if (aBoolean) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Prodotto inserito correttamente", Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
                            Intent intentListaMain = new Intent(getApplicationContext(), ListaSpesaActivity.class);
                            startActivity(intentListaMain);

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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CALL_LIS && resultCode == RESULT_OK) {
            setBarCode(Objects.requireNonNull(data.getExtras()).getString("Barcode"));
        }
    }
}



