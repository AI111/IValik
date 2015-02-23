package com.example.sasha.ivalik.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sasha on 2/21/15.
 */
@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField()
    private short weight;
    ;
    @DatabaseField()
    private short height;
    @DatabaseField()
    private boolean gender;
    @DatabaseField()
    private TainingPurpose purpose;
    @DatabaseField()
    private double latitude;
    @DatabaseField()
    private double longtitude;

    public User() {
        super();
    }

    public User(short weight, short height, boolean gender, TainingPurpose purpose, double latitude, double longtitude) {
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.purpose = purpose;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public TainingPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(TainingPurpose purpose) {
        this.purpose = purpose;
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

    @Override
    public String toString() {
        return "User{" +
                "weight=" + weight +
                ", height=" + height +
                ", gender=" + gender +
                ", purpose=" + purpose +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                "}\n";
    }

    enum TainingPurpose {increasing_muscle_mass, grow_thin, be_strong, stamina}
}
