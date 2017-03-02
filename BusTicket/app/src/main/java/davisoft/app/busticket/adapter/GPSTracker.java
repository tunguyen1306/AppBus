package davisoft.app.busticket.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.security.Provider;

import davisoft.app.busticket.MainActivity;


public class GPSTracker implements SensorEventListener {

    MainActivity context;
    public String provider="";
    Location oldLocation=null;
    Location location=null;
    public LocationManager locationManager;
    public LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(context,
                    "Provider onStatusChanged: " + provider+" Status:"+status, Toast.LENGTH_SHORT)
                    .show();
            context.resetLayout();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(context,
                    "Provider enabled: " + provider, Toast.LENGTH_SHORT)
                    .show();
            context.resetLayout();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(context,
                    "Provider disabled: " + provider, Toast.LENGTH_SHORT)
                    .show();
            context.resetLayout();
        }

        @Override
        public void onLocationChanged(Location location) {
            // Do work with new location. Implementation of this method will be covered later.
            doWorkWithNewLocation(location);
            Toast.makeText(context,
                    "onLocationChanged: " + location.toString(), Toast.LENGTH_SHORT)
                    .show();

        }
    };
    public GPSTracker(MainActivity pcontext)
    {
        this.context=pcontext;

        initLocation();
    }

    public void initLocation()
    {

        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        mRotationMatrix = new float[16];
        mOrientation = new float[9];

        long minTime = 2 * 1000; // Minimum time interval for update in seconds, i.e. 5 seconds.
        long minDistance = 5; // Minimum distance change for update in meters, i.e. 10 meters.

        // Assign LocationListener to LocationManager in order to receive location updates.
        // Acquiring provider that is used for location updates will also be covered later.
        // Instead of LocationListener, PendingIntent can be assigned, also instead of
        // provider name, criteria can be used, but we won't use those approaches now.
        provider=getProviderName();
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, minTime,
                minDistance, locationListener);

        oldLocation=locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER);
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_UI);
    }
    /**
     * Make use of location after deciding if it is better than previous one.
     *
     * @param location Newly acquired location.
     */
    void doWorkWithNewLocation(Location location) {

        if(isBetterLocation(oldLocation, location)) {
            // If location is better, do some user preview.
       //     Toast.makeText(context,
           //         "Better location found: " + provider, Toast.LENGTH_SHORT)
           //         .show();
            this.location=location;
        }
        else
        {
            oldLocation=location;
        }
        updateGeomagneticField();
    }

    /**
     * Time difference threshold set for one minute.
     */
    static final int TIME_DIFFERENCE_THRESHOLD = 1 * 60 * 1000;

    /**
     * Decide if new location is better than older by following some basic criteria.
     * This algorithm can be as simple or complicated as your needs dictate it.
     * Try experimenting and get your best location strategy algorithm.
     *
     * @param oldLocation Old location used for comparison.
     * @param newLocation Newly acquired location compared to old one.
     * @return If new location is more accurate and suits your criteria more than the old one.
     */
    boolean isBetterLocation(Location oldLocation, Location newLocation) {
        // If there is no old location, of course the new location is better.
        if(oldLocation == null) {
            return true;
        }

        // Check if new location is newer in time.
        boolean isNewer = newLocation.getTime() > oldLocation.getTime();

        // Check if new location more accurate. Accuracy is radius in meters, so less is better.
        boolean isMoreAccurate = newLocation.getAccuracy() < oldLocation.getAccuracy();
        if(isMoreAccurate && isNewer) {
            // More accurate and newer is always better.
            return true;
        } else if(isMoreAccurate && !isNewer) {
            // More accurate but not newer can lead to bad fix because of user movement.
            // Let us set a threshold for the maximum tolerance of time difference.
            long timeDifference = newLocation.getTime() - oldLocation.getTime();

            // If time difference is not greater then allowed threshold we accept it.
            if(timeDifference > -TIME_DIFFERENCE_THRESHOLD) {
                return true;
            }
        }

        return false;
    }
    /**
     * Get provider name.
     * @return Name of best suiting provider.
     * */
    public String getProviderName() {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW); // Chose your desired power consumption level.
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // Choose your accuracy requirement.
        criteria.setSpeedRequired(true); // Chose if speed for first location fix is required.
        criteria.setAltitudeRequired(false); // Choose if you use altitude.
        criteria.setBearingRequired(false); // Choose if you use bearing.
        criteria.setCostAllowed(false); // Choose if this provider can waste money :-)

        // Provide your criteria and flag enabledOnly that tells
        // LocationManager only to return active providers.
        return locationManager.getBestProvider(criteria, true);
    }

    public Location getLocation() {
        try {


            location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
        if (mSensorManager!=null)
        {
            mSensorManager.unregisterListener(this);
        }
    }


    private static final int ARM_DISPLACEMENT_DEGREES = 6;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            //Get the current heading from the sensor
            SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values);
            SensorManager.remapCoordinateSystem(mRotationMatrix, SensorManager.AXIS_X,
                    SensorManager.AXIS_Z, mRotationMatrix);
            SensorManager.getOrientation(mRotationMatrix, mOrientation);

            //Log.i("GPSLocationManager-heading", "ort: "+mOrientation[0]);

            // Convert the heading (which is relative to magnetic north) to one that is
            // relative to true north, using the user's current location to compute this.
            float magneticHeading = (float) Math.toDegrees(mOrientation[0]);
            float mHeading = mod(computeTrueNorth(magneticHeading), 360.0f) - ARM_DISPLACEMENT_DEGREES;
            //Log.i("GPS-heading", "heading: "+mHeading);
            //Set the map's rotation angle to the true north heading.
           // this.mMapView.setRotationAngle(mHeading);


        }
    }
    float[] mRotationMatrix;
    float[] mOrientation;
    SensorManager mSensorManager;
    GeomagneticField mGeomagneticField;
    private void updateGeomagneticField() {
        this.mGeomagneticField = new GeomagneticField((float) this.location.getLatitude(),
                (float) location.getLongitude(), (float) location.getAltitude(),
                location.getTime());
    }
    private float computeTrueNorth(float heading) {
        if (mGeomagneticField != null) {
            return heading + mGeomagneticField.getDeclination();
        } else {
            return heading;
        }
    }
    public  float mod(float a, float b) {
        return (a % b + b) % b;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
