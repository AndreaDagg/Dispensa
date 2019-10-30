package android.corso.dispensa.Fragment.ItemsFragments;

import androidx.recyclerview.widget.RecyclerView;

import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.corso.dispensa.Fragment.ItemsFragments.ItemListFragment.OnListFragmentInteractionListener;


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
        holder.mIdView.setText(mValues.get(position).getUsed());
        holder.mContentView.setText("seta la data");

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
        public final TextView mIdView;
        public final TextView mContentView;
        public ArticoloEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.ItemToggleButton);
            mContentView = (TextView) view.findViewById(R.id.ItemDeadline);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
