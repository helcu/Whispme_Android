package com.whisper.whispme.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.whisper.whispme.R;
import com.whisper.whispme.network.WhispmeApi;
import com.whisper.whispme.network.WhispmeApiInterface;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText usernameInputEditText, emailInputEditText,
            passwordInputEditText;

    Button signUpButton;

    WhispmeApi whispmeApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        whispmeApi = new WhispmeApi();
        whispmeApi.signUpListener = new WhispmeApiInterface.SignUpListener() {
            @Override
            public void onEventCompleted(boolean wasCreated) {

                if (!wasCreated) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setTitle("Sign Up")
                                .setMessage("Retry please")
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

                    return;
                }


                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("Sign Up")
                            .setMessage("Your account was created successfully")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent intent = new Intent(SignUpActivity.this,
                                                    SignInActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                    builder.create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEventFailed(String apiResponse) {
                Toast.makeText(SignUpActivity.this, apiResponse,
                        Toast.LENGTH_SHORT).show();
            }
        };


        usernameInputEditText = (TextInputEditText) findViewById(R.id.usernameInputEditText);
        usernameInputEditText.requestFocus();

        emailInputEditText = (TextInputEditText) findViewById(R.id.emailInputEditText);
        passwordInputEditText = (TextInputEditText) findViewById(R.id.passwordInputEditText);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whispmeApi.signUp(emailInputEditText.getText().toString(),
                        usernameInputEditText.getText().toString(),
                        passwordInputEditText.getText().toString());
            }
        });


    }
}
