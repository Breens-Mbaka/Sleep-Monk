package com.example.dozetracker.ui;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dozetracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInScreen extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = SignInScreen.class.getSimpleName();

    @BindView(R.id.textView10) TextView mRegisterAccountTextView;
    @BindView(R.id.emailInput) EditText mEmailInputEditText;
    @BindView(R.id.passwordInput) EditText mPasswordInputEditText;
    @BindView(R.id.signInUser) Button mSignInButton;

    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);


//        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = firebaseAuth -> {
//            FirebaseUser user = firebaseAuth.getCurrentUser();
//            Log.d(TAG, user.getEmail());
//            if (user != null) {
//                Intent intent = new Intent(SignInScreen.this, SleepEntry.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            }
//        };

        ButterKnife.bind(this);
        changePartOfTextViewColor();
        mRegisterAccountTextView.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
    }

    private void changePartOfTextViewColor() {
        String text = "Not registered? Create a new account";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpanPurple = new ForegroundColorSpan(Color.rgb(51,116,229));
        spannableString.setSpan(foregroundColorSpanPurple, 16, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRegisterAccountTextView.setText(spannableString);
    }

    @Override
    public void onClick(View v) {
        if (v == mRegisterAccountTextView) {
            Intent intent = new Intent(this,SignUpScreen.class);
            startActivity(intent);
        }
        else if(v == mSignInButton) {
            loginWithPassword();
        }
    }

    private void loginWithPassword() {
        String email = mEmailInputEditText.getText().toString().trim();
        String password = mPasswordInputEditText.getText().toString().trim();
        if (email.equals("")) {
            mEmailInputEditText.setError("Please enter your email");
            return;
        }
        if (password.equals("")) {
            mPasswordInputEditText.setError("Password cannot be blank");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
            if (!task.isSuccessful()) {
                Log.w(TAG, "signInWithEmail", task.getException());
                Toast.makeText(SignInScreen.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
}