package driver.driver.Library;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class StateClass {

    private String state="";


    public void checkState(){

       DatabaseReference databaseReference = F.reference().child("state").child(F.Uid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (state.equals("service")){


                    onGetStateEventListener.onServiceRemove(dataSnapshot);

                    state = "";

                    return;
                }




                if (!dataSnapshot.exists()){ //  LksBJtKKulS117ANRVff9vm92O32v
                    state = "";

                    if (onGetStateEventListener!=null){

                        F.Users().child(F.Uid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                onGetStateEventListener.onNo(dataSnapshot);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                    return;
                }





                if (dataSnapshot.hasChild("state")){

                    String states = dataSnapshot.child("state").getValue(String.class);

                    switch (states){

                        case "service":


                            if (onGetStateEventListener!=null){
                                onGetStateEventListener.onService(dataSnapshot);
                            }

                            state = "service";

                            break;

                        case "request":


                            if (onGetStateEventListener!=null){
                                onGetStateEventListener.onRequest(dataSnapshot);
                            }

                            state = "request";

                            break;


                    }

                    return;
                }


                if (state.equals("request")){

                    onGetStateEventListener.onRequestRemove(dataSnapshot);

                    state = "";

                    return;
                }


                // if exist but no have data
                if (onGetStateEventListener!=null){

                    F.Users().child(F.Uid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            onGetStateEventListener.onNo(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private onGetStateEventListener onGetStateEventListener;

    public void setOnGetStateEventListener(StateClass.onGetStateEventListener onGetStateEventListener) {
        this.onGetStateEventListener = onGetStateEventListener;

        checkState();
    }

    public interface onGetStateEventListener{

        void onNo(DataSnapshot dataSnapshot);
        void onService(DataSnapshot dataSnapshot);
        void onRequest(DataSnapshot dataSnapshot);
        void onRequestRemove(DataSnapshot dataSnapshot);
        void onServiceRemove(DataSnapshot dataSnapshot);
    }
}
