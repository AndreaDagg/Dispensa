package android.corso.dispensa.Fragment.ProductFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.corso.dispensa.Activity.AlimentiActivity.DispensaAlimentiActivity;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.Dialog.ConfirmDeleteItemAlim;
import android.corso.dispensa.Dialog.DialogAlert;
import android.corso.dispensa.Fragment.ItemsFragments.ItemFragmentHead;
import android.corso.dispensa.Fragment.ItemsFragments.ItemListFragment;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.corso.dispensa.Fragment.ProductFragments.ProdottoFragment.OnListFragmentInteractionListener;
import android.widget.Toast;

import java.util.List;

public class MyProdottoRecyclerViewAdapter extends RecyclerView.Adapter<MyProdottoRecyclerViewAdapter.ViewHolder> {

    private final List<ProdottoEntity> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyProdottoRecyclerViewAdapter(List<ProdottoEntity> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        if (items.size() == 0) {
            DialogAlert dialogAlert = new DialogAlert("Nessun elemento trovato!");
            //TODO: forse non si possono iusare i dialog
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_prodotto, parent, false);


        return new ViewHolder(view);


    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mType.setText(mValues.get(position).getProducttype() + "");
        holder.mBrand.setText(mValues.get(position).getBrand() + "");

        final Long _id = mValues.get(position).getIdbarcode();

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {

                return DispensaDatabase.getInstance(holder.mType.getContext()).getArticoloDao().CountItemByBarcode(mValues.get(position).getIdbarcode());

            }


            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                /*Product item quantity*/
                holder.mQuantity.setText(integer + "");
                if (integer == 0){
                    holder.mQuantity.setTextColor(holder.mView.getResources().getColor(R.color.redLight,holder.mView.getContext().getTheme()));
                    holder.mBrand.setTextColor(holder.mView.getResources().getColor(R.color.redLight,holder.mView.getContext().getTheme()));
                    holder.mType.setTextColor(holder.mView.getResources().getColor(R.color.redLight,holder.mView.getContext().getTheme()));
                }
            }
        }.execute();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }

                /* Call 2nd fragment */
                Bundle bundle = new Bundle();
                bundle.putLong("idItem", _id);

                ItemFragmentHead itemFragment = new ItemFragmentHead();
                ItemListFragment itemListFragment = new ItemListFragment();
                itemFragment.setArguments(bundle);
                itemListFragment.setArguments(bundle);

                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                if (mValues.get(position).getCategory().equals(new CategoryItem().getCATEGORY_ALI())) {
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameNameTable, itemFragment).addToBackStack(null).commit();
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.listFragmentDisp, itemListFragment).addToBackStack(null).commit();
                } else if (mValues.get(position).getCategory().equals(new CategoryItem().getCATEGORY_FAR())) {
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameNameTableFarm, itemFragment).addToBackStack(null).commit();
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.listFragmentDispFarm, itemListFragment).addToBackStack(null).commit();
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mType;
        public final TextView mBrand;
        public final TextView mQuantity;
        public ProdottoEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mType = (TextView) view.findViewById(R.id.itemTypeProductList);
            mBrand = (TextView) view.findViewById(R.id.itemBrandProductList);
            mQuantity = (TextView) view.findViewById(R.id.itemQuantityProductList);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBrand.getText() + "'";
        }


    }
}
