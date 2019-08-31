package driver.driver.Register.database;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;

import driver.driver.Library.Clasess.SharePref;
import driver.driver.Library.F;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.UUID;

import driver.driver.Register.RegisterActivity;
import driver.driver.Register.xml.RegisterXml;
import driver.driver.maps.MapsActivity;





public class RegisterDatabase {



    public static String name;
    public static String name_family;
    public static String city;
    public static String number_personal_card;
    public static String numbe_iban;
    public static String number_phone;
    public static String number_model_Car;
    public static String number_Car;
    public static String kind_car;
    public static String email;
    public static String password;



    private ProgressDialog progressDialog;
    private RegisterActivity registerActivity;
    private RegisterDatabase registerDatabase;






    public RegisterDatabase(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    public void setAllData_InDatabaseRefrence() {



        HashMap<String, Object> userMap = new HashMap<>();


        if (x.register_radio_sat7a_3ady().isChecked()){

            userMap.put("sat7a_3ady", "true");
        }


        if (x.register_radio_sat7a().isChecked()){

            userMap.put("sat7a_hedrolek", "true");
        }


        if (x.register_radio_bancher().isChecked()){

            userMap.put("bancher", "true");
        }


        if (x.register_radio_banzen().isChecked()){

            userMap.put("banzem", "true");
        }


        if (x.register_radio_bettery().isChecked()){

            userMap.put("bettery", "true");
        }


        if (x.register_radio_open_key().isChecked()){

            userMap.put("opne_key", "true");
        }


        if (x.register_radio_wensh().isChecked()){

            userMap.put("wensh", "true");
        }



        userMap.put("online", "false");
        userMap.put("name", name);
        userMap.put("family", name_family);
        userMap.put("city", city);
        userMap.put("image", Url_image_profile);
        userMap.put("image_car_card", Url_Image_car_card);
        userMap.put("image_personal_card", Url_Image_personal_card);
        userMap.put("image_up_car", Url_Image_up_car);
        userMap.put("image_back_car", Url_Image_back_car);
        userMap.put("image_save", Url_Image_save);
        userMap.put("image_save_car", Url_Image_save_car);
        userMap.put("Token", F.getDaviceToken());
        userMap.put("uid", F.Uid());
        userMap.put("phone", number_phone);
        userMap.put("number_car", number_Car);
        userMap.put("number_iban", numbe_iban);
        userMap.put("number_model_car", number_model_Car);
        userMap.put("number_personal_card", number_personal_card);
        userMap.put("email", email);
        userMap.put("password", password);
        userMap.put("kind_car", kind_car);
        userMap.put("serial", Build.SERIAL);


        F.Drivers().child(F.Uid()).setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();

                x.mRegProgress.setTitle("100%");

                SharePref sharePref = new SharePref(registerActivity);
                sharePref.set("UID",F.Uid());

                Intent mainIntent = new Intent(registerActivity, MapsActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                registerActivity.startActivity(mainIntent);
                registerActivity.finish();


            }
        });
    }


    public  byte[] bytes_image_profile;
    public  byte[] bytes_Image_car_card;
    public  byte[] bytes_Image_personal_card;
    public  byte[] bytes_Image_up_car;
    public  byte[] bytes_Image_back_car;
    public  byte[] bytes_Image_save;
    public  byte[] bytes_Image_save_car;



    private String Url_image_profile;
    private String Url_Image_car_card;
    private String Url_Image_personal_card;
    private String Url_Image_up_car;
    private String Url_Image_back_car;
    private String Url_Image_save;
    private String Url_Image_save_car;



    private RegisterXml x;


    Task<AuthResult> authResult;

    @SuppressLint("LongLogTag")
    public void createEmailAndUploadImage(ProgressDialog mRegProgress, RegisterActivity registerActivity, RegisterDatabase registerDatabase, final FragmentActivity activity, final RegisterXml x){
        this.registerActivity = registerActivity;
        this.progressDialog = mRegProgress;
        this.registerDatabase = registerDatabase;
        this.x = x;


        F.firebaseAuth().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    x.mRegProgress.setTitle("14%");

                    Log.d("createEmailAndUploadImage","isSuccessful");
                    authResult = task;
                    startUploadImage();
                }else {



                    progressDialog.dismiss();

                    Log.d("createEmailAndUploadImage",task.getException().getMessage()+"");
                    Toast.makeText(activity, task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
                }

            }
        });







    }

    private void startUploadImage() {

        final StorageReference thumb_filepath = FirebaseStorage.getInstance().getReference("profile_images/"+ F.Uid() + "/" + UUID.randomUUID().toString());

        thumb_filepath.putBytes(bytes_image_profile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Url_image_profile = taskSnapshot.getDownloadUrl().toString();
                x.mRegProgress.setTitle("20%");
                uploadImages2();

            }
        });

    }

    private void uploadImages2() {

        final StorageReference thumb_filepath = FirebaseStorage.getInstance().getReference("profile_images/"+ F.Uid() + "/" + UUID.randomUUID().toString());

        thumb_filepath.putBytes(bytes_Image_personal_card).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Url_Image_personal_card = taskSnapshot.getDownloadUrl().toString();

                x.mRegProgress.setTitle("30%");

                uploadImages3();

            }
        });
    }

    private void uploadImages3() {

        final StorageReference thumb_filepath = FirebaseStorage.getInstance().getReference("profile_images/"+ F.Uid() + "/" + UUID.randomUUID().toString());

        thumb_filepath.putBytes(bytes_Image_car_card).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Url_Image_car_card = taskSnapshot.getDownloadUrl().toString();

                x.mRegProgress.setTitle("40%");
                uploadImages4();

            }
        });

    }

    private void uploadImages4() {

        final StorageReference thumb_filepath = FirebaseStorage.getInstance().getReference("profile_images/"+ F.Uid() + "/" + UUID.randomUUID().toString());

        thumb_filepath.putBytes(bytes_Image_up_car).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Url_Image_up_car = taskSnapshot.getDownloadUrl().toString();

                x.mRegProgress.setTitle("55%");
                uploadImages5();

            }
        });

    }

    private void uploadImages5() {

        final StorageReference thumb_filepath = FirebaseStorage.getInstance().getReference("profile_images/"+ F.Uid() + "/" + UUID.randomUUID().toString());

        thumb_filepath.putBytes(bytes_Image_back_car).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Url_Image_back_car = taskSnapshot.getDownloadUrl().toString();
                x.mRegProgress.setTitle("70%");

                uploadImages6();

            }
        });

    }

    private void uploadImages6() {

        final StorageReference thumb_filepath = FirebaseStorage.getInstance().getReference("profile_images/"+ F.Uid() + "/" + UUID.randomUUID().toString());

        thumb_filepath.putBytes(bytes_Image_save_car).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                x.mRegProgress.setTitle("80%");
                Url_Image_save_car = taskSnapshot.getDownloadUrl().toString();

                uploadImages7();

            }
        });
    }

    private void uploadImages7() {

        final StorageReference thumb_filepath = FirebaseStorage.getInstance().getReference("profile_images/"+ F.Uid() + "/" + UUID.randomUUID().toString());

        thumb_filepath.putBytes(bytes_Image_save_car).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Url_Image_save_car = taskSnapshot.getDownloadUrl().toString();

                x.mRegProgress.setTitle("90%");
                setAllData_InDatabaseRefrence();

            }
        });
    }


}
