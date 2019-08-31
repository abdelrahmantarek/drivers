package driver.driver.Library.Listener;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface OnLocation_Update {

   void onLocationChanged(Location location, LatLng latLng);
   void onLocationStop();
}
