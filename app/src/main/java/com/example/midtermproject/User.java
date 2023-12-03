package com.example.midtermproject;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String email;
    private List<Booking> bookingList;


    public User(String name, String email, List<Booking> bookingList) {
        this.name = name;
        this.email = email;
        this.bookingList = bookingList;
    }
    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public List<Booking> getBookingList() {
        return this.bookingList;
    }
}
