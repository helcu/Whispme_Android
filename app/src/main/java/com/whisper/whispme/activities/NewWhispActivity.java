package com.whisper.whispme.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.whisper.whispme.R;
import com.whisper.whispme.models.Whisp;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewWhispActivity extends AppCompatActivity {

    TextView recordStatusTextView;

    private MediaRecorder mRecorder;
    private MediaPlayer mMediaplayer;

    private String mFileName = null;


    Button recordWhispButton;

    private ProgressDialog mProgress;

    private static final String LOG_TAG = "Record_log";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_whisp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recordStatusTextView = (TextView) findViewById(R.id.recordStatusTextView);


        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        recordWhispButton = (Button) findViewById(R.id.recordWhispButton);
        recordWhispButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                // Record the whisp

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startWhispRecording();
                    recordStatusTextView.setText("Recording started...");
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    stopWhispRecording();
                    recordStatusTextView.setText("Recording stopped...");
                    // TODO uploadWhisp();
                    startActivity(new Intent(
                            NewWhispActivity.this,
                            NewWhispRecordedActivity.class));
                }
                return false;
            }
        });

        mProgress = new ProgressDialog(this);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/recorded_whisp.mp3";
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        recordStatusTextView.setText("Hold to whisp!");
    }

    private void startWhispRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(getApplicationContext(), "prepare() failed", Toast.LENGTH_LONG).show();
        }


        mRecorder.start();

    }

    private void stopWhispRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    /*private void uploadWhisp() {

        mProgress.setMessage("Uploading audio...");
        mProgress.show();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String audioName = dateFormat.format(new Date());

        // TODO upload whisp to server

    }*/



}
