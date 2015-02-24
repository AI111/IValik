package com.example.sasha.ivalik.database;

import com.example.sasha.ivalik.models.GeoPoint;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sasha on 2/15/15.
 */
public class GeoPointDAO extends BaseDaoImpl<GeoPoint, Integer> {

    protected GeoPointDAO(ConnectionSource connectionSource,
                          Class<GeoPoint> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<GeoPoint> getAllGeoPoints() throws SQLException {
        return this.queryForAll();
    }
}

