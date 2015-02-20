package com.example.sasha.ivalik.database;

import com.example.sasha.ivalik.models.Training;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sasha on 2/15/15.
 */
public class TrainingDAO extends BaseDaoImpl<Training, Integer> {

    protected TrainingDAO(ConnectionSource connectionSource,
                          Class<Training> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Training> getAllTrainings() throws SQLException {
        return this.queryForAll();
    }
}

