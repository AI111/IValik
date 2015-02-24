package com.example.sasha.ivalik.geolocation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.example.sasha.ivalik.MainActivity;
import com.example.sasha.ivalik.database.HelperFactory;
import com.example.sasha.ivalik.models.GeoPoint;

import java.sql.SQLException;

/**
 * Created by sasha on 2/23/15.
 */
public class SpyService extends Service {
    private static final int LOCATION_INTERVAL = 5000;
    private static final float LOCATION_DISTANCE = 10f;
    LocationManager locationManager;

    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Log.d(MainActivity.LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(MainActivity.LOG_TAG, "onStartCommand");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.LOG_TAG, "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d(MainActivity.LOG_TAG, "onBind");
        return null;
    }

    void someTask() {
        new Thread(new Runnable() {
            public void run() {
//                for (int i = 1; i<=30; i++) {
//                    Log.d(MainActivity.LOG_TAG, "i = " + i);
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                stopSelf();

                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        // makeUseOfNewLocation(location);
                        Log.d(MainActivity.LOG_TAG, " onLocationChanged " + location.getLatitude() + " " + location.getLongitude() + " " + location.getAccuracy() + " " + location.getProvider() + " " + location.getTime());
                        try {
                            HelperFactory.getHelper().getGeoPointDAO().create(new GeoPoint(location.getLatitude(), location.getLongitude(), location.getAccuracy(), location.getTime()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        Log.d(MainActivity.LOG_TAG, " onStatusChanged " + provider);
                    }

                    public void onProviderEnabled(String provider) {
                        Log.d(MainActivity.LOG_TAG, " onProviderEnabled " + provider);

                    }

                    public void onProviderDisabled(String provider) {
                        Log.d(MainActivity.LOG_TAG, " onProviderDisabled " + provider);

                    }
                };
                Looper.prepare();
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, locationListener);

                Looper.loop();
            }
        }).start();
    }
}
