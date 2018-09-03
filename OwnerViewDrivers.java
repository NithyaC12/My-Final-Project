package com.example.lenovo.bustracking;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.model.BusStopModel;
import com.example.lenovo.model.DriverModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Driver;
import java.util.ArrayList;

public class OwnerViewDrivers extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv_dr;
    ArrayList<DriverModel> driverModels;
    DatabaseReference mReference;
    EditText ed_search;
    BaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view_drivers);

        lv_dr=(ListView)findViewById(R.id.lv_dr);
        ed_search=(EditText)findViewById(R.id.ed_search);

        lv_dr.setOnItemClickListener(this);
        displayDrivers("");
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                displayDrivers(ed_search.getText().toString());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        final CharSequence[] items={"Allocate","Cancel"};

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(OwnerViewDrivers.this);
        alertDialog.setTitle("");
        alertDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Allocate")){

                    DriverModel m=driverModels.get(position);
                    m.setBus_id(Owner_ViewBus.busList.get(Owner_ViewBus.pos).getBus_id());
                    allocateDriver(m);
                }
                if(items[which].equals("Cancel")){

                }
            }
        });

        AlertDialog dialog=alertDialog.create();
        dialog.show();
    }

    public void displayDrivers(final String keyword){

        mReference=FirebaseDatabase.getInstance().getReference();

        Query query=mReference.child("driver").orderByChild("driver_name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                driverModels=new ArrayList<>();
                adapter=new DriverAdapter(getApplicationContext(),driverModels);
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        DriverModel model=snapshot.getValue(DriverModel.class);
                        if (model.getDriver_name().contains(keyword)||model.getLicense_number().contains(keyword)){

                            driverModels.add(model);
                        }
                    }
                }

                lv_dr.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void allocateDriver(DriverModel driverModel){

        mReference=FirebaseDatabase.getInstance().getReference("driver");
        mReference.child(driverModel.getDriver_id()).setValue(driverModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(OwnerViewDrivers.this, "Success..!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Owner_ViewBus.class));
            }
        });
    }
}
