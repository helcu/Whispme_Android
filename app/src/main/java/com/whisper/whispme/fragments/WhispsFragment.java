package com.whisper.whispme.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.whisper.whispme.activities.NewWhispActivity;
import com.whisper.whispme.models.Whisp;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhispsFragment extends Fragment
        implements OnMapReadyCallback {
    //}, DialogInterface.OnDismissListener {

    // <-- Google map -->
    private GoogleMap mMap;
    private Marker mMakerCurrent;
    // <!-- Google map -->

    // <-- Location -->
    private LocationManager locationManager;
    private LocationListener locationListener;

    private AlertDialog.Builder alertDialogBuilder;
    private boolean isLocationListening;
    // <!-- Location -->


    List<Whisp> whisps;

    private final int PERMISSIONS_REQUEST_CODE_FINE_LOCATION = 10;
    private final long REQUEST_LOCATION_UPDATE_TIME = 0;


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


        // <-- Location -->
        locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            private AlertDialog alertDialog;

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

                isLocationListening = true;

                if (alertDialog == null)
                    return;

                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(getContext(),
                        "onProviderDisabled",
                        Toast.LENGTH_SHORT).show();

                locationManager.removeUpdates(locationListener);
                isLocationListening = false;

                if (!locationManager.isProviderEnabled(
                        LocationManager.GPS_PROVIDER)) {
                    alertDialog = alertDialogBuilder.show();
                }
            }
        };

        requestLocalizationPermissions();

        alertDialogBuilder = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setTitle("GPS")
                .setIcon(R.drawable.ic_gps_fixed_black_24dp)
                .setMessage("Whispme need your position for create")
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

                        })
                .create();


        // <!-- Location -->


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


    // <-- Location -->
    private void requestLocalizationPermissions() {
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Localization permission is needed")
                        .setMessage("Whispme needs your position for create")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSIONS_REQUEST_CODE_FINE_LOCATION);
                            }
                        });
                builder.create().show();

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_CODE_FINE_LOCATION);
            }
        } else {

            if (!isLocationListening) {
                Toast.makeText(getContext(),
                        "Permission granted!",
                        Toast.LENGTH_SHORT).show();

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        REQUEST_LOCATION_UPDATE_TIME, 0, locationListener);
                isLocationListening = true;
            }
        }
    }

    // <!-- Location -->


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getContext(),
                            "GPS activated",
                            Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getContext(),
                            "GPS denied",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onPause() {
        locationManager.removeUpdates(locationListener);
        isLocationListening = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        requestLocalizationPermissions();
        super.onResume();
    }

    @Override
    public void onStop() {
        locationManager.removeUpdates(locationListener);
        isLocationListening = false;
        super.onStop();
    }

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
