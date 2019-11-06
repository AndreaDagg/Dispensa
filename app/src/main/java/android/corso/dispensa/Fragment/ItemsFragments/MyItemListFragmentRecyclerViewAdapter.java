package android.corso.dispensa.Fragment.ItemsFragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.corso.dispensa.Activity.AlimentiActivity.DispensaAlimentiActivity;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.R;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.corso.dispensa.Fragment.ItemsFragments.ItemListFragment.OnListFragmentInteractionListener;
import android.widget.ToggleButton;


import java.util.List;


public class MyItemListFragmentRecyclerViewAdapter extends RecyclerView.Adapter<MyItemListFragmentRecyclerViewAdapter.ViewHolder> {

    private final List<ArticoloEntity> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemListFragmentRecyclerViewAdapter(List<ArticoloEntity> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_itemlistfragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDeadline.setText(mValues.get(position).getDaydeadline() + "/" + mValues.get(position).getMonthdeadline() + "/" + mValues.get(position).getYeardeadline());
        if (mValues.get(position).isUsed()){
            holder.mUsateButton.setText("Usato");
        }else{
            holder.mUsateButton.setText("Nuovo");
        }




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
        public final TextView mDeadline;
        public final Button mUsateButton;

        public ArticoloEntity mItem;

        public ViewHolder(final View view) {
            super(view);
            mView = view;
            mDeadline = (TextView) view.findViewById(R.id.ItemDeadline);
            mUsateButton = (Button) view.findViewById(R.id.ItemButtonUsed);

            mUsateButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onClick(View v) {
                    new AsyncTask<Void,Void,Void>(){
                        @Override
                        protected Void doInBackground(Void... voids) {

                            DispensaDatabase.getInstance(view.getContext()).getArticoloDao().updateUsedById(mItem.getId(),true);

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);

                        }
                    }.execute();
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDeadline.getText() + "'";
        }

    }
}
