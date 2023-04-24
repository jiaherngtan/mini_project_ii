package com.vttp2022.backend;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.vttp2022.backend.models.Movie;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Utils {

    public static JsonArray getMovieListJson(String json) throws IOException {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray ja = !jo.containsKey("results") ? arrBuilder.build() : jo.getJsonArray("results");

            for (int i = 0; i < ja.size(); i++) {
                JsonObject item = ja.getJsonObject(i);
                // System.out.println(">>> item: " + item);

                String id = !item.containsKey("id") ? ""
                        : item.isNull("id") ? "" : item.getJsonNumber("id").toString();
                String title = !item.containsKey("title") ? ""
                        : item.isNull("title") ? "" : item.getString("title");
                String overview = !item.containsKey("overview") ? ""
                        : item.isNull("overview") ? "" : item.getString("overview");
                String posterUrl = !item.containsKey("poster_path") ? ""
                        : item.isNull("poster_path") ? "" : item.getString("poster_path");
                String backdropUrl = !item.containsKey("backdrop_path") ? ""
                        : item.isNull("backdrop_path") ? "" : item.getString("backdrop_path");
                String releaseDate = !item.containsKey("release_date") ? ""
                        : item.isNull("release_date") ? "" : item.getString("release_date");
                Float rating = !item.containsKey("vote_average") ? 0
                        : item.isNull("vote_average") ? 0
                                : item.getJsonNumber("vote_average").bigDecimalValue()
                                        .floatValue();
                Integer scale = (int) Math.pow(10, 1);
                rating = (float) Math.round(rating * scale) / scale;
                BigDecimal ratingCountBigDec = !item.containsKey("vote_count") ? BigDecimal.ZERO
                        : item.isNull("vote_count") ? BigDecimal.ZERO
                                : item.getJsonNumber("vote_count").bigDecimalValue();
                String ratingCount = String.format("%1$,.0f", ratingCountBigDec);

                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                        .add("id", id)
                        .add("title", title)
                        .add("overview", overview)
                        .add("releaseDate", releaseDate)
                        .add("posterUrl", posterUrl)
                        .add("backdropUrl", backdropUrl)
                        .add("rating", rating)
                        .add("ratingCount", ratingCount);
                arrBuilder.add(objBuilder);
            }
        }
        return arrBuilder.build();
    }

    public static JsonArray getPeopleListJson(String json) throws IOException {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray ja = !jo.containsKey("results") ? arrBuilder.build() : jo.getJsonArray("results");
            // System.out.println(ja);

            for (int i = 0; i < ja.size(); i++) {

                JsonObject item = ja.getJsonObject(i);

                String id = !item.containsKey("id") ? ""
                        : item.isNull("id") ? "" : item.getJsonNumber("id").toString();
                String role = !item.containsKey("known_for_department") ? ""
                        : item.isNull("known_for_department") ? ""
                                : item.getString("known_for_department");
                String name = !item.containsKey("name") ? ""
                        : item.isNull("name") ? "" : item.getString("name");
                String url = !item.containsKey("profile_path") ? ""
                        : item.isNull("profile_path") ? ""
                                : item.get("profile_path").toString().replaceAll("\"",
                                        "");

                JsonArrayBuilder movieArrBuilder = Json.createArrayBuilder();
                JsonArray movieArr = item.isNull("known_for") ? movieArrBuilder.build()
                        : item.getJsonArray("known_for");
                for (int j = 0; j < movieArr.size(); j++) {
                    JsonObject movieObj = movieArr.getJsonObject(j);
                    String title = "";
                    if (!movieObj.containsKey("title"))
                        title = !movieObj.containsKey("name") ? ""
                                : movieObj.isNull("name") ? ""
                                        : movieObj.getString("name");
                    else
                        title = !movieObj.containsKey("title") ? ""
                                : movieObj.isNull("title") ? ""
                                        : movieObj.getString("title");
                    Float rating = !movieObj.containsKey("vote_average") ? 0
                            : movieObj.isNull("vote_average") ? 0
                                    : movieObj.getJsonNumber("vote_average")
                                            .bigDecimalValue().floatValue();
                    Integer scale = (int) Math.pow(10, 1);
                    rating = (float) Math.round(rating * scale) / scale;
                    BigDecimal ratingCountBigDec = !movieObj.containsKey("vote_count")
                            ? BigDecimal.ZERO
                            : movieObj.isNull("vote_count") ? BigDecimal.ZERO
                                    : movieObj.getJsonNumber("vote_count")
                                            .bigDecimalValue();
                    String ratingCount = String.format("%1$,.0f", ratingCountBigDec);

                    JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                            .add("id", !movieObj.containsKey("id") ? ""
                                    : movieObj.isNull("id") ? ""
                                            : movieObj.getJsonNumber("id")
                                                    .toString())
                            .add("title", title)
                            .add("overview", !movieObj.containsKey("overview") ? ""
                                    : movieObj.isNull("overview") ? ""
                                            : movieObj.getString(
                                                    "overview"))
                            .add("releaseDate", !movieObj.containsKey("release_date") ? ""
                                    : movieObj.isNull("release_date") ? ""
                                            : movieObj.getString(
                                                    "release_date"))
                            .add("posterUrl", !movieObj.containsKey("poster_path") ? ""
                                    : movieObj.isNull("poster_path") ? ""
                                            : movieObj.getString(
                                                    "poster_path"))
                            .add("backdropUrl", !movieObj.containsKey("backdrop_path") ? ""
                                    : movieObj.isNull("backdrop_path") ? ""
                                            : movieObj.getString(
                                                    "backdrop_path"))
                            .add("rating", rating)
                            .add("ratingCount", ratingCount);
                    movieArrBuilder.add(objBuilder);
                }
                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                        .add("id", id)
                        .add("role", role)
                        .add("name", name)
                        .add("pictureUrl", url)
                        .add("movies", movieArrBuilder.build());
                arrBuilder.add(objBuilder);
            }
        }
        return arrBuilder.build();
    }

    public static JsonObject getMovieJson(String json) throws IOException {

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject item = jr.readObject();
            // genres
            JsonArray genres = Json.createArrayBuilder().build();
            JsonArray genresFull = !item.containsKey("genres") ? genres
                    : item.isNull("genres") ? genres : item.getJsonArray("genres");
            genres = getArrItemJson(genresFull);
            // countries
            JsonArray countries = Json.createArrayBuilder().build();
            JsonArray countriesFull = !item.containsKey("production_countries") ? countries
                    : item.isNull("production_countries") ? countries
                            : item.getJsonArray("production_countries");
            countries = getArrItemJson(countriesFull);
            // languages
            JsonArray languages = Json.createArrayBuilder().build();
            JsonArray languagesFull = !item.containsKey("spoken_languages") ? languages
                    : item.isNull("spoken_languages") ? languages
                            : item.getJsonArray("spoken_languages");
            languages = getArrItemJson(languagesFull);

            String id = !item.containsKey("id") ? ""
                    : item.isNull("id") ? "" : item.getJsonNumber("id").toString();
            String imdbId = !item.containsKey("imdb_id") ? ""
                    : item.isNull("imdb_id") ? "" : item.getString("imdb_id");
            String title = !item.containsKey("title") ? ""
                    : item.isNull("title") ? "" : item.getString("title");
            String tagline = !item.containsKey("tagline") ? ""
                    : item.isNull("tagline") ? "" : item.getString("tagline");
            String overview = !item.containsKey("overview") ? ""
                    : item.isNull("overview") ? "" : item.getString("overview");
            String posterUrl = !item.containsKey("poster_path") ? ""
                    : item.isNull("poster_path") ? "" : item.getString("poster_path");
            String backdropUrl = !item.containsKey("backdrop_path") ? ""
                    : item.isNull("backdrop_path") ? "" : item.getString("backdrop_path");
            String releaseDate = !item.containsKey("release_date") ? ""
                    : item.isNull("release_date") ? "" : item.getString("release_date");
            String[] releaseDateArr = releaseDate.split("-");
            String releaseYear = releaseDateArr[0];
            Integer runtime = !item.containsKey("runtime") ? 0
                    : item.isNull("runtime") ? 0 : item.getInt("runtime");
            Float rating = !item.containsKey("vote_average") ? 0
                    : item.isNull("vote_average") ? 0
                            : item.getJsonNumber("vote_average").bigDecimalValue()
                                    .floatValue();
            Integer scale = (int) Math.pow(10, 1);
            rating = (float) Math.round(rating * scale) / scale;
            BigDecimal ratingCountBigDec = !item.containsKey("vote_count") ? BigDecimal.ZERO
                    : item.isNull("vote_count") ? BigDecimal.ZERO
                            : item.getJsonNumber("vote_count").bigDecimalValue();
            String ratingCount = String.format("%1$,.0f", ratingCountBigDec);

            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("id", id)
                    .add("imdbId", imdbId)
                    .add("imdbUrl", "https://www.imdb.com/title/" + imdbId)
                    .add("title", title)
                    .add("tagline", tagline)
                    .add("overview", overview)
                    .add("runtime", runtime)
                    .add("releaseDate", releaseDate)
                    .add("releaseYear", releaseYear)
                    .add("posterUrl", posterUrl)
                    .add("backdropUrl", backdropUrl)
                    .add("rating", rating)
                    .add("ratingCount", ratingCount)
                    .add("genres", genres)
                    .add("languages", languages)
                    .add("countries", countries);

            return objBuilder.build();
        }
    }

    public static JsonObject getPeopleJson(String json) throws IOException {

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject item = jr.readObject();

            String id = !item.containsKey("id") ? ""
                    : item.isNull("id") ? "" : item.getJsonNumber("id").toString();
            String role = !item.containsKey("known_for_department") ? ""
                    : item.isNull("known_for_department") ? ""
                            : item.getString("known_for_department");
            String name = !item.containsKey("name") ? ""
                    : item.isNull("name") ? "" : item.getString("name");
            String pictureUrl = !item.containsKey("profile_path") ? ""
                    : item.isNull("profile_path") ? "" : item.getString("profile_path");
            String biography = !item.containsKey("biography") ? ""
                    : item.isNull("biography") ? "" : item.getString("biography");
            String birthday = !item.containsKey("birthday") ? ""
                    : item.isNull("birthday") ? "" : item.getString("birthday");
            String place = !item.containsKey("place_of_birth") ? ""
                    : item.isNull("place_of_birth") ? "" : item.getString("place_of_birth");

            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("id", id)
                    .add("role", role)
                    .add("name", name)
                    .add("pictureUrl", pictureUrl)
                    .add("biography", biography)
                    .add("birthday", birthday)
                    .add("place", place);

            return objBuilder.build();
        }
    }

    public static JsonArray getCrewByMovieJson(String json) throws IOException {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray ja = !jo.containsKey("cast") ? arrBuilder.build() : jo.getJsonArray("cast");

            Integer cast = 0;
            Integer director = 0;
            Integer i = 0;

            while (i < ja.size() && (cast < 6 || director < 2)) {
                JsonObject item = ja.getJsonObject(i);
                if (item.containsKey("known_for_department") && !item.isNull("known_for_department")
                        && item.getString("known_for_department").equals("Acting")) {
                    if (cast < 6) {
                        String url = !item.containsKey("profile_path") ? ""
                                : item.isNull("profile_path") ? ""
                                        : item.get("profile_path").toString()
                                                .replaceAll("\"", "");
                        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                                .add("id",
                                        !item.containsKey("id") ? ""
                                                : item.isNull("id") ? ""
                                                        : item.getJsonNumber(
                                                                "id")
                                                                .toString())
                                .add("role", "Cast")
                                .add("name",
                                        !item.containsKey("name") ? ""
                                                : item.isNull("name")
                                                        ? ""
                                                        : item.getString(
                                                                "name"))
                                .add("pictureUrl", url);
                        arrBuilder.add(objBuilder);
                        cast++;
                    }
                }
                if (item.containsKey("known_for_department") && !item.isNull("known_for_department")
                        && item.getString("known_for_department").equals("Directing")) {
                    if (director < 2) {
                        String url = !item.containsKey("profile_path") ? ""
                                : item.isNull("profile_path") ? ""
                                        : item.get("profile_path").toString()
                                                .replaceAll("\"", "");
                        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                                .add("id",
                                        !item.containsKey("id") ? ""
                                                : item.isNull("id") ? ""
                                                        : item.getJsonNumber(
                                                                "id")
                                                                .toString())
                                .add("role", "Director")
                                .add("name",
                                        !item.containsKey("name") ? ""
                                                : item.isNull("name")
                                                        ? ""
                                                        : item.getString(
                                                                "name"))
                                .add("pictureUrl", url);
                        arrBuilder.add(objBuilder);
                        director++;
                    }
                }
                i++;
            }
        }
        return arrBuilder.build();
    }

    public static JsonArray getArrItemJson(JsonArray ja) throws IOException {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (int i = 0; i < ja.size(); i++) {
            JsonObject item = ja.getJsonObject(i);
            String name = !item.containsKey("name") ? ""
                    : item.isNull("name") ? "" : item.getString("name");
            arrBuilder.add(name);
        }
        return arrBuilder.build();
    }

    public static JsonArray getGenresJson(String json) throws IOException {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray ja = !jo.containsKey("genres") ? arrBuilder.build()
                    : jo.isNull("genres") ? arrBuilder.build() : jo.getJsonArray("genres");

            for (int i = 0; i < ja.size(); i++) {
                JsonObject item = ja.getJsonObject(i);

                Integer id = !item.containsKey("id") ? -1
                        : item.isNull("id") ? -1 : item.getJsonNumber("id").intValue();
                String name = !item.containsKey("name") ? ""
                        : item.isNull("name") ? "" : item.getString("name");

                JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                        .add("id", id)
                        .add("name", name);
                arrBuilder.add(objBuilder);
            }
        }
        return arrBuilder.build();
    }

    // public void toMap(Optional<JsonArray> opt) {

    // JsonArray ja = opt.get();
    // for (int i = 0; i < ja.size(); i++) {
    // JsonObject item = ja.getJsonObject(i);
    // genresMap.put(item.getInt("id"), item.getString("name"));
    // }
    // }

    public static JsonArray getMovieListRowSet(SqlRowSet rs) throws IOException {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        while (rs.next()) {
            String tagline = rs.getString("tagline");
            tagline = rs.wasNull() ? "" : rs.getString("tagline");
            String overview = rs.getString("overview");
            overview = rs.wasNull() ? "" : rs.getString("overview");
            String releaseYear = rs.getString("release_year");
            releaseYear = rs.wasNull() ? "" : rs.getString("release_year");
            String posterUrl = rs.getString("release_year");
            posterUrl = rs.wasNull() ? "" : rs.getString("poster_url");
            Float rating = rs.getFloat("rating");
            rating = rs.wasNull() ? -1 : rs.getFloat("rating");
            String countries = rs.getString("countries");
            countries = rs.wasNull() ? "" : rs.getString("countries");
            String[] countryArr = countries.split(",");
            JsonArrayBuilder countryArrBuilder = Json.createArrayBuilder();
            for (String country : countryArr) {
                country = country.replaceAll("\"", "");
                countryArrBuilder.add(country);
            }
            String added = rs.getDate("added").toString();
            added = rs.wasNull() ? "" : rs.getDate("added").toString();
            Integer reviewRating = rs.getInt("review_rating");
            reviewRating = rs.wasNull() ? -1 : rs.getInt("review_rating");
            String text = rs.getString("review_text");
            text = rs.wasNull() ? "" : rs.getString("review_text");
            String reviewed = rs.wasNull() ? "" : rs.getDate("review_added").toString();
            reviewed = rs.wasNull() ? "" : rs.getDate("review_added").toString();

            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("id", rs.getString("movie_id"))
                    .add("title", rs.getString("title"))
                    .add("tagline", tagline)
                    .add("overview", overview)
                    .add("releaseYear", releaseYear)
                    .add("posterUrl", posterUrl)
                    .add("rating", rating)
                    .add("countries", countryArrBuilder.build())
                    .add("added", added)
                    .add("reviewRating", reviewRating)
                    .add("text", text)
                    .add("reviewed", reviewed);
            arrBuilder.add(objBuilder);
        }
        return arrBuilder.build();
    }

    public static JsonArray getReviewsRowSet(SqlRowSet rs) throws IOException {

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        while (rs.next()) {
            String tagline = rs.getString("tagline");
            tagline = rs.wasNull() ? "" : rs.getString("tagline");
            String overview = rs.getString("overview");
            overview = rs.wasNull() ? "" : rs.getString("overview");
            String releaseYear = rs.getString("release_year");
            releaseYear = rs.wasNull() ? "" : rs.getString("release_year");
            String posterUrl = rs.getString("release_year");
            posterUrl = rs.wasNull() ? "" : rs.getString("poster_url");
            Float rating = rs.getFloat("rating");
            rating = rs.wasNull() ? -1 : rs.getFloat("rating");
            String countries = rs.getString("countries");
            countries = rs.wasNull() ? "" : rs.getString("countries");
            String[] countryArr = countries.split(",");
            JsonArrayBuilder countryArrBuilder = Json.createArrayBuilder();
            for (String country : countryArr) {
                country = country.replaceAll("\"", "");
                countryArrBuilder.add(country);
            }
            String added = rs.getDate("added").toString();
            added = rs.wasNull() ? "" : rs.getDate("added").toString();
            String reviewed = rs.wasNull() ? "" : rs.getDate("review_added").toString();
            reviewed = rs.wasNull() ? "" : rs.getDate("review_added").toString();

            JsonObjectBuilder movieObjBuilder = Json.createObjectBuilder()
                    .add("id", rs.getString("movie_id"))
                    .add("title", rs.getString("title"))
                    .add("tagline", tagline)
                    .add("overview", overview)
                    .add("releaseYear", releaseYear)
                    .add("posterUrl", posterUrl)
                    .add("rating", rating)
                    .add("countries", countryArrBuilder.build())
                    .add("added", added)
                    .add("reviewed", reviewed);

            Integer reviewRating = rs.getInt("review_rating");
            reviewRating = rs.wasNull() ? -1 : rs.getInt("review_rating");
            String text = rs.getString("review_text");
            text = rs.wasNull() ? "" : rs.getString("review_text");

            JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("email", rs.getString("email"))
                    .add("movie", movieObjBuilder)
                    .add("reviewRating", reviewRating)
                    .add("text", text);
            arrBuilder.add(objBuilder);
        }
        return arrBuilder.build();
    }

    public static Movie createMovieFromJson(JsonObject movieJo) {
        Movie movie = new Movie();
        movie.setId(movieJo.getString("id"));
        movie.setTitle(movieJo.getString("title"));
        movie.setTagline(movieJo.getString("tagline"));
        movie.setOverview(movieJo.getString("overview"));
        movie.setReleaseYear(movieJo.getString("releaseYear"));
        movie.setPosterUrl(movieJo.getString("posterUrl"));
        movie.setRating(Float.parseFloat(movieJo.getJsonNumber("rating").toString()));
        JsonArray countriesJa = movieJo.getJsonArray("countries");
        List<String> countries = new LinkedList<>();
        for (JsonValue country : countriesJa) {
            countries.add(country.toString());
        }
        movie.setCountries(countries);
        return movie;
    }

}
