package com.example.sasha.ivalik.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sasha on 2/15/15.
 */
@DatabaseTable(tableName = "exercise")
public class Exercise {
    @DatabaseField(generatedId = true)
    private int Id;
    ;
    @DatabaseField()
    private int nameId;
    @DatabaseField()
    private byte pM;
    @DatabaseField()
    private int descriptionId;
    @DatabaseField()
    private String url1;
    @DatabaseField()
    private String url2;

    public Exercise(int nameId, byte pM, int descriptionId, String url1, String url2) {
        this.nameId = nameId;
        this.pM = pM;
        this.descriptionId = descriptionId;
        this.url1 = url1;
        this.url2 = url2;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public byte getpM() {
        return pM;
    }

    public void setpM(byte pM) {
        this.pM = pM;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(int descriptionId) {
        this.descriptionId = descriptionId;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "Id=" + Id +
                ", nameId=" + nameId +
                ", pM=" + pM +
                ", descriptionId=" + descriptionId +
                ", url1='" + url1 + '\'' +
                ", url2='" + url2 + '\'' +
                "}\n";
    }

    enum muscleType {}
}
