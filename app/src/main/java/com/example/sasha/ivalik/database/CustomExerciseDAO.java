package com.example.sasha.ivalik.database;

import com.example.sasha.ivalik.models.CustomExercise;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sasha on 2/15/15.
 */
public class CustomExerciseDAO extends BaseDaoImpl<CustomExercise, Integer> {

    protected CustomExerciseDAO(ConnectionSource connectionSource,
                                Class<CustomExercise> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<CustomExercise> getAllCustomExercises() throws SQLException {
        return this.queryForAll();
    }
}

