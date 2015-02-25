package com.example.sasha.ivalik.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.sasha.ivalik.MainActivity;
import com.example.sasha.ivalik.R;
import com.example.sasha.ivalik.database.HelperFactory;
import com.example.sasha.ivalik.geolocation.SpyService;
import com.example.sasha.ivalik.geolocation.UserWayActivity;
import com.example.sasha.ivalik.models.CustomExercise;
import com.example.sasha.ivalik.models.Exercise;
import com.example.sasha.ivalik.models.GeoPoint;
import com.example.sasha.ivalik.models.Training;
import com.example.sasha.ivalik.models.User;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sasha on 2/10/15.
 */
public class RegistrationActivity extends ActionBarActivity implements View.OnClickListener {

    public static User user = new User();
    ArrayList<Fragment> fragments = new ArrayList<>();
    ImageButton next, prev;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_slide_activity);

        fragments.add(new TestFragment());
        fragments.add(new AnketaFragment());
        fragments.add(new MapaFragment());
        fragments.add(new TrainerTimeManegerFragment());
        fragments.add(new TrainingCustomizationFragment());
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
                    Log.d(MainActivity.LOG_TAG, HelperFactory.getHelper().getGeoPointDAO().getAllGeoPoints().toString());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.clear_tables:
                try {
                    TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), CustomExercise.class);
                    TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), Exercise.class);
                    TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), Training.class);
                    TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), GeoPoint.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.start_service:
                startService(new Intent(this, SpyService.class));
                return true;

            case R.id.stop_service:
                stopService(new Intent(this, SpyService.class));
                return true;
            case R.id.show_map:
                startActivity(new Intent(this, UserWayActivity.class));
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton2:
                if (mPager.getCurrentItem() < fragments.size() - 1) {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
                    prev.setVisibility(View.VISIBLE);
                    if (mPager.getCurrentItem() == fragments.size() - 1) {
                        next.setVisibility(View.GONE);
                    }

                }
                break;
            case R.id.imageButton3:
                if (mPager.getCurrentItem() > 0) {
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1, true);
                    next.setVisibility(View.VISIBLE);
                    if (mPager.getCurrentItem() == 0) {
                        prev.setVisibility(View.GONE);
                    }
                }

                break;
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