package com.whisper.whispme.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.whisper.whispme.R;

public class SplashActivity extends AppCompatActivity {

    final static int SPLASH_OUT_TIMEOUT = 500;
    final static int SLEEP_INTERVAL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


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

                    // TODO if user isLogged -> gotoMainViewActivity

                    startActivity(
                            new Intent(SplashActivity.this,
                                    MainActivity.class));
                    finish();
                }
            }
        };
        splashThread.start();
    }
}
