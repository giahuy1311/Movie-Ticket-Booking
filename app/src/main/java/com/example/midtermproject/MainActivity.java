package com.example.midtermproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.midtermproject.adapter.AllMovieAdapter;
import com.example.midtermproject.adapter.HighlightMovieAdapter;

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

public class MainActivity extends AppCompatActivity {
    private List<Movie> movieList;
    private TextView viewAllMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView highlightMovieRecyclerView = findViewById(R.id.recycler_highlights);
        RecyclerView allMovieRecyclerView = findViewById(R.id.recycler_all_movies);
        // Khởi tạo MovieAdapter

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        highlightMovieRecyclerView.setLayoutManager(layoutManager1);
        allMovieRecyclerView.setLayoutManager(layoutManager2);

        movieList = new ArrayList<>();
        HighlightMovieAdapter highlightMovieAdapter = new HighlightMovieAdapter(movieList);
        AllMovieAdapter allMovieAdapter = new AllMovieAdapter(movieList,0);

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

        loadMovieData();
    }

    private void loadMovieData() {
        String api_key = "c3e95bd49ed09d54a823c7da583a0bc4";
        String api_url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + api_key;

        new FetchMovieDataTask().execute(api_url);
    }

    private class FetchMovieDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // Thực hiện việc tải dữ liệu từ API trong background thread
            String apiUrl = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieDataJson = null;

            try {
                URL url = new URL(apiUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                movieDataJson = buffer.toString();
            } catch (IOException e) {
                Log.e("FetchMovieDataTask", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("FetchMovieDataTask", "Error closing stream", e);
                    }
                }
            }

            return movieDataJson;
        }

        @Override
        protected void onPostExecute(String movieDataJson) {
            // Xử lý dữ liệu JSON ở đây sau khi AsyncTask hoàn thành
            if (movieDataJson != null) {
                parseMovieData(movieDataJson);
            } else {
                Log.e("FetchMovieDataTask", "Error: Unable to fetch movie data");
            }
        }

        private void parseMovieData(String movieDataJson) {
            try {
                JSONObject jsonObject = new JSONObject(movieDataJson);
                JSONArray resultsArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieObject = resultsArray.getJSONObject(i);
                    String title = movieObject.getString("original_title");
                    String rating = movieObject.getString("vote_average");
                    Log.d("FetchMovieDataTask", "Rating: " + rating);
                    String posterPath = movieObject.getString("poster_path");
                    String overView = movieObject.getString("overview");
                    String completePosterPath = "https://image.tmdb.org/t/p/w500" + posterPath;
                    Movie movie = new Movie(title, completePosterPath,rating,overView);
                    movieList.add(movie);
                }

                // Sau khi đã thêm dữ liệu vào danh sách, cập nhật RecyclerView
                Log.d("FetchMovieDataTask", "Movie list size: " + movieList.size());
                RecyclerView highlightMovieRecyclerView = findViewById(R.id.recycler_highlights);
                HighlightMovieAdapter highlightMovieAdapter = new HighlightMovieAdapter(movieList);
                highlightMovieRecyclerView.setAdapter(highlightMovieAdapter);

                RecyclerView allMovieRecyclerView = findViewById(R.id.recycler_all_movies);
                ArrayList<Movie> allMovieList = new ArrayList<>(movieList);
                Collections.shuffle(allMovieList);
                AllMovieAdapter allMovieAdapter = new AllMovieAdapter(allMovieList,0);
                allMovieRecyclerView.setAdapter(allMovieAdapter);
            } catch (JSONException e) {
                Log.e("FetchMovieDataTask", "Error parsing JSON", e);
            }
        }


    }
}