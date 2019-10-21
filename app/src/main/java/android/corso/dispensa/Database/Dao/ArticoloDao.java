package android.corso.dispensa.Database.Dao;

import android.corso.dispensa.Database.Entity.ArticoloEntity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ArticoloDao {

    @Insert
    public Long insertArticolo(ArticoloEntity articoloEntity);

    @Update
    public void updateArticolo(ArticoloEntity articoloEntity);

    @Delete
    public void deleteArticolo(ArticoloEntity articoloEntity);

    @Query("SELECT * FROM articoloentity")
    public List<ArticoloEntity> findAll();



}
