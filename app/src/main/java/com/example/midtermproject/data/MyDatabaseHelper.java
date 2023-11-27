package com.example.midtermproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo các bảng khi cơ sở dữ liệu được tạo lần đầu tiên
        db.execSQL(MovieContract.MovieEntry.CREATE_TABLE);
        db.execSQL(CinemaContract.CinemaEntry.CREATE_TABLE);
        db.execSQL(ShowtimeContract.ShowtimeEntry.CREATE_TABLE);
        db.execSQL(SeatStatusContract.SeatStatusEntry.CREATE_TABLE);
        db.execSQL(BookingHistoryContract.BookingHistoryEntry.CREATE_TABLE);
        db.execSQL(UserContract.UserEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Các bước thực hiện khi có bản nâng cấp cơ sở dữ liệu
        // Ví dụ: db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        //       onCreate(db);
    }
}
