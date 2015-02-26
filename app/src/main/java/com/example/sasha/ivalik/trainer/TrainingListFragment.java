package com.example.sasha.ivalik.trainer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sasha.ivalik.MainActivity;
import com.example.sasha.ivalik.R;
import com.example.sasha.ivalik.database.HelperFactory;
import com.example.sasha.ivalik.models.CustomExercise;
import com.example.sasha.ivalik.models.Training;
import com.example.sasha.ivalik.registration.OnItemClicklistener;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sasha on 2/10/15.
 */
public class TrainingListFragment extends Fragment implements View.OnClickListener {
    public final static String SER_KEY = "com.example.sasha.ivalik.ser";
    public ArrayList<CustomExercise> exercises;//= new ArrayList<>();
    ImageButton addBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    //  ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.training_list_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
//        mRecyclerView.setItemViewCacheSize(6);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);
        try {
            Training training = HelperFactory.getHelper().getTrainingDAO().queryForId(1);
            exercises = new ArrayList<>();
            exercises.addAll(training.getExercises());
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        // specify an adapter (see also next example)
        // listView = (ListView) rootView.findViewById(R.id.list_view);
//        exercises = new ArrayList<>();
//        try {
//            exercises.add(new CustomExercise(
//                    HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.pull_ups_on_the_bar)
//                    , (byte) 10, (byte) 10, (byte) 80, false));
//            exercises.add(new CustomExercise(
//                    HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.dips)
//                    , (byte) 10, (byte) 10, (byte) 80, false));
//            exercises.add(new CustomExercise(
//                    HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.bench_press)
//                    , (byte) 6, (byte) 4, (byte) 80, false));
//            exercises.add(new CustomExercise(
//                    HelperFactory.getHelper().getExerciseDAO().queryForId(R.string.lifting_dumbbells_for_biceps_sitting)
//                    , (byte) 6, (byte) 1, (byte) 80, false));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        //  listView
        mAdapter = new MyAdapter(exercises);
        // mAdapter.setOnItemClickListener(this);
//
        mAdapter.setOnItemClickListener(new OnItemClicklistener() {
            @Override
            public void onClickItem(View rootView, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(SER_KEY, exercises.get(position));
                ExerciseFragment fragment = new ExerciseFragment();
                fragment.setArguments(bundle);
                Log.v(MainActivity.LOG_TAG, "OnItemClick  " + position);
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Log.d(MainActivity.LOG_TAG, "onClick " + exercises.size());

    }


    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        ArrayList<CustomExercise> exercises;
        OnItemClicklistener onItemClicklistener;

        MyAdapter(ArrayList<CustomExercise> exercises) {
            this.exercises = exercises;
        }

        public void setOnItemClickListener(OnItemClicklistener onItemClicklistener) {
            this.onItemClicklistener = onItemClicklistener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.training_list_item, viewGroup, false);
            return new MyViewHolder(v, onItemClicklistener);
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {
            viewHolder.number.setText((i + 1) + "");
            viewHolder.title.setText(exercises.get(i).getExercise().getNameId());
            viewHolder.approach.setText(exercises.get(i).getApproach() + "");
            viewHolder.repeat.setText(exercises.get(i).getRepeat() + "");
            viewHolder.weight.setText((exercises.get(i).getPercentPM() / 100 * exercises.get(i).getExercise().getpM()) + "");

        }


        @Override
        public int getItemCount() {
            return exercises.size();
        }


    }

    //
    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        OnItemClicklistener onItemClicklistener;
        View itemView;
        TextView title, approach, repeat, weight, number;
        private TextView time;
        private ToggleButton toggleButton;
        private LinearLayout layout;

        public MyViewHolder(View itemView, OnItemClicklistener onItemClicklistener) {
            super(itemView);
            this.itemView = itemView;
            this.onItemClicklistener = onItemClicklistener;
            time = (TextView) itemView.findViewById(R.id.textView4);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.UP_DOWN);
            toggleButton.setOnCheckedChangeListener(this);
            title = (TextView) itemView.findViewById(R.id.textView4);
            number = (TextView) itemView.findViewById(R.id.textView5);
            layout = (LinearLayout) itemView.findViewById(R.id.Calendar_feeld);
            title.setOnClickListener(this);
            approach = (TextView) itemView.findViewById(R.id.textView7);
            repeat = (TextView) itemView.findViewById(R.id.textView9);
            weight = (TextView) itemView.findViewById(R.id.textView11);
        }

        @Override
        public void onClick(View view) {
            if (onItemClicklistener != null)
                onItemClicklistener.onClickItem(itemView, view, getPosition());

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            layout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        }
    }
}
