package davisoft.app.busticket.data;


import java.util.List;

import davisoft.app.busticket.data.pojo.Counters;
import davisoft.app.busticket.data.pojo.DmTaiXe;
import davisoft.app.busticket.data.pojo.DmTram;
import davisoft.app.busticket.data.pojo.DmTuyen;
import davisoft.app.busticket.data.pojo.DmTuyenChiTietTram;
import davisoft.app.busticket.data.pojo.DmXe;
import davisoft.app.busticket.data.pojo.LoTrinhChoXe;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by TuNguyen on 01/12/2017.
 */

public interface ServiceConnect {
    @GET("/DMTAIXEs/")
    void GetDMTAIXEs(Callback<List<DmTaiXe>> items);
    @GET("/DMTRAMs/")
    void GetDMTRAMs(Callback<List<DmTram>> items);
    @GET("/DMTUYENs/")
    void GetDMTUYENs(Callback<List<DmTuyen>> items);
    @GET("/DMXEs/")
    void GetDMXEs(Callback<List<DmXe>> items);
    @GET("/DMTUYENCHITIETTRAMs/")
    void GetDMTUYENCHITIETTRAMs(Callback<List<DmTuyenChiTietTram>> items);
    @GET("/LOTRINHCHOXEs/")
    void GetLOTRINHCHOXEs(Callback<List<LoTrinhChoXe>> items);
    @GET("/Counters/")
    void GetCounters(Callback<List<Counters>> items);
}
