package com.example.sasha.ivalik.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sasha on 2/15/15.
 */
@DatabaseTable(tableName = "custom_exercise")
public class CustomExercise {

    @DatabaseField(foreign = true)
    private Exercise exercise;  //id упражнения

    @DatabaseField()
    private byte approach;

    @DatabaseField()
    private byte repeat;

    @DatabaseField()
    private byte percentPM;

    @DatabaseField()
    private boolean finished;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Training training; //id of feeld in  training

    public CustomExercise(Exercise exercise, byte approach, byte repeat, byte percentPM, boolean finished) {
        this.exercise = exercise;
        this.approach = approach;
        this.repeat = repeat;
        this.percentPM = percentPM;
        this.finished = finished;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public byte getApproach() {
        return approach;
    }

    public void setApproach(byte approach) {
        this.approach = approach;
    }

    public byte getRepeat() {
        return repeat;
    }

    public void setRepeat(byte repeat) {
        this.repeat = repeat;
    }

    public byte getPercentPM() {
        return percentPM;
    }

    public void setPercentPM(byte percentPM) {
        this.percentPM = percentPM;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    @Override
    public String toString() {
        return "CustomExercise{" +
                "exercise=" + exercise +
                ", approach=" + approach +
                ", repeat=" + repeat +
                ", percentPM=" + percentPM +
                ", finished=" + finished +
                "}\n";
    }
}
