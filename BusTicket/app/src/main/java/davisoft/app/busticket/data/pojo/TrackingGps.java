package davisoft.app.busticket.data.pojo;

/**
 * Created by ducthien on 02/03/2017.
 */

public class TrackingGps {
    public long IdTracking ;
    public String MaXe ;
    public String MaTuyen ;
    public String Time ;
    public String DeviceId;
    public String Lat;
    public String lng ;

    public long getIdTracking() {
        return IdTracking;
    }

    public void setIdTracking(long idTracking) {
        IdTracking = idTracking;
    }

    public String getMaXe() {
        return MaXe;
    }

    public void setMaXe(String maXe) {
        MaXe = maXe;
    }

    public String getMaTuyen() {
        return MaTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        MaTuyen = maTuyen;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
