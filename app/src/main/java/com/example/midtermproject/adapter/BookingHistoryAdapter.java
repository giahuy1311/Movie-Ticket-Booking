package com.example.midtermproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.Booking;
import com.example.midtermproject.R;
import com.example.midtermproject.Seat;
import com.example.midtermproject.SeatBookingActivity;
import com.google.gson.Gson;


import java.io.Serializable;
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
        Log.d("Booking", booking.isPaid() + "");
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
        holder.theaterTextView.setText("Theater: " + booking.getTheaterName());
        holder.seatsInfoTextView.setText("Seats booked: "+ seatsInfo);
        holder.bookTimeTextView.setText("Time booked: "+booking.getTimeBooked());
        holder.totalPriceTextView.setText("Total: $"+totalPrice);

        if (booking.isPaid()) {
            holder.payButton.setText("Paid");
        }
        // Implement button click listener
        holder.payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!booking.isPaid()) {
                    booking.setPaid();
                    showPurchaseDialog(booking);
                } else {
                    Toast.makeText(context, "You have already paid for this booking", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.adjustButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SeatBookingActivity.class);
                intent.putExtra("activity","bookingHistory");
                intent.putExtra("booking",(Serializable) booking);
                context.startActivity(intent);
            }
        });
    }

    private void showPurchaseDialog(Booking booking) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String totalPrice = Double.toString(booking.getTotalPrice());
        builder.setTitle("Purchase information");
        builder.setMessage("Your total is $" + totalPrice + ". Do you want to pay now?");

        // Add positive button (Yes action)
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the booking action
                booking.setPaid();
                booking.updatePurchase();
                Toast.makeText(context, "Thank you! Wish you have a great time!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                // restart activity
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Pay later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the booking action
                dialog.dismiss();
            }
        });

        // Show the dialog
        builder.show();
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView movieNameTextView, dateTextView, timeTextView,theaterTextView, seatsInfoTextView, bookTimeTextView, totalPriceTextView;
        Button payButton, adjustButton;


        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            movieNameTextView = itemView.findViewById(R.id.movie_name);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            seatsInfoTextView = itemView.findViewById(R.id.seatsInfo);
            bookTimeTextView = itemView.findViewById(R.id.bookTime);
            totalPriceTextView = itemView.findViewById(R.id.totalPrice);
            theaterTextView = itemView.findViewById(R.id.theaterTextView);
            payButton = itemView.findViewById(R.id.payBtn);
            adjustButton = itemView.findViewById(R.id.adjustBtn);
        }
    }
}
