package android.corso.dispensa.Database.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "articoloentity")
public class ArticoloEntity {


    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long barcode;
    private int daydeadline;
    private int monthdeadline;
    private int yeardeadline;
    private int used;
    private String categoryItem;

    public String getCategoryItem() {
        return categoryItem;
    }

    public void setCategoryItem(String categoryItem) {
        this.categoryItem = categoryItem;
    }

    public int getDaydeadline() {
        return daydeadline;
    }

    public void setDaydeadline(int daydeadline) {
        this.daydeadline = daydeadline;
    }

    public int getMonthdeadline() {
        return monthdeadline;
    }

    public void setMonthdeadline(int monthdeadline) {
        this.monthdeadline = monthdeadline;
    }

    public int getYeardeadline() {
        return yeardeadline;
    }

    public void setYeardeadline(int yeardeadline) {
        this.yeardeadline = yeardeadline;
    }

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

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
