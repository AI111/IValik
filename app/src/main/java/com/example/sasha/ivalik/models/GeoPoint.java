package com.example.sasha.ivalik.models;

import android.text.format.DateFormat;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sasha on 2/23/15.
 */
@DatabaseTable(tableName = "geo_point")
public class GeoPoint {

    @DatabaseField()
    private double latitude;
    @DatabaseField()
    private double longtitude;
    @DatabaseField()
    private float accuracy;
    @DatabaseField()
    private long time;

    public GeoPoint() {

    }

    public GeoPoint(double latitude, double longtitude, float accuracy, long time) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.accuracy = accuracy;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
                "latitude=" + latitude +
                ", longtitude=" + longtitude +
                ", accuracy=" + accuracy +
                ", time=" + DateFormat.format("y M E k m s", time) +
                "}\n";
    }
}
