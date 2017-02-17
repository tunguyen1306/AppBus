package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DmTram")
public class DmTram {
    @DatabaseField
    private Integer id;
    @DatabaseField
    private String maTram;
    @DatabaseField
    private String tenTram;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaTram() {
        return maTram;
    }

    public void setMaTram(String maTram) {
        this.maTram = maTram;
    }

    public String getTenTram() {
        return tenTram;
    }

    public void setTenTram(String tenTram) {
        this.tenTram = tenTram;
    }
}
