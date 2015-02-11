package com.example.sasha.ivalik.registration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sasha.ivalik.R;

/**
 * Created by sasha on 2/10/15.
 */
public class TrainerTimeManegerFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.time_maneger_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
//        mRecyclerView.setItemViewCacheSize(6);
        mLayoutManager= new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            mRecyclerView.setItemAnimator(itemAnimator);
//        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(guides);
//        mAdapter.setOnItemClickListener(this);
//
//        mRecyclerView.setAdapter(mAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
//    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
//        ArrayList<CityGuide> citys;
//        OnItemClicklistener onItemClicklistener;
//
//        MyAdapter(ArrayList<CityGuide> citys) {
//            this.citys = citys;
//        }
//
//        public void setOnItemClickListener(OnItemClicklistener onItemClicklistener) {
//            this.onItemClicklistener = onItemClicklistener;
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
//            return new MyViewHolder(v, onItemClicklistener);
//        }
//
//        @Override
//        public void onBindViewHolder(MyViewHolder viewHolder, int i) {
//
//
//        }
//
//
//
//        @Override
//        public int getItemCount() {
//            return citys.size();
//        }
//
//    }
////
//    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        OnItemClicklistener onItemClicklistener;
//        View itemView;
//
//
//        public MyViewHolder(View itemView, OnItemClicklistener onItemClicklistener) {
//            super(itemView);
//            this.itemView = itemView;
//
//            this.onItemClicklistener = onItemClicklistener;
//
//
//        }
//
//        @Override
//        public void onClick(View view) {
//            // if(mItemClickListener!=null)
//
//            // if (onItemClicklistener != null)
//            onItemClicklistener.onClickItem(itemView, view, getPosition());
//
//        }
//    }
}
