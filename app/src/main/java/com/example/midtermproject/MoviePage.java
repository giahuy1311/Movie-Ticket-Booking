package com.example.midtermproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.midtermproject.adapter.DateAdapter;
import com.example.midtermproject.adapter.TheaterAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoviePage extends AppCompatActivity implements TheaterAdapter.OnTimeClickListener{
    private TextView movieNameText;
    private ImageView movieImageView;
    private TextView movieDescriptionText;
    private TextView movieRatingText, movieDurationText, movieGenreText;
    Button watchTrailerButton;
    private RecyclerView dateRecyclerView;
    private RecyclerView theaterRecyclerView;
    private RecyclerView timeRecyclerView;
    private List<Showtime> showtimeList;
    List<Showtime> showtimeOnSelectedDate;
    private Movie movie;
    private FloatingActionButton fabButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        movieNameText = findViewById(R.id.movieTitleTextView);
        movieImageView = findViewById(R.id.imageView);
        movieDescriptionText = findViewById(R.id.movieSypnosisTextView);
        movieRatingText = findViewById(R.id.movieRatingTextView);
        movieDurationText = findViewById(R.id.durationTextView);
        movieGenreText = findViewById(R.id.movieGenreTextView);
        watchTrailerButton = findViewById(R.id.trailerBtn);

        Intent intent = getIntent();
        movie = new Movie();
        if (intent != null) {
             movie.setTitle(intent.getStringExtra("movieTitle"));
             movie.setPosterPath(intent.getStringExtra("movieImage"));
             movie.setOverview(intent.getStringExtra("movieDescription"));
             movie.setRating(intent.getStringExtra("movieRating"));
             movie.setDuration(intent.getStringExtra("movieDuration"));
             movie.setGenre(intent.getStringExtra("movieGenre"));
             movie.setTrailer(intent.getStringExtra("movieTrailer"));

            Picasso.get().load(movie.getPoster()).into(movieImageView);
            movieNameText.setText(movie.getTitle());
            movieDescriptionText.setText(movie.getSypnosis());
            movieRatingText.setText(movie.getRating());
            movieDurationText.setText(movie.getDuration());
            movieGenreText.setText(movie.getGenre());
        }

        // back to previous page
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fabButton = (FloatingActionButton) findViewById(R.id.fabButton);
        watchTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TrailerActivity.class);
                Log.d("MoviePage", "onClick: " + movie.getTrailer());
                intent.putExtra("movieTrailer", movie.getTrailer());
                startActivity(intent);
            }
        });
        loadShowtimeData();

    }

    private void loadShowtimeData() {
        showtimeList = new ArrayList<>();
        String movieName = movie.getTitle();
        DatabaseReference showtimesRef = FirebaseDatabase.getInstance().getReference("showtime");
        showtimesRef.child(movieName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Iterate through showtimes and display them
                for (DataSnapshot showtimeSnapshot : dataSnapshot.getChildren()) {
                    Showtime showtime = showtimeSnapshot.getValue(Showtime.class);
                    showtime.setShowtimeId(showtimeSnapshot.getKey());
                    // Display or process the showtime information
                    showtimeList.add(showtime);
                    Log.d("Showtime", "onDataChange: " + showtime.getTheaterName());
                    setupDateAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Showtime", "onCancelled: " + databaseError.getMessage());
                // Handle error
            }
        });
    }

    private void setupDateAdapter() {
        List<String> dateList = new ArrayList<>();
        List<String> dayOfWeekList = new ArrayList<>();
        // Collect all dates from showtime into dateList
        for (Showtime showtime : showtimeList) {
            String date = showtime.getDate();
            String dayOfWeek = showtime.getDayOfWeek();
            if (!dateList.contains(date)) {
                dateList.add(date);
                dayOfWeekList.add(dayOfWeek);
            }
        }

        // Create and set the DateAdapter
        dateRecyclerView = findViewById(R.id.recyclerView_date);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dateRecyclerView.setLayoutManager(layoutManager);
        DateAdapter dateAdapter = new DateAdapter(dayOfWeekList,dateList);
        dateRecyclerView.setAdapter(dateAdapter);
        dateAdapter.setOnDateClickListener(new DateAdapter.OnDateClickListener() {
            @Override
            public void onDateClick(String selectedDate) {

                Log.d("MoviePage", "onDateClick: " + selectedDate);
                setupTheaterAdapter(selectedDate);
            }
        });
    }

    private void setupTheaterAdapter(String selectedDate) {
        showtimeOnSelectedDate = new ArrayList<>();
        // Collect all theaters from showtime into theaterList
        for (Showtime showtime : showtimeList) {
            String date = showtime.getDate();
            if (date.equals(selectedDate)) {
                showtimeOnSelectedDate.add(showtime);

            }

        }
        Log.d("MoviePage", "setupTheaterAdapter: " + showtimeOnSelectedDate.size());
        theaterRecyclerView = findViewById(R.id.recyclerView_theater);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        theaterRecyclerView.setLayoutManager(layoutManager);

        TheaterAdapter theaterAdapter = new TheaterAdapter(showtimeOnSelectedDate);
        theaterAdapter.setOnTimeClickListener(this);
        theaterRecyclerView.setAdapter(theaterAdapter);
        theaterAdapter.notifyDataSetChanged();

    }
    @Override
    public void onTimeClick(TimeSlot timeSlot, int showListIndex) {
        // Handle the time slot click event here
        Log.d("MoviePage", "onTimeClick: " + timeSlot.getTime());
        Showtime showtimeSelected = showtimeOnSelectedDate.get(showListIndex);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SeatBookingActivity.class);
                intent.putExtra("activity", "MoviePage");
                intent.putExtra("theaterName", showtimeSelected.getTheaterName());
                intent.putExtra("movie", (Serializable) movie);
                intent.putExtra("showtime", (Serializable) showtimeSelected);
                intent.putExtra("time", timeSlot.getTime());
                startActivity(intent);
            }
        });

    }

}