package com.whisper.whispme.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.whisper.whispme.R;

import java.io.IOException;

public class NewWhispActivity extends AppCompatActivity {

    TextView recordStatusTextView;

    private MediaRecorder mRecorder;
    CountDownTimer recorderCountDownTimer;
    boolean isFirstRecording;

    private String mFileName = null;


    Button recordWhispButton;



    private static final String LOG_TAG = "Record_log";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_whisp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recordStatusTextView = (TextView) findViewById(R.id.recordStatusTextView);

        recordWhispButton = (Button) findViewById(R.id.recordWhispButton);
        recordWhispButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                // Record the whisp
                if (!isFirstRecording) {
                    return false;
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startAudioRecording();
                    //recordStatusTextView.setText("Recording started...");
                    recordWhispButton.setAlpha(0.6f);
                    recorderCountDownTimer.start();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    stopAudioRecording();
                    recordStatusTextView.setText("Recording stopped...");
                    recordWhispButton.setAlpha(1f);
                    recorderCountDownTimer.cancel();

                    startActivity(new Intent(
                            NewWhispActivity.this,
                            NewWhispRecordedActivity.class));
                }
                return false;
            }
        });

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/recorded_whisp.mp3";


        recorderCountDownTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                recordStatusTextView.setText("Recording started..." + millisUntilFinished/1000 + "s");
            }

            public void onFinish() {
                Toast.makeText(getApplicationContext(),
                        "Stop auto",
                        Toast.LENGTH_LONG).show();
                stopAudioRecording();
                recordStatusTextView.setText("Recording stopped...");
                recordWhispButton.setAlpha(1f);
                recorderCountDownTimer.cancel();

                startActivity(new Intent(
                        NewWhispActivity.this,
                        NewWhispRecordedActivity.class));
            }};


        isFirstRecording = true;
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        recordStatusTextView.setText("Hold to whisp!");
        isFirstRecording = true;
    }

    private void startAudioRecording() {
        Toast.makeText(getApplicationContext(), "START",
                Toast.LENGTH_LONG).show();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(getApplicationContext(), "prepare() failed",
                    Toast.LENGTH_LONG).show();
        }
        mRecorder.start();
    }

    private void stopAudioRecording() {
        Toast.makeText(getApplicationContext(), "STOP",
                Toast.LENGTH_LONG).show();

        if(mRecorder==null)
            return;

        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        isFirstRecording = false;
    }




}
