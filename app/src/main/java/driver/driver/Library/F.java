package driver.driver.Library;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;


public class F {

    public static int ScreenHieght;
    public static int ScreenWidth;

    public static int getScreenWidth() {
        return ScreenWidth;
    }

    public static void setScreenWidth(int screenWidth) {
        ScreenWidth = screenWidth;
    }

    public static int getScreenHieght() {
        return ScreenHieght;
    }

    public static void setScreenHieght(int screenHieght) {
        ScreenHieght = screenHieght;
    }


    public static DatabaseReference Users(){
        return reference().child("Users");
    }

    public static DatabaseReference Drivers(){
        return reference().child("Drivers");
    }


    public static DatabaseReference reference(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseAuth firebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser firebaseUser(){
        return firebaseAuth().getCurrentUser();
    }

    public static String Uid(){
        return mAuth().getUid();
    }

    public static FirebaseUser mAuth(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }


    public static String getDaviceToken(){
        return FirebaseInstanceId.getInstance().getToken();
    }


    public static DatabaseReference OnlineDrivers() {
        return reference().child("Online_Drivers");
    }



    public static void message(String message, View view, Context context, int color) {

        Snackbar snackbar = Snackbar.make(view, message+"", Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, color));
        snackbar.show();
    }

    public static void GoGoogleMap(LatLng latLng, Activity activity){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?daddr="+ latLng.latitude+","+latLng.longitude));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    public static void hide_all_sheet(){


    }

}
