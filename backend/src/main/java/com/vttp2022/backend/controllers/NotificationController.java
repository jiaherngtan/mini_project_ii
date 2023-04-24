package com.vttp2022.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vttp2022.backend.models.Notification;
import com.vttp2022.backend.services.NotificationService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping(path = "/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping(path = "/notification")
    @ResponseBody
    @CrossOrigin()
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {

        try {
            notificationService.sendNotification(notification);

            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Notification sent successfully")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        } catch (Exception e) {
            JsonObject result = Json.createObjectBuilder()
                    .add("result", "Notification error")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toString());
        }
    }

}
