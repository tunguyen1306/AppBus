package davisoft.app.busticket.data.pojo;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable(tableName = "DmTuyen")
public class DmTuyen {
    @DatabaseField
    private String IDTUYEN;
    @DatabaseField
    private String MATUYEN;
    @DatabaseField
    private String TENTUYENEND;
    @DatabaseField
    private String TENTUYENVN;
    @DatabaseField
    private String MATRAMDAU;
    @DatabaseField
    private String MATRAMGIUA;
    @DatabaseField
    private String MATRAMCUOI;
    @DatabaseField
    private String TONGTRAM;
    @DatabaseField
    private Integer MUCDO;
    @DatabaseField
    private Date THOIGIANTOANTRAM;
    @DatabaseField
    private double GIAVE1;
    @DatabaseField
    private String DIENGIAIVE1;
    @DatabaseField
    private boolean  CAMVE1;
    @DatabaseField
    private String IDVE1IDHOADON;
    @DatabaseField
    private double GIAVE2;
    @DatabaseField
    private String DIENGIAIVE2;
    @DatabaseField
    private boolean  CAMVE2;
    @DatabaseField
    private String IDVE2IDHOADON;
    @DatabaseField
    private double GIAVE3;
    @DatabaseField
    private String DIENGIAIVE3;
    @DatabaseField
    private boolean  CAMVE3;
    @DatabaseField
    private String IDVE3IDHOADON;
    @DatabaseField
    private double GIAVE4;
    @DatabaseField
    private String DIENGIAIVE4;
    @DatabaseField
    private boolean  CAMVE4;
    @DatabaseField
    private String IDVE4IDHOADON;
    @DatabaseField
    private double GIAVE5;
    @DatabaseField
    private String DIENGIAIVE5;
    @DatabaseField
    private boolean  CAMVE5;
    @DatabaseField
    private String IDVE5IDHOADON;
    @DatabaseField
    private double GIAVE6;
    @DatabaseField
    private String DIENGIAIVE6;
    @DatabaseField
    private boolean  CAMVE6;
    @DatabaseField
    private String IDVE6IDHOADON;


