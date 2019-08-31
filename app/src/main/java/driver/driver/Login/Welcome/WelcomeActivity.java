package driver.driver.Login.Welcome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import driver.driver.Library.Tools;
import driver.driver.Login.LoginActivity;
import driver.driver.Register.RegisterActivity;
import driver.driver.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button mRegBtn;private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Tools.setSystemBarColor(this, R.color.colorPrimary);

        mRegBtn = (Button) findViewById(R.id.start_reg_btn);mLoginBtn = (Button) findViewById(R.id.start_login_btn);

        mRegBtn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) { Intent reg_intent = new Intent(WelcomeActivity.this, RegisterActivity.class);startActivity(reg_intent);}});

        mLoginBtn.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) { Intent login_intent = new Intent(WelcomeActivity.this, LoginActivity.class);startActivity(login_intent);}});

    }

}
