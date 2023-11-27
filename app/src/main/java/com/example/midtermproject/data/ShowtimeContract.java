package com.example.midtermproject.data;

import android.provider.BaseColumns;

public class ShowtimeContract {

    public static final class ShowtimeEntry implements BaseColumns {
        public static final String TABLE_NAME = "showtimes";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_CINEMA_ID = "cinema_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_STATUS = "status"; // selected, available, unavailable

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                COLUMN_CINEMA_ID + " INTEGER NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_TIME + " TEXT NOT NULL, " +
                COLUMN_STATUS + " TEXT NOT NULL);";
        // Thêm các cột khác tùy thuộc vào yêu cầu của bạn
    }
}