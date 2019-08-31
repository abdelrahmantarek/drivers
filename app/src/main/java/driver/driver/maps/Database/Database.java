package driver.driver.maps.Database;


import android.app.Activity;


import driver.driver.Library.Clasess.GetLocationClass;
import driver.driver.Library.F;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import driver.driver.Library.Listener.OnGet_Location_And_Zoom_map;
import driver.driver.Library.StateClass;
import driver.driver.R;
import driver.driver.Register.helper;
import driver.driver.model.Service;

import static driver.driver.Library.Clasess.GetLocationClass.MY_LOCATION;









public class Database {




    public Activity activity;

    private On_Request_Or_Service on_request_or_service;

    private ChildEventListener childEventListener;

    private DatabaseReference databaseReference;


    public void setOn_request_or_service(On_Request_Or_Service on_request_or_service) {
        this.on_request_or_service = on_request_or_service;

        Check_State_Service_Request();
    }

    public Database(Activity activity) {
        this.activity = activity;
    }

    public void Check_State_Service_Request(){


        Log.d("UID",F.Uid() +"");

        StateClass stateClass = new StateClass();
        stateClass.setOnGetStateEventListener(new StateClass.onGetStateEventListener() {
            @Override
            public void onNo(DataSnapshot your_data_colunm_Users) {

                on_request_or_service.onCheckNo(your_data_colunm_Users);


            }

            @Override
            public void onService(final DataSnapshot dataSnapshot) {

                final String user_id = dataSnapshot.child("user_id").getValue(String.class);

                on_request_or_service.onService(user_id,dataSnapshot);



            }

            @Override
            public void onRequest(DataSnapshot dataSnapshot) {

                String user_id = dataSnapshot.child("user_id").getValue(String.class);

                on_request_or_service.onRequest(user_id,dataSnapshot);


            }

            @Override
            public void onRequestRemove(DataSnapshot dataSnapshot) {

                on_request_or_service.onRequestRemove(dataSnapshot);


            }

            @Override
            public void onServiceRemove(DataSnapshot dataSnapshot) {

                on_request_or_service.onServiceRemove(dataSnapshot);
            }
        });


    }



    public void onStop() {


    }

    public void onResume() {
        if (databaseReference==null){
            return;
        }

        Check_State_Service_Request();
    }

    public void onStart() {


    }

    public void onDestroy() {

    }

    public void onPause() {

    }


    private void getValueInformationTheService(final String user_id) {





    }







    public void deleteService(String user_id) {

        String state_user_id = "state/"+user_id;
        String state_uid = "state/"+ F.Uid();

        final Map<String,Object> hashMap = new HashMap<>();

        hashMap.put(state_user_id,null);
        hashMap.put(state_uid,null);

        F.reference().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


            }
        });

    }



    public void set_Online_location(final DataSnapshot dataSnapshot, GetLocationClass location) {


        location.setZoomAnimation(false);
        location.setOn_Get_Location_And_Zoom_map(new OnGet_Location_And_Zoom_map() {
            @Override
            public void onFinishGettingLocation(LatLng latLng, Location location) {

                F.Drivers().child(F.Uid()).child("online").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        Add_Online();
                        Add_MyLocation();
                    }
                });
            }
        });
    }


    public static void Remove_Online(){

        F.OnlineDrivers().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(F.Uid())){

                    F.OnlineDrivers().child(F.Uid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    public  void Add_MyLocation(){

        GeoFire geoFire;
        geoFire = new GeoFire(F.Drivers().child(F.Uid()).child("location"));
        geoFire.setLocation(F.Uid(), new GeoLocation(MY_LOCATION.getLatitude(), MY_LOCATION.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                //   Snackbar.make(getlayout,"انت الان نشط تنتطر عميلا", Snackbar.LENGTH_LONG).show();

            }
        });


    }



    public static void Add_Online(){




        GeoFire geoFire;
        geoFire = new GeoFire(F.OnlineDrivers());
        geoFire.setLocation(F.Uid(), new GeoLocation(MY_LOCATION.getLatitude(), MY_LOCATION.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                //   Snackbar.make(getlayout,"انت الان نشط تنتطر عميلا", Snackbar.LENGTH_LONG).show();
            }
        });

    }


    public void set_Offline_location(DataSnapshot dataSnapshot, GetLocationClass location) {

        F.Drivers().child(F.Uid()).child("online").setValue("false").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Remove_Online();
            }
        });

    }

    public void CancelRequest(final String user_id) {


        String state_user_id = "state/"+user_id;
        String state_uid = "state/"+ F.Uid();


        final Map<String,Object> hashMap = new HashMap<>();

        hashMap.put(state_user_id,null);
        hashMap.put(state_uid,null);

        F.reference().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {



            }
        });
    }

    public void AcceptRequest(final String user_id, final Activity activity) {


        String state_user_id = "state/"+user_id+"/state";
        String state_uid = "state/"+F.Uid()+"/state";


        final Map<String,Object> hashMap2 = new HashMap<>();
        hashMap2.put(state_user_id,"service");
        hashMap2.put(state_uid, "service");


        F.reference().updateChildren(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });


    }


    public void updateMyLocationDatabase_And_DriversOnline() {

        Add_Online();
        Add_MyLocation();
    }

    public void updateMyLocationDatabase_Only() {

        Add_MyLocation();
    }
}
