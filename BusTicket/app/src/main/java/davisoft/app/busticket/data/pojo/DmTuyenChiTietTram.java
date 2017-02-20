package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DmTuyenChiTietTram")
public class DmTuyenChiTietTram {
    @DatabaseField
    private Integer  ID;
    @DatabaseField
    private String IDTUYEN;
    @DatabaseField
    private String MATRAM;
    @DatabaseField
    private Boolean TRAMDAU;
    @DatabaseField
    private Boolean TRAMCUOI;



    public Integer getId() {
        return ID;
    }

    public void setId(Integer id) {
        this.ID = id;
    }

    public String getIdTuyen() {
        return IDTUYEN;
    }

    public void setIdTuyen(String idTuyen) {
        this.IDTUYEN = idTuyen;
    }

    public String getMaTram() {
        return MATRAM;
    }

    public void setMaTram(String maTram) {
        this.MATRAM = maTram;
    }

    public Boolean getTramDau() {
        return TRAMDAU;
    }

    public void setTramDau(Boolean tramDau) {
        this.TRAMDAU = tramDau;
    }

    public Boolean getTramCuoi() {
        return TRAMCUOI;
    }

    public void setTramCuoi(Boolean tramCuoi) {
        this.TRAMCUOI = tramCuoi;
    }
}
