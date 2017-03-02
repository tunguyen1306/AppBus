package davisoft.app.busticket.data;


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
import davisoft.app.busticket.data.pojo.TrackingGps;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
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
    @GET("/DICHVUs/")
    void GetDICHVUs(Callback<List<DichVu>> items);
    @GET("/DMHOADONs/")
    void GetDMHOADONs(Callback<List<DmHoaDon>> items);
    @GET("/DMHOADONs/CountView/")
    void CountView(@Query("id")String id,Callback<List<DmHoaDon>> items);

    @POST("/TrackingGPs/")
    void PostTrackingGP(@Body TrackingGps item, Callback<List<TrackingGps>> items);
}
