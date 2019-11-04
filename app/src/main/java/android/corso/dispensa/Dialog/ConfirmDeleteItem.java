package android.corso.dispensa.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.corso.dispensa.Activity.ArticoliScaduti;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.R;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmDeleteItem extends DialogFragment {

    private Long IDITEM;
    private String CATEGORY;
    private ArticoliScaduti articoliScaduti;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Confermi di voler eliminare?");
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        DispensaDatabase.getInstance(getContext()).getArticoloDao().deleteArticolo(
                                DispensaDatabase.getInstance(getContext()).getArticoloDao().findItemByIdItem(IDITEM)
                        );
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        // articoliScaduti.removeFragmentList();
                        articoliScaduti.setFragmentList(CATEGORY);
                    }
                }.execute();

            }
        });
        builder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        return builder.create();
    }

    public void setIdItem(long id_item, String category, ArticoliScaduti scaduti) {
        this.IDITEM = id_item;
        this.CATEGORY = category;
        this.articoliScaduti = scaduti;
    }


}
