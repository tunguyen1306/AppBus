package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DmTuyenChiTietTram")
public class DmTuyenChiTietTram {
    @DatabaseField
    public Integer  ID;
    @DatabaseField
    public String IDTUYEN;
    @DatabaseField
    public String MATRAM;
    @DatabaseField
    public Boolean TRAMDAU;
    @DatabaseField
    public Boolean TRAMCUOI;

public DmTuyenChiTietTram(String ID,String IDTUYEN,String MATRAM,String TRAMDAU,String TRAMCUOI){
             this.ID=Integer.parseInt(ID);
            this.IDTUYEN=IDTUYEN;
            this.MATRAM=MATRAM;
            this.TRAMDAU=Boolean.parseBoolean(TRAMDAU) ;
            this.TRAMCUOI=Boolean.parseBoolean(TRAMCUOI);
}
    public DmTuyenChiTietTram(){

    }
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
