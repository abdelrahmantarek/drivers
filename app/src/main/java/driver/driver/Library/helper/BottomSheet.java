package driver.driver.Library.helper;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;

import driver.driver.Library.Listener.BottomSheetEvent;


public class BottomSheet {


    private boolean isShow = false;

    public boolean isShow() {
        return isShow;
    }



    public void expandle(){

        sheetBehavior = BottomSheetBehavior.from(layout_sheet);
        // sheetBehavior.setPeekHeight(F.getScreenHieght());

        if (sheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){

            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void collapsed(){

        sheetBehavior = BottomSheetBehavior.from(layout_sheet);
        // sheetBehavior.setPeekHeight(F.getScreenHieght());

        if (sheetBehavior.getState()!=BottomSheetBehavior.STATE_COLLAPSED){

            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }






    public BottomSheetBehavior sheetBehavior;

    private BottomSheetEvent bottomSheetEvent;
    private View layout_sheet;

    public BottomSheet(View layout_sheet) {
        this.layout_sheet = layout_sheet;
    }

    public void addBottomSheetEvent(final BottomSheetEvent bottomSheetEvent){
        this.bottomSheetEvent = bottomSheetEvent;


        sheetBehavior = BottomSheetBehavior.from(layout_sheet);


        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){



                    case BottomSheetBehavior.STATE_HIDDEN:


                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:

                        bottomSheetEvent.onSheetExpandle();

                        isShow = true;



                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:

                        bottomSheetEvent.onSheetCollapsed();

                        isShow = false;

                        break;



                    case BottomSheetBehavior.STATE_SETTLING:


                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


    }




}
