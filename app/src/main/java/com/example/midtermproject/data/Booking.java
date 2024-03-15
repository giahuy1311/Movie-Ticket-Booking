package com.example.midtermproject;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Booking implements Serializable {
    private List<Seat> seatList;
    private Movie movie;
    private String theaterName;
    private String time;
    private String date;
    private double totalPrice;
    private String timeBooked;
    private String bookingId;

    private boolean paid ;
    private String showtimeId;
    public Booking() {
    }
    public Booking(List<Seat> seatList, Movie movie, String theaterName, String time, String date, double totalPrice, String timeBooked, String showtimeId) {
        this.seatList = seatList;
        this.movie = movie;
        this.theaterName = theaterName;
        this.time = time;
        this.date = date;
        this.totalPrice = totalPrice;
        this.timeBooked = timeBooked;
        this.bookingId = UUID.randomUUID().toString();
        this.paid = false;
        this.showtimeId = showtimeId;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }
    public Movie getMovie() {
        return movie;
    }
    public String getTheaterName() {
        return theaterName;
    }
    public String getTime() {
        return time;
    }
    public String getDate() {
        return date;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public String getTimeBooked() {
        return timeBooked;
    }
    public String getBookingId() {
        return bookingId;
    }
    public String getShowtimeId() {
        return showtimeId;
    }
    public boolean isPaid() {
        return paid;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setTimeBooked(String timeBooked) {
        this.timeBooked = timeBooked;
    }
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
    public void setPaid() {
        paid = true;
    }
    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }


    public void reverseSeatList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("showtime");
        // Get seatList from firebase, then remove seat in this booking from seatList and update seatList to firebase
        List<String> seatBooked = new ArrayList<>();
        reference.child(movie.getTitle()).child(showtimeId).child("bookedseat").child(time).child("seat").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String seatBookedString = task.getResult().getValue(String.class);
                Log.d("Booking", "reverseSeatList1: " + seatBookedString);
                String[] seatBookedArray = seatBookedString.split(" ");
                for (String seatId : seatBookedArray) {
                    seatBooked.add(seatId);
                }
                Log.d("Booking", "reverseSeatList2: " + seatBooked.size());
                for (Seat seat : seatList) {
                    seat.setUnBooked();
                    seat.setIsSelected();
                    seatBooked.remove(seat.getSeatId());
                }
                Log.d("Booking", "reverseSeatList3: " + seatBooked.size());
                String seatBookedString1 = "";
                for (String seatId : seatBooked) {
                    seatBookedString1 += seatId + " ";
                }
                Log.d("Booking", "reverseSeatList4: " + seatBookedString1);
                reference.child(movie.getTitle()).child(showtimeId).child("bookedseat").child(time).child("seat").setValue(seatBookedString1);
            }
        });





    }

    public void updatePurchase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("booking");
        reference.child(bookingId).child("paid").setValue(true);
    }
}
