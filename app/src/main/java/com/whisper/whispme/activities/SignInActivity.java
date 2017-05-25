package com.whisper.whispme.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whisper.whispme.R;

public class SignInActivity extends AppCompatActivity {

    TextInputEditText emailTextInputEditText, passwordInputEditText;
    Button loginButton, loginFacebookButton, loginGoogleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailTextInputEditText = (TextInputEditText) findViewById(R.id.usernameInputEditText);
        emailTextInputEditText.requestFocus();
        passwordInputEditText = (TextInputEditText) findViewById(R.id.passwordInputEditText);

        loginButton = (Button) findViewById(R.id.signInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO request username and password validation to backend

                gotoMainViewActivity();
            }
        });

        loginFacebookButton = (Button) findViewById(R.id.signInFacebookButton);
        loginFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO request Google token to backend

                gotoMainViewActivity();
            }
        });

        loginGoogleButton = (Button) findViewById(R.id.signInGoogleButton);
        loginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO request Google token to backend

                gotoMainViewActivity();
            }
        });


    }


    private void gotoMainViewActivity() {
        Intent intent = new Intent(SignInActivity.this,
                MainViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


}
