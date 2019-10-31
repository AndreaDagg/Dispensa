package android.corso.dispensa.Fragment.ItemScaduti;

import android.annotation.SuppressLint;
import android.content.Context;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.corso.dispensa.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class expiredItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;
    private OnListFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expireditem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

                    ArticoloEntity articoloEntity = new ArticoloEntity();


                    //TODO:Valutare di cambuiare il list con un json
                    List<ProdottoEntity> prodottoEntities = null;

                    JSONArray productsExpiredJson = new JSONArray();


                    //TODO: IF scaduto
                    //Per ora tutti scaduti e predno solo ilBarcode

                    //Recupre gli idBarcode
                    List<ArticoloEntity> articoloEntities = DispensaDatabase.getInstance(getContext()).getArticoloDao().findAll();
                    for (int i = 0; i < articoloEntities.size(); i++) {
                        //perogni barcode recupero le info del prodotto
                        //prodottoEntities.add(DispensaDatabase.getInstance(getContext()).getProdottoDao().findInfoById(articoloEntities.get(i).getId()));
                        ProdottoEntity productInfo = DispensaDatabase.getInstance(getContext()).getProdottoDao().findInfoById(articoloEntities.get(i).getBarcode());
                        JSONObject productJson = new JSONObject();
                       try {
                            productJson.put("idBarcode", productInfo.getIdbarcode());
                            productJson.put("Brand", productInfo.getBrand());
                            productJson.put("Type", productInfo.getProducttype());
                            productJson.put("scadenza",
                                    articoloEntities.get(i).getDaydeadline() + "/" + articoloEntities.get(i).getMonthdeadline() + "/" + articoloEntities.get(i).getYeardeadline());
                            productsExpiredJson.put(productJson);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       // Log.d("-> ", i + " " +productInfo.getIdbarcode().toString());
                        //Log.d("-> ", i + " " + articoloEntities.size());
                        //Log.d("--> ",articoloEntities.get(i).getBarcode()+"");


                    }


                    recyclerView.setAdapter(new MyexpiredItemRecyclerViewAdapter(productsExpiredJson, mListener));


                    return null;
                }
            }.execute();

        }
        return view;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(JSONArray item);
    }
}
