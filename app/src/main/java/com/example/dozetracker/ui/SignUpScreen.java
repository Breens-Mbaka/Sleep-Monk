package com.example.dozetracker.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.dozetracker.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;


public class SignUpScreen extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = SignUpScreen.class.getSimpleName();

    @BindView(R.id.textView10) TextView mSignInTextView;
    @BindView(R.id.emailTextInput) EditText mEmailEditText;
    @BindView(R.id.passwordTextInput) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordTextInput) EditText mConfirmPasswordEditText;
    @BindView(R.id.createUser) Button mCreateUserButton;
    @BindView(R.id.facebook_sign_in_button) CardView mFacebookSignIn;
    @BindView(R.id.google_sign_in_button) CardView mGoogleSignIn;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN =  100;
    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        //initializing Facebook SDK
        FacebookSdk.sdkInitialize(SignUpScreen.this);

        // Initialize Facebook Login button
        initializeFacebookLogin();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mSignInTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
        mGoogleSignIn.setOnClickListener(this);
        changePartOfTextViewColor();
    }

    private void initializeFacebookLogin() {
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SignUpScreen.this,
                        Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null ) {
            updateUI(currentUser);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(SignUpScreen.this, WelcomeScreen.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Sign in to continue", Toast.LENGTH_SHORT);
        }
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
        if (view == mGoogleSignIn) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Exception exception = task.getException();
            if(task.isSuccessful()) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                }
            } else {
                Log.w("SignUpScreen", exception);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignUpScreen", "signInWithCredential:success");
                            Intent intent = new Intent(SignUpScreen.this, SleepEntry.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUpScreen", "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void changePartOfTextViewColor() {
        String text = "Already have an account? Sign in";
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpanPurple = new ForegroundColorSpan(Color.rgb(51,116,229));
        spannableString.setSpan(foregroundColorSpanPurple, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignInTextView.setText(spannableString);
    }

    private void registerNewUser() {
        final String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password, confirmPassword);

        //if invalid email and password a user isn't created and user is shown errors
        if (!validEmail || !validPassword ) return;

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpScreen.this, "Sign up successful.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, SleepEntry.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpScreen.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private boolean isValidEmail(String email) {
        //Built in regex for checking correct email format(Android Pattern)
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmailEditText.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mPasswordEditText.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}