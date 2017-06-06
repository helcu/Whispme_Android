package com.whisper.whispme.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.whisper.whispme.R;
import com.whisper.whispme.activities.MainViewActivity;
import com.whisper.whispme.activities.NewWhispActivity;
import com.whisper.whispme.models.Whisp;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhispsFragment extends Fragment
        implements OnMapReadyCallback {

    // <-- Google map -->
    private GoogleMap mMap;
    private Marker mMakerCurrent;
    // <!-- Google map -->

    // <-- GPS -->
    private LocationManager locationManager;
    private LocationListener locationListener;
    // <!-- GPS -->


    List<Whisp> whisps;

    public WhispsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_whisps, container, false);

        if (mMap == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }


        // <-- GPS -->
        locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                /*Toast.makeText(getApplicationContext(),
                        "onLocationChanged",
                        Toast.LENGTH_SHORT).show();*/

                if (mMakerCurrent != null) {
                    mMakerCurrent.remove();
                }

                LatLng latLng = new LatLng(
                        location.getLatitude(),
                        location.getLongitude());
                mMakerCurrent = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("I'm here!"));

                //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f), 4000, null);
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(latLng)
                                .tilt(45)
                                .bearing(mMap.getCameraPosition().bearing)
                                .zoom(mMap.getCameraPosition().zoom)
                                .build()));

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Toast.makeText(getContext(),
                        "onStatusChanged",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(getContext(),
                        "onProviderEnabled",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(getContext(),
                        "onProviderDisabled",
                        Toast.LENGTH_SHORT).show();
            }
        };


        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET}
                        , 10);
            }


            // Message: Need GPS!
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(getContext(),
                        "Need GPS!",
                        Toast.LENGTH_SHORT).show();

                showPrompt();
            }

            locationManager.requestLocationUpdates("gps", 100, 0, locationListener);
        }

        // <!-- GPS -->


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(
                        view.getContext(),
                        NewWhispActivity.class));
            }
        });

        return view;

    }


    // <-- GPS -->
    private void showPrompt() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext());

        builder.setTitle("GPS")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Go to GPS setting?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                startActivity(new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }

                        });

        builder.create().show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "GPS ACTIVATED",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "NO GPS ACTIVATED",
                            Toast.LENGTH_SHORT).show();
                }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // <!-- GPS -->


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.setMinZoomPreference(16.0f);
        mMap.setMaxZoomPreference(17.0f);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        //mMap.getUiSettings().setRotateGesturesEnabled(false);
        //mMap.getUiSettings().setAllGesturesEnabled(false);


        // Set a style to the map
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                getContext(), R.raw.style_json));

        // Add a default marker in Sydney
        LatLng defaultLatLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(defaultLatLng)
                .title("I'm here!"));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(defaultLatLng)
                        .tilt(40)
                        .zoom(17f)
                        .build()));
    }

}
