package driver.driver.Library.Listener;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface OnGet_Location_And_Zoom_map {

    void onFinishGettingLocation(LatLng latLng, Location location);
}
