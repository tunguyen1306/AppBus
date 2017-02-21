package davisoft.app.busticket.data.pojo;



import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DmXe")
public class DmXe {
    @DatabaseField
    public String MAXE;
    @DatabaseField
    public String SOXE;
    @DatabaseField
    public String LOAIXE;
    @DatabaseField
    public String MATAIXE;
    @DatabaseField
    public String SOGHE;

    public DmXe(){

    }
    public DmXe(String MAXE,String SOXE,String LOAIXE,String MATAIXE,String SOGHE){
        this.MAXE=MAXE;
        this.SOXE=SOXE;
        this.LOAIXE=LOAIXE;
        this.MATAIXE=MATAIXE;
        this.SOGHE=SOGHE;

    }
    public String getMaXe() {
        return MAXE;
    }

    public void setMaXe(String maXe) {
        this.MAXE = maXe;
    }

    public String getSoXe() {
        return SOXE;
    }

    public void setSoXe(String soXe) {
        this.SOXE = soXe;
    }

    public String getLoaiXe() {
        return LOAIXE;
    }

    public void setLoaiXe(String loaiXe) {
        this.LOAIXE = loaiXe;
    }

    public String getMaTaiXe() {
        return MATAIXE;
    }

    public void setMaTaiXe(String maTaiXe) {
        this.MATAIXE = maTaiXe;
    }

    public String getSoGhe() {
        return SOGHE;
    }

    public void setSoGhe(String soGhe) {
        this.SOGHE = soGhe;
    }
}
