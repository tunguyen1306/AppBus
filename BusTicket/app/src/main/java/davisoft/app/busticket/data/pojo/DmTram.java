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

    public String getLatLng() {
        return LatLng;
    }

    public void setLatLng(String latLng) {
        LatLng = latLng;
    }

    public String LatLng;
    public String getFileTram() {
        return FileTram;
    }

    public void setFileTram(String fileTram) {
        FileTram = fileTram;
    }

    public String FileTram;
    public DmTram(){

    }

    @Override
    public DmTram clone() throws CloneNotSupportedException {
        return (DmTram) super.clone();
    }

    public DmTram(String ID, String MATRAM, String TENTRAM,String FILETRAM,String LATLNG){
    this.ID=Integer.parseInt(ID);
    this.MATRAM=MATRAM;
    this.TENTRAM=TENTRAM;
     if (FILETRAM!=null)
        this.FileTram=FILETRAM.indexOf(".wav")>-1?FILETRAM:FILETRAM+".wav";
        else
        this.FileTram="";
    this.LatLng=LATLNG;
    if (this.LatLng!=null && this.LatLng.trim().length()>0)
    {
        setLAT(Double.valueOf(this.getLatLng().split(";")[0]));
        setLNG(Double.valueOf(this.getLatLng().split(";")[1]));
    }

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
