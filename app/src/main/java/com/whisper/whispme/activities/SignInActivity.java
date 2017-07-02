package com.whisper.whispme.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.whisper.whispme.R;
import com.whisper.whispme.helpers.InputValidator;
import com.whisper.whispme.network.WhispmeApi;


public class SignInActivity
        extends AppCompatActivity
        implements View.OnFocusChangeListener {

    TextInputEditText usernameInputEditText, passwordInputEditText;
    Button loginButton, loginFacebookButton, loginGoogleButton;

    WhispmeApi whispmeApi;
    boolean isUsingWhispApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        whispmeApi = new WhispmeApi(new WhispmeApi.WhispmeApiListener() {
            @Override
            public void onEventCompleted(boolean hasAccess) {
                if (hasAccess) {
                    gotoMainViewActivity();
                    return;
                }
                isUsingWhispApi = false;
                // TODO show dialog "NO ACCESS"
                Toast.makeText(getBaseContext(), "NO ACCESS",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEventFailed(String apiResponse) {
                Toast.makeText(getBaseContext(), apiResponse,
                        Toast.LENGTH_SHORT).show();
                isUsingWhispApi = false;
            }
        });
        isUsingWhispApi = false;

        usernameInputEditText = (TextInputEditText)
                findViewById(R.id.usernameInputEditText);
        usernameInputEditText.requestFocus();
        usernameInputEditText.setOnFocusChangeListener(this);

        passwordInputEditText = (TextInputEditText)
                findViewById(R.id.passwordInputEditText);
        passwordInputEditText.setOnFocusChangeListener(this);

        loginButton = (Button) findViewById(R.id.signInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO remove this! DEBUG ONLY
                //gotoMainViewActivity();
                //finish();


                if (isUsingWhispApi) {
                    Toast.makeText(getBaseContext(), "Loading...",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO validate input data
                String username = usernameInputEditText.getText().toString();
                String password = passwordInputEditText.getText().toString();

                if (!isInputValid(username, password)) {
                    return;
                }


                // TODO request username and password validation to backend
                isUsingWhispApi = true;
                whispmeApi.loginWithUsernameAndPassword(username, password);
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

    private boolean isInputValid(String username, String password) {
        boolean isBlankUsername = InputValidator.isBlank(username);
        boolean isBlankPassword = InputValidator.isBlank(password);

        if (isBlankUsername) {
            usernameInputEditText.setError("required");
        }
        if (isBlankPassword) {
            passwordInputEditText.setError("required");
        }
        if (isBlankUsername || isBlankPassword) {
            return false;
        }

        boolean isValidUsername = InputValidator.isValidUsername(username);
        boolean isValidPassword = InputValidator.isValidPassword(password);

        if (!isValidUsername) {
            usernameInputEditText.setError("format 5-10");
        }
        if (!isValidPassword) {
            passwordInputEditText.setError("format >=6");
        }
        return !(!isValidUsername || !isValidPassword);

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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (hasFocus) {
            return;
        }
        if (InputValidator.isBlank(((TextView) v).getText().toString())) {
            ((TextView) v).setError("required");
            return;
        }

        switch (v.getId()) {
            case R.id.usernameInputEditText:
                if (!InputValidator.isValidUsername(((TextView) v).getText().toString())) {
                    ((TextView) v).setError("format 5-10");
                }
                break;
            case R.id.passwordInputEditText:
                if (!InputValidator.isValidPassword(((TextView) v).getText().toString())) {
                    ((TextView) v).setError("format >=6");
                }
                break;
        }
    }
}
