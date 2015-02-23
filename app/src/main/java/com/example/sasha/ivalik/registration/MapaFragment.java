package com.example.sasha.ivalik.registration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sasha.ivalik.MainActivity;
import com.example.sasha.ivalik.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by sasha on 2/10/15.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback {
    Marker marker;
    private SupportMapFragment fragment;
    private GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map, container, false);

        return rootView;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d(MainActivity.LOG_TAG, "onMapLongClick " + latLng.latitude + " " + latLng.longitude);
                if (marker == null)
                    marker = googleMap.addMarker(new MarkerOptions().title("Зал").snippet("Место тренеровок").position(latLng).anchor(0.0f, 1.0f).draggable(true));
            }
        });
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.d(MainActivity.LOG_TAG, "onMarkerDragStart " + marker.getPosition().latitude + " " + marker.getPosition().longitude);

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.d(MainActivity.LOG_TAG, "onMarkerDragEnd " + marker.getPosition().latitude + " " + marker.getPosition().longitude);
                // marker.setPosition();
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
        fragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (map == null) {
//            map = fragment.getMap();
//            map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
//        }
    }

}
