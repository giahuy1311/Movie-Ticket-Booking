package com.example.midtermproject;

import java.sql.Time;

public class Showtime implements java.io.Serializable{ // lịch chiếu
    private String theaterName;
    private String movieName;
    private String time;

    public String date; // ngày chiếu
    public String day; // thứ trong tuần


    public Showtime() {
    }
    public Showtime(String theaterName, String movieName, String time, String date, String dayOfWeek) {
        this.theaterName = theaterName;
        this.movieName = movieName;
        this.time = time;
        this.date = date;
        this.day = dayOfWeek;
    }

    public String getDate() {
        return date;
    }
    public String getDayOfWeek() {
        return day;
    }
    public String getTheaterName() {
        return theaterName;
    }
    public String getMovieName() {
        return movieName;
    }
    public String getTime() {
        return time;
    }
    public Theater getTheater() {
        return new Theater(theaterName);
    }
    public TimeSlot getTimeSlot() {
        return new TimeSlot(time);
    }

}
