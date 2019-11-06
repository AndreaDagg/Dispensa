package android.corso.dispensa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.corso.dispensa.Fragment.ItemScaduti.expiredItemFragment;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.Logic.OptionMenuLogic;
import android.corso.dispensa.Logic.SharedPreferencesApp;
import android.corso.dispensa.MainActivity;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Objects;

import static android.corso.dispensa.R.id.FrameDeadLine;

public class ArticoliScaduti extends AppCompatActivity {

    private boolean futureExpireds = false;
    private String CATEGORY = new CategoryItem().getCATEGORY_ALI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        //Set if show today expired
        if (extras != null) {
            this.futureExpireds = extras.getBoolean("today");
        }

        setContentView(R.layout.activity_articoli_scaduti);
        setFragmentList(new CategoryItem().getCATEGORY_ALI());


    }

    @Override
    protected void onResume() {
        super.onResume();

                getCategoryToShowALI();
                getCategoryToShowFAR();
                getIfShowToday();




    }

    private void getCategoryToShowALI() {
        final Button ButtonAli = (Button) findViewById(R.id.buttonAliDead);

        ButtonAli.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                CATEGORY = new CategoryItem().getCATEGORY_ALI();

                AlphaAnimation buttonClick = new AlphaAnimation(1F, 1.5F);
                v.startAnimation(buttonClick);

                // removeFragmentList();
                //replaceFragmentList(CATEGORY);
                setFragmentList(CATEGORY);

                Button buttonFar = (Button) findViewById(R.id.buttonFarDead);
                ButtonAli.setBackgroundColor(getResources().getColor(R.color.blueSwitch, getTheme()));
                ButtonAli.setTextColor(getResources().getColor(R.color.greyLight, getTheme()));
                buttonFar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, getTheme()));
                buttonFar.setTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));


            }


        });
    }

    private void getCategoryToShowFAR() {
        final Button buttonFar = (Button) findViewById(R.id.buttonFarDead);

        buttonFar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                CATEGORY = new CategoryItem().getCATEGORY_FAR();

                //removeFragmentList();
                //replaceFragmentList(CATEGORY);
                setFragmentList(CATEGORY);
                Button ButtonAli = (Button) findViewById(R.id.buttonAliDead);
                buttonFar.setBackgroundColor(getResources().getColor(R.color.blueSwitch, getTheme()));
                buttonFar.setTextColor(getResources().getColor(R.color.greyLight, getTheme()));
                ButtonAli.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, getTheme()));
                ButtonAli.setTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));
            }
        });


    }

    private void getIfShowToday() {
        final Switch switchToday = (Switch) findViewById(R.id.switchToday);
        switchToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchToday.isChecked()) {
                    futureExpireds = true;
                    //removeFragmentList();
                    // replaceFragmentList(CATEGORY);
                    setFragmentList(CATEGORY);
                    Toast.makeText(getApplicationContext(),"Prodotti in scadenza nei porsssimi "+ new SharedPreferencesApp(getApplicationContext()).getDayFuture()+" giorni",Toast.LENGTH_SHORT).show();

                } else {
                    futureExpireds = false;
                    //removeFragmentList();
                    // replaceFragmentList(CATEGORY);
                    setFragmentList(CATEGORY);
                }
            }
        });

    }

    public void setFragmentList(String category) {
        getSupportFragmentManager().beginTransaction().add(FrameDeadLine, new expiredItemFragment(category, futureExpireds)).addToBackStack(null).commitAllowingStateLoss();


    }


    public void replaceFragmentList(final String category) {
        getSupportFragmentManager().beginTransaction().replace(FrameDeadLine, new expiredItemFragment(category, futureExpireds)).addToBackStack(null).commit();
        getSupportFragmentManager().executePendingTransactions();


    }

    public void removeFragmentList() {
       /* FrameLayout frameLayout = findViewById(FrameDeadLine);
        frameLayout.removeAllViews();*/

        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(FrameDeadLine))).commit();
        // getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.FrameDeadLine))).commitNow();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuOpHome:
                startActivity(new OptionMenuLogic(getApplicationContext()).getGoHomeIntent());
                return true;
            case R.id.menuOpNotify:
                startActivity(new OptionMenuLogic(getApplicationContext()).getGoNotificationIntent());
                return true;
            case R.id.menuOpSetDay:
                startActivity(new OptionMenuLogic(getApplicationContext()).getGoDay());
                return true;
            case R.id.menuOpInfo:
                startActivity(new OptionMenuLogic(getApplicationContext()).getGoInfo());
        }


        return super.onOptionsItemSelected(item);
    }
}
