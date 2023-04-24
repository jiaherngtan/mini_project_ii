package com.vttp2022.backend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import com.vttp2022.backend.repositories.MovieRepository;

import static com.vttp2022.backend.Utils.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    private static String apiKey = System.getenv("TMDB_API_KEY");
    private static String URL = "https://api.themoviedb.org/3/movie/";

    public Optional<JsonArray> getMovieList(String keyword, Integer id) {

        // check from redis cache
        if (movieRepository.checkFromRedis(keyword + "-" + id)) {
            System.out.println(">>> Getting movie list data from Redis..." + keyword + id);
            // get from redis cache
            return movieRepository.getArrFromRedis(keyword + "-" + id);
        } else {
            System.out.println(">>> Getting movie list data from API..." + keyword + id);
            String url = "";
            switch (keyword) {
                case "genre":
                    url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/discover/movie?")
                            .queryParam("api_key", apiKey)
                            .queryParam("language", "en")
                            .queryParam("sort_by", "popularity.desc")
                            .queryParam("page", "1")
                            .queryParam("with_genres", id)
                            .toUriString();
                    break;
                case "similar":
                    url = UriComponentsBuilder.fromUriString(URL + id + "/similar?")
                            .queryParam("api_key", apiKey)
                            .queryParam("language", "en")
                            .queryParam("page", "1")
                            .toUriString();
                    break;
                default:
                    System.out.println("invalid keyword");
            }
            try {
                Optional<JsonArray> result = getMovieListResp(url);
                // add to redis cache
                movieRepository.addToRedis(keyword + "-" + id, result.get());
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }
    }

    public Optional<JsonArray> getMovieList(String keyword) {

        // check from redis cache
        if (movieRepository.checkFromRedis(keyword)) {
            System.out.println(">>> Getting movie list data from Redis..." + keyword);
            // get from redis cache
            return movieRepository.getArrFromRedis(keyword);
        } else {
            System.out.println(">>> Getting movie list data from API..." + keyword);
            String url = "";
            switch (keyword) {
                case "popular":
                    url = UriComponentsBuilder.fromUriString(URL + "popular?")
                            .queryParam("api_key", apiKey)
                            .queryParam("language", "en")
                            .queryParam("page", "1")
                            .toUriString();
                    break;
                case "top":
                    url = UriComponentsBuilder.fromUriString(URL + "top_rated?")
                            .queryParam("api_key", apiKey)
                            .queryParam("language", "en")
                            .queryParam("page", "1")
                            .toUriString();
                    break;
                case "now":
                    url = UriComponentsBuilder.fromUriString(URL + "now_playing?")
                            .queryParam("api_key", apiKey)
                            .queryParam("language", "en")
                            .queryParam("page", "1")
                            .toUriString();
                    break;
                default:
                    System.out.println("invalid keyword");
            }
            try {
                Optional<JsonArray> result = getMovieListResp(url);
                // add to redis cache
                movieRepository.addToRedis(keyword, result.get());
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }
    }

    public Optional<JsonObject> getMovie(String id) {

        // check from redis cache
        if (movieRepository.checkFromRedis("movie-" + id)) {
            System.out.println(">>> Getting movie data from Redis..." + id);
            // get from redis cache
            return movieRepository.getObjFromRedis("movie-" + id);
        } else {
            System.out.println(">>> Getting movie data from API..." + id);
            String url = UriComponentsBuilder.fromUriString(URL + id + "?")
                    .queryParam("api_key", apiKey)
                    .queryParam("language", "en")
                    .toUriString();

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = null;

            try {
                resp = template.getForEntity(url, String.class);
                Optional<JsonObject> result = Optional.of(getMovieJson(resp.getBody()));
                // add to redis cache
                movieRepository.addToRedis("movie-" + id, result.get());
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }
    }

    public Optional<JsonObject> getPeople(String id) {

        // check from redis cache
        if (movieRepository.checkFromRedis("people-" + id)) {
            System.out.println(">>> Getting people data from Redis..." + id);
            // get from redis cache
            return movieRepository.getObjFromRedis("people-" + id);
        } else {
            System.out.println(">>> Getting people data from API..." + id);
            String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/person/" + id + "?")
                    .queryParam("api_key", apiKey)
                    .queryParam("language", "en")
                    .toUriString();

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = null;

            try {
                resp = template.getForEntity(url, String.class);
                Optional<JsonObject> result = Optional.of(getPeopleJson(resp.getBody()));
                // add to redis cache
                movieRepository.addToRedis("people-" + id, result.get());
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }
    }

    public Optional<JsonArray> getCrewByMovie(String id) {

        // check from redis cache
        if (movieRepository.checkFromRedis("movie-crew-" + id)) {
            System.out.println(">>> Getting movie crew data from Redis..." + id);
            // get from redis cache
            return movieRepository.getArrFromRedis("movie-crew-" + id);
        } else {
            System.out.println(">>> Getting movie crew data from API..." + id);
            String url = UriComponentsBuilder.fromUriString(URL + id + "/credits?")
                    .queryParam("api_key", apiKey)
                    .queryParam("language", "en")
                    .toUriString();

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = null;

            try {
                resp = template.getForEntity(url, String.class);
                Optional<JsonArray> result = Optional.of(getCrewByMovieJson(resp.getBody()));
                // add to redis cache
                movieRepository.addToRedis("movie-crew-" + id, result.get());
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }
    }

    public Optional<JsonArray> getPopularPeople() {

        // check from redis cache
        if (movieRepository.checkFromRedis("popular-people")) {
            System.out.println(">>> Getting popular people data from Redis...");
            // get from redis cache
            return movieRepository.getArrFromRedis("popular-people");
        } else {
            System.out.println(">>> Getting popular people data from API...");
            String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/person/popular?")
                    .queryParam("api_key", apiKey)
                    .queryParam("language", "en")
                    .toUriString();

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = null;

            try {
                resp = template.getForEntity(url, String.class);
                Optional<JsonArray> result = Optional.of(getPeopleListJson(resp.getBody()));
                // add to redis cache
                movieRepository.addToRedis("popular-people", result.get());
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }
    }

    public Optional<JsonArray> getModelBySearch(String query, String keyword) {

        Optional<JsonArray> result = Optional.of(Json.createArrayBuilder().build());

        try {
            switch (keyword) {
                case "movie":
                    // check from redis cache
                    if (movieRepository.checkFromRedis(query + "-movie")) {
                        System.out.println(">>> Getting movie query data from Redis..." + query);
                        // get from redis cache
                        result = movieRepository.getArrFromRedis(query + "-movie");
                    } else {
                        System.out.println(">>> Getting movie query data from API..." + query);
                        String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/search/movie?")
                                .queryParam("api_key", apiKey)
                                .queryParam("language", "en")
                                .queryParam("query", query)
                                .queryParam("page", "1")
                                .queryParam("include_adult", "false")
                                .toUriString()
                                .replace("%20", " ");

                        result = getMovieListResp(url);
                        // add to redis cache
                        movieRepository.addToRedis(query + "-movie", result.get());
                    }
                    break;
                case "people":
                    // check from redis cache
                    if (movieRepository.checkFromRedis(query + "-people")) {
                        System.out.println(">>> Getting people query data from Redis..." + query);
                        // get from redis cache
                        result = movieRepository.getArrFromRedis(query + "-people");
                    } else {
                        System.out.println(">>> Getting people query data from API..." + query);
                        String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/search/person?")
                                .queryParam("api_key", apiKey)
                                .queryParam("language", "en")
                                .queryParam("query", query)
                                .queryParam("page", "1")
                                .queryParam("include_adult", "false")
                                .toUriString()
                                .replace("%20", " ");

                        RestTemplate template = new RestTemplate();
                        ResponseEntity<String> resp = null;

                        resp = template.getForEntity(url, String.class);
                        result = Optional.of(getPeopleListJson(resp.getBody()));
                        // add to redis cache
                        movieRepository.addToRedis(query + "-people", result.get());
                    }
                    break;
                default:
                    System.out.println("invalid keyword");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<JsonArray> getGenres() {

        // check from redis cache
        if (movieRepository.checkFromRedis("genres")) {
            System.out.println(">>> Getting genres data from Redis...");
            // get from redis cache
            return movieRepository.getArrFromRedis("genres");
        } else {
            System.out.println(">>> Getting genres data from API...");
            String url = UriComponentsBuilder.fromUriString(
                    "https://api.themoviedb.org/3/genre/movie/list?")
                    .queryParam("api_key", apiKey)
                    .queryParam("language", "en")
                    .toUriString();

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = null;

            try {
                resp = template.getForEntity(url, String.class);
                Optional<JsonArray> result = Optional.of(getGenresJson(resp.getBody()));
                // add to redis cache
                movieRepository.addToRedis("genres", result.get());
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }
    }

    public Optional<JsonArray> getMovieListResp(String url) {

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(url, String.class);
            return Optional.of(getMovieListJson(resp.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
