package com.example.sasha.ivalik.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sasha.ivalik.MainActivity;
import com.example.sasha.ivalik.R;
import com.example.sasha.ivalik.database.HelperFactory;
import com.example.sasha.ivalik.geolocation.SpyService2;
import com.example.sasha.ivalik.models.CustomExercise;
import com.example.sasha.ivalik.models.Exercise;
import com.example.sasha.ivalik.models.Training;
import com.example.sasha.ivalik.models.User;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by sasha on 2/21/15.
 */
public class TestFragment extends Fragment implements View.OnClickListener, OnChangeFragmente {
    TextView textView;
    private Button addExercise, showTables, addCustomExercise, addTraining, save, start, stop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.test_fragment, container, false);
        addExercise = (Button) rootView.findViewById(R.id.button);
        addExercise.setOnClickListener(this);

        showTables = (Button) rootView.findViewById(R.id.button2);
        showTables.setOnClickListener(this);

        addCustomExercise = (Button) rootView.findViewById(R.id.button4);
        addCustomExercise.setOnClickListener(this);

        addTraining = (Button) rootView.findViewById(R.id.button5);
        addTraining.setOnClickListener(this);

        start = (Button) rootView.findViewById(R.id.button6);
        start.setOnClickListener(this);

        stop = (Button) rootView.findViewById(R.id.button7);
        stop.setOnClickListener(this);

        save = (Button) rootView.findViewById(R.id.button8);
        save.setOnClickListener(this);

        textView = (TextView) rootView.findViewById(R.id.textView19);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {


            case R.id.button2:
                try {
                    textView.setText(HelperFactory.getHelper().getExerciseDAO().getAllExercises().toString() + "\n" + HelperFactory.getHelper().getCustomExerciseDAO().getAllCustomExercises().toString() + "\n" + HelperFactory.getHelper().getTrainingDAO().getAllTrainings().toString());

                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getExerciseDAO().getAllExercises().toString());
                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getCustomExerciseDAO().getAllCustomExercises().toString());
                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getTrainingDAO().getAllTrainings().toString());
                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getUserDAO().getAllUsers().toString());
                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getGeoPointDAO().getAllGeoPoints().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button4:
                Exercise exercise = null;
                try {
                    exercise = HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.dips);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Log.d(MainActivity.LOG_TAG, " Exercise for id =" + exercise);

                CustomExercise customExercise = new CustomExercise(exercise
                        , (byte) 10, (byte) 10, (byte) 80, false);
                try {
                    HelperFactory.getHelper().getCustomExerciseDAO().create(customExercise);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.button5:
                Log.d(MainActivity.LOG_TAG, "ADD TRAININGS");
                Training training = new Training();
                training.setDate(new Date());
                CustomExercise customExercise1 = null;
                try {
                    customExercise1 = new CustomExercise(HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.pull_ups_on_the_bar)
                            , (byte) 10, (byte) 10, (byte) 80, false);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    HelperFactory.getHelper().getTrainingDAO().create(training);

                    training.addCustomExercise(new CustomExercise(HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.bench_press)
                            , (byte) 2, (byte) 10, (byte) 80, false));
                    training.addCustomExercise(new CustomExercise(HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.pull_ups_on_the_bar)
                            , (byte) 2, (byte) 10, (byte) 0, false));
                    training.addCustomExercise(new CustomExercise(HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.squats_on_his_shoulders)
                            , (byte) 4, (byte) 10, (byte) 30, false));
                    training.addCustomExercise(new CustomExercise(HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.deadlift)
                            , (byte) 2, (byte) 10, (byte) 30, false));
                    training.addCustomExercise(new CustomExercise(HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.lifting_dumbbells_for_biceps_sitting)
                            , (byte) 4, (byte) 10, (byte) 12, false));
                    training.addCustomExercise(new CustomExercise(HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.twist_on_an_incline_bench)
                            , (byte) 4, (byte) 10, (byte) 12, false));

                    Log.d(MainActivity.LOG_TAG, customExercise1.toString());
                    Log.d(MainActivity.LOG_TAG, training.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button6:
                //start
                getActivity().startService(new Intent(getActivity(), SpyService2.class));
                break;
            case R.id.button7:
                //stop
                getActivity().stopService(new Intent(getActivity(), SpyService2.class));
                break;
            case R.id.button8:
                try {
                    TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), User.class);
                    HelperFactory.getHelper().getUserDAO().create(RegistrationActivity.user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //save
                break;
        }

    }

    @Override
    public void saveData() {

    }
}
