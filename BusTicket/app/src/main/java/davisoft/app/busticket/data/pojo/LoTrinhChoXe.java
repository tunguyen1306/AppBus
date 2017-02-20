package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "LoTrinhChoXe")
public class LoTrinhChoXe {
    @DatabaseField
    private Integer IDLOTRINH;
    @DatabaseField
    private String MAXE;
    @DatabaseField
    private String IDTUYEN;
    @DatabaseField
    private String MATAIXE;
    @DatabaseField
    private Boolean CAM;
    @DatabaseField
    private Boolean KICHHOAT;



    public Integer getIdLoTrinh() {
        return IDLOTRINH;
    }

    public void setIdLoTrinh(Integer idLoTrinh) {
        this.IDLOTRINH = idLoTrinh;
    }

    public String getMaXe() {
        return MAXE;
    }

    public void setMaXe(String maXe) {
        this.MAXE = maXe;
    }

    public String getIdTuyen() {
        return IDTUYEN;
    }

    public void setIdTuyen(String idTuyen) {
        this.IDTUYEN = idTuyen;
    }

    public String getMaTaiXe() {
        return MATAIXE;
    }

    public void setMaTaiXe(String maTaiXe) {
        this.MATAIXE = maTaiXe;
    }

    public Boolean getCam() {
        return CAM;
    }

    public void setCam(Boolean cam) {
        this.CAM = cam;
    }

    public Boolean getKichHoat() {
        return KICHHOAT;
    }

    public void setKichHoat(Boolean kichHoat) {
        this.KICHHOAT = kichHoat;
    }
}
