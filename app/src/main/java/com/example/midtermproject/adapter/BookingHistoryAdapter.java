package com.example.midtermproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.Booking;
import com.example.midtermproject.R;
import com.example.midtermproject.Seat;


import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private Context context;

    public BookingHistoryAdapter(List<Booking> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        List<Seat> seatList = booking.getSeatList();
        String seatsInfo = "";
        for (int i = 0; i < seatList.size(); i++) {
            seatsInfo += seatList.get(i).getSeatId();
            if (i != seatList.size() - 1) {
                seatsInfo += ", ";
            }
        }

        String totalPrice = Double.toString(booking.getTotalPrice());
        // Set data to views
        holder.movieNameTextView.setText(booking.getMovie().getTitle());
        holder.dateTextView.setText("Date: " + booking.getDate());
        holder.timeTextView.setText("Time: " + booking.getTime());
        holder.seatsInfoTextView.setText("Seats booked: "+ seatsInfo);
        holder.bookTimeTextView.setText("Time booked:"+booking.getTimeBooked());
        holder.totalPriceTextView.setText("Total: $"+totalPrice);

        // Implement button click listener
        holder.payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click, e.g., show payment screen
                // You can use movieItem to get additional information about the clicked item
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView movieNameTextView, dateTextView, timeTextView, seatsInfoTextView, bookTimeTextView, totalPriceTextView;
        Button payButton;


        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            movieNameTextView = itemView.findViewById(R.id.movie_name);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            seatsInfoTextView = itemView.findViewById(R.id.seatsInfo);
            bookTimeTextView = itemView.findViewById(R.id.bookTime);
            totalPriceTextView = itemView.findViewById(R.id.totalPrice);
            payButton = itemView.findViewById(R.id.payBtn);

        }
    }
}
