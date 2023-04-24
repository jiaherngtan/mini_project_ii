package com.vttp2022.backend.models;

import java.util.Date;

public class Review {

    private Movie movie;
    private Integer rating;
    private String text;
    private Date created;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Review [movie=" + movie + ", rating=" + rating + ", text=" + text + ", created=" + created + "]";
    }

}
