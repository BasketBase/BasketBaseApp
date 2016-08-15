package com.base.basket.basketbase1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.base.basket.basketbase1.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends Activity {
    private static final long SPLASH_SCREEN_DELAY =(long) (Math.random()+1) * 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screensplash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent in = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(in);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}