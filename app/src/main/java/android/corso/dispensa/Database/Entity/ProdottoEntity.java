package android.corso.dispensa.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Blob;

@Entity(tableName = "prodottoentity")
public class ProdottoEntity {


    @PrimaryKey(autoGenerate = true)
    private Long _id;
    private Long idbarcode;
    private String category;
    private String brand;
    private String producttype;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    private boolean isList;
    private int newBuy;
    private String note;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getIdbarcode() {
        return idbarcode;
    }

    public void setIdbarcode(@NonNull Long idbarcode) {
        this.idbarcode = idbarcode;
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

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
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
