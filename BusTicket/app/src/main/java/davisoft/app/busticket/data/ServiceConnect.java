package davisoft.app.busticket.data;


import java.util.List;

import davisoft.app.busticket.data.pojo.DmTaiXe;
import davisoft.app.busticket.data.pojo.DmTram;
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

}
