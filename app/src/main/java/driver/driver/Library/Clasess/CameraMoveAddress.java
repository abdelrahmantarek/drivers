package driver.driver.Library.Clasess;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import driver.driver.Library.Listener.OnGet_Address_From_Camera;
import driver.driver.Library.Listener.OnGet_Address_From_Location;


public class CameraMoveAddress {


    private GoogleMap map;
    private Context context;
    private OnGet_Address_From_Camera onGetAddressFromCamera;


    public CameraMoveAddress(GoogleMap map, Context context) {
        this.map = map;
        this.context = context;
    }


    public void setOn_Get_Address_From_Camera(final OnGet_Address_From_Camera onGetAddressFromCamera) {
        this.onGetAddressFromCamera = onGetAddressFromCamera;

        setCamreaMoveListener();
    }

    private void setCamreaMoveListener() {


        onGetAddressFromCamera.onCameraMove();


        Location location = new Location("a");
        location.setLatitude(map.getCameraPosition().target.latitude);
        location.setLongitude(map.getCameraPosition().target.longitude);




        GetAddressClass getLocationClass = new GetAddressClass(context,location);

        getLocationClass.setOn_Get_Address_From_Location(new OnGet_Address_From_Location() {
            @Override
            public void onAddressCome(String address, Location location) {

                onGetAddressFromCamera.onCameraGetAddress(address,location);

                map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {

                        onGetAddressFromCamera.onCameraMove();

                        onCameraStopListener();
                    }
                });
            }
        });
    }


    private void onCameraStopListener() {

        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                getLocationAdress(map);
            }
        });

    }

    private void getLocationAdress(GoogleMap map) {


        Location location = new Location("a");
        location.setLatitude(map.getCameraPosition().target.latitude);
        location.setLongitude(map.getCameraPosition().target.longitude);

       GetAddressClass getLocationClass = new GetAddressClass(context,location);

        getLocationClass.setOn_Get_Address_From_Location(new OnGet_Address_From_Location() {
            @Override
            public void onAddressCome(String address, Location location) {

                onGetAddressFromCamera.onCameraGetAddress(address,location);
            }
        });
    }


    public void stop(){

        if (map!=null){
            map.setOnCameraMoveListener(null);
            map.setOnCameraIdleListener(null);
        }

    }

    public void start(){
        if (map!=null){
            setCamreaMoveListener();
        }
    }
}
