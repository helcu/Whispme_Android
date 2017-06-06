package com.whisper.whispme.models;

/**
 * Created by NightmareTK on 31/05/2017.
 */

public class User {
    private int userId;
    private String username;
    private String urlPhoto;
    private String email;
    private String description;

    public User() {
    }

    public User(int userId, String username, String urlPhoto, String email, String description) {
        this.userId = userId;
        this.username = username;
        this.urlPhoto = urlPhoto;
        this.email = email;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public User setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public User setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public User setDescription(String description) {
        this.description = description;
        return this;
    }
}
