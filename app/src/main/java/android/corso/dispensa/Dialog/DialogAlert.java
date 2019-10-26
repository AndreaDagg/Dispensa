package android.corso.dispensa.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.corso.dispensa.BarcodeDetect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogAlert extends DialogFragment {

    private String textMessageToShow;

    public DialogAlert(String textMessage) {
        this.textMessageToShow = textMessage;
    }


    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(textMessageToShow).setPositiveButton("Procedi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentBercode = new Intent(getActivity(), BarcodeDetect.class);
                intentBercode.putExtra("call_by", 8);
                startActivityForResult(intentBercode, 8);
            }
        });

        builder.setMessage(textMessageToShow).setNegativeButton("Indietro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        return builder.create();
    }
}
