package driver.driver.Library.Listener;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface OnGet_Address_From_EditComplete {

    void onFinishGettingAddress(String address, Location latLng);
    void onTextChange(String s);
}