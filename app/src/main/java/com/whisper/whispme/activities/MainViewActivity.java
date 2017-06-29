package com.whisper.whispme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.whisper.whispme.R;
import com.whisper.whispme.fragments.NewsFragment;
import com.whisper.whispme.fragments.SettingsFragment;
import com.whisper.whispme.fragments.UsersFragment;
import com.whisper.whispme.fragments.WhispsFragment;
import com.whisper.whispme.helpers.BottomNavigationViewHelper;

public class MainViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.navigation_create_whisp) {
                            startActivity(new Intent(
                                    getApplicationContext(),
                                    NewWhispActivity.class));
                            return true;
                        }

                        return navigateAccordingTo(item.getItemId());
                    }
                });
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        navigateAccordingTo(R.id.navigation_whisps);

    }

    private Fragment getFragmentFor(int id) {
        switch (id) {
            case R.id.navigation_whisps:
                return new WhispsFragment();
            case R.id.navigation_users:
                return new UsersFragment();
            case R.id.navigation_news:
                return new NewsFragment();
            case R.id.navigation_settings:
                return new SettingsFragment();
            default:
                return null;
        }
    }

    private boolean navigateAccordingTo(int id) {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, getFragmentFor(id))
                    .commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
