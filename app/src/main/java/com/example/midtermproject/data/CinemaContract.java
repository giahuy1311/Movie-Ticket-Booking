package com.example.midtermproject.data;

import android.provider.BaseColumns;

public class CinemaContract {

    public static final class CinemaEntry implements BaseColumns {
        public static final String TABLE_NAME = "cinemas";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL);";
        // Thêm các cột khác tùy thuộc vào yêu cầu của bạn
    }
}