package com.whisper.whispme.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.whisper.whispme.R;

public class SplashActivity extends AppCompatActivity {

    final static int SPLASH_OUT_TIMEOUT = 3000;
    final static int SLEEP_INTERVAL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_splash);


        Thread splashThread = new Thread() {
            int wait = 0;
            @Override
            public void run() {
                try {
                    super.run();
                    while (wait < SPLASH_OUT_TIMEOUT) {
                        sleep(SLEEP_INTERVAL);
                        wait += SLEEP_INTERVAL;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(
                            new Intent(SplashActivity.this,
                                    LoginActivity.class));
                    finish();
                }
            }
        };
        splashThread.start();
    }
}
