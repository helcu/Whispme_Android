package com.whisper.whispme.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.whisper.whispme.R;

public class NewWhispRecordedActivity extends AppCompatActivity {


    Button playWhispButton;
    TextInputEditText whispTitleInputEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_whisp_recorded);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        whispTitleInputEditText = (TextInputEditText) findViewById(R.id.whispTitleInputEditText);
        playWhispButton = (Button) findViewById(R.id.playWhispButton);
        playWhispButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri data = Uri.parse(Environment.getExternalStorageDirectory()
                        .getPath() + "/recorded_whisp.mp3");
                MediaPlayer mp = MediaPlayer.create(v.getContext(), data);
                mp.start();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Create whisp


                startActivity(new Intent(
                        NewWhispRecordedActivity.this,
                        MainViewActivity.class));

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
