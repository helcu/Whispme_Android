package com.whisper.whispme.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whisper.whispme.R;
import com.whisper.whispme.adapters.NewsAdapter;
import com.whisper.whispme.adapters.UsersAdapter;
import com.whisper.whispme.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersWorldFragment extends Fragment {

    RecyclerView usersWorldRecyclerView;
    UsersAdapter usersAdapter;
    RecyclerView.LayoutManager usersLayoutManager;
    List<User> users;
    private static String TAG = "Whispme";

    public UsersWorldFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users_world, container, false);
        usersWorldRecyclerView = (RecyclerView) view.findViewById(R.id.usersWorldRecyclerView);
        users = new ArrayList<>();

        // FOR WHILE
        // int whispId, int userId, String urlAudio, String title, String description, String urlPhoto, Date dateCreation, float latitude, float longitude, String place

        users.add(new User());
        users.add(new User());
        users.add(new User());

        usersAdapter = new UsersAdapter(users);
        usersLayoutManager = new LinearLayoutManager(view.getContext());
        usersWorldRecyclerView.setAdapter(usersAdapter);
        usersWorldRecyclerView.setLayoutManager(usersLayoutManager);
        return view;

    }

}
