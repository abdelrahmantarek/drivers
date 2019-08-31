package driver.driver.Register.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import driver.driver.Library.F;
import driver.driver.R;
import driver.driver.Register.RegisterActivity;
import driver.driver.Register.database.RegisterDatabase;
import driver.driver.Register.helper;
import driver.driver.Register.view.RegisterListener;
import driver.driver.Register.xml.RegisterXml;

import static android.app.Activity.RESULT_OK;
import static driver.driver.Register.helper.have_text;
import static driver.driver.Register.helper.message;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Register_2_fragment extends Fragment {



    private Uri uri_image_car_up;
    private Uri uri_image_car_back;
    private Uri uri_image_save;
    private Uri uri_image_save_car;



    private static final int GALLERY_Image_Save = 4;
    private static final int GALLERY_Image_Save_Car = 5;
    private static final int GALLERY_Image_Up_Car = 6;
    private static final int GALLERY_Image_Back_Car = 7;

    private RegisterListener registerListener;
    private RegisterActivity registerActivity;
    private RegisterDatabase registerDatabase;

    public Register_2_fragment(RegisterDatabase registerDatabase, RegisterActivity registerActivity, RegisterListener registerListener) {
        // Required empty public constructor
        this.registerListener = registerListener;
        this.registerActivity = registerActivity;
        this.registerDatabase = registerDatabase;
    }


    View view;

    RegisterXml x;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.register_fragment_2_fragment, container, false);

        x = new RegisterXml(view);

        x.mRegProgress = new ProgressDialog(getActivity());



        onClickButtons();



        return view;
    }





    private void onClickButtons() {


        x.reg_image_back_car().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_Image_Back_Car);

            }
        });


        x.reg_image_save().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_Image_Save);

            }
        });

        x.reg_image_save_car().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_Image_Save_Car);

            }
        });

        x.reg_image_up_car().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_Image_Up_Car);

            }
        });



        x.reg_btn_2().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (checkAllEditAndImage()){

                    x.mRegProgress.setTitle("5%");
                    x.mRegProgress.setMessage("من فضلك انتظر حتى يكتمل تحميل المعلومات الخاصة بك الي شركة الفرج");
                    x.mRegProgress.setCanceledOnTouchOutside(false);
                    x.mRegProgress.show();




                    F.Drivers().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            if (dataSnapshot.hasChild(RegisterDatabase.password)){

                                message("هذا الهاتف مسجل بالفعل",view,getActivity());


                                return;
                            }


                            registerDatabase.createEmailAndUploadImage(x.mRegProgress,registerActivity,registerDatabase,getActivity(),x);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }
            }
        });

    }


    private boolean checkAllEditAndImage() {



        if (!have_text(RegisterDatabase.password)){

            registerListener.onSwitchTab.onPosition(0);
            message("يرجى تكملة المعلومات الشخصية هنا اولا",view,getActivity());

            return false;
        }


        if (! have_text(RegisterDatabase.number_phone)){

            registerListener.onSwitchTab.onPosition(0);
            message("يرجى تكملة المعلومات الشخصية هنا اولا",view,getActivity());

            return false;
        }

        if (! have_text(RegisterDatabase.numbe_iban)){

            registerListener.onSwitchTab.onPosition(0);
            message("يرجى تكملة المعلومات الشخصية هنا اولا",view,getActivity());

            return false;
        }

        if (! have_text( RegisterDatabase.number_personal_card )){

            registerListener.onSwitchTab.onPosition(0);
            message("يرجى تكملة المعلومات الشخصية هنا اولا",view,getActivity());

            return false;
        }

        if (! have_text(RegisterDatabase.city )){

            registerListener.onSwitchTab.onPosition(0);
            message("يرجى تكملة المعلومات الشخصية هنا اولا",view,getActivity());

            return false;
        }

        if (! have_text(RegisterDatabase.name)){

            registerListener.onSwitchTab.onPosition(0);
            message("يرجى تكملة المعلومات الشخصية هنا اولا",view,getActivity());

            return false;
        }

        if (! have_text( RegisterDatabase.name_family)){

            registerListener.onSwitchTab.onPosition(0);
            message("يرجى تكملة المعلومات الشخصية هنا اولا",view,getActivity());

            return false;
        }




        if (!helper.isOnline(getActivity())){

            message("من فضلك تأكد من خدمة الانترنت",view,getActivity());

            return false;
        }




        if (!have_text( x.reg_text_kind_car())){

            x.reg_text_kind_car().setError("ادخال نوع السيارة");

            message("يجب عليك ادخال نوع السيارة",view,getActivity());

            return false;
        }

        if (!have_text(x.reg_text_model_car())){

            x.reg_text_model_car().setError("ادخال موديل السيارة");

            message("يجب عليك ادخال موديل السيارة",view,getActivity());

            return false;
        }

        if (!have_text(x.reg_text_model_car())){

            x.reg_text_model_car().setError("ادخال رقم لوحة السيارة");

            message("يجب عليك ادخال رقم لوحة السيارة",view,getActivity());

            return false;
        }





        if (uri_image_car_back==null){


            message("يجب عليك ادخال صورة السيارة من الخلف",view,getActivity());

            return false;
        }

        if (uri_image_car_up==null){


            message("يجب عليك ادخال صورة السيارة من الامام",view,getActivity());
            return false;
        }

        if (uri_image_save==null){


            message("يجب عليك ادخال صورة التأمين",view,getActivity());
            return false;
        }

        if (uri_image_save_car==null){


            message("يجب عليك ادخال صورة اثتمارة السيارة",view,getActivity());
            return false;
        }



        if (!x.register_radio_sat7a_3ady().isChecked() &&
                !x.register_radio_sat7a().isChecked() &&
                !x.register_radio_bancher().isChecked() &&
                !x.register_radio_banzen().isChecked() &&
                !x.register_radio_bettery().isChecked() &&
                !x.register_radio_open_key().isChecked() &&
                !x.register_radio_wensh().isChecked()){


            message("يجب ان تختار نوع خدمة واحدة على الاقل",view,getActivity());

        }




        RegisterDatabase.number_model_Car = helper.getString(x.reg_text_model_car());
        RegisterDatabase.number_Car = helper.getString(x.reg_text_number_car());
        RegisterDatabase.kind_car = helper.getString(x.reg_text_kind_car());



        return true;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data == null){

            return;
        }

        if(resultCode == RESULT_OK){


            switch (requestCode){



                case GALLERY_Image_Back_Car:

                    uri_image_car_back = data.getData();
                    x.reg_image_back_car().setImageURI(uri_image_car_back);
                    setBytes(uri_image_car_back,"back_car");

                    break;

                case GALLERY_Image_Up_Car:

                    uri_image_car_up = data.getData();
                    x.reg_image_up_car().setImageURI(uri_image_car_up);
                    setBytes(uri_image_car_up,"car_up");

                    break;

                case GALLERY_Image_Save:

                    uri_image_save = data.getData();
                    x.reg_image_save().setImageURI(uri_image_save);
                    setBytes(uri_image_save,"image_save");

                    break;

                case GALLERY_Image_Save_Car:

                    uri_image_save_car = data.getData();
                    x.reg_image_save_car().setImageURI(uri_image_save_car);
                    setBytes(uri_image_save_car,"image_save_car");

                    break;
            }

        }

    }


    public void setBytes(Uri file_path,String State){

        try {

            InputStream inputStream = getActivity().getContentResolver().openInputStream(file_path);
            Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);



            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//Compression quality, here 100 means no compression, the storage of compressed data to baos
            int options = 70;
            while (baos.toByteArray().length / 1024 > 400) {  //Loop if compressed picture is greater than 400kb, than to compression
                baos.reset();//Reset baos is empty baos
                imgBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//The compression options%, storing the compressed data to the baos
                options -= 10;//Every time reduced by 10
            }



            switch (State){

                case "back_car":

                    registerDatabase.bytes_Image_back_car = baos.toByteArray();

                    break;

                case "car_up":

                    registerDatabase.bytes_Image_up_car = baos.toByteArray();

                    break;

                case "image_save":

                    registerDatabase.bytes_Image_save = baos.toByteArray();

                    break;

                case "image_save_car":

                    registerDatabase.bytes_Image_save_car = baos.toByteArray();

                    break;


            }






        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


























}
