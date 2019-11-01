package android.corso.dispensa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.corso.dispensa.Fragment.ItemScaduti.expiredItemFragment;
import android.corso.dispensa.Logic.CheckDeadline;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.util.Log;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;

public class ArticoliScaduti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articoli_scaduti);
        setFragmentList();


        /*Date calendar = Calendar.getInstance().getTime();
        //String cal = DateFormat.getDateInstance().format(new Date());
        Date cal = new Date();*/

    /*    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse*/
     //   CheckDeadline checkDeadline = new CheckDeadline(getApplicationContext());






    }


    public void setFragmentList(){
      getSupportFragmentManager().beginTransaction().add(R.id.FrameDeadLine, new expiredItemFragment()).commit();
    }




}
