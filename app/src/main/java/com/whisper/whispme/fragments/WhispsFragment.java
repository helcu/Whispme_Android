package com.whisper.whispme.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.whisper.whispme.R;
import com.whisper.whispme.models.Whisp;
import com.whisper.whispme.network.WhispmeApi;
import com.whisper.whispme.network.WhispmeApiInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // <-- Internet -->
    private AlertDialog.Builder internetAlertDialogBuilder;
    // <!- Internet -->

    // <-- Location -->
    private LocationManager locationManager;
    private LocationListener locationListener;

    private AlertDialog.Builder locationAlertDialogBuilder;
    private boolean isLocationListening;

    public static float LATITUDE;
    public static float LONGITUDE;

    // <!-- Location -->

    // <-- WhispmeApi -->
    WhispmeApi whispmeApi;
    boolean isUsingWhispApi;
    Map<String, Whisp> markerWhisps;

    private int nearWhispsUpdateTime = 0;
    // <!- WhispmeApi -->


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


        markerWhisps = new HashMap<>();

        // <-- WhispmeApi -->
        whispmeApi = new WhispmeApi();
        whispmeApi.getNearWhispsListener = new WhispmeApiInterface.GetNearWhispsListener() {
            @Override
            public void onEventCompleted(List<Whisp> whisps) {
                mMap.clear();
                markerWhisps.clear();

                for (Whisp whisp : whisps) {
                    Marker marker = addMarkerToMap(
                            new LatLng(whisp.getLatitude(), whisp.getLongitude()),
                            String.valueOf(whisp.getWhispId()),
                            R.drawable.ic_globe_32x32);
                    markerWhisps.put(marker.getId(), whisp);
                }
                isUsingWhispApi = false;
            }

            @Override
            public void onEventFailed(String apiResponse) {
                Toast.makeText(getContext(),
                        apiResponse,
                        Toast.LENGTH_SHORT).show();
                isUsingWhispApi = false;
            }
        };


        // <!- WhispmeApi -->


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

                LATITUDE = (float) location.getLatitude();
                LONGITUDE = (float) location.getLongitude();

                LatLng latLng = new LatLng(
                        location.getLatitude(),
                        location.getLongitude());
                mMakerCurrent = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("I'm here!")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_40x40)));

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(latLng)
                                .tilt(45)
                                .bearing(mMap.getCameraPosition().bearing)
                                .zoom(mMap.getCameraPosition().zoom)
                                .build()));


                if (nearWhispsUpdateTime == 0) {
                    whispmeApi.getNearWhisps(
                            (float) location.getLatitude(),
                            (float) location.getLongitude());
                }
                nearWhispsUpdateTime++;
                if (nearWhispsUpdateTime > 9) {
                    nearWhispsUpdateTime = 0;
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                /*Toast.makeText(getContext(),
                        "onStatusChanged",
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onProviderEnabled(String s) {
                /*Toast.makeText(getContext(),
                        "onProviderEnabled",
                        Toast.LENGTH_SHORT).show();*/

                isLocationListening = true;

                // Add a default marker
                //addMarkerToMap(new LatLng(-34, 151), "I'm here", R.drawable.ic_fallout_32x32);


                /*mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(new LatLng(-34, 151))
                                .tilt(40)
                                .zoom(17f)
                                .build()));*/


                if (alertDialog == null)
                    return;

                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }

            @Override
            public void onProviderDisabled(String s) {
                /*Toast.makeText(getContext(),
                        "onProviderDisabled",
                        Toast.LENGTH_SHORT).show();*/

                locationManager.removeUpdates(locationListener);
                isLocationListening = false;

                if (!locationManager.isProviderEnabled(
                        LocationManager.GPS_PROVIDER)) {
                    alertDialog = locationAlertDialogBuilder.show();
                }
            }
        };

        requestLocalizationPermissions();

        locationAlertDialogBuilder = new AlertDialog.Builder(getContext());

        locationAlertDialogBuilder.setTitle("GPS")
                .setIcon(R.drawable.ic_gps_fixed_black_24dp)
                .setMessage("Whispme needs your position for create whisps")
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


        // <-- Internet -->
        ConnectivityManager cm =
                (ConnectivityManager) getContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
            internetAlertDialogBuilder = new AlertDialog.Builder(getContext());

            internetAlertDialogBuilder.setTitle("Internet")
                    .setIcon(R.drawable.ic_wifi_black_24dp)
                    .setMessage("Whispme needs to know how you are")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    startActivity(new Intent(
                                            Settings.ACTION_NETWORK_OPERATOR_SETTINGS));

                                    d.dismiss();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    d.cancel();
                                }

                            })
                    .create().show();
        }

        //<!-- Internet -->

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
                        .setMessage("Whispme needs your position for create whisps")
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
        // TODO update whips in map
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
        //mMap.setMinZoomPreference(16.0f);
        //mMap.setMaxZoomPreference(17.0f);
        //mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        //mMap.getUiSettings().setRotateGesturesEnabled(false);
        //mMap.getUiSettings().setAllGesturesEnabled(false);


        // Set a style to the map
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                getContext(), R.raw.style_json));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setTitle(marker.getId());
                marker.showInfoWindow();

                Whisp whisp = markerWhisps.get(marker.getId());

                StorageReference mStorage = FirebaseStorage.getInstance().getReference();
                mStorage.child("whisps_audios").child(whisp.getUrlAudio() + ".mp3").getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                WhispPlayerFragment fragment = new WhispPlayerFragment();

                                fragment.whispUri = uri;
                                fragment.show(getFragmentManager(), "Player");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),
                                "Firebase error downloading",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });
    }


    Marker addMarkerToMap(LatLng position, String title, int iconId) {
        return mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(iconId)));
    }

    // <-- MediaPlayer -->

    /*
        String whispUrl =
            "https://www.dropbox.com/s/v9fporswii5q00d/Este%20altavoz%20es__.mp3?dl=1";
        //mediaPlayer = MediaPlayer.create(this, whispUri);

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(whispUrl);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    */
    // <!-- MediaPlayer -->


}
