package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.example.dozetracker.MainActivity;
import com.example.dozetracker.R;

public class NewUserScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_screen);
        getWindow().setStatusBarColor(ContextCompat.getColor(NewUserScreen.this, R.color.colorPrimaryDark));
    }
}