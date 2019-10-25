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

    @Query("SELECT * FROM prodottoentity")
    public List<ProdottoEntity> findAll();

    @Query("SELECT idbarcode FROM prodottoentity WHERE idbarcode LIKE :barcode")
    public boolean findIdBarcode(Long barcode);

    @Query("SELECT * FROM prodottoentity WHERE idbarcode LIKE :barcode")
    public ProdottoEntity findInfoById(Long barcode);
}
