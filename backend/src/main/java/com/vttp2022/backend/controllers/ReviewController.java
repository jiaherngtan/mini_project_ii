package com.vttp2022.backend.controllers;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vttp2022.backend.models.Movie;
import com.vttp2022.backend.models.Review;
import com.vttp2022.backend.services.ReviewService;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import static com.vttp2022.backend.Utils.*;

@Controller
@RequestMapping(path = "/api/watchlist")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping(path = "/add/review", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> addReview(@RequestBody String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jo = reader.readObject();

        System.out.println(jo);

        String email = jo.getString("email");
        JsonObject movieJo = jo.getJsonObject("movie");

        Movie movie = createMovieFromJson(movieJo);

        Integer reviewRating = jo.getInt("reviewRating");
        String text = jo.getString("text");
        Review review = new Review();
        review.setMovie(movie);
        reviewRating = (reviewRating == 0) ? 0 : reviewRating;
        review.setRating(reviewRating);
        text = (text == null) ? "" : text;
        review.setText(text);

        try {
            review = reviewService.addReview(email, movie, review);

            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Review added successfully")
                    .add("id", movie.getId())
                    .add("title", movie.getTitle())
                    .add("email", email)
                    .add("reviewRating", review.getRating())
                    .add("text", review.getText())
                    .add("created", review.getCreated().toString())
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Error in adding review")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @PutMapping(path = "/update/review", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> editReview(@RequestBody String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject jo = reader.readObject();

        String email = jo.getString("email");
        JsonObject movieJo = jo.getJsonObject("movie");

        Movie movie = createMovieFromJson(movieJo);

        Integer reviewRating = jo.getInt("reviewRating");
        String text = jo.getString("text");
        Review review = new Review();
        review.setMovie(movie);
        reviewRating = (reviewRating == 0) ? 0 : reviewRating;
        review.setRating(reviewRating);
        text = (text == null) ? "" : text;
        review.setText(text);

        try {
            review = reviewService.editReview(email, movie, review);

            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Review edited successfully")
                    .add("id", movie.getId())
                    .add("title", movie.getTitle())
                    .add("email", email)
                    .add("reviewRating", review.getRating())
                    .add("text", review.getText())
                    .add("created", review.getCreated().toString())
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Error in editing review")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @GetMapping(path = "/reviews/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getReviews(@PathVariable String id) {
        Optional<JsonArray> optResult = reviewService.getReviews(id);
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

    @GetMapping(path = "/reviews/{id}/{pattern}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getSortedReviews(@PathVariable String id, @PathVariable String pattern) {
        Optional<JsonArray> optResult = reviewService.getSortedReviews(id, pattern);
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

}
