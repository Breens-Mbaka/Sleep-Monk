package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dozetracker.MainActivity;
import com.example.dozetracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        fadeInTransition();
        getWindow().setStatusBarColor(ContextCompat.getColor(SplashScreen.this, R.color.colorPrimaryDark));
    }

    private void fadeInTransition() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (user != null) {
                Intent intent = new Intent(SplashScreen.this, SleepEntry.class);
                startActivity(intent);
                overridePendingTransition(R.transition.fade_in,R.transition.fade_out);
                finish();
            } else {
                Intent intent = new Intent(SplashScreen.this, SignUpScreen.class);
                startActivity(intent);
                overridePendingTransition(R.transition.fade_in,R.transition.fade_out);
                finish();
            }
        },3000);
    }
}