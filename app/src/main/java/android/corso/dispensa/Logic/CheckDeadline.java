package android.corso.dispensa.Logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.corso.dispensa.Database.DispensaDatabase;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckDeadline {
    private int CURRENTDAY = 0;
    private int CURRENTMONTH = 0;
    private int CURRENTYEAR = 0;
    private Context context;

    private List<ArticoloEntity> articoloEntitiesInfoFromDB, articoloEntitiesExpired = new ArrayList<ArticoloEntity>(),
            getArticoloEntitiesExpiredToday = new ArrayList<ArticoloEntity>(), getArticoloEntitiesByCategory = new ArrayList<ArticoloEntity>();


    public CheckDeadline(Context view) {
        super();
        this.context = view;

        Calendar cal = Calendar.getInstance();
        //cal.get(Calendar.DAY_OF_MONTH);

        CURRENTDAY = cal.get(Calendar.DAY_OF_MONTH);
        CURRENTMONTH = cal.get(Calendar.MONTH) + 1;
        CURRENTYEAR = cal.get(Calendar.YEAR);

        getExpiredItem();
    }


    @SuppressLint("StaticFieldLeak")
    private void getExpiredItem() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                articoloEntitiesInfoFromDB = DispensaDatabase.getInstance(context).getArticoloDao().findAll();

                for (int i = 0; i < articoloEntitiesInfoFromDB.size(); i++) {

                    if (articoloEntitiesInfoFromDB.get(i).getYeardeadline() < getCURRENTYEAR()) {
                        //Scaduto
                       /* Log.d("Scaduto: ", articoloEntitiesInfoFromDB.get(i).getId().toString() + " || " + articoloEntitiesInfoFromDB.get(i).getBarcode().toString());
                        Log.d("S=> ", articoloEntitiesInfoFromDB.get(i).getDaydeadline() + " " + articoloEntitiesInfoFromDB.get(i).getMonthdeadline() + " " + articoloEntitiesInfoFromDB.get(i).getYeardeadline());*/

                        addExpired(articoloEntitiesInfoFromDB.get(i), false);

                    } else if (articoloEntitiesInfoFromDB.get(i).getYeardeadline() == getCURRENTYEAR()) {
                        if (articoloEntitiesInfoFromDB.get(i).getMonthdeadline() < getCURRENTMONTH()) {
                            //Scaduto
                           /* Log.d("Scaduto: ", articoloEntitiesInfoFromDB.get(i).getId().toString() + " || " + articoloEntitiesInfoFromDB.get(i).getBarcode().toString());
                            Log.d("S=> ", articoloEntitiesInfoFromDB.get(i).getDaydeadline() + " " + articoloEntitiesInfoFromDB.get(i).getMonthdeadline() + " " + articoloEntitiesInfoFromDB.get(i).getYeardeadline());*/

                            addExpired(articoloEntitiesInfoFromDB.get(i), false);
                        } else if (articoloEntitiesInfoFromDB.get(i).getMonthdeadline() == getCURRENTMONTH()) {
                            if (articoloEntitiesInfoFromDB.get(i).getDaydeadline() < getCURRENTDAY()) {
                                //Scaduto
                               /* Log.d("Scaduto: ", articoloEntitiesInfoFromDB.get(i).getId().toString() + " || " + articoloEntitiesInfoFromDB.get(i).getBarcode().toString());
                                Log.d("S=> ", articoloEntitiesInfoFromDB.get(i).getDaydeadline() + " " + articoloEntitiesInfoFromDB.get(i).getMonthdeadline() + " " + articoloEntitiesInfoFromDB.get(i).getYeardeadline());*/

                                addExpired(articoloEntitiesInfoFromDB.get(i), false);

                            } else if (articoloEntitiesInfoFromDB.get(i).getDaydeadline() == getCURRENTDAY()) {
                                //Scade oggi
                               /* Log.d("Scaduto OGGI: ", articoloEntitiesInfoFromDB.get(i).getId().toString() + " || " + articoloEntitiesInfoFromDB.get(i).getBarcode().toString());
                                Log.d("S=> ", articoloEntitiesInfoFromDB.get(i).getDaydeadline() + " " + articoloEntitiesInfoFromDB.get(i).getMonthdeadline() + " " + articoloEntitiesInfoFromDB.get(i).getYeardeadline());*/
                                addExpired(articoloEntitiesInfoFromDB.get(i), true);
                            }
                        }
                    }

                    //    Log.d("--------------------------------", "|");

                }


                //Log.d("->**", articoloEntitiesInfoFromDB.toString());


                return null;
            }
        }.execute();
    }


    private void addExpired(ArticoloEntity articoloEntityExpired, boolean today) {
        if (today) {
            this.getArticoloEntitiesExpiredToday.add(articoloEntityExpired);
        } else {
            this.articoloEntitiesExpired.add(articoloEntityExpired);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public List<ArticoloEntity> getArticoloEntitiesByCategory(final String category, final boolean today) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                if (!today) {
                    for (int i = 0; i < getArticoloEntitiesExpired().size(); i++) {

                        if (getArticoloEntitiesExpired().get(i).getCategoryItem().equals(category)) {

                            getArticoloEntitiesByCategory.add(getArticoloEntitiesExpired().get(i));
                        }

                    }
                } else {
                    for (int i = 0; i < getArticoloEntitiesExpiredToday().size(); i++) {

                        if (getArticoloEntitiesExpiredToday().get(i).getCategoryItem().equals(category)) {

                            getArticoloEntitiesByCategory.add(getArticoloEntitiesExpiredToday().get(i));
                        }

                    }
                }

                //TODO:se vuoi passeare anche gli scaduti del giorno basta riscrivere un for
                //Log.d("--", getArticoloEntitiesByCategory + "");
                return null;
            }
        }.execute();

        return getArticoloEntitiesByCategory;
    }

    public List<ArticoloEntity> getArticoloEntitiesExpired() {
        return articoloEntitiesExpired;
    }

    public List<ArticoloEntity> getArticoloEntitiesExpiredToday() {
        return getArticoloEntitiesExpiredToday;
    }

    public int getdayplus(int add) {

        Date curretndate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(curretndate);

        cal.add(Calendar.DAY_OF_MONTH, add);

        Date date = cal.getTime();

       /* Log.d("-------------------------------", "---------");
        Log.d("Oggi_e'", curretndate + "");
        Log.d("Tra_", +add + " giorni sarà: " + date);
        Log.d("-------------------------------", "---------");*/

        return 0;
    }

    public int getCURRENTDAY() {
        return CURRENTDAY;
    }

    public int getCURRENTMONTH() {
        return CURRENTMONTH;
    }

    public int getCURRENTYEAR() {
        return CURRENTYEAR;
    }
}
