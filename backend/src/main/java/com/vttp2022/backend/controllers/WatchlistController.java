package com.vttp2022.backend.controllers;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vttp2022.backend.models.Movie;
import com.vttp2022.backend.models.Review;
import com.vttp2022.backend.services.WatchlistService;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import static com.vttp2022.backend.Utils.*;

@Controller
@RequestMapping(path = "/api/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @PostMapping(path = "/add/pend", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> addToWantToWatch(@RequestBody String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jo = reader.readObject();

        String email = jo.getString("email");
        JsonObject movieJo = jo.getJsonObject("movie");

        Movie movie = createMovieFromJson(movieJo);
        movie.setStatus("pend");

        try {
            movie = watchlistService.addToWantToWatch(email, movie);

            JsonObject result = Json.createObjectBuilder()
                    .add("result", movie.getTitle() + " is added to want to watch")
                    .add("added", movie.getAddedDateTime().toString())
                    .add("email", email)
                    .build();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Error adding movie to want to watch")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @PostMapping(path = "/add/done", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> addToWatched(@RequestBody String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jo = reader.readObject();

        String email = jo.getString("email");
        Integer reviewRating = jo.getInt("reviewRating");
        System.out.println(reviewRating);
        String text = jo.getString("text");

        JsonObject movieJo = jo.getJsonObject("movie");

        Movie movie = createMovieFromJson(movieJo);
        movie.setStatus("done");

        Review review = new Review();
        review.setMovie(movie);
        reviewRating = (reviewRating == 0) ? 0 : reviewRating;
        review.setRating(reviewRating);
        text = (text == null) ? "" : text;
        review.setText(text);

        try {
            movie = watchlistService.addToWatched(email, movie, review);

            JsonObject result = Json.createObjectBuilder()
                    .add("result", movie.getTitle() + " is added to watched")
                    .add("id", movie.getId())
                    .add("title", movie.getTitle())
                    .add("added", movie.getAddedDateTime().toString())
                    .add("email", email)
                    .add("reviewRating", review.getRating())
                    .add("text", review.getText())
                    .build();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Error adding movie to watched")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> updateToWatched(@RequestBody String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jo = reader.readObject();

        String email = jo.getString("email");
        JsonObject movieJo = jo.getJsonObject("movie");

        Movie movie = createMovieFromJson(movieJo);
        movie.setStatus("done");

        Integer reviewRating = jo.getInt("reviewRating");
        String text = jo.getString("text");
        Review review = new Review();
        review.setMovie(movie);
        reviewRating = (reviewRating == 0) ? 0 : reviewRating;
        review.setRating(reviewRating);
        text = (text == null) ? "" : text;
        review.setText(text);

        try {
            movie = watchlistService.updateToWatched(email, movie, review);

            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Update successful")
                    .add("id", movie.getId())
                    .add("title", movie.getTitle())
                    .add("added", movie.getAddedDateTime().toString())
                    .add("email", email)
                    .add("reviewRating", review.getRating())
                    .add("text", review.getText())
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Error in updating movie")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @DeleteMapping(path = "/remove/{email}/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> removeFromWatchlist(@PathVariable String email, @PathVariable String movieId) {
        try {
            Boolean delResult = watchlistService.removeFromWatchlist(email, movieId);
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Movie with " + movieId + " is deleted: " + delResult)
                    .add("user id", email)
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Error removing movie from watchlist")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @GetMapping(path = "/{id}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getWatchlist(@PathVariable String id, @PathVariable String status) {
        Optional<JsonArray> optResult = watchlistService.getWatchlist(id, status);
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

}
