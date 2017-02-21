package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "LoTrinhChoXe")
public class LoTrinhChoXe {
    @DatabaseField
    public Integer IDLOTRINH;
    @DatabaseField
    public String MAXE;
    @DatabaseField
    public String IDTUYEN;
    @DatabaseField
    public String MATAIXE;
    @DatabaseField
    public Boolean CAM;
    @DatabaseField
    public Boolean KICHHOAT;

    public LoTrinhChoXe(String IDLOTRINH,String MAXE,String IDTUYEN,String MATAIXE,String CAM,String KICHHOAT){
        this.IDLOTRINH=Integer.parseInt(IDLOTRINH);
        this.MAXE=MAXE;
        this.IDTUYEN=IDTUYEN;
        this.MATAIXE=MATAIXE ;
        this.CAM=Boolean.parseBoolean(CAM);
        this.KICHHOAT=Boolean.parseBoolean(KICHHOAT);
    }
    public LoTrinhChoXe(){

    }


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
