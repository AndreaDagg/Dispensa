package android.corso.dispensa.Fragment.ItemsFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ProdottoEntity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.corso.dispensa.R;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemFragmentHead.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ItemFragmentHead extends Fragment {


    private OnFragmentInteractionListener mListener;

    public ItemFragmentHead() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_item, container, false);
        Bundle bundle = this.getArguments();
        final Long _idItem = (Long) bundle.get("idItem");



                new AsyncTask<Void, Void, ProdottoEntity>() {
                    @Override
                    protected ProdottoEntity doInBackground(Void... voids) {
                        ProdottoEntity prodottoEntity = new ProdottoEntity();
                        prodottoEntity = DispensaDatabase.getInstance(getContext()).getProdottoDao().findInfoById(_idItem);


                        return prodottoEntity;
                    }


                    @Override
                    protected void onPostExecute(final ProdottoEntity prodottoEntity) {
                        super.onPostExecute(prodottoEntity);

                                TextView textViewItem = (TextView) view.findViewById(R.id.ItemFragmentItem);
                                TextView textViewBrand = (TextView) view.findViewById(R.id.ItemFragmentBrand);
                                final TextView textViewQuantity = (TextView) view.findViewById(R.id.ItemFragmentQuantity);
                                ImageView imageViewPicture = (ImageView) view.findViewById(R.id.ItemFragmentImage);
                                textViewItem.setText(prodottoEntity.getProducttype());
                                textViewBrand.setText(prodottoEntity.getBrand());
                                new AsyncTask<Void,Void,Integer>(){
                                    @Override
                                    protected Integer doInBackground(Void... voids) {
                                        return DispensaDatabase.getInstance(getContext()).getArticoloDao().CountItemByBarcode(_idItem);
                                    }

                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    protected void onPostExecute(Integer integer) {
                                        super.onPostExecute(integer);
                                        textViewQuantity.setText("Quantità: " + integer);
                                        if (integer == 0){
                                            textViewQuantity.setTextColor(view.getResources().getColor(R.color.redLight,view.getContext().getTheme()));
                                        }
                                    }
                                }.execute();


                                if (prodottoEntity.getImage() != null) {
                                    Bitmap bitmapImage = BitmapFactory.decodeByteArray(prodottoEntity.getImage(), 0, prodottoEntity.getImage().length);
                                    bitmapImage = Bitmap.createScaledBitmap(bitmapImage, 120, 190, true);
                                    imageViewPicture.setImageBitmap(bitmapImage);
                                } else {
                                    Toast toast = Toast.makeText(getContext(), "Nessuna immagine trovata", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                    }
                }.execute();



        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
