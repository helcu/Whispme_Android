package com.whisper.whispme.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.whisper.whispme.models.Whisp;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;


/**
 * Created by profesores on 5/22/17.
 */

public class WhispmeApi {

    private final String whisp_url = "https://frozen-anchorage-68785.herokuapp.com/";
    private final String tag = "Whispme";

    public WhispmeApiInterface.SignInListener signInListener;
    public WhispmeApiInterface.GetNearWhispsListener getNearWhispsListener;
    public WhispmeApiInterface.UploadWhispListener uploadWhispListener;

    public WhispmeApi() {
    }


    public void loginWithUsernameAndPassword(String username, String password) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_name", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            //"SIGN IN ERROR";
            signInListener.onEventFailed("SIGN IN ERROR");
            e.printStackTrace();
            return;
        }

        AndroidNetworking.post(whisp_url + "AuthenticateUser")
                .addJSONObjectBody(jsonObject)
                .setTag(tag)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("203")) {
                                //"NO ACCESS " + response.getString("UserId");
                                signInListener.onEventCompleted(0);
                                return;
                            }

                            if (response.getString("status").equalsIgnoreCase("200")) {
                                //"GET ACCESS";
                                signInListener.onEventCompleted(response.getInt("UserId"));
                            }

                        } catch (JSONException e) {
                            //"SIGN IN ERROR";
                            signInListener.onEventFailed("SIGN IN ERROR");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //apiResponse = anError.getLocalizedMessage();
                        signInListener.onEventFailed("JSONObjectRequestListener error");
                    }
                });
    }


    public void getNearWhisps(float longitude, float latitude) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("longitude", longitude);
            jsonObject.put("latitude", latitude);
        } catch (JSONException e) {
            //"SIGN IN ERROR";
            getNearWhispsListener.onEventFailed("SIGN IN ERROR");
            e.printStackTrace();
            return;
        }

        AndroidNetworking.get(whisp_url + "Whispers")
                .addPathParameter(jsonObject)
                .setTag(tag)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getString("StatusCode").equalsIgnoreCase("203")) {
                                //"NO ACCESS"
                                getNearWhispsListener.onEventCompleted(Whisp.buildNearWhisps(response.getJSONArray("Items")));
                                return;
                            }

                            if (response.getString("StatusCode").equalsIgnoreCase("200")) {
                                //"GET ACCESS";
                                getNearWhispsListener.onEventCompleted(Whisp.buildNearWhisps(response.getJSONArray("Items")));
                            }


                        } catch (JSONException e) {
                            getNearWhispsListener.onEventFailed("GET WHISPS ERROR");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //anError.getLocalizedMessage();
                        getNearWhispsListener.onEventFailed("GET WHISPS ERROR");
                    }
                });

    }


    public void uploadWhisp(Whisp whisp) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("idUser", whisp.getUserId());
            jsonObject.put("title", whisp.getTitle());
            jsonObject.put("dateCreation",
                    new SimpleDateFormat("yyyy-MM-dd")
                            .format(whisp.getDateCreation()));
            jsonObject.put("latitude", whisp.getLatitude());
            jsonObject.put("longitude", whisp.getLongitude());
            jsonObject.put("urlAudio", whisp.getUrlAudio());
            jsonObject.put("place", whisp.getPlace());
            jsonObject.put("text", whisp.getDescription());
            jsonObject.put("urlPhoto", whisp.getUrlPhoto());

        } catch (JSONException e) {
            //"SIGN IN ERROR";
            uploadWhispListener.onEventFailed("SIGN IN ERROR");
            e.printStackTrace();
            return;
        }

        AndroidNetworking.post(whisp_url + "WhispersPost")
                .addJSONObjectBody(jsonObject)
                .setTag(tag)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("StatusCode").equalsIgnoreCase("203")) {
                                //"NO ACCESS " + response.getString("UserId");
                                uploadWhispListener.onEventCompleted(response.getString("Message"));
                                return;
                            }

                            if (response.getString("StatusCode").equalsIgnoreCase("200")) {
                                //"GET ACCESS";
                                uploadWhispListener.onEventCompleted(response.getString("Message"));
                            }

                        } catch (JSONException e) {
                            //"SIGN IN ERROR";
                            uploadWhispListener.onEventFailed("Upload whisp ERROR");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //apiResponse = anError.getLocalizedMessage();
                        signInListener.onEventFailed("JSONObjectRequestListener error");
                    }
                });

    }
}
