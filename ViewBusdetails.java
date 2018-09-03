package com.example.lenovo.bustracking;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceDataStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.model.BusLocationModel;
import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.LoginModel;
import com.example.lenovo.model.UserModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewBusdetails extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    ImageView img;
    TextView tv_bus_name,tv_bus_number,tv_bus_type,tv_imei,tv_user_name,tv_place,tv_phone,tv_email;
    Button ba,bb;
    BusModel busModel;
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_busdetails);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        img=(ImageView)findViewById(R.id.img);
        tv_bus_name=(TextView)findViewById(R.id.textView25);
        tv_bus_number=(TextView)findViewById(R.id.textView26);
        tv_bus_type=(TextView)findViewById(R.id.textView27);
        tv_imei=(TextView)findViewById(R.id.textView28);
        tv_user_name=(TextView)findViewById(R.id.oname);
        tv_place=(TextView)findViewById(R.id.oplace);
        tv_phone=(TextView)findViewById(R.id.ophone);
        tv_email=(TextView)findViewById(R.id.oemail);
        ba=(Button)findViewById(R.id.ba);
        bb=(Button)findViewById(R.id.bb);

        busModel=AdminViewBus.busList.get(AdminViewBus.pos);

        tv_bus_name.setText(busModel.getBus_name());
        tv_bus_number.setText(busModel.getBus_number());
        tv_bus_type.setText(busModel.getBus_type());
        tv_imei.setText(busModel.getImei());

        String busStatus=busModel.getStatus();
        if(busStatus.equalsIgnoreCase("accepted")){
            ba.setText("Block");
            bb.setText("Unblock");
            bb.setEnabled(false);
        }
        if(busStatus.equalsIgnoreCase("blocked")){
            ba.setText("Block");
            bb.setText("Unblock");
            ba.setEnabled(false);
        }
        if(busStatus.equalsIgnoreCase("rejected")){
            ba.setText("Accept");
            bb.setText("Block");
            bb.setEnabled(false);
        }

        //display image
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        storageReference.child("bus_images/"+busModel.getBus_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(getApplicationContext()).load(uri.toString()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).error(R.drawable.buss).into(img);
            }
        });

        //fetching owner details
        mReference=FirebaseDatabase.getInstance().getReference();
        Query query=mReference.child("user").orderByKey();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        UserModel userModel=snapshot.getValue(UserModel.class);

                        if(userModel.getLogin_id().replaceAll("-","").trim().equals(busModel.getUser_id().replaceAll("-","").trim())){
                            tv_user_name.setText(userModel.getFname()+" "+userModel.getSname());
                            tv_place.setText(userModel.getPlace());
                            tv_email.setText(userModel.getEmail());
                            tv_phone.setText(userModel.getPhone());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(ViewBusdetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //fetching bus location
        mReference= FirebaseDatabase.getInstance().getReference();
        Query query=mReference.child("bus_location").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BusLocationModel busLocationModel=null;
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        BusLocationModel locationModel=snapshot.getValue(BusLocationModel.class);

                        if(locationModel.getBus_id().replaceAll("-","").trim().equals(busModel.getBus_id().replaceAll("-","").trim())){
                            busLocationModel=locationModel;
                        }
                    }
                    if(busLocationModel!=null){
                        LatLng point=new LatLng(Double.parseDouble(busLocationModel.getLattitude()),Double.parseDouble(busLocationModel.getLongitude()));
                        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(point , 7.0f) );

                        Marker marker = mMap.addMarker(new MarkerOptions().position(point).title(busLocationModel.getLast_updation())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    }
                    else{
                        Toast.makeText(ViewBusdetails.this, "Location not available...!!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ViewBusdetails.this, "Location not available....!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //update ba button status
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ba.getText().toString().equalsIgnoreCase("accept")){
                    updateBusStatus("accepted");
                }
                if(ba.getText().toString().equalsIgnoreCase("block")){
                    updateBusStatus("blocked");
                }

            }
        });

        //update bb button status
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bb.getText().toString().equalsIgnoreCase("reject")){
                    updateBusStatus("rejected");
                }
                if(bb.getText().toString().equalsIgnoreCase("block")){
                    updateBusStatus("blocked");
                }
                if(bb.getText().toString().equalsIgnoreCase("unblock")){
                    updateBusStatus("accepted");
                }
            }
        });
    }

    //update bus status
    public void updateBusStatus(String status){

        busModel.setStatus(status);
        mReference=FirebaseDatabase.getInstance().getReference("bus");
        mReference.child(busModel.getBus_id()).setValue(busModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(ViewBusdetails.this, "Updated...!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),AdminViewBus.class));
            }
        });
    }
}
