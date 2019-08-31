package driver.driver.Register;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import driver.driver.Register.database.RegisterDatabase;
import driver.driver.Register.view.RegisterListener;
import driver.driver.Register.fragment.Register_1_fragment;
import driver.driver.Register.fragment.Register_2_fragment;

class SectionPager extends FragmentPagerAdapter {

    private RegisterListener registerListener;
    private RegisterActivity registerActivity;
    private RegisterDatabase registerDatabase;
    public SectionPager(RegisterDatabase registerDatabase, RegisterListener registerListener, RegisterActivity registerActivity, FragmentManager fm) {
        super(fm);
        this.registerListener = registerListener;
        this.registerActivity = registerActivity;
        this.registerDatabase = registerDatabase;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                Register_1_fragment register_1_fragment = new Register_1_fragment(registerDatabase,registerActivity,registerListener);
                return register_1_fragment;

            case 1:
                Register_2_fragment chatsFragment = new Register_2_fragment(registerDatabase,registerActivity,registerListener);
                return  chatsFragment;

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "1";

            case 1:
                return "2";


            default:
                return null;
        }

    }
}
