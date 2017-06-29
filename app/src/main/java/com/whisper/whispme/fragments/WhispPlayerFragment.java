package com.whisper.whispme.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.whisper.whispme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhispPlayerFragment extends DialogFragment {

    MediaPlayer mediaPlayer;
    Uri recordedWhispUri = Uri.parse(Environment.getExternalStorageDirectory()
            .getPath() + "/recorded_whisp.mp3");

    SeekBar whispSeekBar;
    Handler handler = new Handler();
    Runnable updateTime;

    public WhispPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE,
                0);//R.style.whispPlayerDialogStyle);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_whisp_player, container, false);

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);


        whispSeekBar = (SeekBar) view.findViewById(R.id.whispSeekBar);

        updateTime = new Runnable() {
            public void run() {
                whispSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 100);
            }
        };

        mediaPlayer = MediaPlayer.create(getContext(), recordedWhispUri);
        mediaPlayer.start();
        whispSeekBar.setMax(mediaPlayer.getDuration());
        whispSeekBar.setProgress(mediaPlayer.getCurrentPosition());
        handler.postDelayed(updateTime, 100);


        view.findViewById(R.id.playButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {

                    mediaPlayer.stop();
                    mediaPlayer.release();
                    handler.removeCallbacks(updateTime);
                }

                mediaPlayer = MediaPlayer.create(getContext(), recordedWhispUri);
                mediaPlayer.start();
                whispSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(updateTime, 100);
            }
        });

        return view;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        mediaPlayer.stop();
        mediaPlayer.release();

        handler.removeCallbacks(updateTime);

        super.onDismiss(dialog);
    }
}
