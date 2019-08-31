package driver.driver.maps;

import driver.driver.D;
import driver.driver.Library.BoomAnimation;
import driver.driver.Library.Clasess.GetLocationClass;
import driver.driver.Library.Clasess.LocationUpdate;
import driver.driver.Library.Clasess.MapStyle;
import driver.driver.Library.Clasess.SensorClass;
import driver.driver.Library.Clasess.SharePref;
import driver.driver.Library.Listener.OnGet_Location_And_Zoom_map;
import driver.driver.Library.Listener.OnLocation_Update;
import driver.driver.Library.LocaleHelper;
import driver.driver.Library.MapHelper;
import driver.driver.Library.OnBoomAnimation;
import driver.driver.Library.ShowMapClass;
import driver.driver.Library.ShowMap_EventListener;
import driver.driver.Library.Tools;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import driver.driver.Library.F;

import com.firebase.geofire.GeoLocation;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import driver.driver.Login.LoginActivity;
import driver.driver.Login.Welcome.WelcomeActivity;
import driver.driver.NAV.NavigationDrawer;
import driver.driver.maps.Database.Database;
import driver.driver.maps.Database.On_Request_Or_Service;
import driver.driver.R;
import driver.driver.maps.helper.helper;
import driver.driver.maps.view.BottomSheetService;
import driver.driver.maps.view.FabClick;
import driver.driver.maps.view.RequestClass;
import driver.driver.model.Drivers;
import driver.driver.model.Service;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,On_Request_Or_Service {


    private boolean Request = false;
    private boolean Service = false;
    private String isOnline = "";
    private ActionBar actionBar;
    private DrawerLayout drawer_layout;
    private DataSnapshot dataSnapshotDrivers;
    private NavigationDrawer navigationDrawer;
    private SensorClass sensorClass;
    private RequestClass requestClass;
    private BottomSheetService bottomSheetService;
    private Database database;
    private GetLocationClass location;
    private MapStyle mapStyle;
    private GoogleMap map;
    MapHelper mapHelper;


    private Toolbar toolbar;
    private FabClick fabClick;
    private LocationUpdate locationUpdate;



    private Location location_customer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_maps_activity);

        setTitle(R.string.app_name);

        go_login_if_user_null();

        setColorTopLine();

        IntentAnimation();

        if (F.firebaseAuth().getCurrentUser() == null){
            return;
        }

        setScreeSize();

        initFragmentMap();


    }

    private void go_login_if_user_null() {

        if (F.firebaseAuth().getCurrentUser() == null) {

            // Intent mainIntent = new Intent(getContext(), LoginActivity.class);
            Intent mainIntent = new Intent(MapsActivity.this, WelcomeActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();

            return;
        }

    }

    // color top line Activity
    private void setColorTopLine() {
        Tools.setSystemBarColor(this, R.color.shfaf_gray_background);
    }
    // setScreen Size
    private void setScreeSize() {
        helper.screen(this);
    }
    // init map fragment
    private void initFragmentMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.no_map);
        mapFragment.getMapAsync(this);

    }
    // Animation start Screen
    private void IntentAnimation() {


        BoomAnimation boomAnimation = new BoomAnimation(((findViewById(R.id.boom_animation_layout))),this);
        boomAnimation.setOnBoomAnimation(new OnBoomAnimation() {
            @Override
            public void onFinish() {

                if (F.firebaseAuth().getCurrentUser() == null) {

                    // Intent mainIntent = new Intent(getContext(), LoginActivity.class);
                    Intent mainIntent = new Intent(MapsActivity.this, WelcomeActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    Toast.makeText(MapsActivity.this, "2", Toast.LENGTH_SHORT).show();
                    finish();

                    return;
                }


            SharePref sharePref = new SharePref(MapsActivity.this);


                if (!sharePref.exist(sharePref.database_name)){

                    // Intent mainIntent = new Intent(getContext(), LoginActivity.class);
                    Intent mainIntent = new Intent(MapsActivity.this, WelcomeActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                    return;
                }




                (findViewById(R.id.boom_animation_layout)).setVisibility(View.GONE);
                (findViewById(R.id.boom_animation_layout_image)).setVisibility(View.GONE);

            }
        });


    }
    // location update
    private void startLocationUpdate() {
        if (locationUpdate!=null){
            locationUpdate.StartUpdateLocation();
        }
    }
    private void stopLocationUpdate() {
        if (locationUpdate!=null){
            locationUpdate.StopLocationUpdate();

        }
    }
    // sensor rotation
    private void stopSensor() {
        if (sensorClass!=null){
            sensorClass.stop();
        }
    }
    private void startSensor() {
        if (sensorClass!=null){
            sensorClass.start();
        }
    }
    // refresh token
    private void updateToken() {
        F.Drivers().child(F.Uid()).child("Token").setValue(F.getDaviceToken()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }










    @Override
    protected void onStart() {
        super.onStart();
        if (F.firebaseAuth().getCurrentUser() == null){
            return;
        }

        updateToken();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (F.firebaseAuth().getCurrentUser() == null){
            return;
        }

        if (mapHelper!=null){
            mapHelper.onStop();
        }

        stopLocationUpdate();
        stopSensor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (F.firebaseAuth().getCurrentUser() == null){
            return;
        }
        stopLocationUpdate();
        stopSensor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (F.firebaseAuth().getCurrentUser() == null){
            return;
        }

        startLocationUpdate();
        startSensor();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (F.firebaseAuth().getCurrentUser() == null){
            return;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (F.firebaseAuth().getCurrentUser() == null){
            return;
        }
        stopLocationUpdate();
        stopSensor();

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (F.firebaseAuth().getCurrentUser() == null){
            return;
        }

        Get_Location_And_Zoom();

        new_For_All_Classes();

        Check_Request_Or_Service();

    }













    private void new_For_All_Classes() {
        mapHelper = new MapHelper(map);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fabClick = new FabClick(MapsActivity.this,map);
        setSupportActionBar(toolbar);
        navigationDrawer = new NavigationDrawer(this,mapHelper);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));



        bottomSheetService = new BottomSheetService(this, database);
        mapStyle = new MapStyle(this,map);
        mapStyle.checkMapStyle();
        location = new GetLocationClass(this,map);
        database = new Database(this);
        requestClass = new RequestClass(MapsActivity.this,database);
        bottomSheetService = new BottomSheetService(this,database);

        navigationDrawer.navigation_Drawer();


    }





    private void Get_Location_And_Zoom() {

        if (location== null){
            location = new GetLocationClass(this,map);
        }

        location.setZoomAnimation(true);
        location.setOn_Get_Location_And_Zoom_map(new OnGet_Location_And_Zoom_map() {
            @Override
            public void onFinishGettingLocation(LatLng latLng, Location location) {

                if (d == null){
                    d = new D();
                }

                StartLocationUpdate();

                d.marker_location_update(GetLocationClass.MY_LAT_LNG,map);

                sensor(latLng,map);

                d.hide_floationg((FloatingActionMenu) findViewById(R.id.service_map_floating_action_menu)
                        ,(FloatingActionMenu) findViewById(R.id.no_map_floating_action_menu));


            }
        });

    }

    private void sensor(final LatLng latLng, final GoogleMap map) {


    }



    private void StartLocationUpdate() {

         locationUpdate = new LocationUpdate(this);
        locationUpdate.setOn_Location_Update(new OnLocation_Update() {
            @Override
            public void onLocationChanged(Location location, LatLng latLng) {



                if (F.firebaseUser() == null){
                    return;
                }
                d.marker_location_update(GetLocationClass.MY_LAT_LNG,map);
                SetMyLocationDatabase();
            }

            @Override
            public void onLocationStop() {

            }
        });

    }

    private void SetMyLocationDatabase() {

        if (isOnline.equals("true")){

            database.updateMyLocationDatabase_And_DriversOnline();

        }else {

            database.updateMyLocationDatabase_Only();
        }


    }

    private void Check_Request_Or_Service() {

        database.setOn_request_or_service(this);

    }












    public boolean firebase(){
        return F.firebaseAuth().getCurrentUser() != null;
    }



    public static D d;


    @Override
    public void onRequest(final String user_id, DataSnapshot dataSnapshot_service) {

        if (d==null){
            d = new D();
        }

        Request = true;
        Service = false;

        Service service = dataSnapshot_service.getValue(Service.class);

        LatLng latLng_1 = new LatLng(service.getFrom_lat(),service.getFrom_lng());
        LatLng latLng_2 = new LatLng(service.getTo_lat(),service.getTo_lng());
        d.map_clear(this,map,mapHelper);
        ShowCustomerMapRequest(user_id);
        d.hide_all_sheet();
        requestClass.addDataUser(service,user_id);
        RequestClass.show();
        d.addPolylineAndMarker(latLng_1,latLng_2,service.getText_from(),service.getText_to(),map);
        Load_nav_Data();


    }

    private void ShowCustomerMapRequest(String user_id_request) {

            if (showMapClass == null){
                showMapClass = new ShowMapClass();
            }

            showMapClass.hide();
            MapsActivity.d.map_clear(this,map,mapHelper);
            showMapClass.setMap(map);
            showMapClass.setTitleMarker("عميلك");
            showMapClass.setReduis(8000);
            showMapClass.setDatabaseReference(F.Users().child(user_id_request).child("location"));
            showMapClass.setResourseMarkerDrawAbe(R.drawable.location_person);
            showMapClass.setSearch(false);
            showMapClass.setLocation(GetLocationClass.MY_LOCATION);
            showMapClass.addShowMapEventListener(new ShowMap_EventListener() {
                @Override
                public void onKeyEntered(String key, GeoLocation location) {


                    location_customer = new Location("");
                    location_customer.setLatitude(location.latitude);
                    location_customer.setLongitude(location.longitude);


                    d.marker_location_update(GetLocationClass.MY_LAT_LNG,map);



                }

                @Override
                public void onKeyExited(String key) {


                }

                @Override
                public void onKeyMoved(String key, GeoLocation location) {

                }

                @Override
                public void onGeoQueryReady() {

                }

                @Override
                public void onGeoQueryError(DatabaseError error) {

                }
            });



    }

    private void Load_nav_Data() {

        F.Drivers().child(F.Uid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                navigationDrawer.setMyData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onService(String user_id, DataSnapshot dataSnapshot_service) {

        if (d==null){
            d = new D();
        }

        Toast.makeText(this, "انت الان مع عميل في خدمة", Toast.LENGTH_SHORT).show();

        Service = true;
        Request = false;

        Service service = dataSnapshot_service.getValue(Service.class);

        LatLng latLng_1 = new LatLng(service.getFrom_lat(),service.getFrom_lng());
        LatLng latLng_2 = new LatLng(service.getTo_lat(),service.getTo_lng());
        d.map_clear(this,map,mapHelper);
        showCusomerMapService(user_id);
        d.hide_all_sheet();
        d.hide_floationg((FloatingActionMenu) findViewById(R.id.no_map_floating_action_menu)
                ,(FloatingActionMenu) findViewById(R.id.service_map_floating_action_menu));
        bottomSheetService.setDataService(service,user_id);
        fabClick.fab_service(service,user_id,location_customer);
        d.addPolylineAndMarker(latLng_1,latLng_2,service.getText_from(),service.getText_to(),map);

        Load_nav_Data();


    }

    private ShowMapClass showMapClass;



    private void showCusomerMapService(String user_id_service) {

        if (showMapClass == null){
            showMapClass = new ShowMapClass();
        }


        showMapClass.hide();
        MapsActivity.d.map_clear(this,map,mapHelper);
        showMapClass.setMap(map);
        showMapClass.setTitleMarker("عميلك");
        showMapClass.setReduis(8000);
        showMapClass.setDatabaseReference(F.Users().child(user_id_service).child("location"));
        showMapClass.setResourseMarkerDrawAbe(R.drawable.location_person);
        showMapClass.setSearch(false);
        showMapClass.setLocation(GetLocationClass.MY_LOCATION);
        showMapClass.addShowMapEventListener(new ShowMap_EventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {

                location_customer = new Location("");
                location_customer.setLatitude(location.latitude);
                location_customer.setLongitude(location.longitude);

                d.marker_location_update(GetLocationClass.MY_LAT_LNG,map);


            }

            @Override
            public void onKeyExited(String key) {


            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }

    @Override
    public void onRequestRemove(DataSnapshot dataSnapshot) {

        if (d==null){
            d = new D();
        }

        Request = false;

        d.hide_all_sheet();

        start_no_have();

        d.hide_floationg((FloatingActionMenu) findViewById(R.id.service_map_floating_action_menu)
                ,(FloatingActionMenu) findViewById(R.id.no_map_floating_action_menu));


        Toast.makeText(this, "لقد تم الالغاء", Toast.LENGTH_SHORT).show();


    }



    private void start_no_have() {

        if (d==null){
            d = new D();
        }

        if (showMapClass == null){
            showMapClass = new ShowMapClass();
        }

        showMapClass.hide();
        d.hide_all_sheet();
        d.map_clear(this,map,mapHelper);
        getLocation();
        startLocationUpdate();
        startSensor();
        d.hide_floationg((FloatingActionMenu) findViewById(R.id.service_map_floating_action_menu)
                ,(FloatingActionMenu) findViewById(R.id.no_map_floating_action_menu));
        load_my_data();


        location.setZoomAnimation(true);
        location.setOn_Get_Location_And_Zoom_map(new OnGet_Location_And_Zoom_map() {
            @Override
            public void onFinishGettingLocation(LatLng latLng, Location location) {

            }
        });

    }

    @Override
    public void onServiceRemove(DataSnapshot dataSnapshot) {

        Service = false;

        start_no_have();

        d.hide_floationg((FloatingActionMenu) findViewById(R.id.service_map_floating_action_menu)
                ,(FloatingActionMenu) findViewById(R.id.no_map_floating_action_menu));

        Toast.makeText(this, "لقد انتهت الخدمة وانت في وضع انتظار عميل جديد", Toast.LENGTH_SHORT).show();
    }





    @Override
    public void onCheckNo(DataSnapshot dataSnapshot) {

        load_my_data();

    }


    boolean main_data_loaded = false;

    private void load_my_data() {

        F.Drivers().child(F.Uid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshotDrivers = dataSnapshot;



                Drivers drivers = dataSnapshot.getValue(Drivers.class);


                if (drivers == null){
                    return;
                }


                if (drivers.getOnline() == null){

                    drivers.setOnline("false");
                }


                if (isOnline!=null){

                    isOnline = drivers.getOnline();
                }



                fabClick.Online_Ofline_State(drivers.getOnline());



                navigationDrawer.setMyData(dataSnapshot);
                fabClick.fab_ChangeColor(mapStyle);
                fabClick.fab_get_Location(location);
                fabClick.fab_get_Online_ofline(drivers.getOnline(),dataSnapshot,location);


                main_data_loaded = true;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void getLocation(){

        if (location == null){
            location = new GetLocationClass(this,map);
        }

        location.setZoomAnimation(false);
        location.setOn_Get_Location_And_Zoom_map(new OnGet_Location_And_Zoom_map() {
            @Override
            public void onFinishGettingLocation(LatLng latLng, Location location) {

                d.marker_location_update(latLng,map);
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (location!=null){
            location.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }


    }
}


