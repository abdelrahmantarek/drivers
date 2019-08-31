package driver.driver.maps.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;

import driver.driver.Library.Clasess.GetLocationClass;
import driver.driver.Library.Clasess.MapStyle;
import driver.driver.Library.F;
import driver.driver.Library.Listener.OnGet_Location_And_Zoom_map;
import driver.driver.Library.MasterPermission;
import driver.driver.Login.LoginActivity;
import driver.driver.R;
import driver.driver.Register.helper;
import driver.driver.maps.Database.Database;
import driver.driver.model.Drivers;
import driver.driver.model.Service;
import driver.driver.model.Users;

public class FabClick {


    private FloatingActionButton getLocation,changeColor,online_offline;
    private FloatingActionMenu floating_action_menu;

    private Activity activity;
    private Database database;


    private GoogleMap map;


    private FloatingActionButton fab_getLocation,fab_show_details,fab_location_navigation_to_client,fab_go_car_client;
    public FloatingActionMenu fab_floating_action_menu;





    public FabClick(Activity activity,GoogleMap map) {
        this.activity = activity;

        this.map = map;

        floating_action_menu = activity.findViewById(R.id.no_map_floating_action_menu);
        getLocation = activity.findViewById(R.id.no_map_fab_get_location_map);
        changeColor = activity.findViewById(R.id.no_map_fab_change_color_map);
        online_offline = activity.findViewById(R.id.no_map_fab_online_oflline_map);




        fab_floating_action_menu = activity.findViewById(R.id.service_map_floating_action_menu);
        fab_getLocation = activity.findViewById(R.id.service_map_fab_get_location_map);
        fab_show_details = activity.findViewById(R.id.service_map_fab_show_details);
        fab_location_navigation_to_client = activity.findViewById(R.id.service_map_fab_location_the_client);
        fab_go_car_client = activity.findViewById(R.id.service_map_fab_go_car_client);








    }


    public void fab_service(final Service service, String user_id, final Location location_customer){


        fab_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!helper.isOnline(activity)){

                    F.message("من فضلك تأكد من خدمة الانترنت",((activity.findViewById(R.id.login_view))),activity,R.color.red_A700);

                    return;
                }


                getLocation();

            }
        });


        fab_show_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!helper.isOnline(activity)){

                    F.message("من فضلك تأكد من خدمة الانترنت",((activity.findViewById(R.id.login_view))),activity,R.color.red_A700);

                    return;
                }



                BottomSheetService.show();

            }
        });




        fab_location_navigation_to_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!helper.isOnline(activity)){

                    F.message("من فضلك تأكد من خدمة الانترنت",((activity.findViewById(R.id.login_view))),activity,R.color.red_A700);

                    return;
                }




                if (location_customer==null){
                    return;
                }

                F.GoGoogleMap(new LatLng(location_customer.getLatitude(),location_customer.getLongitude()),activity);

            }
        });





        fab_go_car_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!helper.isOnline(activity)){

                    F.message("من فضلك تأكد من خدمة الانترنت",((activity.findViewById(R.id.login_view))),activity,R.color.red_A700);

                    return;
                }




                F.GoGoogleMap(new LatLng(service.getFrom_lat(),service.getFrom_lng()),activity);

            }
        });



        ((activity.findViewById(R.id.service_map_fab_call_the_client))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!helper.isOnline(activity)){

                    F.message("من فضلك تأكد من خدمة الانترنت",((activity.findViewById(R.id.login_view))),activity,R.color.red_A700);

                    return;
                }




                MasterPermission masterPermission = new MasterPermission(activity);

                if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    masterPermission.addPermissionCallPhone();
                    return;
                }


                if (service.getPhone().length() > 9) {

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + service.getPhone().length()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    activity.startActivity(intent);
                }


            }
        });




    }




    GetLocationClass getLocationClass;

    private void getLocation() {

        if (getLocationClass == null){
            getLocationClass = new GetLocationClass(activity,map);
        }


        getLocationClass.setZoomAnimation(true);
        getLocationClass.setOn_Get_Location_And_Zoom_map(new OnGet_Location_And_Zoom_map() {
            @Override
            public void onFinishGettingLocation(LatLng latLng, Location location) {




            }
        });






    }



    public static void message(String message, View view, Context context, int color) {

        Snackbar snackbar = Snackbar.make(view, message+"", Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, color));
        snackbar.show();
    }




    public void Online_Ofline_State(final String online) {


        if (online.equals("true")){

            message("انت الان نشط تنتظر عميل",(activity.findViewById(R.id.drawer_layout)),activity,android.R.color.holo_green_dark);
            online_offline.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_location_online));

        }else {

            message("انت الان غير نشط",(activity.findViewById(R.id.drawer_layout)),activity,android.R.color.holo_red_dark);
            online_offline.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_location_off_red));
        }




    }

    public void fab_get_Location(final GetLocationClass location) {

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                location.setZoomAnimation(true);
               location.setOn_Get_Location_And_Zoom_map(new OnGet_Location_And_Zoom_map() {
                    @Override
                    public void onFinishGettingLocation(LatLng latLng, Location location) {

                    }
                });


                floating_action_menu.close(true);

            }
        });
    }

    public void fab_ChangeColor(final MapStyle mapStyle) {


        if (!helper.isOnline(activity)){

            F.message("من فضلك تأكد من خدمة الانترنت",((activity.findViewById(R.id.login_view))),activity,R.color.red_A700);

            return;
        }



        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mapStyle.changeColor();
            }
        });
    }

    public void fab_get_Online_ofline(final String online, final DataSnapshot dataSnapshot, final GetLocationClass location) {

        online_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!helper.isOnline(activity)){

                    F.message("من فضلك تأكد من خدمة الانترنت",((activity.findViewById(R.id.login_view))),activity,R.color.red_A700);

                    return;
                }



                if (database == null){
                    database = new Database(activity);
                }

                if (online == null){

                    database.set_Online_location(dataSnapshot,location);

                    return;
                }


               if (online.equals("true")){

                   database.set_Offline_location(dataSnapshot,location);

                }else {

                   database.set_Online_location(dataSnapshot,location);
                }



            }
        });
    }




}
