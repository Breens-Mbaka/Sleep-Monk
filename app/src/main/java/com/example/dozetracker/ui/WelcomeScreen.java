package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dozetracker.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.myName) TextView mName;
    @BindView(R.id.myPhoto) ImageView mProfile;
    @BindView(R.id.myEmail) TextView mEmail;
    @BindView(R.id.signout_button) Button mSignOut;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ButterKnife.bind(this);

        mSignOut.setOnClickListener(this);
        mName.setOnClickListener(this);
        mEmail.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        displayProfile();
    }

    @Override
    public void onClick(View v) {
        if(v == mSignOut) {
            firebaseAuth.signOut();
            LoginManager.getInstance().logOut();
            finish();
        }
    }

    public void displayProfile() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photoURL = user.getPhotoUrl().toString();

            Glide.with(this).load(photoURL).into(mProfile);
            mName.setText(name);
            mEmail.setText(email);
        }
    }
}