package android.corso.dispensa.Fragment.ItemScaduti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.R;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.corso.dispensa.Fragment.ItemScaduti.expiredItemFragment.OnListFragmentInteractionListener;


import java.util.List;


public class MyexpiredItemRecyclerViewAdapter extends RecyclerView.Adapter<MyexpiredItemRecyclerViewAdapter.ViewHolder> {

    private final List<ArticoloEntity> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyexpiredItemRecyclerViewAdapter(List<ArticoloEntity> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_expireditem, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        new AsyncTask<ProdottoEntity, Void, ProdottoEntity>() {
            @Override
            protected ProdottoEntity doInBackground(ProdottoEntity... entities) {
                return DispensaDatabase.getInstance(holder.mType.getContext()).getProdottoDao().findInfoById(mValues.get(position).getBarcode());
            }

            @Override
            protected void onPostExecute(ProdottoEntity entity) {
                super.onPostExecute(entity);
                holder.mType.setText(entity.getProducttype());
                holder.mBrand.setText(entity.getBrand());
                holder.mDeadline.setText(mValues.get(position).getDaydeadline() + "/" + mValues.get(position).getMonthdeadline() + "/" + mValues.get(position).getYeardeadline());
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
        public final TextView mDeadline;
        public ArticoloEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mType = (TextView) view.findViewById(R.id.ExpiredTypeTw);
            mBrand = (TextView) view.findViewById(R.id.ExpiredBrandTw);
            mDeadline = (TextView) view.findViewById(R.id.ExpiredDeadlineTw);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBrand.getText() + "'";
        }
    }
}
