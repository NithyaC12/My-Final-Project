package com.example.lenovo.bustracking;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.model.BusLocationModel;
import com.example.lenovo.model.BusModel;
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

public class PassengerViewBusDetails extends AppCompatActivity  implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    ImageView img;
    TextView tv_bus_name,tv_bus_number,tv_bus_type;
    Button aa,ab,ba,bb;
    BusModel busModel;
    DatabaseReference mReference;
    RatingBar avg_rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_view_bus_details);

        img=(ImageView)findViewById(R.id.img);
        tv_bus_name=(TextView)findViewById(R.id.textView25);
        tv_bus_number=(TextView)findViewById(R.id.textView26);
        tv_bus_type=(TextView)findViewById(R.id.textView27);
        ba=(Button)findViewById(R.id.ba);
        bb=(Button)findViewById(R.id.bb);
        aa=(Button)findViewById(R.id.aa);
        ab=(Button)findViewById(R.id.ab);
        avg_rating=(RatingBar)findViewById(R.id.avg_rating);
        avg_rating.setEnabled(false);
        avg_rating.setMax(5);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        busModel=Passenger_SearchBus.busList.get(Passenger_SearchBus.pos);

        tv_bus_name.setText(busModel.getBus_name());
        tv_bus_number.setText(busModel.getBus_number());
        tv_bus_type.setText(busModel.getBus_type());

        //display image
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        storageReference.child("bus_images/"+busModel.getBus_image()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(getApplicationContext()).load(uri.toString()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).error(R.drawable.buss).into(img);
            }
        });

        aa.setOnClickListener(this);
        ab.setOnClickListener(this);
        ba.setOnClickListener(this);
        bb.setOnClickListener(this);

        //fetching bus avg rating
        mReference= FirebaseDatabase.getInstance().getReference();
        Query query=mReference.child("bus_rating").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        Toast.makeText(PassengerViewBusDetails.this, "No Passenger Rated...!!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PassengerViewBusDetails.this, "Location not available...!!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(PassengerViewBusDetails.this, "Location not available....!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v==aa){

        }
        else if(v==ab){
            startActivity(new Intent(getApplicationContext(),Passenger_Complaints.class));
        }
        else if(v==ba){
            startActivity(new Intent(getApplicationContext(),Passenger_Feedback.class));
        }
        else if(v==bb){
            startActivity(new Intent(getApplicationContext(),Passenger_Ratings.class));
        }
    }
}
