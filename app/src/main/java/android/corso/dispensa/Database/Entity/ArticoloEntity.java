package android.corso.dispensa.Database.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "articoloentity")
public class ArticoloEntity {


    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long barcode;
    private Long deadline;
    private int used;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBarcode() {
        return barcode;
    }

    public void setBarcode(Long barcode) {
        this.barcode = barcode;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
