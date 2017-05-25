package com.whisper.whispme.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.whisper.whispme.R;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText usernameInputEditText, emailInputEditText,
            passwordInputEditText;

    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameInputEditText = (TextInputEditText) findViewById(R.id.usernameInputEditText);
        usernameInputEditText.requestFocus();

        emailInputEditText = (TextInputEditText) findViewById(R.id.emailInputEditText);
        passwordInputEditText = (TextInputEditText) findViewById(R.id.passwordInputEditText);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,
                        MainViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });

    }
}
