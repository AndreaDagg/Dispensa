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

import java.util.Objects;

public class ArticoliScaduti extends AppCompatActivity {

    /* private String CATEGORYDEFAULT = new CategoryItem().getCATEGORY_ALI();
     private String CATEGORY
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articoli_scaduti);
        setFragmentList(new CategoryItem().getCATEGORY_ALI());


    }

    @Override
    protected void onResume() {
        super.onResume();

        getCategoryToShowALI();
        getCategoryToShowFAR();


    }

    private void getCategoryToShowALI() {
        final Button ButtonAli = (Button) findViewById(R.id.buttonAliDead);

        ButtonAli.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                AlphaAnimation buttonClick = new AlphaAnimation(1F, 1.5F);
                v.startAnimation(buttonClick);

                removeFragmentList();
                setFragmentList(new CategoryItem().getCATEGORY_ALI());

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
                removeFragmentList();
                setFragmentList(new CategoryItem().getCATEGORY_FAR());
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

    public void setFragmentList(String category) {
        Log.d("Cat: ", category + "");
        getSupportFragmentManager().beginTransaction().add(R.id.FrameDeadLine, new expiredItemFragment(category)).commitNow();
    }

    public void removeFragmentList() {
        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.FrameDeadLine))).commitNow();
       // getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.FrameDeadLine))).commitNow();
    }

}
