package com.vttp2022.backend.models;

import java.util.Date;
import java.util.List;

public class Movie {

    private String id;
    private String imdbId;
    private String imdbUrl;
    private String title;
    private String tagline;
    private String overview;
    private Integer runtime;
    private String releaseDate;
    private String releaseYear;
    private String posterUrl;
    private String backdropUrl;
    private Float rating;
    private String ratingCount;
    private List<String> genres;
    private List<String> countries;
    private List<String> languages;
    // private String queryString;
    private String status;
    private Date addedDateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getImdbUrl() {
        return imdbUrl;
    }

    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    // public String getQueryString() {
    // return queryString;
    // }

    // public void setQueryString(String queryString) {
    // this.queryString = queryString;
    // }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(Date addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    @Override
    public String toString() {
        return "Movie [id=" + id + ", imdbId=" + imdbId + ", imdbUrl=" + imdbUrl + ", title=" + title + ", tagline="
                + tagline + ", overview=" + overview + ", runtime=" + runtime + ", releaseDate=" + releaseDate
                + ", releaseYear=" + releaseYear + ", posterUrl=" + posterUrl + ", backdropUrl=" + backdropUrl
                + ", rating=" + rating + ", ratingCount=" + ratingCount + ", genres=" + genres + ", countries="
                + countries + ", languages=" + languages + ", status=" + status + ", addedDateTime=" + addedDateTime
                + "]";
    }

}
