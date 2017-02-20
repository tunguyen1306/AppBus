package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DmTram")
public class DmTram {
    @DatabaseField
    private Integer ID;
    @DatabaseField
    private String MATRAM;
    @DatabaseField
    private String TENTRAM;
public DmTram(String ID,String MATRAM,String TENTRAM){
    this.ID=Integer.parseInt(ID);
    this.MATRAM=MATRAM;
    this.TENTRAM=TENTRAM;
}
    public Integer getId() {
        return ID;
    }

    public void setId(Integer id) {
        this.ID = id;
    }

    public String getMaTram() {
        return MATRAM;
    }

    public void setMaTram(String maTram) {
        this.MATRAM = maTram;
    }

    public String getTenTram() {
        return TENTRAM;
    }

    public void setTenTram(String tenTram) {
        this.TENTRAM = tenTram;
    }
}
