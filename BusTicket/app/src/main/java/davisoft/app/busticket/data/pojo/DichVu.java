package davisoft.app.busticket.data.pojo;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by ducthien on 22/02/2017.
 */

public class DichVu implements Serializable {
    @DatabaseField(generatedId = true, columnName = "Id")
    public int Id;
    @DatabaseField
    public int ID;
    @DatabaseField
    public String NGAY;
    @DatabaseField
    public String SQMS;
    @DatabaseField
    public String GIOLAYSO;
    @DatabaseField
    public String dichvu1;
    @DatabaseField
    public String GIAVE;
    @DatabaseField
    public String MATUYEN;
    @DatabaseField
    public String TENTUYEN;
    @DatabaseField
    public String MATRAMDAU;
    @DatabaseField
    public String MATRAMCUOI;
    @DatabaseField
    public String LOTRINH;
    @DatabaseField
    public String BienSoXe;
    @DatabaseField
    public String TRANGTHAI;
    @DatabaseField
    public boolean PHUCVU;
    @DatabaseField
    public String GHICHU;
    @DatabaseField
    public boolean Contro;
    @DatabaseField
    public boolean Doc;
    @DatabaseField
    public boolean DATCHO;
    @DatabaseField
    public String GIO_GOC;
    @DatabaseField
    public String BINH_CHON;
    @DatabaseField
    public String GIO_BINHCHON;
    @DatabaseField
    public String NGONNGU;
    @DatabaseField
    public String DIEMGIAODICH;
    @DatabaseField
    public String MANV;
    @DatabaseField
    public String QUAYCHUYEN;
    @DatabaseField
    public String QUAYTHAMCHIEU;
    @DatabaseField
    public String SODIENTHOAI;
    @DatabaseField
    public String QUAY;
    @DatabaseField
    public String GIOPHUCVU;
    @DatabaseField
    public String MAXE;
    @DatabaseField
    public String MATAIXE;
    @DatabaseField
    public String MATRAM;
    @DatabaseField
    public String IDTUYEN;
    @DatabaseField
    public String KYHIEUVE;
    @DatabaseField
    public String MAUSO;
    @DatabaseField
    public String MATRAMGIUA;

    public DichVu()
    {

    }
    public DichVu( String ID,
                   String NGAY,
                   String SQMS,
                   String GIOLAYSO,
                   String dichvu1,
                   String GIAVE,
                   String MATUYEN,
                   String TENTUYEN,
                   String MATRAMDAU,
                   String MATRAMCUOI,
                   String LOTRINH,
                   String BienSoXe,
                   String TRANGTHAI,
                   String PHUCVU,
                   String GHICHU,
                   String Contro,
                   String Doc,
                   String DATCHO,
                   String GIO_GOC,
                   String BINH_CHON,
                   String GIO_BINHCHON,
                   String NGONNGU,
                   String DIEMGIAODICH,
                   String MANV,
                   String QUAYCHUYEN,
                   String QUAYTHAMCHIEU,
                   String SODIENTHOAI,
                   String QUAY,
                   String GIOPHUCVU,
                   String MAXE,
                   String MATAIXE,
                   String MATRAM,
                   String IDTUYEN,
                   String KYHIEUVE,
                   String MAUSO,
                   String MATRAMGIUA)
    {
        this.ID=Integer.parseInt(ID) ;
        this.NGAY=NGAY;
        this.SQMS=SQMS;
        this.GIOLAYSO=GIOLAYSO;
        this.dichvu1=dichvu1;
        this.GIAVE=GIAVE;
        this.MATUYEN=MATUYEN;
        this.TENTUYEN=TENTUYEN;
        this.MATRAMDAU=MATRAMDAU;
        this.MATRAMCUOI=MATRAMCUOI;
        this.LOTRINH=LOTRINH;
        this.BienSoXe=BienSoXe;
        this.TRANGTHAI=TRANGTHAI;
        this.PHUCVU=Boolean.parseBoolean(PHUCVU);
        this.GHICHU=GHICHU;
        this.Contro=Boolean.parseBoolean(Contro);
        this.Doc=Boolean.parseBoolean(Doc);
        this.DATCHO=Boolean.parseBoolean(DATCHO);
        this.GIO_GOC=GIO_GOC;
        this.BINH_CHON=BINH_CHON;
        this.GIO_BINHCHON=GIO_BINHCHON;
        this.NGONNGU=NGONNGU;
        this.DIEMGIAODICH=DIEMGIAODICH;
        this.MANV=MANV;
        this.QUAYCHUYEN=QUAYCHUYEN;
        this.QUAYTHAMCHIEU=QUAYTHAMCHIEU;
        this.SODIENTHOAI=SODIENTHOAI;
        this.QUAY=QUAY;
        this.GIOPHUCVU=GIOPHUCVU;
        this.MAXE=MAXE;
        this.MATAIXE=MATAIXE;
        this.MATRAM=MATRAM;
        this.IDTUYEN=IDTUYEN;
        this.KYHIEUVE=KYHIEUVE;
        this.MAUSO=MAUSO;
        this.MATRAMGIUA=MATRAMGIUA;
    }

    public String getMATRAMDAU() {
        return MATRAMDAU;
    }

