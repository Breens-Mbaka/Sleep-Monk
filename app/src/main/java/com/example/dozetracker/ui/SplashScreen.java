package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dozetracker.R;
import com.google.firebase.auth.FirebaseAuth;

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
            Intent intent = new Intent(SplashScreen.this, SignInScreen.class);
            startActivity(intent);
            overridePendingTransition(R.transition.fade_in,R.transition.fade_out);
            finish();
        },3000);
    }
}