package driver.driver;

/**
 * Created by abdo tarek on 12/24/2017.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URL;

import driver.driver.Library.F;

import driver.driver.Library.StateClass;
import driver.driver.maps.MapsActivity;


public class MyFirebaseMessagingService extends  FirebaseMessagingService {


    int mNotificationId = 1;
    NotificationManager mManager;


    public MyFirebaseMessagingService( ) {

    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        String message = remoteMessage.getData().get("message");
        String user_id = remoteMessage.getData().get("uid");

        String click_action = remoteMessage.getData().get("click_action");
        String image = remoteMessage.getData().get("image");
        String name = remoteMessage.getData().get("body");


        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.startShowNotification();


        Log.d("MessagingService", image+ message + user_id + click_action + name + "notificatiooooooooooooooo");

        //  Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);


        //  sendDefault(name,click_action,largeIcon);

    }




    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    NotificationManager mNotificationManager;
    Uri alarmSound;

    public class NotificationRequest {

        String imgUrl="";
        String appImgUrl="";
        String notifTitle="";
        String notifText="لديك طلب";
        RemoteViews contentViewBig,contentViewSmall;



        public void startShowNotification(){


          mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

          alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);



            StateClass stateClass = new StateClass();
            stateClass.setOnGetStateEventListener(new StateClass.onGetStateEventListener() {
                @Override
                public void onNo(DataSnapshot your_data_colunm_Users) {



                }

                @Override
                public void onService(final DataSnapshot dataSnapshot) {




                }

                @Override
                public void onRequest(DataSnapshot dataSnapshot) {



                    ShowNotification();


                }

                @Override
                public void onRequestRemove(DataSnapshot dataSnapshot) {


                    if (mNotificationManager!=null){
                        mNotificationManager.cancelAll();
                    }




                }

                @Override
                public void onServiceRemove(DataSnapshot dataSnapshot) {


                }
            });






        }

        private void ShowNotification() {


            try {






                long when = System.currentTimeMillis();

                contentViewBig = new RemoteViews(getPackageName(), R.layout.notification_custom);
                contentViewSmall = new RemoteViews(getPackageName(),R.layout.notification_custom_small);
                //Null and Empty checks for your Key Value Pairs
                if(imgUrl!=null && !imgUrl.isEmpty()) {
                    URL imgUrlLink = new URL(imgUrl);
                    contentViewBig.setImageViewBitmap(R.id.image_pic, BitmapFactory.decodeStream(imgUrlLink.openConnection().getInputStream()));
                }
                if(appImgUrl!=null && !appImgUrl.isEmpty()) {
                    URL appImgUrlLink = new URL(appImgUrl);
                    contentViewBig.setImageViewBitmap(R.id.image_app, BitmapFactory.decodeStream(appImgUrlLink.openConnection().getInputStream()));
                    contentViewSmall.setImageViewBitmap(R.id.image_app, BitmapFactory.decodeStream(appImgUrlLink.openConnection().getInputStream()));
                }
                if(notifTitle!=null && !notifTitle.isEmpty()) {
                    contentViewBig.setTextViewText(R.id.title, notifTitle);
                    contentViewSmall.setTextViewText(R.id.title, notifTitle);
                }

                if(notifText!=null && !notifText.isEmpty()) {
                    contentViewBig.setTextViewText(R.id.text, notifText);
                    contentViewSmall.setTextViewText(R.id.text, notifText);
                }

                Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_location_online)
                        .setCustomContentView(contentViewSmall)
                        .setCustomBigContentView(contentViewBig)
                        .setContentTitle("Custom Notification")
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setWhen(when)
                        .setSound(alarmSound);



                mNotificationManager.notify(1, notificationBuilder.build());


            } catch (Throwable t) {
                Log.d("MYFCMLIST", "Error parsing FCM message", t);
            }

        }

    }

}

