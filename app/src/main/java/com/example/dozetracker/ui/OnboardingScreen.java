package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dozetracker.MainActivity;
import com.example.dozetracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnboardingScreen extends AppCompatActivity {
    @BindView(R.id.button) Button mGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);
        ButterKnife.bind(this);
        getStarted();
        getWindow().setStatusBarColor(ContextCompat.getColor(OnboardingScreen.this, R.color.colorPrimaryDark));
    }

    private void getStarted() {
        mGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(OnboardingScreen.this, SignInScreen.class);
            startActivity(intent);
        });
    }
}