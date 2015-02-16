package com.example.sasha.ivalik.models;

import com.example.sasha.ivalik.database.HelperFactory;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

/**
 * Created by sasha on 2/15/15.
 */
@DatabaseTable(tableName = "training")
public class Training {

    @ForeignCollectionField(eager = true)
    private Collection<CustomExercise> roleList;

    @DatabaseField()
    private Date date;

    public void addCustomExercise(CustomExercise value) throws SQLException {
        value.setTraining(this);
        HelperFactory.getHelper().getCustomExerciseDAO().create(value);
        roleList.add(value);
    }

    public void removeCustomExercise(CustomExercise value) throws SQLException {
        roleList.remove(value);
        HelperFactory.getHelper().getCustomExerciseDAO().delete(value);
    }
}
