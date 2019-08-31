package driver.driver.Register.xml;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import driver.driver.R;

public class RegisterXml {


    public View view;

    public RegisterXml(View view) {
        this.view = view;
    }



    // EditText
    public EditText name(){
        return view.findViewById(R.id.reg_text_name);
    }

    public EditText reg_text_name_family(){
        return view.findViewById(R.id.reg_text_name_family);
    }

    public EditText reg_text_city(){
        return view.findViewById(R.id.reg_text_city);
    }

    public EditText reg_number_personal(){
        return view.findViewById(R.id.reg_number_personal);
    }

    public EditText reg_number_iban(){
        return view.findViewById(R.id.reg_number_iban);
    }

    public EditText email(){
        return view.findViewById(R.id.reg_text_email);
    }

    public EditText reg_text_phone(){
        return view.findViewById(R.id.reg_text_phone);
    }
    public EditText reg_text_pass(){
        return view.findViewById(R.id.reg_text_pass);
    }
    public EditText reg_text_confarm_pass(){
        return view.findViewById(R.id.reg_text_confarm_pass);
    }



    // fragment 2
    public EditText reg_text_number_car(){
        return view.findViewById(R.id.reg_text_number_car);
    }
    public EditText reg_text_model_car(){
        return view.findViewById(R.id.reg_text_model_car);
    }
    public EditText reg_text_kind_car(){
        return view.findViewById(R.id.reg_text_kind_car);
    }


    // Button
    public Button reg_btn_1(){
        return view.findViewById(R.id.reg_btn_1);
    }



    // fragment 2
    public Button reg_btn_2(){
        return view.findViewById(R.id.reg_btn_2);
    }

    //ProgressDialog
    public ProgressDialog mRegProgress;




    // ImageView
    public ImageView reg_profile_image(){
        return view.findViewById(R.id.reg_profile_image);
    }

    public ImageView reg_image_personal_card(){
        return view.findViewById(R.id.reg_image_personal_card);
    }

    public ImageView reg_image_car_card(){
        return view.findViewById(R.id.reg_image_car_card);
    }



    // fragment 2
    public ImageView reg_image_save_car(){
        return view.findViewById(R.id.reg_image_save_car);
    }
    public ImageView reg_image_save(){
        return view.findViewById(R.id.reg_image_save);
    }
    public ImageView reg_image_up_car(){
        return view.findViewById(R.id.reg_image_up_car);
    }
    public ImageView reg_image_back_car(){
        return view.findViewById(R.id.reg_image_back_car);
    }




    // RadioButton

    public RadioButton register_radio_sat7a(){
        return view.findViewById(R.id.register_radio_sat7a);
    }



    public RadioButton register_radio_sat7a_3ady(){
        return view.findViewById(R.id.register_radio_sat7a_3ady);
    }



    public RadioButton register_radio_bancher(){
        return view.findViewById(R.id.register_radio_bancher);
    }



    public RadioButton register_radio_bettery(){
        return view.findViewById(R.id.register_radio_bettery);
    }



    public RadioButton register_radio_banzen(){
        return view.findViewById(R.id.register_radio_banzen);
    }



    public RadioButton register_radio_open_key(){
        return view.findViewById(R.id.register_radio_open_key);
    }


    public RadioButton register_radio_wensh(){
        return view.findViewById(R.id.register_radio_wensh);
    }





}
