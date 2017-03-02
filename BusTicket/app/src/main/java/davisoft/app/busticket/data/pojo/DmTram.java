package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

public class DmTram implements Serializable {
    @DatabaseField(generatedId = true, columnName = "Id")
    public int Id;
    @DatabaseField
    public Integer ID;
    @DatabaseField
    public String MATRAM;
    @DatabaseField
    public String TENTRAM;
    public double LAT;
    public double LNG;
    public DmTram(){

    }
public DmTram(String ID,String MATRAM,String TENTRAM){
    this.ID=Integer.parseInt(ID);
    this.MATRAM=MATRAM;
    this.TENTRAM=TENTRAM;
}
    public Integer getId() {
        return ID;
    }

    public void setId(Integer id) {
        this.ID = id;
    }

    public String getMaTram() {
        return MATRAM;
    }

    public void setMaTram(String maTram) {
        this.MATRAM = maTram;
    }

    public String getTenTram() {
        return TENTRAM;
    }

    public void setTenTram(String tenTram) {
        this.TENTRAM = tenTram;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }

    public double getLNG() {
        return LNG;
    }

    public void setLNG(double LNG) {
        this.LNG = LNG;
    }
}
