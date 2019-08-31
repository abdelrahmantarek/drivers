package driver.driver.Library;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MapHelper {

    GoogleMap map;
    Marker_My_Location marker_my_location;

    public MapHelper(GoogleMap map) {
        this.map = map;
        marker_my_location = new Marker_My_Location();
    }


    public void setOnClearMapListener(OnClearMapListener onClearMapListener) {
        this.onClearMapListener = onClearMapListener;
        map.clear();
        marker_my_location.Remove();
        Marker_My_Location.isMarkerOn = false;

        onClearMapListener.onClear();
    }

    public void setOnClearMapListener(OnClearMapListener onClearMapListener, LatLng latLng) {
        this.onClearMapListener = onClearMapListener;
        map.clear();
        marker_my_location.Remove();
        marker_my_location.Add(latLng,map);
    }

    public void AddMarker(LatLng latLng){
        marker_my_location.Add(latLng,map);

    }


    public void setOnSignOutListener(OnSignOutListener onSignOutListener) {
        this.onSignOutListener = onSignOutListener;
         Marker_My_Location.isMarkerOn = false;

         onSignOutListener.onSignOut();
    }

    private OnClearMapListener onClearMapListener;
    public interface OnClearMapListener{
       void onClear();
    }

    private OnSignOutListener onSignOutListener;
    public interface OnSignOutListener{
       void onSignOut();
    }


    public void onStop(){

        Marker_My_Location.isMarkerOn = false;
        marker_my_location.Remove();
    }

}
