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
import com.example.lenovo.model.FeedbackModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Passenger_Feedback extends AppCompatActivity {

    EditText ed_feedback;
    Button b_send;
    DatabaseReference mReference;
    BusModel busModel;
    FeedbackModel feedbackModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger__feedback);

        ed_feedback=(EditText)findViewById(R.id.feedback);
        b_send=(Button)findViewById(R.id.send);

        busModel=Passenger_SearchBus.busList.get(Passenger_SearchBus.pos);
        feedbackModel=new FeedbackModel();

        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReference= FirebaseDatabase.getInstance().getReference("feedback");
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                feedbackModel.setFeedback_id(mReference.push().getKey());
                feedbackModel.setBus_id(busModel.getBus_id());
                feedbackModel.setFeedback_detail(ed_feedback.getText().toString());
                feedbackModel.setFeedback_date(sdf.format(new Date()));
                feedbackModel.setPassenger_id(sh.getString("lid","0"));

                mReference.child(feedbackModel.getFeedback_id()).setValue(feedbackModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(Passenger_Feedback.this, "Success...!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),PassengerViewBusDetails.class));
                    }
                });
            }
        });
    }
}
