package driver.driver.Library;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;



public class MasterPermission implements ActivityCompat.OnRequestPermissionsResultCallback{


    public EventLocationListener permissionEventListener;

    private Activity activity;

    public MasterPermission(Activity activity){

        this.activity = activity;
    }

    public boolean isLocation() {


        if(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            return true;
        }


        return false;


    }


    public void addPermissionLocation(EventLocationListener permissionEventListener){

        this.permissionEventListener = permissionEventListener;

        if(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                new android.app.AlertDialog.Builder(activity)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();
            }
            else{
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                permissionEventListener.on_Location_Ok();
            }
        }

    }

    public static final int CODE_PHONE = 2;

    public void addPermissionCallPhone(){
        if(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.CALL_PHONE)) {
                new android.app.AlertDialog.Builder(activity)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE},CODE_PHONE );
                            }
                        })
                        .create()
                        .show();
            }
            else{
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CALL_PHONE},CODE_PHONE);
            }
        }
    }

    public void addPermissionOpenGps(final EventOpenGpsListener eventOpenGpsListener){

        final LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                GoogleApiClient googleApiClient = new GoogleApiClient.Builder(activity.getApplicationContext())
                        .addApi(LocationServices.API).build();
                googleApiClient.connect();

                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(10000);
                locationRequest.setFastestInterval(10000 / 2);

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);

                final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();


                        switch (status.getStatusCode()) {

                            case LocationSettingsStatusCodes.SUCCESS:

                                eventOpenGpsListener.onGpsOpned();

                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                try {
                                    // Start_Mode the dialog by calling startResolutionForResult(), and check the result
                                    // in onActivityResult().
                                    status.startResolutionForResult(activity, 1);
                                } catch (IntentSender.SendIntentException e) {

                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                                break;
                        }
                    }
                });



            }
        }

    }

    public boolean Phone_Ok() {

        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            addPermissionCallPhone();
            return false;
        }
        return true;
    }


    public interface EventCallPhoneListener{

        void on_Call_Phone_Ok();
    }

    public interface EventLocationListener{

         void on_Location_Ok();
    }

    public interface EventOpenGpsListener{

        void onGpsOpned();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode){

            case 1:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    }
                } else{
                    Toast.makeText(activity, "Please provide the permission", Toast.LENGTH_LONG).show();
                }
                break;
            }

            case MasterPermission.CODE_PHONE:{

                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){

                    }
                } else{
                    Toast.makeText(activity, "Please provide the permission", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }



}
