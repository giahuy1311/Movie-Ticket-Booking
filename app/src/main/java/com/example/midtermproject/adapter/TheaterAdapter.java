package com.example.midtermproject.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.R;
import com.example.midtermproject.Showtime;
import com.example.midtermproject.Theater;
import com.example.midtermproject.TimeSlot;


import java.util.ArrayList;
import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> implements TimeAdapter.OnTimeClickListener {

    private List<Showtime> showtimeList;
    public interface OnTimeClickListener {
        void onTimeClick(TimeSlot timeSlot, int showListIndex);
    }

    private OnTimeClickListener onTimeClickListener;

    public TheaterAdapter(List<Showtime> showtimeList) {
        this.showtimeList = showtimeList;
    }

    public void setOnTimeClickListener(OnTimeClickListener listener) {
        this.onTimeClickListener = listener;
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.theater_item, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        Showtime showtime = showtimeList.get(position);

        Theater theater = new Theater();
        theater = showtime.getTheater();
        TextView theaterNameTextView = holder.theaterNameTextView;

        // Truy cập trường name của đối tượng Theater
        String theaterName = theater.getName();

        theaterNameTextView.setText(theaterName);
        List<TimeSlot> timeSlots =  new ArrayList<>();

        int showListIndex = 0;
        for (Showtime showtime1 : showtimeList) {
            Theater theater1 = showtime1.getTheater();
            if (theater1.name.equals(theaterName)) {
                String timeList = showtime1.getTime();
                String[] timeArray = timeList.split(" ");
                for (String time : timeArray) {
                    TimeSlot timeSlot = new TimeSlot(time);
                    timeSlots.add(timeSlot);
                }
                showListIndex = showtimeList.indexOf(showtime1);
            }
        }

        TimeAdapter timeAdapter = new TimeAdapter(timeSlots,this,showtimeList, showListIndex);
        timeAdapter.setOnTimeClickListener(this);
        holder.timeRecyclerView.setAdapter(timeAdapter);

        holder.showMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TheaterAdapter", "onClick: " + theaterName);
                String location = theaterName;
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + location);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                if (mapIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(mapIntent);
                }
            }
        });
    }
    @Override
    public void onTimeClick(TimeSlot timeSlot, int showListIndex) {
        if (onTimeClickListener != null) {
            onTimeClickListener.onTimeClick(timeSlot, showListIndex);
        }
        Log.d("TheaterAdapter", "onTimeClick: " + timeSlot.getTime() + " " + showListIndex);

    }

    @Override
    public int getItemCount() {
        return showtimeList.size();
    }

    // setup view holder
    public class TheaterViewHolder extends RecyclerView.ViewHolder {
        private TextView theaterNameTextView;
        private TextView showMapTextView;
        private RecyclerView timeRecyclerView;


        public TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            theaterNameTextView = itemView.findViewById(R.id.theaterNameTextView);
            timeRecyclerView = itemView.findViewById(R.id.recyclerView_time);
            showMapTextView = itemView.findViewById(R.id.showMap);

            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            timeRecyclerView.setLayoutManager(layoutManager);
        }
    }

}






