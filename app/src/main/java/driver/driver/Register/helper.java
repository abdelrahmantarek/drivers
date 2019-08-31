package driver.driver.Register;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class helper {

    public static boolean have_text(EditText appCompatEditText){
        return !TextUtils.isEmpty(getString(appCompatEditText));
    }

    public static String getString(EditText appCompatEditText) {
        return appCompatEditText.getText().toString();
    }

    public static boolean have_text(String string){
        return !TextUtils.isEmpty(string);
    }


    public static void message(String message,View view,Context context) {

        Snackbar snackbar = Snackbar.make(view, message+"", Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        snackbar.show();
    }
    public static boolean isOnline(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

}
