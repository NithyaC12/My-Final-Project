package com.example.lenovo.bustracking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.model.BusStopModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class BusStops extends AppCompatActivity {

    EditText ed19,ed20,ed53;
    Button b6;
    CheckBox ch1,ch2,ch3,ch4,ch5,ch6;
    ImageView img2;
    DatabaseReference mReference;
    BusStopModel busStopModel;
    private static final int PLACE_PICKER_REQUEST = 1000;
    private GoogleApiClient mClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stops);

        ed19=(EditText)findViewById(R.id.editText19);
        ed20=(EditText)findViewById(R.id.editText20);
        ed53=(EditText)findViewById(R.id.editText53);
        b6=(Button)findViewById(R.id.button6);
        ch1=(CheckBox)findViewById(R.id.checkBox);
        ch2=(CheckBox)findViewById(R.id.checkBox2);
        ch3=(CheckBox)findViewById(R.id.checkBox3);
        ch4=(CheckBox)findViewById(R.id.checkBox4);
        ch5=(CheckBox)findViewById(R.id.checkBox5);
        ch6=(CheckBox)findViewById(R.id.checkBox6);
        img2=(ImageView)findViewById(R.id.imageView2);

        mClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).build();
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(BusStops.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mReference= FirebaseDatabase.getInstance().getReference("bus_stop");
                busStopModel=new BusStopModel();
                busStopModel.setStop_id(mReference.push().getKey());

                String stopType="";
                if(ch1.isChecked()){
                    if(stopType.equalsIgnoreCase("")){
                        stopType="ordinary";
                    }
                    else{
                        stopType+=",ordinary";
                    }
                }
                if(ch2.isChecked()){
                    if(stopType.equalsIgnoreCase("")){
                        stopType="limited stop";
                    }
                    else{
                        stopType+=",limited stop";
                    }
                }
                if(ch3.isChecked()){
                    if(stopType.equalsIgnoreCase("")){
                        stopType="fast passenger";
                    }
                    else{
                        stopType+=",fast passenger";
                    }
                }
                if(ch4.isChecked()){
                    if(stopType.equalsIgnoreCase("")){
                        stopType="super fast";
                    }
                    else{
                        stopType+=",super fast";
                    }
                }
                if(ch5.isChecked()){
                    if(stopType.equalsIgnoreCase("")){
                        stopType="super express";
                    }
                    else{
                        stopType+=",super express";
                    }
                }
                if(ch6.isChecked()){
                    if(stopType.equalsIgnoreCase("")){
                        stopType="super delux";
                    }
                    else{
                        stopType+=",super delux";
                    }
                }

                busStopModel.setLattitude(ed20.getText().toString());
                busStopModel.setLongitude(ed53.getText().toString());
                busStopModel.setLocation(ed19.getText().toString().toLowerCase());
                busStopModel.setStop_type(stopType);

                mReference.child(busStopModel.getStop_id()).setValue(busStopModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(BusStops.this, "Success..!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                LatLng latLng=place.getLatLng();
                DecimalFormat df = new DecimalFormat("#.###");
                ed19.setText(place.getName());
                ed20.setText(df.format(latLng.latitude)+"");
                ed53.setText(df.format(latLng.longitude)+"");
            }
        }
    }
}
