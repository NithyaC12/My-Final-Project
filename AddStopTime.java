package com.example.lenovo.bustracking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.model.BusStopModel;
import com.example.lenovo.model.StopAllocationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class AddStopTime extends AppCompatActivity {

    Button save;
    EditText ed_ordinary,ed_limited,ed_fast_passenger,ed_super_fast,ed_super_express,ed_super_delux;
    StopAllocationModel allocationModel;
    BusStopModel busStopModel;
    String route="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop_time);

        save=(Button)findViewById(R.id.save);
        ed_ordinary=(EditText)findViewById(R.id.ordinary);
        ed_limited=(EditText)findViewById(R.id.limited);
        ed_fast_passenger=(EditText)findViewById(R.id.fast);
        ed_super_fast=(EditText)findViewById(R.id.super_fast);
        ed_super_express=(EditText)findViewById(R.id.super_express);
        ed_super_delux=(EditText)findViewById(R.id.super_delux);

        String allocateString=getIntent().getStringExtra("allocate");
        String stopString=getIntent().getStringExtra("stop");
        route=getIntent().getStringExtra("route");
        Gson gson=new Gson();
        allocationModel=gson.fromJson(allocateString,StopAllocationModel.class);
        busStopModel=gson.fromJson(stopString,BusStopModel.class);

        if(!busStopModel.getStop_type().contains("ordinary")){
            ed_ordinary.setEnabled(false);
            ed_ordinary.setText("no");
        }
        else{
            ed_ordinary.setText(allocationModel.getOrdinary());
        }
        if(!busStopModel.getStop_type().contains("limited")){
            ed_limited.setEnabled(false);
            ed_limited.setText("no");
        }
        else{
            ed_limited.setText(allocationModel.getLimited_stop());
        }
        if(!busStopModel.getStop_type().contains("fast passenger")){
            ed_fast_passenger.setEnabled(false);
            ed_fast_passenger.setText("no");
        }
        else{
            ed_fast_passenger.setText(allocationModel.getFast_passenger());
        }
        if(!busStopModel.getStop_type().contains("super fast")){
            ed_super_fast.setEnabled(false);
            ed_super_fast.setText("no");
        }
        else{
            ed_super_fast.setText(allocationModel.getSuper_fast());
        }
        if(!busStopModel.getStop_type().contains("super express")){
            ed_super_express.setEnabled(false);
            ed_super_express.setText("no");
        }
        else{
            ed_super_express.setText(allocationModel.getSuper_express());
        }
        if(!busStopModel.getStop_type().contains("super delux")){
            ed_super_delux.setEnabled(false);
            ed_super_delux.setText("no");
        }
        else{
            ed_super_delux.setText(allocationModel.getSuper_delux());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allocationModel.setOrdinary(ed_ordinary.getText().toString());
                allocationModel.setLimited_stop(ed_limited.getText().toString());
                allocationModel.setFast_passenger(ed_fast_passenger.getText().toString());
                allocationModel.setSuper_fast(ed_super_fast.getText().toString());
                allocationModel.setSuper_express(ed_super_express.getText().toString());
                allocationModel.setSuper_delux(ed_super_delux.getText().toString());
                DatabaseReference mReference= FirebaseDatabase.getInstance().getReference("stop_allocation");
                mReference.child(allocationModel.getStop_allocation_id()).setValue(allocationModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(AddStopTime.this, "Added...!!", Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(getApplicationContext(),AdminViewBusStops.class);
                        in.putExtra("routes",route);
                        startActivity(in);
                    }
                });

            }
        });
    }
}
