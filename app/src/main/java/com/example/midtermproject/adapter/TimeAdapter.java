package com.example.midtermproject.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.R;
import com.example.midtermproject.Showtime;
import com.example.midtermproject.Theater;
import com.example.midtermproject.TimeSlot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder>{
    private List<TimeSlot> timeSlotList;
    private TheaterAdapter theaterAdapter;
    private OnTimeClickListener onTimeClickListener;
    private List<Showtime> showtimeList;
    private int showListIndex;

    public TimeAdapter(List<TimeSlot> timeSlotList,TheaterAdapter theaterAdapter, List<Showtime> showtimeList,int showListIndex) {
        this.timeSlotList = timeSlotList;
        this.theaterAdapter = theaterAdapter;
        this.showtimeList = showtimeList;
        this.showListIndex = showListIndex;
    }

    public void setOnTimeClickListener(OnTimeClickListener listener) {
        this.onTimeClickListener = listener;
    }
    public interface OnTimeClickListener {
        void onTimeClick(TimeSlot timeSlot, int showListIndex);
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        TimeSlot timeSlot = timeSlotList.get(position);
        holder.bind(timeSlot);
        if (!timeSlot.isSelected())  {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < showtimeList.size(); i++) {
                    if (i != showListIndex) {
                        theaterAdapter.notifyItemChanged(i);
                    }
                }

                for (TimeSlot timeSlot : timeSlotList) {
                    timeSlot.setUnSelected();
                }
                timeSlotList.get(position).setSelected();
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));
                notifyDataSetChanged();
                if (onTimeClickListener != null) {
                    onTimeClickListener.onTimeClick(timeSlot, showListIndex);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }



    public class TimeViewHolder extends RecyclerView.ViewHolder {
        private TextView timeTextView;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }

        public void bind(TimeSlot timeSlot) {
            timeTextView.setText(timeSlot.getTime());
        }
    }
}
