package davisoft.app.busticket.data.pojo;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by ducthien on 22/02/2017.
 */

public class DmHoaDon implements Serializable {
    @DatabaseField(generatedId = true, columnName = "Id")
    public int Id;
    @DatabaseField
    public String IDHOADON;
    @DatabaseField
    public String MAXE;
    @DatabaseField
    public String KYHIEUVE;
    @DatabaseField
    public String MAUSO;
    @DatabaseField
    public int TONGSOVEPHATHANH;
    @DatabaseField
    public int SOVEHIENTAI;
    @DatabaseField
    public int IDVE;

    public DmHoaDon(){

    }
    public DmHoaDon(String IDHOADON,
                          String MAXE,
                          String KYHIEUVE,
                          String MAUSO,
                    String TONGSOVEPHATHANH,
                    String SOVEHIENTAI,
                    String IDVE){


        this.IDHOADON=IDHOADON;
        this.MAXE=MAXE;
        this.KYHIEUVE=KYHIEUVE;
        this.MAUSO=MAUSO;
        this.TONGSOVEPHATHANH=Integer.parseInt(TONGSOVEPHATHANH) ;
        this.SOVEHIENTAI=Integer.parseInt(SOVEHIENTAI) ;
        this.IDVE=Integer.parseInt(IDVE) ;

    }
    public String getIDHOADON() {
        return IDHOADON;
    }

    public void setIDHOADON(String IDHOADON) {
        this.IDHOADON = IDHOADON;
    }

    public String getMAXE() {
        return MAXE;
    }

    public void setMAXE(String MAXE) {
        this.MAXE = MAXE;
    }

    public String getKYHIEUVE() {
        return KYHIEUVE;
    }

    public void setKYHIEUVE(String KYHIEUVE) {
        this.KYHIEUVE = KYHIEUVE;
    }

    public String getMAUSO() {
        return MAUSO;
    }

    public void setMAUSO(String MAUSO) {
        this.MAUSO = MAUSO;
    }

    public int getTONGSOVEPHATHANH() {
        return TONGSOVEPHATHANH;
    }

    public void setTONGSOVEPHATHANH(int TONGSOVEPHATHANH) {
        this.TONGSOVEPHATHANH = TONGSOVEPHATHANH;
    }

    public int getSOVEHIENTAI() {
        return SOVEHIENTAI;
    }

    public void setSOVEHIENTAI(int SOVEHIENTAI) {
        this.SOVEHIENTAI = SOVEHIENTAI;
    }

    public int getIDVE() {
        return IDVE;
    }

    public void setIDVE(int IDVE) {
        this.IDVE = IDVE;
    }
}
