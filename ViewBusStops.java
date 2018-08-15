package com.example.lenovo.bustracking;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ViewBusStops extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText ed_search;
    ListView lv_stops;
    DatabaseReference mReference;
    ArrayList<BusStopModel> busStopModels;
    BaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus_stops);

        ed_search=(EditText)findViewById(R.id.ed_search);
        lv_stops=(ListView)findViewById(R.id.lv_stops);
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

        lv_stops.setOnItemClickListener(this);
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
        final CharSequence[] items={"Edit","Locate"};

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(ViewBusStops.this);
        alertDialog.setTitle("Options....");
        alertDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Edit")){

                }
                if(items[which].equals("Locate")){

                    String uri ="http://maps.google.com/maps?q=loc:"+busStopModels.get(position).getLocation()+"("+busStopModels.get(position).getLocation()+")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
            }
        });

        AlertDialog dialog=alertDialog.create();
        dialog.show();
    }
}
