package com.example.sasha.ivalik.geolocation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.sasha.ivalik.MainActivity;
import com.example.sasha.ivalik.R;
import com.example.sasha.ivalik.database.HelperFactory;
import com.example.sasha.ivalik.models.User;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;

/**
 * Created by sasha on 2/23/15.
 */
public class SpyService2 extends Service {
    private static final int LOCATION_INTERVAL = 60 * 1000;
    private static final float LOCATION_DISTANCE = 0f;
    private static final double radius = 0.0005;
    private static final double r2 = radius * radius;
    LocationManager locationManager;
    private LatLng latLng;
    private LocationListener locationListener;

    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Log.d(MainActivity.LOG_TAG, "onCreate");
        try {
            User user = HelperFactory.getHelper().getUserDAO().queryForId(0);
            latLng = new LatLng(user.getLatitude(), user.getLongtitude());
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(MainActivity.LOG_TAG, "onStartCommand");
        someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
        locationManager = null;
        stopSelf();
        Log.d(MainActivity.LOG_TAG, "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d(MainActivity.LOG_TAG, "onBind");
        return null;
    }

    void someTask() {
        new Thread(new Runnable() {
            public void run() {


                locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        // makeUseOfNewLocation(location);
                        Log.d(MainActivity.LOG_TAG, " onLocationChanged " + location.getLatitude() + " " + location.getLongitude() + " " + location.getAccuracy() + " " + location.getProvider() + " " + location.getTime());
                        double x = latLng.longitude - location.getLongitude();
                        double y = latLng.latitude - location.getLatitude();

                        if (x * x + y * y < r2) {
                            Log.d(MainActivity.LOG_TAG, " in gym ");

                        } else {
                            Log.d(MainActivity.LOG_TAG, " not in gym ");
                            showNotification(1);
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

    public void showNotification(int mId) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.plus)
                        .setContentTitle("My notification")
                        .setContentText("ПИЗДУЙ В ЗАЛ")
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.go_gym));
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
        Log.d(MainActivity.LOG_TAG, " ///////////////// ");
    }
}
