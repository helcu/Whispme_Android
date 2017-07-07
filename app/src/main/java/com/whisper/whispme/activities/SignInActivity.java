package com.whisper.whispme.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.whisper.whispme.R;
import com.whisper.whispme.helpers.InputValidatorHelper;
import com.whisper.whispme.network.WhispmeApi;
import com.whisper.whispme.network.WhispmeApiInterface;


public class SignInActivity
        extends AppCompatActivity
        implements View.OnFocusChangeListener {

    TextInputEditText usernameInputEditText, passwordInputEditText;
    Button loginButton;

    WhispmeApi whispmeApi;
    boolean isUsingWhispApi;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        whispmeApi = new WhispmeApi();
        whispmeApi.signInListener =
                new WhispmeApiInterface.SignInListener() {
                    @Override
                    public void onEventCompleted(int userId) {

                        mProgress.dismiss();


                        if (userId != 0) {

                            SharedPreferences.Editor editor = getSharedPreferences(
                                    "WhispmeSP", Context.MODE_PRIVATE).edit();
                            editor.putInt(getString(R.string.sp_user_id), userId);
                            editor.apply();

                            gotoMainViewActivity();
                            return;
                        }
                        isUsingWhispApi = false;

                        // TODO show dialog "NO ACCESS"
                        try {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                            builder.setTitle("Sign In")
                                    .setMessage("Incorrect user or password")
                                    .setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                            builder.create().show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onEventFailed(String apiResponse) {

                        mProgress.dismiss();

                        Toast.makeText(SignInActivity.this, apiResponse,
                                Toast.LENGTH_SHORT).show();
                        isUsingWhispApi = false;
                    }
                };
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
                mProgress.setMessage("Sign in...");
                mProgress.show();


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
                whispmeApi.signIn(username, password);
            }
        });

        mProgress = new ProgressDialog(this);

    }

    private boolean isInputValid(String username, String password) {
        boolean isBlankUsername = InputValidatorHelper.isBlank(username);
        boolean isBlankPassword = InputValidatorHelper.isBlank(password);

        if (isBlankUsername) {
            usernameInputEditText.setError("required");
        }
        if (isBlankPassword) {
            passwordInputEditText.setError("required");
        }
        if (isBlankUsername || isBlankPassword) {
            return false;
        }

        boolean isValidUsername = InputValidatorHelper.isValidUsername(username);
        boolean isValidPassword = InputValidatorHelper.isValidPassword(password);

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
        if (InputValidatorHelper.isBlank(((TextView) v).getText().toString())) {
            ((TextView) v).setError("required");
            return;
        }

        switch (v.getId()) {
            case R.id.usernameInputEditText:
                if (!InputValidatorHelper.isValidUsername(((TextView) v).getText().toString())) {
                    ((TextView) v).setError("format 5-10");
                }
                break;
            case R.id.passwordInputEditText:
                if (!InputValidatorHelper.isValidPassword(((TextView) v).getText().toString())) {
                    ((TextView) v).setError("format >=6");
                }
                break;
        }
    }
}
