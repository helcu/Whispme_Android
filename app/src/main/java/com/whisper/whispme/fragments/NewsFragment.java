package com.whisper.whispme.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.whisper.whispme.R;
import com.whisper.whispme.adapters.NewsAdapter;
import com.whisper.whispme.models.Whisp;
import com.whisper.whispme.network.WhispmeApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NewsFragment extends Fragment {

    RecyclerView newsRecyclerView;
    NewsAdapter newsAdapter;
    RecyclerView.LayoutManager newsLayoutManager;
    List<Whisp> whisps;
    private static String TAG = "Whispme";

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsRecyclerView = (RecyclerView) view.findViewById(R.id.newsRecyclerView);
        whisps = new ArrayList<>();

        // FOR WHILE
        // int whispId, int userId, String urlAudio, String title, String description, String urlPhoto, Date dateCreation, float latitude, float longitude, String place

        whisps.add(new Whisp(1, 1, "", "En la calle1", "Con mis prros Fido y Firulais1", "", new Date(), -12.02f, -77.11f, "Aeropuerto Jorge Chavez"));
        whisps.add(new Whisp(2, 2, "", "En la calle2", "Con mis prros Fido y Firulais2", "", new Date(), -12.02f, -77.11f, "Aeropuerto Jorge Chavez"));
        whisps.add(new Whisp(3, 1, "", "En la calle3", "Con mis prros Fido y Firulais3", "", new Date(), -12.02f, -77.13f, "Aeropuerto Jorge Chavez"));


        newsAdapter = new NewsAdapter(whisps);
        newsLayoutManager = new LinearLayoutManager(view.getContext());
        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.setLayoutManager(newsLayoutManager);
        return view;
    }

    /*private void updateData() {

        AndroidNetworking.get(WhispmeApi.WHIPS_URL)
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equalsIgnoreCase("error")) {
                                Log.d(TAG, response.getString("message"));
                                return;
                            }
                            users = Whisp.build(response.getJSONArray("sources"));
                            sourcesAdapter.setSources(sources).notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, anError.getLocalizedMessage());
                    }
                });

    }*/

    @Override
    public void onResume() {
        super.onResume();
        //updateData();
    }

}
