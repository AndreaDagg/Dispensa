package android.corso.dispensa.Database.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "prodotto_entity")
public class ProdottoEntity {


    @PrimaryKey
    private Long idBarcode;   //TODO:It's not autoGenerate we have to do the check before the insert
    private String category;
    private String brand;
    private String productType;

    //private Bitmap image; TODO: Trovare un metodo per salvare bitmap nel db

    private boolean isList;
    private int newBuy;
    private String note;

    public Long getIdBarcode() {
        return idBarcode;
    }

    public void setIdBarcode(Long idBarcode) {
        this.idBarcode = idBarcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public int getNewBuy() {
        return newBuy;
    }

    public void setNewBuy(int newBuy) {
        this.newBuy = newBuy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
