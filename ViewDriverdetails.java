package com.example.lenovo.bustracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lenovo.model.BusStopModel;
import com.example.lenovo.model.DriverModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewDriverdetails extends AppCompatActivity {

    ListView lv_drivers;
    ArrayList<DriverModel> driverModels;
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_driverdetails);

        lv_drivers=(ListView)findViewById(R.id.lv_drivers);

        mReference= FirebaseDatabase.getInstance().getReference();
        Query query=mReference.child("driver").orderByChild("driver_name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                driverModels=new ArrayList<>();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        DriverModel model=snapshot.getValue(DriverModel.class);
                        driverModels.add(model);
                    }
                    lv_drivers.setAdapter(new DriverAdapter(getApplicationContext(),driverModels));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
