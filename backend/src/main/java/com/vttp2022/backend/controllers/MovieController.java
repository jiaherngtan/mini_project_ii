package com.vttp2022.backend.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vttp2022.backend.services.MovieService;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping(path = "/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getPopularMovies(@PathVariable String keyword) {
        System.out.println(keyword);
        Optional<JsonArray> optResult = movieService.getMovieList(keyword);
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

    @GetMapping(path = "/genre/{id}/{genre}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getMoviesByGenre(
            @PathVariable Integer id,
            @PathVariable String genre) {
        System.out.println(id);
        System.out.println(genre);
        Optional<JsonArray> optResult = movieService.getMovieList("genre", id);
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

    @GetMapping(path = "/movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getMovie(@PathVariable String id) {
        Optional<JsonObject> optResult = movieService.getMovie(id);
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

    @GetMapping(path = "/people/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getPeople(@PathVariable String id) {
        Optional<JsonObject> optResult = movieService.getPeople(id);
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

    @GetMapping(path = "/movie/{id}/similar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getSimilarMovies(@PathVariable String id) {
        Optional<JsonArray> optResult = movieService.getMovieList("similar", Integer.parseInt(id));
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

    @GetMapping(path = "/movie/{id}/crew", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getCrewByMovie(@PathVariable String id) {
        Optional<JsonArray> optResult = movieService.getCrewByMovie(id);
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

    @GetMapping(path = "/people/popular", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getPopularPeople() {
        Optional<JsonArray> optResult = movieService.getPopularPeople();
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

    @GetMapping(path = "/search/{query}/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getModelBySearch(
            @PathVariable String query,
            @PathVariable String keyword) {
        Optional<JsonArray> optResult = movieService.getModelBySearch(query, keyword);
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

    @GetMapping(path = "/genres", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getGenres() {
        Optional<JsonArray> optResult = movieService.getGenres();
        if (optResult.isEmpty())
            return null;
        return ResponseEntity.ok(optResult.get().toString());
    }

}
