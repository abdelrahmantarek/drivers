package driver.driver.Login;

import driver.driver.Library.Clasess.SharePref;
import driver.driver.Library.F;
import driver.driver.Library.Tools;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import driver.driver.R;
import driver.driver.Register.helper;
import driver.driver.maps.MapsActivity;
import driver.driver.model.Users;


public class LoginActivity extends AppCompatActivity {



    private EditText mLoginEmail;
    private EditText mLoginPassword;

    private Button mLogin_btn;

    private ProgressBar progress_bar;
    private View parent_view;

    private FirebaseAuth mAuth;

    private DatabaseReference mUserDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mAuth = FirebaseAuth.getInstance();
        parent_view = findViewById(android.R.id.content);
        Tools.setSystemBarColor(this, R.color.colorPrimary);

        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

         View  parent_view = findViewById(android.R.id.content);



        mLogin_btn = findViewById(R.id.login_btn);
         mLoginEmail = findViewById(R.id.login_email);
         mLoginPassword = findViewById(R.id.login_pass);

        Tools.setSystemBarColor(this, R.color.blue_grey_900);



        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = mLoginEmail.getText().toString();
                String password = mLoginPassword.getText().toString();


                if (!helper.isOnline(LoginActivity.this)){

                    F.message("من فضلك تأكد من خدمة الانترنت",((findViewById(R.id.login_view))),LoginActivity.this,R.color.red_A700);



                    return;
                }


                if (!helper.have_text(email)){

                    F.message("من فضلك قم بادخال البريد الالكتروني",((findViewById(R.id.login_view))),LoginActivity.this,R.color.red_A700);

                    helper.message("",((findViewById(R.id.login_view))),LoginActivity.this);

                    return;

                }

                if (!helper.have_text(password)){

                    F.message("من فضلك قم بادخال كلمة السر",((findViewById(R.id.login_view))),LoginActivity.this,R.color.red_A700);


                    return;

                }



                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        F.message(i++ +"",((findViewById(R.id.login_view))),LoginActivity.this,R.color.red_A700);


                        F.message("خدمة الانترنت ضعيفة جدا",((findViewById(R.id.login_view))),LoginActivity.this,R.color.red_A700);

                    }
                }, 10000);


                mLogin_btn.setVisibility(View.GONE);
                progress_bar.setVisibility(View.VISIBLE);


                loginUser(email, password);

            }
        });


    }

    int i = 0;

    private void loginUser(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    check_If_UID_Exist_In_Drivers();


                } else {

                    mLogin_btn.setVisibility(View.VISIBLE);
                    progress_bar.setVisibility(View.GONE);

                    String task_result = task.getException().getMessage().toString();

                    F.message(task.getException().getMessage() +"",((findViewById(R.id.login_view))),LoginActivity.this,R.color.red_A700);

                    Log.d("",task.getException().getMessage() + "");

                }

            }
        });


    }

    private void check_If_UID_Exist_In_Drivers() {

       F.Drivers().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(F.Uid())){
					
					   if (dataSnapshot.child(F.Uid()).hasChild("name")){
						   
						        Intent_To_MapsActivity();
					   }else{
						   
						   
						    F.message("انت لست مقيد في السائقين من فضلك سجل مجددا او يمكنك الاتصال بالادارة",((findViewById(R.id.login_view))),LoginActivity.this,R.color.red_A700);
						           F.firebaseAuth().signOut();

                     mLogin_btn.setVisibility(View.VISIBLE);
                    progress_bar.setVisibility(View.GONE);
                   
						   
					   }

               


                }else {

                    mLogin_btn.setVisibility(View.VISIBLE);
                    progress_bar.setVisibility(View.GONE);

                    F.firebaseAuth().signOut();


                    F.message("انت لست مقيد في السائقين من فضلك سجل مجددا او يمكنك الاتصال بالادارة",((findViewById(R.id.login_view))),LoginActivity.this,R.color.red_A700);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void Intent_To_MapsActivity() {

        mLogin_btn.setVisibility(View.VISIBLE);
        progress_bar.setVisibility(View.GONE);

        String current_user_id = mAuth.getCurrentUser().getUid();
        String deviceToken = FirebaseInstanceId.getInstance().getToken();

        mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                SharePref sharePref = new SharePref(LoginActivity.this);
                sharePref.set("UID",F.Uid());


                Intent mainIntent = new Intent(LoginActivity.this, MapsActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                Toast.makeText(LoginActivity.this, "1", Toast.LENGTH_SHORT).show();
                finish();



            }
        });
    }
}
