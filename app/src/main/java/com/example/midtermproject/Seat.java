package com.example.midtermproject;

public class Seat implements java.io.Serializable{
    private String seatId;
    private boolean isBooked;
    private boolean isSelected;
    private Double price;

    public Seat() {
    }

    public Seat(String seatId, boolean isBooked, Double price) {
        this.seatId = seatId;
        this.isBooked = isBooked;
        this.price = price;
    }

    public String getSeatId() {
        return seatId;
    }
    public double getPrice() {
        return price;
    }
    public boolean getIsBooked() {
        return isBooked;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setIsBooked() {
        this.isBooked = true;
    }
    public void setUnBooked() {
        this.isBooked = false;
    }
    public void setUnSelected() {
        this.isSelected = false;
    }
    public boolean isBooked() {
        return isBooked;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setIsSelected() {
        this.isSelected = true;
    }

}
