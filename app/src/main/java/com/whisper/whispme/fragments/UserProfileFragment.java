package com.whisper.whispme.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.whisper.whispme.R;
import com.whisper.whispme.activities.SignInActivity;
import com.whisper.whispme.models.User;
import com.whisper.whispme.network.WhispmeApi;
import com.whisper.whispme.network.WhispmeApiInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    WhispmeApi whispmeApi;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        whispmeApi = new WhispmeApi();
        whispmeApi.getUserDetailListener = new WhispmeApiInterface.GetUserDetailListener() {
            @Override
            public void onEventCompleted(User user) {
                ((TextView) view.findViewById(R.id.usernameTextView)).setText(user.getUsername());
                ((TextView) view.findViewById(R.id.userDescriptionTextView)).setText(user.getDescription());
                ((TextView) view.findViewById(R.id.userEmailTextView)).setText(user.getEmail());
            }

            @Override
            public void onEventFailed(String apiResponse) {
                Toast.makeText(getContext(), apiResponse,
                        Toast.LENGTH_SHORT).show();
            }
        };

        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        "WhispmeSP", Context.MODE_PRIVATE);
        int userId = prefs.getInt(getString(R.string.sp_user_id), 0);

        whispmeApi.getAccountDetail(userId);


        return view;
    }

}
