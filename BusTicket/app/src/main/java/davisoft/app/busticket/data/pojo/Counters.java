package davisoft.app.busticket.data.pojo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Counters implements Serializable

    {
    @DatabaseField(generatedId = true, columnName = "Id")
    public int Id;
//    @DatabaseField
//    public String counter1;
//    @DatabaseField
//    public String counter2;
//    @DatabaseField
//    public String counter3;
//    @DatabaseField
//    public String counter4;
//    @DatabaseField
//    public String counter5;
//    @DatabaseField
//    public String counter6;
//    @DatabaseField
//    public String counter7;
//    @DatabaseField
//    public String counter8;
//    @DatabaseField
//    public String counter9;
//    @DatabaseField
//    public String counter10;
//    @DatabaseField
//    public String counter11;
//    @DatabaseField
//    public String counter12;
//    @DatabaseField
//    public String counter13;
//    @DatabaseField
//    public String counter14;
//    @DatabaseField
//    public String counter15;
//    @DatabaseField
//    public String counter16;
//    @DatabaseField
//    public String counter17;
//    @DatabaseField
//    public String counter18;
//    @DatabaseField
//    public String counter19;
//    @DatabaseField
//    public String counter20;
//    @DatabaseField
//    public String counter21;
//    @DatabaseField
//    public String counter22;
//    @DatabaseField
//    public String counter23;
//    @DatabaseField
//    public String counter24;
//    @DatabaseField
//    public String counter25;
//    @DatabaseField
//    public String counter26;
//    @DatabaseField
//    public String counter27;
//    @DatabaseField
//    public String counter28;
//    @DatabaseField
//    public String counter29;
//    @DatabaseField
//    public String counter30;
//    @DatabaseField
//    public String mark;
//    @DatabaseField
//    public String pathLoa1;
//    @DatabaseField
//    public Boolean camLoa1;
//    @DatabaseField
//    public String pathLoa2;
//    @DatabaseField
//    public Boolean camLoa2;
//    @DatabaseField
//    public String pathLoa3;
//    @DatabaseField
//    public Boolean camLoa3;
//    @DatabaseField
//    public String pathLoa4;
//    @DatabaseField
//    public Boolean camLoa4;
//    @DatabaseField
//    public String pathLoa5;
//    @DatabaseField
//    public Boolean camLoa5;
//    @DatabaseField
//    public String pathLoa6;
//    @DatabaseField
//    public Boolean camLoa6;
//    @DatabaseField
//    public String pathLoa7;
//    @DatabaseField
//    public Boolean camLoa7;
//    @DatabaseField
//    public String pathLoa8;
//    @DatabaseField
//    public Boolean camLoa8;
//    @DatabaseField
//    public String pathLoa9;
//    @DatabaseField
//    public Boolean camLoa9;
//    @DatabaseField
//    public Integer amLuongVolumn;
//    @DatabaseField
//    public Boolean camValumn;
//    @DatabaseField
//    public Integer amLuongWave;
//    @DatabaseField
//    public Boolean camWave;
//    @DatabaseField
//    public String portgiaoTiep;
//    @DatabaseField
//    public Boolean camPort;
//    @DatabaseField
//    public String kieuPrint;
//    @DatabaseField
//    public Boolean camPrint;
//    @DatabaseField
//    public Integer mainDisplay;
//    @DatabaseField
//    public Integer totalPrinter;
//    @DatabaseField
//    public String logoPath;
//    @DatabaseField(dataType = DataType.BYTE_ARRAY)
//    public Byte[] logo;
//    @DatabaseField
//    public Boolean camLogo;
//    @DatabaseField
//    public String tenCty;
//    @DatabaseField
//    public String diaChi;
//    @DatabaseField
//    public String companyName;
//    @DatabaseField
//    public String address;
//    @DatabaseField
//    public String path;
//    @DatabaseField
//    public String direction1;
//    @DatabaseField
//    public String direction2;
//    @DatabaseField
//    public String direction3;
//    @DatabaseField
//    public String direction4;
//    @DatabaseField
//    public String direction5;
//    @DatabaseField
//    public String direction6;
//    @DatabaseField
//    public String direction7;
//    @DatabaseField
//    public String direction8;
//    @DatabaseField
//    public String direction9;
//    @DatabaseField
//    public String direction10;
//    @DatabaseField
//    public String direction11;
//    @DatabaseField
//    public String direction12;
//    @DatabaseField
//    public String direction13;
//    @DatabaseField
//    public String direction14;
//    @DatabaseField
//    public String direction15;
//    @DatabaseField
//    public String direction16;
//    @DatabaseField
//    public String direction17;
//    @DatabaseField
//    public String direction18;
//    @DatabaseField
//    public String direction19;
//    @DatabaseField
//    public String direction20;
    @DatabaseField
    public String CopyRight;
    @DatabaseField()
    public String LogoCopyRight;
    @DatabaseField
    public String CopyRightKey;
    @DatabaseField
    public Boolean ChedoInCSDL;
    @DatabaseField
    public Boolean ChedoInDefault;
    @DatabaseField
    public Boolean SetDefaultIn;
    @DatabaseField
    public Boolean MutiServices;
    @DatabaseField
    public Boolean TransferAuto;
    @DatabaseField
    public Date TransferTime;
    @DatabaseField
    public String PGDCode;
    @DatabaseField
    public Boolean AutoNext;
    @DatabaseField
    public Boolean BackupSQLserver;
    @DatabaseField
    public String TieudeVN;
    @DatabaseField
    public String TieudeENG;
    @DatabaseField
    public String MST;
    @DatabaseField
    public String DT;
    @DatabaseField
    public String DCPRINT;
    @DatabaseField
    public String MAXE;
    @DatabaseField
    public String Luot;
    @DatabaseField
    public String Lastday;

        public String getTenCongTy() {
            return TenCongTy;
        }

        public void setTenCongTy(String tenCongTy) {
            TenCongTy = tenCongTy;
        }

        public String getDiaChi() {
            return DiaChi;
        }

        public void setDiaChi(String diaChi) {
            DiaChi = diaChi;
        }

        @DatabaseField
    public String TenCongTy;
    @DatabaseField
    public String DiaChi;
    public Counters()
    {

    }

    public Counters(String CopyRight,
                    String LogoCopyRight,
                    String CopyRightKey,
                    String ChedoInCSDL,
                    String ChedoInDefault,
                    String SetDefaultIn,
                    String MutiServices,
                    String TransferAuto,
                    String TransferTime,
                    String PGDCode,
                    String AutoNext,
                    String BackupSQLserver,
                    String TieudeVN,
                    String TieudeENG,
                    String MST,
                    String DT,
                    String DCPRINT,
                    String MAXE,
                    String Luot,
                    String Lastday,String TenCongTy ,String DiaChi    )
    {
        this.CopyRight=CopyRight;
        this.LogoCopyRight=LogoCopyRight;
        this.CopyRightKey=CopyRightKey;
        this.ChedoInCSDL=Boolean.parseBoolean(ChedoInCSDL);
        this.ChedoInDefault=Boolean.parseBoolean(ChedoInDefault);
        this.SetDefaultIn=Boolean.parseBoolean(SetDefaultIn);
        this.MutiServices=Boolean.parseBoolean(MutiServices);
        this.TransferAuto=Boolean.parseBoolean(TransferAuto);
        this.TransferTime=changeToate(TransferTime);
        this.PGDCode=PGDCode;
        this.AutoNext=Boolean.parseBoolean(AutoNext);
        this.BackupSQLserver=Boolean.parseBoolean(BackupSQLserver);
        this.TieudeVN=TieudeVN;
        this.TieudeENG=TieudeENG;
        this.MST=MST;
        this.DT=DT;
        this.DCPRINT=DCPRINT;
        this.MAXE=MAXE;
        this.Luot=Luot;
        this.Lastday=Lastday;
        this.TenCongTy=TenCongTy;
        this.DiaChi=DiaChi;

    }



    public static Date changeToate( String dateString)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return convertedDate;
    }
    public Boolean getBackupSQLserver() {
        return BackupSQLserver;
    }

    public void setBackupSQLserver(Boolean backupSQLserver) {
        BackupSQLserver = backupSQLserver;
    }

    public String getCopyRight() {
        return CopyRight;
    }

    public void setCopyRight(String copyRight) {
        CopyRight = copyRight;
    }

    public String getLogoCopyRight() {
        return LogoCopyRight;
    }

    public void setLogoCopyRight(String logoCopyRight) {
        LogoCopyRight = logoCopyRight;
    }

    public String getCopyRightKey() {
        return CopyRightKey;
    }

    public void setCopyRightKey(String copyRightKey) {
        CopyRightKey = copyRightKey;
    }

    public Boolean getChedoInCSDL() {
        return ChedoInCSDL;
    }

    public void setChedoInCSDL(Boolean chedoInCSDL) {
        ChedoInCSDL = chedoInCSDL;
    }

    public Boolean getChedoInDefault() {
        return ChedoInDefault;
    }

    public void setChedoInDefault(Boolean chedoInDefault) {
        ChedoInDefault = chedoInDefault;
    }

    public Boolean getSetDefaultIn() {
        return SetDefaultIn;
    }

    public void setSetDefaultIn(Boolean setDefaultIn) {
        SetDefaultIn = setDefaultIn;
    }

    public Boolean getMutiServices() {
        return MutiServices;
    }

    public void setMutiServices(Boolean mutiServices) {
        MutiServices = mutiServices;
    }

    public Boolean getTransferAuto() {
        return TransferAuto;
    }

    public void setTransferAuto(Boolean transferAuto) {
        TransferAuto = transferAuto;
    }

    public Date getTransferTime() {
        return TransferTime;
    }

    public void setTransferTime(Date transferTime) {
        TransferTime = transferTime;
    }

    public String getPGDCode() {
        return PGDCode;
    }

    public void setPGDCode(String PGDCode) {
        this.PGDCode = PGDCode;
    }

    public Boolean getAutoNext() {
        return AutoNext;
    }

    public void setAutoNext(Boolean autoNext) {
        AutoNext = autoNext;
    }

    public String getTieudeVN() {
        return TieudeVN;
    }

    public void setTieudeVN(String tieudeVN) {
        TieudeVN = tieudeVN;
    }

    public String getTieudeENG() {
        return TieudeENG;
    }

    public void setTieudeENG(String tieudeENG) {
        TieudeENG = tieudeENG;
    }

    public String getMST() {
        return MST;
    }

    public void setMST(String MST) {
        this.MST = MST;
    }

    public String getDT() {
        return DT;
    }

    public void setDT(String DT) {
        this.DT = DT;
    }

    public String getDCPRINT() {
        return DCPRINT;
    }

    public void setDCPRINT(String DCPRINT) {
        this.DCPRINT = DCPRINT;
    }

    public String getMAXE() {
        return MAXE;
    }

    public void setMAXE(String MAXE) {
        this.MAXE = MAXE;
    }

    public String getLuot() {
        return Luot;
    }

    public void setLuot(String luot) {
        Luot = luot;
    }

    public String getLastday() {
        return Lastday;
    }

    public void setLastday(String lastday) {
        Lastday = lastday;
    }


