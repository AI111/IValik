package com.example.sasha.ivalik.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sasha.ivalik.R;
import com.example.sasha.ivalik.models.CustomExercise;
import com.example.sasha.ivalik.models.Exercise;
import com.example.sasha.ivalik.models.GeoPoint;
import com.example.sasha.ivalik.models.Training;
import com.example.sasha.ivalik.models.User;
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
    private GeoPointDAO geoPointDAO = null;
    private UserDAO userDAO = null;

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
            TableUtils.createTable(connectionSource, GeoPoint.class);
            TableUtils.createTable(connectionSource, User.class);
            writeTrainings();
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
            TableUtils.dropTable(connectionSource, GeoPoint.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);


            TableUtils.createTable(connectionSource, Exercise.class);
            TableUtils.createTable(connectionSource, Training.class);
            TableUtils.createTable(connectionSource, CustomExercise.class);
            TableUtils.createTable(connectionSource, GeoPoint.class);
            TableUtils.createTable(connectionSource, User.class);
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
        if (trainingDAO == null) {
            trainingDAO = new TrainingDAO(getConnectionSource(), Training.class);
        }
        return trainingDAO;
    }

    public GeoPointDAO getGeoPointDAO() throws SQLException {
        if (geoPointDAO == null) {
            geoPointDAO = new GeoPointDAO(getConnectionSource(), GeoPoint.class);
        }
        return geoPointDAO;
    }

    public UserDAO getUserDAO() throws SQLException {
        if (userDAO == null) {
            userDAO = new UserDAO(getConnectionSource(), User.class);
        }
        return userDAO;
    }

    private void writeTrainings() throws SQLException {
        HelperFactory.getHelper().getExerciseDAO().create(new Exercise(R.string.pull_ups_on_the_bar, (byte) 0, R.string.pull_ups_on_the_bar_desc, "o_b189b90655199e57-0.jpg", "o_b189b90655199e57-1.jpg", R.raw.stanovaya_pelmeshek));
        HelperFactory.getHelper().getExerciseDAO().create(new Exercise(R.string.dips, (byte) 0, R.string.dips_description, "o_4add3c18dd626cdc-0.jpg", "o_4add3c18dd626cdc-1.jpg", 0));
        HelperFactory.getHelper().getExerciseDAO().create(new Exercise(R.string.bench_press, (byte) 70, R.string.bench_press_description, "o_321230e9d2037e98-0.jpg", "o_321230e9d2037e98-1.jpg", R.raw.zhmi_silnee));
        HelperFactory.getHelper().getExerciseDAO().create(new Exercise(R.string.squats_on_his_shoulders, (byte) 40, R.string.squats_on_his_shoulders_description, "o_3be12118d114ad3b-1.jpg", "o_3be12118d114ad3b-2.jpg", R.raw.priseday_nakhuy));
        HelperFactory.getHelper().getExerciseDAO().create(new Exercise(R.string.lifting_dumbbells_for_biceps_sitting, (byte) 12, R.string.lifting_dumbbells_for_biceps_sitting_description, "o_c0f43f1a08915910-1.jpg", "o_c0f43f1a08915910-2.jpg", 0));
        HelperFactory.getHelper().getExerciseDAO().create(new Exercise(R.string.deadlift, (byte) 50, R.string.deadlift_description, "o_820d5ed6f9560f09-1.jpg", "o_820d5ed6f9560f09-1.jpg", R.raw.zhim_vytochu_spinu));
        HelperFactory.getHelper().getExerciseDAO().create(new Exercise(R.string.twist_on_an_incline_bench, (byte) 0, R.string.twist_on_an_incline_bench_description, "o_42cce36403b0a149-0.jpg", "o_42cce36403b0a149-1.jpg", 0));
        HelperFactory.getHelper().getExerciseDAO().create(new Exercise(R.string.press_bar_on_incline_bench, (byte) 70, R.string.press_bar_on_incline_bench_description, "o_db2bf39da41513cc-0.jpg", "o_db2bf39da41513cc-0.jpg", 0));
    }
    //выполняется при закрытии приложения
    @Override
    public void close() {
        super.close();
        exerciseDAO = null;
        trainingDAO = null;
        customExerciseDAO = null;
        geoPointDAO = null;
        userDAO = null;
    }
}
