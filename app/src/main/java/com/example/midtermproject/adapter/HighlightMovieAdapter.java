package com.example.midtermproject.adapter;

import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.Movie;
import com.example.midtermproject.MoviePage;
import com.example.midtermproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class HighlightMovieAdapter extends RecyclerView.Adapter<HighlightMovieAdapter.ViewHolder> {

    private List<Movie> movieList;

    public HighlightMovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.highlight_movies_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Picasso.get().load(movie.getPoster()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item's data if needed
                Movie clickedItem = movieList.get(position);

                // Start the new activity
                Intent intent = new Intent(view.getContext(), MoviePage.class);
                // Pass any data to the new activity if needed
                intent.putExtra("movieTitle", clickedItem.getTitle());
                intent.putExtra("movieDescription", clickedItem.getSypnosis());
                intent.putExtra("movieImage", clickedItem.getPoster());
                intent.putExtra("movieRating", clickedItem.getRating());
                intent.putExtra("movieDuration", clickedItem.getDuration());
                intent.putExtra("movieGenre", clickedItem.getGenre());
                intent.putExtra("movieTrailer", clickedItem.getTrailer());

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}

