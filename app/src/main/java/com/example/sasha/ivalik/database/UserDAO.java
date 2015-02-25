package com.example.sasha.ivalik.database;

import com.example.sasha.ivalik.models.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sasha on 2/25/15.
 */
public class UserDAO extends BaseDaoImpl<User, Integer> {

    protected UserDAO(ConnectionSource connectionSource,
                      Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<User> getAllUsers() throws SQLException {
        return this.queryForAll();
    }
}