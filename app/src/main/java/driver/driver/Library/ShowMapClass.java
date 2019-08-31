package driver.driver.Library;

import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


import driver.driver.model.Users;


public class ShowMapClass {

    public boolean driverFound = false;
    public ShowMap_EventListener showMap_eventListener;


    public List<Marker> MARKERS_CUSTOMER_ONLINE = new ArrayList<Marker>();
    public static List<String> DriverOnlineKey = new ArrayList<String>();


    private GeoQuery GeoQuery_CUSTOMER_ONLINE;

    private static boolean You_Have_Show_Customer_Online = false;

    private static String TitleMarker = "";
    private DatabaseReference databaseReference;
    private static int ResourseMarkerDrawAbe;
    private static float Reduis = 700;


    private Location location;


    public GoogleMap map;

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public  Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public boolean isSearch = false;

    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }

    public void addShowMapEventListener(ShowMap_EventListener showMap_eventListener){

         this.showMap_eventListener = showMap_eventListener;



         show();
     }


    private void show(){


        You_Have_Show_Customer_Online = true;


        if (GeoQuery_CUSTOMER_ONLINE !=null){
            GeoQuery_CUSTOMER_ONLINE.removeAllListeners();
        }

        if (MARKERS_CUSTOMER_ONLINE.size()>0){
            MARKERS_CUSTOMER_ONLINE.clear();
        }


        if (isSearch){

            SearchFromthisPoint();

            return;
        }




        GeoFire GeoFire_CUSTOMER_ONLINE = new GeoFire(databaseReference);


        GeoQuery_CUSTOMER_ONLINE = GeoFire_CUSTOMER_ONLINE.queryAtLocation(new GeoLocation(getLocation().getLatitude(), getLocation().getLongitude()), getReduis());

        GeoQuery_CUSTOMER_ONLINE.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(final String key,final GeoLocation location) {

                if (You_Have_Show_Customer_Online){

                    if (key != null && !key.equals(F.Uid())) {

                        for (Marker markerIt : MARKERS_CUSTOMER_ONLINE) {
                            if (markerIt.getTag()!=null){
                                if (markerIt.getTag().equals(key)) {
                                    return;
                                }
                            }

                        }



                        driverFound = true;

                        final LatLng driverLocation = new LatLng(location.latitude, location.longitude);

                        Marker mDriverMarker = getMap().addMarker(new MarkerOptions().flat(isFlat()).position(driverLocation).title(TitleMarker).icon(BitmapDescriptorFactory.fromResource(getResourseMarkerDrawAbe())));
                        MARKERS_CUSTOMER_ONLINE.add(mDriverMarker);
                        DriverOnlineKey.add(key);
                        mDriverMarker.setTag(key);


                        showMap_eventListener.onKeyEntered(key,location);

                    }



                }


            }

            @Override
            public void onKeyExited(String key) {

                if (You_Have_Show_Customer_Online){

                    if (key != null) {
                        for (Marker markerIt : MARKERS_CUSTOMER_ONLINE) {
                            if (markerIt.getTag() != null) {
                                if (markerIt.getTag().equals(key)) {
                                    markerIt.remove();
                                }
                            }
                        }
                    }

                    showMap_eventListener.onKeyExited(key);
                }


            }

            @Override
            public void onKeyMoved(String key, final GeoLocation location) {
                if (You_Have_Show_Customer_Online){
                    if (key!=null){
                        for(final Marker markerIt : MARKERS_CUSTOMER_ONLINE){
                            if (  markerIt.getTag() !=null) {
                                if (markerIt.getTag().equals(key)) {

                                    Location location1 = new Location("s");
                                    location1.setLatitude(location.latitude);
                                    location1.setLongitude(location.longitude);
                                    animateMarker(markerIt,location1,key);

                                    final LatLng driverLocation = new LatLng(location.latitude, location.longitude);

                                    markerIt.setPosition(driverLocation);


                                    showMap_eventListener.onKeyMoved(key,location);
                                }
                            }
                        }
                    }
                }


            }

            @Override
            public void onGeoQueryReady() {
                showMap_eventListener.onGeoQueryReady();
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

                showMap_eventListener.onGeoQueryError(error);

            }
        });




    }





    public float Plus = 0.200f;
    public float Start = 0.200f;
    public float End = 1.200f;

    public void SearchFromthisPoint(){


        GeoFire GeoFire_CUSTOMER_ONLINE = new GeoFire(databaseReference);


        GeoQuery_CUSTOMER_ONLINE = GeoFire_CUSTOMER_ONLINE.queryAtLocation(new GeoLocation(getLocation().getLatitude(), getLocation().getLongitude()), Start);

        GeoQuery_CUSTOMER_ONLINE.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(final String key,final GeoLocation location) {

                if (You_Have_Show_Customer_Online){

                    if (key != null && !key.equals(F.Uid())) {

                        for (Marker markerIt : MARKERS_CUSTOMER_ONLINE) {
                            if (markerIt.getTag()!=null){
                                if (markerIt.getTag().equals(key)) {
                                    return;
                                }
                            }

                        }

                        showMap_eventListener.onKeyEntered(key,location);

                        final LatLng driverLocation = new LatLng(location.latitude, location.longitude);


                        F.Users().child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Users drivers = dataSnapshot.getValue(Users.class);

                                if (drivers!=null){

                                    if (drivers.getRotation()!=0.0){

                                        Marker mDriverMarker = getMap().addMarker(new MarkerOptions().rotation(drivers.getRotation()).flat(isFlat()).position(driverLocation).title(TitleMarker).icon(BitmapDescriptorFactory.fromResource(getResourseMarkerDrawAbe())));
                                        MARKERS_CUSTOMER_ONLINE.add(mDriverMarker);
                                        DriverOnlineKey.add(key);
                                        mDriverMarker.setTag(key);

                                    }else {

                                        Marker mDriverMarker = getMap().addMarker(new MarkerOptions().rotation(drivers.getRotation()).flat(isFlat()).position(driverLocation).title(TitleMarker).icon(BitmapDescriptorFactory.fromResource(getResourseMarkerDrawAbe())));
                                        MARKERS_CUSTOMER_ONLINE.add(mDriverMarker);
                                        DriverOnlineKey.add(key);
                                        mDriverMarker.setTag(key);
                                    }

                                }else {
                                    Marker mDriverMarker = getMap().addMarker(new MarkerOptions().flat(isFlat()).position(driverLocation).title(TitleMarker).icon(BitmapDescriptorFactory.fromResource(getResourseMarkerDrawAbe())));
                                    MARKERS_CUSTOMER_ONLINE.add(mDriverMarker);
                                    DriverOnlineKey.add(key);
                                    mDriverMarker.setTag(key);
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });




                    }



                }


            }

            @Override
            public void onKeyExited(String key) {

                if (You_Have_Show_Customer_Online){

                    if (key != null) {
                        for (Marker markerIt : MARKERS_CUSTOMER_ONLINE) {
                            if (markerIt.getTag() != null) {
                                if (markerIt.getTag().equals(key)) {
                                    markerIt.remove();
                                }
                            }
                        }
                    }

                    showMap_eventListener.onKeyExited(key);
                }


            }

            @Override
            public void onKeyMoved(String key, final GeoLocation location) {
                if (You_Have_Show_Customer_Online){
                    if (key!=null){
                        for(final Marker markerIt : MARKERS_CUSTOMER_ONLINE){
                            if (  markerIt.getTag() !=null) {
                                if (markerIt.getTag().equals(key)) {

                                    Location location1 = new Location("s");
                                    location1.setLatitude(location.latitude);
                                    location1.setLongitude(location.longitude);
                                    animateMarker(markerIt,location1,key);

                                    final LatLng driverLocation = new LatLng(location.latitude, location.longitude);

                                    markerIt.setPosition(driverLocation);



                                    showMap_eventListener.onKeyMoved(key,location);
                                }
                            }
                        }
                    }
                }


            }

            @Override
            public void onGeoQueryReady() {



                if (Start>End) {

                    showMap_eventListener.onGeoQueryReady();

                    return;

                }else {

                    Log.d("GGGGGGGGGGGGGGG" , Start +"");

                    Start = Plus + Start;

                    SearchFromthisPoint();
                }


            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

                showMap_eventListener.onGeoQueryError(error);

            }
        });



    }


    public void hide(){

        if (GeoQuery_CUSTOMER_ONLINE!=null){
            GeoQuery_CUSTOMER_ONLINE.removeAllListeners();
        }

        if (MARKERS_CUSTOMER_ONLINE.size()>0){
            MARKERS_CUSTOMER_ONLINE.clear();
        }

        if (DriverOnlineKey.size()>0){
           DriverOnlineKey.clear();
        }

        You_Have_Show_Customer_Online = false;

    }

    public  String getTitleMarker() {
        return TitleMarker;
    }

    public boolean flat = false;

    public boolean isFlat() {
        return flat;
    }

    public void setFlat(boolean flat) {
        this.flat = flat;
    }

    public  void setTitleMarker(String titleMarker) {
        TitleMarker = titleMarker;
    }

    public  DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public  int getResourseMarkerDrawAbe() {
        return ResourseMarkerDrawAbe;
    }

    public  void setResourseMarkerDrawAbe(int resourseMarkerDrawAbe) {
        ResourseMarkerDrawAbe = resourseMarkerDrawAbe;
    }

    public  float getReduis() {
        return Reduis;
    }

    public void setReduis(float reduis) {
        Reduis = reduis;
    }

    public void animateMarker(final Marker marker, final Location location, final String user_id) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final LatLng startLatLng = marker.getPosition();
        final double startRotation = marker.getRotation();
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);

                final double lng = t * location.getLongitude() + (1 - t)
                        * startLatLng.longitude;
                final double lat = t * location.getLatitude() + (1 - t)
                        * startLatLng.latitude;

                final float rotation = (float) (t * location.getBearing() + (1 - t)
                        * startRotation);


                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }


}
