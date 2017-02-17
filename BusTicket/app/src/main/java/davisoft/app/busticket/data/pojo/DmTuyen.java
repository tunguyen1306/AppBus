package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "DmTuyen")
public class DmTuyen {
    @DatabaseField
    private String idTuyen;
    @DatabaseField
    private String maTuyen;
    @DatabaseField
    private String tenTuyenEnd;
    @DatabaseField
    private String tenTuyenVN;
    @DatabaseField
    private String maTramDau;
    @DatabaseField
    private String maTramGiua;
    @DatabaseField
    private String maTramCuoi;
    @DatabaseField
    private String tongTram;
    @DatabaseField
    private Integer mucDo;
    @DatabaseField
    private Date thoiGianToanTram;
    @DatabaseField
    private Double giaVe1;
    @DatabaseField
    private String dienGiaiVe1;
    @DatabaseField
    private Boolean camVe1;
    @DatabaseField
    private String idVe1IdHoaDon;
    @DatabaseField
    private Double giaVe2;
    @DatabaseField
    private String dienGiaiVe2;
    @DatabaseField
    private Boolean camVe2;
    @DatabaseField
    private String idVe2IdHoaDon;
    @DatabaseField
    private Double giaVe3;
    @DatabaseField
    private String dienGiaiVe3;
    @DatabaseField
    private Boolean camVe3;
    @DatabaseField
    private String idVe3IdHoaDon;
    @DatabaseField
    private Double giaVe4;
    @DatabaseField
    private String dienGiaiVe4;
    @DatabaseField
    private Boolean camVe4;
    @DatabaseField
    private String idVe4IdHoaDon;
    @DatabaseField
    private Double giaVe5;
    @DatabaseField
    private String dienGiaiVe5;
    @DatabaseField
    private Boolean camVe5;
    @DatabaseField
    private String idVe5IdHoaDon;
    @DatabaseField
    private Double giaVe6;
    @DatabaseField
    private String dienGiaiVe6;
    @DatabaseField
    private Boolean camVe6;
    @DatabaseField
    private String idVe6IdHoaDon;

    public String getIdTuyen() {
        return idTuyen;
    }

    public void setIdTuyen(String idTuyen) {
        this.idTuyen = idTuyen;
    }

    public String getMaTuyen() {
        return maTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        this.maTuyen = maTuyen;
    }

    public String getTenTuyenEnd() {
        return tenTuyenEnd;
    }

    public void setTenTuyenEnd(String tenTuyenEnd) {
        this.tenTuyenEnd = tenTuyenEnd;
    }

    public String getTenTuyenVN() {
        return tenTuyenVN;
    }

    public void setTenTuyenVN(String tenTuyenVN) {
        this.tenTuyenVN = tenTuyenVN;
    }

    public String getMaTramDau() {
        return maTramDau;
    }

    public void setMaTramDau(String maTramDau) {
        this.maTramDau = maTramDau;
    }

    public String getMaTramGiua() {
        return maTramGiua;
    }

    public void setMaTramGiua(String maTramGiua) {
        this.maTramGiua = maTramGiua;
    }

    public String getMaTramCuoi() {
        return maTramCuoi;
    }

    public void setMaTramCuoi(String maTramCuoi) {
        this.maTramCuoi = maTramCuoi;
    }

    public String getTongTram() {
        return tongTram;
    }

    public void setTongTram(String tongTram) {
        this.tongTram = tongTram;
    }

    public Integer getMucDo() {
        return mucDo;
    }

    public void setMucDo(Integer mucDo) {
        this.mucDo = mucDo;
    }

    public Date getThoiGianToanTram() {
        return thoiGianToanTram;
    }

    public void setThoiGianToanTram(Date thoiGianToanTram) {
        this.thoiGianToanTram = thoiGianToanTram;
    }

    public Double getGiaVe1() {
        return giaVe1;
    }

    public void setGiaVe1(Double giaVe1) {
        this.giaVe1 = giaVe1;
    }

    public String getDienGiaiVe1() {
        return dienGiaiVe1;
    }

    public void setDienGiaiVe1(String dienGiaiVe1) {
        this.dienGiaiVe1 = dienGiaiVe1;
    }

    public Boolean getCamVe1() {
        return camVe1;
    }

    public void setCamVe1(Boolean camVe1) {
        this.camVe1 = camVe1;
    }

