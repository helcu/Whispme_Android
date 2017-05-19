package com.whisper.whispme.models;

import java.util.Date;

/**
 * Created by NightmareTK on 19/05/2017.
 */

public class Whisp {
    private int userId;
    private String title;
    private Date dateCreation;
    private float latitude;
    private float longitude;
    private String place;
    private String urlPhoto;
    private String urlAudio;

    public Whisp() {
    }

    public Whisp(int userId, String title, Date dateCreation, float latitude, float longitude, String place, String urlPhoto, String urlAudio) {
        this.userId = userId;
        this.title = title;
        this.dateCreation = dateCreation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.place = place;
        this.urlPhoto = urlPhoto;
        this.urlAudio = urlAudio;
    }

    public int getUserId() {
        return userId;
    }

    public Whisp setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Whisp setTitle(String title) {
        this.title = title;
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

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public Whisp setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
        return this;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public Whisp setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
        return this;
    }
}
