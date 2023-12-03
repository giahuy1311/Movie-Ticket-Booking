package com.example.midtermproject;

import java.io.Serializable;
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

    public Booking() {
    }
    public Booking(List<Seat> seatList, Movie movie, String theaterName, String time,String date, double totalPrice, String timeBooked) {
        this.seatList = seatList;
        this.movie = movie;
        this.theaterName = theaterName;
        this.time = time;
        this.date = date;
        this.totalPrice = totalPrice;
        this.timeBooked = timeBooked;
        this.bookingId = UUID.randomUUID().toString();
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
}
