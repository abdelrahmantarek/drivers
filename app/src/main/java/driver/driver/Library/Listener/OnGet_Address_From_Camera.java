package driver.driver.Library.Listener;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface OnGet_Address_From_Camera {
    void onCameraMove();
    void onCameraGetAddress(String Address, Location latLng);


}
