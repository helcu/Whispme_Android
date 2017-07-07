package com.whisper.whispme.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.whisper.whispme.models.User;
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
    public WhispmeApiInterface.GetWhispDetailListener getWhispDetailListener;
    public WhispmeApiInterface.SignUpListener signUpListener;
    public WhispmeApiInterface.GetUserDetailListener getUserDetailListener;


    public WhispmeApi() {
    }


    public void signIn(String username, String password) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_name", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            //"SIGN IN ERROR";
            signInListener.onEventFailed("Sign In: Error");
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
                            signInListener.onEventFailed("Sign In: Error");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //apiResponse = anError.getLocalizedMessage();
                        signInListener.onEventFailed("Sign In: Error");
                    }
                });
    }


    public void getNearWhisps(float latitude, float longitude) {

        // Whispers?latitude=-12.013996&longitude=-77.098170

        String url = whisp_url + "Whispers?latitude=" + String.valueOf(latitude)
                + "&longitude=" + String.valueOf(longitude);

        AndroidNetworking.get(url)
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
                        getNearWhispsListener.onEventFailed("GET WHISPS ERROR 2");
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
            jsonObject.put("text", whisp.getText());
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


    public void getWhispDetail(int whispId) {

        // Whispers?latitude=-12.013996&longitude=-77.098170

        String url = whisp_url + "WhispersDeteail/" + String.valueOf(whispId);

        AndroidNetworking.get(url)
                .setTag(tag)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getString("StatusCode").equalsIgnoreCase("203")) {
                                //"NO ACCESS"
                                getWhispDetailListener.onEventCompleted(null);
                                return;
                            }

                            if (response.getString("StatusCode").equalsIgnoreCase("200")) {
                                //"GET ACCESS";
                                getWhispDetailListener.onEventCompleted(Whisp.build(response.getJSONArray("Items")).get(0));
                            }


                        } catch (JSONException e) {
                            getWhispDetailListener.onEventFailed("GET WHISPS ERROR");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //anError.getLocalizedMessage();
                        getWhispDetailListener.onEventFailed("GET WHISPS ERROR 2");
                    }
                });
    }


    public void signUp(String email, String username, String password) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("user_name", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            //"SIGN UP ERROR";
            signUpListener.onEventFailed("Sign Up: Error");
            e.printStackTrace();
            return;
        }

        AndroidNetworking.post(whisp_url + "CreateUser")
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
                                signUpListener.onEventCompleted(false);
                                return;
                            }

                            if (response.getString("StatusCode").equalsIgnoreCase("200")) {
                                //"GET ACCESS";
                                signUpListener.onEventCompleted(true);
                            }

                        } catch (JSONException e) {
                            //"SIGN IN ERROR";
                            signUpListener.onEventFailed("Sign Up: Error");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //apiResponse = anError.getLocalizedMessage();
                        signUpListener.onEventFailed("Sign Up: Error");
                    }
                });
    }

    public void getAccountDetail(int userId) {

        // Whispers?latitude=-12.013996&longitude=-77.098170

        String url = whisp_url + "AccountDetail/" + String.valueOf(userId);

        AndroidNetworking.get(url)
                .setTag(tag)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getString("StatusCode").equalsIgnoreCase("203")) {
                                //"NO ACCESS"
                                getUserDetailListener.onEventCompleted(null);
                                return;
                            }

                            if (response.getString("StatusCode").equalsIgnoreCase("200")) {
                                //"GET ACCESS";
                                getUserDetailListener.onEventCompleted(User.build(response.getJSONArray("Items")).get(0));
                            }


                        } catch (JSONException e) {
                            getUserDetailListener.onEventFailed("GET USER ERROR");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //anError.getLocalizedMessage();
                        getUserDetailListener.onEventFailed("GET USER ERROR 2");
                    }
                });
    }

}
