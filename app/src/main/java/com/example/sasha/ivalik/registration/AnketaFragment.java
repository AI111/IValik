package com.example.sasha.ivalik.registration;

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
import com.example.sasha.ivalik.models.CustomExercise;
import com.example.sasha.ivalik.models.Exercise;

import java.sql.SQLException;

/**
 * Created by sasha on 2/10/15.
 */
public class AnketaFragment extends Fragment implements View.OnClickListener {
    TextView textView;
    private Button addExercise, showTables, addCustomExercise;

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
        textView = (TextView) rootView.findViewById(R.id.textView19);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button:
                Exercise exercise1 = new Exercise(R.string.pull_ups_on_the_bar, (byte) 0, R.string.pull_ups_on_the_bar_desc, "o_b189b90655199e57-0.jpg", "o_b189b90655199e57-1.jpg");
                Exercise exercise2 = new Exercise(R.string.dips, (byte) 0, R.string.dips_description, "o_4add3c18dd626cdc-0.jpg", "o_4add3c18dd626cdc-1.jpg");
                try {
                    HelperFactory.getHelper().getExerciseDAO().createIfNotExists(exercise1);
                    HelperFactory.getHelper().getExerciseDAO().createIfNotExists(exercise2);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button2:
                try {
                    textView.setText(HelperFactory.getHelper().getExerciseDAO().getAllExercises().toString() + "\n" + HelperFactory.getHelper().getCustomExerciseDAO().getAllCustomExercises().toString() + "\n" + HelperFactory.getHelper().getTrainingDAO().getAllTrainings().toString());

//                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getExerciseDAO().getAllExercises().toString());
//                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getCustomExerciseDAO().getAllCustomExercises().toString());
//                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getTrainingDAO().getAllTrainings().toString());
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
        }

    }
}
