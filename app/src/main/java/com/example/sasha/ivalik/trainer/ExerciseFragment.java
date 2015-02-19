package com.example.sasha.ivalik.trainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sasha.ivalik.MainActivity;
import com.example.sasha.ivalik.R;
import com.example.sasha.ivalik.models.CustomExercise;
import com.squareup.picasso.Picasso;

/**
 * Created by sasha on 2/18/15.
 */
public class ExerciseFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    CountDownTimer timer;
    ToggleButton toggleButton;
    private ProgressBar progressBar;
    private CustomExercise exercise;
    private ImageView img1, img2;
    private TextView description, title, approach, repeat, weight;

    public ExerciseFragment(CustomExercise exercise) {
        this.exercise = exercise;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_detail_fragment, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        description = (TextView) rootView.findViewById(R.id.textView15);
        description.setText(exercise.getExercise().getDescriptionId());
        title = (TextView) rootView.findViewById(R.id.textView12);
        title.setText(exercise.getExercise().getNameId());
        img1 = (ImageView) rootView.findViewById(R.id.imageView);
        Picasso.with(getActivity()).load("file:///android_asset/" + exercise.getExercise().getUrl1()).into(img1);

        img2 = (ImageView) rootView.findViewById(R.id.imageView2);
        Picasso.with(getActivity()).load("file:///android_asset/" + exercise.getExercise().getUrl2()).into(img2);
        approach = (TextView) rootView.findViewById(R.id.textView17);
        toggleButton = (ToggleButton) rootView.findViewById(R.id.UP_DOWN);
        toggleButton.setOnCheckedChangeListener(this);
        progressBar.setMax(5000);
        progressBar.setOnClickListener(this);

        return rootView;
    }

    public void startTimer() {
        progressBar.setProgress(0);
        timer = new CountDownTimer(5000, 20) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) (5000 - millisUntilFinished));
                Log.d(MainActivity.LOG_TAG, "onTick " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(5000);
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        startTimer();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        description.setMaxLines(isChecked ? 200 : 3);
    }
}


