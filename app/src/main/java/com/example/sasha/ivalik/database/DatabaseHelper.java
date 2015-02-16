package com.example.sasha.ivalik.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sasha.ivalik.models.CustomExercise;
import com.example.sasha.ivalik.models.Exercise;
import com.example.sasha.ivalik.models.Training;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by sasha on 1/26/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    //имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
    private static final String DATABASE_NAME = "myappname.db";

    //с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
    private static final int DATABASE_VERSION = 1;

    //ссылки на DAO соответсвующие сущностям, хранимым в БД
    private ExerciseDAO exerciseDAO = null;
    private CustomExerciseDAO customExerciseDAO = null;
    private TrainingDAO trainingDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Выполняется, когда файл с БД не найден на устройстве
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Exercise.class);
            TableUtils.createTable(connectionSource, Training.class);
            TableUtils.createTable(connectionSource, CustomExercise.class);

        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer) {

        try {
            TableUtils.dropTable(connectionSource, Exercise.class, true);
            TableUtils.dropTable(connectionSource, Training.class, true);
            TableUtils.dropTable(connectionSource, CustomExercise.class, true);


            //TableUtils.dropTable(connectionSource, Role.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from ver " + oldVer);
            throw new RuntimeException(e);
        }
    }

    public ExerciseDAO getExerciseDAO() throws SQLException {
        if (exerciseDAO == null) {
            exerciseDAO = new ExerciseDAO(getConnectionSource(), Exercise.class);
        }
        return exerciseDAO;
    }

    public CustomExerciseDAO getCustomExerciseDAO() throws SQLException {
        if (customExerciseDAO == null) {
            customExerciseDAO = new CustomExerciseDAO(getConnectionSource(), CustomExercise.class);
        }
        return customExerciseDAO;
    }

    public TrainingDAO getTrainingDAO() throws SQLException {
        if (customExerciseDAO == null) {
            trainingDAO = new TrainingDAO(getConnectionSource(), Training.class);
        }
        return trainingDAO;
    }

    //выполняется при закрытии приложения
    @Override
    public void close() {
        super.close();
        exerciseDAO = null;
        trainingDAO = null;
        customExerciseDAO = null;
    }
}
