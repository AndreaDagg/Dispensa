package android.corso.dispensa.Activity.AlimentiActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.corso.dispensa.R;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;

public class NuovoAlimentoActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

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

        getExpiry();
        getFoodPicture();
        getinsertMarca();
        getinsertType();
        getBarCode();
        setInsertButton();


        super.onResume();
    }


    private void getinsertMarca() {
        EditText foodBrand = (EditText) findViewById(R.id.InsMarcaAli);
    }

    private void getinsertType() {
        EditText foodType = (EditText) findViewById(R.id.InsTipoAli);

    }

    private void getBarCode() {
        EditText barCode = (EditText) findViewById(R.id.barCodeAlim);
    }

    private void getExpiry() {
        CalendarView foodExpiry = (CalendarView) findViewById(R.id.calendarViewScadenzaAlim);

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
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = (ImageView)findViewById(R.id.imageViewAli);
            imageView.setImageBitmap(imageBitmap);
        }
    }

}
