package davisoft.app.busticket.data;


import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import davisoft.app.busticket.data.pojo.Counters;
import davisoft.app.busticket.data.pojo.DichVu;
import davisoft.app.busticket.data.pojo.DmHoaDon;
import davisoft.app.busticket.data.pojo.DmTaiXe;
import davisoft.app.busticket.data.pojo.DmTram;
import davisoft.app.busticket.data.pojo.DmTuyen;
import davisoft.app.busticket.data.pojo.DmTuyenChiTietTram;
import davisoft.app.busticket.data.pojo.DmXe;
import davisoft.app.busticket.data.pojo.LoTrinhChoXe;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ControlDatabase {
    private static DatabaseHelper databaseHelper = null;

    private static DatabaseHelper getHelper(Context context) {

        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }

//    ////Advert Read///////
//    static List<DmTaiXe> ItemAllDmTaiXe;
//    static List<String> ListmaTaiXe = new ArrayList<>();
//    static List<String> ListtenTaiXe = new ArrayList<>();
//    static List<String> Listtuoi = new ArrayList<>();
//    static List<String> ListgioiTinh = new ArrayList<>();
//    static List<String> ListbangLai = new ArrayList<>();
//    static List<String> Listsdt = new ArrayList<>();
//    static List<String> ListdiaChiNoiO = new ArrayList<>();
//    static List<String> Listemail = new ArrayList<>();
//
//
//    ////End Advert Read///////
//    public static void LoadDmTaiXe() {
//        ResClien restClient = new ResClien();
//        restClient.GetService().GetDMTAIXEs(new Callback<List<DmTaiXe>>() {
//            @Override
//            public void success(List<DmTaiXe> DmTaiXe, Response response) {
//                for (int i = 0; i < DmTaiXe.size(); i++) {
//                    String tmpStr10 = Integer.toString(DmTaiXe.get(i).getTUOI());
//                    ListmaTaiXe.add(DmTaiXe.get(i).getMATAIXE());
//                    ListtenTaiXe.add(DmTaiXe.get(i).getTENTAIXE());
//                    Listtuoi.add(tmpStr10);
//                    ListgioiTinh.add(DmTaiXe.get(i).getGIOITINH());
//                    ListbangLai.add(DmTaiXe.get(i).getBANGLAI());
//                    Listsdt.add(DmTaiXe.get(i).getSDT());
//                    ListdiaChiNoiO.add(DmTaiXe.get(i).getDIACHINOIO());
//                    Listemail.add(DmTaiXe.get(i).getEMAIL());
//                }
//                getAllItemsAllAdvert();
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d("myLogs", "-------ERROR-------Slide");
//                Log.d("myLogs", Log.getStackTraceString(error));
//            }
//        });
//
//    }
//
//    public static List<DmTaiXe> getAllItemsAllAdvert() {
//
//        List<DmTaiXe> items = new ArrayList<>();
//        for (int i = 0; i < ListmaTaiXe.size(); i++) {
//            items.add(
//                    new DmTaiXe(
//                            ListmaTaiXe.get(i),
//                            ListtenTaiXe.get(i),
//                            Listtuoi.get(i),
//                            ListgioiTinh.get(i),
//                            ListbangLai.get(i),
//                            Listsdt.get(i),
//                            ListdiaChiNoiO.get(i),
//                            Listemail.get(i)
//                    )
//            );
//        }
//        return items;
//    }
//
//    public static List<DmTaiXe> loadDataAllAdvert() {
//        LoadDmTaiXe();
//        ItemAllDmTaiXe = getAllItemsAllAdvert();
//        return ItemAllDmTaiXe;
//    }

    private static Date changeToate(String transferTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(transferTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    //////////DmTaiXe//////////
    public static void AddDmTaiXe(final Context context) {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMTAIXEs(new Callback<List<DmTaiXe>>() {
            @Override
            public void success(List<DmTaiXe> DmTaiXe, Response response) {
                for (int i = 0; i < DmTaiXe.size(); i++) {
                    String tmpStr10 = Integer.toString(DmTaiXe.get(i).getTUOI());
                    AddDmTaiXeSqlite(context, DmTaiXe.get(i).getMATAIXE(), DmTaiXe.get(i).getTENTAIXE(), tmpStr10, DmTaiXe.get(i).getGIOITINH(), DmTaiXe.get(i).getBANGLAI(), DmTaiXe.get(i).getSDT(), DmTaiXe.get(i).getDIACHINOIO(), DmTaiXe.get(i).getEMAIL());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private static Dao<DmTaiXe, Integer> DmTaiXeMangasDao;
    private static List<DmTaiXe> DmTaiXeMangasList;
    public static void AddDmTaiXeSqlite(Context context, String MATAIXE, String TENTAIXE, String TUOI, String GIOITINH, String BANGLAI, String SDT, String DIACHINOIO, String EMAIL) {
        try {

            DmTaiXeMangasDao = getHelper(context).getDmTaiXeMangasDao();
            QueryBuilder<DmTaiXe, Integer> queryBuilder = DmTaiXeMangasDao.queryBuilder();
            queryBuilder.where().eq("MATAIXE", MATAIXE);
            DmTaiXeMangasList = queryBuilder.query();
            if (DmTaiXeMangasList.size() <= 0) {
                final DmTaiXe DmTaiXe = new DmTaiXe();
                DmTaiXe.MATAIXE = MATAIXE;
                DmTaiXe.TENTAIXE = TENTAIXE;
                DmTaiXe.TENTAIXE = TENTAIXE;
                DmTaiXe.TUOI = Integer.parseInt(TUOI);
                DmTaiXe.GIOITINH = GIOITINH;
                DmTaiXe.BANGLAI = BANGLAI;
                DmTaiXe.SDT = SDT;
                DmTaiXe.DIACHINOIO = DIACHINOIO;
                DmTaiXe.EMAIL = EMAIL;
                try {
                    final Dao<DmTaiXe, Integer> advertMangas = getHelper(context).getDmTaiXeMangasDao();
                    advertMangas.create(DmTaiXe);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                UpdateBuilder<DmTaiXe, Integer> updateBuilder = DmTaiXeMangasDao.updateBuilder();
                updateBuilder.updateColumnValue("MATAIXE", MATAIXE);
                updateBuilder.updateColumnValue("TENTAIXE", TENTAIXE);
                updateBuilder.updateColumnValue("TUOI", TUOI);
                updateBuilder.updateColumnValue("GIOITINH", GIOITINH);
                updateBuilder.updateColumnValue("BANGLAI", BANGLAI);
                updateBuilder.updateColumnValue("SDT", SDT);
                updateBuilder.updateColumnValue("EMAIL", EMAIL);
                updateBuilder.updateColumnValue("DIACHINOIO", DIACHINOIO);
                updateBuilder.where().eq("MATAIXE", MATAIXE);
                updateBuilder.update();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //////////Counters/////////
    public static void AddCounters(final Context context) {
        ResClien restClient = new ResClien();
        restClient.GetService().GetCounters(new Callback<List<Counters>>() {
            @Override
            public void success(List<Counters> Counters, Response response) {
                for (int i = 0; i < Counters.size(); i++) {

                    AddCounterseSqlite(context, Counters.get(i).getCopyRight().toString(),
                            Counters.get(i).getLogoCopyRight(),
                            Counters.get(i).getCopyRightKey(),
                            Counters.get(i).getChedoInCSDL().toString(),
                            Counters.get(i).getChedoInDefault().toString(),
                            Counters.get(i).getSetDefaultIn().toString(),
                            Counters.get(i).getMutiServices().toString(),
                            Counters.get(i).getTransferAuto().toString(),
                            Counters.get(i).getTransferTime().toString(),
                            Counters.get(i).getPGDCode().toString(),
                            Counters.get(i).getAutoNext().toString(),
                            Counters.get(i).getBackupSQLserver().toString(),
                            Counters.get(i).getTieudeVN().toString(),
                            Counters.get(i).getTieudeENG().toString(),
                            Counters.get(i).getMST().toString(),
                            Counters.get(i).getDT().toString(),
                            Counters.get(i).getDCPRINT().toString(),
                            Counters.get(i).getMAXE().toString(),
                            Counters.get(i).getLuot().toString(),
                            Counters.get(i).getLastday().toString(),
                            Counters.get(i).getTenCongTy().toString(),
                            Counters.get(i).getDiaChi().toString()
                    );
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private static Dao<Counters, Integer> CountersMangasDao;
    private static List<Counters> CountersList;
    public static void AddCounterseSqlite(Context context, String CopyRight, String LogoCopyRight, String CopyRightKey, String ChedoInCSDL, String ChedoInDefault, String SetDefaultIn, String MutiServices, String TransferAuto, String TransferTime, String PGDCode, String AutoNext, String BackupSQLserver, String TieudeVN, String TieudeENG, String MST, String DT, String DCPRINT, String MAXE, String Luot, String Lastday, String TenCongTy, String DiaChi) {
        try {

            CountersMangasDao = getHelper(context).getCountMangasDao();
            QueryBuilder<Counters, Integer> queryBuilder = CountersMangasDao.queryBuilder();
            queryBuilder.where().eq("MAXE", MAXE);
            CountersList = queryBuilder.query();
            if (CountersList.size() <= 0) {
                final Counters Counters = new Counters();
                Counters.CopyRight = CopyRight;
                Counters.LogoCopyRight = LogoCopyRight;
                Counters.CopyRightKey = CopyRightKey;
                Counters.ChedoInCSDL = Boolean.parseBoolean(ChedoInCSDL);
                Counters.ChedoInDefault = Boolean.parseBoolean(ChedoInDefault);
                Counters.SetDefaultIn = Boolean.parseBoolean(SetDefaultIn);
                Counters.MutiServices = Boolean.parseBoolean(MutiServices);
                Counters.TransferAuto = Boolean.parseBoolean(TransferAuto);
                Counters.TransferTime = changeToate(TransferTime);
                Counters.PGDCode = PGDCode;
                Counters.AutoNext = Boolean.parseBoolean(AutoNext);
                Counters.BackupSQLserver = Boolean.parseBoolean(BackupSQLserver);
                Counters.TieudeVN = TieudeVN;
                Counters.TieudeENG = TieudeENG;
                Counters.MST = MST;
                Counters.DT = DT;
                Counters.DCPRINT = DCPRINT;
                Counters.MAXE = MAXE;
                Counters.Luot = Luot;
                Counters.Lastday = Lastday;
                Counters.TenCongTy = TenCongTy;
                Counters.DiaChi = DiaChi;

                try {
                    final Dao<Counters, Integer> counters = getHelper(context).getCountMangasDao();
                    counters.create(Counters);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                UpdateBuilder<Counters, Integer> updateBuilder = CountersMangasDao.updateBuilder();
                updateBuilder.updateColumnValue("CopyRight", CopyRight);
                updateBuilder.updateColumnValue("LogoCopyRight", LogoCopyRight);
                updateBuilder.updateColumnValue("CopyRightKey", CopyRightKey);
                updateBuilder.updateColumnValue("ChedoInCSDL", ChedoInCSDL);
                updateBuilder.updateColumnValue("ChedoInDefault", ChedoInDefault);
                updateBuilder.updateColumnValue("SetDefaultIn", SetDefaultIn);
                updateBuilder.updateColumnValue("MutiServices", MutiServices);
                updateBuilder.updateColumnValue("TransferAuto", TransferAuto);
                updateBuilder.updateColumnValue("TransferTime", TransferTime);
                updateBuilder.updateColumnValue("PGDCode", PGDCode);
                updateBuilder.updateColumnValue("AutoNext", AutoNext);
                updateBuilder.updateColumnValue("BackupSQLserver", BackupSQLserver);
                updateBuilder.updateColumnValue("TieudeVN", TieudeVN);
                updateBuilder.updateColumnValue("TieudeENG", TieudeENG);
                updateBuilder.updateColumnValue("MST", MST);
                updateBuilder.updateColumnValue("DT", DT);
                updateBuilder.updateColumnValue("DCPRINT", DCPRINT);
                updateBuilder.updateColumnValue("MAXE", MAXE);
                updateBuilder.updateColumnValue("Luot", Luot);
                updateBuilder.updateColumnValue("Lastday", Lastday);
                updateBuilder.updateColumnValue("TenCongTy", TenCongTy);
                updateBuilder.updateColumnValue("DiaChi", DiaChi);
                updateBuilder.where().eq("MAXE", MAXE);
                updateBuilder.update();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //////////DmTram//////////
    public static void AddDmTram(final Context context) {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMTRAMs(new Callback<List<DmTram>>() {
            @Override
            public void success(List<DmTram> DmTram, Response response) {
                for (int i = 0; i < DmTram.size(); i++) {

                    AddDmTramSqlite(context, DmTram.get(i).getId().toString(), DmTram.get(i).getMaTram(), DmTram.get(i).getTenTram());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private static Dao<DmTram, Integer> DmTramMangasDao;
    private static List<DmTram> DmTramMangasList;
    public static void AddDmTramSqlite(Context context,String ID,String MATRAM,String TENTRAM) {
        try {

            DmTramMangasDao = getHelper(context).getDmTramMangasDao();
            QueryBuilder<DmTram, Integer> queryBuilder = DmTramMangasDao.queryBuilder();
            queryBuilder.where().eq("ID", ID);
            DmTramMangasList = queryBuilder.query();
            if (DmTaiXeMangasList.size() <= 0) {
                final DmTram DmTram = new DmTram();
                DmTram.ID = Integer.parseInt( ID);
                DmTram.MATRAM = MATRAM;
                DmTram.TENTRAM = TENTRAM;

                try {
                    final Dao<DmTram, Integer> dmTram = getHelper(context).getDmTramMangasDao();
                    dmTram.create(DmTram);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                UpdateBuilder<DmTram, Integer> updateBuilder = DmTramMangasDao.updateBuilder();
                updateBuilder.updateColumnValue("ID", ID);
                updateBuilder.updateColumnValue("MATRAM", MATRAM);
                updateBuilder.updateColumnValue("TENTRAM", TENTRAM);
                updateBuilder.where().eq("ID", ID);
                updateBuilder.update();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //////////DmXe//////////
    public static void AddDmXe(final Context context) {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMXEs(new Callback<List<DmXe>>() {
            @Override
            public void success(List<DmXe> DmXe, Response response) {
                for (int i = 0; i < DmXe.size(); i++) {

                    AddDmXeSqlite(context, DmXe.get(i).getMaXe(), DmXe.get(i).getSoXe(), DmXe.get(i).getLoaiXe(), DmXe.get(i).getMaTaiXe(), DmXe.get(i).getSoGhe());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private static Dao<DmXe, Integer> DmXeMangasDao;
    private static List<DmXe> DmXeMangasList;
    public static void AddDmXeSqlite(Context context,String MAXE,String SOXE,String LOAIXE,String MATAIXE,String SOGHE) {
        try {

            DmXeMangasDao = getHelper(context).getDmXeMangasDao();
            QueryBuilder<DmXe, Integer> queryBuilder = DmXeMangasDao.queryBuilder();
            queryBuilder.where().eq("MAXE", MAXE);
            DmXeMangasList = queryBuilder.query();
            if (DmTaiXeMangasList.size() <= 0) {
                final DmXe DmXe = new DmXe();
                DmXe.MAXE = MAXE;
                DmXe.SOXE = SOXE;
                DmXe.LOAIXE = LOAIXE;
                DmXe.MATAIXE = MATAIXE;
                DmXe.SOGHE = SOGHE;

                try {
                    final Dao<DmXe, Integer> dmXe = getHelper(context).getDmXeMangasDao();
                    dmXe.create(DmXe);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                UpdateBuilder<DmXe, Integer> updateBuilder = DmXeMangasDao.updateBuilder();
                updateBuilder.updateColumnValue("MAXE", MAXE);
                updateBuilder.updateColumnValue("SOXE", SOXE);
                updateBuilder.updateColumnValue("LOAIXE", LOAIXE);
                updateBuilder.updateColumnValue("MATAIXE", MATAIXE);
                updateBuilder.updateColumnValue("SOGHE", SOGHE);
                updateBuilder.where().eq("MAXE", MAXE);
                updateBuilder.update();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //////////DmTuyenChiTietTram//////////
    public static void AddDmTuyenChiTietTram(final Context context) {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMTUYENCHITIETTRAMs(new Callback<List<DmTuyenChiTietTram>>() {
            @Override
            public void success(List<DmTuyenChiTietTram> DmTuyenChiTietTram, Response response) {
                for (int i = 0; i < DmTuyenChiTietTram.size(); i++) {

                    AddDmTuyenChiTietTramSqlite(context, DmTuyenChiTietTram.get(i).getId().toString(), DmTuyenChiTietTram.get(i).getIdTuyen(), DmTuyenChiTietTram.get(i).getMaTram(), DmTuyenChiTietTram.get(i).getTramDau().toString(), DmTuyenChiTietTram.get(i).getTramCuoi().toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private static Dao<DmTuyenChiTietTram, Integer> DmTuyenChiTietTramMangasDao;
    private static List<DmTuyenChiTietTram> DmTuyenChiTietTramMangasList;
    public static void AddDmTuyenChiTietTramSqlite(Context context,String ID,String IDTUYEN,String MATRAM,String TRAMDAU,String TRAMCUOI) {
        try {

            DmTuyenChiTietTramMangasDao = getHelper(context).getDmTuyenChiTietTramMangasDao();
            QueryBuilder<DmTuyenChiTietTram, Integer> queryBuilder = DmTuyenChiTietTramMangasDao.queryBuilder();
            queryBuilder.where().eq("ID", ID);
            DmTuyenChiTietTramMangasList = queryBuilder.query();
            if (DmTaiXeMangasList.size() <= 0) {
                final DmTuyenChiTietTram DmTuyenChiTietTram = new DmTuyenChiTietTram();
                DmTuyenChiTietTram.ID =Integer.parseInt(ID) ;
                DmTuyenChiTietTram.IDTUYEN = IDTUYEN;
                DmTuyenChiTietTram.MATRAM = MATRAM;
                DmTuyenChiTietTram.TRAMDAU = Boolean.parseBoolean(TRAMDAU);
                DmTuyenChiTietTram.TRAMCUOI = Boolean.parseBoolean(TRAMCUOI) ;

                try {
                    final Dao<DmTuyenChiTietTram, Integer> dmTuyenChiTietTram = getHelper(context).getDmTuyenChiTietTramMangasDao();
                    dmTuyenChiTietTram.create(DmTuyenChiTietTram);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                UpdateBuilder<DmTuyenChiTietTram, Integer> updateBuilder = DmTuyenChiTietTramMangasDao.updateBuilder();
                updateBuilder.updateColumnValue("ID", ID);
                updateBuilder.updateColumnValue("IDTUYEN", IDTUYEN);
                updateBuilder.updateColumnValue("MATRAM", MATRAM);
                updateBuilder.updateColumnValue("TRAMDAU", TRAMDAU);
                updateBuilder.updateColumnValue("TRAMCUOI", TRAMCUOI);
                updateBuilder.where().eq("ID", ID);
                updateBuilder.update();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //////////LoTrinhChoXe//////////
    public static void AddLoTrinhChoXe(final Context context) {
        ResClien restClient = new ResClien();
        restClient.GetService().GetLOTRINHCHOXEs(new Callback<List<LoTrinhChoXe>>() {
            @Override
            public void success(List<LoTrinhChoXe> LoTrinhChoXe, Response response) {
                for (int i = 0; i < LoTrinhChoXe.size(); i++) {

                    AddLoTrinhChoXeSqlite(context, LoTrinhChoXe.get(i).getIdLoTrinh().toString(), LoTrinhChoXe.get(i).getMaXe(), LoTrinhChoXe.get(i).getIdTuyen(), LoTrinhChoXe.get(i).getMaTaiXe(), LoTrinhChoXe.get(i).getCam().toString(),LoTrinhChoXe.get(i).getKichHoat().toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private static Dao<LoTrinhChoXe, Integer> LoTrinhChoXeMangasDao;
    private static List<LoTrinhChoXe> LoTrinhChoXeMangasList;
    public static void AddLoTrinhChoXeSqlite(Context context,String IDLOTRINH,String MAXE,String IDTUYEN,String MATAIXE,String CAM,String KICHHOAT) {
        try {

            LoTrinhChoXeMangasDao = getHelper(context).getLoTrinhChoXeMangasDao();
            QueryBuilder<LoTrinhChoXe, Integer> queryBuilder = LoTrinhChoXeMangasDao.queryBuilder();
            queryBuilder.where().eq("IDLOTRINH", IDLOTRINH);
            LoTrinhChoXeMangasList = queryBuilder.query();
            if (DmTaiXeMangasList.size() <= 0) {
                final LoTrinhChoXe LoTrinhChoXe = new LoTrinhChoXe();
                LoTrinhChoXe.IDLOTRINH =Integer.parseInt(IDLOTRINH) ;
                LoTrinhChoXe.MAXE = MAXE;
                LoTrinhChoXe.IDTUYEN = IDTUYEN;
                LoTrinhChoXe.MATAIXE = MATAIXE;
                LoTrinhChoXe.CAM =Boolean.parseBoolean(CAM) ;
                LoTrinhChoXe.KICHHOAT =Boolean.parseBoolean(KICHHOAT) ;

                try {
                    final Dao<LoTrinhChoXe, Integer> loTrinhChoXe = getHelper(context).getLoTrinhChoXeMangasDao();
                    loTrinhChoXe.create(LoTrinhChoXe);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                UpdateBuilder<LoTrinhChoXe, Integer> updateBuilder = LoTrinhChoXeMangasDao.updateBuilder();
                updateBuilder.updateColumnValue("IDLOTRINH", IDLOTRINH);
                updateBuilder.updateColumnValue("MAXE", MAXE);
                updateBuilder.updateColumnValue("IDTUYEN", IDTUYEN);
                updateBuilder.updateColumnValue("MATAIXE", MATAIXE);
                updateBuilder.updateColumnValue("CAM", CAM);
                updateBuilder.updateColumnValue("KICHHOAT", KICHHOAT);
                updateBuilder.where().eq("IDLOTRINH", IDLOTRINH);
                updateBuilder.update();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //////////DmTuyen//////////
    public static void AddDmTuyen(final Context context) {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMTUYENs(new Callback<List<DmTuyen>>() {
            @Override
            public void success(List<DmTuyen> DmTuyen, Response response) {
                for (int i = 0; i < DmTuyen.size(); i++) {
                 String GETGIAVE1 = String.valueOf(DmTuyen.get(i).GETGIAVE1());
                    String GETGIAVE2 = String.valueOf(DmTuyen.get(i).GETGIAVE2());
                    String GETGIAVE3 = String.valueOf(DmTuyen.get(i).GETGIAVE3());
                    String GETGIAVE4 = String.valueOf(DmTuyen.get(i).GETGIAVE4());
                    String GETGIAVE5 = String.valueOf(DmTuyen.get(i).GETGIAVE5());
                    String GETGIAVE6 = String.valueOf(DmTuyen.get(i).GETGIAVE6());

                    String GETCAMVE1 = String.valueOf(DmTuyen.get(i).GETCAMVE1());
                    String GETCAMVE2 = String.valueOf(DmTuyen.get(i).GETCAMVE2());
                    String GETCAMVE3 = String.valueOf(DmTuyen.get(i).GETCAMVE3());
                    String GETCAMVE4 = String.valueOf(DmTuyen.get(i).GETCAMVE4());
                    String GETCAMVE5 = String.valueOf(DmTuyen.get(i).GETCAMVE5());
                    String GETCAMVE6 = String.valueOf(DmTuyen.get(i).GETCAMVE6());

                    AddDmTuyenSqlite(context,DmTuyen.get(i).GETIDTUYEN(),DmTuyen.get(i).GETMATUYEN(),
                            DmTuyen.get(i).GETTENTUYENEND(),DmTuyen.get(i).GETTENTUYENVN(),
                            DmTuyen.get(i).GETMATRAMDAU(),DmTuyen.get(i).GETMATRAMGIUA(),
                            DmTuyen.get(i).GETMATRAMCUOI(),DmTuyen.get(i).GETMUCDO().toString(),
                            DmTuyen.get(i).GETTHOIGIANTOANTRAM().toString(),
                            GETGIAVE1,DmTuyen.get(i).GETDIENGIAIVE1().toString(),GETCAMVE1,DmTuyen.get(i).GETIDVE1IDHOADON().toString(),
                            GETGIAVE2,DmTuyen.get(i).GETDIENGIAIVE2().toString(),GETCAMVE2,DmTuyen.get(i).GETIDVE2IDHOADON(),
                            GETGIAVE3,DmTuyen.get(i).GETDIENGIAIVE3(),GETCAMVE3,DmTuyen.get(i).GETIDVE3IDHOADON(),
                            GETGIAVE4,DmTuyen.get(i).GETDIENGIAIVE4().toString(),GETCAMVE4,DmTuyen.get(i).GETIDVE4IDHOADON().toString(),
                            GETGIAVE5,DmTuyen.get(i).GETDIENGIAIVE5().toString(),GETCAMVE5,DmTuyen.get(i).GETIDVE5IDHOADON(),
                            GETGIAVE6,DmTuyen.get(i).GETDIENGIAIVE6().toString(),GETCAMVE6,DmTuyen.get(i).GETIDVE6IDHOADON().toString(),DmTuyen.get(i).GETTONGTRAM());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private static Dao<DmTuyen, Integer> DmTuyenMangasDao;
    private static List<DmTuyen> DmTuyenMangasList;
    public static void AddDmTuyenSqlite(Context context,String IDTUYEN,String MATUYEN,String TENTUYENEND,String TENTUYENVN,String MATRAMDAU,String MATRAMGIUA,String MATRAMCUOI,
                                        String MUCDO,String THOIGIANTOANTRAM,String GIAVE1,String DIENGIAIVE1,String  CAMVE1,String IDVE1IDHOADON,String GIAVE2,
                                        String DIENGIAIVE2,String  CAMVE2,String IDVE2IDHOADON,String GIAVE3,String DIENGIAIVE3,String  CAMVE3,String IDVE3IDHOADON,
                                        String GIAVE4,String DIENGIAIVE4,String  CAMVE4,String IDVE4IDHOADON,String GIAVE5,String DIENGIAIVE5,String  CAMVE5,
                                        String IDVE5IDHOADON,String GIAVE6,String DIENGIAIVE6,String  CAMVE6,String IDVE6IDHOADON,String TONGTRAM) {
        try {

            DmTuyenMangasDao = getHelper(context).getDmTuyenMangasDao();
            QueryBuilder<DmTuyen, Integer> queryBuilder = DmTuyenMangasDao.queryBuilder();
            queryBuilder.where().eq("IDTUYEN", IDTUYEN);
            DmTuyenMangasList = queryBuilder.query();
            if (DmTaiXeMangasList.size() <= 0) {
                final DmTuyen DmTuyen = new DmTuyen();DmTuyen.IDTUYEN=IDTUYEN;DmTuyen.MATUYEN=MATUYEN;
                DmTuyen.TENTUYENEND=TENTUYENEND;DmTuyen.TENTUYENVN=TENTUYENVN;
                DmTuyen.MATRAMDAU=MATRAMDAU; DmTuyen.MATRAMGIUA=MATRAMGIUA;
                DmTuyen.MATRAMCUOI=MATRAMCUOI; DmTuyen.TONGTRAM=TONGTRAM;
                DmTuyen.MUCDO=Integer.parseInt(MUCDO) ;
                DmTuyen.THOIGIANTOANTRAM=THOIGIANTOANTRAM ;
                DmTuyen.GIAVE1=Double.parseDouble(GIAVE1) ;DmTuyen.DIENGIAIVE1=DIENGIAIVE1;
                DmTuyen.CAMVE1=Boolean.parseBoolean(CAMVE1) ;DmTuyen.IDVE1IDHOADON=IDVE1IDHOADON;
                DmTuyen.GIAVE2=Double.parseDouble(GIAVE2) ; DmTuyen.DIENGIAIVE2=DIENGIAIVE2;
                DmTuyen.CAMVE2=Boolean.parseBoolean(CAMVE2);DmTuyen.IDVE2IDHOADON=IDVE2IDHOADON;
                DmTuyen.GIAVE3=Double.parseDouble(GIAVE3) ;DmTuyen.DIENGIAIVE3=DIENGIAIVE3;
                DmTuyen.CAMVE3=Boolean.parseBoolean(CAMVE3);DmTuyen.IDVE3IDHOADON=IDVE3IDHOADON;
                DmTuyen.GIAVE4=Double.parseDouble(GIAVE4) ;  DmTuyen.DIENGIAIVE4=DIENGIAIVE4;
                DmTuyen.CAMVE4=Boolean.parseBoolean(CAMVE5);  DmTuyen.IDVE4IDHOADON=IDVE4IDHOADON;
                DmTuyen.GIAVE5=Double.parseDouble(GIAVE5) ;   DmTuyen.DIENGIAIVE5=DIENGIAIVE5;
                DmTuyen.CAMVE5=Boolean.parseBoolean(CAMVE5);DmTuyen.IDVE5IDHOADON=IDVE5IDHOADON;
                DmTuyen.GIAVE6=Double.parseDouble(GIAVE6) ; DmTuyen.DIENGIAIVE6=DIENGIAIVE6;
                DmTuyen.CAMVE6=Boolean.parseBoolean(CAMVE6); DmTuyen.IDVE6IDHOADON=IDVE6IDHOADON;

                try {
                    final Dao<DmTuyen, Integer> dmTuyen = getHelper(context).getDmTuyenMangasDao();
                    dmTuyen.create(DmTuyen);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                UpdateBuilder<DmTuyen, Integer> updateBuilder = DmTuyenMangasDao.updateBuilder();
                updateBuilder.updateColumnValue("IDTUYEN", IDTUYEN);
                updateBuilder.updateColumnValue("MATUYEN", MATUYEN);
                updateBuilder.updateColumnValue("TENTUYENEND", TENTUYENEND);
                updateBuilder.updateColumnValue("TENTUYENVN", TENTUYENVN);
                updateBuilder.updateColumnValue("MATRAMDAU", MATRAMDAU);
                updateBuilder.updateColumnValue("MATRAMGIUA", MATRAMGIUA);
                updateBuilder.updateColumnValue("MATRAMCUOI", MATRAMCUOI);
                updateBuilder.updateColumnValue("TONGTRAM", TONGTRAM);
                updateBuilder.updateColumnValue("MUCDO", MUCDO);
                updateBuilder.updateColumnValue("THOIGIANTOANTRAM", THOIGIANTOANTRAM);
                updateBuilder.updateColumnValue("GIAVE1", GIAVE1);
                updateBuilder.updateColumnValue("DIENGIAIVE1", DIENGIAIVE1);
                updateBuilder.updateColumnValue("CAMVE1", CAMVE1);
                updateBuilder.updateColumnValue("IDVE1IDHOADON", IDVE1IDHOADON);

                updateBuilder.updateColumnValue("GIAVE2", GIAVE2);
                updateBuilder.updateColumnValue("DIENGIAIVE2", DIENGIAIVE2);
                updateBuilder.updateColumnValue("CAMVE2", CAMVE2);
                updateBuilder.updateColumnValue("IDVE2IDHOADON", IDVE2IDHOADON);

                updateBuilder.updateColumnValue("GIAVE3", GIAVE3);
                updateBuilder.updateColumnValue("DIENGIAIVE3", DIENGIAIVE3);
                updateBuilder.updateColumnValue("CAMVE3", CAMVE3);
                updateBuilder.updateColumnValue("IDVE3IDHOADON", IDVE3IDHOADON);

                updateBuilder.updateColumnValue("GIAVE4", GIAVE4);
                updateBuilder.updateColumnValue("DIENGIAIVE4", DIENGIAIVE4);
                updateBuilder.updateColumnValue("CAMVE4", CAMVE4);
                updateBuilder.updateColumnValue("IDVE4IDHOADON", IDVE4IDHOADON);

                updateBuilder.updateColumnValue("GIAVE5", GIAVE5);
                updateBuilder.updateColumnValue("DIENGIAIVE5", DIENGIAIVE5);
                updateBuilder.updateColumnValue("CAMVE5", CAMVE5);
                updateBuilder.updateColumnValue("IDVE5IDHOADON", IDVE5IDHOADON);

                updateBuilder.updateColumnValue("GIAVE6", GIAVE6);
                updateBuilder.updateColumnValue("DIENGIAIVE6", DIENGIAIVE6);
                updateBuilder.updateColumnValue("CAMVE6", CAMVE6);
                updateBuilder.updateColumnValue("IDVE6IDHOADON", IDVE6IDHOADON);




                updateBuilder.where().eq("IDTUYEN", IDTUYEN);
                updateBuilder.update();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    //////////DICHVU//////////
    public static void AddDichVu(final Context context) {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDICHVUs(new Callback<List<DichVu>>() {
            @Override
            public void success(List<DichVu> DichVu, Response response) {
                for (int i = 0; i < DichVu.size(); i++) {
                    String getID = String.valueOf(DichVu.get(i).getID());
                    String isPHUCVU = String.valueOf(DichVu.get(i).isPHUCVU());
                    String isContro = String.valueOf(DichVu.get(i).isContro());
                    String isDoc = String.valueOf(DichVu.get(i).isDoc());
                    String isDATCHO = String.valueOf(DichVu.get(i).isDATCHO());


                    AddDichVuSqlite(context,getID,
                            DichVu.get(i).getNGAY(),
                            DichVu.get(i).getSQMS(),
                            DichVu.get(i).getGIOLAYSO(),
                            DichVu.get(i).getDichvu1(),
                            DichVu.get(i).getGIAVE(),
                            DichVu.get(i).getMATUYEN(),
                            DichVu.get(i).getTENTUYEN(),
                            DichVu.get(i).getMATRAMDAU(),
                            DichVu.get(i).getMATRAMCUOI(),
                            DichVu.get(i).getLOTRINH(),
                            DichVu.get(i).getBienSoXe(),
                            DichVu.get(i).getTRANGTHAI(),
                            isPHUCVU,
                            DichVu.get(i).getGHICHU(),
                            isContro,
                            isDoc,
                            isDATCHO,
                            DichVu.get(i).getGIO_GOC(),
                            DichVu.get(i).getBINH_CHON(),
                            DichVu.get(i).getGIO_BINHCHON(),
                            DichVu.get(i).getNGONNGU(),
                            DichVu.get(i).getDIEMGIAODICH(),
                            DichVu.get(i).getMANV(),
                            DichVu.get(i).getQUAYCHUYEN(),
                            DichVu.get(i).getQUAYTHAMCHIEU(),
                            DichVu.get(i).getSODIENTHOAI(),
                            DichVu.get(i).getQUAY(),
                            DichVu.get(i).getGIOPHUCVU(),
                            DichVu.get(i).getMAXE(),
                            DichVu.get(i).getMATAIXE(),
                            DichVu.get(i).getMATRAM(),
                            DichVu.get(i).getIDTUYEN(),
                            DichVu.get(i).getKYHIEUVE(),
                            DichVu.get(i).getMAUSO(),
                            DichVu.get(i).getMATRAMGIUA());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private static Dao<DichVu, Integer> DichVuMangasDao;
    private static List<DichVu> DichVuMangasList;
    public static void AddDichVuSqlite(Context context,String ID,
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
                                       String MATRAMGIUA) {
        try {

            DichVuMangasDao = getHelper(context).getDichVuMangasDao();
            QueryBuilder<DichVu, Integer> queryBuilder = DichVuMangasDao.queryBuilder();
            queryBuilder.where().eq("IDTUYEN", IDTUYEN);
            DichVuMangasList = queryBuilder.query();
            if (DmTaiXeMangasList.size() <= 0) {
                final DichVu DichVu = new DichVu();
                DichVu.ID=Integer.parseInt(ID) ;
                DichVu.NGAY=NGAY;
                DichVu.SQMS=SQMS;
                DichVu.GIOLAYSO=GIOLAYSO;
                DichVu.dichvu1=dichvu1;
                DichVu.GIAVE=GIAVE;
                DichVu.MATUYEN=MATUYEN;
                DichVu.TENTUYEN=TENTUYEN;
                DichVu.MATRAMDAU=MATRAMDAU;
                DichVu.MATRAMCUOI=MATRAMCUOI;
                DichVu.LOTRINH=LOTRINH;
                DichVu.BienSoXe=BienSoXe;
                DichVu.TRANGTHAI=TRANGTHAI;
                DichVu.PHUCVU=Boolean.parseBoolean(PHUCVU);
                DichVu.GHICHU=GHICHU;
                DichVu.Contro=Boolean.parseBoolean(Contro);
                DichVu.Doc=Boolean.parseBoolean(Doc);
                DichVu.DATCHO=Boolean.parseBoolean(DATCHO);
                DichVu.GIO_GOC=GIO_GOC;
                DichVu.BINH_CHON=BINH_CHON;
                DichVu.GIO_BINHCHON=GIO_BINHCHON;
                DichVu.NGONNGU=NGONNGU;
                DichVu.DIEMGIAODICH=DIEMGIAODICH;
                DichVu.MANV=MANV;
                DichVu.QUAYCHUYEN=QUAYCHUYEN;
                DichVu.QUAYTHAMCHIEU=QUAYTHAMCHIEU;
                DichVu.SODIENTHOAI=SODIENTHOAI;
                DichVu.QUAY=QUAY;
                DichVu.GIOPHUCVU=GIOPHUCVU;
                DichVu.MAXE=MAXE;
                DichVu.MATAIXE=MATAIXE;
                DichVu.MATRAM=MATRAM;
                DichVu.IDTUYEN=IDTUYEN;
                DichVu.KYHIEUVE=KYHIEUVE;
                DichVu.MAUSO=MAUSO;
                DichVu.MATRAMGIUA=MATRAMGIUA;

                try {
                    final Dao<DichVu, Integer> dichVu = getHelper(context).getDichVuMangasDao();
                    dichVu.create(DichVu);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                UpdateBuilder<DichVu, Integer> updateBuilder = DichVuMangasDao.updateBuilder();
                updateBuilder.updateColumnValue("ID", ID );
                updateBuilder.updateColumnValue("NGAY", NGAY);
                updateBuilder.updateColumnValue("SQMS", SQMS);
                updateBuilder.updateColumnValue("GIOLAYSO", GIOLAYSO);
                updateBuilder.updateColumnValue("dichvu1", dichvu1);
                updateBuilder.updateColumnValue("GIAVE", GIAVE);
                updateBuilder.updateColumnValue("MATUYEN", MATUYEN);
                updateBuilder.updateColumnValue("TENTUYEN", TENTUYEN);
                updateBuilder.updateColumnValue("MATRAMDAU", MATRAMDAU);
                updateBuilder.updateColumnValue("MATRAMCUOI", MATRAMCUOI);
                updateBuilder.updateColumnValue("LOTRINH", LOTRINH);
                updateBuilder.updateColumnValue("BienSoXe", BienSoXe);
                updateBuilder.updateColumnValue("TRANGTHAI", TRANGTHAI);
                updateBuilder.updateColumnValue("PHUCVU", Boolean.parseBoolean(PHUCVU));
                updateBuilder.updateColumnValue("GHICHU", GHICHU);
                updateBuilder.updateColumnValue("Contro", Boolean.parseBoolean(Contro));
                updateBuilder.updateColumnValue("Doc", Boolean.parseBoolean(Doc));
                updateBuilder.updateColumnValue("DATCHO", Boolean.parseBoolean(DATCHO));
                updateBuilder.updateColumnValue("GIO_GOC", GIO_GOC);
                updateBuilder.updateColumnValue("BINH_CHON", BINH_CHON);
                updateBuilder.updateColumnValue("GIO_BINHCHON", GIO_BINHCHON);
                updateBuilder.updateColumnValue("NGONNGU", NGONNGU);
                updateBuilder.updateColumnValue("DIEMGIAODICH", DIEMGIAODICH);
                updateBuilder.updateColumnValue("MANV", MANV);
                updateBuilder.updateColumnValue("QUAYCHUYEN", QUAYCHUYEN);
                updateBuilder.updateColumnValue("QUAYTHAMCHIEU", QUAYTHAMCHIEU);
                updateBuilder.updateColumnValue("SODIENTHOAI", SODIENTHOAI);
                updateBuilder.updateColumnValue("QUAY", QUAY);
                updateBuilder.updateColumnValue("GIOPHUCVU", GIOPHUCVU);
                updateBuilder.updateColumnValue("MAXE", MAXE);
                updateBuilder.updateColumnValue("MATAIXE", MATAIXE);
                updateBuilder.updateColumnValue("MATRAM", MATRAM);
                updateBuilder.updateColumnValue("IDTUYEN", IDTUYEN);
                updateBuilder.updateColumnValue("KYHIEUVE", KYHIEUVE);
                updateBuilder.updateColumnValue("MAUSO", MAUSO);
                updateBuilder.updateColumnValue("MATRAMGIUA", MATRAMGIUA);
                 updateBuilder.where().eq("ID", ID);
                updateBuilder.update();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    //////////DmHoaDon//////////
    public static void AddDmHoaDon(final Context context) {
        ResClien restClient = new ResClien();
        restClient.GetService().GetDMHOADONs(new Callback<List<DmHoaDon>>() {
            @Override
            public void success(List<DmHoaDon> DmHoaDon, Response response) {
                for (int i = 0; i < DmHoaDon.size(); i++) {
                    String getTONGSOVEPHATHANH = String.valueOf(DmHoaDon.get(i).getTONGSOVEPHATHANH());
                    String getSOVEHIENTAI = String.valueOf(DmHoaDon.get(i).getSOVEHIENTAI());
                    String getIDVE = String.valueOf(DmHoaDon.get(i).getIDVE());

                    AddDmHoaDonSqlite(context,DmHoaDon.get(i).getIDHOADON().toString(),
                            DmHoaDon.get(i).getMAXE(),DmHoaDon.get(i).getKYHIEUVE(),
                            DmHoaDon.get(i).getMAUSO().toString(),getTONGSOVEPHATHANH,
                            getSOVEHIENTAI,getIDVE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("myLogs", "-------ERROR-------Slide");
                Log.d("myLogs", Log.getStackTraceString(error));
            }
        });

    }
    private static Dao<DmHoaDon, Integer> DmHoaDonMangasDao;
    private static List<DmHoaDon> DmHoaDonMangasList;
    public static void AddDmHoaDonSqlite(Context context,String IDHOADON,
                                         String MAXE,
                                         String KYHIEUVE,
                                         String MAUSO,
                                         String TONGSOVEPHATHANH,
                                         String SOVEHIENTAI,
                                         String IDVE) {
        try {

            DmHoaDonMangasDao = getHelper(context).getDmHoaDonMangasDao();
            QueryBuilder<DmHoaDon, Integer> queryBuilder = DmHoaDonMangasDao.queryBuilder();
            queryBuilder.where().eq("IDHOADON", IDHOADON);
            DmHoaDonMangasList = queryBuilder.query();
            if (DmTaiXeMangasList.size() <= 0) {
                final DmHoaDon DmHoaDon = new DmHoaDon();
                DmHoaDon.IDHOADON =IDHOADON ;
                DmHoaDon.MAXE = MAXE;
                DmHoaDon.KYHIEUVE = KYHIEUVE;
                DmHoaDon.MAUSO = MAUSO;
                DmHoaDon.TONGSOVEPHATHANH = Integer.parseInt(TONGSOVEPHATHANH);
                DmHoaDon.SOVEHIENTAI = Integer.parseInt(SOVEHIENTAI) ;
                DmHoaDon.IDVE = Integer.parseInt(IDVE) ;

                try {
                    final Dao<DmHoaDon, Integer> dmHoaDon = getHelper(context).getDmHoaDonMangasDao();
                    dmHoaDon.create(DmHoaDon);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                UpdateBuilder<DmHoaDon, Integer> updateBuilder = DmHoaDonMangasDao.updateBuilder();
                updateBuilder.updateColumnValue("IDHOADON", IDHOADON);
                updateBuilder.updateColumnValue("MAXE", MAXE);
                updateBuilder.updateColumnValue("KYHIEUVE", KYHIEUVE);
                updateBuilder.updateColumnValue("TONGSOVEPHATHANH", TONGSOVEPHATHANH);
                updateBuilder.updateColumnValue("SOVEHIENTAI", SOVEHIENTAI);
                updateBuilder.updateColumnValue("IDVE", IDVE);
                updateBuilder.where().eq("IDHOADON", IDHOADON);
                updateBuilder.update();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
