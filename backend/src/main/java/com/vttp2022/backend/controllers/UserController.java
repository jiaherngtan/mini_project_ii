package com.vttp2022.backend.controllers;

import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vttp2022.backend.models.User;
import com.vttp2022.backend.services.S3Service;
import com.vttp2022.backend.services.UserService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private S3Service s3Service;

    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> register(
            @RequestPart MultipartFile myImage,
            @RequestPart String username,
            @RequestPart String email) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        System.out.println(user);

        // file upload
        String key = "";

        try {
            key = s3Service.upload(username, myImage);
            user = userService.register(user, key);

            JsonObject result = Json.createObjectBuilder()
                    .add("result", "User registered successfully")
                    .add("id", user.getId())
                    .add("username", user.getUsername())
                    .add("email", user.getEmail())
                    .add("imageKey", key)
                    .add("created", user.getCreated().toString())
                    .build();

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "User registration error")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @DeleteMapping(path = "/delete/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<String> deleteUser(@PathVariable String email) {

        try {
            User user = userService.delete(email);

            JsonObject result = Json.createObjectBuilder()
                    .add("result", "User deleted successfully")
                    .add("id", user.getId())
                    .add("username", user.getUsername())
                    .add("email", user.getEmail())
                    .add("imageKey", user.getImageUrl())
                    .add("created", user.getCreated().toString())
                    .add("deleted", (new Date()).toString())
                    .build();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "User deletion error")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

    @GetMapping(path = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> getUserDetails(@PathVariable String email) {
        try {
            User user = userService.getUserDetails(email);
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Username retrieval succeeded")
                    .add("id", user.getId())
                    .add("username", user.getUsername())
                    .add("created", user.getCreated().toString())
                    .add("imageUrl", user.getImageUrl())
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Error in retrieving username")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

}
