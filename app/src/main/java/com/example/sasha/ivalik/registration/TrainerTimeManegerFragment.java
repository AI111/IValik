package com.example.sasha.ivalik.registration;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sasha.ivalik.MainActivity;
import com.example.sasha.ivalik.R;
import com.example.sasha.ivalik.models.TrainingDay;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sasha on 2/10/15.
 */
public class TrainerTimeManegerFragment extends Fragment implements View.OnClickListener {
    public ArrayList<TrainingDay> days;//= new ArrayList<>();
    ImageButton addBtn;
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();
    TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(android.widget.TimePicker view,
                                      int hourOfDay, int minute) {
                    Log.i("", "" + hourOfDay + ":" + minute);
                    days.add(new TrainingDay(hourOfDay, minute));
                    Log.d(MainActivity.LOG_TAG, "ADD TRAINING " + days.size());
                    mAdapter.notifyDataSetChanged();
                }
            };
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_maneger_fragment, container, false);
        addBtn = (ImageButton) rootView.findViewById(R.id.button3);
        addBtn.setOnClickListener(this);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
//        mRecyclerView.setItemViewCacheSize(6);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);
//        // specify an adapter (see also next example)
        days = new ArrayList<>();
        days.add(new TrainingDay(15, 20));

        mAdapter = new MyAdapter(days);
        // mAdapter.setOnItemClickListener(this);
//
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Log.d(MainActivity.LOG_TAG, "onClick " + days.size());
        DialogFragment newFragment = new TimePickerFragment(mTimeSetListener);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showMenu(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);
        animate.setDuration(300);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }

    public void hideMenu(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);
        animate.setDuration(300);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        ArrayList<TrainingDay> trainind;
        OnItemClicklistener onItemClicklistener;

        MyAdapter(ArrayList<TrainingDay> citys) {
            this.trainind = citys;
        }

        public void setOnItemClickListener(OnItemClicklistener onItemClicklistener) {
            this.onItemClicklistener = onItemClicklistener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_selection_item, viewGroup, false);
            return new MyViewHolder(v, onItemClicklistener);
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {
            viewHolder.time.setText(trainind.get(i).hour + ":" + trainind.get(i).minute);


        }


        @Override
        public int getItemCount() {
            return trainind.size();
        }


    }

    //
    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        OnItemClicklistener onItemClicklistener;
        View itemView;
        private TextView time;
        private ToggleButton toggleButton;
        private LinearLayout layout;
        public MyViewHolder(View itemView, OnItemClicklistener onItemClicklistener) {
            super(itemView);
            this.itemView = itemView;
            this.onItemClicklistener = onItemClicklistener;
            time = (TextView) itemView.findViewById(R.id.textView4);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.UP_DOWN);
            layout = (LinearLayout) itemView.findViewById(R.id.Calendar_feeld);
            toggleButton.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View view) {
            // if(mItemClickListener!=null)

            // if (onItemClicklistener != null)
            onItemClicklistener.onClickItem(itemView, view, getPosition());

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            layout.setVisibility(isChecked ? View.VISIBLE : View.GONE);

        }
    }
}
