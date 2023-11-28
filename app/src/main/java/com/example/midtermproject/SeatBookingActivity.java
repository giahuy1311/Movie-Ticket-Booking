package com.example.midtermproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SeatBookingActivity extends AppCompatActivity {
    private Movie movie;
    private Showtime showtime;
    private String theaterName;
    private String time;
    private TextView movieNameTextView;
    private TextView theaterNameTextView;
    private TextView timeTextView;
    private TextView dayTextView;
    private ImageView movieImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_booking);

        movieNameTextView = findViewById(R.id.movieTitleTextView);
        theaterNameTextView = findViewById(R.id.theaterTextView);
        timeTextView = findViewById(R.id.timeTextView);
        dayTextView = findViewById(R.id.dayTextView);
        movieImageView = findViewById(R.id.imageView);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        showtime = (Showtime) getIntent().getSerializableExtra("showtime");
        theaterName = getIntent().getStringExtra("theaterName");
        time = getIntent().getStringExtra("time");

        movieNameTextView.setText(movie.getTitle());
        theaterNameTextView.setText(theaterName);
        timeTextView.setText(time);
        dayTextView.setText(showtime.getDate()+", "+showtime.getDayOfWeek());
        Picasso.get().load(movie.getPoster()).into(movieImageView);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}