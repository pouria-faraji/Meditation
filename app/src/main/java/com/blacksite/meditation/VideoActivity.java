package com.blacksite.meditation;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity {

    private final int VIDEO_ACTIVITY_HEADER_TIMEOUT = 4000;//In milliseconds

    FullscreenVideoLayout videoLayout;
    private RelativeLayout close_btn, video_activity_header;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getSupportActionBar().hide();

        videoLayout = (FullscreenVideoLayout) findViewById(R.id.videoview);
        close_btn = (RelativeLayout)findViewById(R.id.video_close_btn);
        video_activity_header = (RelativeLayout)findViewById(R.id.video_activity_header);
        mHandler.postDelayed(hideVideoActivityHeader, VIDEO_ACTIVITY_HEADER_TIMEOUT);
        videoLayout.setActivity(this);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.take10_1_video);
        try {
            videoLayout.setVideoURI(videoUri);
            videoLayout.setShouldAutoplay(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoActivity.this.finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        videoLayout.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                VideoActivity.this.finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        videoLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mHandler.removeCallbacks(hideVideoActivityHeader);
                video_activity_header.setVisibility(View.VISIBLE);
                mHandler.postDelayed(hideVideoActivityHeader, VIDEO_ACTIVITY_HEADER_TIMEOUT);
                return true;
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e("logger", "onPause");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private Runnable hideVideoActivityHeader = new Runnable(){
        public void run(){
            video_activity_header.setVisibility(View.GONE);
        }
    };

}
