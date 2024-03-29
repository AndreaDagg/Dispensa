package android.corso.dispensa.Fragment.ItemScaduti;

import android.annotation.SuppressLint;
import android.content.Context;
import android.corso.dispensa.Activity.ArticoliScaduti;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.corso.dispensa.Logic.CheckDeadline;
import android.corso.dispensa.Logic.SharedPreferencesApp;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.corso.dispensa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class expiredItemFragment extends Fragment {

    private String CALLBY;
    private boolean TODAY;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    public expiredItemFragment(String callby, boolean today) {
        this.CALLBY = callby;
        this.TODAY = today;
    }

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
            final Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            new AsyncTask<Void,Void,List<ArticoloEntity>>(){
                @Override
                protected List<ArticoloEntity> doInBackground(Void... voids) {

                    return new CheckDeadline(context,new SharedPreferencesApp(getContext()).getDayFuture()).getArticoloEntitiesByCategory(CALLBY, TODAY);
                }

                @Override 
                protected void onPostExecute(final List<ArticoloEntity> articoloEntities) {
                    super.onPostExecute(articoloEntities);
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(new MyexpiredItemRecyclerViewAdapter(articoloEntities, mListener));
                        }
                    });

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
        void onListFragmentInteraction(ArticoloEntity item);
    }
}
