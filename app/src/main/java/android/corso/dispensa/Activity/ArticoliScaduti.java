package android.corso.dispensa.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.corso.dispensa.Fragment.ItemScaduti.expiredItemFragment;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Objects;

public class ArticoliScaduti extends AppCompatActivity {

    private boolean TODAY = false;
    private String CATEGORY = new CategoryItem().getCATEGORY_ALI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        //Set if show today expired
        if (extras != null) {
            this.TODAY = extras.getBoolean("today");
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
                CATEGORY =new CategoryItem().getCATEGORY_ALI();

                AlphaAnimation buttonClick = new AlphaAnimation(1F, 1.5F);
                v.startAnimation(buttonClick);

                removeFragmentList();
                setFragmentList(CATEGORY);

                ViewGroup viewGroup = findViewById(R.id.FrameDeadLine);
                viewGroup.invalidate();

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
                removeFragmentList();
                setFragmentList(CATEGORY);
                ViewGroup viewGroup = findViewById(R.id.FrameDeadLine);
                viewGroup.invalidate();
                Button ButtonAli = (Button) findViewById(R.id.buttonAliDead);
                buttonFar.setBackgroundColor(getResources().getColor(R.color.blueSwitch, getTheme()));
                buttonFar.setTextColor(getResources().getColor(R.color.greyLight, getTheme()));
                ButtonAli.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark, getTheme()));
                ButtonAli.setTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));
            }
        });


    }

    private void getIfShowToday(){
        final Switch switchToday = (Switch) findViewById(R.id.switchToday);
        switchToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchToday.isChecked()) {
                    TODAY = true;
                    removeFragmentList();
                    setFragmentList(CATEGORY);

                }else{
                    TODAY = false;
                    removeFragmentList();
                    setFragmentList(CATEGORY);
                }
            }
        });

    }

    public void setFragmentList(String category) {
        Log.d("Cat: ", category + "");
        getSupportFragmentManager().beginTransaction().add(R.id.FrameDeadLine, new expiredItemFragment(category, TODAY)).commitNow();
    }

    public void removeFragmentList() {
        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.FrameDeadLine))).commitNow();
        // getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.FrameDeadLine))).commitNow();
    }

}
