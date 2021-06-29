package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.dozetracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpScreen extends AppCompatActivity {
    @BindView(R.id.textView10) TextView mSignInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        ButterKnife.bind(this);
        changePartOfTextViewColor();
        signInToAccount();
    }

    private void changePartOfTextViewColor() {
        String text = "Already have an account? Sign in";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpanPurple = new ForegroundColorSpan(Color.rgb(51,116,229));
        spannableString.setSpan(foregroundColorSpanPurple, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignInTextView.setText(spannableString);
    }

    private void signInToAccount() {
        mSignInTextView.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpScreen.this,SignInScreen.class);
            startActivity(intent);
        });
    }
}