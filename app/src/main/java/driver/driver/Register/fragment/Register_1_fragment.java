package driver.driver.Register.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import driver.driver.R;
import driver.driver.Register.RegisterActivity;
import driver.driver.Register.database.RegisterDatabase;
import driver.driver.Register.helper;
import driver.driver.Register.view.RegisterListener;
import driver.driver.Register.xml.RegisterXml;

import static android.app.Activity.RESULT_OK;
import static driver.driver.Register.helper.have_text;
import static driver.driver.Register.helper.message;
import static driver.driver.Register.helper.isOnline;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Register_1_fragment extends Fragment {




    private static final int GALLERY_Profile_Image = 1;
    private static final int GALLERY_car_card = 2;
    private static final int GALLERY_image_prsonal_card = 3;


    private Uri uri_image_profile;
    private Uri uri_car_card_image;
    private Uri uri_image_personal_card;


    private RegisterListener registerListener;
    private RegisterActivity registerActivity;
    private RegisterDatabase registerDatabase;

    public Register_1_fragment(RegisterDatabase registerDatabase, RegisterActivity registerActivity, RegisterListener registerListener) {
        // Required empty public constructor
        this.registerListener = registerListener;
        this.registerActivity = registerActivity;
        this.registerDatabase = registerDatabase;
    }


    RegisterXml x;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.register_fragment_1_fragment, container, false);

        x = new RegisterXml(view);

        onClickButtons();


        return view;
    }

    private void onClickButtons() {


        x.reg_profile_image().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_Profile_Image);
            }
        });


        x.reg_image_personal_card().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_image_prsonal_card);
            }
        });


        x.reg_image_car_card().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_car_card);
            }
        });



        x.reg_btn_1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (checkAllEditAndImage()){

                    registerListener.onSwitchTab.onPosition(1);
                }
            }
        });

    }


    private boolean checkAllEditAndImage() {


        if (!isOnline(getActivity())){

            message("من فضلك تأكد من خدمة الانترنت",view,getActivity());

            return false;
        }


        if (!have_text(x.name())){

            x.name().setError("ادخال الاسم");

            message("يجب عليك ادخال الاسم",view,getActivity());

            return false;
        }

        if (!have_text(x.reg_text_name_family())){

            x.reg_text_name_family().setError("ادخال اسم العائلة");

            message("يجب عليك ادخال اسم العائلة",view,getActivity());

            return false;
        }

        if (!have_text(x.reg_text_city())){

            x.reg_text_city().setError("ادخال اسم مدينتك");

            message("يجب عليك ادخال اسم المدينة",view,getActivity());

            return false;
        }


        if (!have_text(x.reg_number_personal())){

            x.reg_number_personal().setError("ادخال رقم الهوية الخاص بك");

            message("يجب عليك ادخال رقم الهوية الخاص بك",view,getActivity());

            return false;
        }


        if (!have_text(x.reg_number_iban())){

            x.reg_number_iban().setError("ادخال رقم IBAN الخاص بك");

            message("يجب عليك ادخال رقم IBAN الخاص بك",view,getActivity());

            return false;
        }



        if (!have_text( x.reg_text_phone())){

            x.reg_text_phone().setError("ادخال الهاتف الخاص بك");

            message("يجب عليك ادخال رقم الهاتف",view,getActivity());

            return false;
        }

        if (!have_text(x.email())){

            x.email().setError("ادخال البريد الالكتروني");

            message("يجب عليك ادخال البريد الالكتروني",view,getActivity());

            return false;
        }

        if (!have_text( x.reg_text_pass())){

            x.reg_text_pass().setError("ادخال كلمة السر");

            message("يجب عليك ادخال كلمة السر",view,getActivity());

            return false;
        }

        if (!have_text( x.reg_text_confarm_pass())){

            x.reg_text_confarm_pass().setError("ادخال تأكيد كلمة السر");

            message("يجب عليك ادخال تأكيد كلمة السر",view,getActivity());

            return false;
        }



        if (x.reg_text_pass().length() < 6){

            message("يجب عليك ادخال 6 احرف وارقام على الاقل",view,getActivity());
            x.reg_text_pass().setError("6 احرف وارقام على الاقل");
            x.reg_text_confarm_pass().setError("6 احرف وارقام على الاقل");

            return false;

        }


        if (!helper.getString(x.reg_text_pass()).equals(helper.getString(x.reg_text_confarm_pass()))){

            x.reg_text_pass().setError("كلمات السر ليست متطابقة");
            x.reg_text_confarm_pass().setError("كلمات السر ليست متطابقة");

            message("يجب عليك ادخال كلمات سر متطابقة",view,getActivity());

            return false;

        }





        if (uri_image_profile == null){

            message("يجب عليك ادخال صورة شخصية",view,getActivity());
            return false;
        }


        if (uri_car_card_image == null){

            message("يجب عليك ادخال صورة رخصة السيارة",view,getActivity());
            return false;
        }

        if (uri_image_personal_card == null){

            message("يجب عليك ادخال صورة البطاقة الشحصية",view,getActivity());

            return false;
        }


        RegisterDatabase.name = helper.getString(x.name());
        RegisterDatabase.name_family = helper.getString(x.reg_text_name_family());
        RegisterDatabase.city = helper.getString(x.reg_text_city());
        RegisterDatabase.number_personal_card = helper.getString(x.reg_number_personal());
        RegisterDatabase.numbe_iban = helper.getString(x.reg_number_iban());
        RegisterDatabase.number_phone = helper.getString(x.reg_text_phone());
        RegisterDatabase.email = helper.getString(x.email());
        RegisterDatabase.password = helper.getString(x.reg_text_pass());




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

                case GALLERY_Profile_Image:

                    uri_image_profile = data.getData();
                    x.reg_profile_image().setImageURI(uri_image_profile);
                    setBytes(uri_image_profile,"profile");

                    break;

                case GALLERY_image_prsonal_card:

                    uri_image_personal_card = data.getData();
                    x.reg_image_personal_card().setImageURI(uri_image_personal_card);
                    setBytes(uri_image_personal_card,"personal_card");

                    break;

                case GALLERY_car_card:

                    uri_car_card_image = data.getData();
                    x.reg_image_car_card().setImageURI(uri_car_card_image);
                    setBytes(uri_car_card_image,"car_card");

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

                case "profile":

                    registerDatabase.bytes_image_profile = baos.toByteArray();

                    break;

                case "personal_card":

                    registerDatabase.bytes_Image_personal_card = baos.toByteArray();

                    break;

                case "car_card":

                    registerDatabase.bytes_Image_car_card = baos.toByteArray();

                    break;


            }






        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }









}
