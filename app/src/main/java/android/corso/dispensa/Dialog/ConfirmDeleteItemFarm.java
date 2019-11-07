package android.corso.dispensa.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.corso.dispensa.Activity.AlimentiActivity.DispensaAlimentiActivity;
import android.corso.dispensa.Activity.FarmaciActivity.DispensaFarmaciActivity;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Fragment.ItemsFragments.ItemFragmentHead;
import android.corso.dispensa.Fragment.ItemsFragments.ItemListFragment;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.R;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class ConfirmDeleteItemFarm extends DialogFragment {

    private Long IDITEM, BARCODEITEM;
    private String CATEGORY;
    private DispensaFarmaciActivity farmaciActivity;
    private Context contextItem;


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
                        // farmaciActivity.setFragmentList();

                        Bundle bundle = new Bundle();
                        bundle.putLong("idItem", BARCODEITEM);
                        ItemFragmentHead itemFragment = new ItemFragmentHead();
                        ItemListFragment itemListFragment = new ItemListFragment();
                        itemFragment.setArguments(bundle);
                        itemListFragment.setArguments(bundle);

                        AppCompatActivity appCompatActivity = (AppCompatActivity) contextItem;
                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameNameTableFarm, itemFragment).addToBackStack(null).commit();
                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.listFragmentDispFarm, itemListFragment).addToBackStack(null).commit();


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

    public void setIdItem(long id_item, String category, Long barcode, DispensaFarmaciActivity scaduti) {
        this.IDITEM = id_item;
        this.CATEGORY = category;
        this.farmaciActivity = scaduti;
        this.BARCODEITEM = barcode;
    }

    public void setContext(Context context) {
        this.contextItem = context;
    }


}
