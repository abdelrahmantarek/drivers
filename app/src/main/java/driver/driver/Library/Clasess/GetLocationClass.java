package driver.driver.Library.Clasess;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import driver.driver.Library.Listener.OnGet_Location_And_Zoom_map;


public class GetLocationClass implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    public static Location MY_LOCATION;
    public static LatLng MY_LAT_LNG;
    public GoogleMap map;
    public LatLng eygpteLatLng = new LatLng(31.205753, 29.924526);
    private OnGet_Location_And_Zoom_map getLocationAndZoommap;
    private Activity activity;

    private boolean zoomAnimation = false;

    public void setZoomAnimation(boolean zoomAnimation) {
        this.zoomAnimation = zoomAnimation;
    }

    // counstructer
    public GetLocationClass(Activity context, GoogleMap map) {
        this.activity = context;
        this.map = map;
    }


    // Action get location

    public void setOn_Get_Location_And_Zoom_map(OnGet_Location_And_Zoom_map getLocationAndZoommap) {
        this.getLocationAndZoommap = getLocationAndZoommap;

        if (!GpsState() && location == null){
            CheckAndGetLocation();
            getLocation();
            return;
        }
        if (location == null){
            CheckAndGetLocation();
            getLocation();
            return;
        }


        getLocation_done();


    }

    @SuppressLint("MissingPermission")
    public void getLocation_done() {



        if (!PermissionWork()){
            AskPermissionLocation();
            return;
        }

        if (!GpsState()){
            settingsRequest();
            return;
        }


        Log.d("Locatiojj","2");

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {

                if (location!=null) {

                    Log.d("Locatiojj","3");

                    final double lat = location.getLatitude();
                    final double lng = location.getLongitude();

                    MY_LOCATION = location;
                    MY_LAT_LNG = new LatLng(lat, lng);


                    CameraPosition cameraUpdate = new CameraPosition
                            .Builder()
                            .bearing(15f)
                            .tilt(16f)
                            .zoom(15.15f)
                            .target(new LatLng(lat, lng))
                            .build();

                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraUpdate), 1000, new GoogleMap.CancelableCallback() {
                        @Override
                        public void onFinish() {

                            Log.d("Locatiojj",MY_LOCATION +"5");

                            if (getLocationAndZoommap != null) {
                                getLocationAndZoommap.onFinishGettingLocation(new LatLng(lat, lng), location);
                            }


                        }

                        @Override
                        public void onCancel() {

                        }
                    });

                }
            }
        });


    }


    @SuppressLint("MissingPermission")
    private void CheckAndGetLocation() {

        if (!PermissionWork()){
            AskPermissionLocation();
            return;
        }


        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        locationManager.addGpsStatusListener(new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int i) {


                switch (i){
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:

                        break;
                    case GpsStatus.GPS_EVENT_FIRST_FIX:

                        break;
                    case GpsStatus.GPS_EVENT_STARTED:
                        getLocation();
                        break;
                    case GpsStatus.GPS_EVENT_STOPPED:

                        break;


                }
            }

        });


        if (!GpsState()){
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            settingsRequest();
            return;
        }

        getLocation();

    }


    public LocationManager locationManager;

    @SuppressLint("MissingPermission")

    private void DialogGoOpenNetWorkProvidor() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "يمكنك تشغيل ما يلي لتحسين موقعك (موقع الشبكة)";

        builder.setMessage(message)
                .setPositiveButton("تمكين",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("الغاء",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }


    private void AskPermissionLocation() {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

            }
        }

    }




    public boolean PermissionWork() {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean GpsState() {

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean GpsStateNetWork() {

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    // flag for GPS status
    boolean canGetLocation = false;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1 * 1000 * 60; // 1 minute

    public Location location;



    public void getLocation() {


        try {

            if_gps_enable();

            // if GPS Enabled get lat/long using GPS Services

            if_net_work_enable();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    private void if_net_work_enable() {

        if (location == null) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            Log.d("GPS Enabled", "GPS Enabled");
            if (locationManager != null) {
                canGetLocation = true;
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    location_first_time = true;
                    zoomLocation(location);
                }
            }
        }else {
            getLocation_done();
        }

    }


    @SuppressLint("MissingPermission")

    private void if_gps_enable() {
        if (location == null) {

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Log.d("Network", "Network");
            if (locationManager != null) {
                canGetLocation = true;
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    location_first_time = true;
                    zoomLocation(location);
                }
            }
        }else {
            getLocation_done();
        }

    }

    private void zoomLocation(final Location location) {
        final double lat = location.getLatitude();
        final double lng = location.getLongitude();

        MY_LOCATION = location;
        MY_LAT_LNG = new LatLng(lat, lng);


        CameraPosition cameraUpdate = new CameraPosition
                .Builder()
                .bearing(15f)
                .tilt(16f)
                .zoom(15.15f)
                .target(new LatLng(lat, lng))
                .build();

        if (zoomAnimation){

            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraUpdate), 1000, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {

                    if (getLocationAndZoommap != null) {
                        getLocationAndZoommap.onFinishGettingLocation(new LatLng(lat, lng), location);
                    }

                }

                @Override
                public void onCancel() {

                }
            });

        }else {

            Log.d("Locatiojj","5");

            if (getLocationAndZoommap != null) {
                getLocationAndZoommap.onFinishGettingLocation(new LatLng(lat, lng), location);
            }


        }

    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    boolean location_first_time = false;

    @Override
    public void onLocationChanged(Location location) {

        if (!location_first_time){
            getLocation();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    GoogleApiClient googleApiClient;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public void settingsRequest()
    {
        if(googleApiClient == null){
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    private static final int LOCATION_REQUEST_CODE = 1;

    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {

        switch (i){

            case LOCATION_REQUEST_CODE:

                if (!PermissionWork()){
                    return;
                }

                CheckAndGetLocation();

                break;
        }

    }
}

