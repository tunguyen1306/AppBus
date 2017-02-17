package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "LoTrinhChoXe")
public class LoTrinhChoXe {
    @DatabaseField
    private Integer idLoTrinh;
    @DatabaseField
    private String maXe;
    @DatabaseField
    private String idTuyen;
    @DatabaseField
    private String maTaiXe;
    @DatabaseField
    private Boolean cam;
    @DatabaseField
    private Boolean kichHoat;

    public Integer getIdLoTrinh() {
        return idLoTrinh;
    }

    public void setIdLoTrinh(Integer idLoTrinh) {
        this.idLoTrinh = idLoTrinh;
    }

    public String getMaXe() {
        return maXe;
    }

    public void setMaXe(String maXe) {
        this.maXe = maXe;
    }

    public String getIdTuyen() {
        return idTuyen;
    }

    public void setIdTuyen(String idTuyen) {
        this.idTuyen = idTuyen;
    }

    public String getMaTaiXe() {
        return maTaiXe;
    }

    public void setMaTaiXe(String maTaiXe) {
        this.maTaiXe = maTaiXe;
    }

    public Boolean getCam() {
        return cam;
    }

    public void setCam(Boolean cam) {
        this.cam = cam;
    }

    public Boolean getKichHoat() {
        return kichHoat;
    }

    public void setKichHoat(Boolean kichHoat) {
        this.kichHoat = kichHoat;
    }
}
