package com.example.midtermproject;

import java.io.Serializable;

public class Movie {
    private String title;
    private String posterPath;
    private String rating;
    private String overview;

    public Movie(String title, String posterPath, String rating, String overview) {
        this.title = title;
        this.posterPath = posterPath;
        this.rating = "Rating: " + rating ;
        this.overview = overview;
    }

    // Các phương thức getter
    public String getTitle() {
        return title;
    }
    public String getPosterPath() {
        return posterPath;
    }
    public String getRating() {
        return rating;
    }
    public String getOverview() {
        return overview;
    }

    // Các phương thức setter
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    public void setRating(String rating) {
        this.rating = "Rating: " + rating;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
}
