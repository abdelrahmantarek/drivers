package driver.driver.maps.Database;

import com.google.firebase.database.DataSnapshot;

import driver.driver.model.Service;

public interface On_Request_Or_Service {

    void onRequest(String user_id,DataSnapshot dataSnapshot);
    void onService(String user_id, DataSnapshot dataSnapshot);


    void onRequestRemove(DataSnapshot dataSnapshot);
    void onServiceRemove(DataSnapshot dataSnapshot);


    void onCheckNo(DataSnapshot dataSnapshot);


}
