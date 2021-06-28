package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dozetracker.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fadeInTransition();
    }

    private void fadeInTransition() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, OnboardingScreen.class);
            startActivity(intent);
            overridePendingTransition(R.transition.fade_in,R.transition.fade_out);
        },3000);
    }
}