package com.example.midtermproject;

import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Theater {
    public String name;
    public String seat;
    public String seatPrice;

    public Theater() {
    }

    public Theater(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getSeatList() {
        return seat;
    }
}
