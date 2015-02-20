package com.example.sasha.ivalik.registration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.sasha.ivalik.MainActivity;
import com.example.sasha.ivalik.R;
import com.example.sasha.ivalik.database.HelperFactory;
import com.example.sasha.ivalik.models.CustomExercise;
import com.example.sasha.ivalik.models.Exercise;
import com.example.sasha.ivalik.models.Training;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sasha on 2/10/15.
 */
public class RegistrationActivity extends ActionBarActivity implements View.OnClickListener {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ImageButton next, prev;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_slide_activity);
        fragments.add(new AnketaFragment());
        fragments.add(new MapFragment());
        fragments.add(new TrainerTimeManegerFragment());
        // Instantiate a ViewPager and a PagerAdapter.
        next= (ImageButton)findViewById(R.id.imageButton2);
        prev = (ImageButton)findViewById(R.id.imageButton3);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.global, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.show_tables:
                try {
                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getExerciseDAO().getAllExercises().toString());
                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getCustomExerciseDAO().getAllCustomExercises().toString());
                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getTrainingDAO().getAllTrainings().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.clear_tables:
                try {
                    TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), CustomExercise.class);
                    TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), Exercise.class);
                    TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), Training.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton2:

                if (mPager.getCurrentItem()<fragments.size()-1)mPager.setCurrentItem(mPager.getCurrentItem()+1,true);
                break;
            case R.id.imageButton3:
                if (mPager.getCurrentItem()>0)mPager.setCurrentItem(mPager.getCurrentItem()-1,true);
                break;
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            Log.i("", "" + hourOfDay + ":" + minute);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int mouth = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_WEEK);

            // Create a new instance of TimePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, mouth,
                    day);
        }


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Log.i("", "" + year + ":" + monthOfYear + " " + dayOfMonth);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}