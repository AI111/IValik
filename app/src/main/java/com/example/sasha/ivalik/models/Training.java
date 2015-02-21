package com.example.sasha.ivalik.models;

import com.example.sasha.ivalik.database.HelperFactory;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by sasha on 2/15/15.
 */
@DatabaseTable(tableName = "training")
public class Training {
    @DatabaseField(generatedId = true)
    private int id;
    @ForeignCollectionField(eager = true)
    private Collection<CustomExercise> exercises;
    @DatabaseField()
    private Date date;

    public Training(Date date) {
        this.date = date;
        exercises = new ArrayList<>();

    }

    public Training() {
        super();
        exercises = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addCustomExercise(CustomExercise value) throws SQLException {
        value.setTraining(this);
        HelperFactory.getHelper().getCustomExerciseDAO().create(value);
        exercises.add(value);
    }

    public void removeCustomExercise(CustomExercise value) throws SQLException {
        exercises.remove(value);
        HelperFactory.getHelper().getCustomExerciseDAO().delete(value);
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", exercises=" + /*Arrays.toString(exercises.toArray()) + */" " + exercises.size() +
                ", date=" + date +
                "}\n";
    }
}
