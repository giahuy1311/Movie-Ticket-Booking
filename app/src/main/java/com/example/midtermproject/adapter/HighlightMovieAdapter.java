package com.example.midtermproject.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.Movie;
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
        // Hiển thị thông tin bộ phim trong ViewHolder
        // Ví dụ: holder.titleTextView.setText(movie.getTitle());

        // Sử dụng thư viện Picasso/Glide để tải và hiển thị ảnh poster từ URL
        // Picasso.get().load(movie.getPosterPath()).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        // Khai báo các View trong ViewHolder
        // Ví dụ: public TextView titleTextView;
        //      public ImageView posterImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.posterImageView);
            // Ánh xạ các View từ itemView
            // Ví dụ: titleTextView = itemView.findViewById(R.id.titleTextView);
            //      posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}

