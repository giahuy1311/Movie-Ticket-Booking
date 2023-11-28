package com.example.midtermproject;

public class TimeSlot {
    private String time;
    private boolean isSelected;
    private boolean isAvailable;

    public TimeSlot() {
    }
    public TimeSlot(String time) {
        this.time = time;
        this.isSelected = false;
        this.isAvailable = true;
    }
    public String getTime() {
        return time;
    }
    public void setUnSelected() {
        this.isSelected = false;
    }
    public void setSelected() {
        this.isSelected = true;
    }
    public boolean isSelected() {
        return isSelected;
    }
}
