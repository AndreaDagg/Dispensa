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

    @Query("SELECT * FROM prodottoentity WHERE category LIKE :callby")
    public List<ProdottoEntity> findAllByCategory(String callby);

    @Query("SELECT idbarcode FROM prodottoentity WHERE idbarcode LIKE :barcode")
    public boolean findIdBarcode(Long barcode);

    @Query("SELECT * FROM prodottoentity WHERE idbarcode LIKE :barcode")
    public ProdottoEntity findInfoById(Long barcode);

    @Query("SELECT * FROM prodottoentity WHERE isList LIKE :islist")
    public List<ProdottoEntity> findListShop(Boolean islist);

    @Query("SELECT newBuy FROM prodottoentity WHERE idbarcode LIKE :barcode")
    public int getQuantuityNewBuy(Long barcode);

    @Query("UPDATE prodottoentity SET isList = :islist WHERE idbarcode = :iidbarcode")
    public void updateProdottoIsList(Boolean islist, Long iidbarcode);
    @Query("UPDATE prodottoentity SET newBuy = :quant WHERE idbarcode = :iidbarcode")
    public void updateProdottoQuantity(int quant, Long iidbarcode);
}
