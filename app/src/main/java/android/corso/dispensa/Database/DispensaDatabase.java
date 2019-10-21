package android.corso.dispensa.Database;

import android.content.Context;
import android.corso.dispensa.Database.Dao.ArticoloDao;
import android.corso.dispensa.Database.Dao.ProdottoDao;
import android.corso.dispensa.Database.Entity.ArticoloEntity;
import android.corso.dispensa.Database.Entity.ProdottoEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ProdottoEntity.class, ArticoloEntity.class}, version = 1)
public abstract class DispensaDatabase extends RoomDatabase {

    public abstract ProdottoDao getProdottoDao();

    public abstract ArticoloDao getArticoloDao();

    private static DispensaDatabase dispensaDatabase;

    public static DispensaDatabase getInstance(Context context) {
        if (dispensaDatabase == null) {
            dispensaDatabase = Room.databaseBuilder(context, DispensaDatabase.class, "dispensa_database.db").build();
        }
        return dispensaDatabase;
    }
}
