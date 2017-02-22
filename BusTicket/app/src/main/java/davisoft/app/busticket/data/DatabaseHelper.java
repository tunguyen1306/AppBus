package davisoft.app.busticket.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import davisoft.app.busticket.data.pojo.Counters;
import davisoft.app.busticket.data.pojo.DichVu;
import davisoft.app.busticket.data.pojo.DmHoaDon;
import davisoft.app.busticket.data.pojo.DmTaiXe;
import davisoft.app.busticket.data.pojo.DmTram;
import davisoft.app.busticket.data.pojo.DmTuyen;
import davisoft.app.busticket.data.pojo.DmTuyenChiTietTram;
import davisoft.app.busticket.data.pojo.DmXe;
import davisoft.app.busticket.data.pojo.LoTrinhChoXe;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME    = "davisoft.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Counters, Integer> CountersMangasesDao;
    private Dao<DmTaiXe, Integer> DmTaiXeMangasesDao;
    private Dao<DmTram, Integer> DmTramMangasesDao;
    private Dao<DmTuyen, Integer>DmTuyenMangasesDao;
    private Dao<DmTuyenChiTietTram, Integer> DmTuyenChiTietTramMangasesDao;
    private Dao<DmXe, Integer> DmXeMangasesDao;
    private Dao<LoTrinhChoXe, Integer> LoTrinhChoXeMangasesDao;
    private Dao<DmHoaDon, Integer>DmHoaDonMangasesDao;
    private Dao<DichVu, Integer>DichVuMangasesDao;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTable(connectionSource, Counters.class);
            TableUtils.createTable(connectionSource, DmTaiXe.class);
            TableUtils.createTable(connectionSource, DmTram.class);
            TableUtils.createTable(connectionSource, DmTuyen.class);
            TableUtils.createTable(connectionSource, DmTuyenChiTietTram.class);
            TableUtils.createTable(connectionSource, DmXe.class);
            TableUtils.createTable(connectionSource, LoTrinhChoXe.class);
            TableUtils.createTable(connectionSource, DichVu.class);
            TableUtils.createTable(connectionSource, DmHoaDon.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, Counters.class,true);
            TableUtils.dropTable(connectionSource, DmTaiXe.class,true);
            TableUtils.dropTable(connectionSource, DmTram.class,true);
            TableUtils.dropTable(connectionSource, DmTuyen.class,true);
            TableUtils.dropTable(connectionSource, DmTuyenChiTietTram.class,true);
            TableUtils.dropTable(connectionSource, DmXe.class,true);
            TableUtils.dropTable(connectionSource, LoTrinhChoXe.class,true);
            TableUtils.dropTable(connectionSource, DichVu.class,true);
            TableUtils.dropTable(connectionSource, DmHoaDon.class,true);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }
    }
    public Dao<Counters, Integer> getCountMangasDao() throws SQLException {
        if (CountersMangasesDao == null) {
            CountersMangasesDao = getDao(Counters.class);
        }
        return CountersMangasesDao;
    }
    public Dao<DmTaiXe, Integer> getDmTaiXeMangasDao() throws SQLException {
        if (DmTaiXeMangasesDao == null) {
            DmTaiXeMangasesDao = getDao(DmTaiXe.class);
        }
        return DmTaiXeMangasesDao;
    }
    public Dao<DmTram, Integer> getDmTramMangasDao() throws SQLException {
        if (DmTramMangasesDao == null) {
            DmTramMangasesDao = getDao(DmTram.class);
        }
        return DmTramMangasesDao;
    }
    public Dao<DmTuyen, Integer> getDmTuyenMangasDao() throws SQLException {
        if (DmTuyenMangasesDao == null) {
            DmTuyenMangasesDao = getDao(DmTuyen.class);
        }
        return DmTuyenMangasesDao;
    }
    public Dao<DmTuyenChiTietTram, Integer> getDmTuyenChiTietTramMangasDao() throws SQLException {
        if (DmTuyenChiTietTramMangasesDao == null) {
            DmTuyenChiTietTramMangasesDao = getDao(DmTuyenChiTietTram.class);
        }
        return DmTuyenChiTietTramMangasesDao;
    }
    public Dao<DmXe, Integer> getDmXeMangasDao() throws SQLException {
        if (DmXeMangasesDao == null) {
            DmXeMangasesDao = getDao(DmXe.class);
        }
        return DmXeMangasesDao;
    }
    public Dao<LoTrinhChoXe, Integer> getLoTrinhChoXeMangasDao() throws SQLException {
        if (LoTrinhChoXeMangasesDao == null) {
            LoTrinhChoXeMangasesDao = getDao(LoTrinhChoXe.class);
        }
        return LoTrinhChoXeMangasesDao;
    }

    public Dao<DichVu, Integer> getDichVuMangasDao() throws SQLException {
        if (DichVuMangasesDao == null) {
            DichVuMangasesDao = getDao(DichVu.class);
        }
        return DichVuMangasesDao;
    }

    public Dao<DmHoaDon, Integer> getDmHoaDonMangasDao() throws SQLException {
        if (DmHoaDonMangasesDao == null) {
            DmHoaDonMangasesDao = getDao(DmHoaDon.class);
        }
        return DmHoaDonMangasesDao;
    }
}


