package com.vttp2022.backend.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vttp2022.backend.models.User;
import com.vttp2022.backend.repositories.ReviewRepository;
import com.vttp2022.backend.repositories.UserRepository;
import com.vttp2022.backend.repositories.WatchlistRepository;

import jakarta.json.JsonArray;

@Service
public class UserService {

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(rollbackFor = { Exception.class })
    public User register(User user, String key) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        user.setId(id);
        // add the signup timestamp
        user.setCreated(new Date());

        // save the user to sql
        userRepository.register(user, key);

        return user;
    }

    @Transactional(rollbackFor = { Exception.class })
    public User delete(String email) {
        User user = getUserDetails(email);

        // delete the user from sql with transactions -> delete reviews -> delete
        // watchlist -> delete user
        JsonArray reviewJa = reviewRepository.getReviews(email).get();
        if (null != reviewJa && reviewJa.size() > 0)
            reviewRepository.deleteAllReview(email);
        JsonArray watchlistPendJa = watchlistRepository.getWatchlist(user.getId(), "pend").get();
        JsonArray watchlistDoneJa = watchlistRepository.getWatchlist(user.getId(), "done").get();
        Integer watchlistLength = watchlistPendJa.size() + watchlistDoneJa.size();
        if (watchlistLength > 0)
            watchlistRepository.deleteWatchlist(email);
        userRepository.deleteUser(email);

        return user;
    }

    public User getUserDetails(String email) {
        return userRepository.getUserDetails(email);
    }

}
