package com.example.lenovo.bustracking;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.model.RouteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BusRoutes extends AppCompatActivity {

    EditText ed15,ed16,ed17,ed18;
    Button b4,b5;
    DatabaseReference mReference;
    String route_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_routes);

        ed15=(EditText)findViewById(R.id.editText15);
        ed16=(EditText)findViewById(R.id.editText16);
        ed17=(EditText)findViewById(R.id.editText17);
        ed18=(EditText)findViewById(R.id.editText18);
        b4=(Button)findViewById(R.id.button4);
        b5=(Button)findViewById(R.id.button5);

        mReference= FirebaseDatabase.getInstance().getReference("route");
        route_id=mReference.push().getKey();
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RouteModel routeModel=new RouteModel();
                routeModel.setRoute_id(route_id);
                routeModel.setRoute_name(ed15.getText().toString());
                routeModel.setRoute_number(ed16.getText().toString());
                routeModel.setRoute_from(ed17.getText().toString());
                routeModel.setRoute_to(ed18.getText().toString());
                
                mReference.child(route_id).setValue(routeModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(BusRoutes.this, "Success...!!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
