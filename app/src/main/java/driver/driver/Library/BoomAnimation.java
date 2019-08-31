package driver.driver.Library;

import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.view.View;

public class BoomAnimation {


    private OnBoomAnimation onBoomAnimation;
    private Handler handler_h = new Handler();
    private Runnable runnable_h;
    int Nsba_H = 100;
    int Nsba_W = 100;
    private View view;
    private Activity activity;



    public BoomAnimation(View view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }








    private int Nsba_H(){
        return F.getScreenHieght() * Nsba_H / 100;
    }

    private int Nsba_W(){
        return F.getScreenWidth() * Nsba_W/100;
    }




    private boolean Opned = false;





    public int width(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public int height(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;

    }




    public void OpenToinSameTime(){


        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {


            }

            public void onFinish() {

                runnable_h = new Runnable() {
                    @Override
                    public void run() {

                        if (!Opned){

                            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
                            params.height = params.height + 50;
                            params.width = params.width + 50;
                            view.setLayoutParams(params);

                            if (params.height > F.getScreenHieght()*2){
                                handler_h.removeCallbacksAndMessages(null);
                                Opned = true;

                                onBoomAnimation.onFinish();

                                return;
                            }

                            handler_h.postDelayed(runnable_h, 1);

                        }
                    }


                };
                handler_h.post(runnable_h);



            }
        }.start();


    }


    public void setOnBoomAnimation(OnBoomAnimation onBoomAnimation) {
        this.onBoomAnimation = onBoomAnimation;

        OpenToinSameTime();
    }
}
