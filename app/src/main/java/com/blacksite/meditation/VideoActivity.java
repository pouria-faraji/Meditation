package com.blacksite.meditation;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity {

    FullscreenVideoLayout videoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getSupportActionBar().hide();

        videoLayout = (FullscreenVideoLayout) findViewById(R.id.videoview);
        videoLayout.setActivity(this);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.take10_1_video);
        try {
            videoLayout.setVideoURI(videoUri);
            videoLayout.setShouldAutoplay(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
