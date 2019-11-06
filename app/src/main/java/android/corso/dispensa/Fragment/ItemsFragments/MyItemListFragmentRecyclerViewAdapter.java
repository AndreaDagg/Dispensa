package android.corso.dispensa.Fragment.ItemsFragments;


import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.corso.dispensa.Activity.AlimentiActivity.DispensaAlimentiActivity;

import android.corso.dispensa.Activity.FarmaciActivity.DispensaFarmaciActivity;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.Dialog.ConfirmDeleteItem;
import android.corso.dispensa.Dialog.ConfirmDeleteItemAlim;
import android.corso.dispensa.Dialog.ConfirmDeleteItemFarm;
import android.corso.dispensa.Logic.CategoryItem;
import android.corso.dispensa.R;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.corso.dispensa.Fragment.ItemsFragments.ItemListFragment.OnListFragmentInteractionListener;
import android.widget.Toast;
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
        if (mValues.get(position).isUsed()) {
            holder.mUsateButton.setText("Usato");
        } else {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public final View mView;
        public final TextView mDeadline;
        public final Button mUsateButton;

        public ArticoloEntity mItem;

        public ViewHolder(final View view) {
            super(view);
            mView = view;
            mDeadline = (TextView) view.findViewById(R.id.ItemDeadline);
            mUsateButton = (Button) view.findViewById(R.id.ItemButtonUsed);
            //associate a menu with the element
            mView.setOnCreateContextMenuListener(this);
            mUsateButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onClick(View v) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {

                            DispensaDatabase.getInstance(view.getContext()).getArticoloDao().updateUsedById(mItem.getId(), true);

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

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            if (mItem.getCategoryItem().equals(new CategoryItem().getCATEGORY_ALI())) {
                final DispensaAlimentiActivity dispensaAlimentiActivity = (DispensaAlimentiActivity) v.getContext();

                MenuInflater inflater = new MenuInflater(v.getContext());
                inflater.inflate(R.menu.expireditemmenu, menu);

                MenuItem move = menu.findItem(R.id.menumove);
                move.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                DispensaDatabase.getInstance(mView.getContext()).getProdottoDao().updateProdottoIsList(true, mItem.getBarcode());
                                DispensaDatabase.getInstance(mView.getContext()).getProdottoDao().updateProdottoQuantity(
                                        DispensaDatabase.getInstance(mView.getContext()).getProdottoDao().getQuantuityNewBuy(mItem.getBarcode()) + 1, mItem.getBarcode());
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                Toast.makeText(mView.getContext(), "Aggiunto a lista della spesa!", Toast.LENGTH_SHORT).show();
                            }
                        }.execute();

                        return false;
                    }
                });

                MenuItem remove = menu.findItem(R.id.menudelete);
                remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ConfirmDeleteItemAlim confirmDeleteItemAlim = new ConfirmDeleteItemAlim();
                        confirmDeleteItemAlim.setContext(mView.getContext());
                        confirmDeleteItemAlim.setIdItem(mItem.getId(), mItem.getCategoryItem(),mItem.getBarcode() ,dispensaAlimentiActivity);
                        confirmDeleteItemAlim.show(dispensaAlimentiActivity.getSupportFragmentManager(), "viewHolderDisp");

                        return false;
                    }
                });

            } else {
                final DispensaFarmaciActivity dispensaFarmaciActivity = (DispensaFarmaciActivity) v.getContext();

                MenuInflater inflater = new MenuInflater(v.getContext());
                inflater.inflate(R.menu.expireditemmenu, menu);

                MenuItem move = menu.findItem(R.id.menumove);
                move.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                DispensaDatabase.getInstance(mView.getContext()).getProdottoDao().updateProdottoIsList(true, mItem.getBarcode());
                                DispensaDatabase.getInstance(mView.getContext()).getProdottoDao().updateProdottoQuantity(
                                        DispensaDatabase.getInstance(mView.getContext()).getProdottoDao().getQuantuityNewBuy(mItem.getBarcode()) + 1, mItem.getBarcode());
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                Toast.makeText(mView.getContext(), "Aggiunto a lista della spesa!", Toast.LENGTH_SHORT).show();
                            }
                        }.execute();

                        return false;
                    }
                });

                MenuItem remove = menu.findItem(R.id.menudelete);
                remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ConfirmDeleteItemFarm confirmDeleteItemFarm = new ConfirmDeleteItemFarm();
                        confirmDeleteItemFarm.setContext(mView.getContext());
                        confirmDeleteItemFarm.setIdItem(mItem.getId(), mItem.getCategoryItem(),mItem.getBarcode() ,dispensaFarmaciActivity);
                        confirmDeleteItemFarm.show(dispensaFarmaciActivity.getSupportFragmentManager(), "viewHolderDisp");

                        return false;
                    }
                });
            }


        }
    }
}
