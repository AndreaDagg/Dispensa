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

    @Query("SELECT * FROM articoloentity WHERE barcode LIKE :barcodeid")
    public List<ArticoloEntity> findByBarcode(Long barcodeid);

    @Query("SELECT COUNT(barcode) FROM articoloentity WHERE barcode LIKE :barcodeid")
    public int CountItemByBarcode(Long barcodeid);

    @Query("SELECT * FROM articoloentity WHERE id LIKE :iditem")
    public ArticoloEntity findItemByIdItem(Long iditem);


   /* @Query("SELECT *, MIN(yeardeadline) as yearcurrent FROM articoloentity")
    public List<ArticoloEntity> getExpired(int yearcurrent);*/

   /*@Query("SELECT id,barcode,daydeadline,monthdeadline,yeardeadline FROM articoloentity")
   public List<ArticoloEntity> getExpiredInfo();*/


}
