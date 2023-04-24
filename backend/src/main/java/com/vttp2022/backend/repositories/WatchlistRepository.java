package com.vttp2022.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.vttp2022.backend.models.Movie;

import jakarta.json.JsonArray;

import static com.vttp2022.backend.repositories.Queries.*;

import static com.vttp2022.backend.Utils.*;

@Repository
public class WatchlistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addToWatchlist(String email, Movie movie) {
        List<String> countryLs = movie.getCountries();
        String countries = String.join(",", countryLs);
        jdbcTemplate.update(SQL_INSERT_TO_WATCHLIST,
                movie.getId(),
                movie.getTitle(),
                movie.getTagline(),
                movie.getOverview(),
                movie.getReleaseYear(),
                movie.getPosterUrl(),
                movie.getRating(),
                countries,
                movie.getStatus(),
                movie.getAddedDateTime(),
                email);
    }

    public void updateToWatched(String email, Movie movie) {
        jdbcTemplate.update(SQL_UPDATE_TO_WATCHED,
                "done",
                email,
                movie.getId());
    }

    public Boolean removeFromWatchlist(String email, String movieId) {
        return jdbcTemplate.update(SQL_DELETE_FROM_WATCHLIST, email, movieId) == 1;
    }

    public void deleteWatchlist(String email) {
        jdbcTemplate.update(SQL_DELETE_ALL_FROM_WATCHLIST, email);
    }

    public Optional<JsonArray> getWatchlist(String id, String status) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(
                SQL_GET_WATCHLIST_BY_USER_ID, id, status);

        try {
            return Optional.of(getMovieListRowSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
