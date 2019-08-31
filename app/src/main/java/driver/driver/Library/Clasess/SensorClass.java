package driver.driver.Library.Clasess;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

public class SensorClass implements SensorEventListener {


    private static SensorManager mySensorManager;
    private boolean sersorrunning;

    private Activity activity;
    public static float rotation = 0.0f;

    public SensorClass(Activity activity) {
        this.activity = activity;
    }

    // Start Sensor ---------------------



    SensorEventRotation sensorEventRotation;

    public void setSensorEventRotation(SensorEventRotation sensorEventRotation) {
        this.sensorEventRotation = sensorEventRotation;
    }

    public interface SensorEventRotation {
        void onRotation(float rotation);
    }

    public void start() {


        mySensorManager = (SensorManager)activity.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> mySensors = mySensorManager.getSensorList(Sensor.TYPE_ORIENTATION);




        if(mySensors.size() > 0){
            mySensorManager.registerListener(this, mySensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
            sersorrunning = true;
        }
        else{

            sersorrunning = false;
        }


    }


    public void stop(){

        if(mySensorManager !=null){
            mySensorManager.unregisterListener(this);
        }

    }



    @Override
    public void onSensorChanged(SensorEvent event) {

       // System.out.println("WWWWWWWWW: " + String.valueOf(event.values[0]));


        float degress = event.values[0];


        rotation = degress - 360;




        if (sensorEventRotation == null){
            return;
        }

        sensorEventRotation.onRotation(rotation);

        // System.out.println("Pitch: " + String.valueOf(event.values[1]));
        // System.out.println("Roll: " + String.valueOf(event.values[2]));




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {



    }




}


