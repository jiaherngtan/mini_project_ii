package com.vttp2022.backend.models;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class User {

    private String id;
    private String username;
    private String email;
    private Date created;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email + ", created=" + created + ", imageUrl="
                + imageUrl + "]";
    }

    public static User createUser(SqlRowSet rs) {

        User user = new User();
        user.setId(rs.getString("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setCreated(rs.getDate("created"));
        user.setImageUrl(rs.getString("image_key"));

        return user;
    }

}
