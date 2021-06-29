package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }
}