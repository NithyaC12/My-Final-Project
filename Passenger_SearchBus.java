package com.example.lenovo.bustracking;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.BusStopModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Passenger_SearchBus extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv_bus;
    EditText ed_search;
    DatabaseReference mReference;
    public static ArrayList<BusModel> busList;
    public static int pos=0;
    BaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__search_bus);

        lv_bus=(ListView)findViewById(R.id.lv_bus);
        ed_search=(EditText)findViewById(R.id.ed_search);

        displayBus("");
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                displayBus(ed_search.getText().toString());
            }
        });

        lv_bus.setOnItemClickListener(this);
    }

    public void displayBus(final String keyword){

        mReference= FirebaseDatabase.getInstance().getReference();
        Query query=mReference.child("bus").orderByChild("bus_number");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                busList=new ArrayList<>();
                adapter=new BusAdapter(getApplicationContext(),busList);
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BusModel model=snapshot.getValue(BusModel.class);
                        if (model.getBus_name().contains(keyword)||model.getBus_number().contains(keyword)){

                            busList.add(model);
                        }
                    }
                }

                lv_bus.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        pos=position;
        final CharSequence[] items={"Details","Cancel"};

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Passenger_SearchBus.this);
        alertDialog.setTitle("");
        alertDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Details")){

                    startActivity(new Intent(getApplicationContext(),PassengerViewBusDetails.class));
                }
                if(items[which].equals("Cancel")){

                }
            }
        });

        AlertDialog dialog=alertDialog.create();
        dialog.show();
    }
}
