package com.vttp2022.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.vttp2022.backend.models.Notification;

import jakarta.annotation.PostConstruct;

@Service
public class NotificationService {

    @Value("${app.firebase-config-file}")
    private String firebaseConfigPath;

    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase app initialized...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(Notification notification) {
        Message message = Message.builder()
                .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(WebpushNotification.builder()
                                .setTitle(notification.getTitle())
                                .setBody(notification.getMessage())
                                .setIcon("")
                                .build())
                        .build())
                .setToken(notification.getTarget())
                .build();
        FirebaseMessaging.getInstance().sendAsync(message);
    }

}
