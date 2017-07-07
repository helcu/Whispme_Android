package com.whisper.whispme.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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


    public static User build(JSONObject jsonSource) {
        User user = new User();
        try {
            return user.setUserId(jsonSource.getInt("idUser"))
                    .setUsername(jsonSource.getString("userName"))
                    .setUrlPhoto(jsonSource.getString("urlPhoto"))
                    .setDescription(jsonSource.getString("description"))
                    .setEmail(jsonSource.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<User> build(JSONArray jsonSources) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < jsonSources.length(); i++)
            try {
                users.add(build(jsonSources.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return users;
    }
}
