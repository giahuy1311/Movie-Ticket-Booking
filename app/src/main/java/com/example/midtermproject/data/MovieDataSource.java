package com.example.midtermproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MovieDataSource {

    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    public MovieDataSource(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Thêm một bộ phim vào cơ sở dữ liệu
    public long addMovie(String title, String releaseDate) {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, title);
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDate);

        // Thêm dữ liệu vào bảng Movies
        return database.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
    }

    // Lấy danh sách tất cả các bộ phim
    public Cursor getAllMovies() {
        String[] projection = {
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.COLUMN_TITLE,
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE
        };

        return database.query(
                MovieContract.MovieEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }
}
