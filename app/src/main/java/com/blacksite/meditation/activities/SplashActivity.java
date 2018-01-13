package com.blacksite.meditation.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blacksite.meditation.R;
import com.blacksite.meditation.view.MyTextView;

public class SplashActivity extends AppCompatActivity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /**
     * Called when the activity is first created.
     */
    Thread splashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        MyTextView iv_title = (MyTextView) findViewById(R.id.splash_title);
        iv_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/B Tabssom_p30download.com.ttf"));
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_screen_splash);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash_img);
        iv.clearAnimation();
        iv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.alpha_title_splash);
        anim.reset();
        MyTextView iv_title = (MyTextView) findViewById(R.id.splash_title);
        iv_title.clearAnimation();
        iv_title.startAnimation(anim);

        splashThread = new Thread() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashActivity.this,
                            WelcomeActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } catch (InterruptedException e) {
                    // do nothing
                    Log.e("logger", "Interrupted");
                } finally {
                    SplashActivity.this.finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        };
        splashThread.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("logger", "onPause");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.e("logger", "onStop");
        if (splashThread.isAlive()) {
            splashThread.interrupt();
        }
    }


    /*@Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("logger", "onDestroy");
        if (splashThread.isAlive()) {
            splashThread.interrupt();
        }
    }*/

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // do something
            Log.e("logger", "onBack");
            if (splashThread.isAlive()) {
                splashThread.interrupt();
            }
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            Log.e("logger", "onHome");
            if (splashThread.isAlive()) {
                splashThread.interrupt();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }*/
}