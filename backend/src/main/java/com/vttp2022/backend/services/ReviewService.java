package com.vttp2022.backend.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vttp2022.backend.models.Movie;
import com.vttp2022.backend.models.Review;
import com.vttp2022.backend.repositories.ReviewRepository;

import jakarta.json.JsonArray;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(rollbackFor = { Exception.class })
    public Review addReview(String email, Movie movie, Review review) {
        review.setCreated(new Date());
        if (review.getRating() != 0 && review.getText() != "")
            reviewRepository.addReview(email, movie.getId(), review);
        return review;
    }

    @Transactional(rollbackFor = { Exception.class })
    public Review editReview(String email, Movie movie, Review review) {
        review.setCreated(new Date());
        System.out.println(review);
        if (review.getRating() != 0 && review.getText() != "")
            reviewRepository.updateReview(email, movie.getId(), review);
        return review;
    }

    public Optional<JsonArray> getReviews(String id) {
        return reviewRepository.getReviews(id);
    }

    public Optional<JsonArray> getSortedReviews(String id, String pattern) {
        String sortBy = pattern;
        Optional<JsonArray> ja = Optional.empty();
        switch (sortBy) {
            case "asc":
                ja = reviewRepository.getSortedReviewsTitleAsc(id);
                break;
            case "desc":
                ja = reviewRepository.getSortedReviewsTitleDesc(id);
                break;
            case "highest":
                ja = reviewRepository.getSortedReviewsReviewRatingDesc(id);
                break;
            case "lowest":
                ja = reviewRepository.getSortedReviewsReviewRatingAsc(id);
                break;
            case "newest":
                ja = reviewRepository.getSortedReviewsReviewAddedDesc(id);
                break;
            case "oldest":
                ja = reviewRepository.getSortedReviewsReviewAddedAsc(id);
                break;
        }
        return ja;
    }

}