    public  DmTuyen(String IDTUYEN,String MATUYEN,String TENTUYENEND,String TENTUYENVN,String MATRAMDAU,String MATRAMGIUA,String MATRAMCUOI,String String,String MUCDO,String THOIGIANTOANTRAM,String GIAVE1,String DIENGIAIVE1,String  CAMVE1,String IDVE1IDHOADON,String GIAVE2,String DIENGIAIVE2,String  CAMVE2,String IDVE2IDHOADON,String GIAVE3,String DIENGIAIVE3,String  CAMVE3,String IDVE3IDHOADON,String GIAVE4,String DIENGIAIVE4,String  CAMVE4,String IDVE4IDHOADON,String GIAVE5,String DIENGIAIVE5,String  CAMVE5,String IDVE5IDHOADON,String GIAVE6,String DIENGIAIVE6,String  CAMVE6,String IDVE6IDHOADON)
    {
        this.IDTUYEN=IDTUYEN;
        this.MATUYEN=MATUYEN;
        this.TENTUYENEND=TENTUYENEND;
        this.TENTUYENVN=TENTUYENVN;
        this.MATRAMDAU=MATRAMDAU;
        this.MATRAMGIUA=MATRAMGIUA;
        this.MATRAMCUOI=MATRAMCUOI;
        this.TONGTRAM=TONGTRAM;
        this.MUCDO=Integer.parseInt(MUCDO) ;
        this.THOIGIANTOANTRAM=changeToate(THOIGIANTOANTRAM) ;
        this.GIAVE1=Double.parseDouble(GIAVE1) ;
        this.DIENGIAIVE1=DIENGIAIVE1;
        this.CAMVE1=Boolean.parseBoolean(CAMVE1) ;
        this.IDVE1IDHOADON=IDVE1IDHOADON;
        this.GIAVE2=Double.parseDouble(GIAVE2) ;
        this.DIENGIAIVE2=DIENGIAIVE2;
        this.CAMVE2=Boolean.parseBoolean(CAMVE2);
        this.IDVE2IDHOADON=IDVE2IDHOADON;
        this.GIAVE3=Double.parseDouble(GIAVE3) ;
        this.DIENGIAIVE3=DIENGIAIVE3;
        this.CAMVE3=Boolean.parseBoolean(CAMVE3);
        this.IDVE3IDHOADON=IDVE3IDHOADON;
        this.GIAVE4=Double.parseDouble(GIAVE4) ;
        this.DIENGIAIVE4=DIENGIAIVE4;
        this.CAMVE4=Boolean.parseBoolean(CAMVE5);
        this.IDVE4IDHOADON=IDVE4IDHOADON;
        this.GIAVE5=Double.parseDouble(GIAVE5) ;
        this.DIENGIAIVE5=DIENGIAIVE5;
        this.CAMVE5=Boolean.parseBoolean(CAMVE5);
        this.IDVE5IDHOADON=IDVE5IDHOADON;
        this.GIAVE6=Double.parseDouble(GIAVE6) ;
        this.DIENGIAIVE6=DIENGIAIVE6;
        this.CAMVE6=Boolean.parseBoolean(CAMVE6);
        this.IDVE6IDHOADON=IDVE6IDHOADON;




    }

public Date changeToate( String dateString)
{
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
    Date convertedDate = new Date();
    try {
        convertedDate = dateFormat.parse(dateString);
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return convertedDate;
}
    public String GETIDTUYEN() {
        return IDTUYEN;
    }

    public void SETIDTUYEN(String IDTUYEN) {
        this.IDTUYEN = IDTUYEN;
    }

    public String GETMATUYEN() {
        return MATUYEN;
    }

    public void SETMATUYEN(String MATUYEN) {
        this.MATUYEN = MATUYEN;
    }

    public String GETTENTUYENEND() {
        return TENTUYENEND;
    }

    public void SETTENTUYENEND(String TENTUYENEND) {
        this.TENTUYENEND = TENTUYENEND;
    }

    public String GETTENTUYENVN() {
        return TENTUYENVN;
    }

    public void SETTENTUYENVN(String TENTUYENVN) {
        this.TENTUYENVN = TENTUYENVN;
    }

    public String GETMATRAMDAU() {
        return MATRAMDAU;
    }

    public void SETMATRAMDAU(String MATRAMDAU) {
        this.MATRAMDAU = MATRAMDAU;
    }

    public String GETMATRAMGIUA() {
        return MATRAMGIUA;
    }

    public void SETMATRAMGIUA(String MATRAMGIUA) {
        this.MATRAMGIUA = MATRAMGIUA;
    }

    public String GETMATRAMCUOI() {
        return MATRAMCUOI;
    }

    public void SETMATRAMCUOI(String MATRAMCUOI) {
        this.MATRAMCUOI = MATRAMCUOI;
    }

    public String GETTONGTRAM() {
        return TONGTRAM;
    }

    public void SETTONGTRAM(String TONGTRAM) {
        this.TONGTRAM = TONGTRAM;
    }

    public Integer GETMUCDO() {
        return MUCDO;
    }

    public void SETMUCDO(Integer MUCDO) {
        this.MUCDO = MUCDO;
    }

    public Date GETTHOIGIANTOANTRAM() {
        return THOIGIANTOANTRAM;
    }

    public void SETTHOIGIANTOANTRAM(Date THOIGIANTOANTRAM) {
        this.THOIGIANTOANTRAM = THOIGIANTOANTRAM;
    }

    public double GETGIAVE1() {
        return GIAVE1;
    }

    public void SETGIAVE1(double GIAVE1) {
        this.GIAVE1 = GIAVE1;
    }

    public String GETDIENGIAIVE1() {
        return DIENGIAIVE1;
    }

    public void SETDIENGIAIVE1(String DIENGIAIVE1) {
        this.DIENGIAIVE1 = DIENGIAIVE1;
    }

    public boolean  GETCAMVE1() {
        return CAMVE1;
    }

    public void SETCAMVE1(boolean  CAMVE1) {
        this.CAMVE1 = CAMVE1;
    }

    public String GETIDVE1IDHOADON() {
        return IDVE1IDHOADON;
    }

    public void SETIDVE1IDHOADON(String IDVE1IDHOADON) {
        this.IDVE1IDHOADON = IDVE1IDHOADON;
    }

    public double GETGIAVE2() {
        return GIAVE2;
    }

    public void SETGIAVE2(double GIAVE2) {
        this.GIAVE2 = GIAVE2;
    }

    public String GETDIENGIAIVE2() {
        return DIENGIAIVE2;
    }

    public void SETDIENGIAIVE2(String DIENGIAIVE2) {
        this.DIENGIAIVE2 = DIENGIAIVE2;
    }

    public boolean  GETCAMVE2() {
        return CAMVE2;
    }

    public void SETCAMVE2(boolean  CAMVE2) {
        this.CAMVE2 = CAMVE2;
    }

    public String GETIDVE2IDHOADON() {
        return IDVE2IDHOADON;
    }

    public void SETIDVE2IDHOADON(String IDVE2IDHOADON) {
        this.IDVE2IDHOADON = IDVE2IDHOADON;
    }

    public double GETGIAVE3() {
        return GIAVE3;
    }

    public void SETGIAVE3(double GIAVE3) {
        this.GIAVE3 = GIAVE3;
    }

    public String GETDIENGIAIVE3() {
        return DIENGIAIVE3;
    }

    public void SETDIENGIAIVE3(String DIENGIAIVE3) {
        this.DIENGIAIVE3 = DIENGIAIVE3;
    }

    public boolean  GETCAMVE3() {
        return CAMVE3;
    }

    public void SETCAMVE3(boolean  CAMVE3) {
        this.CAMVE3 = CAMVE3;
    }

    public String GETIDVE3IDHOADON() {
        return IDVE3IDHOADON;
    }

    public void SETIDVE3IDHOADON(String IDVE3IDHOADON) {
        this.IDVE3IDHOADON = IDVE3IDHOADON;
    }

    public double GETGIAVE4() {
        return GIAVE4;
    }

    public void SETGIAVE4(double GIAVE4) {
        this.GIAVE4 = GIAVE4;
    }

    public String GETDIENGIAIVE4() {
        return DIENGIAIVE4;
    }

    public void SETDIENGIAIVE4(String DIENGIAIVE4) {
        this.DIENGIAIVE4 = DIENGIAIVE4;
    }

    public boolean  GETCAMVE4() {
        return CAMVE4;
    }

    public void SETCAMVE4(boolean  CAMVE4) {
        this.CAMVE4 = CAMVE4;
    }

    public String GETIDVE4IDHOADON() {
        return IDVE4IDHOADON;
    }

    public void SETIDVE4IDHOADON(String IDVE4IDHOADON) {
        this.IDVE4IDHOADON = IDVE4IDHOADON;
    }

    public double GETGIAVE5() {
        return GIAVE5;
    }

    public void SETGIAVE5(double GIAVE5) {
        this.GIAVE5 = GIAVE5;
    }

    public String GETDIENGIAIVE5() {
        return DIENGIAIVE5;
    }

    public void SETDIENGIAIVE5(String DIENGIAIVE5) {
        this.DIENGIAIVE5 = DIENGIAIVE5;
    }

    public boolean  GETCAMVE5() {
        return CAMVE5;
    }

    public void SETCAMVE5(boolean  CAMVE5) {
        this.CAMVE5 = CAMVE5;
    }

    public String GETIDVE5IDHOADON() {
        return IDVE5IDHOADON;
    }

    public void SETIDVE5IDHOADON(String IDVE5IDHOADON) {
        this.IDVE5IDHOADON = IDVE5IDHOADON;
    }

    public double GETGIAVE6() {
        return GIAVE6;
    }

    public void SETGIAVE6(double GIAVE6) {
        this.GIAVE6 = GIAVE6;
    }

    public String GETDIENGIAIVE6() {
        return DIENGIAIVE6;
    }

    public void SETDIENGIAIVE6(String DIENGIAIVE6) {
        this.DIENGIAIVE6 = DIENGIAIVE6;
    }

    public boolean  GETCAMVE6() {
        return CAMVE6;
    }

    public void SETCAMVE6(boolean  CAMVE6) {
        this.CAMVE6 = CAMVE6;
    }

    public String GETIDVE6IDHOADON() {
        return IDVE6IDHOADON;
    }

    public void SETIDVE6IDHOADON(String IDVE6IDHOADON) {
        this.IDVE6IDHOADON = IDVE6IDHOADON;
    }


}
