package android.corso.dispensa.Fragment.ItemScaduti;

import androidx.recyclerview.widget.RecyclerView;

import android.corso.dispensa.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.corso.dispensa.Fragment.ItemScaduti.expiredItemFragment.OnListFragmentInteractionListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class MyexpiredItemRecyclerViewAdapter extends RecyclerView.Adapter<MyexpiredItemRecyclerViewAdapter.ViewHolder> {

    private final JSONArray mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyexpiredItemRecyclerViewAdapter(JSONArray items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_expireditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
         //   holder.mItem = (JSONArray) mValues.get(position + 1);
            /* holder.twBrandExp.setText(mValues.get(position).id);*/

            holder.twTypeExp.setText((mValues.get(position + 1)).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*TODO: Il jsopn arriva viene letto funziona, position itera esattamente 22 posizioni*/

        /*  Log.d("-->", " "+position);*/

        try {
            Log.d("-->", (mValues.get(position + 1).toString()));
        } catch (JSONException e) {
            e.printStackTrace();
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
        return mValues.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView twBrandExp;
        public final TextView twTypeExp;
        public final TextView twDeadExp;
        public JSONArray mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            twBrandExp = (TextView) view.findViewById(R.id.ExpiredBrandTw);
            twTypeExp = (TextView) view.findViewById(R.id.ExpiredTypeTw);
            twDeadExp = (TextView) view.findViewById(R.id.ExpiredDeadlineTw);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + twTypeExp.getText() + "'";
        }
    }
}
