package driver.driver.Library;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;



public class LatLngBound_Tow_point {

    private  OnLatLngBound onLatLngBound;

    public interface OnLatLngBound{
        void onFinish();
    }


    private LatLng latLng_1;
    private LatLng latLngB_2;
    private GoogleMap map;

    public LatLngBound_Tow_point(LatLng latLng_1, LatLng latLngB_2, GoogleMap map) {
        this.latLng_1 = latLng_1;
        this.latLngB_2 = latLngB_2;
        this.map = map;
    }

    public void setOnLatLngBound(OnLatLngBound onLatLngBound) {
        this.onLatLngBound = onLatLngBound;

        set();
    }

    public void set(){

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(latLng_1);
        builder.include(latLngB_2);
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);

        map.animateCamera(cu, new GoogleMap.CancelableCallback(){

            public void onCancel(){

            }

            public void onFinish(){

                CameraUpdate zout = CameraUpdateFactory.zoomBy(-1.0f);
                map.animateCamera(zout);

                onLatLngBound.onFinish();

            }
        });


        //createBoundsWithMinDiagonal(F.getLatLng(),cameraPosition1.target);
    }


}
