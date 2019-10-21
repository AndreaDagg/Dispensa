package android.corso.dispensa.Database.Dao;

import android.corso.dispensa.Database.Entity.ProdottoEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProdottoDao {
    @Insert
    public Long insertProdotto(ProdottoEntity prodottoEntity);

    @Update
    public void updateProdotto(ProdottoEntity prodottoEntity);

    @Delete
    public void deleteProdotto(ProdottoEntity prodottoEntity);

    @Query("SELECT * FROM prodotto_entity")
    public List<ProdottoEntity> findAll();
}
