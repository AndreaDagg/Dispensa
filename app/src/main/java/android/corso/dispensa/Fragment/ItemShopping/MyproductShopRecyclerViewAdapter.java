package android.corso.dispensa.Fragment.ItemShopping;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.R;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import android.corso.dispensa.Fragment.ItemShopping.productShopFragment.OnListFragmentInteractionListener;

import java.util.List;


public class MyproductShopRecyclerViewAdapter extends RecyclerView.Adapter<MyproductShopRecyclerViewAdapter.ViewHolder> {

    private final List<ProdottoEntity> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyproductShopRecyclerViewAdapter(List<ProdottoEntity> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_productshop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mProd.setText(mValues.get(position).getProducttype() + "");
        holder.mQuant.setText(mValues.get(position).getNewBuy() + "");

        holder.mBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            DispensaDatabase.getInstance(holder.mBox.getContext()).getProdottoDao().updateProdottoIsList(false,
                                    mValues.get(position).getIdbarcode());
                            DispensaDatabase.getInstance(holder.mBox.getContext()).getProdottoDao().updateProdottoQuantity(0,
                                    mValues.get(position).getIdbarcode());
                            return null;
                        }
                    }.execute();
                } else {
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView mProd;
        public final TextView mQuant;
        public final CheckBox mBox;
        public ProdottoEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProd = (TextView) view.findViewById(R.id.ShopProdTw);
            mQuant = (TextView) view.findViewById(R.id.ShopQuantTw);
            mBox = (CheckBox) view.findViewById(R.id.checkBox);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mQuant.getText() + "'";
        }


    }
}
