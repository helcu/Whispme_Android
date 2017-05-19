package com.whisper.whispme.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whisper.whispme.R;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText emailTextInputEditText, passwordInputEditText;
    TextView createAccountTextView;
    Button loginButton, loginFacebookButton, loginGoogleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextInputEditText = (TextInputEditText) findViewById(R.id.usernameInputEditText);
        passwordInputEditText = (TextInputEditText) findViewById(R.id.passwordInputEditText);

        createAccountTextView = (TextView) findViewById(R.id.createAccountTextView);
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        LoginActivity.this,
                        SignUpActivity.class));
            }
        });

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        LoginActivity.this,
                        MainViewMapsActivity.class));
            }
        });

        loginFacebookButton = (Button) findViewById(R.id.loginFacebookButton);
        loginFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        LoginActivity.this,
                        SignUpActivity.class));
            }
        });

        loginGoogleButton = (Button) findViewById(R.id.loginGoogleButton);
        loginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        LoginActivity.this,
                        SignUpActivity.class));
            }
        });


    }




}
