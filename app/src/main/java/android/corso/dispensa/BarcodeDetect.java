package android.corso.dispensa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.corso.dispensa.Logic.CategoryItem;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import android.os.Bundle;

import java.io.IOException;

import static android.Manifest.permission.CAMERA;

public class BarcodeDetect extends AppCompatActivity {

   /* private static int CALL_BY_ALI = 8;
    private static int CALL_BY_FAR = 9;
    private int CALL = 0;*/

    private BarcodeDetector detector;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private TextView barcodeTextView;
    private String barcodeRead = null;
    private int CALL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_detector);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceViewBarcode);
        barcodeTextView = (TextView) findViewById(R.id.barcode_text);


        //Call by alimenti or farmaci
        Bundle extras = getIntent().getExtras();
        CALL = extras.getInt("call_by");


        if (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{CAMERA}, 1);
        }

        if (this.CALL == 8) {
            /*Barcode.QR_CODE |*/
            detector = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(Barcode.EAN_13).build();
        } else if (this.CALL == 9) {
            /*Barcode.QR_CODE |*/
            detector = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(Barcode.ITF).build();
        } else {
            detector = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(Barcode.EAN_13 | Barcode.ITF).build();
        }


        if (!detector.isOperational()) {
            //TODO: gestire in caso incui barcode non è operativo
            return;
        }

        cameraSource = new CameraSource
                .Builder(getApplicationContext(), detector)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> items = detections.getDetectedItems();
                if (items.size() != 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String barcode = items.valueAt(0).displayValue;
                            barcodeTextView.setText(barcode);
                            setBarcodeRead(barcode);
                        }
                    });
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        confirmButton();
    }

    private void confirmButton() {
        Button barcodeButton = (Button) findViewById(R.id.buttonBarcodeDetect);
        final TextView textView = (TextView) findViewById(R.id.barcode_text);
        barcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textView.getText().equals("...")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Barcode", barcodeRead);
                    setResult(RESULT_OK, returnIntent);
                    finish();

                }
            }
        });

    }

    private void setBarcodeRead(String barcode) {
        barcodeRead = barcode;
    }

}