    public String getIdVe1IdHoaDon() {
        return idVe1IdHoaDon;
    }

    public void setIdVe1IdHoaDon(String idVe1IdHoaDon) {
        this.idVe1IdHoaDon = idVe1IdHoaDon;
    }

    public Double getGiaVe2() {
        return giaVe2;
    }

    public void setGiaVe2(Double giaVe2) {
        this.giaVe2 = giaVe2;
    }

    public String getDienGiaiVe2() {
        return dienGiaiVe2;
    }

    public void setDienGiaiVe2(String dienGiaiVe2) {
        this.dienGiaiVe2 = dienGiaiVe2;
    }

    public Boolean getCamVe2() {
        return camVe2;
    }

    public void setCamVe2(Boolean camVe2) {
        this.camVe2 = camVe2;
    }

    public String getIdVe2IdHoaDon() {
        return idVe2IdHoaDon;
    }

    public void setIdVe2IdHoaDon(String idVe2IdHoaDon) {
        this.idVe2IdHoaDon = idVe2IdHoaDon;
    }

    public Double getGiaVe3() {
        return giaVe3;
    }

    public void setGiaVe3(Double giaVe3) {
        this.giaVe3 = giaVe3;
    }

    public String getDienGiaiVe3() {
        return dienGiaiVe3;
    }

    public void setDienGiaiVe3(String dienGiaiVe3) {
        this.dienGiaiVe3 = dienGiaiVe3;
    }

    public Boolean getCamVe3() {
        return camVe3;
    }

    public void setCamVe3(Boolean camVe3) {
        this.camVe3 = camVe3;
    }

    public String getIdVe3IdHoaDon() {
        return idVe3IdHoaDon;
    }

    public void setIdVe3IdHoaDon(String idVe3IdHoaDon) {
        this.idVe3IdHoaDon = idVe3IdHoaDon;
    }

    public Double getGiaVe4() {
        return giaVe4;
    }

    public void setGiaVe4(Double giaVe4) {
        this.giaVe4 = giaVe4;
    }

    public String getDienGiaiVe4() {
        return dienGiaiVe4;
    }

    public void setDienGiaiVe4(String dienGiaiVe4) {
        this.dienGiaiVe4 = dienGiaiVe4;
    }

    public Boolean getCamVe4() {
        return camVe4;
    }

    public void setCamVe4(Boolean camVe4) {
        this.camVe4 = camVe4;
    }

    public String getIdVe4IdHoaDon() {
        return idVe4IdHoaDon;
    }

    public void setIdVe4IdHoaDon(String idVe4IdHoaDon) {
        this.idVe4IdHoaDon = idVe4IdHoaDon;
    }

    public Double getGiaVe5() {
        return giaVe5;
    }

    public void setGiaVe5(Double giaVe5) {
        this.giaVe5 = giaVe5;
    }

    public String getDienGiaiVe5() {
        return dienGiaiVe5;
    }

    public void setDienGiaiVe5(String dienGiaiVe5) {
        this.dienGiaiVe5 = dienGiaiVe5;
    }

    public Boolean getCamVe5() {
        return camVe5;
    }

    public void setCamVe5(Boolean camVe5) {
        this.camVe5 = camVe5;
    }

    public String getIdVe5IdHoaDon() {
        return idVe5IdHoaDon;
    }

    public void setIdVe5IdHoaDon(String idVe5IdHoaDon) {
        this.idVe5IdHoaDon = idVe5IdHoaDon;
    }

    public Double getGiaVe6() {
        return giaVe6;
    }

    public void setGiaVe6(Double giaVe6) {
        this.giaVe6 = giaVe6;
    }

    public String getDienGiaiVe6() {
        return dienGiaiVe6;
    }

    public void setDienGiaiVe6(String dienGiaiVe6) {
        this.dienGiaiVe6 = dienGiaiVe6;
    }

    public Boolean getCamVe6() {
        return camVe6;
    }

    public void setCamVe6(Boolean camVe6) {
        this.camVe6 = camVe6;
    }

    public String getIdVe6IdHoaDon() {
        return idVe6IdHoaDon;
    }

    public void setIdVe6IdHoaDon(String idVe6IdHoaDon) {
        this.idVe6IdHoaDon = idVe6IdHoaDon;
    }
}
