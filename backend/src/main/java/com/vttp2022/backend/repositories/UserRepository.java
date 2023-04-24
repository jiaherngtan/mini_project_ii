package com.vttp2022.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.vttp2022.backend.models.User;

import static com.vttp2022.backend.repositories.Queries.*;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void register(User user, String key) {
        jdbcTemplate.update(SQL_INSERT_USER,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreated(),
                key);
    }

    public void deleteUser(String email) {
        jdbcTemplate.update(SQL_DELETE_USER, email);
    }

    public User getUserDetails(String email) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_USER_BY_EMAIL, email);
        User user = new User();
        while (rs.next()) {
            user.setId(rs.getString("id"));
            user.setUsername(rs.getString("username"));
            user.setCreated(rs.getDate("created"));
            user.setImageUrl(rs.getString("image_key"));
        }
        return user;
    }

}
