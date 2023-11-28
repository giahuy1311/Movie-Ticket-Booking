package com.example.midtermproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.midtermproject.adapter.AllMovieAdapter;
import com.example.midtermproject.adapter.HighlightMovieAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllMoviesActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);

        searchView = findViewById(R.id.search_view);

        recyclerView = findViewById(R.id.recycler_search_all_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        movieList = new ArrayList<>();
        AllMovieAdapter allMovieAdapter = new AllMovieAdapter(movieList, 1);
        recyclerView.setAdapter(allMovieAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovie(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchMovie(query);
                return false;
            }
        });
        loadMovieData();
    }

    private void searchMovie(String query) {
        List<Movie> filteredList = new ArrayList<>();

        if (query.isEmpty()) {
            Log.d("SearchView", "Empty query");
            // If the search query is empty, display the full list of movies
            filteredList = new ArrayList<>(movieList);
        } else {
            // Filter the movies based on the search query
            filteredList = new ArrayList<>();
            for (Movie movie : movieList) {
                // Assuming Movie class has a method to get the title for filtering
                if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(movie);
                }
            }
        }

        // Update the RecyclerView with the filtered results
        AllMovieAdapter allMovieAdapter = (AllMovieAdapter) recyclerView.getAdapter();
        if (allMovieAdapter != null) {
            allMovieAdapter.setMovieList(filteredList);
        }
    }

    private void loadMovieData() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("movie");
        databaseReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                movieList.clear();

                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Movie movie = dataSnapshot.getValue(Movie.class);

                    Log.d("FetchMovieDataTask", "Movie poster: " + movie.getPoster());
                    // get image from firebase storage

                    if (movie != null) {
                        movieList.add(movie);
                    }
                }

                Collections.shuffle(movieList);
                AllMovieAdapter allMovieAdapter = (AllMovieAdapter) recyclerView.getAdapter();
                if (allMovieAdapter != null) {
                    allMovieAdapter.setMovieList(movieList);
                }
            }
        });
    }
}

//    private class FetchMovieDataTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            // Thực hiện việc tải dữ liệu từ API trong background thread
//            String apiUrl = params[0];
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            String movieDataJson = null;
//
//            try {
//                URL url = new URL(apiUrl);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuilder buffer = new StringBuilder();
//
//                if (inputStream == null) {
//                    return null;
//                }
//
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line).append("\n");
//                }
//
//                if (buffer.length() == 0) {
//                    return null;
//                }
//
//                movieDataJson = buffer.toString();
//            } catch (IOException e) {
//                Log.e("FetchMovieDataTask", "Error ", e);
//                return null;
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("FetchMovieDataTask", "Error closing stream", e);
//                    }
//                }
//            }
//
//            return movieDataJson;
//        }
//
//        @Override
//        protected void onPostExecute(String movieDataJson) {
//            // Xử lý dữ liệu JSON ở đây sau khi AsyncTask hoàn thành
//            if (movieDataJson != null) {
//                parseMovieData(movieDataJson);
//            } else {
//                Log.e("FetchMovieDataTask", "Error: Unable to fetch movie data");
//            }
//        }
//
//        private void parseMovieData(String movieDataJson) {
//            try {
//                JSONObject jsonObject = new JSONObject(movieDataJson);
//                JSONArray resultsArray = jsonObject.getJSONArray("results");
//
//                for (int i = 0; i < resultsArray.length(); i++) {
//                    JSONObject movieObject = resultsArray.getJSONObject(i);
//                    String title = movieObject.getString("original_title");
//                    String rating = movieObject.getString("vote_average");
//                    Log.d("FetchMovieDataTask", "Rating: " + rating);
//                    String posterPath = movieObject.getString("poster_path");
//                    String overView = movieObject.getString("overview");
//                    String completePosterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
//                    Movie movie = new Movie(title, completePosterPath,rating,overView);
//                    movieList.add(movie);
//                }
//
//                // Sau khi đã thêm dữ liệu vào danh sách, cập nhật RecyclerView
//                recyclerView.getAdapter().notifyDataSetChanged();
//
//            } catch (JSONException e) {
//                Log.e("FetchMovieDataTask", "Error parsing JSON", e);
//            }
//        }
//
//
//    }
