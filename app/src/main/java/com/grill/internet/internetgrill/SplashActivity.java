package com.grill.internet.internetgrill;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.grill.internet.internetgrill.fragments.HomeFragment;

/**
 * Created by denys on 23.03.17.
 */

public class SplashActivity extends AppCompatActivity {
    private static final int TIME_ACTIVITY_SHOWN = 2 * 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_splash);
        startTimer();
        HomeFragment.isFirstLaunch = true;

//        overridePendingTransition(R.anim.in, R.anim.out);
//        startActivity(new Intent(this, MainActivity.class));
    }

    private void startTimer() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startContent();
            }
        }, TIME_ACTIVITY_SHOWN);
    }

    private void startContent() {
        startActivity(MainActivity.getLaunchIntent(this));
        overridePendingTransition(R.anim.in, R.anim.out);
    }


}