    public void setMATRAMDAU(String MATRAMDAU) {
        this.MATRAMDAU = MATRAMDAU;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNGAY() {
        return NGAY;
    }

    public void setNGAY(String NGAY) {
        this.NGAY = NGAY;
    }

    public String getSQMS() {
        return SQMS;
    }

    public void setSQMS(String SQMS) {
        this.SQMS = SQMS;
    }

    public String getGIOLAYSO() {
        return GIOLAYSO;
    }

    public void setGIOLAYSO(String GIOLAYSO) {
        this.GIOLAYSO = GIOLAYSO;
    }

    public String getDichvu1() {
        return dichvu1;
    }

    public void setDichvu1(String dichvu1) {
        this.dichvu1 = dichvu1;
    }

    public String getGIAVE() {
        return GIAVE;
    }

    public void setGIAVE(String GIAVE) {
        this.GIAVE = GIAVE;
    }

    public String getMATUYEN() {
        return MATUYEN;
    }

    public void setMATUYEN(String MATUYEN) {
        this.MATUYEN = MATUYEN;
    }

    public String getTENTUYEN() {
        return TENTUYEN;
    }

    public void setTENTUYEN(String TENTUYEN) {
        this.TENTUYEN = TENTUYEN;
    }

    public String getMATRAMCUOI() {
        return MATRAMCUOI;
    }

    public void setMATRAMCUOI(String MATRAMCUOI) {
        this.MATRAMCUOI = MATRAMCUOI;
    }

    public String getLOTRINH() {
        return LOTRINH;
    }

    public void setLOTRINH(String LOTRINH) {
        this.LOTRINH = LOTRINH;
    }

    public String getBienSoXe() {
        return BienSoXe;
    }

    public void setBienSoXe(String bienSoXe) {
        BienSoXe = bienSoXe;
    }

    public String getTRANGTHAI() {
        return TRANGTHAI;
    }

    public void setTRANGTHAI(String TRANGTHAI) {
        this.TRANGTHAI = TRANGTHAI;
    }

    public boolean isPHUCVU() {
        return PHUCVU;
    }

    public void setPHUCVU(boolean PHUCVU) {
        this.PHUCVU = PHUCVU;
    }

    public String getGHICHU() {
        return GHICHU;
    }

    public void setGHICHU(String GHICHU) {
        this.GHICHU = GHICHU;
    }

    public boolean isContro() {
        return Contro;
    }

    public void setContro(boolean contro) {
        Contro = contro;
    }

    public boolean isDoc() {
        return Doc;
    }

    public void setDoc(boolean doc) {
        Doc = doc;
    }

    public boolean isDATCHO() {
        return DATCHO;
    }

    public void setDATCHO(boolean DATCHO) {
        this.DATCHO = DATCHO;
    }

    public String getGIO_GOC() {
        return GIO_GOC;
    }

    public void setGIO_GOC(String GIO_GOC) {
        this.GIO_GOC = GIO_GOC;
    }

    public String getBINH_CHON() {
        return BINH_CHON;
    }

    public void setBINH_CHON(String BINH_CHON) {
        this.BINH_CHON = BINH_CHON;
    }

    public String getGIO_BINHCHON() {
        return GIO_BINHCHON;
    }

    public void setGIO_BINHCHON(String GIO_BINHCHON) {
        this.GIO_BINHCHON = GIO_BINHCHON;
    }

    public String getNGONNGU() {
        return NGONNGU;
    }

    public void setNGONNGU(String NGONNGU) {
        this.NGONNGU = NGONNGU;
    }

    public String getDIEMGIAODICH() {
        return DIEMGIAODICH;
    }

    public void setDIEMGIAODICH(String DIEMGIAODICH) {
        this.DIEMGIAODICH = DIEMGIAODICH;
    }

    public String getMANV() {
        return MANV;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    }

    public String getQUAYCHUYEN() {
        return QUAYCHUYEN;
    }

    public void setQUAYCHUYEN(String QUAYCHUYEN) {
        this.QUAYCHUYEN = QUAYCHUYEN;
    }

    public String getQUAYTHAMCHIEU() {
        return QUAYTHAMCHIEU;
    }

    public void setQUAYTHAMCHIEU(String QUAYTHAMCHIEU) {
        this.QUAYTHAMCHIEU = QUAYTHAMCHIEU;
    }

    public String getSODIENTHOAI() {
        return SODIENTHOAI;
    }

    public void setSODIENTHOAI(String SODIENTHOAI) {
        this.SODIENTHOAI = SODIENTHOAI;
    }

    public String getQUAY() {
        return QUAY;
    }

    public void setQUAY(String QUAY) {
        this.QUAY = QUAY;
    }

    public String getGIOPHUCVU() {
        return GIOPHUCVU;
    }

    public void setGIOPHUCVU(String GIOPHUCVU) {
        this.GIOPHUCVU = GIOPHUCVU;
    }

    public String getMAXE() {
        return MAXE;
    }

    public void setMAXE(String MAXE) {
        this.MAXE = MAXE;
    }

    public String getMATAIXE() {
        return MATAIXE;
    }

    public void setMATAIXE(String MATAIXE) {
        this.MATAIXE = MATAIXE;
    }

    public String getMATRAM() {
        return MATRAM;
    }

    public void setMATRAM(String MATRAM) {
        this.MATRAM = MATRAM;
    }

    public String getIDTUYEN() {
        return IDTUYEN;
    }

    public void setIDTUYEN(String IDTUYEN) {
        this.IDTUYEN = IDTUYEN;
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

    public String getMATRAMGIUA() {
        return MATRAMGIUA;
    }

    public void setMATRAMGIUA(String MATRAMGIUA) {
        this.MATRAMGIUA = MATRAMGIUA;
    }


}
