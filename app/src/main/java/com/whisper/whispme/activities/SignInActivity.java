package com.whisper.whispme.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.whisper.whispme.R;
import com.whisper.whispme.network.WhispmeApi;

import org.json.JSONException;
import org.json.JSONObject;


public class SignInActivity extends AppCompatActivity {

    TextInputEditText usernameTextInputEditText, passwordInputEditText;
    Button loginButton, loginFacebookButton, loginGoogleButton;

    private final String tag = "Whispme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameTextInputEditText = (TextInputEditText) findViewById(R.id.usernameInputEditText);
        usernameTextInputEditText.requestFocus();
        passwordInputEditText = (TextInputEditText) findViewById(R.id.passwordInputEditText);

        loginButton = (Button) findViewById(R.id.signInButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoMainViewActivity();

                // TODO request username and password validation to backend

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("user_name", usernameTextInputEditText.getText().toString());
                    jsonObject.put("password", passwordInputEditText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidNetworking.post(WhispmeApi.WHIPS_URL + "AuthenticateUser")
                        .addJSONObjectBody(jsonObject) // posting json
                        .setTag(tag)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("status").equalsIgnoreCase("203")) {
                                        Toast.makeText(SignInActivity.this,
                                                "NO ACCESS " + response.getString("UserId"),
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (response.getString("status").equalsIgnoreCase("200")) {
                                        Toast.makeText(SignInActivity.this,
                                                "ACCESS " + response.getString("UserId"),
                                                Toast.LENGTH_SHORT).show();
                                        Log.d(tag, response.getString("UserId"));
                                        gotoMainViewActivity();
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(SignInActivity.this,
                                            "SIGN IN ERROR",
                                            Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d(tag, anError.getLocalizedMessage());
                            }
                        });

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
