package com.example.lenovo.bustracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.ComplaintModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Passenger_Complaints extends AppCompatActivity {

    EditText ed_complaint;
    Button b_send;
    DatabaseReference mReference;
    BusModel busModel;
    ComplaintModel complaintModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__complaints);
        
        ed_complaint=(EditText)findViewById(R.id.complaint);
        b_send=(Button)findViewById(R.id.send);
        
        complaintModel=new ComplaintModel();
        busModel=Passenger_SearchBus.busList.get(Passenger_SearchBus.pos);
        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                mReference= FirebaseDatabase.getInstance().getReference("complaint");
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                complaintModel.setComplaint_id(mReference.push().getKey());
                complaintModel.setBus_id(busModel.getBus_id());
                complaintModel.setComplaint_date(sdf.format(new Date()));
                complaintModel.setComplaint_detail(ed_complaint.getText().toString());
                complaintModel.setPassenger_id(sh.getString("lid","0"));
                complaintModel.setReply("nill");
                complaintModel.setReply_date("nill");
                
                mReference.child(complaintModel.getComplaint_id()).setValue(complaintModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(Passenger_Complaints.this, "Success...!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),PassengerViewBusDetails.class));
                    }
                });
            }
        });
    }
}
