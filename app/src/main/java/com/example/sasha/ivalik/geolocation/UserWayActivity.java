package com.example.sasha.ivalik.geolocation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.sasha.ivalik.R;
import com.example.sasha.ivalik.database.HelperFactory;
import com.example.sasha.ivalik.models.GeoPoint;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sasha on 2/23/15.
 */
public class UserWayActivity extends FragmentActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        List<GeoPoint> points = null;
        // Polylines are useful for marking paths and routes on the map.

        try {
            points = HelperFactory.getHelper().getGeoPointDAO().getAllGeoPoints();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (points != null) {
            PolylineOptions options = new PolylineOptions();
            for (GeoPoint point : points) {
                options.add(new LatLng(point.getLatitude(), point.getLongtitude()));
            }
            map.addPolyline(options.geodesic(true));

        }

    }
}

