package com.example.lenovo.bustracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.RatingModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Passenger_Ratings extends AppCompatActivity {

    RatingBar ratingBar;
    Button b_add;
    RatingModel ratingModel;
    BusModel busModel;
    DatabaseReference mReference;
    String login_id="";
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__ratings);

        b_add=(Button)findViewById(R.id.add);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        login_id=sh.getString("lid","0");
        busModel=Passenger_SearchBus.busList.get(Passenger_SearchBus.pos);
        mReference= FirebaseDatabase.getInstance().getReference();
        Query query=mReference.child("bus_rating").orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        try {
                            RatingModel model=snapshot.getValue(RatingModel.class);
                            if((model.getPassenger_id().replaceAll("-","").trim().equalsIgnoreCase(login_id.replaceAll("-","").trim()))&&(model.getBus_id().replaceAll("-","").trim().equalsIgnoreCase(busModel.getBus_id().replaceAll("-","").trim()))){
                                try {
                                    ratingModel=model;
                                    ratingBar.setRating(Float.parseFloat(model.getBus_rating()));
                                    flag=1;
                                }catch (Exception e){
                                    Log.d("2222=======",e.toString());

                                }
                            }
                        }catch (Exception e){
                            Log.d("1111=======",e.toString());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                DatabaseReference mReference1=FirebaseDatabase.getInstance().getReference("bus_rating");
                //insert
                if(flag==0){

                    ratingModel=new RatingModel();
                    ratingModel.setRating_id(mReference1.push().getKey());
                    ratingModel.setBus_id(busModel.getBus_id());
                    ratingModel.setBus_rating(ratingBar.getRating()+"");
                    ratingModel.setDate(sdf.format(new Date()));
                    ratingModel.setPassenger_id(login_id);
                    mReference1.child(ratingModel.getRating_id()).setValue(ratingModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(Passenger_Ratings.this, "Success..!!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),PassengerViewBusDetails.class));
                        }
                    });
                }
                //update
                else if(flag==1){
                    ratingModel.setBus_rating(ratingBar.getRating()+"");
                    ratingModel.setDate(sdf.format(new Date()));
                    mReference1.child(ratingModel.getRating_id()).setValue(ratingModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(Passenger_Ratings.this, "Success..!!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),PassengerViewBusDetails.class));
                        }
                    });
                }
            }
        });

    }
}
