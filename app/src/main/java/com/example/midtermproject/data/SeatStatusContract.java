package com.example.midtermproject.data;

import android.provider.BaseColumns;

public class SeatStatusContract {

    public static final class SeatStatusEntry implements BaseColumns {
        public static final String TABLE_NAME = "seat_status";
        public static final String COLUMN_SHOWTIME_ID = "showtime_id";
        public static final String COLUMN_SEAT_NUMBER = "seat_number";
        public static final String COLUMN_STATUS = "status"; // selected, available, booked

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SHOWTIME_ID + " INTEGER NOT NULL, " +
                COLUMN_SEAT_NUMBER + " INTEGER NOT NULL, " +
                COLUMN_STATUS + " TEXT NOT NULL);";
        // Thêm các cột khác tùy thuộc vào yêu cầu của bạn
    }
}