package android.corso.dispensa.Database.Entity;



import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "articolo_entity")
public class ArticoloEntity {


    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long barCode;
    private String deadline;
    private int used;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBarCode() {
        return barCode;
    }

    public void setBarCode(Long barCode) {
        this.barCode = barCode;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
