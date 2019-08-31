package driver.driver.Library.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import driver.driver.Library.Listener.TopSheetEvent;


public class TopSheet {



    private boolean isShow = false;

    public boolean isShow() {
        return isShow;
    }

    public void expandle(){

        sheetBehavior = TopSheetBehavior.from(layout_sheet);
        // sheetBehavior.setPeekHeight(F.getScreenHieght());

        if (sheetBehavior.getState()!=TopSheetBehavior.STATE_EXPANDED){

            sheetBehavior.setState(TopSheetBehavior.STATE_EXPANDED);
        }
    }

    public void collapsed(){

        sheetBehavior = TopSheetBehavior.from(layout_sheet);
        // sheetBehavior.setPeekHeight(F.getScreenHieght());

        if (sheetBehavior.getState()!=TopSheetBehavior.STATE_COLLAPSED){

            sheetBehavior.setState(TopSheetBehavior.STATE_COLLAPSED);
        }
    }






    public  TopSheetBehavior sheetBehavior;

    private TopSheetEvent topSheetEvent;

    private View layout_sheet;

    public TopSheet(View layout_sheet) {
        this.layout_sheet = layout_sheet;
    }

    public void addBottomSheetEvent(final TopSheetEvent topSheetEvent){
        this.topSheetEvent = topSheetEvent;


        sheetBehavior = TopSheetBehavior.from(layout_sheet);


        sheetBehavior.setTopSheetCallback(new TopSheetBehavior.TopSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){



                    case TopSheetBehavior.STATE_HIDDEN:


                        break;

                    case TopSheetBehavior.STATE_EXPANDED:

                        topSheetEvent.onSheetExpandle();


                        isShow = true;



                        break;

                    case TopSheetBehavior.STATE_COLLAPSED:

                        topSheetEvent.onSheetCollapsed();


                        isShow = false;

                        break;



                    case TopSheetBehavior.STATE_SETTLING:


                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset, @Nullable Boolean isOpening) {

            }


        });


    }


}
