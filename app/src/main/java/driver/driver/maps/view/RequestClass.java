package driver.driver.maps.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import driver.driver.Library.F;
import driver.driver.Library.LatLngBound_Tow_point;
import driver.driver.Library.Listener.BottomSheetEvent;
import driver.driver.Library.helper.BottomSheet;
import driver.driver.R;
import driver.driver.maps.Database.Database;
import driver.driver.model.Service;

public class RequestClass {


    public static BottomSheet bottomSheet;
    private static boolean isShow = false;
    private Activity activity;
    private String user_id;
    private Database database;


    public RequestClass(final Activity activity, final Database database) {
        this.activity = activity;
        this.database = database;


        bottomSheet = new BottomSheet((activity.findViewById(R.id.sheet_layout_request)));
        bottomSheet.addBottomSheetEvent(new BottomSheetEvent() {
            @Override
            public void onSheetExpandle() {

                isShow = true;

            }

            @Override
            public void onSheetCollapsed() {

                isShow = false;

            }
        });




        this.user_id = user_id;


        ((activity.findViewById(R.id.request_view_accept_btn))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_id==null){
                    return;
                }

                database.AcceptRequest(user_id,activity);

                bottomSheet.collapsed();

            }
        });








        ((activity.findViewById(R.id.request_view_delete_btn))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_id==null){
                    return;
                }

                database.CancelRequest(user_id);

                bottomSheet.collapsed();

            }
        });



    }



    public static void show(){
        if (bottomSheet!=null){
            bottomSheet.expandle();
        }
    }

    public static void hide(){

        if (bottomSheet!=null){
            bottomSheet.collapsed();
        }
    }



    public static boolean isShow(){
        return isShow;
    }

    public static boolean isHide(){

        return !isShow;
    }





    public void addDataUser(Service service,String user_id) {
        this.user_id = user_id;


        F.Users().child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Service service1 = dataSnapshot.getValue(Service.class);

                TextView request_name_user = activity.findViewById(R.id.request_name_user);
                request_name_user.setText(service1.getName());


                CircleImageView circleImageView = activity.findViewById(R.id.request_image_user);

                Glide.with(activity.getApplicationContext()).load(service1.getImage()).into(circleImageView);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView request_text_from = activity.findViewById(R.id.request_text_from);
        request_text_from.setText(service.getText_from());


        TextView request_text_to = activity.findViewById(R.id.request_text_to);
        request_text_to.setText(service.getText_to());


        TextView request_text_price = activity.findViewById(R.id.request_text_price);
        request_text_price.setText(service.getService_price());


        TextView request_kind_request = activity.findViewById(R.id.request_kind_request);
        request_kind_request.setText(service.getService_kind());





    }
}
