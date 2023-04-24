package com.vttp2022.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vttp2022.backend.models.Movie;
import com.vttp2022.backend.models.Review;
import com.vttp2022.backend.repositories.ReviewRepository;
import com.vttp2022.backend.repositories.WatchlistRepository;

import jakarta.json.JsonArray;

import java.util.Date;
import java.util.Optional;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(rollbackFor = { Exception.class })
    public Movie addToWantToWatch(String email, Movie movie) {
        movie.setAddedDateTime(new Date());
        watchlistRepository.addToWatchlist(email, movie);
        return movie;
    }

    @Transactional(rollbackFor = { Exception.class })
    public Movie addToWatched(String email, Movie movie, Review review) {
        movie.setAddedDateTime(new Date());
        review.setCreated(new Date());
        // transactional for following 2 insertions
        watchlistRepository.addToWatchlist(email, movie);
        if (review.getRating() != 0 && review.getText() != "") {
            System.out.println("inside");
            reviewRepository.addReview(email, movie.getId(), review);
        }
        return movie;
    }

    @Transactional(rollbackFor = { Exception.class })
    public Movie updateToWatched(String email, Movie movie, Review review) {
        System.out.println("here 1");
        movie.setAddedDateTime(new Date());
        review.setCreated(new Date());
        // transactional for following 2 insertions
        watchlistRepository.updateToWatched(email, movie);
        if (review.getRating() != 0 && review.getText() != "")
            reviewRepository.addReview(email, movie.getId(), review);
        return movie;
    }

    @Transactional(rollbackFor = { Exception.class })
    public Boolean removeFromWatchlist(String email, String movieId) {
        if (reviewRepository.checkIfReviewExists(email, movieId))
            reviewRepository.deleteReview(email, movieId);
        return watchlistRepository.removeFromWatchlist(email, movieId);
    }

    public Optional<JsonArray> getWatchlist(String id, String status) {
        return watchlistRepository.getWatchlist(id, status);
    }

}