//    public String getCounter1() {
//        return counter1;
//    }
//
//    public void setCounter1(String counter1) {
//        this.counter1 = counter1;
//    }
//
//    public String getCounter2() {
//        return counter2;
//    }
//
//    public void setCounter2(String counter2) {
//        this.counter2 = counter2;
//    }
//
//    public String getCounter3() {
//        return counter3;
//    }
//
//    public void setCounter3(String counter3) {
//        this.counter3 = counter3;
//    }
//
//    public String getCounter4() {
//        return counter4;
//    }
//
//    public void setCounter4(String counter4) {
//        this.counter4 = counter4;
//    }
//
//    public String getCounter5() {
//        return counter5;
//    }
//
//    public void setCounter5(String counter5) {
//        this.counter5 = counter5;
//    }
//
//    public String getCounter6() {
//        return counter6;
//    }
//
//    public void setCounter6(String counter6) {
//        this.counter6 = counter6;
//    }
//
//    public String getCounter7() {
//        return counter7;
//    }
//
//    public void setCounter7(String counter7) {
//        this.counter7 = counter7;
//    }
//
//    public String getCounter8() {
//        return counter8;
//    }
//
//    public void setCounter8(String counter8) {
//        this.counter8 = counter8;
//    }
//
//    public String getCounter9() {
//        return counter9;
//    }
//
//    public void setCounter9(String counter9) {
//        this.counter9 = counter9;
//    }
//
//    public String getCounter10() {
//        return counter10;
//    }
//
//    public void setCounter10(String counter10) {
//        this.counter10 = counter10;
//    }
//
//    public String getCounter11() {
//        return counter11;
//    }
//
//    public void setCounter11(String counter11) {
//        this.counter11 = counter11;
//    }
//
//    public String getCounter12() {
//        return counter12;
//    }
//
//    public void setCounter12(String counter12) {
//        this.counter12 = counter12;
//    }
//
//    public String getCounter13() {
//        return counter13;
//    }
//
//    public void setCounter13(String counter13) {
//        this.counter13 = counter13;
//    }
//
//    public String getCounter14() {
//        return counter14;
//    }
//
//    public void setCounter14(String counter14) {
//        this.counter14 = counter14;
//    }
//
//    public String getCounter15() {
//        return counter15;
//    }
//
//    public void setCounter15(String counter15) {
//        this.counter15 = counter15;
//    }
//
//    public String getCounter16() {
//        return counter16;
//    }
//
//    public void setCounter16(String counter16) {
//        this.counter16 = counter16;
//    }
//
//    public String getCounter17() {
//        return counter17;
//    }
//
//    public void setCounter17(String counter17) {
//        this.counter17 = counter17;
//    }
//
//    public String getCounter18() {
//        return counter18;
//    }
//
//    public void setCounter18(String counter18) {
//        this.counter18 = counter18;
//    }
//
//    public String getCounter19() {
//        return counter19;
//    }
//
//    public void setCounter19(String counter19) {
//        this.counter19 = counter19;
//    }
//
//    public String getCounter20() {
//        return counter20;
//    }
//
//    public void setCounter20(String counter20) {
//        this.counter20 = counter20;
//    }
//
//    public String getCounter21() {
//        return counter21;
//    }
//
//    public void setCounter21(String counter21) {
//        this.counter21 = counter21;
//    }
//
//    public String getCounter22() {
//        return counter22;
//    }
//
//    public void setCounter22(String counter22) {
//        this.counter22 = counter22;
//    }
//
//    public String getCounter23() {
//        return counter23;
//    }
//
//    public void setCounter23(String counter23) {
//        this.counter23 = counter23;
//    }
//
//    public String getCounter24() {
//        return counter24;
//    }
//
//    public void setCounter24(String counter24) {
//        this.counter24 = counter24;
//    }
//
//    public String getCounter25() {
//        return counter25;
//    }
//
//    public void setCounter25(String counter25) {
//        this.counter25 = counter25;
//    }
//
//    public String getCounter26() {
//        return counter26;
//    }
//
//    public void setCounter26(String counter26) {
//        this.counter26 = counter26;
//    }
//
//    public String getCounter27() {
//        return counter27;
//    }
//
//    public void setCounter27(String counter27) {
//        this.counter27 = counter27;
//    }
//
//    public String getCounter28() {
//        return counter28;
//    }
//
//    public void setCounter28(String counter28) {
//        this.counter28 = counter28;
//    }
//
//    public String getCounter29() {
//        return counter29;
//    }
//
//    public void setCounter29(String counter29) {
//        this.counter29 = counter29;
//    }
//
//    public String getCounter30() {
//        return counter30;
//    }
//
//    public void setCounter30(String counter30) {
//        this.counter30 = counter30;
//    }
//
//    public String getMark() {
//        return mark;
//    }
//
//    public void setMark(String mark) {
//        this.mark = mark;
//    }
//
//    public String getPathLoa1() {
//        return pathLoa1;
//    }
//
//    public void setPathLoa1(String pathLoa1) {
//        this.pathLoa1 = pathLoa1;
//    }
//
//    public Boolean getCamLoa1() {
//        return camLoa1;
//    }
//
//    public void setCamLoa1(Boolean camLoa1) {
//        this.camLoa1 = camLoa1;
//    }
//
//    public String getPathLoa2() {
//        return pathLoa2;
//    }
//
//    public void setPathLoa2(String pathLoa2) {
//        this.pathLoa2 = pathLoa2;
//    }
//
//    public Boolean getCamLoa2() {
//        return camLoa2;
//    }
//
//    public void setCamLoa2(Boolean camLoa2) {
//        this.camLoa2 = camLoa2;
//    }
//
//    public String getPathLoa3() {
//        return pathLoa3;
//    }
//
//    public void setPathLoa3(String pathLoa3) {
//        this.pathLoa3 = pathLoa3;
//    }
//
//    public Boolean getCamLoa3() {
//        return camLoa3;
//    }
//
//    public void setCamLoa3(Boolean camLoa3) {
//        this.camLoa3 = camLoa3;
//    }
//
//    public String getPathLoa4() {
//        return pathLoa4;
//    }
//
//    public void setPathLoa4(String pathLoa4) {
//        this.pathLoa4 = pathLoa4;
//    }
//
//    public Boolean getCamLoa4() {
//        return camLoa4;
//    }
//
//    public void setCamLoa4(Boolean camLoa4) {
//        this.camLoa4 = camLoa4;
//    }
//
//    public String getPathLoa5() {
//        return pathLoa5;
//    }
//
//    public void setPathLoa5(String pathLoa5) {
//        this.pathLoa5 = pathLoa5;
//    }
//
//    public Boolean getCamLoa5() {
//        return camLoa5;
//    }
//
//    public void setCamLoa5(Boolean camLoa5) {
//        this.camLoa5 = camLoa5;
//    }
//
//    public String getPathLoa6() {
//        return pathLoa6;
//    }
//
//    public void setPathLoa6(String pathLoa6) {
//        this.pathLoa6 = pathLoa6;
//    }
//
//    public Boolean getCamLoa6() {
//        return camLoa6;
//    }
//
//    public void setCamLoa6(Boolean camLoa6) {
//        this.camLoa6 = camLoa6;
//    }
//
//    public String getPathLoa7() {
//        return pathLoa7;
//    }
//
//    public void setPathLoa7(String pathLoa7) {
//        this.pathLoa7 = pathLoa7;
//    }
//
//    public Boolean getCamLoa7() {
//        return camLoa7;
//    }
//
//    public void setCamLoa7(Boolean camLoa7) {
//        this.camLoa7 = camLoa7;
//    }
//
//    public String getPathLoa8() {
//        return pathLoa8;
//    }
//
//    public void setPathLoa8(String pathLoa8) {
//        this.pathLoa8 = pathLoa8;
//    }
//
//    public Boolean getCamLoa8() {
//        return camLoa8;
//    }
//
//    public void setCamLoa8(Boolean camLoa8) {
//        this.camLoa8 = camLoa8;
//    }
//
//    public String getPathLoa9() {
//        return pathLoa9;
//    }
//
//    public void setPathLoa9(String pathLoa9) {
//        this.pathLoa9 = pathLoa9;
//    }
//
//    public Boolean getCamLoa9() {
//        return camLoa9;
//    }
//
//    public void setCamLoa9(Boolean camLoa9) {
//        this.camLoa9 = camLoa9;
//    }
//
//    public Integer getAmLuongVolumn() {
//        return amLuongVolumn;
//    }
//
//    public void setAmLuongVolumn(Integer amLuongVolumn) {
//        this.amLuongVolumn = amLuongVolumn;
//    }
//
//    public Boolean getCamValumn() {
//        return camValumn;
//    }
//
//    public void setCamValumn(Boolean camValumn) {
//        this.camValumn = camValumn;
//    }
//
//    public Integer getAmLuongWave() {
//        return amLuongWave;
//    }
//
//    public void setAmLuongWave(Integer amLuongWave) {
//        this.amLuongWave = amLuongWave;
//    }
//
//    public Boolean getCamWave() {
//        return camWave;
//    }
//
//    public void setCamWave(Boolean camWave) {
//        this.camWave = camWave;
//    }
//
//    public String getPortgiaoTiep() {
//        return portgiaoTiep;
//    }
//
//    public void setPortgiaoTiep(String portgiaoTiep) {
//        this.portgiaoTiep = portgiaoTiep;
//    }
//
//    public Boolean getCamPort() {
//        return camPort;
//    }
//
//    public void setCamPort(Boolean camPort) {
//        this.camPort = camPort;
//    }
//
//    public String getKieuPrint() {
//        return kieuPrint;
//    }
//
//    public void setKieuPrint(String kieuPrint) {
//        this.kieuPrint = kieuPrint;
//    }
//
//    public Boolean getCamPrint() {
//        return camPrint;
//    }
//
//    public void setCamPrint(Boolean camPrint) {
//        this.camPrint = camPrint;
//    }
//
//    public Integer getMainDisplay() {
//        return mainDisplay;
//    }
//
//    public void setMainDisplay(Integer mainDisplay) {
//        this.mainDisplay = mainDisplay;
//    }
//
//    public Integer getTotalPrinter() {
//        return totalPrinter;
//    }
//
//    public void setTotalPrinter(Integer totalPrinter) {
//        this.totalPrinter = totalPrinter;
//    }
//
//    public String getLogoPath() {
//        return logoPath;
//    }
//
//    public void setLogoPath(String logoPath) {
//        this.logoPath = logoPath;
//    }
//
//    public Byte[] getLogo() {
//        return logo;
//    }
//
//    public void setLogo(Byte[] logo) {
//        this.logo = logo;
//    }
//
//    public Boolean getCamLogo() {
//        return camLogo;
//    }
//
//    public void setCamLogo(Boolean camLogo) {
//        this.camLogo = camLogo;
//    }
//
//    public String getTenCty() {
//        return tenCty;
//    }
//
//    public void setTenCty(String tenCty) {
//        this.tenCty = tenCty;
//    }
//
//    public String getDiaChi() {
//        return diaChi;
//    }
//
//    public void setDiaChi(String diaChi) {
//        this.diaChi = diaChi;
//    }
//
//    public String getCompanyName() {
//        return companyName;
//    }
//
//    public void setCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public String getDirection1() {
//        return direction1;
//    }
//
//    public void setDirection1(String direction1) {
//        this.direction1 = direction1;
//    }
//
//    public String getDirection2() {
//        return direction2;
//    }
//
//    public void setDirection2(String direction2) {
//        this.direction2 = direction2;
//    }
//
//    public String getDirection3() {
//        return direction3;
//    }
//
//    public void setDirection3(String direction3) {
//        this.direction3 = direction3;
//    }
//
//    public String getDirection4() {
//        return direction4;
//    }
//
//    public void setDirection4(String direction4) {
//        this.direction4 = direction4;
//    }
//
//    public String getDirection5() {
//        return direction5;
//    }
//
//    public void setDirection5(String direction5) {
//        this.direction5 = direction5;
//    }
//
//    public String getDirection6() {
//        return direction6;
//    }
//
//    public void setDirection6(String direction6) {
//        this.direction6 = direction6;
//    }
//
//    public String getDirection7() {
//        return direction7;
//    }
//
//    public void setDirection7(String direction7) {
//        this.direction7 = direction7;
//    }
//
//    public String getDirection8() {
//        return direction8;
//    }
//
//    public void setDirection8(String direction8) {
//        this.direction8 = direction8;
//    }
//
//    public String getDirection9() {
//        return direction9;
//    }
//
//    public void setDirection9(String direction9) {
//        this.direction9 = direction9;
//    }
//
//    public String getDirection10() {
//        return direction10;
//    }
//
//    public void setDirection10(String direction10) {
//        this.direction10 = direction10;
//    }
//
//    public String getDirection11() {
//        return direction11;
//    }
//
//    public void setDirection11(String direction11) {
//        this.direction11 = direction11;
//    }
//
//    public String getDirection12() {
//        return direction12;
//    }
//
//    public void setDirection12(String direction12) {
//        this.direction12 = direction12;
//    }
//
//    public String getDirection13() {
//        return direction13;
//    }
//
//    public void setDirection13(String direction13) {
//        this.direction13 = direction13;
//    }
//
//    public String getDirection14() {
//        return direction14;
//    }
//
//    public void setDirection14(String direction14) {
//        this.direction14 = direction14;
//    }
//
//    public String getDirection15() {
//        return direction15;
//    }
//
//    public void setDirection15(String direction15) {
//        this.direction15 = direction15;
//    }
//
//    public String getDirection16() {
//        return direction16;
//    }
//
//    public void setDirection16(String direction16) {
//        this.direction16 = direction16;
//    }
//
//    public String getDirection17() {
//        return direction17;
//    }
//
//    public void setDirection17(String direction17) {
//        this.direction17 = direction17;
//    }
//
//    public String getDirection18() {
//        return direction18;
//    }
//
//    public void setDirection18(String direction18) {
//        this.direction18 = direction18;
//    }
//
//    public String getDirection19() {
//        return direction19;
//    }
//
//    public void setDirection19(String direction19) {
//        this.direction19 = direction19;
//    }
//
//    public String getDirection20() {
//        return direction20;
//    }
//
//    public void setDirection20(String direction20) {
//        this.direction20 = direction20;
//    }


}
