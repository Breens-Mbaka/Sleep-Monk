package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.example.dozetracker.MainActivity;
import com.example.dozetracker.R;

public class SleepEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_entry);
        getWindow().setStatusBarColor(ContextCompat.getColor(SleepEntry.this, R.color.colorPrimaryDark));
    }
}