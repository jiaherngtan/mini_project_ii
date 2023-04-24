package com.vttp2022.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.vttp2022.backend.models.Review;

import jakarta.json.JsonArray;

import static com.vttp2022.backend.repositories.Queries.*;

import static com.vttp2022.backend.Utils.*;

import java.util.Optional;

@Repository
public class ReviewRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addReview(String email, String id, Review review) {
        jdbcTemplate.update(SQL_INSERT_TO_REVIEW,
                email,
                id,
                review.getRating(),
                review.getText(),
                review.getCreated());
    }

    public void updateReview(String email, String id, Review review) {
        jdbcTemplate.update(SQL_UPDATE_REVIEW,
                review.getRating(),
                review.getText(),
                review.getCreated(),
                email,
                id);
    }

    public boolean checkIfReviewExists(String email, String movieId) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_REVIEW, email, movieId);
        return rs.next();
    }

    public Optional<JsonArray> getReviews(String id) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(
                SQL_GET_REVIEWS_BY_USER_ID, id);

        try {
            return Optional.of(getReviewsRowSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<JsonArray> getSortedReviewsReviewRatingAsc(String id) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(
                SQL_GET_SORTED_REVIEWS_REVIEW_RATING_ASC_BY_USER_ID, id);

        try {
            return Optional.of(getReviewsRowSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<JsonArray> getSortedReviewsReviewRatingDesc(String id) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(
                SQL_GET_SORTED_REVIEWS_REVIEW_RATING_DESC_BY_USER_ID, id);

        try {
            return Optional.of(getReviewsRowSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<JsonArray> getSortedReviewsReviewAddedAsc(String id) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(
                SQL_GET_SORTED_REVIEWS_REVIEW_ADDED_ASC_BY_USER_ID, id);

        try {
            return Optional.of(getReviewsRowSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<JsonArray> getSortedReviewsReviewAddedDesc(String id) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(
                SQL_GET_SORTED_REVIEWS_REVIEW_ADDED_DESC_BY_USER_ID, id);

        try {
            return Optional.of(getReviewsRowSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<JsonArray> getSortedReviewsTitleAsc(String id) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(
                SQL_GET_SORTED_REVIEWS_TITLE_ASC_BY_USER_ID, id);

        try {
            return Optional.of(getReviewsRowSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<JsonArray> getSortedReviewsTitleDesc(String id) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(
                SQL_GET_SORTED_REVIEWS_TITLE_DESC_BY_USER_ID, id);

        try {
            return Optional.of(getReviewsRowSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void deleteReview(String email, String movieId) {
        jdbcTemplate.update(SQL_DELETE_FROM_REVIEW, email, movieId);
    }

    public void deleteAllReview(String email) {
        jdbcTemplate.update(SQL_DELETE_ALL_FROM_REVIEW, email);
    }

}
