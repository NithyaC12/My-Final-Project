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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.model.BusStopModel;
import com.example.lenovo.model.StopAllocationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AllocateBusStops extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText ed_search;
    ListView lv_stops,lv_selected_stops;
    DatabaseReference mReference;
    ArrayList<BusStopModel> busStopModels,seletedModels;
    BaseAdapter adapter,sel_adapter;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocate_bus_stops);

        ed_search=(EditText)findViewById(R.id.ed_search);
        lv_stops=(ListView)findViewById(R.id.lv_stops);
        lv_selected_stops=(ListView)findViewById(R.id.selected_stops);
        submit=(Button)findViewById(R.id.submit);

        mReference= FirebaseDatabase.getInstance().getReference();

        displayStops("");

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                displayStops(ed_search.getText().toString().toLowerCase());
            }
        });

        seletedModels=new ArrayList<>();
        sel_adapter=new BusStopAdapter(getApplicationContext(),seletedModels);
        lv_selected_stops.setAdapter(sel_adapter);
        sel_adapter.notifyDataSetChanged();

        lv_stops.setOnItemClickListener(this);
        lv_selected_stops.setOnItemClickListener(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mReference=FirebaseDatabase.getInstance().getReference("stop_allocation");
                for (int i=0;i<seletedModels.size();i++){
                    BusStopModel model=seletedModels.get(i);
                    StopAllocationModel allocationModel=new StopAllocationModel();
                    allocationModel.setRoute_id(AdminViewBusStops.selectedRoute.getRoute_id());
                    allocationModel.setStop_allocation_id(mReference.push().getKey());
                    allocationModel.setStop_id(model.getStop_id());
                    allocationModel.setStop_number((i+1)+"");
                    allocationModel.setOrdinary("nill");
                    allocationModel.setLimited_stop("nill");
                    allocationModel.setFast_passenger("nill");
                    allocationModel.setSuper_fast("nill");
                    allocationModel.setSuper_express("nill");
                    allocationModel.setSuper_delux("nill");

                    mReference.child(allocationModel.getStop_allocation_id()).setValue(allocationModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }

                Gson gson=new Gson();
                String routeString=gson.toJson(AdminViewBusStops.selectedRoute);
                Intent in=new Intent(getApplicationContext(),AdminViewBusStops.class);
                in.putExtra("routes",routeString);
                startActivity(in);
            }
        });
    }

    public void displayStops(final String keyword){

        Query query=mReference.child("bus_stop").orderByChild("location");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                busStopModels=new ArrayList<>();
                adapter=new BusStopAdapter(getApplicationContext(),busStopModels);
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BusStopModel model=snapshot.getValue(BusStopModel.class);
                        if (model.getLocation().contains(keyword)){

                            busStopModels.add(model);
                        }
                    }
                }

                lv_stops.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if(parent==lv_stops){
            seletedModels.add(busStopModels.get(position));
            sel_adapter.notifyDataSetChanged();
        }
        if(parent==lv_selected_stops){
            final CharSequence[] items={"Remove","cancel"};

            AlertDialog.Builder alertDialog=new AlertDialog.Builder(AllocateBusStops.this);
            alertDialog.setTitle("");
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(items[which].equals("Remove")){
                        seletedModels.remove(position);
                        sel_adapter.notifyDataSetChanged();
                    }
                }
            });

            AlertDialog dialog=alertDialog.create();
            dialog.show();
        }
    }
}
