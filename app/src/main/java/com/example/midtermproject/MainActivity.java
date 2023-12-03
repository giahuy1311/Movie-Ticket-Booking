package com.example.midtermproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.midtermproject.adapter.AllMovieAdapter;
import com.example.midtermproject.adapter.HighlightMovieAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private List<Movie> movieList;
    private TextView viewAllMovies;
    private RecyclerView highlightMovieRecyclerView;
    private RecyclerView allMovieRecyclerView;
    private ImageButton btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        highlightMovieRecyclerView = findViewById(R.id.recycler_highlights);
        allMovieRecyclerView = findViewById(R.id.recycler_all_movies);
        btnProfile = findViewById(R.id.user_profile);
        // Khởi tạo MovieAdapter

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        highlightMovieRecyclerView.setLayoutManager(layoutManager1);
        allMovieRecyclerView.setLayoutManager(layoutManager2);

        movieList = new ArrayList<>();
        HighlightMovieAdapter highlightMovieAdapter = new HighlightMovieAdapter(movieList);
        AllMovieAdapter allMovieAdapter = new AllMovieAdapter(movieList, 0);

        // Đặt adapter cho RecyclerView
        highlightMovieRecyclerView.setAdapter(highlightMovieAdapter);
        allMovieRecyclerView.setAdapter(allMovieAdapter);

        viewAllMovies = findViewById(R.id.view_all_text_view);
        viewAllMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllMoviesActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        // load movie data from firebase
        loadMovieData();
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.menu_item);
        popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.signOut) {
                        // Sign out
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else if (item.getItemId() == R.id.bookingHistory) {
                        // Go to booking history
                        List<Booking> bookingList = new ArrayList<>();
                        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("booking");
                        connectedRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        Booking booking = dataSnapshot.getValue(Booking.class);
                                        bookingList.add(booking);
                                    }
                                    Intent intent = new Intent(MainActivity.this, BookingHistoryActivity.class);
                                    String json = new Gson().toJson(bookingList);
                                    intent.putExtra("bookingList", json);
                                    startActivity(intent);
                                } else {
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.d("FirebaseConnection", "Listener was cancelled");
                            }
                        });

                    }
                    return false;
                });
        popupMenu.show();
    }

    private void loadMovieData() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("movie");
        databaseReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("FetchMovieDataTask", "Movie list size: " + task.getResult().getChildrenCount());
                List<Movie> movieList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Movie movie = dataSnapshot.getValue(Movie.class);

                    Log.d("FetchMovieDataTask", "Movie poster: " + movie.getPoster());
                    // get image from firebase storage

                    if (movie != null) {
                        movieList.add(movie);
                    }
                }

                Collections.shuffle(movieList);
                RecyclerView highlightMovieRecyclerView = findViewById(R.id.recycler_highlights);
                HighlightMovieAdapter highlightMovieAdapter = new HighlightMovieAdapter(movieList);
                highlightMovieRecyclerView.setAdapter(highlightMovieAdapter);

                RecyclerView allMovieRecyclerView = findViewById(R.id.recycler_all_movies);
                ArrayList<Movie> allMovieList = new ArrayList<>(movieList);
                Collections.shuffle(allMovieList);
                AllMovieAdapter allMovieAdapter = new AllMovieAdapter(allMovieList, 0);
                allMovieRecyclerView.setAdapter(allMovieAdapter);
            }
        });
    }
}

