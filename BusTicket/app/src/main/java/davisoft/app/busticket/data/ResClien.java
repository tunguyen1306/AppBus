package davisoft.app.busticket.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.client.OkClient;

/**
 * Created by TuNguyen on 01/12/2017.
 */

public class ResClien {
    //You need to change the IP if you testing environment is not local machine
    //or you may have different URL than we have here
    // private static final String URL = "http://instinctcoder.com/wp-content/uploads/2015/08/";
    private static final String URL = "http://bus.vangia.net/api";
    private retrofit.RestAdapter restAdapter;
    private ServiceConnect serviceman;

    public ResClien()
    {

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);

        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .build();
        serviceman =restAdapter.create(ServiceConnect.class);
        Gson localGson = new GsonBuilder().create();

    }

    public  ServiceConnect GetService()
    {
        return serviceman;
    }
}
