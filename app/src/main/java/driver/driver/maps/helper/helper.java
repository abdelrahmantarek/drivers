package driver.driver.maps.helper;

import android.app.Activity;
import driver.driver.Library.F;
import android.util.DisplayMetrics;

public class helper {

    public static void screen(Activity activity){


        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        F.setScreenHieght(height);
        F.setScreenWidth(width);
    }
}
