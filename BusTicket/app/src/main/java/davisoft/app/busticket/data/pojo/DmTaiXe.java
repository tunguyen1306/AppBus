package davisoft.app.busticket.data.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DmTaiXe")
public class DmTaiXe {
    @DatabaseField
    private String maTaiXe;
    @DatabaseField
    private String tenTaiXe;
    @DatabaseField
    private Integer tuoi;
    @DatabaseField
    private String gioiTinh;
    @DatabaseField
    private String bangLai;
    @DatabaseField
    private String sdt;
    @DatabaseField
    private String diaChiNoiO;
    @DatabaseField
    private String email;

    public String getMaTaiXe() {
        return maTaiXe;
    }

    public void setMaTaiXe(String maTaiXe) {
        this.maTaiXe = maTaiXe;
    }

    public String getTenTaiXe() {
        return tenTaiXe;
    }

    public void setTenTaiXe(String tenTaiXe) {
        this.tenTaiXe = tenTaiXe;
    }

    public Integer getTuoi() {
        return tuoi;
    }

    public void setTuoi(Integer tuoi) {
        this.tuoi = tuoi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getBangLai() {
        return bangLai;
    }

    public void setBangLai(String bangLai) {
        this.bangLai = bangLai;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChiNoiO() {
        return diaChiNoiO;
    }

    public void setDiaChiNoiO(String diaChiNoiO) {
        this.diaChiNoiO = diaChiNoiO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
