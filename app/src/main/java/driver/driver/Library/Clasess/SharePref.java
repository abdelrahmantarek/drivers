package driver.driver.Library.Clasess;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

public class SharePref {
    public String language = "language";
    public String database_name = "database";
    private SharedPreferences sharedPreferences;
    Activity activity;
    Context context;



    public SharePref(Activity activity) {
        this.activity = activity;

        sharedPreferences = activity.getSharedPreferences(database_name, Context.MODE_PRIVATE);
    }
    public SharePref(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(language, Context.MODE_PRIVATE);
    }

    // set from SharedPreferences
    public void set(String key, String string){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,string);
        // update color map
        editor.commit();
    }
    public void set(int string){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("",string);
        // update color map
        editor.commit();
    }


    // get from SharedPreferences
    public String getString(String key){
        return sharedPreferences.getString(key,"");
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }



    public boolean exist(String fileName) {
        if (activity == null){
            File f = new File(context.getApplicationInfo().dataDir + "/shared_prefs/"
                    + fileName + ".xml");
            return f.exists();
        }else {

            File f = new File(activity.getApplicationInfo().dataDir + "/shared_prefs/"
                    + fileName + ".xml");
            return f.exists();
        }

    }
	
	    public boolean exists(String fileName) {

        if (activity == null){
            File f = new File(context.getApplicationInfo().dataDir + "/shared_prefs/"
                    + fileName + ".xml");
            return f.exists();
        }else {
            File f = new File(activity.getApplicationInfo().dataDir + "/shared_prefs/"
                    + fileName + ".xml");
            return f.exists();
        }

    }

}
