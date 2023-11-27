package com.example.midtermproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MoviePage extends AppCompatActivity {
    private TextView movieNameText;
    private ImageView movieImageView;
    private TextView movieDescriptionText;
    private TextView movieRatingText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        movieNameText = findViewById(R.id.movieTitleTextView);
        movieImageView = findViewById(R.id.imageView);
        movieDescriptionText = findViewById(R.id.movieSypnosisTextView);
        movieRatingText = findViewById(R.id.movieRatingTextView);

        Intent intent = getIntent();
        if (intent != null) {
            String movieName = intent.getStringExtra("movieTitle");
            String movieImage = intent.getStringExtra("movieImage");
            String movieDescription = intent.getStringExtra("movieDescription");
            String movieRating = intent.getStringExtra("movieRating");

            Log.d("MoviePage", "onCreate: " + movieName);
            Picasso.get().load(movieImage).into(movieImageView);
            movieNameText.setText(movieName);
            movieDescriptionText.setText(movieDescription);
            movieRatingText.setText(movieRating);
        }

        // back to previous page
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}