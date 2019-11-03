package android.corso.dispensa.Logic;

import android.content.Context;
import android.content.Intent;
import android.corso.dispensa.MainActivity;

public class OptionMenuLogic {

    private Context CONTEX;

    public OptionMenuLogic(Context context){
        this.CONTEX = context;

    }

    public Intent getGoHomeIntent(){
        Intent intent = new Intent(CONTEX, MainActivity.class);
        return  intent;
    }


}
