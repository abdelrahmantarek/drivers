package driver.driver.Library.Clasess;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import driver.driver.Library.Listener.OnGet_Address_From_Location;


public class GetAddressClass {

    OnGet_Address_From_Location getAddressEventListener;
    Location location;



    private  Handler handler = new Handler();
    private  Runnable runnable_a;

    private Context context;

    private  String address_a = null;

    private List<Address> list_a  = new ArrayList<>();


    private LatLng location_A;

    private ArrayList<String> strings = new ArrayList<>();

    private String LastAdress = "";


    public GetAddressClass(Context context,Location location){

        this.location = location;
        this.context = context;
    }

    public void setOn_Get_Address_From_Location(OnGet_Address_From_Location getAddress_fromCenterCamera){

        this.getAddressEventListener = getAddress_fromCenterCamera;

        getAddressFromLocation();
    }


    private void getAddressFromLocation() {





        // final Geocoder geocoder = new Geocoder(F.getContext(), Locale.getDefault());

        new Thread(new Runnable() {
            public void run() {

                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(context, Locale.getDefault());


                try {
                    addresses = geocoder.getFromLocation( location.getLatitude(),    location.getLongitude(), 1);

                    if (addresses.isEmpty()){
                        return;
                    }
                    // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    final String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                    LastAdress = address;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();



        runnable_a = new Runnable() {
            @Override
            public void run() {

                time++;

                if (!LastAdress.equals("")){

                    getAddress(LastAdress);

                    LastAdress="";
                    Log.d("ActionListenersss","1");

                    handler.removeCallbacksAndMessages(runnable_a);
                    return;
                }


                if (time>20){
                    handler.removeCallbacksAndMessages(runnable_a);
                    return;
                }


                handler.postDelayed(runnable_a,1000);
            }
        };

        handler.post(runnable_a);

    }
    private int time = 0;






    private void getAddress(String address) {


        getAddressEventListener.onAddressCome(address,location);


    }



}
