package com.example.lenovo.bustracking;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.model.BusLocationModel;
import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.DriverModel;
import com.example.lenovo.model.RatingModel;
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

public class OwnerViewBusDetails extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView img,dimg;
    TextView tv_bus_name,tv_bus_number,tv_bus_type,tv_user_name,tv_phone,cdr,tv_status;
    Button ba,bb,alcted;
    BusModel busModel;
    DatabaseReference mReference;
    LinearLayout lvd,lv_view;
    RatingBar avg_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_view_bus_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        img=(ImageView)findViewById(R.id.img);
        dimg=(ImageView)findViewById(R.id.dimg);
        tv_bus_name=(TextView)findViewById(R.id.textView25);
        tv_bus_number=(TextView)findViewById(R.id.textView26);
        tv_bus_type=(TextView)findViewById(R.id.textView27);
        tv_user_name=(TextView)findViewById(R.id.oname);
        tv_phone=(TextView)findViewById(R.id.ophone);
        tv_status=(TextView)findViewById(R.id.status);
        cdr=(TextView)findViewById(R.id.cdr);
        ba=(Button)findViewById(R.id.ba);
        bb=(Button)findViewById(R.id.bb);
        alcted=(Button)findViewById(R.id.allocate);
        lvd=(LinearLayout)findViewById(R.id.lvd);
        lv_view=(LinearLayout)findViewById(R.id.lview);
        avg_rating=(RatingBar)findViewById(R.id.avg_rating);
        avg_rating.setEnabled(false);
        avg_rating.setMax(5);

        busModel=Owner_ViewBus.busList.get(Owner_ViewBus.pos);

        tv_bus_name.setText(busModel.getBus_name());
        tv_bus_number.setText(busModel.getBus_number());
        tv_bus_type.setText(busModel.getBus_type());
        tv_status.setText(busModel.getStatus());

        //display image
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        storageReference.child("bus_images/"+busModel.getBus_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(getApplicationContext()).load(uri.toString()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).error(R.drawable.buss).into(img);
            }
        });

        if(busModel.getStatus().equalsIgnoreCase("pending")||busModel.getStatus().equalsIgnoreCase("blocked")){
            lvd.setVisibility(View.GONE);
            lv_view.setVisibility(View.GONE);
            bb.setVisibility(View.GONE);
        }

        //fetching driver details
        mReference= FirebaseDatabase.getInstance().getReference();
        Query query=mReference.child("driver").orderByKey();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int flag=0;
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        DriverModel driverModel=snapshot.getValue(DriverModel.class);

                        if(driverModel.getBus_id().replaceAll("-","").trim().equals(busModel.getBus_id().replaceAll("-","").trim())){
                            tv_user_name.setText(driverModel.getDriver_name());
                            tv_phone.setText(driverModel.getPhone());

                            //display driver image
                            StorageReference storageReference= FirebaseStorage.getInstance().getReference();
                            storageReference.child("driver_images/"+driverModel.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Picasso.with(getApplicationContext()).load(uri.toString()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).error(R.drawable.buss).into(dimg);
                                }
                            });
                            flag=1;
                        }
                    }
                }
                if(flag==0){

                    lvd.setVisibility(View.GONE);
                    lv_view.setVisibility(View.VISIBLE);
                }
                else{

                    lvd.setVisibility(View.VISIBLE);
                    lv_view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(OwnerViewBusDetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //change driver
        cdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OwnerViewDrivers.class));
            }
        });

        //view routes
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Owner_AllocateBus.class));
            }
        });

        //add notes
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //allocate driver
        alcted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),OwnerViewDrivers.class));
            }
        });

        //fetching bus avg rating
        mReference= FirebaseDatabase.getInstance().getReference();
        Query query2=mReference.child("bus_rating").orderByKey();
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    float total=0;
                    int count=0;
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        RatingModel ratingModel=snapshot.getValue(RatingModel.class);

                        if(ratingModel.getBus_id().replaceAll("-","").trim().equals(busModel.getBus_id().replaceAll("-","").trim())){
                            total+=Float.parseFloat(ratingModel.getBus_rating());
                            count++;
                        }
                    }
                    if(count>0){
                        total/=count;
                        avg_rating.setRating(total);
                    }
                    else{
                        Toast.makeText(OwnerViewBusDetails.this, "No Passenger Rated...!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                        Toast.makeText(OwnerViewBusDetails.this, "Location not available...!!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(OwnerViewBusDetails.this, "Location not available....!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
