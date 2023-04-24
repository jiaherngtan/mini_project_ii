package com.vttp2022.backend.repositories;

public class Queries {

    // user related
    public static final String SQL_INSERT_USER = "INSERT INTO user(id,username,email,created,image_key) VALUES(?, ?, ?, ?, ?)";

    public static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";

    public static final String SQL_DELETE_USER = "DELETE FROM user WHERE email = ?";

    // watchlist related
    public static final String SQL_INSERT_TO_WATCHLIST = "INSERT INTO watchlist(movie_id,title,tagline,overview,release_year,poster_url,rating,countries,status,added,email) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_UPDATE_TO_WATCHED = "UPDATE watchlist SET status = ? WHERE email = ? AND movie_id = ?";

    public static final String SQL_DELETE_FROM_WATCHLIST = "DELETE FROM watchlist WHERE email = ? AND movie_id = ?";

    public static final String SQL_DELETE_ALL_FROM_WATCHLIST = "DELETE FROM watchlist WHERE email = ?";

    public static final String SQL_GET_WATCHLIST_BY_USER_ID = "SELECT * FROM watchlist LEFT JOIN user ON watchlist.email = user.email LEFT JOIN review ON watchlist.movie_id = review.movie_id WHERE id = ? AND status = ?";

    // review related
    public static final String SQL_INSERT_TO_REVIEW = "INSERT INTO review(email,movie_id,review_rating,review_text,review_added) VALUES (?, ?, ?, ?, ?)";

    public static final String SQL_GET_REVIEW = "SELECT * FROM review WHERE email = ? AND movie_id = ?";

    public static final String SQL_GET_REVIEWS_BY_USER_ID = "SELECT * FROM watchlist LEFT JOIN user ON watchlist.email = user.email LEFT JOIN review ON watchlist.movie_id = review.movie_id WHERE id = ? AND review_id IS NOT NULL";

    public static final String SQL_DELETE_FROM_REVIEW = "DELETE FROM review WHERE email = ? AND movie_id = ?";

    public static final String SQL_DELETE_ALL_FROM_REVIEW = "DELETE FROM review WHERE email = ?";

    public static final String SQL_UPDATE_REVIEW = "UPDATE review SET review_rating = ?, review_text = ?, review_added = ? WHERE email = ? AND movie_id = ?";

    // sorted review related
    public static final String SQL_GET_SORTED_REVIEWS_TITLE_ASC_BY_USER_ID = "SELECT * FROM watchlist LEFT JOIN user ON watchlist.email = user.email LEFT JOIN review ON watchlist.movie_id = review.movie_id WHERE id = ? ORDER BY title ASC";

    public static final String SQL_GET_SORTED_REVIEWS_TITLE_DESC_BY_USER_ID = "SELECT * FROM watchlist LEFT JOIN user ON watchlist.email = user.email LEFT JOIN review ON watchlist.movie_id = review.movie_id WHERE id = ? ORDER BY title DESC";

    public static final String SQL_GET_SORTED_REVIEWS_REVIEW_RATING_ASC_BY_USER_ID = "SELECT * FROM watchlist LEFT JOIN user ON watchlist.email = user.email LEFT JOIN review ON watchlist.movie_id = review.movie_id WHERE id = ? ORDER BY review_rating ASC";

    public static final String SQL_GET_SORTED_REVIEWS_REVIEW_RATING_DESC_BY_USER_ID = "SELECT * FROM watchlist LEFT JOIN user ON watchlist.email = user.email LEFT JOIN review ON watchlist.movie_id = review.movie_id WHERE id = ? ORDER BY review_rating DESC";

    public static final String SQL_GET_SORTED_REVIEWS_REVIEW_ADDED_ASC_BY_USER_ID = "SELECT * FROM watchlist LEFT JOIN user ON watchlist.email = user.email LEFT JOIN review ON watchlist.movie_id = review.movie_id WHERE id = ? ORDER BY review_added ASC";

    public static final String SQL_GET_SORTED_REVIEWS_REVIEW_ADDED_DESC_BY_USER_ID = "SELECT * FROM watchlist LEFT JOIN user ON watchlist.email = user.email LEFT JOIN review ON watchlist.movie_id = review.movie_id WHERE id = ? ORDER BY review_added DESC";

}