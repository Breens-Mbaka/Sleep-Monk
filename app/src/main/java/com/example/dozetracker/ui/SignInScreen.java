package com.example.dozetracker.ui;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.example.dozetracker.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInScreen extends AppCompatActivity {
    @BindView(R.id.textView10) TextView mRegisterAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        ButterKnife.bind(this);
        changePartOfTextViewColor();
        registerAccount();
    }

    private void registerAccount() {
        mRegisterAccountTextView.setOnClickListener(v -> {
            Intent intent = new Intent(SignInScreen.this,SignUpScreen.class);
            startActivity(intent);
        });
    }

    private void changePartOfTextViewColor() {
        String text = "Not registered? Create a new account";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpanPurple = new ForegroundColorSpan(Color.rgb(51,116,229));
        spannableString.setSpan(foregroundColorSpanPurple, 16, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRegisterAccountTextView.setText(spannableString);
    }
}