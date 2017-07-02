package com.whisper.whispme.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by profesores on 5/22/17.
 */

public class WhispmeApi {

    private Context context;

    private final String whisp_url = "https://frozen-anchorage-68785.herokuapp.com/";
    private final String tag = "Whispme";

    WhispmeApiListener callback;

    public interface WhispmeApiListener {
        void onEventCompleted(boolean hasAccess);
        void onEventFailed(String apiResponse);
    }


    public WhispmeApi(WhispmeApiListener callback) {
        this.callback = callback;
    }


    public void loginWithUsernameAndPassword(String username, String password) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_name", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            //"SIGN IN ERROR";
            callback.onEventFailed("SIGN IN ERROR");
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
                                callback.onEventCompleted(false);
                                return;
                            }

                            if (response.getString("status").equalsIgnoreCase("200")) {
                                //"GET ACCESS";
                                callback.onEventCompleted(true);
                            }

                        } catch (JSONException e) {
                            //"SIGN IN ERROR";
                            callback.onEventFailed("SIGN IN ERROR");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //apiResponse = anError.getLocalizedMessage();
                        callback.onEventFailed("JSONObjectRequestListener error");
                    }
                });
    }

}
