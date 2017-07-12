package com.blacksite.meditation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.tankery.lib.circularseekbar.CircularSeekBar;
import nl.changer.audiowife.AudioWife;


public class AudioActivity extends AppCompatActivity {//} implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private final int VIDEO_ACTIVITY_REQUEST_CODE = 555;

    private MediaPlayer mp;

    private View play_button, pause_button;
    private TextView audio_runtime;

    private CircularSeekBar seekBar;
    private int audio_length;
    private int audio_current_position = 0;
    private Handler mHandler = new Handler();
    private String audio_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        seekBar = (CircularSeekBar)findViewById(R.id.audio_player_seek_bar);
        play_button = findViewById(R.id.audio_play);
        pause_button = findViewById(R.id.audio_pause);
        audio_runtime = (TextView)findViewById(R.id.audio_runtime);

        audio_uri = "android.resource://" + getPackageName() + "/" + R.raw.take10_1_audio;

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playClicked();
            }
        });

        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseClicked();
            }
        });

        Intent video_intent = new Intent(this, VideoActivity.class);
        this.startActivityForResult(video_intent, VIDEO_ACTIVITY_REQUEST_CODE);
    }
    public void playClicked(){
        play_button.setVisibility(View.GONE);
        pause_button.setVisibility(View.VISIBLE);
        mp.seekTo(audio_current_position);
        mp.start();
        mHandler.postDelayed(UpdateProgressBar, 100);
    }
    public void pauseClicked(){
        play_button.setVisibility(View.VISIBLE);
        pause_button.setVisibility(View.GONE);
        audio_current_position = mp.getCurrentPosition();
        mp.pause();
        mHandler.removeCallbacks(UpdateProgressBar);
    }
    public void prepareAndPlay(String uri){
        mp = MediaPlayer.create(this, Uri.parse(uri));
        seekBar.setMax(mp.getDuration());
        mp.start();
        play_button.setVisibility(View.GONE);
        pause_button.setVisibility(View.VISIBLE);
        mHandler.postDelayed(UpdateProgressBar, 100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == VIDEO_ACTIVITY_REQUEST_CODE){
            prepareAndPlay(audio_uri);

            seekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
                @Override
                public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                }

                @Override
                public void onStopTrackingTouch(CircularSeekBar seekBar) {
                    // remove message Handler from updating progress bar
                    mHandler.removeCallbacks(UpdateProgressBar);
                    int currentSeekBarPosition = (int)seekBar.getProgress();
                    mp.seekTo(currentSeekBarPosition);
                    mHandler.postDelayed(UpdateProgressBar, 100);
                }

                @Override
                public void onStartTrackingTouch(CircularSeekBar seekBar) {
                    mHandler.removeCallbacks(UpdateProgressBar);
                }
            });

        }
    }
    private Runnable UpdateProgressBar = new Runnable() {
        public void run() {
            audio_current_position = mp.getCurrentPosition();
            audio_runtime.setText(millisecondsToTimer(audio_current_position));
            seekBar.setProgress(audio_current_position);
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onPause(){
        super.onPause();
        if(mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
            }
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
        }
        mHandler.removeCallbacks(UpdateProgressBar);
        seekBar.setProgress(0);
    }
    public String millisecondsToTimer(long milliseconds){
        String finalString = "";
        String secondsString = "";
        String minutesString = "";

        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;
        }
        // Prepending 0 to minutes if it is one digit
        if(minutes < 10){
            minutesString = "0" + minutes;
        }else{
            minutesString = "" + minutes;
        }

        finalString = minutesString + ":" + secondsString;
        return finalString;
    }
}
