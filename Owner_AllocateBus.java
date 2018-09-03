package com.example.lenovo.bustracking;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.BusScheduleModel;
import com.example.lenovo.model.RouteModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Owner_AllocateBus extends AppCompatActivity {

    ListView lv_routes;
    FloatingActionButton fab;
    BusModel busModel;
    DatabaseReference mReference;
    ArrayList<BusScheduleModel> scheduleModels;
    ArrayList<RouteModel> routeModels;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__allocate_bus);

        lv_routes=(ListView)findViewById(R.id.lv_routes);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        busModel=Owner_ViewBus.busList.get(Owner_ViewBus.pos);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OwnerSendRouteRequest.class));
            }
        });

        mReference= FirebaseDatabase.getInstance().getReference();
        Query query=mReference.child("bus_schedule").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    scheduleModels=new ArrayList<>();
                    routeModels=new ArrayList<>();
                    adapter=new BusScheduleAdapter(getApplicationContext(),routeModels,scheduleModels);
                    lv_routes.setAdapter(adapter);
                    for (final DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BusScheduleModel model=snapshot.getValue(BusScheduleModel.class);
                        scheduleModels.add(model);


                        Query query2=mReference.child("route").orderByChild("route_id").equalTo(model.getRoute_id());
                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    RouteModel routeModel=null;
                                    for (DataSnapshot snapshot1: dataSnapshot.getChildren()){
                                        routeModel=snapshot1.getValue(RouteModel.class);
                                    }
                                    routeModels.add(routeModel);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
