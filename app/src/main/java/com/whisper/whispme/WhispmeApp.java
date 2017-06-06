package com.whisper.whispme;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by profesores on 5/22/17.
 */

public class WhispmeApp extends Application {
    private static WhispmeApp instance;

    public WhispmeApp() {
        super();
        instance = this;
    }

    public static WhispmeApp getInstance() { return instance; }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
