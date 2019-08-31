package driver.driver.maps.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import driver.driver.Library.F;
import driver.driver.Library.Listener.BottomSheetEvent;
import driver.driver.Library.helper.BottomSheet;
import driver.driver.R;
import driver.driver.maps.Database.Database;
import driver.driver.model.Service;

public class BottomSheetService {

    private static BottomSheet bottomSheet;
    private Activity activity;



    private static boolean isShow = false;


    private Database database;


    public BottomSheetService(Activity activity, final Database database) {
        this.activity = activity;
        this.database = database;




        bottomSheet = new BottomSheet((activity.findViewById(R.id.sheet_layout_service)));
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




    public void setDataService(Service service, final String user_id) {


        F.Users().child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Service service1 = dataSnapshot.getValue(Service.class);

                TextView service_name_customer = activity.findViewById(R.id.service_name_customer);
                service_name_customer.setText(service1.getName());


                CircleImageView service_image_customer = activity.findViewById(R.id.service_image_customer);

                if (service1.getImage().equals("default")){

                    service_image_customer.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_avatar));

                }else {

                    Glide.with(activity.getApplicationContext()).load(service1.getImage()).into(service_image_customer);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
	

        TextView service_kind = activity.findViewById(R.id.service_kind);
        service_kind.setText(service.getService_kind());


        TextView service_from = activity.findViewById(R.id.service_from);
        service_from.setText(service.getText_from());


        TextView service_to = activity.findViewById(R.id.service_to);
        service_to.setText(service.getText_to());



        TextView service_price = activity.findViewById(R.id.service_price);
        service_price.setText(service.getService_price());








        ((activity.findViewById(R.id.service_delete_service))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.deleteService(user_id);
            }
        });



    }


}
