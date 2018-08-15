package com.example.lenovo.bustracking;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lenovo.model.RouteModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewBusRoutes extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv_routes;
    DatabaseReference mReference;
    ArrayList<RouteModel> routeList;
    ArrayList<String> routes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus_routes);

        lv_routes=(ListView)findViewById(R.id.lv_routes);

        mReference= FirebaseDatabase.getInstance().getReference();

        routeList=new ArrayList<>();
        routes=new ArrayList<>();
        lv_routes.setOnItemClickListener(this);
        Query query=mReference.child("route").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        RouteModel routeModel=snapshot.getValue(RouteModel.class);
                        routeList.add(routeModel);
                        routes.add(routeModel.getRoute_name());
                    }

                    lv_routes.setAdapter(new BusRouteAdapter(getApplicationContext(),routeList));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final CharSequence[] items={"View Stops"};

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(ViewBusRoutes.this);
        alertDialog.setTitle("");
        alertDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("View Stops")){

                }
            }
        });

        AlertDialog dialog=alertDialog.create();
        dialog.show();
    }
}
