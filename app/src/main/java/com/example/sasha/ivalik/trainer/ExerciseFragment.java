package com.example.sasha.ivalik.trainer;

import android.media.MediaPlayer;
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
    private TextView description, title, approach, repeat, weight, time;
    private boolean timerRun;
    private int tmpApproach;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exercise = (CustomExercise) getArguments().getSerializable(TrainingListFragment.SER_KEY);
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
        approach.setText("подходы " + exercise.getApproach());

        repeat = (TextView) rootView.findViewById(R.id.textView13);
        tmpApproach = exercise.getApproach();
        repeat.setText("повторения " + exercise.getRepeat());
        time = (TextView) rootView.findViewById(R.id.textView14);
        toggleButton = (ToggleButton) rootView.findViewById(R.id.UP_DOWN);
        toggleButton.setOnCheckedChangeListener(this);
        progressBar.setOnClickListener(this);

        return rootView;
    }

    public void startTimer(final int tsec) {
        final float s = tsec / progressBar.getMax();
        timerRun = true;
        progressBar.setProgress(0);
        timer = new CountDownTimer(tsec, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) ((tsec - millisUntilFinished) / s));
                time.setText(String.format("%4.2f", (float) (millisUntilFinished * 0.001f)));
                Log.d(MainActivity.LOG_TAG, "onTick " + s + "   " + millisUntilFinished + " " + ((tsec - millisUntilFinished) / s));
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(progressBar.getMax());
                time.setText("START");
                timerRun = false;
                tmpApproach--;
                approach.setText(getText(R.string.repeat) + " " + tmpApproach);
                if (tmpApproach == exercise.getApproach() / 2) {
                    if (exercise.getExercise().getSoundId() != 0) {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), exercise.getExercise().getSoundId());
                        mediaPlayer.start();
                    }
                } else if (tmpApproach == 0) {
                    exercise.setFinished(true);
                }
            }
        }.start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void onClick(View v) {
        if (!timerRun)
            startTimer(6000);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        description.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }
}


