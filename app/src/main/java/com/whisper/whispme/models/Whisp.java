package com.whisper.whispme.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by NightmareTK on 19/05/2017.
 */

public class Whisp {
    private int whispId;
    private int userId;
    private String urlAudio;
    private String title;
    private String description;
    private String urlPhoto;
    private Date dateCreation;
    private float latitude;
    private float longitude;
    private String place;

    public Whisp() {
    }

    public Whisp(int whispId, int userId, String urlAudio, String title, String description, String urlPhoto, Date dateCreation, float latitude, float longitude, String place) {
        this.whispId = whispId;
        this.userId = userId;
        this.urlAudio = urlAudio;
        this.title = title;
        this.description = description;
        this.urlPhoto = urlPhoto;
        this.dateCreation = dateCreation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.place = place;
    }

    public int getWhispId() {
        return whispId;
    }

    public Whisp setWhispId(int whispId) {
        this.whispId = whispId;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Whisp setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public Whisp setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Whisp setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Whisp setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public Whisp setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
        return this;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Whisp setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public float getLatitude() {
        return latitude;
    }

    public Whisp setLatitude(float latitude) {
        this.latitude = latitude;
        return this;
    }

    public float getLongitude() {
        return longitude;
    }

    public Whisp setLongitude(float longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getPlace() {
        return place;
    }

    public Whisp setPlace(String place) {
        this.place = place;
        return this;
    }


    public static Whisp build(JSONObject jsonSource) {
        Whisp whisp = new Whisp();
        try {
            whisp.setWhispId(jsonSource.getInt("whispId"))
                    .setUserId(jsonSource.getInt("UserId"))
                    .setUrlAudio(jsonSource.getString("urlAudio"))
                    .setTitle(jsonSource.getString("title"))
                    .setDescription(jsonSource.getString("description"))
                    .setUrlPhoto(jsonSource.getString("urlPhoto"))
                    .setDateCreation((new SimpleDateFormat("dd-MMM-yyyy"))
                            .parse(jsonSource.getString("dateCreation")))
                    .setLatitude(Float.parseFloat(jsonSource.getString("latitude")))
                    .setLongitude(Float.parseFloat(jsonSource.getString("longitude")))
                    .setPlace(jsonSource.getString("place"));
            return whisp;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Whisp> build(JSONArray jsonSources) {
        List<Whisp> whisps = new ArrayList<>();
        for (int i = 0; i < jsonSources.length(); i++)
            try {
                whisps.add(build(jsonSources.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return whisps;
    }


    public static Whisp buildNearWhisps(JSONObject jsonSource) {
        Whisp whisp = new Whisp();
        try {
            return whisp.setWhispId(jsonSource.getInt("IdWhisp"))
                    .setLatitude(Float.parseFloat(jsonSource.getString("Latitude")))
                    .setLongitude(Float.parseFloat(jsonSource.getString("Longitude")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Whisp> buildNearWhisps(JSONArray jsonSources) {
        List<Whisp> whisps = new ArrayList<>();
        for (int i = 0; i < jsonSources.length(); i++) {
            try {
                whisps.add(buildNearWhisps(jsonSources.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return whisps;
    }

}
