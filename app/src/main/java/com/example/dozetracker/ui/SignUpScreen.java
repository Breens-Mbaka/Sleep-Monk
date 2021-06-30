package com.example.dozetracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dozetracker.R;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpScreen extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = SignUpScreen.class.getSimpleName();

    @BindView(R.id.textView10) TextView mSignInTextView;
    @BindView(R.id.emailTextInput) EditText mEmailEditText;
    @BindView(R.id.passwordTextInput) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordTextInput) EditText mConfirmPasswordEditText;
    @BindView(R.id.createUser) Button mCreateUserButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        ButterKnife.bind(this);

        mSignInTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
        changePartOfTextViewColor();

        mAuth = FirebaseAuth.getInstance();
    }

    private void changePartOfTextViewColor() {
        String text = "Already have an account? Sign in";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpanPurple = new ForegroundColorSpan(Color.rgb(51,116,229));
        spannableString.setSpan(foregroundColorSpanPurple, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignInTextView.setText(spannableString);
    }

    @Override
    public void onClick(View view) {
        if (view == mSignInTextView) {
            Intent intent = new Intent(SignUpScreen.this,SignInScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (view == mCreateUserButton) {
            registerNewUser();
        }
    }

    private void registerNewUser() {
        final String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpScreen.this, "Authentication successful.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUpScreen.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}