package com.whisper.whispme.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.whisper.whispme.R;
import com.whisper.whispme.fragments.WhispPlayerFragment;
import com.whisper.whispme.fragments.WhispsFragment;
import com.whisper.whispme.helpers.UUIDGeneratorHelper;
import com.whisper.whispme.models.Whisp;
import com.whisper.whispme.network.WhispmeApi;
import com.whisper.whispme.network.WhispmeApiInterface;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewWhispRecordedActivity extends AppCompatActivity {


    Button playWhispButton;
    TextInputEditText whispTitleInputEditText;

    WhispmeApi whispmeApi;

    private ProgressDialog mProgress;

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

                WhispPlayerFragment fragment = new WhispPlayerFragment();
                fragment.show(getSupportFragmentManager(), "Player");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Create whisp
                // TODO Upload whisp to server

                uploadWhisp();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        whispmeApi = new WhispmeApi();
        whispmeApi.uploadWhispListener = new WhispmeApiInterface.UploadWhispListener() {
            @Override
            public void onEventCompleted(String apiResponse) {
                mProgress.dismiss();

                Toast.makeText(getApplicationContext(),
                        apiResponse,
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(
                        NewWhispRecordedActivity.this,
                        MainViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }

            @Override
            public void onEventFailed(String apiResponse) {
                Toast.makeText(getApplicationContext(),
                        apiResponse,
                        Toast.LENGTH_SHORT).show();
            }
        };


        mProgress = new ProgressDialog(this);
    }


    // TODO upload whisp to server
    private void uploadWhisp() {

        mProgress.setMessage("Creating your whisp!");
        mProgress.show();

        StorageReference mStorage = FirebaseStorage.getInstance().getReference();

        final String audioName = UUIDGeneratorHelper.generateId();
        StorageReference filepath = mStorage.child("whisps_audios").child(audioName + ".mp3");

        Uri recordedWhispUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                .getPath() + "/recorded_whisp.mp3"));

        filepath.putFile(recordedWhispUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        SharedPreferences prefs =
                                getSharedPreferences("WhispmeSP", Context.MODE_PRIVATE);
                        int userId = prefs.getInt(getString(R.string.sp_user_id), 0);

                        Toast.makeText(NewWhispRecordedActivity.this,
                                String.valueOf(userId),
                                Toast.LENGTH_LONG).show();

                        Whisp whisp = new Whisp();
                        whisp.setUserId(userId)
                                .setUrlAudio(audioName)
                                .setTitle("title")
                                .setDescription("description")
                                .setUrlPhoto("urlPhoto")
                                .setDateCreation(new Date())
                                .setLatitude(WhispsFragment.LATITUDE)
                                .setLongitude(WhispsFragment.LONGITUDE)
                                .setPlace("place");

                        whispmeApi.uploadWhisp(whisp);
                    }
                });

    }
}
