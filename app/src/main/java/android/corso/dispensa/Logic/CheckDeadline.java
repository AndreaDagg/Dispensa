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
    private int CURRENTDAY_PLUS = 0, CURRENTMONTH_PLUS = 0, CURRENTYEAR_PLUS = 0;
    private int TODAY = 1, EXPIRED = 2, INDAYS = 3;

    private Context context;

    private List<ArticoloEntity> articoloEntitiesInfoFromDB, articoloEntitiesExpired = new ArrayList<ArticoloEntity>(),
            getArticoloEntitiesExpiredToday = new ArrayList<ArticoloEntity>(), getArticoloEntitiesByCategory = new ArrayList<ArticoloEntity>(),
            getArticoloEntitiesExpiredInDay = new ArrayList<ArticoloEntity>();


    public CheckDeadline(Context view, int daySelected) {
        super();
        this.context = view;

        Calendar cal = Calendar.getInstance();
        //cal.get(Calendar.DAY_OF_MONTH);

        CURRENTDAY = cal.get(Calendar.DAY_OF_MONTH);
        CURRENTMONTH = cal.get(Calendar.MONTH) + 1;
        CURRENTYEAR = cal.get(Calendar.YEAR);


        getExpiredItem();
        getCURRENTPLUS(daySelected);

    }


    @SuppressLint("StaticFieldLeak")
    private void getExpiredItem() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                articoloEntitiesInfoFromDB = DispensaDatabase.getInstance(context).getArticoloDao().findAll();

                for (int i = 0; i < articoloEntitiesInfoFromDB.size(); i++) {

                    if (articoloEntitiesInfoFromDB.get(i).getYeardeadline() < getCURRENTYEAR()) {
                        addExpired(articoloEntitiesInfoFromDB.get(i), EXPIRED);

                    } else if (articoloEntitiesInfoFromDB.get(i).getYeardeadline() == getCURRENTYEAR()) {
                        if (articoloEntitiesInfoFromDB.get(i).getMonthdeadline() < getCURRENTMONTH()) {
                            addExpired(articoloEntitiesInfoFromDB.get(i), EXPIRED);

                        } else if (articoloEntitiesInfoFromDB.get(i).getMonthdeadline() == getCURRENTMONTH()) {
                            if (articoloEntitiesInfoFromDB.get(i).getDaydeadline() < getCURRENTDAY()) {
                                addExpired(articoloEntitiesInfoFromDB.get(i), EXPIRED);

                            } else if (articoloEntitiesInfoFromDB.get(i).getDaydeadline() == getCURRENTDAY()) {
                                addExpired(articoloEntitiesInfoFromDB.get(i), EXPIRED);
                            }
                        }
                    }
                    int Y_Item = articoloEntitiesInfoFromDB.get(i).getYeardeadline();
                    int M_Item = articoloEntitiesInfoFromDB.get(i).getMonthdeadline() - 1;
                    int D_Item = articoloEntitiesInfoFromDB.get(i).getDaydeadline();

                    int Y_TODAY = getCURRENTYEAR();
                    int M_TODAY = getCURRENTMONTH() - 1;
                    int D_TODAY = getCURRENTDAY();

                    int Y_PLUS = CURRENTYEAR_PLUS;
                    int M_PLUS = CURRENTMONTH_PLUS;
                    int D_PLUS = CURRENTDAY_PLUS;


                    Calendar calendarItemList = Calendar.getInstance();
                    Calendar calendarPlusDay = Calendar.getInstance();
                    Calendar calendarToday = Calendar.getInstance();
                    calendarPlusDay.set(Y_PLUS, M_PLUS, D_PLUS);
                    calendarToday.set(Y_TODAY, M_TODAY, D_TODAY);
                    calendarItemList.set(Y_Item, M_Item, D_Item);

                    if (calendarItemList.after(calendarToday) && calendarItemList.before(calendarPlusDay)) {
                        addExpired(articoloEntitiesInfoFromDB.get(i), INDAYS);
                    }
                }


                return null;
            }
        }.execute();
    }


    private void addExpired(ArticoloEntity articoloEntityExpired, int NUMB) {
        if (NUMB == TODAY) {
            this.getArticoloEntitiesExpiredToday.add(articoloEntityExpired);
        } else if (NUMB == EXPIRED) {
            this.articoloEntitiesExpired.add(articoloEntityExpired);
        } else if (NUMB == INDAYS) {
            this.getArticoloEntitiesExpiredInDay.add(articoloEntityExpired);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public List<ArticoloEntity> getArticoloEntitiesByCategory(final String category, final boolean future) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                if (!future) {
                    for (int i = 0; i < getArticoloEntitiesExpired().size(); i++) {

                        if (getArticoloEntitiesExpired().get(i).getCategoryItem().equals(category)) {
                            getArticoloEntitiesByCategory.add(getArticoloEntitiesExpired().get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < getGetArticoloEntitiesExpiredInDays().size(); i++) {

                        if (getGetArticoloEntitiesExpiredInDays().get(i).getCategoryItem().equals(category)) {
                            getArticoloEntitiesByCategory.add(getGetArticoloEntitiesExpiredInDays().get(i));
                        }
                    }
                }
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

    public List<ArticoloEntity> getGetArticoloEntitiesExpiredInDays() {
        return getArticoloEntitiesExpiredInDay;
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

    public void getCURRENTPLUS(int plus_int) {

        Calendar calendar_plus = Calendar.getInstance();
        calendar_plus.add(Calendar.DAY_OF_MONTH, plus_int);
        CURRENTDAY_PLUS = calendar_plus.get(Calendar.DAY_OF_MONTH);
        CURRENTMONTH_PLUS = calendar_plus.get(Calendar.MONTH);
        CURRENTYEAR_PLUS = calendar_plus.get(Calendar.YEAR);
    }
}