package com.example.midtermproject.data;

public class Movie implements java.io.Serializable{
    public String title;
    public String poster;
    public String rating;
    public String sypnosis;
    private String trailer;
    private String genre;
    private String duration;

    public Movie() {
    }

    public Movie(String title, String posterPath, String rating, String overview, String trailer, String genre, String duration) {
        this.title = title;
        this.poster = posterPath;
        this.rating = "Rating: " + rating ;
        this.sypnosis = overview;
        this.trailer = trailer;
        this.genre = genre;
        this.duration = duration;
    }

    // Các phương thức getter
    public String getTitle() {
        return title;
    }
    public String getPoster() {
        return poster;
    }
    public String getRating() {
        return rating;
    }
    public String getSypnosis() {
        return sypnosis;
    }
    public String getTrailer() {
        return trailer;
    }
    public String getGenre() {
        return genre;
    }
    public String getDuration() {
        return duration;
    }

    // Các phương thức setter
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPosterPath(String posterPath) {
        this.poster = posterPath;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public void setOverview(String overview) {
        this.sypnosis = overview;
    }
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
