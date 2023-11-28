package com.example.midtermproject;

public class Movie implements java.io.Serializable{
    public String title;
    public String poster;
    public String rating;
    public String sypnosis;

    public Movie() {
    }

    public Movie(String title, String posterPath, String rating, String overview) {
        this.title = title;
        this.poster = posterPath;
        this.rating = "Rating: " + rating ;
        this.sypnosis = overview;
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
//    public void setPoster(String poster) {
//        this.poster = poster;
//    }
//    public void setRating(String rating) {
//        this.rating = "Rating: " + rating;
//    }
//    public void setSypnosis(String sypnosis) {
//        this.sypnosis = sypnosis;
//    }
}
