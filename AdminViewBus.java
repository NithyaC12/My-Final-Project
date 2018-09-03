package com.example.lenovo.bustracking;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lenovo.model.BusModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewBus extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv_buses;
    public static ArrayList<BusModel> busList;
    public static int pos=0;
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_bus);

        lv_buses=(ListView)findViewById(R.id.lv_buses);

        mReference= FirebaseDatabase.getInstance().getReference();
        Query query=mReference.child("bus").orderByChild("bus_number");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    busList=new ArrayList<>();
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BusModel model=snapshot.getValue(BusModel.class);
                        busList.add(model);
                    }

                    lv_buses.setAdapter(new BusAdapter(getApplicationContext(),busList));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv_buses.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        pos=position;
        final CharSequence[] items={"Details","Cancel"};

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(AdminViewBus.this);
        alertDialog.setTitle("");
        alertDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Details")){

                    startActivity(new Intent(getApplicationContext(),ViewBusdetails.class));
                }
                if(items[which].equals("Cancel")){

                }
            }
        });

        AlertDialog dialog=alertDialog.create();
        dialog.show();
    }
}
