package com.example.midtermproject.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.Movie;
import com.example.midtermproject.MoviePage;
import com.example.midtermproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllMovieAdapter extends RecyclerView.Adapter<AllMovieAdapter.ViewHolder> {

    private List<Movie> movieList;
    private int layoutType;
    public AllMovieAdapter(List<Movie> movieList, int layoutType) {
        this.movieList = movieList;
        this.layoutType = layoutType;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (layoutType == 0)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_movies_item, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_all_movies_item, parent, false);

        return new AllMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllMovieAdapter.ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Picasso.get().load(movie.getPosterPath()).into(holder.imageView);
        holder.titleTextView.setText(movie.getTitle());
        holder.ratingTextView.setText(movie.getRating().toString());

        // Start movie page activity
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item's data if needed
                Movie clickedItem = movieList.get(position);

                // Start the new activity
                Intent intent = new Intent(view.getContext(), MoviePage.class);
                // Pass any data to the new activity if needed
                intent.putExtra("movieTitle", clickedItem.getTitle());
                intent.putExtra("movieDescription", clickedItem.getOverview());
                intent.putExtra("movieImage", clickedItem.getPosterPath());
                intent.putExtra("movieRating", clickedItem.getRating());
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
        public TextView titleTextView;
        public TextView ratingTextView;
        // Khai báo các View trong ViewHolder
        // Ví dụ: public TextView titleTextView;
        //      public ImageView posterImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            // Ánh xạ các View từ itemView
            // Ví dụ: titleTextView = itemView.findViewById(R.id.titleTextView);
            //      posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}
