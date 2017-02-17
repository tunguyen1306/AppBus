package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DmTuyenChiTietTram")
public class DmTuyenChiTietTram {
    @DatabaseField
    private Integer  id;
    @DatabaseField
    private String idTuyen;
    @DatabaseField
    private String maTram;
    @DatabaseField
    private Boolean tramDau;
    @DatabaseField
    private Boolean tramCuoi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdTuyen() {
        return idTuyen;
    }

    public void setIdTuyen(String idTuyen) {
        this.idTuyen = idTuyen;
    }

    public String getMaTram() {
        return maTram;
    }

    public void setMaTram(String maTram) {
        this.maTram = maTram;
    }

    public Boolean getTramDau() {
        return tramDau;
    }

    public void setTramDau(Boolean tramDau) {
        this.tramDau = tramDau;
    }

    public Boolean getTramCuoi() {
        return tramCuoi;
    }

    public void setTramCuoi(Boolean tramCuoi) {
        this.tramCuoi = tramCuoi;
    }
}
