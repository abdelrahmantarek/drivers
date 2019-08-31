package driver.driver.Register;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import driver.driver.Library.MasterPermission;
import driver.driver.R;
import driver.driver.Register.database.RegisterDatabase;
import driver.driver.Register.registerListener.OnSwitchTab;
import driver.driver.Register.view.RegisterListener;
import driver.driver.Library.Tools;


public class RegisterActivity extends AppCompatActivity {


    // View
    private LinearLayout reg_view(){
        return findViewById(R.id.reg_view);
    }


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SectionPager mSectionsPagerAdapter;

    private RegisterListener registerListener;
    private RegisterDatabase registerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);


        Tools.setSystemBarColor(this, R.color.colorPrimary);

        registerListener = new RegisterListener();
         registerDatabase = new RegisterDatabase(this);

        Tabs(registerListener);



        MasterPermission masterPermission = new MasterPermission(RegisterActivity.this);

        if (!masterPermission.isLocation()){

            masterPermission.addPermissionLocation(new MasterPermission.EventLocationListener() {
                @Override
                public void on_Location_Ok() {

                }
            });

        }



    }

    private void Tabs(RegisterListener registerListener) {
        //Tabs
        mViewPager = (ViewPager) findViewById(R.id.register_tabPager);
        mSectionsPagerAdapter = new SectionPager(registerDatabase,registerListener,RegisterActivity.this,getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.register_tabs);
        mTabLayout.setupWithViewPager(mViewPager);



        this.registerListener.setOnSwitchTab(new OnSwitchTab() {
            @Override
            public void onPosition(int position) {

                Toast.makeText(RegisterActivity.this, position+"", Toast.LENGTH_SHORT).show();

                mTabLayout.setScrollPosition(position,0f,true);
                mViewPager.setCurrentItem(position);
            }
        });
    }




}
