package android.corso.dispensa.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.corso.dispensa.Fragment.ProdottoFragment.OnListFragmentInteractionListener;
import android.corso.dispensa.Fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProdottoRecyclerViewAdapter extends RecyclerView.Adapter<MyProdottoRecyclerViewAdapter.ViewHolder> {

    private final List<ProdottoEntity> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyProdottoRecyclerViewAdapter(List<ProdottoEntity> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.mType.setText(mValues.get(position).getProducttype()+"");
        holder.mBrand.setText(mValues.get(position).getBrand()+"");
        holder.mQuantity.setText(mValues.get(position).getCategory()+"");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
