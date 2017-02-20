package davisoft.app.busticket.data.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DmTaiXe")
public class DmTaiXe {
    @DatabaseField
    private String MATAIXE;
    @DatabaseField
    private String TENTAIXE;
    @DatabaseField
    private Integer TUOI;
    @DatabaseField
    private String GIOITINH;
    @DatabaseField
    private String BANGLAI;
    @DatabaseField
    private String SDT;
    @DatabaseField
    private String DIACHINOIO;
    @DatabaseField
    private String EMAIL;
public DmTaiXe(String MATAIXE,String TENTAIXE,String TUOI,String GIOITINH,String BANGLAI,String SDT,String DIACHINOIO,String EMAIL){
    this.MATAIXE=MATAIXE;
    this.TENTAIXE=TENTAIXE;
    this.TUOI=Integer.parseInt(TUOI);
    this.GIOITINH=GIOITINH;
    this.BANGLAI=BANGLAI;
    this.SDT=SDT;
    this.DIACHINOIO=DIACHINOIO;
    this.EMAIL=EMAIL;

}

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getMATAIXE() {
        return MATAIXE;
    }

    public void setMATAIXE(String MATAIXE) {
        this.MATAIXE = MATAIXE;
    }

    public String getTENTAIXE() {
        return TENTAIXE;
    }

    public void setTENTAIXE(String TENTAIXE) {
        this.TENTAIXE = TENTAIXE;
    }

    public Integer getTUOI() {
        return TUOI;
    }

    public void setTUOI(Integer TUOI) {
        this.TUOI = TUOI;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public String getBANGLAI() {
        return BANGLAI;
    }

    public void setBANGLAI(String BANGLAI) {
        this.BANGLAI = BANGLAI;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDIACHINOIO() {
        return DIACHINOIO;
    }

    public void setDIACHINOIO(String DIACHINOIO) {
        this.DIACHINOIO = DIACHINOIO;
    }



}
