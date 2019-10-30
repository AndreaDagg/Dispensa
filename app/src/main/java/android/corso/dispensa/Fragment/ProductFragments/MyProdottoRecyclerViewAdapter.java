package android.corso.dispensa.Fragment.ProductFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentTransaction;
import android.corso.dispensa.Activity.AlimentiActivity.DispensaAlimentiActivity;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.Dialog.DialogAlert;
import android.corso.dispensa.Fragment.ItemsFragments.ItemFragment;
import android.corso.dispensa.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.corso.dispensa.Fragment.ProductFragments.ProdottoFragment.OnListFragmentInteractionListener;

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


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mType.setText(mValues.get(position).getProducttype() + "");
        holder.mBrand.setText(mValues.get(position).getBrand() + "");
        //TODO: Passare la quantità
        holder.mQuantity.setText(mValues.get(position).getCategory() + "");
        final Long _id = mValues.get(position).getIdbarcode();

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

                ItemFragment itemFragment = new ItemFragment();
                itemFragment.setArguments(bundle);

                AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frameNameTable, itemFragment,"ITEM_FRAGMENT").addToBackStack("ITEM_FRAGMENT").commit();


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
