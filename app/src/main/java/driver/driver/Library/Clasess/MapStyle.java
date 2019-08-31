package driver.driver.Library.Clasess;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.io.File;

import driver.driver.R;


public class MapStyle {


    public   String aubergine = "aubergine";
    public   String black = "black";
    public   String night = "night";
    public   String retro = "retro";
    public   String silver = "silver";
    public   String standerd = "standerd";

    private  SharedPreferences sharedPreferences;

    private MapStyleOptions mapStyle;
    private Activity activity;
    private GoogleMap map;


    public MapStyle(Activity activity,GoogleMap map) {
        this.activity = activity;
        this.map = map;

        sharedPreferences = activity.getSharedPreferences(map_style,Context.MODE_PRIVATE);
    }


    // Check color map
    public void checkMapStyle(){

        if (exist(map_style)){

            switch (getMapColor()){

                case "aubergine":

                    mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.aubergine);
                    map.setMapStyle(mapStyle);


                    break;

                case "black":

                    mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.black);
                    map.setMapStyle(mapStyle);

                    break;

                case "night":

                    mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.night);
                    map.setMapStyle(mapStyle);

                    break;

                case "retro":

                    mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.retro);
                    map.setMapStyle(mapStyle);

                    break;

                case "silver":

                    mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.silver);
                    map.setMapStyle(mapStyle);

                    break;

                case "standerd":

                    mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.standerd);
                    map.setMapStyle(mapStyle);

                    break;


            }
        }else {

            Toast.makeText(activity, "checkMapStyle not exist", Toast.LENGTH_SHORT).show();

            mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.retro);
            map.setMapStyle(mapStyle);
            setMapColor("retro");

        }


    }

    public boolean exist(String fileName) {
        File f = new File(activity.getApplicationInfo().dataDir + "/shared_prefs/"
                + fileName + ".xml");
        return f.exists();
    }


    private String map_color = "map_color";

    // set from SharedPreferences
    public void setMapColor(String mapColor){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(map_color,mapColor);

        // update color map
        setStyle(mapColor);

        editor.commit();
    }


    private String map_style = "map_style";

    // get from SharedPreferences
    public String getMapColor(){
        return sharedPreferences.getString(map_color,"");
    }

    private void setStyle(String style) {

        switch (style){

            case "aubergine":

                mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.aubergine);
                map.setMapStyle(mapStyle);


                break;

            case "black":

                mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.black);
                map.setMapStyle(mapStyle);

                break;

            case "night":

                mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.night);
                map.setMapStyle(mapStyle);

                break;

            case "retro":

                mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.retro);
                map.setMapStyle(mapStyle);

                break;

            case "silver":

                mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.silver);
                map.setMapStyle(mapStyle);

                break;

            case "standerd":

                mapStyle = MapStyleOptions.loadRawResourceStyle(activity, R.raw.standerd);
                map.setMapStyle(mapStyle);

                break;


        }

    }


    public void changeColor() {


        if (!exist(map_style)){
            map.setMapStyle(mapStyle);
            Toast.makeText(activity, "switch not exist", Toast.LENGTH_SHORT).show();
            return;
        }


        switch (getMapColor()){

            case "aubergine":

                setMapColor(black);

                break;

            case "black":

                setMapColor(night);

                break;

            case "night":

                setMapColor(retro);

                break;

            case "retro":

                setMapColor(silver);

                break;

            case "silver":

                setMapColor(standerd);

                break;

            case "standerd":

                setMapColor(aubergine);

                break;


        }

        Toast.makeText(activity, "switch change done", Toast.LENGTH_SHORT).show();

    }




}
