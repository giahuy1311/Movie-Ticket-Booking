package com.example.midtermproject.data;

import android.provider.BaseColumns;

public class BookingHistoryContract {

    public static final class BookingHistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "booking_history";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_SHOWTIME_ID = "showtime_id";
        public static final String COLUMN_SEAT_ID = "seat_id";
        public static final String COLUMN_STATUS = "status"; // confirmed, pending, canceled
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER NOT NULL, " +
                COLUMN_SHOWTIME_ID + " INTEGER NOT NULL, " +
                COLUMN_SEAT_ID + " INTEGER NOT NULL, " +
                COLUMN_STATUS + " TEXT NOT NULL);";
        // Thêm các cột khác tùy thuộc vào yêu cầu của bạn
    }
}