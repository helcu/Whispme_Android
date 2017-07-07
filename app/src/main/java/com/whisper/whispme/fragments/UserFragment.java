package com.whisper.whispme.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whisper.whispme.R;
import com.whisper.whispme.adapters.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    TabLayout mainTabLayout;
    ViewPager mainViewPager;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        mainTabLayout = (TabLayout) view.findViewById(R.id.mainTabLayout);
        mainViewPager = (ViewPager) view.findViewById(R.id.mainViewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new UserProfileFragment(), "Profile");
        adapter.addFragment(new FollowersFragment(), "Followers");
        mainViewPager.setAdapter(adapter);

        mainTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mainTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mainTabLayout.setupWithViewPager(mainViewPager);

        return view;
    }

}
