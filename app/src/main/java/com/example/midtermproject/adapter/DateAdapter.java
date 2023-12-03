package com.example.midtermproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.R;

import java.util.Date;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    private List<String> dateList;
    private List<String> dayOfWeekList;
    private OnDateClickListener onDateClickListener;

    public DateAdapter(List<String> dateList,List<String> dayOfWeekList) {
        this.dateList = dateList;
        this.dayOfWeekList = dayOfWeekList;
    }

    public void setOnDateClickListener(OnDateClickListener listener) {
        this.onDateClickListener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        String date = dateList.get(position);
        String dayOfWeek = dayOfWeekList.get(position);
        holder.bind(date,dayOfWeek);

    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public interface OnDateClickListener {
        void onDateClick(String selectedDate);
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        private TextView dayOfWeekTextView;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            dayOfWeekTextView = itemView.findViewById(R.id.dayTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDateClickListener != null) {

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            String selectedDate = dayOfWeekList.get(position);
                            onDateClickListener.onDateClick(selectedDate);
                        }
                    }
                }
            });
        }

        public void bind(String date,String dayOfWeek) {
            dateTextView.setText(date);
            dayOfWeekTextView.setText(dayOfWeek);
        }
    }
}
